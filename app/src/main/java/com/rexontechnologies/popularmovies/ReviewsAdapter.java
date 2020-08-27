package com.rexontechnologies.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rexontechnologies.popularmovies.utils.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    ArrayList<Review> reviews;

    public ReviewsAdapter(DetailActivity detailActivity, ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_reviews, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Review review = reviews.get(position);
        myViewHolder.tv_reviews_author.setText(review.getAuthor());
        myViewHolder.tv_reviews_content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;
        return reviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reviews_author)
        TextView tv_reviews_author;

        @BindView(R.id.tv_reviews_content)
        TextView tv_reviews_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
