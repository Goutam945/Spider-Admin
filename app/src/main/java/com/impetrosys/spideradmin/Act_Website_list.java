package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

import com.impetrosys.spideradmin.Adapter.Ad_websitelist;
import com.impetrosys.spideradmin.Modelclass.Websitelist;
import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Act_Website_list extends AppCompatActivity {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    RecyclerView recycle;
    FrameLayout container;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    androidx.appcompat.widget.SearchView inputSearch;
    Ad_websitelist ad_websitelist;
    ArrayList<Websitelist> websitelist = new ArrayList<>();
    ArrayList<Websitelist>websitelist2 = new ArrayList<>();

    ArrayList<Websitelist.Game> gamelist = new ArrayList<>();

    private String Document_img="";
    String websitename,url,description;
    Button btn_save;
     EditText et_name,et_url,et_description;
    CheckBox ch, ch1, ch2, ch3,ch4,ch5,ch6;
    EditText cricket,football,tennis,hourse,card,livecasino,politics;
    JSONArray jsonArray=new JSONArray();
   ImageView image;
   TextView imgtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Website List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null)
//        {
//            actionBar.setTitle("Website list");
//        }



        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.recyleview_list, null);
        container.addView(rowView, container.getChildCount());

        recycle = rowView.findViewById(R.id.recycle_all);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


