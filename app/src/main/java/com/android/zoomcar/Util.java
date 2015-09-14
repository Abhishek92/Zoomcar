package com.android.zoomcar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hp pc on 11-07-2015.
 */
public class Util {


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    public static void datePickerDialog(Context context, final EditText et, int setMaxDate) {
        Calendar calendar = Calendar.getInstance();
        if(!TextUtils.isEmpty(et.getText().toString())){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = df.parse(et.getText().toString());
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        DatePickerDialog dateDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String temp = year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth;

                        et.setText(temp);


                    }

                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 0);
        dateDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        dateDialog.show();
        dateDialog.setCancelable(false);

    }

}
