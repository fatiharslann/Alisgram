package com.example.alisgram;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Button;

import java.util.Calendar;

public class SaatSec extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int saat = calendar.get(Calendar.HOUR_OF_DAY);
        int dakika = calendar.get(Calendar.MINUTE);

        //return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(), saat, dakika, DateFormat.is24HourFormat(getActivity()));
        return new TimePickerDialog(getActivity(),this, saat, dakika,DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Tanımlar
        TextView saat_text_1 = (TextView) getActivity().findViewById(R.id.saat_text_1);
        TextView saat_text_2 = (TextView) getActivity().findViewById(R.id.saat_text_2);
        TextView saat_text_3 = (TextView) getActivity().findViewById(R.id.saat_text_3);
        Button saat_ekle = getActivity().findViewById(R.id.Saat_Ekle);
        if (saat_text_1.getVisibility() == View.INVISIBLE ) {
            saat_text_1.setVisibility(View.VISIBLE);
            if(hourOfDay < 10 || minute < 10){
                if(hourOfDay < 10){
                    saat_text_1.setText("0"+String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                }
                if(minute < 10){
                    saat_text_1.setText(String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }
                if(hourOfDay <10 && minute < 10){
                    saat_text_1.setText("0"+String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }

            }

        }
        else if ( saat_text_2.getVisibility() == View.INVISIBLE && saat_text_1.getVisibility()==View.VISIBLE){
            if(hourOfDay < 10 || minute <10 ){
                if(hourOfDay < 10){
                    saat_text_2.setText("0"+String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                }
                if(minute < 10){
                    saat_text_2.setText(String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }
                if(hourOfDay <10 && minute < 10){
                    saat_text_2.setText("0"+String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }
            }
            saat_text_2.setVisibility(View.VISIBLE);
            saat_ekle.setX(800);
        }
        else if(saat_text_3.getVisibility()==View.INVISIBLE && saat_text_1.getVisibility()==View.VISIBLE && saat_text_2.getVisibility()==View.VISIBLE){
            if(hourOfDay < 10 || minute <10 ){
                if(hourOfDay < 10){
                    saat_text_3.setText("0"+String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                }
                if(minute < 10){
                    saat_text_3.setText(String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }
                if(hourOfDay <10 && minute < 10){
                    saat_text_3.setText("0"+String.valueOf(hourOfDay) + ":"+"0"+String.valueOf(minute));
                }
            }
            saat_text_3.setVisibility(View.VISIBLE);
            saat_ekle.setClickable(false);
            saat_ekle.setVisibility(View.INVISIBLE);
        }
    }
}
