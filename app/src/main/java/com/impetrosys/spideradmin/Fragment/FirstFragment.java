package com.impetrosys.spideradmin.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_Paymentdepositslist;
import com.impetrosys.spideradmin.Modelclass.Paymentdepositslist;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Ad_Paymentdepositslist ad_paymentdepositslist;
    ArrayList<Paymentdepositslist> paymentdepositslist = new ArrayList<>();
    ArrayList<Paymentdepositslist>paymentdepositslist2 = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //View view =inflater.inflate(R.layout.act_home_basic, container, false);

        container = view.findViewById(R.id.frament);
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater1.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            Loder();
            ApiGetDepositslist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }
    private void ApiGetDepositslist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("depositlist");
                        paymentdepositslist = baseRequest.getDataList(jsonArray, Paymentdepositslist.class);
                        for (int i = 0; i < paymentdepositslist.size(); i++) {
                            if (paymentdepositslist != null) {
                                //condition manage status
                                if( paymentdepositslist.get(i).getStatus().equalsIgnoreCase("0")){
                                    paymentdepositslist2.add(paymentdepositslist.get(i));}//end contiom


                                ad_paymentdepositslist = new Ad_Paymentdepositslist(paymentdepositslist2,getActivity(), sessionParam, activity);
                                recycle.setAdapter(ad_paymentdepositslist);

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
        baseRequest.callAPIgetDepositslist(1, remainingUrl2);

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