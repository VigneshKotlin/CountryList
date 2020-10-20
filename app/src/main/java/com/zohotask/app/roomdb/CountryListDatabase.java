package com.zohotask.app.roomdb;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = CountryListEntity.class, version = 1)
public abstract class CountryListDatabase extends RoomDatabase {
    public abstract  CountryDAO countryDAO();
}
