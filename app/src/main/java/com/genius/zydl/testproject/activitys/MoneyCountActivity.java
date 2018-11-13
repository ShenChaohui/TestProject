package com.genius.zydl.testproject.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.database.BaseDao;
import com.genius.zydl.testproject.database.BaseDaoImpl;
import com.genius.zydl.testproject.entity.AllPropertyHistory;
import com.genius.zydl.testproject.entity.Property;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoneyCountActivity extends BasicActivity {
    private ListView mListView;
    private TextView mTvAllMoney;
    private LineChart mLineChart;

    private ListViewCommonAdapter adapter;
    private List<Property> mProperties;
    private BaseDao<Property, Integer> mDao;
    private BaseDao<AllPropertyHistory, Integer> mHdao;

    private double mAllMoeny;

    @Override
    protected int getLayout() {
        return R.layout.activity_money_count;
    }

    @Override
    protected void initView() {
        initTitle();
        mTvAllMoney = findViewById(R.id.tv_sum_money);
        mLineChart = findViewById(R.id.lc_history);
        initLineChart();
        getRightButton(R.drawable.ic_add_white_32dp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_property, null);
                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("添加一项资产")
                        .setView(view1)
                        .setCancelable(true)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create();
                dialog.show();
                final TextInputEditText textName = view1.findViewById(R.id.tiet_name);
                final TextInputEditText textMoney = view1.findViewById(R.id.tiet_money);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = textName.getText().toString().trim();
                                String money = textMoney.getText().toString().trim();
                                if (name.length() == 0) {
                                    textName.setError("账户名不能为空");
                                    return;
                                }
                                if (money.length() == 0) {
                                    money = "0";
                                }
                                Property property = new Property();
                                property.setName(name);
                                property.setMoney(Double.valueOf(money));
                                try {
                                    if (mDao.query("mName", name).size() > 0) {
                                        Toast.makeText(context, "该账户以存在", Toast.LENGTH_SHORT).show();
                                    } else {
                                        mDao.save(property);
                                        mProperties.add(property);
                                        setSumMoenyText();
                                        updataHistorySQL();
                                        setLineChartData();
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
        mListView = findViewById(R.id.lv_property);
    }

    @Override
    protected void main() {
        mProperties = new ArrayList<>();
        mDao = new BaseDaoImpl<>(context, Property.class);
        mHdao = new BaseDaoImpl<>(context, AllPropertyHistory.class);
        try {
            mProperties.addAll(mDao.queryAll());
            setSumMoenyText();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new ListViewCommonAdapter<Property>(context, mProperties, R.layout.item_property) {
            @Override
            public void convert(ViewHolder holder, final Property property) {
                holder.setText(R.id.tv_item_property_name, property.getName());
                TextView textView = holder.getView(R.id.tv_item_property_money);
                textView.setText(getTwoDecimal(property.getMoney()));
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Property property = mProperties.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("是否删除" + property.getName() + "账户？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            mDao.delete(property);
                            mProperties.remove(property);
                            setSumMoenyText();
                            updataHistorySQL();
                            setLineChartData();
                            adapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Property property = mProperties.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("修改");
                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_property_revise, null);
                builder.setView(view1);
                TextView tvName = view1.findViewById(R.id.tv_name);
                tvName.setText(property.getName());
                final TextInputEditText textMoney = view1.findViewById(R.id.tiet_money);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String price = textMoney.getText().toString();
                        if (price.length() == 0) {
                            return;
                        }
                        property.setMoney(Double.valueOf(price));
                        setSumMoenyText();
                        try {
                            updataHistorySQL();
                            setLineChartData();
                            mDao.update(property);
                            adapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        setLineChartData();
    }

    private void initLineChart() {
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(false);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        //描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);
        //图例
        Legend legend = mLineChart.getLegend();
        legend.setEnabled(false);

        mLineChart.setNoDataText("暂无数据");
        mLineChart.setScaleYEnabled(false);
    }

    private void setLineChartData() {
        List<AllPropertyHistory> historyList = new ArrayList<>();
        try {
            historyList.addAll(mHdao.queryAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Entry> entries = new ArrayList<>();
        for (int j = 0; j < historyList.size(); j++) {
            Entry entry = new Entry(j, (float) historyList.get(j).getMoney());
            entries.add(entry);
        }
        if (entries.size() > 0) {
            LineDataSet dataSet = new LineDataSet(entries, "label");
            LineData lineData = new LineData(dataSet);
            mLineChart.setData(lineData);
            mLineChart.invalidate();
        }
    }

    private void setSumMoenyText() {
        mAllMoeny = 0;
        for (int i = 0; i < mProperties.size(); i++) {
            mAllMoeny += mProperties.get(i).getMoney();
        }
        mTvAllMoney.setText(getTwoDecimal(mAllMoeny));
    }

    private void updataHistorySQL() throws SQLException {
        AllPropertyHistory allPropertyHistory = new AllPropertyHistory();
        allPropertyHistory.setMoney(mAllMoeny);
        mHdao.save(allPropertyHistory);
    }

    private String getTwoDecimal(double num) {
        if (num == 0) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String format = decimalFormat.format(num);
        return format;
    }
}
