package Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Reporter;

import java.io.*;
import java.util.Properties;

public class Wrapper {

    public static JsonNode convertToJson(File file) throws IOException {
        String output = null;
        InputStream inputStream = new FileInputStream(new File(
                System.getProperty("user.dir") + File.separator
                        + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration" + File.separator + "config.xml"));
        if (getExtension(file).equalsIgnoreCase("xml")) {
            JSONObject jObject = XML.toJSONObject(IOUtils.toString(inputStream, "UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            output = mapper.writeValueAsString(json);
        } else if (getExtension(file).equalsIgnoreCase("properties")) {

            Properties prop = new Properties();

            if (inputStream == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            prop.load(inputStream);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            output = gson.toJson(prop);
            System.out.println(output);
        } else if (getExtension(file).equalsIgnoreCase("json")) {

        }

        System.out.println(output);
        return null;
    }


    public static void checkFileType() {
        String path = System.getProperty("user.dir") + File.separator
                + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration";
        File folder = new File(path);
        if (folder.exists()) {
            path += File.separator + getEnvValue();
            folder = new File(path);
            System.out.println(folder.exists());
        } else {

        }
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                System.out.println(FilenameUtils.getExtension(listOfFiles[i].getName()));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

    }

    public static String getExtension(File fileName) {
        return FilenameUtils.getExtension(fileName.getName());
    }

    public static String getEnvValue() {
        if (!(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment") == null)) {
            return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment");
        } else if (!(System.getProperty("env") == null)) {
            return System.getProperty("env");
        }
        return null;
    }
}
