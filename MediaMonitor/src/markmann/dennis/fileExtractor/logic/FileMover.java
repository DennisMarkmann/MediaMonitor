package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.ShowsToWatch;

/**
 * Used to move files from their current directory to a given destination.
 *
 * @author Dennis.Markmann
 */

class FileMover {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Used to handle configured exceptions for the media to move. i.e. move them to a specific folder instead of the default
     * one, create a subfolder for them e.g.
     *
     * @param medium to move.
     * @param settings configured for special behavior.
     * @param exceptionPath for even more special behavior
     * @return the path to move the file to.
     */
    private String checkForAdditionalFolder(File medium, ShowsToWatch settings) {
        String additionalFolder = "";

        additionalFolder = additionalFolder + medium.getName() + "\\";
        // if (settings.useSeasonFolder() && settings.getType().equals(MediaType.Series)) {
        // additionalFolder = additionalFolder + ((Series) medium).getSeason() + "\\";
        // }
        return additionalFolder;
    }

    /**
     * Moving all given media files to the destination.
     *
     * @param mediaList to move.
     * @param destinationDirectory to move to.
     * @param settings
     */
    void moveFiles(final ArrayList<File> mediaList, final File destinationDirectory, ShowsToWatch settings) {

        for (final File medium : mediaList) {
            try {
                String title = medium.getName();
                String additionalFolder = this.checkForAdditionalFolder(medium, settings);

                File destinationFolder = new File(destinationDirectory.getPath() + additionalFolder + "\\");
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                Path destinationPath = new File(destinationFolder.toString() + "\\" + title).toPath();
                Path sourcePath = new File(medium.getPath()).toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Moving '" + title + "' to '" + destinationPath + "'.");
            }
            catch (IOException e) {
                NotificationHelper.showErrorNotification("Moving of media failed.", true, e);
            }
        }
    }
}