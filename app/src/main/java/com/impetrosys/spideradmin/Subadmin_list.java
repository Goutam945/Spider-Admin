package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.Adapter.Ad_UserAccesspermition;
import com.impetrosys.spideradmin.Adapter.Ad_subaminlist;
import com.impetrosys.spideradmin.Adapter.Ad_userlist;
import com.impetrosys.spideradmin.Adapter.Ad_websitelist;
import com.impetrosys.spideradmin.Modelclass.Subadminlist;
import com.impetrosys.spideradmin.Modelclass.UserAccesspemission;
import com.impetrosys.spideradmin.Modelclass.Userlist;
import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.UtilClasses.MarshMallowPermission;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subadmin_list extends AppCompatActivity {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    RecyclerView recycle,recy;
    FrameLayout container;
    private BaseRequest baseRequest;
    String menuid="";
    Ad_subaminlist ad_subaminlist;
    ArrayList<Subadminlist> subadminlist = new ArrayList<>();
    ArrayList<Subadminlist>subadminlist2 = new ArrayList<>();

    Ad_UserAccesspermition ad_useraccesspermition;
    ArrayList<UserAccesspemission> useraccesspemissionlist = new ArrayList<>();
    ArrayList<UserAccesspemission>useraccesspemissionlist1 = new ArrayList<>();

    androidx.appcompat.widget.SearchView inputSearch;
    EditText sname,smobile,spassword;
    TextView tvp,tvchangepassword;
    CheckBox checkBox1,checkBox2,checkBox3;
    Button btn_save;
    String subadminname,subadminmobile,subadminpass;
    private int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Subadmin list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


//        ad_subaminlist = new Ad_subaminlist(getApplicationContext());
//        recycle.setAdapter(ad_subaminlist);
//        recycle.setHasFixedSize(true);
        try {
            Loder();
            ApiGetSubadminlist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void ApiGetSubadminlist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("userlist");
                        subadminlist = baseRequest.getDataList(jsonArray, Subadminlist.class);
                        for (int i = 0; i < subadminlist.size(); i++) {
                            if (subadminlist != null) {


                                Subadminlist model = new Subadminlist();
                                model.setName(subadminlist.get(i).getName());
                                model.setId(subadminlist.get(i).getId());

                                subadminlist2.add(model);

                                ad_subaminlist = new Ad_subaminlist(subadminlist, getApplicationContext(), sessionParam, activity, new Ad_subaminlist.delete() {
                                    @Override
                                    public void getid(String id) {
                                        deleteasubadmin(id);
                                    }

                                    @Override
                                    public void edit(Subadminlist edit) {
                                        addSubadmin(edit);

                                    }
                                });
                                recycle.setAdapter(ad_subaminlist);

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
        baseRequest.callAPIgetSubadminlist(1, remainingUrl2);

    }

    private void ApiGetMenuacesslist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("menulist");
                        useraccesspemissionlist = baseRequest.getDataList(jsonArray, UserAccesspemission.class);
                        for (int i = 0; i < useraccesspemissionlist.size(); i++) {
                            if (useraccesspemissionlist != null) {


                                UserAccesspemission model = new UserAccesspemission();
                                model.setName(useraccesspemissionlist.get(i).getName());
                                model.setId(useraccesspemissionlist.get(i).getId());

                                useraccesspemissionlist1.add(model);

                                ad_useraccesspermition = new Ad_UserAccesspermition(useraccesspemissionlist, getApplicationContext(), sessionParam, activity, new Ad_UserAccesspermition.menuid() {
                                    @Override
                                    public void getid(String id) {
                                        try {
                                            sbmenuids(id);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                recy.setAdapter(ad_useraccesspermition);

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
        baseRequest.callAPIgetmenulist(1, remainingUrl2);

    }
    private void sbmenuids(String id) throws JSONException {
        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

         if(menuid.contains(id)){
            menuid= menuid.replaceAll(id +",","");
         }else {
             menuid=id+","+menuid;
         }
         Toast.makeText(getApplicationContext(), menuid.substring(0,menuid.length()-1), Toast.LENGTH_SHORT).show();
    }



    private void deleteasubadmin(String id){
        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        alertDialogdelee(id);
    }
    private void apideleteSubAdmin(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    ad_subaminlist.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), Subadmin_list.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Delete", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Subadmin_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIdeleteSubadmin(1, "https://impetrosys.com/spiderapp/",id);

    }



    public void addSubadmin(Subadminlist edit)
    {
        Dialog mDialog = new Dialog(Subadmin_list.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.add_subadmin);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        final EditText et_name,et_url,et_description;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        sname= mDialog.findViewById(R.id.editname);
        smobile= mDialog.findViewById(R.id.editmobile);
        spassword= mDialog.findViewById(R.id.password);
        tvp=mDialog.findViewById(R.id.tv_p);
        btn_save= mDialog.findViewById(R.id.btn_send);
        try {
            ApiGetMenuacesslist();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recy = mDialog.findViewById(R.id.rv_list);
        recy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (edit==null){
            btn_save.setText("Add");

        }else {
            sname.setText(edit.getName());
            smobile.setText(edit.getContact());
            smobile.setEnabled(false);
            spassword.setEnabled(false);
            tvp.setVisibility(View.GONE);
            spassword.setVisibility(View.GONE);


            btn_save.setText("Update");

        }



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subadminname = sname.getText().toString();
                subadminmobile = smobile.getText().toString();
                subadminpass = spassword.getText().toString();
                String btname=btn_save.getText().toString();
                if(btname.equals("Update")){
                    try {
                        apiAddSubadminUpdate(edit.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {


                    if (validate()) {
                        try {
                            apiAddSubadmin();
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
    private void apiAddSubadmin() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Add", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Subadmin_list.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Subadmin_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPAddSubadmin(1, "https://impetrosys.com/spiderapp/",subadminname,subadminmobile,subadminpass,menuid.substring(0,menuid.length()-1));

    }
    private void apiAddSubadminUpdate(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Update", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Subadmin_list.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Subadmin_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPUpdateSubadmin(1, "https://impetrosys.com/spiderapp/",subadminname,id,menuid.substring(0,menuid.length()-1));

    }


    private boolean validate() {
        boolean valid = true;

        if (subadminname.equals("") || subadminname.equals(null)) {
            sname.setError("at least 3 characters");
            valid = false;
        } else {
            sname.setError(null);
        }

        if (subadminmobile.equals("")|| subadminmobile.equals(null)) {
            smobile.setError("enter a valid mobile");
            valid = false;
        } else {
            smobile.setError(null);
        }
        if (subadminpass.equals("") || subadminpass.equals(null)) {
            spassword.setError("at least 3 characters");
            valid = false;
        } else {
            spassword.setError(null);
        }

//        if(!(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked())){
//            Toast.makeText(getApplicationContext(), "select check box", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
        return valid;
    }
    public void alertDialogdelee(String id) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            //                    @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    apideleteSubAdmin(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();

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
        inputSearch = (SearchView)myActionMenuItem.getActionView();
        changeSearchViewTextColor(inputSearch);
        inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ad_subaminlist.getFilter().filter(s);
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
//                Intent intent = new Intent(Subadmin_list.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Subadmin_list.this,Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
            case R.id.action_add:
                addSubadmin(null);
                return true;
        }
        return super.onOptionsItemSelected(item);
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