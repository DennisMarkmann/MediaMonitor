package markmann.dennis.fileExtractor.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import markmann.dennis.fileExtractor.logic.NotificationHelper;

/**
 * Class used to make the XMLFileWriter a bit more tidy and help with creating the XML files.
 *
 * @author Dennis.Markmann
 */
class FileWriteHelper {

    final Document createDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        }
        catch (final ParserConfigurationException e) {
            NotificationHelper.showErrorNotification("Error while trying to write file.", true, e);
        }
        return null;
    }

    final Element createElement(final Document doc, final Element superiorElement, final String name, final String value) {
        final Element element = doc.createElement(name);
        if (value != null) {
            element.appendChild(doc.createTextNode(value));
        }
        superiorElement.appendChild(element);
        return element;
    }

    final Element createMainElement(final Document doc, final String name) {
        final Element element = doc.createElement(name);
        doc.appendChild(element);
        return element;
    }

    final void writeFile(final String path, final String fileName, final Document doc) {
        new File(path).mkdirs();
        final File file = new File(path + fileName);

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            FileOutputStream out = new FileOutputStream(file);
            transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
        }
        catch (final TransformerException | FileNotFoundException | UnsupportedEncodingException e) {
            NotificationHelper.showErrorNotification("Writing of '" + fileName + "' file failed.", true, e);
        }

    }

}
