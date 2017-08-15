package markmann.dennis.fileExtractor.logic;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Medium;
import markmann.dennis.fileExtractor.settings.SettingHandler;
import markmann.dennis.fileExtractor.systemTray.SystemTrayMenu;

/**
 * Used to display popup notifications. Currently only works with up to date windows systems.
 *
 * @author Dennis.Markmann
 */

public class NotificationHelper {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Shows an error notification popup in case the systemTray and popupNotification settings are enabled.
     *
     * @param errorMessage: The message shown in the popup.
     */

    public static void showErrorNotification(String errorMessage, boolean logErrorMessage, Exception exception) {
        if (SettingHandler.getGeneralSettings().useSystemTray() && SettingHandler.getGeneralSettings().usePopupNotification()) {
            SystemTrayMenu.sendTextPopup(errorMessage, MessageType.ERROR);
        }
        if (logErrorMessage) {
            if (exception != null) {
                LOGGER.error(errorMessage, exception);
            }
            else {
                LOGGER.error(errorMessage);
            }
        }
    }

    /**
     * Showing a notification popup displaying the newest processed media file names.
     *
     * @param mediaList: List containing the information about the recently processed media files.
     */

    static void showExtractionNotification(ArrayList<Medium> mediaList) {
        if (!SettingHandler.getGeneralSettings().useSystemTray()
                || !SettingHandler.getGeneralSettings().usePopupNotification()) {
            return;
        }
        StringBuilder fileNames = new StringBuilder();
        int i = 0;
        String infoString = "new file:";
        for (Medium medium : mediaList) {
            fileNames.append("\n");
            fileNames.append(medium.getCompleteTitle());
            i++;
            // Cant show more than 2 entries with the windows 10 default popup anyway.
            if (i == 2) {
                infoString = mediaList.size() + " new files:";
                break;
            }
        }
        SystemTrayMenu.sendTextPopup("Extracted " + infoString + fileNames.toString(), MessageType.INFO);
    }
}
