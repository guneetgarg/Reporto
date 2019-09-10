package Util;

import java.io.IOException;
import java.util.List;

public class Config {


    public static String data(String config) throws IOException {

        String value = "null";
        List<String> jsonData = Wrapper.getJsonData();
        for (int i = 0; i < jsonData.size(); i++) {
            value = String.valueOf(new Wrapper().getConfigValue(jsonData.get(i), config));
            if (!(value == "null")) {
                return value;
            }
        }
        return null;
    }

}