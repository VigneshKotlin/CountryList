package com.zohotask.app.countrylist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.zohotask.app.R;
import com.zohotask.app.countrylist.model.CountryListModel;
import com.zohotask.app.countrylist.viewmodel.CountryDetailsVM;
import com.zohotask.app.databinding.FragmentCountryDetailBinding;

public class CountryDetailsScreen  extends Fragment {
    private FragmentCountryDetailBinding binding;
    private CountryDetailsVM countryDetailsVM;
    private String countryDetailString;
    private CountryListModel countryListModel = new CountryListModel();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_country_detail, container, false);
        if(binding == null){
            binding = FragmentCountryDetailBinding.bind(root);
        }
        if(getArguments() != null) {
            countryDetailString = getArguments().getString("countryData");
            countryListModel = new Gson().fromJson(countryDetailString, CountryListModel.class);
        }
        countryDetailsVM = new CountryDetailsVM(getActivity(), binding, countryListModel);
        binding.setCountryDetailVM(countryDetailsVM);
        return binding.getRoot();
    }
}
