package com.example.alisgram;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SaatSecGuncelle extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView saat_text_1,saat_text_2,saat_text_3;
    private ImageView saat_sil_1,saat_sil_2,saat_sil_3;

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
        saat_text_1 = (TextView) getActivity().findViewById(R.id.saat_text_1_gunle);
        saat_text_2 = (TextView) getActivity().findViewById(R.id.saat_text_2_gunle);
        saat_text_3 = (TextView) getActivity().findViewById(R.id.saat_text_3_gunle);
        saat_sil_1 = (ImageView) getActivity().findViewById(R.id.saat_sil_1);
        saat_sil_2 = (ImageView) getActivity().findViewById(R.id.saat_sil_2);
        saat_sil_3 = (ImageView) getActivity().findViewById(R.id.saat_sil_3);

        Button saat_ekle = getActivity().findViewById(R.id.Saat_Ekle_gunle);
        if (saat_text_1.getVisibility() == View.INVISIBLE ) {
            saat_text_1.setVisibility(View.VISIBLE);
            saat_sil_1.setVisibility(View.VISIBLE);
            saat_text_1.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            saat_ekle.setClickable(true);

        }
        else if ( saat_text_2.getVisibility() == View.INVISIBLE && saat_text_1.getVisibility()==View.VISIBLE){
            saat_text_2.setVisibility(View.VISIBLE);
            saat_sil_2.setVisibility(View.VISIBLE);
            saat_text_2.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            saat_ekle.setClickable(true);
        }
        else if(saat_text_3.getVisibility()==View.INVISIBLE && saat_text_1.getVisibility()==View.VISIBLE && saat_text_2.getVisibility()==View.VISIBLE){
            saat_text_3.setVisibility(View.VISIBLE);
            saat_sil_3.setVisibility(View.VISIBLE);
            saat_text_3.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            saat_ekle.setClickable(false);
            saat_ekle.setVisibility(View.INVISIBLE);
        }
    }
}
