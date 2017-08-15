package markmann.dennis.fileExtractor.logic;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;

/**
 * Class containing just the main method used to start the program.
 *
 * @author Dennis.Markmann
 */

public class StartUp {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Main method used to start the program.
     */
    public static void main(final String[] args) {
        LOGGER.info("Application starting.");
        Controller.startApplication();
    }
}
