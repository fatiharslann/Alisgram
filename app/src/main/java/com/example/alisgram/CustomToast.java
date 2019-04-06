package com.example.alisgram;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

    public static final int NEGATIVE = -1;
    public static final int POZITIVE = 1;
    public static final int ZERO = 0;

    public static void show(Activity activity, int horizontalMargin, int verticalMargin, String konum) {
        Toast toast = Toast.makeText(activity, konum, Toast.LENGTH_LONG);
        toast.setMargin(horizontalMargin, verticalMargin);
        toast.show();
    }

    public static void show(Activity activity, int gravity, int horizontalMargin, int verticalMargin, String konum) {
        Toast toast = Toast.makeText(activity, konum, Toast.LENGTH_LONG);
        toast.setGravity(gravity, horizontalMargin, verticalMargin);
        toast.show();
    }

    private static void show(Activity context, int layout, String text, int gravity) {
        Toast toast = new Toast(context);
        View custom_toast_view = context.getLayoutInflater().inflate(layout, null);
        toast.setGravity(gravity, Gravity.CENTER_VERTICAL, 25);
        toast.setMargin(ZERO, ZERO);
        TextView tvTitle = custom_toast_view.findViewById(R.id.tvTitle);
        tvTitle.setText(text != null ? text : tvTitle.getText());

        toast.setView(custom_toast_view);
        toast.show();
    }


    public static void warning(Activity activity) {
        //show(activity, R.layout.custom_toast_warning, null, Gravity.BOTTOM);
    }

    public static void error(Activity activity) {
        show(activity, R.layout.custom_toast_error, null, Gravity.BOTTOM);
    }

    public static void successful(Activity activity) {
        show(activity, R.layout.custom_toast_successful, null, Gravity.BOTTOM);
    }
}
