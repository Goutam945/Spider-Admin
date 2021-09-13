package com.impetrosys.spideradmin.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET()
    @Streaming
    Call<ResponseBody> downloadImage(@Url String fileUrl);


    @POST
    Call<JsonElement> postData(@Url String remainingURL, @Body JsonObject jsonObject, @Header("Authorization") String token);
    //Map<String, String> params

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);

    @GET
    Call<JsonElement> postDataTellSid(@Url String remainingURL, @QueryMap Map<String, String> map);

    @GET
    Call<JsonElement> getDataWithoutMap(@Url String remainingURL);

    @GET
    Call<JsonElement> getImageUrl(@Url String remainingURL);


    @DELETE
    Call<JsonElement> callAPIDELETE(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);


    @POST
    Call<JsonElement> postDataCustomURL(@Url String remainingURL, @Body JsonObject jsonObject);

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map);


//    name_,action_,mobile_no_,title_,body,story_desc_,user_id_



//    @Multipart
//    @POST("service.php")
//    Call<JsonElement> formData(@Part("email") RequestBody username_,
//                               @Part("password") RequestBody password_,
//                               @Part("deviceid") RequestBody device_id_,
//                               @Part("devicetype") RequestBody device_type_,
//                               @Part("devicetoken") RequestBody device_token_);



    @POST("service.php")
    Call<JsonElement> formData(@Body RequestBody login);
    @POST("service.php")
    Call<JsonElement> banner(@Body RequestBody banner);
    @POST("service.php")
    Call<JsonElement> offers(@Body RequestBody offers);
    @POST("service.php")
    Call<JsonElement> Refralcodecreate(@Body RequestBody Refralcodecreate);
    @POST("service.php")
    Call<JsonElement> forgetpass(@Body RequestBody forgetpass);

    @POST("service.php")
    Call<JsonElement> logout(@Body RequestBody logout);
    @POST("service.php")
    Call<JsonElement> clientid_chagepasslist(@Body RequestBody clientid_chagepasslist);
    @POST("service.php")
    Call<JsonElement> userlist(@Body RequestBody userlist);
    @POST("service.php")
    Call<JsonElement> closeidlist(@Body RequestBody closeidlist);
    @POST("service.php")
    Call<JsonElement> notificationlist(@Body RequestBody notificationlist);
    @POST("service.php")
    Call<JsonElement> bannerlist(@Body RequestBody notificationlist);
    @POST("service.php")
    Call<JsonElement> offerlist(@Body RequestBody notificationlist);
    @POST("service.php")
    Call<JsonElement> accountdetaillist(@Body RequestBody accountdetaillist);
    @POST("service.php")
    Call<JsonElement> countlist(@Body RequestBody countlist);
    @POST("service.php")
    Call<JsonElement> withdrawlist(@Body RequestBody withdrawlist);
    @POST("service.php")
    Call<JsonElement> financial(@Body RequestBody financial);
    @POST("service.php")
    Call<JsonElement> depositslist(@Body RequestBody depositslist);
    @POST("service.php")
    Call<JsonElement> Referalcodelist(@Body RequestBody Referalcodelist);
    @POST("service.php")
    Call<JsonElement> depositsidlist(@Body RequestBody depositsidlist);
    @POST("service.php")
    Call<JsonElement> subadminlist(@Body RequestBody subadminlist);
    @POST("service.php")
    Call<JsonElement> menuaccesslist(@Body RequestBody menuaccesslist);
    @POST("service.php")
    Call<JsonElement> userrequestlist(@Body RequestBody userrequestlist);
    @POST("service.php")
    Call<JsonElement> websitelist(@Body RequestBody websitelist);
    @POST("service.php")
    Call<JsonElement> websitelistdelete(@Body RequestBody websitelistdelete);
    @POST("service.php")
    Call<JsonElement> Subadminlistdelete(@Body RequestBody Subadminlistdelete);
    @POST("service.php")
    Call<JsonElement> bannnerdelete(@Body RequestBody Subadminlistdelete);
    @POST("service.php")
    Call<JsonElement> offersdelete(@Body RequestBody Subadminlistdelete);
    @POST("service.php")
    Call<JsonElement> Accountdelete(@Body RequestBody Accountdelete);
    @POST("service.php")
    Call<JsonElement> websitelistadd(@Body RequestBody websitelistadd);
    @POST("service.php")
    Call<JsonElement> websitelistupdate(@Body RequestBody websitelistupdate);
    @POST("service.php")
    Call<JsonElement> Userrequestapprove(@Body RequestBody Userrequestapprove);

    @POST("service.php")
    Call<JsonElement> Clientrequestchangepass(@Body RequestBody Clientrequestchangepass);
    @POST("service.php")
    Call<JsonElement> updateReferalcode(@Body RequestBody updateReferalcode);
    @POST("service.php")
    Call<JsonElement> Clientrequestnochangepass(@Body RequestBody Clientrequestnochangepass);
    @POST("service.php")
    Call<JsonElement> depositrequestapprove(@Body RequestBody depositrequestapprove);
    @POST("service.php")
    Call<JsonElement> depositIDrequestapprove(@Body RequestBody depositIDrequestapprove);
    @POST("service.php")
    Call<JsonElement> depositrequestreject(@Body RequestBody depositrequestreject);
    @POST("service.php")
    Call<JsonElement> depositIDrequestreject(@Body RequestBody depositIDrequestreject);
    @POST("service.php")
    Call<JsonElement> withdrawrequestapprove(@Body RequestBody withdrawrequestapprove);
    @POST("service.php")
    Call<JsonElement> Closeapprove(@Body RequestBody Closeapprove);
    @POST("service.php")
    Call<JsonElement> Userrequestreject(@Body RequestBody Userrequestreject);
    @POST("service.php")
    Call<JsonElement> Avtivepayment(@Body RequestBody Avtivepayment);
    @POST("service.php")
    Call<JsonElement> DeAvtivepayment(@Body RequestBody DeAvtivepayment);
    @POST("service.php")
    Call<JsonElement> withdrawrequestreject(@Body RequestBody withdrawrequestreject);
    @POST("service.php")
    Call<JsonElement> Closeidreject(@Body RequestBody Closeidreject);
    @POST("service.php")
    Call<JsonElement> subadminadd(@Body RequestBody subadminadd);
    @POST("service.php")
    Call<JsonElement> subadminupdate(@Body RequestBody subadminupdate);
    @POST("service.php")
    Call<JsonElement> upiadd(@Body RequestBody upiadd);
    @POST("service.php")
    Call<JsonElement> bankdetailsadd(@Body RequestBody bankdetailsadd);
    @POST("service.php")
    Call<JsonElement> upiupdate(@Body RequestBody upiupdate);

    @POST("service.php")
    Call<JsonElement> bankdetailupdate(@Body RequestBody bankdetailupdate);

    @Multipart
    @POST("Edit_token")
    Call<JsonElement> Tokenupdate(@Part("user_id") RequestBody user_id_,
                              @Part("token_for_user") RequestBody token_);




    @Multipart
    @POST("Sign_up")//name_,email_,mobile_,password_
    Call<JsonElement> postMessage1(
            @Part("name") RequestBody name_,
            @Part("mobile") RequestBody mobile_,
            @Part("pswd") RequestBody password_,
            @Part("device_id") RequestBody deviceId_
    );



    @Multipart
    @POST("User")
    Call<JsonElement> postInformation1(@Part MultipartBody.Part body1,
                                      @Part MultipartBody.Part body2,
                                      @Part MultipartBody.Part body3,
                                      @Part MultipartBody.Part body4,
                                       @Part MultipartBody.Part body5,
                                       @Part MultipartBody.Part body6,
                                       @Part MultipartBody.Part body7,
                                      @Part("category") RequestBody categorytype_,
                                      @Part("complaint") RequestBody information_,
                                      @Part("user_id") RequestBody user_id_,
                                       @Part("latitude") RequestBody latitude_,
                                       @Part("longitude") RequestBody longitude_);



    @Multipart
    @POST("addUserDirectiveFile")
    Call<JsonElement> testBlueData(@Part MultipartBody.Part answer,
                                   @Header("Authorization") String token);
/*

    @Multipart
    @POST("addDotEvidence")
    Call<MultipartAddEvidence> addEvidence(@Header("Authorization") String token, @Part("file\"; filename=\"pp.png\" ") RequestBody file, @Part("dotId") RequestBody dotId, @Part("description") RequestBody description);
*/

}

