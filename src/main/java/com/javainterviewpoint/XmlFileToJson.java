package com.javainterviewpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;

public class XmlFileToJson {
    public static void main(String[] args) {
        new XmlFileToJson().main();
    }

    public void main() {
        try {
            InputStream inputStream = new FileInputStream(new File(
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration" + File.separator + "config.xml"));

            String xml = IOUtils.toString(inputStream, "UTF-8");
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);

            System.out.println("*** Converting XML to JSON ***");
            System.out.println(output);

            JsonNode node = new ObjectMapper().readTree(output);
            System.out.println(node.findValue("last").textValue());

            System.out.println(node.path("author").path("url").textValue());

            List<JsonNode> jsonNode1 = node.findValues("firstcvxz¸xc");
            System.out.println("Size " + jsonNode1.size());


        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null Point Exception");
        }
    }
}