package markmann.dennis.fileExtractor.settings;

import java.lang.reflect.Field;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import markmann.dennis.fileExtractor.logic.NotificationHelper;

/**
 * Class used to write the current settings into XML files.
 *
 * @author Dennis.Markmann
 */

class XMLFileWriter {

    /**
     * Stores the given settings in the XML file of the given name / path.
     *
     * @param name of the setting file to write to.
     * @param settings to store in the file.
     */
    void createXmlFile(String name, Settings settings) {

        final FileWriteHelper helper = new FileWriteHelper();
        final Document doc = helper.createDocument();
        final Element element = helper.createMainElement(doc, "Settings");

        for (Field field : settings.getClass().getDeclaredFields()) {

            try {
                String fieldName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                helper.createElement(doc, element, fieldName, field.get(settings) + "");
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                NotificationHelper.showErrorNotification("Writing of '" + name + "' file failed.", true, e);
            }
        }
        helper.writeFile("./Settings/", name, doc);
    }
}
