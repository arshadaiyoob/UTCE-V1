package com.izar.dev.utce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izar.dev.utce.Fragments.Feed;
import com.izar.dev.utce.Fragments.LiveScore;
import com.izar.dev.utce.RecyclerAdapter.CO;
import com.izar.dev.utce.Adapters.HLVAdapter;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static com.izar.dev.utce.R.layout.*;

public class MainActivity extends AppCompatActivity{
    static boolean calledAlready = false;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Context ctx;
    ImageView im;
    TextView runs;
    TextView overs;
    TextView away_runs;
    TextView away_overs;
    boolean isConnected;
    TextView runRate;
    String ss;
    double runrate;
    TextView Comments;
    ArrayList<CO> list = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    LiveScore live = new LiveScore();
                    replaceFragment(live);
                    return true;
                case R.id.navigation_dashboard: Feed feed = new Feed();
                   replaceFragment(feed);
                    return true;
                case R.id.navigation_team:
                    return true;
                case R.id.navigation_notifications:
                   Intent myIntent = new Intent(MainActivity.this, Stream.class);
                   MainActivity.this.startActivity(myIntent);
                   // overridePendingTransition(0, 0);

            }
            return false;
        }
    };

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        Utils.getDatabase();
        database = FirebaseDatabase.getInstance();
        runs=(TextView) findViewById(R.id.textView40);
        overs=(TextView) findViewById(R.id.textView41);
        im =(ImageView) findViewById(R.id.imageView2);
        Comments=(TextView) findViewById(R.id.textView46);
        away_runs=(TextView)findViewById(R.id.textView44);
        away_overs=(TextView)findViewById(R.id.textView45);
        myRef= database.getReference("CO");
        myRef.keepSynced(true);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        DatabaseReference connectedRef = database.getReference(".info/connected");
        DatabaseReference Refs = database.getReference("score");
        Refs.keepSynced(true);
        Refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                Long Runs = dataSnapshot2.child("runs").getValue(Long.class);
                Long Wickets=dataSnapshot2.child("wickets").getValue(Long.class);
                Long Overs=dataSnapshot2.child("overs").getValue(Long.class);
                Long Balls=dataSnapshot2.child("balls").getValue(Long.class);
                Long away_Runs = dataSnapshot2.child("awayrun").getValue(Long.class);
                Long away_Wickets=dataSnapshot2.child("awaywkt").getValue(Long.class);
                Long away_Overs=dataSnapshot2.child("awayovers").getValue(Long.class);
                Long away_Balls=dataSnapshot2.child("awayballs").getValue(Long.class);
                String.format("%.2f", runrate);
                String comments=dataSnapshot2.child("comments").getValue(String.class);
                Comments.setText(comments);
                String sd=dataSnapshot2.child("status").getValue(String.class);
                runs.setText(Runs + "-" + Wickets);
                away_runs.setText(away_Runs + "-" + away_Wickets);
                Long ballsMod=Balls%6;
//                Long bls = away_Balls%6;
             //   away_overs.setText(away_Overs + "." + bls+" Ovr");
                overs.setText(Overs + "." + ballsMod+" Ovr");


                if(sd.equals("t")){

                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                   im.setVisibility(View.VISIBLE);
                } else {
                  im.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(MainActivity.this, "Listener was cancelled",
                        Toast.LENGTH_LONG).show();
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        LiveScore androidFragment = new LiveScore();
        this.setDefaultFragment(androidFragment);

    }

  private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment)
    {
       // int resId = R.anim.layout_animation_fall_down;

       // LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.item_animation_from_bottom, 0);

        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout, destFragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isConnected == false){

            Alerter.create(this)
                    .setIcon(R.drawable.nos)
                    .setTitle("No Internet!")
                    .setText("Enable Internet for live Feed!")
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .show();
        }
    }
}
