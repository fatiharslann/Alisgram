package com.example.alisgram;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFragmentDialog extends DialogFragment {

    public interface OnInputSelected {
        void sendInput(String input);
    }

    public OnInputSelected mOnInputSelected;

    View v = null;
    ModelKullanici kullanici;

    EditText et_isim,et_cinsiyet,et_soyisim,et_dogumtarihi,et_email;

    public void callBack(OnInputSelected inputSelected) {
        this.mOnInputSelected = inputSelected;
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle s) {
        v = i.inflate(R.layout.custom_dialog_fragment, null, false);

        FirebaseHelper.readData(new FirebaseHelper.MyCallback() {
            @Override
            public void onCallback(ModelKullanici value) {
                    kullanici=value;
                    editTextDoldur();
            }
        });


        //kullanici = FirebaseHelper.getCurrentKullanici();

        Button bt_profilguncelle = v.findViewById(R.id.bt_profilguncelle);

        bt_profilguncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mOnInputSelected.sendInput("Msj");
                //getDialog().dismiss();

                kullanici.setIsim(getText(et_isim));
                kullanici.setSoyisim(getText(et_soyisim));
                kullanici.setCinsiyet(getText(et_cinsiyet));
                kullanici.setDogumTarihi(getText(et_dogumtarihi));

                FirebaseHelper.setUpdateCurrentUser(kullanici);

            }
        });

        return v;
    }

    private void editTextDoldur(){

        et_email = getView(R.id.et_email, kullanici.getEmail());
        et_isim = getView(R.id.et_isim, kullanici.getIsim());
        et_cinsiyet = getView(R.id.et_cinsiyet, kullanici.getCinsiyet());
        et_soyisim = getView(R.id.et_soyisim, kullanici.getSoyisim());
        et_dogumtarihi = getView(R.id.et_dogumtarihi, kullanici.getDogumTarihi());

    }

    private EditText getView(int idName, String value) {
        EditText et = v.findViewById(idName);
        if (!value.equals("?"))
            et.setText(value);
        return et;
    }

    private String getText(EditText et) {
        return et.getText().toString();
    }

}
