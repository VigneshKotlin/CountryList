package com.zohotask.app.countrylist.viewmodel;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zohotask.app.CountryListParentActivity;
import com.zohotask.app.R;
import com.zohotask.app.countrylist.adapter.CountryListAdapter;
import com.zohotask.app.countrylist.model.CountryListFinalDataModel;
import com.zohotask.app.countrylist.model.CountryListModel;
import com.zohotask.app.countrylist.view.CountryDetailsScreen;
import com.zohotask.app.databinding.FragmentCountryListBinding;
import com.zohotask.app.roomdb.CountryListDatabase;
import com.zohotask.app.roomdb.CountryListEntity;
import com.zohotask.app.utils.InternetReachability;
import com.zohotask.app.utils.LoadingProgress;
import com.zohotask.app.webengine.RetrofitConfig;
import com.zohotask.app.webengine.WebServiceAPI;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CountryListVM implements CountryListAdapter.OnItemClickListener {

    private Activity activity;
    private LoadingProgress progressDialog;
    private FragmentCountryListBinding binding;
    private List<CountryListModel> CountryListArr;
    private CountryListAdapter adapter;
    private CountryListDatabase db;
    private List<CountryListEntity> countryListFinalDataModel = new ArrayList<>();

    public CountryListVM(Activity activity, FragmentCountryListBinding binding){
        this.activity = activity;
        this.binding = binding;
        CountryListArr = new ArrayList<>();
        progressDialog = new LoadingProgress();
        init();
        callCountryListService();
    }

    private void init(){
        db = Room.databaseBuilder(activity.getApplicationContext(), CountryListDatabase.class, "countryDB")
                .allowMainThreadQueries().build();
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchFilter(s.toString());
            }
        });
    }

    private void callCountryListAPI(){
        if(InternetReachability.getInstance().isNetworkAvailable(activity)){
            progressDialog.showProgressBar(activity);
            WebServiceAPI api = RetrofitConfig.getInstance().createService("https://restcountries.eu/rest/v2/");
            Observable<Response<ResponseBody>> observable = api.getCountryList();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<ResponseBody>>() {
                        @Override
                        public void accept(Response<ResponseBody> responses) throws Exception {
                            progressDialog.dismissProgressBar();
                            String response = responses.body().string();
                            //Parsing the data
                            Type inputType = new TypeToken<List<CountryListModel>>(){}.getType();
                            try {
                                CountryListArr = new Gson().fromJson(response, inputType);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            //Load Local DB
                            loadLocalStorage(CountryListArr);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressDialog.dismissProgressBar();
                            Toast.makeText(activity, activity.getString(R.string.oops_something), Toast.LENGTH_SHORT);
                        }
                    });
        }else {
            progressDialog.dismissProgressBar();
           Toast.makeText(activity, activity.getString(R.string.no_internet), Toast.LENGTH_SHORT);
        }
    }

    private void loadAdapter(List<CountryListEntity> countryArr){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new CountryListAdapter(activity,
                countryArr, this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void searchFilter(String searchText){
        if(CountryListArr != null && adapter != null) {
            List<CountryListEntity> tempArr = new ArrayList<>();
            if (!searchText.isEmpty()) {
                for (CountryListEntity countryListModel : countryListFinalDataModel) {
                    if (countryListModel.getName().toLowerCase().contains(searchText.toLowerCase())) {
                        tempArr.add(countryListModel);
                    }
                }
                adapter.updateList(tempArr);
            } else {
                adapter.updateList(countryListFinalDataModel);
            }
        }
    }

    @Override
    public void onItemClick(CountryListEntity countryListModel) {
        String countryData = new Gson().toJson(countryListModel);
        Bundle bundle = new Bundle();
        //Model to string
        bundle.putString("countryData", countryData);
        Fragment detailFragment= new CountryDetailsScreen();
        detailFragment.setArguments(bundle);
        ((CountryListParentActivity)activity).getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadLocalStorage(List<CountryListModel> CountryListArr){
        for(CountryListModel cl : CountryListArr) {
            CountryListEntity countryList = new CountryListEntity();
            countryList.setFlag(cl.getFlag());
            countryList.setName(cl.getName());
            countryList.setCapital(cl.getCapital());
            countryList.setCurrency(cl.getCurrencies().get(0).getName());
            countryList.setCurrencySymbol(cl.getCurrencies().get(0).getSymbol());
            countryList.setLanguage(cl.getLanguages().get(0).getName());
            countryList.setPopulation(String.valueOf(cl.getPopulation()));
            db.countryDAO().addCountry(countryList);
        }

        countryListFinalDataModel = db.countryDAO().getCountries();
        Log.e("Test CountryList", ">>"+countryListFinalDataModel.size());
        loadAdapter(countryListFinalDataModel);
    }

    public void callCountryListService(){
        List<CountryListEntity> countryList = new ArrayList<>();
        countryList = db.countryDAO().getCountries();
        Log.e("Test CountryList", ">>"+countryList.size());
        if(countryList.size() > 0 ) {
            countryListFinalDataModel.addAll(countryList);
            loadAdapter(countryList);
        }else{
            callCountryListAPI();
        }
    }
}
