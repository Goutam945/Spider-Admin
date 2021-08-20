package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.impetrosys.spideradmin.UtilClasses.SessionParam;
import com.impetrosys.spideradmin.retrofit.BaseRequest;
import com.impetrosys.spideradmin.retrofit.RequestReciever;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Act_Deposit_Id_details extends AppCompatActivity {
    String Username,Userid,Coins,Websitename,Websiteurl,Requestusername,Createddate,Id,Paywallet,Paymentmethod,Paymentscreenshot,Depositstatus;
    FrameLayout container;
    TextView username,userid,coins,websitename,websiteurl,requestusername,paymentmethod,createddate,depositstatus;
    ImageView imageView;
    ProgressBar progress;
    Button approve,reject;
    Context context;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Button btn_approve,btn_reject;
    TextView textimge,reject_textimge;
    ImageView rejct_img;
    EditText reject_description;
    String Rejct_dis;
    private String upload_img="",rejrct_upload_img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_deposit_id_details);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_deposit_id_details, null);
        container.addView(rowView, container.getChildCount());

        username = rowView.findViewById(R.id.d_username);
        userid = rowView.findViewById(R.id.d_id);
        coins = rowView.findViewById(R.id.d_coin);
        depositstatus = rowView.findViewById(R.id.d_depositstatus);

        websitename = rowView.findViewById(R.id.d_websitename);
        websiteurl = rowView.findViewById(R.id.d_websiteurl);
        requestusername = rowView.findViewById(R.id.d_requestusername);
        paymentmethod = rowView.findViewById(R.id.d_paymentmethod);
        createddate = rowView.findViewById(R.id.d_date);
        imageView = rowView.findViewById(R.id.icon);
        progress = rowView.findViewById(R.id.progressBar);

        approve = rowView.findViewById(R.id.btn_accept);
        reject = rowView.findViewById(R.id.btn_reject);

        Intent intent=getIntent();
        Username=intent.getStringExtra("username");
        Userid=intent.getStringExtra("userid");
        Coins=intent.getStringExtra("coins");
        Depositstatus=intent.getStringExtra("depositstatus");
        Websitename=intent.getStringExtra("websitename");
        Websiteurl=intent.getStringExtra("websiteurl");
        Requestusername=intent.getStringExtra("requestusername");
        Createddate=intent.getStringExtra("created_date");
        Id=intent.getStringExtra("id");
        Paywallet=intent.getStringExtra("pay_wallet");
        Paymentmethod=intent.getStringExtra("paymentmethod");
        Paymentscreenshot=intent.getStringExtra("paymentscreenshot");

        username.setText("Username : "+Username);
        userid.setText("Userid : "+Userid);
        coins.setText("Coins : "+Coins);
        depositstatus.setText("Deposits : "+Depositstatus);
        websitename.setText("Websitename : "+Websitename);
        websiteurl.setText("Websiteurl : "+Websiteurl);
        requestusername.setText("Requestusername : "+Requestusername);
        paymentmethod.setText("Paymentmethod : "+Paymentmethod);
        createddate.setText("Date : "+Createddate);



        if (Paywallet.equalsIgnoreCase("0")){
            imageView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(Paymentscreenshot)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progress.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }else {
            imageView.setVisibility(View.GONE);
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiapprovrequest_depositid();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  try {
                    apiRejectrequest_depositid();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Reject_request();

            }
        });

    }
    private void apiapprovrequest_depositid() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Intent i = new Intent(getApplicationContext(), Act_paymentdeposit.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Approve", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Deposit_Id_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIapproveDepositsID_request(1, "https://impetrosys.com/spiderapp/",Id);

    }
    private void apiRejectrequest_depositid() throws JSONException {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    Intent i = new Intent(getApplicationContext(), Act_paymentdeposit.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Sucessfully Reject", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(Act_Deposit_Id_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIDeposits_reject(1, "https://impetrosys.com/spiderapp/",Id,upload_img,Rejct_dis);

    }
    public void Reject_request()
    {
        Dialog mDialog = new Dialog(Act_Deposit_Id_details.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.deposit_rejectw);
        mDialog.setCanceledOnTouchOutside(false);
        ImageView iv_cancel_dialog;
        iv_cancel_dialog=mDialog.findViewById(R.id.iv_cancel_dialog);
        reject_description=mDialog.findViewById(R.id.editudescription);
        reject_textimge=mDialog.findViewById(R.id.tv_img);
        rejct_img=mDialog.findViewById(R.id.iv_image);
        btn_reject= mDialog.findViewById(R.id.btn_reject);

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

        reject_textimge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }

        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reject_description.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Rejct_dis=reject_description.getText().toString();
                    try {
                        apiRejectrequest_depositid();
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



    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Act_Deposit_Id_details.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 1);
                    // activityResultLauncher.launch(intent);


                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                    //activityResultLauncher.launch(intent);
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
                rejct_img.setImageBitmap(srcBmp);
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
                rejct_img.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        upload_img = Base64.encodeToString(b, Base64.DEFAULT);
        rejct_img.setVisibility(View.VISIBLE);
        return upload_img;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Deposit_Id_details.this, Act_paymentdeposit.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Act_Deposit_Id_details.this,Act_paymentdeposit.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}