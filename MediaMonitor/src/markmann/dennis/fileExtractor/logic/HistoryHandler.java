package markmann.dennis.fileExtractor.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.mediaObjects.Anime;
import markmann.dennis.fileExtractor.mediaObjects.Medium;

/**
 * Class used for creating and expanding the history file containing information about the media files processed by the
 * application.
 *
 * @author Dennis.Markmann
 */

class HistoryHandler {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");
    private String historyPath = "./Logs/History.txt";

    /**
     * Adds new entries in the history file for recently processed media files. Creates the history file in case it doesn't
     * exist yet.
     *
     * @param mediaList: List containing the information about the recently processed media files.
     */
    void addToHistory(ArrayList<Medium> mediaList) {
        try {
            if (new File(this.historyPath).createNewFile()) {
                LOGGER.info("History file created successfully.");
            }
        }
        catch (IOException e1) {
            NotificationHelper.showErrorNotification("Error while trying to create '" + this.historyPath + "'.", true, e1);
        }
        if (!mediaList.isEmpty()) {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.historyPath, true)))) {
                StringBuilder sb = new StringBuilder();
                Date date = new Date();
                sb.append(this.handleDayChange(date));
                for (Medium medium : mediaList) {
                    sb.append(new SimpleDateFormat("HH:mm:ss").format(date));
                    sb.append("  (");
                    sb.append(medium.getClass().getSimpleName());
                    sb.append(")  ");
                    if (medium instanceof Anime) {
                        sb.append(" ");
                    }
                    sb.append(medium.getCompleteTitleNoExt());
                    sb.append("\n");
                }
                out.print(sb.toString());
            }
            catch (IOException e) {
                NotificationHelper.showErrorNotification("Error while trying to access '" + this.historyPath + "'.", true, e);
                return;
            }
        }
    }

    /**
     * Returning the date of the last extraction according to the log file. Format("dd.MM.yyyy").
     *
     * @return date of the last extraction.
     */
    private String getLastExtractionDate() {
        String lastExtractionDate = "";
        try {
            String history = Files.lines(Paths.get(this.historyPath)).collect(Collectors.joining("\n"));
            if (!history.isEmpty()) {
                try {
                    int index = history.lastIndexOf(") ***");
                    lastExtractionDate = history.substring(index - 10, index);
                }
                catch (StringIndexOutOfBoundsException e) {
                    // nothing to do here, returning the empty String is fine.
                }
            }
        }
        catch (IOException e) {
            NotificationHelper.showErrorNotification("Error while trying to access '" + this.historyPath + "'.", true, e);
        }
        return lastExtractionDate;
    }

    /**
     * Checks if the date changed since the last extraction. If it did: Returns a String containing the information for the new
     * date header. If not: Returns an empty String.
     *
     * @param date: Date object containing the current time.
     * @return String containing the new date header.
     */
    private String handleDayChange(Date date) {
        String newDateString = new SimpleDateFormat("dd.MM.yyyy").format(date);
        if (!this.getLastExtractionDate().equals(newDateString)) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("*** ");
            sb.append(Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
            sb.append(" (");
            sb.append(newDateString);
            sb.append(") ***");
            sb.append("\n");
            return sb.toString();
        }
        return "";
    }
}
