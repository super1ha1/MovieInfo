package com.example.khue.movieinfo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preference manager to hold data
 */
public class PreferenceManager {
    int page, total_pages, total_results;
    String maximumDate, minimumDate;
    String api_key;
    SharedPreferences pref;

    public PreferenceManager(Context context) {
        super();
        pref = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
        api_key = "a3d00aa285158eb87a869a5806fad9ba";
        page = 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        pref.edit().putInt("page", page).commit();
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
        pref.edit().putInt("total_pages", total_pages).commit();
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
        pref.edit().putInt("total_results", total_results).commit();

    }

    public String getMaximumDate() {
        return maximumDate;
    }

    public void setMaximumDate(String maximumDate) {
        this.maximumDate = maximumDate;
        pref.edit().putString("maximumDate", maximumDate).commit();

    }

    public String getMinimumDate() {
        return minimumDate;
    }

    public void setMinimumDate(String minimumDate) {
        this.minimumDate = minimumDate;
        pref.edit().putString("minimumDate", minimumDate).commit();
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
        pref.edit().putString("api_key", api_key).commit();
    }
}
