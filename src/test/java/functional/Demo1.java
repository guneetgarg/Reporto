package functional;


import Util.Config;

import java.io.IOException;

public class Demo1 {

    public static void main(String[] args)  {
        System.out.println(Config().data("url"));
        System.out.println(Config().data("name.first"));
        System.out.println(Config().data("name.first1"));
    }
}