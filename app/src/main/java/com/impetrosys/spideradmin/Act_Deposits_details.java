package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Act_Deposits_details extends AppCompatActivity {
String username,userid,paymentmethod,coins,Depositstatus,paymentscreenshot,createddate,ID,Rewardstatus, Wallet="0",Reward="0",Total="0",Clientname, Websitename;
    FrameLayout container;
    TextView name,uid,pmethod,coin,dete,wallet,reward,total,depositstatus,websitename ,clientname;
    ImageView imageView;
    ProgressBar progress;
    Button approve,reject;
    Context context;
    SessionParam sessionParam;
    private BaseRequest baseRequest;
    private int progressStatus = 0;
    Button btn_reject;
    TextView reject_textimge;
    ImageView rejct_img;
    EditText reject_description;
    String Rejct_dis;
    private String upload_img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_deposits_details);
        setContentView(R.layout.act_home_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        container = findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_deposits_details, null);
        container.addView(rowView, container.getChildCount());

        name = rowView.findViewById(R.id.d_username);
        uid = rowView.findViewById(R.id.d_id);
        pmethod = rowView.findViewById(R.id.d_paymentmethod);
        coin = rowView.findViewById(R.id.d_coin);
        depositstatus = rowView.findViewById(R.id.depositstatus);
        dete = rowView.findViewById(R.id.d_date);
        imageView = rowView.findViewById(R.id.icon);
        progress = rowView.findViewById(R.id.progressBar);
        approve = rowView.findViewById(R.id.btn_accept);
        reject = rowView.findViewById(R.id.btn_reject);

        wallet = rowView.findViewById(R.id.wallet);
        reward = rowView.findViewById(R.id.reward);
        total = rowView.findViewById(R.id.total);

        clientname = rowView.findViewById(R.id.clietname);
        websitename = rowView.findViewById(R.id.webstename);


        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        userid=intent.getStringExtra("userid");
        paymentmethod=intent.getStringExtra("paymentmethod");
        coins=intent.getStringExtra("coins");
        Depositstatus=intent.getStringExtra("depositstatus");
        paymentscreenshot=intent.getStringExtra("paymentpic");
        createddate=intent.getStringExtra("created_date");
        ID=intent.getStringExtra("id");

        Rewardstatus=intent.getStringExtra("rewardstatus");
        Wallet=intent.getStringExtra("wallet");
        Reward=intent.getStringExtra("reward");
        Total=intent.getStringExtra("total");
        Clientname=intent.getStringExtra("clientname");
        Websitename=intent.getStringExtra("websitename");


        name.setText("Client Name : "+username);
        uid.setText("User Id : "+userid);
        pmethod.setText("Paymentmethod : "+paymentmethod);
        coin.setText("Coins : "+coins);
        depositstatus.setText("Deposits : "+Depositstatus);
        dete.setText("Date : "+createddate);
        clientname.setText("User Name : "+Clientname);
        websitename.setText("Website Name : "+Websitename);


        if(Rewardstatus.equalsIgnoreCase("0")){
            wallet.setVisibility(View.GONE);
            reward.setVisibility(View.GONE);
            total.setVisibility(View.GONE);

        }else {
            wallet.setText("Wallet : "+Wallet);
            reward.setText("Reward : "+Reward);
            total.setText("Total : "+Total);
        }




//        Picasso.get()
//                .load(paymentscreenshot)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);

        Picasso.get()
                .load(paymentscreenshot)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Act_Deposits_details.this, Act_Full_image.class);
                i.putExtra("paymentpic",paymentscreenshot);
                startActivity(i);
               finish();

            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apiapprovrequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    apiRejectrequest();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Reject_request();
            }
        });


    }
    public void SaveImage() {
        Picasso.get().load(paymentscreenshot).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydie = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/HIR_IMG");
                    if (!mydie.exists()) {
                        mydie.mkdirs();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(mydie, new Date().toString() + ".jpg"));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }


    private void apiapprovrequest() throws JSONException {
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
                Toast.makeText(Act_Deposits_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIapproveDeposits_request(1, "https://impetrosys.com/spiderapp/",ID,Rewardstatus);

    }
    private void apiRejectrequest() throws JSONException {
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
                Toast.makeText(Act_Deposits_details.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        baseRequest.callAPIDeposits_reject(1, "https://impetrosys.com/spiderapp/",ID,upload_img,Rejct_dis);

    }
    public void Reject_request()
    {
        Dialog mDialog = new Dialog(Act_Deposits_details.this);
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
                        apiRejectrequest();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Act_Deposits_details.this);
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
//                Intent intent = new Intent(Deposits_details.this, Act_paymentdeposit.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                Intent i=new Intent(Act_Deposits_details.this,Act_paymentdeposit.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
   /* @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }*/
}