package Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.Properties;

public class Wrapper {

    public static JsonNode convertToJson(Object obj) throws IOException {

        if (true) {
            InputStream inputStream = new FileInputStream(new File(
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration" + File.separator + "config.xml"));

            String xml = IOUtils.toString(inputStream, "UTF-8");
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
        } else if (true) {
            InputStream inputStream = new FileInputStream(new File(
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                            + "configuration" + File.separator + "config.properties"));


            Properties prop = new Properties();

            if (inputStream == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(inputStream);

            //get the property value and print it out
            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("object.man.name"));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String strJson = gson.toJson(prop);
            System.out.println(strJson);
        }


        return null;
    }


    public void checkFileType() {

    }

}
