package com.hamad.ali.hotelslist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ali on 1/9/2016.
 */
public class Hotel {

    @SerializedName("hotel_name_en")
    private String name;
    @SerializedName("distance_from_attarction")
    private String distance;
    private int stars;
    private double avgRate;
    @SerializedName("is_cardless")
    private boolean is_cardLess;
    private boolean pre_paid;
    private boolean post_paid;
    private String coverImage;

    public Hotel(String coverImage, String name, String distance, int stars, double avgRate, boolean is_cardLess, boolean pre_paid, boolean post_paid) {
        this.coverImage = coverImage;
        this.name = name;
        this.distance = distance;
        this.stars = stars;
        this.avgRate = avgRate;
        this.is_cardLess = is_cardLess;
        this.pre_paid = pre_paid;
        this.post_paid = post_paid;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public boolean is_cardLess() {
        return is_cardLess;
    }

    public void setIs_cardLess(boolean is_cardLess) {
        this.is_cardLess = is_cardLess;
    }

    public boolean isPre_paid() {
        return pre_paid;
    }

    public void setPre_paid(boolean pre_paid) {
        this.pre_paid = pre_paid;
    }

    public boolean isPost_paid() {
        return post_paid;
    }

    public void setPost_paid(boolean post_paid) {
        this.post_paid = post_paid;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
