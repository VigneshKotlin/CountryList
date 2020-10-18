package com.zohotask.app.countrylist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.zohotask.app.R;
import com.zohotask.app.countrylist.viewmodel.CountryListVM;
import com.zohotask.app.databinding.FragmentCountryListBinding;

public class CountryListScreen extends Fragment {
    CountryListVM countryListVM;
    FragmentCountryListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_country_list, container, false);
        if(binding == null){
            binding = FragmentCountryListBinding.bind(root);
        }
        countryListVM = new CountryListVM(getActivity(), binding);
        binding.setCountryListVM(countryListVM);
        return binding.getRoot();
    }
}
