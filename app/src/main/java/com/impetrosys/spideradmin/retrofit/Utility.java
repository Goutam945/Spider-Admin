package com.impetrosys.spideradmin.retrofit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


import com.impetrosys.spideradmin.R;

import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {
    public static ProgressDialog progressDialog =null;
    public static AlertDialog.Builder alertbox;
    public static AlertDialog alertDialog;
    public static LayoutInflater _inflater;
    public static View progressdialogview;
    public static Dialog progress_dialog;
    public static RotateAnimation rAnim;
    FingerprintManager fingerprintManager;
//    GoogleMap googleMap;
    public Context activity;
    public static String _user_image_base64 = "";
    public static String firs_char = "";



    //in this class we will writing code which we need to use more often
    //for eg: fetching current date or showing toast

    //    //change status bar code on lollipop
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void changeStatusBarColor(Activity con)

    {
        Window window = con.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(con.getResources().getColor(R.color.black));
    }


    public static boolean getAPIVerison() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    }

    public boolean isValidLatLng(double lat, double lng){
        if(lat < -90 || lat > 90)
        {
            return false;
        }
        else if(lng < -180 || lng > 180)
        {
            return false;
        }
        return true;
    }


    public static void cancelDialog()
    {
        if(progress_dialog!=null)
        {
            //System.out.println("Dialog is Canceling");
            progress_dialog.cancel();
            progress_dialog = null;
        }
    }


    //    // ///////////////////show Progress dialog
    public static void showProgressDialog(final Context context)
 {
        progressDialog = new ProgressDialog(context,
                android.R.style.Theme_Panel);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        //		System.out.println("Dialog is Showing");
    }

    public static void sucessDialog(String message,String title,String btn, Context context) {

        final Dialog mDialog=new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.notification_dailog);
        mDialog.setCanceledOnTouchOutside(true);

        Button btn_ok;
        TextView tv_notification,tv_title;
        btn_ok= mDialog.findViewById(R.id.btn_ok);
        tv_title= mDialog.findViewById(R.id.tv_title);
        tv_notification= mDialog.findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        tv_title.setText(title);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();

            }
        });
        mDialog.show();


    }

    public static boolean checkEmail(String email)
    {
        String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*"
                + "+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern emailPattern = Pattern.compile(expression);
        return emailPattern.matcher(email).matches();
    }

//                        //hide key board
    public static void hideKeyBoard(View view, Activity context)
    {
        View focusedView = context.getCurrentFocus();
        if (focusedView != null) {
            ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void showAlertDialog(final String title, String message, final Context context, final boolean redirectToPreviousScreen)
    {
        if (alertDialog != null && alertDialog.isShowing())
        {

        }
        else
        {
            alertbox = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            alertbox.setMessage(message);
            alertbox.setTitle(title);
            /*alertbox.setTitle(Gravity.CENTER);*/
            alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface arg0, int arg1)
                {
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertbox.create();
            alertDialog.show();
        }
    }
    ///////// key board hide



    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    //----------- hide KeyBoard------------

    public static void hideKeyBoard(Activity context)
    {
        View focusedView = context.getCurrentFocus();
        //	 Toast.makeText(context,"not hide", 1).show();
        if (focusedView != null) {
            //       Toast.makeText(context,"hide", 1).show();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }



    //----------- bitmap To Base64------------

    public static String bitmapToBase64(Bitmap bitmap)
    {
        String base64=null;
        try{
            if(bitmap!=null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,90, baos);
                byte [] _byteArray = baos.toByteArray();
                base64 = Base64.encodeToString(_byteArray, Base64.DEFAULT);
//				base64 = Base64.encodeToString(_byteArray,Base64.NO_WRAP);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }catch (OutOfMemoryError e)
        {
            e.printStackTrace();
        }
        return base64;
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    Bitmap ShrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }




    //-----------decode Sampled Bitmap From Resource------------

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    //-----------calculate In Sample Size------------

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //-----------get image uri------------

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //-----------Get image path------------

    public static String getPath(Uri uri, Context context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    //-----------change Date YMD to DMY------------

    public String changeDateYMDtoDMY(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    //-----------change Date month Name------------

    public static String changeDatemonthName(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("MMM-dd, yyyy HH:mm");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }
    //-----------changeDate DMY to YMD-----------

    public static String changeDateDMYtoYMD(String date) {

        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);

        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    public static String changeYMDtoDateDMY(String date) {

        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);

        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    //-----------Email validation------------

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //-----------convert To String------------

    public static String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        return sb.toString();
    }
    //-----------convert To Array------------

    private ArrayList<String> convertToArray(String string) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }

    //-----------CONVERT meter To KM--------------

    public String meterToKM(float meters) {
        float km;
        km = meters / 1000;
        return format(km);
    }

    //-----------CONVERT Km To meter--------------

    public String kmToMeter(float km) {
        float meter;
        meter = km * 1000;
        return format(meter);
    }


    // Function to convert ArrayList<String> to String[]

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    //-----------FORMAT------------

    public static String format(Number n) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return format.format(n);
    }


    //-----------Dist in km------------

    public static double distanceInKM(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public float distanceFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        Log.e("DISTANCE RADIUS: ", String.valueOf(dist));

        int meterConversion = 1609;
        return new Float(dist * meterConversion).floatValue();
    }
         public String removeFirstChar(String s){
             return s.substring(1);
            }
    public static String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }


    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i ++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length)?1:-1;
        }

        return res;
    }


}



