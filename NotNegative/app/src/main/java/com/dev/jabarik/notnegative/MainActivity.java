package com.dev.jabarik.notnegative;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {


    android.support.v7.widget.Toolbar toolbar;

    TextView butt1,butt2, butt3, butt4, butt5, butt6, butt7, butt8, butt9;

    ImageView colorpick;
    Button setWallbutt;
    Button setWallPrev, cancel;

    View onel,one2,one3,twol,two2,two3,three1,three2,three3,four1,four2;


    TextView setWallText;
    public static TextView auth;

    public static ImageView backgroundColor;

    public static TextView backText;
    public static TextView loading;

    int defaultColor;
    public static int colorPicked;
    int widthMain;
    int heightMain;

    public static Bitmap statBit;

    ImageView borderSolid;
    public static ImageView border;
    ImageView previewImage;


    static String quoteMain;
    static String authorMain;
    public static Tasker task;
    static String quote = "";

    Dialog myPrev;
    Bitmap stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rellay);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toob);
        setSupportActionBar(toolbar);

      taskRun();



        butt1 = (TextView) findViewById(R.id.butt1);
        butt2 = (TextView) findViewById(R.id.butt2);
        butt3 = (TextView) findViewById(R.id.butt3);
         butt4 = (TextView) findViewById(R.id.butt4);
         butt5 = (TextView) findViewById(R.id.butt5);
         butt6 = (TextView) findViewById(R.id.butt6);
        butt7 = (TextView) findViewById(R.id.butt4);
        butt8 = (TextView) findViewById(R.id.butt8);
        butt9 = (TextView) findViewById(R.id.butt9);
       //  setWallbutt = findViewById(R.id.setWall);
        colorpick = (ImageView) findViewById(R.id.imageButton);

        auth = (TextView) findViewById(R.id.authorView);
        loading =(TextView) findViewById(R.id.loadText);
        backText = (TextView) findViewById(R.id.backText);
        defaultColor = ContextCompat.getColor(MainActivity.this,R.color.cred);
        backgroundColor = (ImageView) findViewById(R.id.background);
        border = (ImageView) findViewById(R.id.border);

       quoteMain = "";

    onel = (View) findViewById(R.id.one1);
    one2 = (View) findViewById(R.id.one2);
    one3 = (View) findViewById(R.id.one3);
        twol = (View) findViewById(R.id.two1);
        two2 = (View) findViewById(R.id.two2);
        two3 = (View) findViewById(R.id.two3);
    three1 = (View) findViewById(R.id.three1);
    three2 = (View) findViewById(R.id.three2);
    three3 = (View) findViewById(R.id.three3);
        four1 = (View) findViewById(R.id.four1);
        four2 = (View) findViewById(R.id.four2);

        setDefaults();
    }

    public void previewDialog(View view){
        myPrev = new Dialog(MainActivity.this);
        myPrev.setContentView(R.layout.dialogpreview);
        myPrev.setTitle("Preview");


        setWallPrev = (Button)myPrev.findViewById(R.id.setPreview);
        cancel = (Button) myPrev.findViewById(R.id.cancel);

        previewImage = (ImageView) myPrev.findViewById(R.id.previewImage);

        setWallPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewToBitmap(backgroundColor,backText,auth,border,backgroundColor.getWidth(),backgroundColor.getHeight(),colorPicked,getApplicationContext(),1);
                myPrev.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPrev.cancel();
            }
        });

        myPrev.show();

        previewImage.setImageBitmap(viewToBitmap(backgroundColor,backText,auth,border,backgroundColor.getWidth(),backgroundColor.getHeight(),colorPicked,getApplicationContext(),0));

    }


    public static void taskRun(){
        task = new Tasker();
        task.execute("https://api.forismatic.com/api/1.0/?method=getQuote&format=text&lang=en");
    }


    public static class Tasker extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection;
            String result = "";


            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader read = new InputStreamReader(in);
                int data = read.read();

                while(data!=-1){
                    char c = (char) data;
                    result+=c;
                    data = read.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                return result;

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.i("Length", String.valueOf(result.length()));
                if (result != null && result.length() < 90) {
                    loading.setText("");
                    String list = result.substring(0, result.indexOf("("));
                    String authorList = result.substring(result.indexOf("(") + 1, result.indexOf(")"));

                    quoteMain = list;
                    authorMain = authorList;
                } else {
                    loading.setText("LOADING...");
                    taskRun();
                }
            }catch (Exception e){
                String error = e.toString();
                loading.setText("Error Loading, Restart App");
            }
        }
    }


    public void pickColor(View view){
        openColorPicker();
    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                colorPicked = color;
               colorpick.setBackgroundColor(color);
               backgroundColor.setBackgroundColor(color);

               backgroundColor.refreshDrawableState();
               // Log.i("COLOR",String.valueOf(defaultColor));
                Log.i("COLORPICKED",String.valueOf(colorPicked));
            }
        });
        colorPicker.show();
    }

    public Bitmap viewToBitmap(View view, View view2,View view3, View view4, int width, int heigth, int colorW, Context con, int choice){

        taskRun();

        backText.setText(quoteMain);
        auth.setText(" - "+ authorMain);

        Bitmap b = Bitmap.createBitmap(width,heigth, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
           view.draw(c);
           view2.draw(c);
           view3.draw(c);
           view4.draw(c);

        statBit = b;
if(choice==1){
        WallpaperManager manage = WallpaperManager.getInstance(con);
        try {
            int a = quoteMain.length();
            manage.setBitmap(b);
            manage.setWallpaperOffsetSteps(0,0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stat = b;}
    return b;
    }

    public void setDefaults(){
        backText.setTypeface(Typeface.DEFAULT_BOLD);
        border.setImageResource(R.drawable.borderdotted);
        backText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        backText.setTextColor(getResources().getColor(R.color.clearblack));
    }

    public void buttonTS(View view) {

        String one = String.valueOf(view.getTag());

        if(one.equals("1")) {
           backText.setTypeface(Typeface.DEFAULT_BOLD);
            onel.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            one2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            one3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("2")) {
            backText.setTypeface(Typeface.DEFAULT);
            onel.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            one2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            one3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }


        if(one.equals("3")) {
            backText.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            onel.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            one2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            one3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

    }

    public void buttonBorder(View view) {


        String one = String.valueOf(view.getTag());

        if(one.equals("4")) {
            border.setImageResource(R.drawable.borderdotted);
            twol.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            two2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            two3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("5")) {
           border.setImageResource(R.drawable.borderempty);
            twol.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            two2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            two3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("6")) {
           border.setImageResource(R.drawable.bordersolid);
            twol.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            two2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            two3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

    }

    public void buttonAlignment(View view) {

        String one = String.valueOf(view.getTag());
        if(one.equals("7")) {
           backText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
            three1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            three2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            three3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("8")) {
           backText.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER);
            three1.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            three2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            three3.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("9")) {
           backText.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            three1.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            three2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            three3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

    }

    public void setTextColor(View view){

        String one = view.getTag().toString();

        if(one.equals("10")){
            backText.setTextColor(getResources().getColor(R.color.clearblack));
            four1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            four2.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
        }

        if(one.equals("11")){
            backText.setTextColor(getResources().getColor(R.color.clearwhite));
            four1.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
            four2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

    }

    public void setWallpaper(View view){

        viewToBitmap(backgroundColor,backText,auth,border,backgroundColor.getWidth(),backgroundColor.getHeight(),colorPicked,getApplicationContext(),1);
        //setWallbutt.setBackgroundColor(getResources().getColor(R.color.greenSelect));
        //taskRun();
    }




}