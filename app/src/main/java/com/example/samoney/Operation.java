package com.example.samoney;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {
    public String _id;
    public String name;
    public Integer sum;
    public String comments;
    public String object;
    public Date date;

    Operation(String name, Integer sum, String comments, String object) {
        this.name = name;
        this.sum = sum;
        this.comments = comments;
        this.object = object;
    }

    Operation(String _id, String name, Integer sum, String comments, String object) {
        this._id = _id;
        this.name = name;
        this.sum = sum;
        this.comments = comments;
        this.object = object;
    }
}


