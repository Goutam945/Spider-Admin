package com.impetrosys.spideradmin.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRequest<T> extends BaseRequestParser {
    private Context mContext;
    private ApiInterface apiInterface;
    private RequestReciever requestReciever;
    private boolean runInBackground = false;
    private Dialog dialog;
//    ProgressDialog progressDialog;
    private View loaderView = null;
    private int APINumber_ = 1;
    private boolean showErrorDialog = true;

    String token = "";
    SessionParam sessionParam;

    public boolean isRunInBackground() {
        return runInBackground;
    }

    public void setRunInBackground(boolean runInBackground) {
        this.runInBackground = runInBackground;
    }

    public void setLoaderView(View loaderView_) {
        this.loaderView = loaderView_;
    }

    public BaseRequest(Context context) {
        mContext = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
      //gg  dialog = getProgressesDialog(context);
      //gg  sessionParam = new SessionParam(mContext);

        //dialog.setTitle("Fetching details...");
    }

    public BaseRequest() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        dialog.setTitle("Fetching details...");
    }


    public BaseRequest(Context context, Fragment fm) {
        mContext = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
    }

    public void setBaseRequestListner(RequestReciever requestListner) {
        this.requestReciever = requestListner;

    }


    public void callAPIPostCustomURL(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();

        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }

        //  String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : " + remainingURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.postDataCustomURL(remainingURL, jsonObject);

        call.enqueue(responseCallback);
    }

    public ArrayList<Object> getDataList(JSONArray mainArray, Class<T> t) {
        Gson gsm = null;
        ArrayList<Object> list = null;
        list = new ArrayList<>();
        if (null != mainArray) {

            for (int i = 0; i < mainArray.length(); i++) {
                gsm = new Gson();
                Object object = gsm.fromJson(mainArray.optJSONObject(i).toString(), t);
                list.add(object);
            }
        }
        return list;
    }

    public ArrayList<Object> getDataListreverse(JSONArray mainArray, Class<T> t) {
        Gson gsm = null;
        ArrayList<Object> list = null;
        list = new ArrayList<>();
        if (null != mainArray) {

            for (int i = mainArray.length()-1; i >= 0; i--) {
                gsm = new Gson();
                Object object = gsm.fromJson(mainArray.optJSONObject(i).toString(), t);
                list.add(object);
            }
        }
        return list;
    }


    public Callback<JsonElement> responseCallback1 = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
