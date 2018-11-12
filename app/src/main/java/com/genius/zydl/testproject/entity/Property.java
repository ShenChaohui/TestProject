package com.genius.zydl.testproject.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Property {
    @DatabaseField(id = true)
    private String mName;
    @DatabaseField
    private double mMoney;

    public Property() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getMoney() {
        return mMoney;
    }

    public void setMoney(double money) {
        mMoney = money;
    }
}
