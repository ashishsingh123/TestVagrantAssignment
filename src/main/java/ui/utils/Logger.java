package ui.utils;

import org.testng.Reporter;

public class Logger {

    public static void log(String arg){
        System.out.println(arg);
        Reporter.log("<p>"+ arg +"</p>");
    }
}
