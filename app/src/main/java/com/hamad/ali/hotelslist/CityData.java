package com.hamad.ali.hotelslist;

import java.util.List;

/**
 * Created by Ali on 1/9/2016.
 *
 * this is class will hold city object which holds the hotels list back from the json url
 */
public class CityData {

    private ResponseData city;

    public ResponseData getCity() {
        return city;
    }

    public void setCity(ResponseData city) {
        this.city = city;
    }
}
