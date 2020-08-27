package com.rexontechnologies.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rexontechnologies.popularmovies.utils.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    ArrayList<Movies> arrayList;
    private ListItemClickListener mOnClickListener;
    private final Context mContext;
    private Cursor mCursor;

    public MoviesAdapter(Context context, ListItemClickListener listItemClickListener) {
        this.mOnClickListener = listItemClickListener;
        mContext = context;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movie_list, viewGroup, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //mCursor.moveToPosition(i);
        Movies movies = arrayList.get(i);
        //myViewHolder.iv_movie_image.setImageResource(image[i]);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + movies.getMovieImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .noFade()
                .into(myViewHolder.iv_movie_image);
    }


    @Override
    public int getItemCount() {
        if (null == arrayList) return 0;
        return arrayList.size();
    }

        public void setMovieData(ArrayList<Movies> movies) {
        arrayList = movies;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.iv_movie_image)
        ImageView iv_movie_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }
}
