package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Deposit_Id_details extends AppCompatActivity {
    String Username,Userid,Coins,Websitename,Websiteurl,Requestusername,Createddate,Id,Paywallet,Paymentmethod,Paymentscreenshot;
    FrameLayout container;
    TextView username,userid,coins,websitename,websiteurl,requestusername,paymentmethod,createddate;
    ImageView imageView;
    ProgressBar progress;
    Button approve,reject;
    Context context;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_deposit_id_details);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_deposit_id_details, null);
        container.addView(rowView, container.getChildCount());

        username = rowView.findViewById(R.id.d_username);
        userid = rowView.findViewById(R.id.d_id);
        coins = rowView.findViewById(R.id.d_coin);
        websitename = rowView.findViewById(R.id.d_websitename);
        websiteurl = rowView.findViewById(R.id.d_websiteurl);
        requestusername = rowView.findViewById(R.id.d_requestusername);
        paymentmethod = rowView.findViewById(R.id.d_paymentmethod);
        createddate = rowView.findViewById(R.id.d_date);
        imageView = rowView.findViewById(R.id.icon);
        progress = rowView.findViewById(R.id.progressBar);

        approve = rowView.findViewById(R.id.btn_accept);
        reject = rowView.findViewById(R.id.btn_reject);

        Intent intent=getIntent();
        Username=intent.getStringExtra("username");
        Userid=intent.getStringExtra("userid");
        Coins=intent.getStringExtra("coins");
        Websitename=intent.getStringExtra("websitename");
        Websiteurl=intent.getStringExtra("websiteurl");
        Requestusername=intent.getStringExtra("requestusername");
        Createddate=intent.getStringExtra("created_date");
        Id=intent.getStringExtra("id");
        Paywallet=intent.getStringExtra("pay_wallet");
        Paymentmethod=intent.getStringExtra("paymentmethod");
        Paymentscreenshot=intent.getStringExtra("paymentscreenshot");

        username.setText("Username : "+Username);
        userid.setText("Userid : "+Userid);
        coins.setText("Coins : "+Coins);
        websitename.setText("Websitename : "+Websitename);
        websiteurl.setText("Websiteurl : "+Websiteurl);
        requestusername.setText("Requestusername : "+Requestusername);
        paymentmethod.setText("Paymentmethod : "+Paymentmethod);
        createddate.setText("Date : "+Createddate);



        if (Paywallet.equalsIgnoreCase("0")){
            imageView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(Paymentscreenshot)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progress.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }else {
            imageView.setVisibility(View.GONE);
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiapprovrequest_depositid();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiRejectrequest_depositid();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private void apiapprovrequest_depositid() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Intent i = new Intent(getApplicationContext(), Act_paymentdeposit.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Approve", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Deposit_Id_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIapproveDepositsID_request(1, "https://impetrosys.com/spiderapp/",Id);

    }
    private void apiRejectrequest_depositid() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Intent i = new Intent(getApplicationContext(), Act_paymentdeposit.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Reject", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Deposit_Id_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIDeposits_reject(1, "https://impetrosys.com/spiderapp/",Id);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Deposit_Id_details.this, Act_paymentdeposit.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Deposit_Id_details.this,Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}