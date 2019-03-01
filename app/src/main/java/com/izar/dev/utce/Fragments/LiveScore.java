package com.izar.dev.utce.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izar.dev.utce.Adapters.HLVAdapter;
import com.izar.dev.utce.R;
import com.izar.dev.utce.RecyclerAdapter.CO;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


/**
 * Developed by CloudCode
 */
public class LiveScore extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    DatabaseReference myRef;
    ProgressBar mProgressBar;
    ImageView img1,img2;
    TextView P1_name, P2_name,P1_s, P2_s,P1_b, P2_b,P1_4, P2_4,P1_6, P2_6;
    TextView Player1 , Player2, partnership;
    ArrayList<CO> list = new ArrayList<>();
    //FadingTextView Comments;
    TextView Comments;
    private ShimmerFrameLayout mShimmerViewContainer;
    public LiveScore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_live_score, container, false);
        //mShimmerViewContainer = v.findViewById(R.id.shimmer_view_container2);
        //Comments=(TextView) v.findViewById(R.id.textView10);
        mProgressBar =(ProgressBar) v.findViewById(R.id.progress_bar);
        myRef= getInstance().getReference("CO");
        myRef.keepSynced(true);
        P1_name = (TextView) v.findViewById(R.id.textView48);
        P2_name = (TextView) v.findViewById(R.id.textView49);
        P1_s = (TextView) v.findViewById(R.id.textView20);
        P1_b = (TextView) v.findViewById(R.id.textView21);
        P1_4 = (TextView) v.findViewById(R.id.textView22);
        P1_6 = (TextView) v.findViewById(R.id.textView23);
        P2_s = (TextView) v.findViewById(R.id.textView27);
        P2_b = (TextView) v.findViewById(R.id.textView28);
        P2_4 = (TextView) v.findViewById(R.id.textView29);
        P2_6 = (TextView) v.findViewById(R.id.textView30);
        Player1 = (TextView) v.findViewById(R.id.textView14) ;
        Player2 = (TextView) v.findViewById(R.id.textView15);
        img1 =(ImageView) v.findViewById(R.id.imageView4);
        img2 = (ImageView) v.findViewById(R.id.imageView12);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){


                    CO value = dataSnapshot1.getValue(CO.class);
                    list.add(value);

                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        };
        myRef.addValueEventListener(postListener);
        DatabaseReference Refs = FirebaseDatabase.getInstance().getReference("Batsman");
        Refs.keepSynced(true);
        Refs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                String player1_name = dataSnapshot2.child("Player1").child("name").getValue(String.class);
                String player2_name = dataSnapshot2.child("Player2").child("name").getValue(String.class);
                Long player1_b = dataSnapshot2.child("Player1").child("b").getValue(Long.class);
                Long player2_b = dataSnapshot2.child("Player2").child("b").getValue(Long.class);
                Long player1_s = dataSnapshot2.child("Player1").child("s").getValue(Long.class);
                Long player2_s = dataSnapshot2.child("Player2").child("s").getValue(Long.class);
                Long player1_4 = dataSnapshot2.child("Player1").child("4's").getValue(Long.class);
                Long player2_4 = dataSnapshot2.child("Player2").child("4's").getValue(Long.class);
                Long player1_6 = dataSnapshot2.child("Player1").child("6's").getValue(Long.class);
                Long player2_6 = dataSnapshot2.child("Player2").child("6's").getValue(Long.class);
                Long st = dataSnapshot2.child("Player1").child("st").getValue(Long.class);
                Long st2 = dataSnapshot2.child("Player2").child("st").getValue(Long.class);
                if (st == 1){
                    img1.setVisibility(View.VISIBLE);

                }
                else if(st == 0){
                    img1.setVisibility(View.GONE);

                }
                if (st2 == 1){
                    img2.setVisibility(View.VISIBLE);

                }
                else if(st2 == 0){

                    img2.setVisibility(View.GONE);
                }
                Long pt = dataSnapshot2.child("pt").getValue(Long.class);
                P1_name.setText(player1_name);
                P2_name.setText(player2_name);
                P1_s.setText(String.valueOf(player1_s));
                P1_b.setText(String.valueOf(player1_b));
                P1_4.setText(String.valueOf(player1_4));
                P1_6.setText(String.valueOf(player1_6));
                P2_s.setText(String.valueOf(player2_s));
                P2_b.setText(String.valueOf(player2_b));
                P2_4.setText(String.valueOf(player2_4));
                P2_6.setText(String.valueOf(player2_6));
                Player1.setText(player1_s+"("+player1_b+")");
                Player2.setText(player2_s+"("+player2_b+")");
//                    Comments.setText("");
                // Comments.setText(Comments.getText()+comments);

                // mShimmerViewContainer.stopShimmer();
                // mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        int resId = R.anim.layout_animation_fall_down;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);
        //mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HLVAdapter(getContext(), list);

        mRecyclerView.setAdapter(mAdapter);

        return  v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        //  mShimmerViewContainer.stopShimmer();

        super.onPause();
    }

}
