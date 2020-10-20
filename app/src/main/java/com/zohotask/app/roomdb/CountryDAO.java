package com.zohotask.app.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CountryDAO {

    @Insert(onConflict = IGNORE)
    public void addCountry(CountryListEntity countryListEntity);

    @Query("select * from country_list")
    public List<CountryListEntity> getCountries();

    @Delete
    public  void deleteCountries(CountryListEntity countryListEntity);

    @Update(onConflict = REPLACE)
    public void updateCountry(CountryListEntity countryListEntity);
}
