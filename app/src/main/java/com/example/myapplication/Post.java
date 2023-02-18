package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("currencyCodeA")
    @Expose
    private int currencyCodeA;
    @SerializedName("currencyCodeB")
    @Expose
    private int currencyCodeB;
    @SerializedName("date")
    @Expose
    private double date;
    @SerializedName("rateBuy")
    @Expose
    private double rateBuy;
    @SerializedName("rateCross")
    @Expose
    private double rateCross;
    @SerializedName("rateSell")
    @Expose
    private double rateSell;

    public Post() {
    }

    public int getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(int currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public int getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(int currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public double getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(double rateBuy) {
        this.rateBuy = rateBuy;
    }

    public double getRateCross() {
        return rateCross;
    }

    public void setRateCross(double rateCross) {
        this.rateCross = rateCross;
    }

    public double getRateSell() {
        return rateSell;
    }

    public void setRateSell(double rateSell) {
        this.rateSell = rateSell;
    }
}