//        ad_websitelist = new Ad_websitelist(getApplicationContext());
//        recycle.setAdapter(ad_websitelist);
//        recycle.setHasFixedSize(true);


        Loder();
        try {
            ApiGetwebsitelist();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void ApiGetwebsitelist () throws JSONException {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                websitelist.clear();
                websitelist2.clear();
                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    if (!jsonObject.getString("message").equals("Failed")) {

                        JSONArray jsonArray = jsonObject.optJSONArray("websitelist");
                        websitelist = baseRequest.getDataList(jsonArray, Websitelist.class);
                        for (int i = 0; i < websitelist.size(); i++) {
                            if (websitelist != null) {
                                Websitelist model = new Websitelist();
                                model.setName(websitelist.get(i).getName());
                                model.setUrl(websitelist.get(i).getUrl());
                                model.setDescription(websitelist.get(i).getDescription());
                                websitelist2.add(model);

                                for(int k=0;k<websitelist.get(i).getGames().size();k++){
                                   //     Toast.makeText(getApplicationContext(),websitelist.get(i).getGames().get(k).getName()+"hii",Toast.LENGTH_SHORT).show();
                                        Log.d("app"+ getApplicationContext(),websitelist.get(i).getGames().get(k).getName());

                                }


                                ad_websitelist = new Ad_websitelist(websitelist,gamelist, getApplicationContext(), sessionParam, activity, new Ad_websitelist.delete() {
                                   //interfaceget Ad_websitelist.delete()
                                    @Override
                                    public void getid(String id) {
                                        try {
                                            deleteiteam(id);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void edit(Websitelist websitelist) {
                                        addwebsites(websitelist);
                                    }
                                });
                                recycle.setAdapter(ad_websitelist);

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
        baseRequest.callAPIgetwebsitelist(1, remainingUrl2);

    }

    private void deleteiteam(String id) throws JSONException {
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        apideletewebsite(id);
    }

    private void apideletewebsite(String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                   ad_websitelist.notifyDataSetChanged();
                    Intent i = new Intent(getApplicationContext(), Act_Website_list.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Delete", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Website_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIdeletewebsite(1, "https://impetrosys.com/spiderapp/",id);

    }

    public void addwebsites(Websitelist websitelist)
    {

        Dialog mDialog = new Dialog(Act_Website_list.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.add_website);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;


        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        et_name= mDialog.findViewById(R.id.editname);
        et_url= mDialog.findViewById(R.id.editurl);
        et_description= mDialog.findViewById(R.id.editdescription);
        btn_save= mDialog.findViewById(R.id.btn_save);
        ch=mDialog.findViewById(R.id.cricket);
        ch1=mDialog.findViewById(R.id.football);
        ch2=mDialog.findViewById(R.id.tennis);
        ch3=mDialog.findViewById(R.id.horseRacing);
        ch4=mDialog.findViewById(R.id.Cards);
        ch5=mDialog.findViewById(R.id.livcosino);
        ch6=mDialog.findViewById(R.id.politics);
        cricket=mDialog.findViewById(R.id.et_cricketprice);
        football=mDialog.findViewById(R.id.et_footbalprice);
        tennis=mDialog.findViewById(R.id.et_tenies);
        hourse=mDialog.findViewById(R.id.et_hourse);
        card=mDialog.findViewById(R.id.et_card);
        livecasino=mDialog.findViewById(R.id.et_livcosino);
        politics=mDialog.findViewById(R.id.et_politics);
        Checkboxshideshow();

        image=mDialog.findViewById(R.id.mv_image);
        imgtext=mDialog.findViewById(R.id.tv_img);
        imgtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }

        });


        //checkcondition update add

        if (websitelist==null){
            btn_save.setText("Add");


        }else {
            et_name.setText(websitelist.getName());
            et_url.setText(websitelist.getUrl());
            et_description.setText(websitelist.getDescription());
            if(websitelist.getPhoto() != null && !websitelist.getPhoto().isEmpty() ) {
            Picasso.get()
                    .load(websitelist.getPhoto())
                    .into(image);
            image.setVisibility(View.VISIBLE);}



            for(int i=0;i<websitelist.getGames().size();i++){
                String price=websitelist.getGames().get(i).getPrice().toString();
                String name=websitelist.getGames().get(i).getName().toString();
                if (name.equalsIgnoreCase("cricket")){
                    cricket.setText(price);
                    cricket.setVisibility(View.VISIBLE);
                      ch.setChecked(true);
                }
                if (name.equalsIgnoreCase("football")){
                    football.setText(price);
                    football.setVisibility(View.VISIBLE);
                    ch1.setChecked(true);
                }
                if (name.equalsIgnoreCase("tennis")){
                    tennis.setText(price);
                    tennis.setVisibility(View.VISIBLE);
                    ch2.setChecked(true);
                }
                if (name.equalsIgnoreCase("horse racing")){
                    hourse.setText(price);
                    hourse.setVisibility(View.VISIBLE);
                    ch3.setChecked(true);
                }
                if (name.equalsIgnoreCase("cards")){
                    card.setText(price);
                    card.setVisibility(View.VISIBLE);
                    ch4.setChecked(true);
                }
                if (name.equalsIgnoreCase("live casino")){
                    livecasino.setText(price);
                    livecasino.setVisibility(View.VISIBLE);
                    ch5.setChecked(true);
                }
                if (name.equalsIgnoreCase("politics")){
                    politics.setText(price);
                    politics.setVisibility(View.VISIBLE);
                    ch6.setChecked(true);
                }
            }



            btn_save.setText("Update");
        }
        iv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btname=btn_save.getText().toString();
                websitename = et_name.getText().toString();
                url = et_url.getText().toString();
                description = et_description.getText().toString();
                //check condition button
                if(btname.equals("Update")){
                    try {
                        if(ch.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","cricket");
                                jsonObject.put("price",Integer.parseInt(cricket.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch1.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","football");
                                jsonObject.put("price",Integer.parseInt(football.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch2.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","tennis");
                                jsonObject.put("price",Integer.parseInt(tennis.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch3.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","horse racing");
                                jsonObject.put("price",Integer.parseInt(hourse.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch4.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","cards");
                                jsonObject.put("price",Integer.parseInt(card.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch5.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","live casino");
                                jsonObject.put("price",Integer.parseInt(livecasino.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        Log.e("add",""+jsonArray);

                        apiUpdatewebsite(jsonArray,websitelist.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    try {
                        if(ch.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","cricket");
                                jsonObject.put("price",Integer.parseInt(cricket.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch1.isChecked())
                            try {

                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","football");
                                jsonObject.put("price",Integer.parseInt(football.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch2.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","tennis");
                                jsonObject.put("price",Integer.parseInt(tennis.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch3.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","horse racing");
                                jsonObject.put("price",Integer.parseInt(hourse.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch4.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","cards");
                                jsonObject.put("price",Integer.parseInt(card.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch5.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","live casino");
                                jsonObject.put("price",Integer.parseInt(livecasino.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        if(ch6.isChecked())
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("name","politics");
                                jsonObject.put("price",Integer.parseInt(politics.getText().toString()));
                                jsonObject.put("status",1);
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        Log.e("add",""+jsonArray);

                    if(validate()){
                        apiAddwebsite(jsonArray);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }}
            }
        });

        mDialog.show();

    }


    private void apiAddwebsite(JSONArray jsonArray) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Add", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Website_list.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Website_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPAddwebsite(1, "https://impetrosys.com/spiderapp/",websitename,url,description,jsonArray,Document_img);

    }
    private void apiUpdatewebsite(JSONArray jsonArray ,String id) throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Sucessfully Edit", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Act_Website_list.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Website_list.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIUpdatewebsite(1, "https://impetrosys.com/spiderapp/",id,websitename,url,description,jsonArray,Document_img);

    }
    public void Checkboxshideshow(){
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    cricket.setVisibility(View.VISIBLE);

                }else
                    cricket.setVisibility(View.GONE);


            }
        });
        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    football.setVisibility(View.VISIBLE);
                }else
                    football.setVisibility(View.GONE);

            }
        });
        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    tennis.setVisibility(View.VISIBLE);
                }else
                    tennis.setVisibility(View.GONE);

            }
        });
        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    hourse.setVisibility(View.VISIBLE);
                }else
                    hourse.setVisibility(View.GONE);

            }
        });
        ch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    card.setVisibility(View.VISIBLE);
                }else
                    card.setVisibility(View.GONE);

            }
        });
        ch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    livecasino.setVisibility(View.VISIBLE);
                }else
                    livecasino.setVisibility(View.GONE);

            }
        });
        ch6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    politics.setVisibility(View.VISIBLE);
                }else
                    politics.setVisibility(View.GONE);

            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        if (websitename.equals("") || websitename.equals(null)) {
            et_name.setError("at least 3 characters");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (url.equals("")|| url.equals(null) || !Patterns.WEB_URL.matcher(url).matches()) {
            et_url.setError("enter a valid url");
            valid = false;
        } else {
            et_url.setError(null);
        }
        if (description.equals("") || description.equals(null)) {
            et_description.setError("at least 3 characters");
            valid = false;
        } else {
            et_description.setError(null);
        }
        if(!(ch.isChecked()||ch1.isChecked()||ch2.isChecked()||ch3.isChecked()||ch4.isChecked()||ch5.isChecked()||ch6.isChecked())){
            Toast.makeText(getApplicationContext(), "select check box", Toast.LENGTH_SHORT).show();
            valid = false;
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

   private void selectImage() {
       final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
       AlertDialog.Builder builder = new AlertDialog.Builder(Act_Website_list.this);
       builder.setTitle("Add Photo!");
       builder.setItems(options, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int item) {
               if (options[item].equals("Take Photo"))
               {
                   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                   startActivityForResult(intent, 1);

               }
               else if (options[item].equals("Choose from Gallery"))
               {
                   Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(intent, 2);
               }
               else if (options[item].equals("Cancel")) {
                   dialog.dismiss();
               }
           }
       });
       builder.show();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap srcBmp = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(srcBmp);
                BitMapToString(srcBmp);
                Log.w("path.....", srcBmp+"");

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("path.....", picturePath+"");
                image.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img = Base64.encodeToString(b, Base64.DEFAULT);
        image.setVisibility(View.VISIBLE);
        return Document_img;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.web_search);
        inputSearch = (SearchView)myActionMenuItem.getActionView();
        changeSearchViewTextColor(inputSearch);

        inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ad_websitelist.getFilter().filter(s);
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
//                Intent intent = new Intent(Website_list.this, Dashbord.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Act_Website_list.this, Act_Dashbord.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
            case R.id.action_add:
                ArrayList<String> arrPerm = new ArrayList<>();
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    arrPerm.add(Manifest.permission.CAMERA);
                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    arrPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    arrPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                if(!arrPerm.isEmpty()) {
                    String[] permissions = new String[arrPerm.size()];
                    permissions = arrPerm.toArray(permissions);
                    ActivityCompat.requestPermissions(this, permissions, 1);
                }
                addwebsites(null);
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
