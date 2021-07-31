package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Fragment.FirstFragment;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Deposits_details extends AppCompatActivity {
String username,userid,paymentmethod,coins,paymentscreenshot,createddate,ID;
    FrameLayout container;
    TextView name,uid,pmethod,coin,dete;
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
        //setContentView(R.layout.activity_deposits_details);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_deposits_details, null);
        container.addView(rowView, container.getChildCount());

        name = rowView.findViewById(R.id.d_username);
        uid = rowView.findViewById(R.id.d_id);
        pmethod = rowView.findViewById(R.id.d_paymentmethod);
        coin = rowView.findViewById(R.id.d_coin);
        dete = rowView.findViewById(R.id.d_date);
        imageView = rowView.findViewById(R.id.icon);
        progress = rowView.findViewById(R.id.progressBar);
        approve = rowView.findViewById(R.id.btn_accept);
        reject = rowView.findViewById(R.id.btn_reject);


        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        userid=intent.getStringExtra("userid");
        paymentmethod=intent.getStringExtra("paymentmethod");
        coins=intent.getStringExtra("coins");
        paymentscreenshot=intent.getStringExtra("paymentpic");
        createddate=intent.getStringExtra("created_date");
        ID=intent.getStringExtra("id");

        name.setText("User : "+username);
        uid.setText("User Id : "+userid);
        pmethod.setText("Paymentmethod : "+paymentmethod);
        coin.setText("Coins : "+coins);
        dete.setText("Date : "+createddate);
//        Picasso.get()
//                .load(paymentscreenshot)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);

        Picasso.get()
                .load(paymentscreenshot)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Deposits_details.this, Full_image.class);
                i.putExtra("paymentpic",paymentscreenshot);
                startActivity(i);
               finish();

            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiapprovrequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiRejectrequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void apiapprovrequest() throws JSONException {
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
                Toast.makeText(Deposits_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIapproveDeposits_request(1, "https://impetrosys.com/spiderapp/",ID);

    }
    private void apiRejectrequest() throws JSONException {
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
                Toast.makeText(Deposits_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIDeposits_reject(1, "https://impetrosys.com/spiderapp/",ID);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Deposits_details.this, Act_paymentdeposit.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Deposits_details.this,Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
   /* @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }*/
}