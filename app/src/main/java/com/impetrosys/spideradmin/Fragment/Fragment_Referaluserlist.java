package com.impetrosys.spideradmin.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_Clientids_requestchangepass;
import com.impetrosys.spideradmin.Act_Userlist_Referallist;
import com.impetrosys.spideradmin.Adapter.Ad_Paymentdepositslist;
import com.impetrosys.spideradmin.Adapter.Ad_Referalcodelist;
import com.impetrosys.spideradmin.Modelclass.Paymentdepositslist;
import com.impetrosys.spideradmin.Modelclass.Referalcode;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Referaluserlist extends Fragment {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Ad_Referalcodelist ad_referalcodelist;
    ArrayList<Referalcode> referalcodeslist = new ArrayList<>();
    ArrayList<Referalcode>referalcodeslist2 = new ArrayList<>();
    EditText updatecode,updatereward;
    String Updatecode,Updatereward;
    Button btn_update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //View view =inflater.inflate(R.layout.act_home_basic, container, false);

        container = view.findViewById(R.id.frament);
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater1.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());
        sessionParam = new SessionParam(getActivity());
        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            Loder();
            ApiGetReferalcodelist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }
    private void ApiGetReferalcodelist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("referalcode");
                        referalcodeslist = baseRequest.getDataList(jsonArray, Referalcode.class);
                        for (int i = 0; i < referalcodeslist.size(); i++) {
                            if (referalcodeslist != null) {
                                ad_referalcodelist = new Ad_Referalcodelist(referalcodeslist, getActivity(), sessionParam, activity, new Ad_Referalcodelist.update() {
                                    @Override
                                    public void edit(Referalcode edit) {
                                        UpdateReferal(edit);
                                    }
                                });
                                recycle.setAdapter(ad_referalcodelist);

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
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onNetworkFailure(int requestCode, String message) {

            }
        });
        String remainingUrl2 = "https://impetrosys.com/spiderapp/";
        baseRequest.callAPIgetRefralcodelist(1, remainingUrl2);

    }

    public void UpdateReferal(Referalcode edit)
    {
        Dialog mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.update_referalcode);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        updatecode= mDialog.findViewById(R.id.code);
        updatereward= mDialog.findViewById(R.id.reward);
        btn_update= mDialog.findViewById(R.id.btn_send);

        updatecode.setText(edit.getCode());
        updatereward.setText(edit.getReward());
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updatecode = updatecode.getText().toString();
                Updatereward = updatereward.getText().toString();
                try {
                    Loder();
                    APi_UpdateRefralcocde(edit.getId(),edit.getUid());
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void APi_UpdateRefralcocde(String rid,String uid) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_referalcodelist.notifyDataSetChanged();
                    Intent i = new Intent(getActivity(), Act_Userlist_Referallist.class);
                    startActivity(i);
                    Toast.makeText(getActivity(), "Sucessfully ", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIUpdate_Referalcode(1, "https://impetrosys.com/spiderapp/",rid,uid,Updatecode,Updatereward);

    }


    public void Loder() {
        ProgressDialog pd = new ProgressDialog(getActivity() , R.style.MyAlertDialogStyle);
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