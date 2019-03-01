package com.izar.dev.utce;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Countdown extends AppCompatActivity {
    TextView days;
    TextView hours;
    TextView mins;
    String data ="";
    long timeInMilliseconds;
    DatabaseReference fbd;
    String dateString;
    FirebaseDatabase database;
    int countdown_status;
    TextView seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        long tm = ms("15:20:00");
        long dt =  milliseconds ("2019-02-28");
        timeInMilliseconds = dt +tm;
        final MediaPlayer mp ;

        mp = MediaPlayer.create(this, R.raw.tone);
        final Intent intent = new Intent(this, MainActivity.class);
        days  = (TextView)findViewById(R.id.day);
        hours = (TextView)findViewById(R.id.hours);
        mins = (TextView)findViewById(R.id.minutes);
        seconds = (TextView)findViewById(R.id.seconds);//172800000
      /*  LocationManager locMan = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        long networkTS = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        dateString = formatter.format(new Date(networkTS));*/
       new CountDownTimer(timeInMilliseconds, 1000) {

            @Override

            public void onTick(long millisUntilFinished) {

                days.setText(TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + "");

                hours.setText((TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))) + "");

                mins.setText((TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + "");

                seconds.setText((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "");
            }

            @Override
            public void onFinish() {
                days.setText("Na");
                hours.setText("Na");
                mins.setText("Na");
                seconds.setText("Na");
                startActivity(intent);
                finish();
            }


        }.start();


        }

    public long milliseconds(String date)
    {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date mDate = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            Date ddate= sdf.parse(formattedDate);

            long timeInMilliseconds = mDate.getTime() - ddate.getTime();

            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    public long ms(String time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try
        {
            Date mDate = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            Date ddate= sdf.parse(formattedDate);
            long timeInMilliseconds = mDate.getTime() - ddate.getTime();
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
