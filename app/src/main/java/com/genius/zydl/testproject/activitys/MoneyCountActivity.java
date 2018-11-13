package com.genius.zydl.testproject.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
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
import com.genius.zydl.testproject.entity.Property;
import com.genius.zydl.testproject.entity.PropertyHistory;
import com.genius.zydl.testproject.utils.TimeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoneyCountActivity extends BasicActivity {
    private ListView mListView;
    private TextView mTvSumMoney;
    private LineChart mLcHistory;

    private ListViewCommonAdapter adapter;
    private List<Property> mProperties;
    private BaseDao<Property, Integer> mDao;
    private BaseDao<PropertyHistory, Integer> mHdao;

    private double mSumMoeny;

    @Override
    protected int getLayout() {
        return R.layout.activity_money_count;
    }

    @Override
    protected void initView() {
        initTitle();
        mTvSumMoney = findViewById(R.id.tv_sum_money);
        mLcHistory = findViewById(R.id.lc_history);
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
                                        addToHistorySQL(property);
                                        mProperties.add(property);
                                        adapter.notifyDataSetChanged();
                                        updataSumMoney();
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
        mHdao = new BaseDaoImpl<>(context, PropertyHistory.class);
        try {
            mProperties.addAll(mDao.queryAll());
            updataSumMoney();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new ListViewCommonAdapter<Property>(context, mProperties, R.layout.item_property) {
            @Override
            public void convert(ViewHolder holder, final Property property) {
                holder.setText(R.id.tv_item_property_name, property.getName());
                TextView textView = holder.getView(R.id.tv_item_property_money);
                textView.setText(getTwoDecimal(property.getMoney()));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                                try {
                                    mDao.update(property);
                                    addToHistorySQL(property);
                                    ShowHistoryData(property);
                                    adapter.notifyDataSetChanged();
                                    updataSumMoney();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        builder.show();
                    }
                });
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
                            mHdao.delete(mHdao.queryT("pId", property.getId()));
                            mProperties.remove(property);
                            adapter.notifyDataSetChanged();
                            updataSumMoney();
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
                ShowHistoryData(mProperties.get(i));
            }
        });
        try {
            if (mDao.count() > 0) {
                ShowHistoryData(mDao.queryAll().get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initLineChart() {
        //设置样式
        YAxis rightAxis = mLcHistory.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        YAxis leftAxis = mLcHistory.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        //设置x轴
        XAxis xAxis = mLcHistory.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        //隐藏图例
        Legend legend = mLcHistory.getLegend();
        legend.setEnabled(false);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mLcHistory.setDescription(description);

        mLcHistory.setScaleYEnabled(false);
        mLcHistory.setNoDataText("暂无数据");
    }

    private void setOrUpdataLineChart(final List<PropertyHistory> historyList) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < historyList.size(); i++) {
            Entry entry = new Entry(i, (float) historyList.get(i).getMoney());
            entries.add(entry);
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(Color.parseColor("#CD8162"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#CD6839"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        XAxis xAxis = mLcHistory.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index >= historyList.size()) {
                    return "";
                } else {
                    return TimeUtil.getCurrentTime(historyList.get((int) value).getDate());
                }
            }
        });
        LineData lineData = new LineData(dataSet);
        mLcHistory.setData(lineData);
        mLcHistory.invalidate();
    }

    private void ShowHistoryData(Property property) {
        try {
            List<PropertyHistory> historyList = mHdao.query("pId", property.getId());
            setOrUpdataLineChart(historyList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updataSumMoney() throws SQLException {
        mSumMoeny = 0;
        for (int i = 0; i < mDao.queryAll().size(); i++) {
            Property property = mDao.queryAll().get(i);
            mSumMoeny += property.getMoney();
        }
        mTvSumMoney.setText(getTwoDecimal(mSumMoeny));
    }

    private void addToHistorySQL(Property property) throws SQLException {
        PropertyHistory propertyHistory = new PropertyHistory();
        propertyHistory.setpId(property.getId());
        propertyHistory.setMoney(property.getMoney());
        mHdao.save(propertyHistory);
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
