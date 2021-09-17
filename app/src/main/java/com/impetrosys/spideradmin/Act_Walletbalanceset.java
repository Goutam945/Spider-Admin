package com.impetrosys.spideradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONException;
import org.json.JSONObject;

public class Act_Walletbalanceset extends AppCompatActivity {
    Context context;
    Activity activity;
    private BaseRequest baseRequest;
    SessionParam sessionParam;
    private int progressStatus = 0;
    EditText et_mindeposits,et_maxdeposits,et_minwithral,et_maxwithdral;
    String mindeposits,maxdeposits,minwithral,maxwithdral;
    Button Save;
    ImageView btnback;
    String name,url;
    private String Document_img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_walletbalanceset);
        et_mindeposits=findViewById(R.id.mindeposits);
        et_maxdeposits=findViewById(R.id.maxdeposits);
        et_minwithral=findViewById(R.id.minwithdraw);
        et_maxwithdral=findViewById(R.id.maxwithdraw);
        btnback=findViewById(R.id.back_btn);
        Save=findViewById(R.id.btn_add);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Act_Walletbalanceset.this, Act_Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxdeposits = et_maxdeposits.getText().toString();
                mindeposits = et_mindeposits.getText().toString();
                maxwithdral = et_maxwithdral.getText().toString();
                minwithral = et_minwithral.getText().toString();
                if(validate()){
                    try {
                        Loder();
                        api_Addwalltevalue();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });




    }
    private void api_Addwalltevalue() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Dashbord.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Walletbalanceset.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPIWalltevalue(1, "https://impetrosys.com/spiderapp/",maxdeposits,mindeposits,maxwithdral,minwithral);

    }
    private boolean validate() {
        boolean valid = true;

        if (maxdeposits.equals("") || maxdeposits.equals(null)) {
            et_maxdeposits.setError("Enter value");
            valid = false;
        } else {
            et_maxdeposits.setError(null);
        }

        if (mindeposits.equals("") || mindeposits.equals(null)) {
            et_mindeposits.setError("Enter value");
            valid = false;
        } else {
            et_mindeposits.setError(null);
        }

        if (maxwithdral.equals("") || maxwithdral.equals(null)) {
            et_maxwithdral.setError("Enter value");
            valid = false;
        } else {
            et_maxwithdral.setError(null);
        }

        if (minwithral.equals("") || minwithral.equals(null)) {
            et_minwithral.setError("Enter value");
            valid = false;
        } else {
            et_minwithral.setError(null);
        }


        return valid;
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
}