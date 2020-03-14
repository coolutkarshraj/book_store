package com.io.bookstores.utility;


import com.io.bookstores.Config;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class UrlLocator {
    private static String tempIP = "" ;

    public static String getBaseIP() {

        if (tempIP.equals("") || tempIP == null) {
            return Config.IP;
        } else {
            return tempIP;
        }
    }

    public static void setBaseIP(String ip) {
        tempIP = ip;
    }

    public static String getFinalUrl(String url) {
        String ip = getBaseIP();
        return "http://" + ip + "/api/" + url;
    }

    public static String getFinalUrl(String url, String params) {
        String ip = getBaseIP();
        return "http://" + ip + "/api/" + url + params;
    }
}
