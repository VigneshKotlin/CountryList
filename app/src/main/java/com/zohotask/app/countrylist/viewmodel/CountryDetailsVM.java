package com.zohotask.app.countrylist.viewmodel;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ahmadrosid.svgloader.SvgLoader;
import com.zohotask.app.CountryListParentActivity;
import com.zohotask.app.R;
import com.zohotask.app.countrylist.model.CountryListModel;
import com.zohotask.app.databinding.FragmentCountryDetailBinding;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CountryDetailsVM {

    private Activity activity;
    private FragmentCountryDetailBinding binding;
    private CountryListModel countryData;

    public CountryDetailsVM(Activity activity, FragmentCountryDetailBinding binding, CountryListModel countryData){
        this.activity = activity;
        this.binding = binding;
        this.countryData = countryData;
        initViews();
        hideKeyBoard();
    }

    private void initViews(){
        binding.capitalTv.setText(countryData.getCapital());
        binding.nameTv.setText(countryData.getName());
        binding.currencyTv.setText(countryData.getCurrencies().get(0).getName()+" - "+countryData.getCurrencies().get(0).getSymbol());
        binding.populationTv.setText(String.valueOf(countryData.getPopulation()));
        if(countryData.getLanguages().size() > 0) {
            binding.languageTv.setText(countryData.getLanguages().get(0).getName());
        }
        SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.drawable.ic_loading, R.drawable.ic_loading)
                .load(countryData.getFlag(), binding.flagImage);
    }

    public void onCloseClick(View v){
        ((CountryListParentActivity)activity).getSupportFragmentManager().popBackStack();
    }

    private void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) ((CountryListParentActivity)activity).getSystemService(INPUT_METHOD_SERVICE);
        View focusedView = ((CountryListParentActivity)activity).getCurrentFocus();
        if(focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(((CountryListParentActivity) activity).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
