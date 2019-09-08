package functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.sun.xml.internal.fastinfoset.sax.Properties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.json.XML;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Demo {
    public static void main(String[] args) throws FileNotFoundException {

        String pathFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties";

        System.out.println(pathFile);

        File file = new File("/Users/guneetgarg/IdeaProjects/Reporto/src/main/resources/config.properties");
        FileInputStream fileInput = new FileInputStream(file);


        Properties properties = new Properties();
        properties.load(Demo.class.getResourceAsStream("config.properties"));


    }
}