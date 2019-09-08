package com.javainterviewpoint;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlFileToJson {
    public static void main(String[] args) {
        new XmlFileToJson().main();
    }

    public void main() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        //File configFile = new File(classLoader.getResource("config.xml").getFile());

        String data = "";
        try {
            // Read the student.xml
            data = FileUtils.readFileToString(new File(System.getProperty("user.dir") + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration" + File.separator + "config.xml"), "UTF-8");

            // Create a new XmlMapper to read XML tags
            XmlMapper xmlMapper = new XmlMapper();

            //Reading the XML
            JsonNode jsonNode = xmlMapper.readTree(data.getBytes());

            //Create a new ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            String value = objectMapper.writeValueAsString(jsonNode);

            System.out.println("*** Converting XML to JSON ***");
            System.out.println(value);

            JsonNode node = new ObjectMapper().readTree(value);
            System.out.println(node.findValue("first").textValue());

            List<JsonNode> jsonNode1 = node.findValues("first");
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