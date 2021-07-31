package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.NetworkConnection;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Login_screen extends AppCompatActivity {
    Context context;
    Activity activity;
    private BaseRequest baseRequest;
    SessionParam sessionParam;
    private int progressStatus = 0;
    Button btn_login;
    EditText et_username,et_password;
    TextView forgotpass;
    String password, username,device_id = "545454",devicetype="android",devicetoken="";
    private boolean isShowPassword = false;
    ImageView showpassbtn;
    ImageView iv_cancel_dialog;
    Button btn_forgot;
     EditText e_mobile,e_pass;
     String fmobile,fpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        et_username=findViewById(R.id.username);
        et_password=findViewById(R.id.password);
        btn_login = findViewById(R.id.button);
        showpassbtn=findViewById(R.id.show_pass_btn);
        forgotpass=findViewById(R.id.tvForgotPass);
        sessionParam = new SessionParam(getApplicationContext());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        devicetoken = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                      Log.d("TIKEN", devicetoken);
                       // Toast.makeText(Login_screen.this, devicetoken, Toast.LENGTH_SHORT).show();

                    }
                });


        ArrayList<String> arrPerm = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
        showpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPassword) {
                    et_password.setTransformationMethod(new PasswordTransformationMethod());
                    showpassbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_24));
                    isShowPassword = false;
                }else{
                    et_password.setTransformationMethod(null);
                    showpassbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                    isShowPassword = true;
                }
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotpassword();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//                device_id = TelephonyMgr.getDeviceId();
//                Log.d("Android","Android ID : "+device_id);

                if (et_username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (device_id.equals("")) {
                    return;
                } if (device_id.equals("")) {
                return;
            }
                if (devicetype.equals("")) {
                    return;
                }
                if (devicetoken.equals("")) {
                    return;
                }
                else {
                    password = et_password.getText().toString();
                    username = et_username.getText().toString();

                    try {
                        api_login();
                        Loder();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void api_login() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                sessionParam.loginSession(getApplicationContext());

                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    sessionParam = new SessionParam(getApplicationContext(), jsonObject1);
                    String userid = jsonObject1.getString("id");
                    sessionParam.userId(getApplicationContext(),userid);

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Dashbord.class);
                    startActivity(i);
                    et_password.setText("");
                    et_username.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Login_screen.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPILogin(1, "https://impetrosys.com/spiderapp/",username, password,  device_id,devicetype,devicetoken);

    }

    private void callApilogin(String device_id) throws JSONException {

        if (NetworkConnection.checkNetworkStatus(getApplicationContext()) == true) {
            api_login();
        } else {
            sucessDialog2(getResources().getString(R.string.Internet_connection), context);
        }

    }


    public void sucessDialog2(String message, Context context) {
        final Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.notification_dailog2);
        mDialog.setCanceledOnTouchOutside(true);

        Button btn_ok;
        TextView tv_retry;
        TextView tv_notification;
        btn_ok = mDialog.findViewById(R.id.btn_ok);
        tv_retry = mDialog.findViewById(R.id.tv_retry);
        tv_notification = mDialog.findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
                try {
                    callApilogin(device_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        mDialog.show();

    }

    public void forgotpassword()
    {
        Dialog mDialog = new Dialog(Login_screen.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.forgotpassword);
        mDialog.setCanceledOnTouchOutside(false);

        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        e_mobile= mDialog.findViewById(R.id.et_mobile);
        e_pass= mDialog.findViewById(R.id.et_password);
        btn_forgot= mDialog.findViewById(R.id.btn_forgot);
        iv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmobile = e_mobile.getText().toString();
                fpass=e_pass.getText().toString();
                try {
                    api_forgotpass();
                    Loder();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mDialog.show();

    }

    private void api_forgotpass() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Login_screen.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Login_screen.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPIforgotpass(1, "https://impetrosys.com/spiderapp/",fmobile, fpass);

    }


    public void Loder() {
        ProgressDialog pd = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        pd.setMessage("Please wait ...");
        pd.show();
        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus +=1;
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pd.setProgress(progressStatus);
                            if(progressStatus == 100){
                                pd.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();
//lowder end
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
