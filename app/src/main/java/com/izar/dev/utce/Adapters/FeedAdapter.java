package com.izar.dev.utce.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.izar.dev.utce.CustomItemClickListener;
import com.izar.dev.utce.FeedDetails;
import com.izar.dev.utce.RecyclerAdapter.Commentary;
import com.izar.dev.utce.RecyclerAdapter.FeedData;
import com.izar.dev.utce.R;

import java.util.ArrayList;
import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context mContext;
    private List<FeedData> albumList;
    CustomItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public FeedAdapter(Context mContext, ArrayList<FeedData> albumList,CustomItemClickListener listener) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_card, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FeedData album = albumList.get(position);
        holder.title.setText(album.getfeedname());
        holder.count.setText(album.getshortdes());


        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext,FeedDetails.class);
                intent.putExtra("title",album.getfeedname());
                intent.putExtra("desc",album.getfulldes());
                intent.putExtra("thumb",album.getThumbnail());
                intent.putExtra("author",album.getauthor());
                intent.putExtra("date",album.getdate());
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(0,0);

            }
        });*/


    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
}