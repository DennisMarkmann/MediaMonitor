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
     * Starts the deletion of a given list of files / folder. Subfolder are included.
     *
     * @param fileList List of files / folder to delete.
     */

    void cleanFiles(ArrayList<File> fileList) {
        for (File file : fileList) {
            this.deleteDir(file);
            LOGGER.info("Deleting folder: '" + file.getName() + "'.");
        }
    }

    /**
     * Recursively deleting the given file / folder and everything in it.
     *
     * @param file / folder to delete.
     */
    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                this.deleteDir(f);
            }
        }
        file.delete();
    }
}
