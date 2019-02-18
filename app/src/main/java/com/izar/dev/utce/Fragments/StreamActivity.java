package com.izar.dev.utce.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izar.dev.utce.Adapters.CommentaryAdapter;
import com.izar.dev.utce.Adapters.FeedAdapter;
import com.izar.dev.utce.R;
import com.izar.dev.utce.RecyclerAdapter.Commentary;
import com.izar.dev.utce.RecyclerAdapter.FeedData;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StreamActivity extends Fragment {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    DatabaseReference myRef;
    RecyclerView.Adapter mAdapter;
    ArrayList<Commentary> list = new ArrayList<>();

    public StreamActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myRef= FirebaseDatabase.getInstance().getReference("commentry");
        myRef.keepSynced(true);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    Commentary value = dataSnapshot1.getValue(Commentary.class);
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

        View v= inflater.inflate(R.layout.fragment_stream, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view3);
        mShimmerViewContainer = v.findViewById(R.id.shimmer_view_container3);

        mAdapter = new CommentaryAdapter(getContext(), list);

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
