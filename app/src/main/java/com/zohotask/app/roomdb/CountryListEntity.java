package com.zohotask.app.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_list")
public class CountryListEntity {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "capital")
    private String capital;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "currency")
    private String currency;
    @ColumnInfo(name = "currency_symbol")
    private String currencySymbol;
    @ColumnInfo(name = "population")
    private String population;
    @ColumnInfo(name = "flag")
    private String flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
