package com.plj.java.flink.utils.config;

public class ConfigKeys {
    public static String  DRIVER_CLASS()
    {
        return "com.mysql.jdbc.Driver";
    };
    public static String  SOURCE_DRIVER_URL()
    {
        return "jdbc:mysql://10.2.206.21:3306/haolaoban";
    };
    public static String  SOURCE_USER()
    {
        return "root";
    };
    public static String  SOURCE_PASSWORD()
    {
        return "thbl123";
    };
    public static String  SOURCE_SQL()
    {
        return "select name, email from the_user";
    };


    public static String  SINK_DRIVER_URL()
    {
        return "";
    };
    public static String  SINK_USER()
    {
        return "root";
    };
    public static String  SINK_PASSWORD()
    {
        return "thbl123";
    };
    public static String  SINK_SQL()
    {
        return "";
    };
}