//            hideLoader();
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }

            logFullResponse(responseServer, "OUTPUT");

            if (parseJson(responseServer)) {
                if (null != requestReciever) {
                    if (null != getDataArray()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
                    } else if (null != getDataObject()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataObject());
                    } else {
                        requestReciever.onSuccess(APINumber_, responseServer, message);
                    }
                }
            } else {
                if (null != requestReciever) {
                    requestReciever.onFailure(1, "" + mResponseCode, message);
                }
            }

        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
           /* if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };

    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            hideLoader();
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }

            logFullResponse(responseServer, "OUTPUT");

            if (parseJson(responseServer)) {
                if (null != requestReciever) {
                    if (null != getDataArray()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
                    } else if (null != getDataObject()) {
                        requestReciever.onSuccess(APINumber_, responseServer, getDataObject());
                    } else {
                        requestReciever.onSuccess(APINumber_, responseServer, message);
                    }
                }
            } else {
                if (null != requestReciever) {
                    requestReciever.onFailure(1, "" + mResponseCode, message);
                }
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
           /* if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };


    public Callback<JsonElement> responseCallbackCustom = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            hideLoader();
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            requestReciever.onSuccess(APINumber_, responseServer, null);
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
            /*if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };

    public Callback<JsonElement> responseCallbackCustomchat = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";

            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            requestReciever.onSuccess(APINumber_, responseServer, null);
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
            /*if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };

    private RequestType requestType = null;

    public enum RequestType {
        Post, Get
    }

    String network_error_message = "Check internet connection";


//    Handler handler = new Handler();
//    Runnable r = new Runnable() {
//        @Override
//        public void run() {
//            hideLoader();
//            if (null != requestReciever) {
//                requestReciever.onNetworkFailure(APINumber_, network_error_message);
//            }
//            if (showErrorDialog) {
//            }
//        }
//    };


    public void callAPIPost(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(remainingURL, jsonObject, "Bearer " + token);

        Log.d("Token", token);

        call.enqueue(responseCallback);
    }


    public void callAPIPostWOLoader(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        //showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(remainingURL, jsonObject, "Bearer " + token);

        Log.d("Token", token);

        call.enqueue(responseCallback);
    }

//    public void callAPIPostIMAGE(final int APINumber, JsonObject jsonObject, String remainingURL, MultipartBody.Part body, RequestBody dotId, RequestBody description, RequestBody section, RequestBody sectionId) {
//
//        APINumber_ = APINumber;
//        showLoader();
//
//        if (jsonObject == null) {
//            jsonObject = new JsonObject();
//        }
//
//        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        Log.i("BaseReq",
//                "Url" + " : "
//                        + baseURL);
//        logFullResponse(jsonObject.toString(), "INPUT");
//        //Call<JsonElement> call = apiInterface.uploadImage(body, "Bearer " + token);
//        Call<JsonElement> call = apiInterface.uploadImage(body, dotId, description, section, sectionId, "Bearer " + token);
//        Log.d("Token", token);
//        call.enqueue(responseCallback);
//    }





    public void callAPIGET(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }


    public void callPostInformation1(final int APINumber, String remainingURL, MultipartBody.Part body1, MultipartBody.Part body2, MultipartBody.Part body3, MultipartBody.Part body4, MultipartBody.Part body5, MultipartBody.Part body6, MultipartBody.Part body7, RequestBody categorytype_,RequestBody information_,RequestBody user_id_,RequestBody latitude_,RequestBody longitude_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.postInformation1(body1,body2,body3,body4,body5,body6,body7,
                categorytype_,information_,user_id_,latitude_,longitude_);
        call.enqueue(responseCallback);
    }



    public void callAPILogin(final int APINumber, String remainingURL, String username_, String password_, String device_id_,String device_type_,String device_token_) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("email",username_);
        jsonObject1.put("password",password_);
        jsonObject1.put("deviceid",device_id_);
        jsonObject1.put("devicetype",device_type_);
        jsonObject1.put("devicetoken",device_token_);

        jsonObject.put("func_name","login");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.formData(body);
        call.enqueue(responseCallback);

    }
    public void callAPIBanner(final int APINumber, String remainingURL, String name_, String url_, String image) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("name",name_);
        jsonObject1.put("url",url_);
        jsonObject1.put("photo",image);
        jsonObject.put("func_name","createbanner");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.banner(body);
        call.enqueue(responseCallback);

    }
    public void callAPIforgotpass(final int APINumber, String remainingURL, String mobile, String password) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("contact",mobile);
        jsonObject1.put("password",password);
        jsonObject.put("func_name","resetpassword");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.forgetpass(body);
        call.enqueue(responseCallback);

    }

    public void callAPIlogout(final int APINumber, String remainingURL, String userid_, String device_id_) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("uid",userid_);
        jsonObject1.put("deviceid",device_id_);
        jsonObject.put("func_name","logout");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.logout(body);
        call.enqueue(responseCallback);

    }

    public void callAPIdeletewebsite(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject.put("func_name","deletewebsite");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.websitelistdelete(body);
        call.enqueue(responseCallback);
    }
    public void callAPIdeleteSubadmin(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject.put("func_name","deleteuser");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.Subadminlistdelete(body);
        call.enqueue(responseCallback);
    }
    public void callAPIdeleteAccountdetail(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject.put("func_name","deleteaccount");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.Accountdelete(body);
        call.enqueue(responseCallback);
    }

    public void callAPIapproveUser_request(final int APINumber, String remainingURL, String id,String username,String password) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","1");
        jsonObject1.put("username",username);
        jsonObject1.put("password",password);
        jsonObject1.put("description","");

        jsonObject.put("func_name","approverequestid");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.Userrequestapprove(body);
        call.enqueue(responseCallback);

    }

    public void callAPIClientid_Changepassword(final int APINumber, String remainingURL, String id,String password,String userid) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("requestid",id);
        jsonObject1.put("password",password);
        jsonObject1.put("uid",userid);

        jsonObject.put("func_name","changerequestidpassword");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.Clientrequestchangepass(body);
        call.enqueue(responseCallback);

    }

    public void callAPIapproveDeposits_request(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","1");
        jsonObject.put("func_name","approvedepositrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositrequestapprove(body);
        call.enqueue(responseCallback);
    }
    public void callAPIapproveDepositsID_request(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","1");
        jsonObject.put("func_name","approvedepositrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositIDrequestapprove(body);
        call.enqueue(responseCallback);
    }

    public void callAPIDeposits_reject(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","2");
        jsonObject.put("func_name","approvedepositrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositrequestreject(body);
        call.enqueue(responseCallback);

    }
    public void callAPIDepositsID_reject(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","2");
        jsonObject.put("func_name","approvedepositrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositIDrequestreject(body);
        call.enqueue(responseCallback);

    }
    public void callAPIapproveWithdrawl_request(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","1");
        jsonObject.put("func_name","approverwithdrawalrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.withdrawrequestapprove(body);
        call.enqueue(responseCallback);

    }


    public void callAPIReject_request(final int APINumber, String remainingURL, String id,String description) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","2");
        jsonObject1.put("username","");
        jsonObject1.put("password","");
        jsonObject1.put("description",description);
        jsonObject.put("func_name","approverequestid");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.Userrequestreject(body);
        call.enqueue(responseCallback);

    }
    public void callAPIReject_withdrawrequest(final int APINumber, String remainingURL, String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("status","2");
        jsonObject.put("func_name","approverwithdrawalrequest");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.withdrawrequestreject(body);
        call.enqueue(responseCallback);

    }
    public void callAPAddwebsite(final int APINumber, String remainingURL, String websitename, String url, String description,JSONArray jsonArray,String Document_img) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("name",websitename);
        jsonObject1.put("url",url);
        jsonObject1.put("description",description);
        jsonObject1.put("games",jsonArray);
        jsonObject1.put("photo",Document_img);

        jsonObject.put("func_name","createwebsite");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.websitelistadd(body);
        call.enqueue(responseCallback);

    }

    public void callAPAddSubadmin(final int APINumber, String remainingURL, String sname, String smobile, String spass,String id) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("name",sname);
        jsonObject1.put("contact",smobile);
        jsonObject1.put("password",spass);
        jsonObject1.put("menu_access",id);
        jsonObject.put("func_name","createsubadmin");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.subadminadd(body);
        call.enqueue(responseCallback);

    }
    public void callAPUpdateSubadmin(final int APINumber, String remainingURL, String sname,String id ,String menuid) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("name",sname);
        jsonObject1.put("id",id);
        jsonObject1.put("menu_access",menuid);
        jsonObject.put("func_name","updatesubadminprofile");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.subadminupdate(body);
        call.enqueue(responseCallback);

    }
    public void callAPI_UPI(final int APINumber, String remainingURL, String uname,String unumber ,String uid,String UserId) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("displayname",uname);
        jsonObject1.put("number",unumber);
        jsonObject1.put("paymentmethodid",uid);
        jsonObject1.put("uid",UserId);
        jsonObject.put("func_name","createupiaccount");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.upiadd(body);
        call.enqueue(responseCallback);

    }
    public void callAPI_UpdateUPI(final int APINumber, String remainingURL, String uname,String unumber ,String upid,String paymenyid,String UserId ) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("displayname",uname);
        jsonObject1.put("number",unumber);
        jsonObject1.put("id",upid);
        jsonObject1.put("paymentmethodid",paymenyid);
        jsonObject1.put("uid",UserId);
        jsonObject.put("func_name","updateupiaccount");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.upiupdate(body);
        call.enqueue(responseCallback);

    }
    public void callAPI_BankDetailadd(final int APINumber, String remainingURL, String acoutnumber,String bankname ,String ifccode,String branch,String UserId) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("accountno",acoutnumber);
        jsonObject1.put("bankname",bankname);
        jsonObject1.put("ifsc",ifccode);
        jsonObject1.put("branch",branch);
        jsonObject1.put("uid",UserId);
        jsonObject.put("func_name","createaccount");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.bankdetailsadd(body);
        call.enqueue(responseCallback);

    }
    public void callAPI_BankDetailUpdate(final int APINumber, String remainingURL, String acoutnumber,String bankname ,String ifccode,String branch,String UserId,String uid) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();

        jsonObject1.put("accountno",acoutnumber);
        jsonObject1.put("bankname",bankname);
        jsonObject1.put("ifsc",ifccode);
        jsonObject1.put("branch",branch);
        jsonObject1.put("uid",UserId);
        jsonObject1.put("id",uid);
        jsonObject1.put("accountholder","accountholder");
        jsonObject1.put("accounttype","accounttype");
        jsonObject.put("func_name","updateaccount");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.bankdetailupdate(body);
        call.enqueue(responseCallback);

    }



    public void callAPIUpdatewebsite(final int APINumber, String remainingURL,String id ,String websitename, String url, String description,JSONArray jsonArray,String Document_img) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("id",id);
        jsonObject1.put("name",websitename);
        jsonObject1.put("url",url);
        jsonObject1.put("description",description);
        jsonObject1.put("photo",Document_img);
        jsonObject1.put("games",jsonArray);

        jsonObject.put("func_name","updatewebsite");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.websitelistupdate(body);
        call.enqueue(responseCallback);

    }
    public void callAPIgetClient_changepasslist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","changerequestidpasswordlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.clientid_chagepasslist(body);
        call.enqueue(responseCallback);
    }


    public void callAPIgetUserlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","userlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.userlist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIAcoountdetaillist(final int APINumber, String remainingURL,String userid) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject1.put("uid",userid);
        jsonObject.put("func_name","accountlist");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.accountdetaillist(body);
        call.enqueue(responseCallback);
    }

    public void callAPIgetCountlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","dashboard");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.countlist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetwithdrawlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","withdrawlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.withdrawlist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetFinancial(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","paymentmethodlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.financial(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetDepositslist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","depositlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositslist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetDeposits_IDlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","depositrequestlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.depositsidlist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetSubadminlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","subadminlist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.subadminlist(body);
        call.enqueue(responseCallback);
    }
    public void callAPIgetmenulist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","menuaccesslist");
        jsonObject.put("data",jsonObject1);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.menuaccesslist(body);
        call.enqueue(responseCallback);
    }


    public void callAPIgetUser_requestlist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","requestidlist");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.userrequestlist(body);
        call.enqueue(responseCallback);

    }
    public void callAPIgetwebsitelist(final int APINumber, String remainingURL) throws JSONException {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        jsonObject.put("func_name","websitelist");
        jsonObject.put("data",jsonObject1);

        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        showLoader();
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.websitelist(body);
        call.enqueue(responseCallback);

    }







    public void callAPIDELETE(final int APINumber, Map<String, String> map, String remainingURL, String id) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL;
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.callAPIDELETE(remainingURL + id, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }
    public void callAPIGETData(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL_);
        call.enqueue(responseCallbackCustom);
    }

    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;

        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);


            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize,
                                    response.length()));
                }
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("BaseReq", inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("BaseReq", " logFullResponse=>  " + response);
            }

        }
    }

    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
/*

    progressDialog = new ProgressDialog(ChatActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
*/
//   public ProgressDialog getProgressesDialog(Context ct) {
//       ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//
//       progressDialog.setIndeterminate(true);
//       progressDialog.setMessage("Please Wait...");
//       progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//       progressDialog.show();
//       progressDialog.setCancelable(false);
//       return progressDialog;
//}


    public Dialog getProgressesDialog(Context ct) {
        Dialog dialog = new Dialog(ct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("Fetching details...");
        dialog.setContentView(R.layout.progress_dialog_loader);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }


    public void showErrorDialog(Context ct, String msg, final int APINumber, final JsonObject jsonObject, String remainingURL) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ct);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void showLoader() {
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.VISIBLE);
                } else if (null != dialog) {
                    dialog.show();
                }
            }
        }
    }

    public void hideLoader() {
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.GONE);
                } else if (null != dialog) {
                    dialog.dismiss();
                }
            }
        }
    }

    public int TYPE_NOT_CONNECTED = 0;
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;

    public int getConnectivityStatus(Context context) {
        if (null == context) {
            return TYPE_NOT_CONNECTED;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null != activeNetwork && activeNetwork.isConnected()) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }


}
