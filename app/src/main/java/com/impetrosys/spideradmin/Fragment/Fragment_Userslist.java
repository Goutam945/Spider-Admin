package com.impetrosys.spideradmin.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.impetrosys.spideradmin.Act_Dashbord;
import com.impetrosys.spideradmin.Act_User_list;
import com.impetrosys.spideradmin.Act_Userlist_Referallist;
import com.impetrosys.spideradmin.Adapter.Ad_Paymentdepositslist;
import com.impetrosys.spideradmin.Adapter.Ad_userlist;
import com.impetrosys.spideradmin.Modelclass.Paymentdepositslist;
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

public class Fragment_Userslist extends Fragment {
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


        Loder();
        try {
            ApiGetUserlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
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

                                ad_userlist = new Ad_userlist(userlist, paymentDetails, getActivity(), sessionParam, activity, new Ad_userlist.CreateRefral() {
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
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override

            public void onNetworkFailure(int requestCode, String message) {

            }
        });
        String remainingUrl2 = "https://impetrosys.com/spiderapp/";
        baseRequest.callAPIgetUserlist(1, remainingUrl2);

    }

    public void DetailsReferal(Userlist detail)
    {
        Dialog mDialog = new Dialog(getActivity());
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
       // btn_addreawrd= mDialog.findViewById(R.id.btn_addreward);


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



        iv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });

        mDialog.show();

    }
   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // First clear current all the menu items
        menu.clear();

        // Add the new menu items
        inflater.inflate(R.menu.search, menu);
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

        super.onCreateOptionsMenu(menu, inflater);
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