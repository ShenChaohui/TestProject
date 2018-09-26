package com.genius.zydl.testproject.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.CommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.entity.People;

import java.util.ArrayList;


public class FragmentOne extends Fragment {
    private ListView listView;
    private ArrayList<People> peoples;
    private CommonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        listView = view.findViewById(R.id.lv_one);
        initData();
        listView.setAdapter(adapter = new CommonAdapter<People>(getActivity(), peoples, R.layout.item_list_one) {
            @Override
            public void convert(ViewHolder holder, People item) {
                holder.setText(R.id.tv_item_list_one_name, "姓名：" + item.getName());
                holder.setText(R.id.tv_item_list_one_age, "年龄：" + item.getAge());
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final People people = peoples.get(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("提示");
                dialog.setMessage("是否要删除-" + people.getName());
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        peoples.remove(people);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("否", null);
                dialog.show();
                return false;
            }
        });
        return view;
    }

    private void initData() {
        peoples = new ArrayList<>();
        peoples.add(new People("qqq", 20));
        peoples.add(new People("www", 21));
        peoples.add(new People("eee", 22));
        peoples.add(new People("rrr", 23));
        peoples.add(new People("ttt", 25));
        peoples.add(new People("yyy", 27));
        peoples.add(new People("uuu", 26));
        peoples.add(new People("iii", 27));
        peoples.add(new People("ooo", 18));
        peoples.add(new People("ppp", 21));
        peoples.add(new People("zzz", 23));
        peoples.add(new People("xxx", 25));
        peoples.add(new People("ccc", 24));
    }

}
