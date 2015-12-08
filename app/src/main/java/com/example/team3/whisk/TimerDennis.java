package com.example.team3.whisk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class TimerDennis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button stopAlert, start, pause, resume, reset;
    private NumberPicker numberRollMin,numberRollHr,numberRollSec;
    private  ProgressBar timeProgress;
    private TextView time,textHr,textMin,textSec;
    private MediaPlayer playClip;
    private NotificationManager notimanager;
    private CounterClass timer;
    private long totalTime;
    private boolean resetGuard=false;
    private int hou,minu,secd,timeMax, progressCounter=0;
    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_dennis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //*** Declaring and Connecting ID ***//
        // Button id is connected
        stopAlert=(Button)findViewById(R.id.stopRing);
        start=(Button)findViewById(R.id.buttonStart);
        pause=(Button)findViewById(R.id.buttonPause);
        resume=(Button)findViewById(R.id.buttonResume);
        reset=(Button)findViewById(R.id.buttonReset);

        //Visibility for button
        pause.setVisibility(View.INVISIBLE);
        resume.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);


        //ProgressBar ID
        timeProgress=(ProgressBar)findViewById(R.id.progressBar);

        //TextView id is connected
        time=(TextView)findViewById(R.id.timeText);
        textHr=(TextView)findViewById(R.id.hourstext);
        textMin=(TextView)findViewById(R.id.mintext);
        textSec=(TextView)findViewById(R.id.sectext);


        //NumberPicker id is connected
        numberRollHr=(NumberPicker)findViewById(R.id.numberPickerHR);
        numberRollMin=(NumberPicker)findViewById(R.id.numberPickerMIN);
        numberRollSec=(NumberPicker)findViewById(R.id.numberPickerSEC);

        //setRange numberRolls
        //Hr
        numberRollHr.setMaxValue(3);
        numberRollHr.setMinValue(0);

        //Min
        numberRollMin.setMaxValue(60);
        numberRollMin.setMinValue(0);

        //Sec
        numberRollSec.setMaxValue(60);
        numberRollSec.setMinValue(0);

        //Invisible for Textview
        time.setVisibility(View.INVISIBLE);
        timeProgress.setVisibility(View.INVISIBLE);

        //Invisible Button
        stopAlert.setVisibility(View.INVISIBLE);

        //numRoll Listner for hrs
        numberRollHr.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                hou = numberRollHr.getValue();
            }
        });

        //numRoll Listner for mins
        numberRollMin.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                minu = numberRollMin.getValue();
            }
        });

        //numRoll Listner for mins
        numberRollSec.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                secd = numberRollSec.getValue();
            }
        });

        //*** Button Listners ***//
        //Start listner
        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        //resetGuard set to true
                        resetGuard = true;

                        //Invisible for NumberPicker/TextView
                        numberRollHr.setVisibility(View.INVISIBLE);
                        numberRollMin.setVisibility(View.INVISIBLE);
                        numberRollSec.setVisibility(View.INVISIBLE);
                        textHr.setVisibility(View.INVISIBLE);
                        textMin.setVisibility(View.INVISIBLE);
                        textSec.setVisibility(View.INVISIBLE);
                        time.setVisibility(View.VISIBLE);

                        //Visibility for Button
                        start.setVisibility(View.INVISIBLE);
                        pause.setVisibility(View.VISIBLE);
                        reset.setVisibility(View.VISIBLE);

                        //Visible for ProgressBar
                        timeProgress.setVisibility(View.VISIBLE);

                        hou = hou * 3600000;
                        minu = minu * 60000;
                        secd = secd * 1000;

                        totalTime = hou + minu + secd;
                        timeMax= hou + minu + secd;

                        //******* Setting timer(CHECK BACK AGAIN)
                        if (hou >= 0 || minu >= 0 || secd >= 0) {
                            //CounterClass
                            timer = new CounterClass(totalTime, 1000);
                        }
                        timer.start();
                        hou=minu=secd=0;

                    }
                }

        );

        //pause listner
        pause.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        //Visibility for button
                        pause.setVisibility(View.INVISIBLE);
                        resume.setVisibility(View.VISIBLE);

                        timer.cancel();
                    }
                }

        );

        //resume listner
        resume.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        //Visibility for button
                        pause.setVisibility(View.VISIBLE);
                        resume.setVisibility(View.INVISIBLE);

                        timer = new CounterClass(totalTime, 1000);
                        timer.start();
                    }
                }

        );

        //reset listner
        reset.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if(resetGuard){
                            timer.cancel();
                        }

                        //Visibility for button
                        start.setVisibility(View.VISIBLE);
                        pause.setVisibility(View.INVISIBLE);
                        resume.setVisibility(View.INVISIBLE);
                        reset.setVisibility(View.INVISIBLE);
                        textHr.setVisibility(View.VISIBLE);
                        textMin.setVisibility(View.VISIBLE);
                        textSec.setVisibility(View.VISIBLE);


                        //Empty ProgressBar
                        progressCounter=0;


                        //Invisible for Textview/ProgressBar
                        time.setVisibility(View.INVISIBLE);
                        timeProgress.setVisibility(View.INVISIBLE);

                        //Visible for NumberPicker
                        numberRollHr.setVisibility(View.VISIBLE);
                        numberRollMin.setVisibility(View.VISIBLE);
                        numberRollSec.setVisibility(View.VISIBLE);

                        //Text color
                        time.setTextColor(Color.BLACK);

                        //Notification cancel
                        notimanager.cancel(2);

                        //Set NumberPicker
                        numberRollHr.setValue(0);
                        numberRollMin.setValue(0);
                        numberRollSec.setValue(0);
                    }
                }

        );



        //StopAlert listner
        stopAlert.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        //Visibility for Button
                        stopAlert.setVisibility(View.INVISIBLE);
                        pause.setVisibility(View.INVISIBLE);
                        resume.setVisibility(View.INVISIBLE);
                        start.setVisibility(View.VISIBLE);
                        reset.setVisibility(View.INVISIBLE);

                        //Visibility for NumberPicker/TextView
                        numberRollHr.setVisibility(View.VISIBLE);
                        numberRollMin.setVisibility(View.VISIBLE);
                        numberRollSec.setVisibility(View.VISIBLE);
                        textHr.setVisibility(View.VISIBLE);
                        textMin.setVisibility(View.VISIBLE);
                        textSec.setVisibility(View.VISIBLE);


                        //Delete Notification
                        notimanager.cancel(1);
                        notimanager.cancel(2);

                        //Set NumberPicker
                        numberRollHr.setValue(0);
                        numberRollMin.setValue(0);
                        numberRollSec.setValue(0);


                        //Text Color
                        time.setTextColor(Color.BLACK);

                        playClip.stop();
                    }
                }

        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    /***CountDownClass override***/
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer{

        //Constructor
        public CounterClass(long millisInFuture, long countDownInterval){

            super(millisInFuture, countDownInterval);
        }

        //onFinish
        public void onFinish(){

            time.setTextColor(Color.BLACK);

            //Invisibility for button
            time.setVisibility(View.INVISIBLE);
            timeProgress.setVisibility(View.INVISIBLE);

            //Visibility for button
            stopAlert.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
            resume.setVisibility(View.INVISIBLE);
            start.setVisibility(View.INVISIBLE);
            reset.setVisibility(View.INVISIBLE);

            //Empty ProgressBar
            progressCounter=0;

            //Setting Up AlertSound by User's Preference
            showUserSettings();
            playClip.start();

            //Setting up Notification
            NotificationCompat.Builder notification= new NotificationCompat.Builder(TimerDennis.this)
                    .setSmallIcon(R.drawable.hourglass)
                    .setContentTitle("Time is up!!!")
                    .setContentText("Stop Cooking :)");

            Bitmap picture= BitmapFactory.decodeResource(getResources(), R.drawable.hourglass);
            notification.setLargeIcon(picture);

            //Vibrate
            long [] vibrate= {0,100,200,300};
            notification.setVibrate(vibrate);

            //Notification Action
            PendingIntent myPendingIntent;
            Intent notiIntent = new Intent();
            Context myContext= getApplicationContext();

            notiIntent.setClass(myContext, TimerDennis.class);
            notiIntent.putExtra("ID", 1);
            myPendingIntent= PendingIntent.getActivity(myContext,0,notiIntent,0);

            notification.setContentIntent(myPendingIntent);


            //Put notification in action
            notimanager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notimanager.notify(1,notification.build());
        }

        //onTick
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @SuppressLint("NewApi")
        public void onTick(long millisUntilFinished){
            totalTime= millisUntilFinished;
            String hms= String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
            System.out.print(hms);

            //Progress Bar
            timeProgress=(ProgressBar)findViewById(R.id.progressBar);
            progressCounter+=1000;
            timeProgress.setMax(timeMax);
            timeProgress.setProgress(progressCounter);

            //***Setting up Notification
            NotificationCompat.Builder notiStatus= new NotificationCompat.Builder(TimerDennis.this)
                    .setTicker(hms)
                    .setSmallIcon(R.drawable.hourglass)
                    .setContentTitle("Cooking Done in: " + hms);

            Bitmap picture= BitmapFactory.decodeResource(getResources(), R.drawable.hourglass);
            notiStatus.setLargeIcon(picture);

            //Notification Action
            PendingIntent myPendingIntent;
            Intent notiIntent = new Intent();
            Context myContext= getApplicationContext();

            notiIntent.setClass(myContext, TimerDennis.class);
            notiIntent.putExtra("ID", 2);
            myPendingIntent= PendingIntent.getActivity(myContext,0,notiIntent,0);

            notiStatus.setContentIntent(myPendingIntent);

            //Put notification in action
            notimanager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notimanager.notify(2,notiStatus.build());

            //Text color 5s before time up!!
            if(totalTime<= 11000){
                time.setTextColor(Color.RED);

            }

            time.setText(hms);
        }
    }

    public void changeTones(View view){
        showUserSettings();
    }

    //Setting Up User's favourite Alert Tone
    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String favTone= sharedPrefs.getString("prefSyncFrequency", "");

        switch (favTone){

            case "musicbox":
                playClip = MediaPlayer.create(TimerDennis.this,R.raw.musicbox);
                playClip.setAudioStreamType(AudioManager.STREAM_MUSIC);
                break;
            case "prelude":
                playClip = MediaPlayer.create(TimerDennis.this,R.raw.prelude);
                playClip.setAudioStreamType(AudioManager.STREAM_MUSIC);
                break;
            case "loudalarm":
                playClip = MediaPlayer.create(TimerDennis.this,R.raw.loudalarm);
                playClip.setAudioStreamType(AudioManager.STREAM_MUSIC);
                break;
            case "tornadosiren":
                playClip = MediaPlayer.create(TimerDennis.this,R.raw.tornadosiren);
                playClip.setAudioStreamType(AudioManager.STREAM_MUSIC);
                break;
            default:
                playClip = MediaPlayer.create(TimerDennis.this,R.raw.loudalarm);
                playClip.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer_dennis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        } else if (id == R.id.nav_timer) {

            Intent intent = new Intent(getApplicationContext(), TimerDennis.class);
            startActivity(intent);

        } else if (id == R.id.nav_preferences) {

            Intent intent = new Intent(getApplicationContext(), Preferences.class);
            startActivity(intent);

        } else if (id == R.id.nav_ingredients) {
            Intent intent = new Intent(getApplicationContext(), IngredientsList.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
