package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.util.Log;
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

import com.impetrosys.spideradmin.Adapter.Ad_Detailsaccount;
import com.impetrosys.spideradmin.Adapter.Ad_financialdetail;
import com.impetrosys.spideradmin.Modelclass.AccountDetails;
import com.impetrosys.spideradmin.Modelclass.Acountdeatil1;
import com.impetrosys.spideradmin.Modelclass.Financialdetails;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_Financialdetail extends AppCompatActivity {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle,recyclerdetail;
    FrameLayout container;
    ConstraintLayout constraintLayout;
    private BaseRequest baseRequest;
    private int progressStatus = 0;

    Ad_financialdetail ad_financialdetail;
    ArrayList<Financialdetails> financialdetails = new ArrayList<>();
    ArrayList<Financialdetails>financialdetails2 = new ArrayList<>();

    Ad_Detailsaccount ad_detailsaccount;
    ArrayList<AccountDetails> accountdetails = new ArrayList<>();
    ArrayList<Acountdeatil1> accountdetails1 = new ArrayList<>();

    EditText et_uname,et_number;
    EditText et_accountno,et_bankname,et_ifc,et_holdername,et_actype,et_bankaddress;
    String Upiname,Upinumber;
    String Baccount,Bbankname,Bifc,Bholdername,Bactype,Bbankaddress;
    Button save_upi,save_bankdetails;

    String updatenumber,updatename,Updateid,UpdatePaymentId;
    String updatebankename,updatebankeaccount,updateifc,updatebrach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Financial Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        sessionParam = new SessionParam(getApplicationContext());
        Log.d("USERRRRRRRR",sessionParam.userId);

        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        Loder();
        try {
            ApiGetaccountDetaillist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiGetfinanciallist();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Do something after 100ms
            }
        }, 500);


