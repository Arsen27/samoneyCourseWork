package com.example.samoney;

import java.io.Serializable;

public class Limit implements Serializable {
    String _id;
    String name;
    Integer sum;
    String object;

    Limit(String _id, String name, Integer sum, String object) {
        this._id = _id;
        this.name = name;
        this.sum = sum;
        this.object = object;
    }

    Limit(Integer sum, String name, String object) {
        this.name = name;
        this.sum = sum;
        this.object = object;
    }
}
