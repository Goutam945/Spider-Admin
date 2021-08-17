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
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_userlist;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_User_list extends AppCompatActivity {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    TextView code,reward,username,uid,referdate,nocodemessage;
    LinearLayout layout;
    String Code,Reward,Username,Uid,Referdate;
    Button btn_create,btn_addreawrd;
    private int progressStatus = 0;
    androidx.appcompat.widget.SearchView inputSearch;
    Ad_userlist ad_userlist;
    ArrayList<Userlist> userlist = new ArrayList<>();
    ArrayList<Userlist.PaymentDetail> paymentDetails = new ArrayList<>();
    ArrayList<Userlist>userlist2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Users List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));


//        ad_userlist = new Ad_userlist(getApplicationContext());
//        recycle.setAdapter(ad_userlist);
//        recycle.setHasFixedSize(true);
        Loder();
        try {
            ApiGetUserlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void ApiGetUserlist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("userlist");
                        userlist = baseRequest.getDataList(jsonArray, Userlist.class);
                        for (int i = 0; i < userlist.size(); i++) {
                            if (userlist != null) {


                                Userlist model = new Userlist();
                                model.setName(userlist.get(i).getName());
                                model.setContact(userlist.get(i).getContact());
                                model.setId(userlist.get(i).getId());

                                userlist2.add(model);

                                ad_userlist = new Ad_userlist(userlist, paymentDetails, getApplicationContext(), sessionParam, activity, new Ad_userlist.CreateRefral() {
                                    @Override
                                    public void detail(Userlist detail) {
                                        DetailsReferal(detail);
                                    }
                                }
                                );
                                recycle.setAdapter(ad_userlist);

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
        baseRequest.callAPIgetUserlist(1, remainingUrl2);

    }


    public void reward_create(String id)
    {
        Dialog mDialog = new Dialog(Act_User_list.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.referalcode_create);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
       // code= mDialog.findViewById(R.id.code);
        reward= mDialog.findViewById(R.id.reward);
        btn_create=mDialog.findViewById(R.id.btn_refral);

//

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Code = code.getText().toString();
                Reward = reward.getText().toString();
                if(validate()){
                    try {
                        api_Refralcode(id);
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


    public void DetailsReferal(Userlist detail)
    {
        Dialog mDialog = new Dialog(Act_User_list.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.refrals_details_dailog);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        code= mDialog.findViewById(R.id.tv_code);
        reward= mDialog.findViewById(R.id.tv_reward);
        username= mDialog.findViewById(R.id.tv_username);
        uid= mDialog.findViewById(R.id.tv_uid);
        referdate= mDialog.findViewById(R.id.tv_referdate);
        layout= mDialog.findViewById(R.id.laout);
        nocodemessage= mDialog.findViewById(R.id.nocode);
      //  btn_addreawrd= mDialog.findViewById(R.id.btn_addreward);


        for (int k = 0; k < detail.getReferaldetail().size(); k++) {
            String type=detail.getIsrefer().toString();
            if (type.equalsIgnoreCase("1")) {
                code.setText("Code : "+detail.getReferaldetail().get(k).getCode());
                reward.setText("Reward : "+detail.getReferaldetail().get(k).getReward());
                username.setText("Username : "+detail.getReferaldetail().get(k).getUsername());
                uid.setText("UserID : "+detail.getReferaldetail().get(k).getUid());
                referdate.setText("Date : "+detail.getReferaldetail().get(k).getReferdate());
                layout.setVisibility(View.VISIBLE);
                nocodemessage.setVisibility(View.GONE);
            }else{
                layout.setVisibility(View.GONE);
            }
        }
        /*btn_addreawrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int k = 0; k < detail.getReferaldetail().size(); k++) {
                reward_create(detail.getReferaldetail().get(k).getUid());
                }
            }
        });*/


        iv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });

        mDialog.show();

    }



    private void api_Refralcode(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_User_list.class);
                    startActivity(i);
                    reward.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_User_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        baseRequest.callAPICreate_refralcode(1, "https://impetrosys.com/spiderapp/", Reward,id);

    }

    private boolean validate() {
        boolean valid = true;

       /* if (Code.equals("") || Code.equals(null)) {
            code.setError("enter a valid Code");
            valid = false;
        } else {
            code.setError(null);
        }*/

        if (Reward.equals("") || Reward.equals(null)) {
            reward.setError("enter a valid Reward");
            valid = false;
        } else {
            reward.setError(null);
        }
        return valid;
    }
    @Override
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

                ad_userlist.getFilter().filter(s);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(User_list.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Act_User_list.this, Act_Dashbord.class);
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