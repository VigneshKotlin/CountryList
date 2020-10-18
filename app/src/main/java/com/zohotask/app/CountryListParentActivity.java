package com.zohotask.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.zohotask.app.countrylist.view.CountryListScreen;
import com.zohotask.app.utils.InternetReachability;
import com.zohotask.app.utils.LoadingProgress;
import com.zohotask.app.weather.model.WeatherResponseModel;
import com.zohotask.app.weather.view.WeatherDialog;
import com.zohotask.app.webengine.RetrofitConfig;
import com.zohotask.app.webengine.WebServiceAPI;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CountryListParentActivity extends AppCompatActivity {
    private Fragment countryListFragment;
    private ImageView weatherImageView;
    private FrameLayout frameLayout;
    private LoadingProgress progressDialog;
    private FusedLocationProviderClient client;
    private String lat = "", lng = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);
        weatherImageView = findViewById(R.id.weather);
        countryListFragment = new CountryListScreen();
        //Check location permission
        requestPermission();
        //Get Lat lng
        getCurrentLocation();
        //Initialize the fragment
        loadFragment();

        weatherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWeatherAPI();
            }
        });
    }

    private void getCurrentLocation(){
        client = LocationServices.getFusedLocationProviderClient(this);
        //Check location permission
        if (ActivityCompat.checkSelfPermission(CountryListParentActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(CountryListParentActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());
                }
            }
        });
    }

    private void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, countryListFragment);
        fragmentTransaction.commit();
    }

    private void callWeatherAPI(){
        progressDialog = new LoadingProgress();
        String url = "https://rapidapi.p.rapidapi.com/";
        if(InternetReachability.getInstance().isNetworkAvailable(CountryListParentActivity.this)){
            progressDialog.showProgressBar(CountryListParentActivity.this);
            WebServiceAPI api = RetrofitConfig.getInstance().createServiceWithHeader(url);
            Observable<Response<ResponseBody>> observable = api.getWeatherDetail("chennai", lat, lng, "en");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<ResponseBody>>() {
                        @Override
                        public void accept(Response<ResponseBody> responses) throws Exception {
                            progressDialog.dismissProgressBar();
                            //String response
                            String response = responses.body().string();
                            //Log.e("Main",">>"+response);
                            //Parsing string responbse to model class
                            WeatherResponseModel responseModel = new Gson().fromJson(response, WeatherResponseModel.class);
                            Double degreeCelsius = responseModel.getMain().getTemp() - 273.15;
                            WeatherDialog.getInstance().showAlert(CountryListParentActivity.this, String.valueOf(degreeCelsius),
                                    responseModel.getWeather().get(0).getMain(), String.valueOf(responseModel.getMain().getHumidity()));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressDialog.dismissProgressBar();
                            Toast.makeText(CountryListParentActivity.this, getString(R.string.oops_something), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            progressDialog.dismissProgressBar();
            Toast.makeText(CountryListParentActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    //Run time permission
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

}