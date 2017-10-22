package markmann.dennis.fileExtractor.logic;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;

/**
 * Used to delete given files / folder and their subfolder.
 *
 * @author Dennis.Markmann
 */

class FileCleaner {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Starts the deletion of a given list of files / folder.
     *
     * @param fileList List of files / folder to delete.
     */

    void cleanFiles(ArrayList<File> fileList) {
        for (File file : fileList) {
            File[] contents = file.listFiles();
            if (contents == null) {
                LOGGER.info("Deleting folder: '" + file.getName() + "'.");
                file.delete();
            }
        }
    }
}
