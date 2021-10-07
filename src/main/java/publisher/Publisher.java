package publisher;

import broker.BrokerSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Constants;
import utils.Payload;
import utils.TransportService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Publisher {

    public static void main(String[] args) throws IOException {
        while (true) {
            String message;
            String receiver;

            List<String> receivers = new ArrayList<>();
            Socket socket;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            socket = new Socket(Constants.HOSTNAME, Constants.PORT);
            System.out.println("Input the receivers: (enter ok after typing the receivers)");
            while (!(receiver = bufferedReader.readLine()).equals("ok")) {
                receivers.add(receiver);
            }

            System.out.println("Input the message: ");
            message = bufferedReader.readLine();
            try {
                message = formXMLMessage(message, receivers);
                System.out.println("Serialized data in XML: ");
                System.out.println(message);
                BrokerSocket readWrite = new TransportService(socket);
                readWrite.writeAsync(message);
                System.out.println("data has been sent");
            } catch (ParserConfigurationException e) {
                System.out.println("connection failed");
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    static String formXMLMessage(String message, List<String> rec) throws ParserConfigurationException, TransformerException {
        Payload payload = new Payload(rec,message);
        String payloadGson = new Gson().toJson(payload);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        DOMImplementation impl = builder.getDOMImplementation();
//        Document doc = impl.createDocument(null, null, null);
//        Element rootNode = doc.createElement("message");
//        rootNode.setAttribute("text", message);
//        doc.appendChild(rootNode);
//        for (String s : rec) {
//            Element childNode = doc.createElement("receiver");
//            childNode.setTextContent(s);
//            rootNode.appendChild(childNode);
//        }
//        DOMSource domSource = new DOMSource(doc);
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        StringWriter sw = new StringWriter();
//        StreamResult sr = new StreamResult(sw);
//        transformer.transform(domSource, sr);
        return payloadGson;
//        return sw.toString();
    }
}
