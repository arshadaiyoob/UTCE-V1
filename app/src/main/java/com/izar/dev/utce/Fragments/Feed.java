package com.izar.dev.utce.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izar.dev.utce.Adapters.FeedAdapter;
import com.izar.dev.utce.CustomItemClickListener;
import com.izar.dev.utce.FeedDetails;
import com.izar.dev.utce.R;
import com.izar.dev.utce.RecyclerAdapter.CO;
import com.izar.dev.utce.RecyclerAdapter.FeedData;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feed extends Fragment {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    DatabaseReference myRef;
    RecyclerView.Adapter mAdapter;
    boolean loadingFinished = true;
    boolean redirect = false;
    ArrayList<FeedData> list = new ArrayList<>();
    public Feed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myRef= FirebaseDatabase.getInstance().getReference("feed");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    FeedData value = dataSnapshot1.getValue(FeedData.class);
                    list.add(value);

                    mAdapter.notifyDataSetChanged();

                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        myRef.addValueEventListener(postListener);
        int resId = R.anim.layout_animation_fall_down;

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        View v= inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutAnimation(animation);
        mShimmerViewContainer = v.findViewById(R.id.shimmer_view_container);

        mAdapter = new FeedAdapter(getContext(), list, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                final FeedData fd = list.get(position);
                Intent intent = new Intent(getContext(),FeedDetails.class);
                intent.putExtra("title",fd.getfeedname());
                intent.putExtra("desc",fd.getfulldes());
                intent.putExtra("thumb",fd.getThumbnail());
                intent.putExtra("author",fd.getauthor());
                intent.putExtra("date",fd.getdate());
                getContext().startActivity(intent);
                ((Activity)getContext()).overridePendingTransition(0,0);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);





        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

}
