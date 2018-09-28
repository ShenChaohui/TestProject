package com.genius.zydl.testproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.CommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.entity.Movie;
import com.genius.zydl.testproject.newwork.NetRequestManagers;

import org.xutils.x;

import java.util.ArrayList;


public class FragmentOne extends Fragment {
    private ListView mListView;
    private Context context;
    private ArrayList<Movie> mMovies;
    private CommonAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        context = getActivity();
        mListView = view.findViewById(R.id.lv_one);
        mMovies = new ArrayList<>();
        mListView.setAdapter(mAdapter = new CommonAdapter<Movie>(context, mMovies, R.layout.item_list_one) {
            @Override
            public void convert(ViewHolder holder, Movie item) {
                x.image().bind((ImageView) holder.getView(R.id.iv_item_one), item.getImages().getLarge());
                holder.setText(R.id.tv_item_one_name, item.getTitle());
                holder.setText(R.id.tv_item_one_year, item.getYear());
                holder.setText(R.id.tv_item_one_average, String.valueOf(item.getRating().getAverage()));
            }
        });
        NetRequestManagers.getMovies(context, 0, 10, mAdapter,mMovies);
        return view;
    }


}
