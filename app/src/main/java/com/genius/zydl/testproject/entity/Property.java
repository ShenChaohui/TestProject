package com.genius.zydl.testproject.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable
public class Property {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String mName;
    @DatabaseField
    private double mMoney;

    public Property() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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
