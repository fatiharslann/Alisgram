package com.example.alisgram;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;


public class FragmentEkle extends Fragment implements TimePickerDialog.OnTimeSetListener {
    MaterialSpinner Kategori_spinner,Alt_Kategori_spinner,Hatirlatici_spinner;
    MaterialEditText Detay_edit,Etiket_edit;
    Button Saat_Ekle;
    TextView saat_goster;
    Switch hatirlatici_switch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_ekle, container, false);
    }

    private void tanimla() {

        Alt_Kategori_spinner = (MaterialSpinner) getActivity().findViewById(R.id.Alt_Kategori_spinner);
        Alt_Kategori_spinner.setItems("0"," 1", "2", "3", "4", "5");
        Alt_Kategori_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Toast.makeText(FragmentEkle.this, item, Toast.LENGTH_SHORT).show();
            }
        });

        Kategori_spinner = (MaterialSpinner) getActivity().findViewById(R.id.Kategori_spinner);
        Kategori_spinner.setItems("0"," 1", "2", "3", "4", "5");
        Kategori_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });
        Hatirlatici_spinner = (MaterialSpinner) getActivity().findViewById(R.id.Hatirlatici_spinner);
        Hatirlatici_spinner.setItems("0"," 1", "2", "3", "4", "5");
        Hatirlatici_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });

        Detay_edit = getActivity().findViewById(R.id.Detay_edit);
        Etiket_edit = getActivity().findViewById(R.id.Etiket_edit);
        Saat_Ekle = getActivity().findViewById(R.id.Saat_Ekle);


        Saat_Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment saat_sec = new SaatSec();
                saat_sec.show(getActivity().getSupportFragmentManager(),"Saat seç");
            }
        });

        hatirlatici_switch = getActivity().findViewById(R.id.hatirlatici_switch);
        hatirlatici_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Hatirlatici_spinner.setVisibility(View.VISIBLE);
                }else {
                    Hatirlatici_spinner.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    @Override
    public void onTimeSet(TimePicker view, int saat, int dakika) {
        saat_goster = getActivity().findViewById(R.id.saat_text);
        saat_goster.setVisibility(View.VISIBLE);
        saat_goster.setText(saat+":"+dakika);
    }

}
