package ru.chernykh.MySecondTestAppSpringBoot.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {

    public static SimpleDateFormat getCustomerFormat(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
}
