package com.zohotask.app.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponseModel {
    @SerializedName("coord")
    @Expose
    private CoordModel coord;
    @SerializedName("weather")
    @Expose
    private List<WeatherModel> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private MainModel main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private WindModel wind;
    @SerializedName("rain")
    @Expose
    private RainModel rain;
    @SerializedName("clouds")
    @Expose
    private CloudsModel clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private SysModel sys;
    @SerializedName("timezone")
    @Expose
    private Integer timezone;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    public CoordModel getCoord() {
        return coord;
    }

    public void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainModel getMain() {
        return main;
    }

    public void setMain(MainModel main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindModel getWind() {
        return wind;
    }

    public void setWind(WindModel wind) {
        this.wind = wind;
    }

    public RainModel getRain() {
        return rain;
    }

    public void setRain(RainModel rain) {
        this.rain = rain;
    }

    public CloudsModel getClouds() {
        return clouds;
    }

    public void setClouds(CloudsModel clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public SysModel getSys() {
        return sys;
    }

    public void setSys(SysModel sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
}
