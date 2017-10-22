package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.util.ArrayList;

/**
 * Class used to help with everything concidering settings.
 *
 * @author Dennis.Markmann
 */

public class SettingHandler {

    private static GeneralSettings generalSettings = new GeneralSettings();
    private static ShowsToWatch showsToWatch = new ShowsToWatch();
    private static File folder = new File("./Settings/");

    /**
     * Returning every kind of settings existing.
     *
     * @return ArrayList containing settings.
     */
    public static ArrayList<Settings> getAllSettings() {
        ArrayList<Settings> settings = new ArrayList<>();
        settings.add(generalSettings);
        settings.add(showsToWatch);
        return settings;
    }

    public static GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    public static ShowsToWatch getShowsToWatch() {
        return showsToWatch;
    }

    /**
     * Used to start reading in all settings from XML files.
     *
     * @param initial: First time reading in -> no logging for value changes.
     */
    public static void readSettingsFromXML(boolean initial) {
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String name = fileEntry.getName();
                if (name.equals("General.xml")) {
                    new XMLFileReader().readSettingsXML(name, generalSettings, initial);
                }
                else if (name.equals("ShowsToWatch.xml")) {
                    new XMLFileReader().readSettingsXML(name, showsToWatch, initial);
                }
            }
        }
    }

    /**
     * Used to start writing all currently configured settings into their XML files.
     */
    public static void writeSettingsToXML() {
        new XMLFileWriter().createXmlFile("ShowsToWatch.xml", SettingHandler.getShowsToWatch());
        new XMLFileWriter().createXmlFile("General.xml", SettingHandler.getGeneralSettings());
    }
}
