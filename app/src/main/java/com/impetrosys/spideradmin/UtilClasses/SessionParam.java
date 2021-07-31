package com.impetrosys.spideradmin.UtilClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionParam {
    public String signupStage = "0", loginSession = "n";
    public String userId = "",name="",sp_id = "",login = "",role;
    String PREF_NAME = "MyPref";
    Context _context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;


    public SessionParam(Context context, String signupStage) {
        this.signupStage = signupStage;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("signupStage", signupStage);
        prefsEditor.commit();
    }


    @SuppressLint("LongLogTag")
    public SessionParam(Context context, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                userId = String.valueOf(jsonObject.optInt("id"));
                name = jsonObject.optString("name");
                role = jsonObject.optString("role");
                sp_id = jsonObject.optString("SP");

                Log.d("ID SESSION", userId);
                userId(context, userId);
                name(context, name);
                role(context, role);
                spId(context, sp_id);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @SuppressLint("LongLogTag")
    public SessionParam(Context context, JSONObject jsonObject) {
        if (jsonObject !=null){
            userId = String.valueOf(jsonObject.optInt("id"));;
            name = jsonObject.optString("name");
            role = jsonObject.optString("role");


            Log.d("ID SESSION",userId);

            userId(context,userId);
            name(context, name);
            role(context,role);
        }
    }

    public SessionParam(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getString("id", "");
        this.name = sharedPreferences.getString("name", "");
        this.role = sharedPreferences.getString("role", "");
        this.sp_id = sharedPreferences.getString("sp_id", "");
        this.login = sharedPreferences.getString("login", "");

    }


    private void spId(Context context, String sp_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("sp_id", sp_id);
        prefsEditor.commit();
    }

    public void org_name(Context context, String org_name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("org_name", org_name);
        prefsEditor.commit();
    }

    public void dept_id(Context context, String dept_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("dept_id", dept_id);
        prefsEditor.commit();
    }

    public void userId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("id", userId);
        prefsEditor.commit();
    }


    public void off_mobile(Context context, String off_mobile) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("off_mobile", off_mobile);
        prefsEditor.commit();
    }

    public void name(Context context, String login_name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("name", login_name);
        prefsEditor.commit();
    }
    public void role(Context context, String role_) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("role", role_);
        prefsEditor.commit();
    }



    public void deviceId(Context context, String device_id) {
        Log.d("DeviceId Session", device_id);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("deviceId", device_id);
        prefsEditor.commit();
    }


    public void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        /*AccessToken.setCurrentAccessToken(null);
        LoginManager.getInstance().logOut();*/
        prefsEditor.clear();
        prefsEditor.commit();
    }


    @Override
    public String toString() {
        return "SessionParam [name=" + "]";
    }


    public void loginSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("login", "yes");
        prefsEditor.commit();
    }

    public void UserloginSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("login", "yes");
        prefsEditor.commit();
    }

    public void saveArrayList(ArrayList<String> list, String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void saveInSp(String key, boolean value, Context context) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


}
