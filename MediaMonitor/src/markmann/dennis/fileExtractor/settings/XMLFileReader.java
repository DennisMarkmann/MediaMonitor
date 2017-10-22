package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import markmann.dennis.fileExtractor.logging.LogHandler;
import markmann.dennis.fileExtractor.logic.NotificationHelper;
import markmann.dennis.fileExtractor.mediaObjects.MediaType;

/**
 * Class used to read the current settings from XML files.
 *
 * @author Dennis.Markmann
 */

class XMLFileReader {

    private static final Logger LOGGER = LogHandler.getLogger("./Logs/FileExtractor.log");

    /**
     * Converts the given XML attribute into the desired object.
     *
     * @param fieldType to convert the value into.
     * @param value to convert.
     * @return the converted value as the given type. Logs an error if the type was not supported.
     */
    private Object convertValueToType(Object fieldType, String value) {
        if (fieldType.equals(boolean.class)) {
            return Boolean.valueOf(value);
        }
        else if (fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        else if (fieldType.equals(MediaType.class)) {
            if (value.equals("Anime")) {
                return MediaType.Anime;
            }
            else if (value.equals("Series")) {
                return MediaType.Series;
            }
        }
        else if (fieldType.equals(String.class)) {
            return value;
        }
        NotificationHelper
                .showErrorNotification("No handling implemented for reading given datatype '" + fieldType + "'.", true, null);
        return null;
    }

    /**
     * Method used to extract the value of the given node.
     *
     * @param element to get the value from.
     * @param name of the node searched for.
     * @return the value as a String.
     */
    private String getValueByName(Element element, String name) {
        Node node = element.getElementsByTagName(name).item(0);
        if (node != null) {
            return node.getTextContent();
        }
        return null;
    }

    /**
     * Used to read in all settings from the XML file at the given path.
     *
     * @param name / path of the XML file.
     * @param settings to save the new values into.
     * @param initial time reading the settings since program start? Only logging changed attributes if its not the first time
     *            happening.
     */
    void readSettingsXML(String name, Settings settings, boolean initial) {

        try {

            if (SettingHandler.getGeneralSettings().useExtendedLogging()) {
                LOGGER.info("Reading '" + name + "' file.");
            }

            File fXmlFile = new File("./Settings/" + name);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Settings");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nNode;

                    for (Field field : settings.getClass().getDeclaredFields()) {

                        String fieldName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                        Object oldFieldValue = field.get(settings);
                        Object newFieldValue = this
                                .convertValueToType(field.getType(), this.getValueByName(element, fieldName));
                        if ((oldFieldValue == null) || !oldFieldValue.equals(newFieldValue)) {
                            if (!initial) {
                                LOGGER.info("Changed value of '" + fieldName + "' to '" + newFieldValue + "'.");
                            }
                            field.set(settings, newFieldValue);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            NotificationHelper.showErrorNotification("Reading of '" + name + "' file failed.", true, e);
        }
    }

}