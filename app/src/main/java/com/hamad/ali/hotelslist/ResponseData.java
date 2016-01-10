package com.hamad.ali.hotelslist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 1/9/2016.
 *
 * this is class will hold the hotels list back from the json url
 */
public class ResponseData {

    List<Hotel> data;

    public List<Hotel> getData() {
        return data;
    }

    public void setData(List<Hotel> data) {
        this.data = data;
    }
}