//        ad_financialdetail = new Ad_financialdetail(getApplicationContext());
//        recycle.setAdapter(ad_financialdetail);
//        recycle.setHasFixedSize(true);
    }
    private void ApiGetfinanciallist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("pymentmethodlist");
                        financialdetails = baseRequest.getDataList(jsonArray, Financialdetails.class);
                        for (int i = 0; i < financialdetails.size(); i++) {
                            if (financialdetails != null) {

                                ad_financialdetail = new Ad_financialdetail(financialdetails,accountdetails, getApplicationContext(), sessionParam, activity, new Ad_financialdetail.add() {
                                    @Override
                                    public void getid(String id) {
                                        addupi(id);
                                    }

                                    @Override
                                    public void edit(JSONObject edit) {
                                        UpdateUpi(edit);
                                    }

                                    @Override
                                    public void delete(String id) {
                                        try {
                                            apideleteAccount(id);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                });
                                recycle.setAdapter(ad_financialdetail);

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
        baseRequest.callAPIgetFinancial(1, remainingUrl2,sessionParam.userId);

    }
    private void ApiGetaccountDetaillist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("accountlist");
                        accountdetails = baseRequest.getDataList(jsonArray,AccountDetails .class);
                      /*  for (int i = 0; i < accountdetails.size(); i++) {
                            if (accountdetails != null) {



                                ad_detailsaccount = new Ad_Detailsaccount(accountdetails1,getApplicationContext(), sessionParam, activity);


                            } else {
                                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                            }
                        }*/

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
        baseRequest.callAPIAcoountdetaillist(1, remainingUrl2,sessionParam.userId);

    }


    private void addupi(String id){
        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        //addupiDetails();

        if("4".contains(id)){
            addbankDetails();
        }else {
            addupiDetails(id);
        }
    }

    private void UpdateUpi(JSONObject edit){
//        try {
//            updatename= edit.getString("Upiname");
//            updatenumber=edit.getString("Upinumber");
//            Updateid=edit.getString("id");
//            UpdatePaymentId=edit.getString("Paymentid");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            apiUpdateUPI();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        try {
            if("4".contains(edit.getString("Paymentid"))){
                updatebankename=edit.getString("bankname");
                updatebankeaccount=edit.getString("bankaccount");
                updateifc=edit.getString("bankifc");
                updatebrach=edit.getString("bankbranch");
                Updateid=edit.getString("id");
                apiUpdate_Bankdetails();



            }else {
                updatename= edit.getString("Upiname");
                updatenumber=edit.getString("Upinumber");
                Updateid=edit.getString("id");
                UpdatePaymentId=edit.getString("Paymentid");
                apiUpdateUPI();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void addupiDetails(String id)
    {
        Dialog mDialog = new Dialog(Act_Financialdetail.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.add_upi);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        et_uname= mDialog.findViewById(R.id.uname);
        et_number= mDialog.findViewById(R.id.unumber);
        save_upi= mDialog.findViewById(R.id.btn_saveupi);
//

        save_upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upiname = et_uname.getText().toString();
                Upinumber = et_number.getText().toString();
                if(validate()){
                    try {
                        apiAddUPI(id);
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
    public void addbankDetails()
    {
        Dialog mDialog = new Dialog(Act_Financialdetail.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.add_bankdetails);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        et_accountno= mDialog.findViewById(R.id.acountno);
        et_bankname= mDialog.findViewById(R.id.bank);
        et_ifc= mDialog.findViewById(R.id.ifc);
       et_holdername= mDialog.findViewById(R.id.holdername);
        et_actype= mDialog.findViewById(R.id.accounttype);
        et_bankaddress= mDialog.findViewById(R.id.branchaddress);
        save_bankdetails= mDialog.findViewById(R.id.btn_savebankdetal);

        save_bankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Baccount = et_accountno.getText().toString();
                Bbankname = et_bankname.getText().toString();
                Bifc = et_ifc.getText().toString();
                Bbankaddress = et_bankaddress.getText().toString();
                Bholdername=et_holdername.getText().toString();
                Bactype=et_actype.getText().toString();
               if(validate1()){
                   try {
                       apiAdd_Bankdetails();
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

    private void apiAddUPI(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Add", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Financialdetail.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Financialdetail.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPI_UPI(1, "https://impetrosys.com/spiderapp/",Upiname,Upinumber,id,sessionParam.userId);
    }


    private void apiUpdateUPI() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Update", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Financialdetail.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Financialdetail.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPI_UpdateUPI(1, "https://impetrosys.com/spiderapp/",updatename,updatenumber,Updateid,UpdatePaymentId,sessionParam.userId);

    }
    private void apiUpdate_Bankdetails() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Update BankDetails", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Financialdetail.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Financialdetail.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPI_BankDetailUpdate(1, "https://impetrosys.com/spiderapp/",updatebankeaccount,updatebankename,updateifc,updatebrach,sessionParam.userId,Updateid);

    }
    private void apiAdd_Bankdetails() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Add BankDetails", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Financialdetail.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Financialdetail.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPI_BankDetailadd(1, "https://impetrosys.com/spiderapp/",Baccount,Bbankname,Bifc,Bbankaddress,sessionParam.userId,Bholdername,Bactype);

    }
    private void apideleteAccount(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Intent i = new Intent(getApplicationContext(), Act_Dashbord.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Delete", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Financialdetail.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIdeleteAccountdetail(1, "https://impetrosys.com/spiderapp/",id);

    }



    private boolean validate() {
        boolean valid = true;

        if (Upiname.equals("") || Upiname.equals(null)) {
            et_uname.setError("at least 3 characters");
            valid = false;
        } else {
            et_uname.setError(null);
        }

        if (Upinumber.equals("")|| Upinumber.equals(null)) {
            et_number.setError("enter a valid mobile");
            valid = false;
        } else {
            et_number.setError(null);
        }

        return valid;
    }
    private boolean validate1() {
        boolean valid = true;
        if (Baccount.equals("")|| Baccount.equals(null)) {
            et_accountno.setError("enter a valid accountnumber");
            valid = false;
        } else {
            et_accountno.setError(null);
        }
        if (Bbankname.equals("")|| Bbankname.equals(null)) {
            et_bankname.setError("enter a valid bankname");
            valid = false;
        } else {
            et_bankname.setError(null);
        }
        if (Bifc.equals("")|| Bifc.equals(null)) {
            et_ifc.setError("enter a valid IFC Code");
            valid = false;
        } else {
            et_ifc.setError(null);
        }
        if (Bbankaddress.equals("")|| Bbankaddress.equals(null)) {
            et_bankaddress.setError("enter a valid bankaddress");
            valid = false;
        } else {
            et_bankaddress.setError(null);
        }
        if (Bholdername.equals("")|| Bholdername.equals(null)) {
            et_holdername.setError("enter a valid account holdername");
            valid = false;
        } else {
            et_holdername.setError(null);
        }
        if (Bactype.equals("")|| Bactype.equals(null)) {
            et_actype.setError("enter a valid account type");
            valid = false;
        } else {
            et_actype.setError(null);
        }
        return valid;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Financialdetail.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Act_Financialdetail.this, Act_Dashbord.class);
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
                        Thread.sleep(30);
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