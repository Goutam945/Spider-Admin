package com.impetrosys.spideradmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_Dashbordcoutlist;
import com.impetrosys.spideradmin.Modelclass.Dashbordcountlist;
import com.impetrosys.spideradmin.UtilClasses.NetworkConnection;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_Dashbord extends AppCompatActivity {
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    Activity activity;
    CardView card1,card2,card3,card4,card5,card6,card7,card8,card9,card10,card11;
    TextView userid,username;
    ImageView logout,notification;
    Context context;
    String device_id="5454844";
    RecyclerView recy;
    private int progressStatus = 0;
    Ad_Dashbordcoutlist ad_dashbordcountlist;
    ArrayList<Dashbordcountlist> dashbordcountlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_dashbord);
        setContentView(R.layout.new_dashbord);
        context = this;
        sessionParam = new SessionParam(getApplicationContext());
        card1=(CardView) findViewById(R.id.card1);
        card2=(CardView) findViewById(R.id.card2);
        card3=(CardView) findViewById(R.id.card3);
        card4=(CardView) findViewById(R.id.card4);
        card5=(CardView) findViewById(R.id.card5);
        card6=(CardView) findViewById(R.id.card6);
        card7=(CardView) findViewById(R.id.card7);
        card8=(CardView) findViewById(R.id.card8);
        card9=(CardView) findViewById(R.id.card9);
        card10=(CardView) findViewById(R.id.card10);
        userid=(TextView)findViewById(R.id.user_id);
        username=(TextView)findViewById(R.id.user_name);
        logout=(ImageView)findViewById(R.id.logoutt);
        notification=(ImageView)findViewById(R.id.notification);

        userid.setText(sessionParam.role);
        username.setText(sessionParam.name);
//        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//        device_id = TelephonyMgr.getDeviceId();
//        Log.d("Android","Android ID : "+device_id);

        card1.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
//                                         Intent i=new Intent(Dashbord.this, User_list.class);
//                                         startActivity(i);
//                                         finish();
                                         Intent i=new Intent(Act_Dashbord.this, Act_Userlist_Referallist.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card2.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Website_list.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card3.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                        /* Intent i=new Intent(Act_Dashbord.this, Act_Subadmin_list.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();*/

                                         if(sessionParam.role.equalsIgnoreCase("Admin")){
                                             Intent i=new Intent(Act_Dashbord.this, Act_Subadmin_list.class);
                                             startActivity(i);
                                             overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                             startActivity(i);
                                             finish();
                                         }else {
                                             Toast.makeText(Act_Dashbord.this,"Only Admin Use",Toast.LENGTH_SHORT).show();
                                         }

                                     }
                                 }

        );
        card4.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         //Intent i=new Intent(Dashbord.this, Paymentdeposits_list.class);
                                         Intent i=new Intent(Act_Dashbord.this, Act_paymentdeposit.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card5.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Withdrawals_request.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card6.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Financialdetail.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card7.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Clientids_requestchangepass.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card8.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Banner_create.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card9.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_CloseId_list.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );
        card10.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent i=new Intent(Act_Dashbord.this, Act_Raiseconcern.class);
                                         startActivity(i);
                                         overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                         startActivity(i);
                                         finish();
                                     }
                                 }

        );

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogLogout();

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Act_Dashbord.this, Act_Puch_Notification.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivity(i);
                finish();
            }
        });

        recy = findViewById(R.id.rv_Dlist);
        recy.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));

//        ad_dashbordcountlist = new Ad_Dashbordcoutlist(getApplicationContext());
//        recy.setAdapter(ad_dashbordcountlist);
//        recy.setHasFixedSize(true);

        try {
            Loder();
            ApiGetCountlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void ApiGetCountlist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                       // JSONArray jsonArray = jsonObject.optJSONArray("dashboard");
                        JSONObject jsonObject1 = jsonObject.optJSONObject("dashboard");
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject1);

                        dashbordcountlist = baseRequest.getDataList(jsonArray, Dashbordcountlist.class);
                        for (int i = 0; i < dashbordcountlist.size(); i++) {
                            if (dashbordcountlist != null) {

                                ad_dashbordcountlist = new Ad_Dashbordcoutlist(dashbordcountlist,getApplicationContext(), sessionParam, activity);
                                recy.setAdapter(ad_dashbordcountlist);

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
        baseRequest.callAPIgetCountlist(1, remainingUrl2);

    }


    private void api_logout() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Login_screen.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Dashbord.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIlogout(1, "https://impetrosys.com/spiderapp/",sessionParam.userId, device_id);

    }

    private void callApilogout() throws JSONException {

        if (NetworkConnection.checkNetworkStatus(getApplicationContext()) == true) {
            api_logout();
        } else {
            sucessDialog2(getResources().getString(R.string.Internet_connection), context);
        }

    }

    public void alertDialogLogout() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionParam.clearPreferences(context);
                        try {
                            api_logout();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Intent intent = new Intent(Dashbord.this, Login_screen.class);
//                        startActivity(intent);
//                        finish();
                    }
                });


        builder.setNegativeButton("No", null);
        builder.show();

       /* AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage("Are you sure you want to Logout?");
        builder.setTitle("Logout");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sessionParam.clearPreferences(context);
                        try {
                            api_logout();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

*/
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
                    callApilogout();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mDialog.show();

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