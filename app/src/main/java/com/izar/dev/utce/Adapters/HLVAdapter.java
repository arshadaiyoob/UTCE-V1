package com.izar.dev.utce.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.izar.dev.utce.ItemClickListener;
import com.izar.dev.utce.MainActivity;
import com.izar.dev.utce.R;
import com.izar.dev.utce.RecyclerAdapter.CO;

import java.util.ArrayList;
import java.util.List;

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

    List<CO> alName;

    Context context;

    public HLVAdapter(Context context, ArrayList<CO> alName) {
        super();
        this.context = context;
        this.alName = alName;

        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CO mylist = alName.get(i);
        Drawable tempDrawable = context.getResources().getDrawable(R.drawable.circ);
        LayerDrawable bubble = (LayerDrawable) tempDrawable;
        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.id.outerRectangle);
        int c =6;


for (int e =0;e<getItemCount();e++) {
    if (i == e) {

        if(alName.get(i).b1.equals("W")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ1));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#FF1744"));
        }
        else if(alName.get(i).b1.equals("4")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ2));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#FF4081"));
        }
        else if(alName.get(i).b1.equals("6")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ3));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#7986CB"));
        }
        else if(alName.get(i).b1.equals("0")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ4));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#8D6E63"));
        }
        else if(alName.get(i).b1.equals("NB")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ5));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#9E9E9E"));
        }
        else if(alName.get(i).b1.equals("WD")) {
            viewHolder.tvSpecies.setBackground(ContextCompat.getDrawable(context, R.drawable.circ6));
            viewHolder.tvSpecies.setTextColor(Color.parseColor("#546E7A"));
        }
    }
}
        viewHolder.tvSpecies.setText(mylist.getb1());


    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(alName.size()==0){

                arr = 0;

            }
            else{

                arr=alName.size();

            }



        }catch (Exception e){

        System.out.printf(e.toString());

        }

        return arr;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {


        public TextView tvSpecies;


        public ViewHolder(View itemView) {
            super(itemView);

            tvSpecies = (TextView) itemView.findViewById(R.id.circle1);

        }




    }

}