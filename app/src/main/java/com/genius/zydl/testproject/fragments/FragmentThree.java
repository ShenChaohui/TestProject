package com.genius.zydl.testproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.activitys.MainActivity;
import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.entity.Movie;
import com.genius.zydl.testproject.newwork.NetRequestManagers;

import org.xutils.x;

import java.util.ArrayList;


public class FragmentThree extends Fragment {
    private Context context;
    private Spinner mSpinner1, mSpinner2, mSpinner3;
    private ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    private ArrayAdapter<String> cityAdapter = null;    //地级适配器
    private ArrayAdapter<String> countyAdapter = null;
    private int provincePosition = 3;
    //省级选项值
    private String[] province = new String[]{"北京", "上海", "天津", "广东"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    private String[][] city = new String[][]
            {
                    {"北京市"},
                    {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"},
                    {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区"},
                    {"广州", "深圳", "韶关"}
            };

    //县级选项值
    private String[][][] county = new String[][][]
            {
                    {   //北京
                            {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
                                    "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
                                    "延庆县"}
                    },
                    {    //上海
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //天津
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //广东
                            {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
                            {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
                            {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"}  //韶关
                    }
            };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        context = getActivity();
        mSpinner1 = view.findViewById(R.id.sp1);
        mSpinner2 = view.findViewById(R.id.sp2);
        mSpinner3 = view.findViewById(R.id.sp3);
        setSpinner();
        return view;
    }

    private void setSpinner() {
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, province);
        mSpinner1.setAdapter(provinceAdapter);
        mSpinner1.setSelection(3, true);
        cityAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, city[3]);
        mSpinner2.setAdapter(cityAdapter);
        mSpinner2.setSelection(0, true);  //默认选中第0个

        countyAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, county[3][0]);
        mSpinner3.setAdapter(countyAdapter);
        mSpinner3.setSelection(0, true);

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityAdapter = new ArrayAdapter<String>(
                        context, android.R.layout.simple_spinner_item, city[i]);
                // 设置二级下拉列表的选项内容适配器
                mSpinner2.setAdapter(cityAdapter);
                provincePosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countyAdapter = new ArrayAdapter<String>(
                        context, android.R.layout.simple_spinner_item, county[provincePosition][i]);
                // 设置二级下拉列表的选项内容适配器
                mSpinner3.setAdapter(countyAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
