package com.genius.zydl.testproject.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class AllPropertyHistory {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private double mMoney;

    public AllPropertyHistory() {

    }

    public double getMoney() {
        return mMoney;
    }

    public void setMoney(double money) {
        mMoney = money;
    }
}
