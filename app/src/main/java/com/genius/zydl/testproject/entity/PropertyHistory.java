package com.genius.zydl.testproject.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class PropertyHistory {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String pId;
    @DatabaseField
    private double mMoney;
    @DatabaseField
    private long mDate;

    public PropertyHistory() {
        mDate = System.currentTimeMillis();
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public double getMoney() {
        return mMoney;
    }

    public void setMoney(double money) {
        mMoney = money;
    }

    public long getDate() {
        return mDate;
    }
}
