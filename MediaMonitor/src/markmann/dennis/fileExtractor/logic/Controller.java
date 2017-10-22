package markmann.dennis.fileExtractor.logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

/**
 * Controller class used for application management like handling the timer and write access.
 *
 * @author Dennis.Markmann
 */

public class Controller {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private static Timer timer = null;
    private static boolean timerIsActive = false;
    private static boolean applicationIsBusy = false;

    /**
     * Handles the write access so only one instance of the scan can run at the same time.
     *
     * @return if the write access is locked or not.
     */
    static boolean applyForWriteAccess() {
        while (applicationIsBusy) {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                LOGGER.error(e);
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Initial scan for files to extract.
     */
    public static void initiateManualScan() {
        for (String pathToWatch : SettingHandler.getGeneralSettings().getMonitoredPaths()) {
            new Thread(new ProcessingThread(true, pathToWatch)).start();
        }
    }

    /**
     * Checks if the application is running timer based or not / paused.
     */
    public static boolean isTimerIsActive() {
        return timerIsActive;
    }

    /**
     * Used to open a file with the given name with the configured default application.
     *
     * @param fileName of the file to open.
     */
    public static void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(fileName));
        }
        catch (IOException e) {
            LOGGER.error("File can't be opened '" + fileName + "'.", e);
            e.printStackTrace();
        }
    }

    /**
     * Returning the write access so another scan may get it and start.
     */
    static void returnWriteAccess() {
        applicationIsBusy = false;
    }

    /**
     * Controlled shutdown of the whole application.
     */
    public static void shutDownApplication() {
        LOGGER.info("Application stopped.");
        System.exit(0);
    }

    /**
     * Start method of the application. Reads the settings from XML, adds new settings to the XML files if created through an
     * update of the tool, starts the timer for the scan if configured and creates the system tray if configured.
     */
    static void startApplication() {
        boolean overwriteExistingSettings = false;
        if (overwriteExistingSettings) {
            // Use with caution! This overwrites the currently defined settings with default ones!
            SettingHandler.writeSettingsToXML();
        }
        else {
            SettingHandler.readSettingsFromXML(true);
            SettingHandler.writeSettingsToXML();
        }

        if (SettingHandler.getGeneralSettings().useTimer() && !SettingHandler.getGeneralSettings().startPaused()) {
            startTimer(true);
        }
        if (SettingHandler.getGeneralSettings().useSystemTray()) {
            new SystemTrayMenu().createSystemTrayEntry();
        }
    }

    /**
     * Starts the timer for the automatic scans.
     *
     * @param initialStart: Logs additional parameters if its the initial start.
     */
    public static void startTimer(boolean initialStart) {
        int timerInterval = SettingHandler.getGeneralSettings().getTimerInterval();
        if (initialStart) {
            LOGGER.info("Timer activated. Interval: '" + timerInterval + "' minutes.");
        }
        else {
            LOGGER.info("Timer resumed.");
        }

        timer = new Timer();
        timerIsActive = true;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                for (String pathToWatch : SettingHandler.getGeneralSettings().getMonitoredPaths()) {
                    new Thread(new ProcessingThread(false, pathToWatch)).start();
                }
            }

        }, 1000, timerInterval * 60000);
    }

    /**
     * Pauses the timer.
     */
    public static void stopTimer() {
        LOGGER.info("Timer stopped.");
        timer.cancel();
        timerIsActive = false;
    }
}
