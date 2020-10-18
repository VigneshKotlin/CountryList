package com.zohotask.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.zohotask.app.R;

import java.util.Objects;

public class LoadingProgress {
    private ProgressDialog progress;

    public void dismissProgressBar() {
        try {
            if ((progress != null) && progress.isShowing())
                progress.dismiss();
            progress = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressBar(Context context) {
        try {

            if ((progress != null) && progress.isShowing()) {
                return;
            }
            progress = null;

            progress = new ProgressDialog(context);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(progress.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progress.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

            if (!((Activity) context).isFinishing()) {
                progress.show();
            }

            ProgressBar spinner = new ProgressBar(context);
            spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.theme_primary_font_color), PorterDuff.Mode.SRC_IN);
            progress.setContentView(spinner);
            // progress.setMessage("Loading...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            // progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
