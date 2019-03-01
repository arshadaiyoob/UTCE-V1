package com.izar.dev.utce;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.izar.dev.utce.Fragments.StreamActivity;

public class Stream extends AppCompatActivity {
    private static final String TAG = Stream.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    String videoid = "";
    private YouTubePlayer youTubePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        StreamActivity androidFragment = new StreamActivity ();
        this.setDefaultFragment(androidFragment);
        fetchLiveLink();



    }

    private void fetchLiveLink() {
        long cacheExpiration = 3600;

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                            videoid = mFirebaseRemoteConfig.getString("livelink");
                            initializeYoutubePlayer();
                        }
                    }
                });
    }
    private void initializeYoutubePlayer() {

        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);

        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize("AIzaSyA73DkcGQ5m50baIMerqiK__XjC4eoDcjM", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo(videoid);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG, "Youtube Player View initialization failed");
            }

        });
    }
    private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dynamic_fragment_frame_layout2, destFragment);
        fragmentTransaction.commit();
    }



}
