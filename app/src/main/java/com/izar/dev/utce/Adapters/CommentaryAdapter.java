package com.izar.dev.utce.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.izar.dev.utce.R;
import com.izar.dev.utce.RecyclerAdapter.Commentary;
import com.izar.dev.utce.RecyclerAdapter.FeedData;

import java.util.ArrayList;
import java.util.List;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryAdapter.MyViewHolder> {
    private Context mContext;
    private List<Commentary> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,ball;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtName);
            count = (TextView) view.findViewById(R.id.txtDe);
            ball = (TextView) view.findViewById(R.id.textView39);


        }
    }


    public CommentaryAdapter(Context mContext, ArrayList<Commentary> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public CommentaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commentary_item, parent, false);

        return new CommentaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Commentary album = albumList.get(position);
        for (int e =0;e<getItemCount();e++) {
            if (position == e) {

                if(albumList.get(position).c_ball.equals("W")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ1));
                    holder.ball.setTextColor(Color.parseColor("#FF1744"));
                }
                else if(albumList.get(position).c_ball.equals("4")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ2));
                    holder.ball.setTextColor(Color.parseColor("#FF4081"));
                }
                else if(albumList.get(position).c_ball.equals("6")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ3));
                    holder.ball.setTextColor(Color.parseColor("#7986CB"));
                }
                else if(albumList.get(position).c_ball.equals("0")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ4));
                    holder.ball.setTextColor(Color.parseColor("#8D6E63"));
                }
                else if(albumList.get(position).c_ball.equals("Nb")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ5));
                    holder.ball.setTextColor(Color.parseColor("#9E9E9E"));
                }
                else if(albumList.get(position).c_ball.equals("Wd")) {
                    holder.ball.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circ6));
                    holder.ball.setTextColor(Color.parseColor("#546E7A"));
                }
            }
        }
        holder.title.setText("Over "+album.getC_title());
        holder.count.setText(album.getC_desc());
        holder.ball.setText(album.getC_ball());




    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

}
