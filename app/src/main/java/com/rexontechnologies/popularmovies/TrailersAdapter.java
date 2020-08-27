package com.rexontechnologies.popularmovies;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rexontechnologies.popularmovies.utils.Trailer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.MyViewHolder> {
    ArrayList<Trailer> trailers;
    // String name;
    Activity a;

    public TrailersAdapter(Activity a, ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        this.a = a;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_trailers, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        final Trailer trailer = trailers.get(position);
        myViewHolder.tv_trailer.setText("Trailer " + (position + 1));
        myViewHolder.tv_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                try {
                    a.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    a.startActivity(webIntent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (trailers == null) return 0;
        return trailers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trailer)
        TextView tv_trailer;

        @BindView(R.id.iv_trailer)
        ImageView iv_trailer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
