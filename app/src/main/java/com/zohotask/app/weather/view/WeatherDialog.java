package com.zohotask.app.weather.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zohotask.app.R;


public class WeatherDialog {
    @SuppressLint("StaticFieldLeak")
    private static Context m_Context;
    @SuppressLint("StaticFieldLeak")
    private static WeatherDialog showAlertDialog;
    AlertDialog alert;

    public static WeatherDialog getInstance() {
        if (showAlertDialog == null) {
            showAlertDialog = new WeatherDialog();
        }
        return showAlertDialog;
    }

    public void showAlert(Activity activity, String tempVal, String weatherVal, String humidityVal) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_weather, null);
        builder.setView(dialogView);

        TextView tempTv = dialogView.findViewById(R.id.tempTV);
        TextView weatherTv = dialogView.findViewById(R.id.weatherTV);
        TextView humidityTv = dialogView.findViewById(R.id.humidityTV);
        ImageView weatherImage = dialogView.findViewById(R.id.weatherIcon);
        Button closeBtn = dialogView.findViewById(R.id.cancelButton);

        builder.setCancelable(false);
        if(weatherVal.toLowerCase().equals("rain")){
            weatherImage.setImageResource(R.drawable.ic_rain);
        }else if(weatherVal.toLowerCase().equals("mist")){
            weatherImage.setImageResource(R.drawable.ic_clouds);
        }else{
            weatherImage.setImageResource(R.drawable.ic_sun);
        }
        tempTv.setText(tempVal+" \u2103");
        weatherTv.setText(weatherVal);
        humidityTv.setText(humidityVal);

        alert = builder.create();
        if (alert.getWindow() != null)
            alert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        alert.show();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

    }

}
