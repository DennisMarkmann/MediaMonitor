package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.MediaType;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.mediaObjects.Series;
import markmann.dennis.fileExtractor.settings.ExceptionPath;
import markmann.dennis.fileExtractor.settings.TypeSettings;

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
    private String checkForAdditionalFolder(Medium medium, TypeSettings settings, String exceptionPath) {
        String additionalFolder = "";
        boolean addSeriesFolder = false;

        if (settings.useSeriesFolder()) {
            addSeriesFolder = true;
        }

        if (exceptionPath.equals("") && settings.useCurrentlyWatchingCheck()) {
            if (!new File(settings.getCompletionPath() + "\\" + medium.getTitle()).exists()) {
                additionalFolder = additionalFolder + "\\Later\\";
                addSeriesFolder = true;
            }
        }

        if (addSeriesFolder) {
            additionalFolder = additionalFolder + medium.getTitle() + "\\";
        }
        if (settings.useSeasonFolder() && settings.getType().equals(MediaType.Series)) {
            additionalFolder = additionalFolder + ((Series) medium).getSeason() + "\\";
        }
        return additionalFolder;
    }

    private String checkForException(String fileName, ArrayList<ExceptionPath> exceptions) {
        for (ExceptionPath exceptionPath : exceptions) {
            if (fileName.toLowerCase().startsWith(exceptionPath.getName().toLowerCase())) {
                return exceptionPath.getPath();
            }
        }
        return "";
    }

    /**
     * Moving all given media files to the destination.
     *
     * @param mediaList to move.
     * @param destinationDirectory to move to.
     * @param settings
     */
    void moveFiles(final ArrayList<Medium> mediaList, final File destinationDirectory, TypeSettings settings) {

        for (final Medium medium : mediaList) {
            try {
                String title = medium.getCompleteTitle();
                String exceptionPath = this.checkForException(title, settings.getExceptions());
                String additionalFolder = this.checkForAdditionalFolder(medium, settings, exceptionPath);

                File destinationFolder = new File(destinationDirectory.getPath() + additionalFolder + "\\" + exceptionPath);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                Path destinationPath = new File(destinationFolder.toString() + "\\" + title).toPath();
                Path sourcePath = new File(medium.getCompletePath()).toPath();
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Moving '" + title + "' to '" + destinationPath + "'.");
            }
            catch (IOException e) {
                NotificationHelper.showErrorNotification("Moving of media failed.", true, e);
            }
        }
    }
}