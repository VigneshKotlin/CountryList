package com.zohotask.app.countrylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryListResponseModel {
    @SerializedName("master")
    @Expose
    List<CountryListModel> countryListModelList;

    public List<CountryListModel> getCountryListModelList() {
        return countryListModelList;
    }

    public void setCountryListModelList(List<CountryListModel> countryListModelList) {
        this.countryListModelList = countryListModelList;
    }
}
