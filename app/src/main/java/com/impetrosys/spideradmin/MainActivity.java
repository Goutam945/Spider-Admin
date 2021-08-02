package com.impetrosys.spideradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.impetrosys.spideradmin.UtilClasses.SessionParam;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    SessionParam sessionParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("TIKEN", token);
//                       // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//
//                    }
//                });


        sessionParam=new SessionParam(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sessionParam.login.equals("yes")){
                    Intent i=new Intent(MainActivity.this, Act_Dashbord.class);
                    startActivity(i);
                    finish();

                }else {
                    Intent i=new Intent(MainActivity.this, Act_Login_screen.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);


    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}