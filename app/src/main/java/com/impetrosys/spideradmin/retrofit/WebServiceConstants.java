package com.impetrosys.spideradmin.retrofit;


public class WebServiceConstants {
    public static final String URL = "https://tellsid.softintelligence.co.uk/index.php/apitellsid/postdetails/"; //test api
    public static final String chatUrl = "http://live.thechangeconsultancy.co/tellsid/index.php/apitellsid/";
    public static String getMethodUrl(String methodName) {
        String url = "";
        url = URL + methodName;
        return url;
    }
}
