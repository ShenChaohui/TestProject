package com.genius.zydl.testproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.entity.Book;

import java.util.ArrayList;


public class FragmentTwo extends Fragment {
    private ListView listView;
    private ArrayList<Book> books;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        listView = view.findViewById(R.id.lv_two);
        initData();
        listView.setAdapter(new ListViewCommonAdapter<Book>(getActivity(), books, R.layout.item_list_two) {
            @Override
            public void convert(ViewHolder holder, final Book item) {
                holder.setText(R.id.tv_item_list_two_name, "书名：" + item.getName());
                holder.setText(R.id.tv_item_list_two_price, "价格：" + item.getPrice());
                holder.setText(R.id.tv_item_list_two_num, "数量：" + item.getNum());
                holder.getView(R.id.tv_item_list_two_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;

    }

    private void initData() {
        books = new ArrayList<>();
        books.add(new Book("三国", 99.9f, 11));
        books.add(new Book("水浒", 99.9f, 17));
        books.add(new Book("西游", 69.9f, 15));
        books.add(new Book("红楼", 79.9f, 15));
        books.add(new Book("斗破", 29.9f, 12));
        books.add(new Book("盗墓", 39.9f, 10));
        books.add(new Book("鬼吹灯", 89.9f, 13));
        books.add(new Book("坏蛋", 89.9f, 13));
        books.add(new Book("天蚕", 89.9f, 13));
    }
}
