package Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Wrapper {

    public static String convertToJson(File file) throws IOException {
        String output = null;

        InputStream inputStream = new FileInputStream(file.getAbsolutePath());

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
        } else if (getExtension(file).equalsIgnoreCase("json")) {

        }
        return output;
    }


    public static List<String> getJsonData() throws IOException {
        String path = System.getProperty("user.dir") + File.separator +
                "src" + File.separator + "main" + File.separator + "resources" + File.separator +
                "configuration";

        File folder = new File(path);

        if (folder.exists()) {
            path += File.separator + getEnvValue();
            folder = new File(path);
            if (folder.exists()) {
                System.out.println(folder.exists());
                System.out.println(path);
            }
        } else {
            Assert.fail("configuration folder missing from resources folder");
        }
        File[] listOfFiles = folder.listFiles();
        List<String> jsonList = new LinkedList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                jsonList.add(convertToJson(new File(path + File.separator + listOfFiles[i].getName())));
            }
        }
        return jsonList;
    }

    public static String getExtension(File fileName) {
        return FilenameUtils.getExtension(fileName.getName());
    }

    public static String getEnvValue() {
        String res;
        try {
            res = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("environment");
        } catch (NullPointerException e) {
            res = System.getProperty("env");
        }
        return res;
    }

}
