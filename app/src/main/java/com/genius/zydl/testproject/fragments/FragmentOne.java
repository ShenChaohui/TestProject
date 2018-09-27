package com.genius.zydl.testproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.newwork.NetRequestManagers;


public class FragmentOne extends Fragment {
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mListView = view.findViewById(R.id.lv_one);
        NetRequestManagers.getMovies(getActivity(), 0, 100,mListView);
        return view;
    }


}
