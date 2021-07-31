package com.impetrosys.spideradmin.retrofit;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseRequestParser {
    public String message;
    public String mResponseCode = "0";
    private JSONObject mRespJSONObject = null;

    /*public boolean parseJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                mRespJSONObject = new JSONObject(json);
                if (null!=mRespJSONObject){
                    boolean status=true;
                    if (mRespJSONObject.optBoolean("msg_code")==status){
                        return true;
                    } else {
                        message=mRespJSONObject.optString("message");
                        return false;
                    }
                }else{
                    message=mRespJSONObject.optString("message");
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR :",e.getMessage());
            }

        }
        message=mRespJSONObject.optString("message");
        return false;
    }*/
    public boolean parseJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                mRespJSONObject = new JSONObject(json);
                if (null!=mRespJSONObject){
                    if (mRespJSONObject.optInt("msg_code")==1){
                        return true;
                    } else {
                        message=mRespJSONObject.optString("message");
                        return false;
                    }
                }else{
                    message=mRespJSONObject.optString("message");
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERROR :",e.getMessage());
            }

        }
        message=mRespJSONObject.optString("message");
        return false;
    }

    public Object getDataObject() {
        if (null == mRespJSONObject) {
            return null;
        }
        try {
            return mRespJSONObject.getJSONObject("");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public JSONArray getDataArray() {
        if (null == mRespJSONObject) {
            return null;
        }
        try {
            return mRespJSONObject.getJSONArray("");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



//    public JSONArray getDataArray() {
//        if (null == mRespJSONObject) {
//            return null;
//        }
//        try {
//            if(mRespJSONObject.optJSONArray("data")!=null){
//                return mRespJSONObject.optJSONArray("data");
//            }else {
//                return mRespJSONObject.optJSONArray("response");
//            }
//        } catch (Exception e) {
//            return mRespJSONObject.optJSONArray("data");
//            //e.printStackTrace();
//            //return null;
//        }
//    }
//
//    public Object getDataObject() {
//        if (null == mRespJSONObject) {
//            return null;
//        }
//        try {
//            return mRespJSONObject.optJSONObject("data");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
