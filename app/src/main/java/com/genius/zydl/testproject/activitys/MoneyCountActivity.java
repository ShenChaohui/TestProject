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
import com.genius.zydl.testproject.entity.Property;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoneyCountActivity extends BasicActivity {
    private ListView mListView;
    private TextView mTvSumMoney;

    private ListViewCommonAdapter adapter;
    private List<Property> mProperties;
    private BaseDao<Property, Integer> dao;
    private double mSumMoeny;

    @Override
    protected int getLayout() {
        return R.layout.activity_money_count;
    }

    @Override
    protected void initView() {
        initTitle();
        mTvSumMoney = findViewById(R.id.tv_sum_money);
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
                                    if (dao.query("mName", name).size() > 0) {
                                        Toast.makeText(context, "该账户以存在", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dao.save(property);
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
        dao = new BaseDaoImpl<>(context, Property.class);
        try {
            mProperties.addAll(dao.queryAll());
            updataSumMoney();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new ListViewCommonAdapter<Property>(context, mProperties, R.layout.item_property) {
            @Override
            public void convert(ViewHolder holder, final Property item) {
                holder.setText(R.id.tv_item_property_name, item.getName());
                holder.setText(R.id.tv_item_property_money, getTwoDecimal(item.getMoney()));
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
                            dao.delete(property);
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
                        try {
                            dao.update(property);
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

    private void updataSumMoney() throws SQLException {
        mSumMoeny = 0;
        for (int i = 0; i < dao.queryAll().size(); i++) {
            Property property = dao.queryAll().get(i);
            mSumMoeny += property.getMoney();
        }
        mTvSumMoney.setText(getTwoDecimal(mSumMoeny));
    }

    private String getTwoDecimal(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String format = decimalFormat.format(num);
        return format;
    }
}
