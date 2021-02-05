package com.capston.cd_timer_v2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(@NonNull Context context, String message, float size) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.custom_dialog);
        TextView text = findViewById(R.id.customDialog_tv);
        text.setText(message);
        text.setTextSize(size);
    }

    @Override
    public void onBackPressed() {

    }
}
