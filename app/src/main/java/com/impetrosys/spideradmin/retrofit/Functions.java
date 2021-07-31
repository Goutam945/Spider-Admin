package com.impetrosys.spideradmin.retrofit;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.JsonObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
public class Functions extends Application {

    private static Functions functions;

    public static Functions getClient() {
        if (functions == null) {
            functions = new Functions();
        }
        return functions;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // FacebookSdk.sdkInitialize(this.getApplicationContext());
    }

    public HashMap<String, String> getHashMapObject(String... nameValuePair) {
        HashMap<String, String> hashMap = null;

        if (null != nameValuePair && nameValuePair.length % 2 == 0) {

            hashMap = new HashMap<>();

            int i = 0;
            while (i < nameValuePair.length) {
                hashMap.put(nameValuePair[i], nameValuePair[i + 1]);
                i += 2;
            }

        }

        return hashMap;
    }

    public JsonObject getJsonMapObject(String... nameValuePair) {
        JsonObject jsonObject = null;

        if (null != nameValuePair && nameValuePair.length % 2 == 0) {

            jsonObject = new JsonObject();

            int i = 0;
            while (i < nameValuePair.length) {
                jsonObject.addProperty(nameValuePair[i], nameValuePair[i + 1]);
                i += 2;
            }

        }

        return jsonObject;
    }

    public String getNextDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        return sdf.format(tomorrow);
    }

    /* Input in yyyy-MM-dd */
    public String DateInMobileView_(String dateStr) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /* Input in yyyy-MM-dd */
    public String DateInMobileView2(String dateStr) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /* Input in dd MMM yyyy */
    public String DateInServerFormat(String dateStr) {
        String inputPattern = "dd MMM yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String getTodaysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        return sdf.format(today);
    }

    public double getRoundToNextInt(String rate) {

        return TextUtils.isEmpty(rate) ? 0 : (int) Math.ceil(Float.valueOf(rate));
    }

    public String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();


            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }

        return hexString.toString();


    }

    private String txnID() {
        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        return hashCal("SHA-256", rndm).substring(0, 20);
    }

}
