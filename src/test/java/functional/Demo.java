package functional;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.util.*;

public class Demo {
    public static void main(String[] args) {

        // try (InputStream input = Demo.class.getClassLoader().getResourceAsStream("config.properties")) {
        try {
            InputStream inputStream = new FileInputStream(new File(
                    System.getProperty("user.dir") + File.separator
                            + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                            + "configuration" + File.separator + "config.properties"));


            Properties prop = new Properties();

            if (inputStream == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(inputStream);

            //get the property value and print it out
            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("object.man.name"));

            Gson gsonObj = new Gson();
            String strJson = gsonObj.toJson(prop);
            System.out.println(strJson);

            Map<String, Object> map = new TreeMap<>();

            for (Object key : prop.keySet()) {
                List<String> keyList = Arrays.asList(((String) key).split("\\."));
                Map<String, Object> valueMap = createTree(keyList, map);
                String value = prop.getProperty((String) key);
                value = StringEscapeUtils.unescapeHtml(value);
                valueMap.put(keyList.get(keyList.size() - 1), value);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(map);

            System.out.println("Ready, converts " + prop.size() + " entries.");
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> createTree(List<String> keys, Map<String, Object> map) {
        Map<String, Object> valueMap = (Map<String, Object>) map.get(keys.get(0));
        if (valueMap == null) {
            valueMap = new HashMap<String, Object>();
        }
        map.put(keys.get(0), valueMap);
        Map<String, Object> out = valueMap;
        if (keys.size() > 2) {
            out = createTree(keys.subList(1, keys.size()), valueMap);
        }
        return out;
    }
}
