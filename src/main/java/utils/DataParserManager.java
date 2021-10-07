package utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DataParserManager {
    Payload payloadGson;
    String stringData;

    public DataParserManager(String stringData) {
        this.stringData = stringData;
    }

    public List<String> getReceivers() {
        Payload payload = new Gson().fromJson(stringData,Payload.class);
        return payload.getTopic();
    }

    public String getMessage() {
        Payload payload = new Gson().fromJson(stringData,Payload.class);
        return payload.getMessage();
    }

    public boolean isXML() {
        try {
            Payload payload = new Gson().fromJson(stringData,Payload.class);
            this.payloadGson = payload;
            return true;
        } catch (JsonSyntaxException jsonSyntaxException)
        {
            return false;
        }
    }
}
