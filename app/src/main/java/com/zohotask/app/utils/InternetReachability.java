package com.zohotask.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetReachability {
    private boolean isReachable = false;

    private static InternetReachability ourInstance = new InternetReachability();

    public static InternetReachability getInstance() {
        if (ourInstance == null)
            ourInstance = new InternetReachability();
        return ourInstance;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
