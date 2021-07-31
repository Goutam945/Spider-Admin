package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_user_requestlist;
import com.impetrosys.spideradmin.Adapter.Ad_userlist;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.Modelclass.UserRequestlist;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User_requestlist extends AppCompatActivity {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Ad_user_requestlist ad_userrequest;
    ArrayList<UserRequestlist> userrequest = new ArrayList<>();
    ArrayList<UserRequestlist>userrequest1 = new ArrayList<>();
    EditText Uname,Upass,Udesc;
    String Username,Userpass,UDescription;
    Button btn_save,btn_rsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Client id's list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Loder();
        try {
            ApiGetUser_requestlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void ApiGetUser_requestlist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("requestidlist");
                        userrequest = baseRequest.getDataList(jsonArray, UserRequestlist.class);
                        userrequest1.clear();
                        for (int i = 0; i < userrequest.size(); i++) {
                            if (userrequest != null) {
                               if( userrequest.get(i).getStatus().equalsIgnoreCase("0")){
                                   userrequest1.add(userrequest.get(i));

                               }


                                ad_userrequest = new Ad_user_requestlist(userrequest1, getApplicationContext(), sessionParam, activity, new Ad_user_requestlist.aprove() {
                                    @Override
                                    public void getid(String id) {
                                        Approvrequest(id);
                                    }

                                    @Override
                                    public void rejetid(String id) {
                                        Rejectrequest(id);
                                    }
                                });
                                recycle.setAdapter(ad_userrequest);

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
        baseRequest.callAPIgetUser_requestlist(1, remainingUrl2);

    }

//    private void approv_request(String id) throws JSONException {
//        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
//        apiapprovrequest(id);
//    }

    public void Approvrequest(String id)
    {
        Dialog mDialog = new Dialog(User_requestlist.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.userrequsetaprrove_dailog);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        final EditText et_name,et_url,et_description;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        Uname= mDialog.findViewById(R.id.edituname);
        Upass= mDialog.findViewById(R.id.upassword);
        btn_save= mDialog.findViewById(R.id.btn_send);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = Uname.getText().toString();
                Userpass = Upass.getText().toString();

                if (validate()) {
                    try {
                        apiapprovrequest(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    public void Rejectrequest(String id)
    {
        Dialog mDialog = new Dialog(User_requestlist.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.userrequesrreject_dailog);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        Udesc= mDialog.findViewById(R.id.editudescription);
        btn_rsave= mDialog.findViewById(R.id.btn_rsend);

        btn_rsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Udesc.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    UDescription = Udesc.getText().toString();
                    try {
                        apiRejectrequest(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
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



    private void apiapprovrequest(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_userrequest.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), User_requestlist.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Approve", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(User_requestlist.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIapproveUser_request(1, "https://impetrosys.com/spiderapp/",id,Username,Userpass);

    }
    private void apiRejectrequest(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_userrequest.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), User_requestlist.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Reject", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(User_requestlist.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIReject_request(1, "https://impetrosys.com/spiderapp/",id,UDescription);

    }
    private boolean validate() {
        boolean valid = true;

        if (Username.equals("") || Username.equals(null)) {
            Uname.setError("at least 3 characters");
            valid = false;
        } else {
            Uname.setError(null);
        }

        if (Userpass.equals("") || Userpass.equals(null)) {
            Upass.setError("at least 3 characters");
            valid = false;
        } else {
            Upass.setError(null);
        }
        return valid;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(User_requestlist.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(User_requestlist.this,Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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