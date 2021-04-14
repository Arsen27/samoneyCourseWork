package com.example.samoney;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Bill implements Serializable {
    public String _id;
    public String name;
    public String category;
    public Integer balance;
    public Integer from;

    Bill(String name, String category, Integer balance, Integer from) {
        this.name = name;
        this.category = category;
        this.balance = balance;
        this.from = from;
    }

    Bill(String _id, String name, String category, Integer balance, Integer from) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.balance = balance;
        this.from = from;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
