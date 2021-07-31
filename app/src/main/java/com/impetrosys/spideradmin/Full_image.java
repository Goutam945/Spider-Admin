package com.impetrosys.spideradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Full_image extends AppCompatActivity {
ImageView imageView,backbtn,imageView1;
String imgurl,imagurl1;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        imageView=(ImageView)findViewById(R.id.img_full);
        imageView1=(ImageView)findViewById(R.id.img_full1);
        //backbtn=(ImageView)findViewById(R.id.back_arrow);
        Intent intent=getIntent();
        imgurl=intent.getStringExtra("paymentscreenshot");
        Picasso.get()
                .load(imgurl)
                .into(imageView);
        Intent intent1=getIntent();

        imagurl1=intent1.getStringExtra("paymentpic");
        Picasso.get()
                .load(imagurl1)
                .into(imageView1);


        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            imageView1.setScaleX(mScaleFactor);
            imageView1.setScaleY(mScaleFactor);

            return true;
        }
    }
}