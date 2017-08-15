package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.util.ArrayList;

import markmann.dennis.fileExtractor.mediaObjects.MediaType;

/**
 * Class used to help with everything concidering settings.
 *
 * @author Dennis.Markmann
 */

public class SettingHandler {

    private static GeneralSettings generalSettings = new GeneralSettings();
    private static ArrayList<TypeSettings> settingList = new ArrayList<>();
    private static File folder = new File("./Settings/");

    /**
     * Creating general, series and anime settings with default values.
     */
    public static void createDefaultSettings() {
        generalSettings = new GeneralSettings();
        settingList.add(new TypeSettings());
        TypeSettings seriesSettings = new TypeSettings();
        seriesSettings.setType(MediaType.Series);
        settingList.add(seriesSettings);
    }

    /**
     * Returning every kind of settings existing.
     *
     * @return ArrayList containing settings.
     */
    public static ArrayList<Settings> getAllSettings() {
        ArrayList<Settings> settings = new ArrayList<>();
        settings.add(generalSettings);
        for (TypeSettings s : settingList) {
            settings.add(s);
        }
        return settings;
    }

    public static GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    /**
     * Searches and returns existing settings for the given name. Creates new settings with that name if they dont exist yet.
     *
     * @param name of the settings to search for.
     * @return the found / created settings.
     */
    private static TypeSettings getMatchingTypeSettings(String name) {
        for (TypeSettings typeSettings : settingList) {
            if ((typeSettings.getType().toString() + ".xml").equals(name)) {
                return typeSettings;
            }
        }
        TypeSettings typeSettings = new TypeSettings();
        settingList.add(typeSettings);
        return typeSettings;
    }

    public static ArrayList<TypeSettings> getTypeSettings() {
        return settingList;
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
                else if (name.equals("Anime.xml") || name.equals("Series.xml")) {
                    TypeSettings typeSettings = getMatchingTypeSettings(name);
                    new XMLFileReader().readSettingsXML(name, typeSettings, initial);
                }

            }
        }
    }

    /**
     * Used to start writing all currently configured settings into their XML files.
     */
    public static void writeSettingsToXML() {
        for (final TypeSettings settings : SettingHandler.getTypeSettings()) {
            new XMLFileWriter().createXmlFile((settings.getType().toString() + ".xml"), settings);
        }
        new XMLFileWriter().createXmlFile("General.xml", SettingHandler.getGeneralSettings());
    }
}
