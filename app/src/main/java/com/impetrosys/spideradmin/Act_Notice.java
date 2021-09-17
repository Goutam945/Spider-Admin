package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_Noticelist;
import com.impetrosys.spideradmin.Adapter.Ad_pushNotification;
import com.impetrosys.spideradmin.Modelclass.Notice;
import com.impetrosys.spideradmin.Modelclass.Notification;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_Notice extends AppCompatActivity {

    Context context;
    Activity activity;
    SessionParam sessionParam;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Ad_Noticelist ad_noticelist;
    ArrayList<Notice> notices = new ArrayList<>();
    EditText et_notice,et_support;
    Button btn_save;
    String Notice,Support;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        try {
            Loder();
            ApiGetNotice();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void ApiGetNotice () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("msg_code")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("noticelist");
                        notices = baseRequest.getDataList(jsonArray, Notice.class);
                        for (int i = 0; i < notices.size(); i++) {
                            if (notices != null) {

                                ad_noticelist = new Ad_Noticelist(notices, getApplicationContext(), sessionParam, activity, new Ad_Noticelist.edit() {
                                    @Override
                                    public void edit(Notice edit) {
                                        addnotice(edit);

                                    }

                                    @Override
                                    public void delete(String id) {
                                        try {
                                            api_Noticedelete(id);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                recycle.setAdapter(ad_noticelist);

                            } else {
                                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onNetworkFailure(int requestCode, String message) {

            }
        });
        String remainingUrl2 = "https://impetrosys.com/spiderapp/";
        baseRequest.callAPIgetNoticelist(1, remainingUrl2);

    }
    public void addnotice(Notice edit)
    {
        Dialog mDialog = new Dialog(Act_Notice.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.add_notice);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        final EditText et_name,et_url,et_description;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        et_notice= mDialog.findViewById(R.id.editnotice);
        et_support= mDialog.findViewById(R.id.editsupport);
        btn_save= mDialog.findViewById(R.id.btn_add);


        if (edit==null){
            btn_save.setText("Add");

        }else {
            et_notice.setText(edit.getNotice());
            et_support.setText(edit.getSupportno());
            btn_save.setText("Update");




        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notice = et_notice.getText().toString();
                Support = et_support.getText().toString();
                String btname=btn_save.getText().toString();
                if(btname.equals("Update")){
                    try {
                        api_NoticeUpdate(edit.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {


                    if (validate()) {
                        try {
                            api_Noticeadd();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        iv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });

        mDialog.show();

    }
    private void api_Noticeadd() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Notice.class);
                    startActivity(i);
                    et_notice.setText("");
                    et_support.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Notice.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPINoticeadd(1, "https://impetrosys.com/spiderapp/",Notice, Support);

    }

    private void api_NoticeUpdate(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Notice.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Notice.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPINoticeupdate(1, "https://impetrosys.com/spiderapp/",Notice, Support,id);

    }
    private void api_Noticedelete(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Notice.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Notice.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPINoticedelete(1, "https://impetrosys.com/spiderapp/",id);

    }

    private boolean validate() {
        boolean valid = true;

        if (Notice.equals("") || Notice.equals(null)) {
            et_notice.setError("at least 3 characters");
            valid = false;
        } else {
            et_notice.setError(null);
        }

        if (Support.equals("") || Support.equals(null)) {
            et_support.setError("Enter phone number");
            valid = false;
        } else {
            et_support.setError(null);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.web_search);
        MenuItem v = menu.findItem(R.id.action_add);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(Act_Notice.this, Act_Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
            case R.id.action_add:
                addnotice(null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }
}