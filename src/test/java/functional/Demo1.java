package functional;


import Util.Wrapper;

import java.io.IOException;

public class Demo1 {

    public static void main(String[] args) throws IOException {
        Wrapper wrapper = new Wrapper();
        System.out.println(wrapper.getJsonData().get(1));
    }
}