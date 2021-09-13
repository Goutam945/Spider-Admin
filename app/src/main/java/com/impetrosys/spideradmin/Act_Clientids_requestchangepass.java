package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_ClientidRequest_changepasslist;
import com.impetrosys.spideradmin.Modelclass.ClientidRequestchangepasslsit;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_Clientids_requestchangepass extends AppCompatActivity {

    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    androidx.appcompat.widget.SearchView inputSearch;
    Ad_ClientidRequest_changepasslist ad_clientidRequestlist;
    ArrayList<ClientidRequestchangepasslsit> clientidrequestlsits = new ArrayList<>();
    ArrayList<ClientidRequestchangepasslsit>clientidrequestlsits2 = new ArrayList<>();
    EditText passwordchange,discription;
    String PasswordChange,Description;
    Button btn_change,btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        sessionParam = new SessionParam(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Change Id Password");
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
            ApiGetCLientidpasswordlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void ApiGetCLientidpasswordlist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("changepasswordrequestlist");
                        clientidrequestlsits = baseRequest.getDataList(jsonArray, ClientidRequestchangepasslsit.class);
                        clientidrequestlsits2.clear();
                        for (int i = 0; i < clientidrequestlsits.size(); i++) {
                            if (clientidrequestlsits != null) {

                                if( clientidrequestlsits.get(i).getStatus().equalsIgnoreCase("0")){
                                    clientidrequestlsits2.add(clientidrequestlsits.get(i));

                                }

                                ad_clientidRequestlist = new Ad_ClientidRequest_changepasslist(clientidrequestlsits2, getApplicationContext(), sessionParam, activity, new Ad_ClientidRequest_changepasslist.Changepass() {
                                    @Override
                                    public void pass(String id,String uid) {
                                        changepass(id,uid);

                                    }

                                    @Override
                                    public void nopass(String id,String uid) {
                                        NOchangepass(id,uid);

                                    }
                                });
                                recycle.setAdapter(ad_clientidRequestlist);

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
        baseRequest.callAPIgetClient_changepasslist(1, remainingUrl2,sessionParam.userId);

    }

    public void changepass(String id,String uid)
    {
        Dialog mDialog = new Dialog(Act_Clientids_requestchangepass.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.changepassword_requestclient);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        passwordchange= mDialog.findViewById(R.id.passs);
        btn_change=mDialog.findViewById(R.id.btn_changepass);

//

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChange = passwordchange.getText().toString();

                if (passwordchange.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    try {
                        apichange_passwordclientid(id,uid);
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

    public void NOchangepass(String id,String uid)
    {
        Dialog mDialog = new Dialog(Act_Clientids_requestchangepass.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.nochangepass);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        btn_reject= mDialog.findViewById(R.id.btn_reject);
        discription= mDialog.findViewById(R.id.editudescription);
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Description = discription.getText().toString();

                if (discription.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter discription", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    try {
                        apichange_passwordclientidReject(id,uid);
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
    private void apichange_passwordclientid(String id ,String uid) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_clientidRequestlist.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), Act_Clientids_requestchangepass.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Change", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Clientids_requestchangepass.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIClientid_Changepassword(1, "https://impetrosys.com/spiderapp/",id,PasswordChange,uid);

    }

    private void apichange_passwordclientidReject(String id,String uid) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_clientidRequestlist.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), Act_Clientids_requestchangepass.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Reject", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Clientids_requestchangepass.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIClientid_NOchangepassword(1, "https://impetrosys.com/spiderapp/",id,Description,uid);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.mi_search);
        inputSearch = (SearchView)myActionMenuItem.getActionView();
        changeSearchViewTextColor(inputSearch);

        inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ad_clientidRequestlist.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));


                }}}}
*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Clientids_requestchangepass.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent i=new Intent(Act_Clientids_requestchangepass.this, Act_Dashbord.class);
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
}