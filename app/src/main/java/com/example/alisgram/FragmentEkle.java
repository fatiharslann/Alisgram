package com.example.alisgram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentEkle extends Fragment {
    private MaterialSpinner Kategori_spinner, Alt_Kategori_spinner;
    private MaterialEditText Detay_edit, Etiket_edit;
    private Button Saat_Ekle,btn_kaydet,pazartesi,sali,carsamba,persembe,cuma,cumartesi,pazar;
    private TextView saat_text_1,saat_text_2,saat_text_3;
    private View view;
    private Switch hatirlatici_switch,gizlilik_switch;
    private ArrayList<String> list_gun,list_saat,listKategori,listAltKategori;
    private EditText hatirlatici_text;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference,kDatabaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private Intent maine_don;

    private String kategori_adi,alt_kategori_adi,etiket,detay,hatirlatici,tarih;
    private boolean gizlilik;
    private int kategori_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_ekle, container, false);
        tanimla();
        kategoriGetir();
        return view;
    }

    private void tanimla() {

        mDatabase = FirebaseDatabase.getInstance();
        //--------------------------------------------------------------- Tarih

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        tarih = df.format(currentTime);

        Log.d("TAGIM",tarih);
        //-------------------------------------------------------------- User
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        //------------------------------------------ List

        list_gun = new ArrayList<>();
        list_saat = new ArrayList<>();

        //--------------------------------------------------    Edit Text
        Detay_edit = view.findViewById(R.id.Detay_edit);
        Etiket_edit = view.findViewById(R.id.Etiket_edit);
        hatirlatici_text = view.findViewById(R.id.hatirlatici_text);

        //_--------------------------------------------------   Saat Kısmı
        saat_text_1 = view.findViewById(R.id.saat_text_1);
        saat_text_2= view.findViewById(R.id.saat_text_2);
        saat_text_3 = view.findViewById(R.id.saat_text_3);

        Saat_Ekle = view.findViewById(R.id.Saat_Ekle);
        Saat_Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment saat_sec = new SaatSec();
                saat_sec.show(getFragmentManager(),"Saat seç");

            }
        });

        //------------------------------------------------- Switch
        hatirlatici_switch = view.findViewById(R.id.hatirlatici_switch);
        hatirlatici_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    hatirlatici_text.setVisibility(View.VISIBLE);
                } else {
                    hatirlatici_text.setVisibility(View.INVISIBLE);
                }
            }
        });

        gizlilik_switch = view.findViewById(R.id.gizliilik_switch);
        gizlilik_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        gizlilik=true;
                    } else {
                        gizlilik=false;
                    }
                }

        });
        //-----------------------------------------------------------------------------------------         Günler
        pazartesi = view.findViewById(R.id.Pazartesi_button);
        sali = view.findViewById(R.id.Sali_Button);
        carsamba = view.findViewById(R.id.Carsamba_button);
        persembe = view.findViewById(R.id.Persembe_button);
        cuma = view.findViewById(R.id.Cuma_button);
        cumartesi = view.findViewById(R.id.Cumartesi_button);
        pazar = view.findViewById(R.id.Pazar_button);


        pazartesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pazartesi.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    pazartesi.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Pazartesi");
                }else {
                    pazartesi.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Pazartesi");
                }
            }
        });
        sali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sali.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    sali.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Salı");
                }else {
                    sali.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Salı");
                }
            }
        });
        carsamba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carsamba.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    carsamba.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Çarşamba");
                }else {
                    carsamba.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Çarşamba");
                }
            }
        });
        persembe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(persembe.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    persembe.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Perşembe");
                }else {
                    persembe.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Perşembe");
                }
            }
        });
        cuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cuma.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    cuma.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Cuma");
                }else {
                    cuma.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Cuma");
                }
            }
        });
        cumartesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cumartesi.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    cumartesi.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Cumartesi");
                }else {
                    cumartesi.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Cumartesi");
                }
            }
        });
        pazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pazar.getBackground().getConstantState()==getResources().getDrawable(R.drawable.circle).getConstantState()){
                    pazar.setBackgroundResource(R.drawable.selected_circle);
                    list_gun.add("Pazar");
                }else {
                    pazar.setBackgroundResource(R.drawable.circle);
                    list_gun.remove("Pazar");
                }
            }
        });
        //  -   ------------------------------------------------------------------------------------    Kaydet
        btn_kaydet = view.findViewById(R.id.kaydet_btn);
        btn_kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiket = Etiket_edit.getText().toString();
                detay = Detay_edit.getText().toString();
                hatirlatici = hatirlatici_text.getText().toString();
                if(!TextUtils.isEmpty(saat_text_1.getText().toString())){
                    list_saat.add(saat_text_1.getText().toString());
                }
                if(!TextUtils.isEmpty(saat_text_2.getText().toString())){
                    list_saat.add(saat_text_2.getText().toString());
                }
                if(!TextUtils.isEmpty(saat_text_3.getText().toString())){
                    list_saat.add(saat_text_3.getText().toString());
                }
                if(!TextUtils.isEmpty(hatirlatici_text.getText().toString())){
                    hatirlatici = hatirlatici_text.getText().toString();
                }
                getir();

            }
        });


    }

    private void getir(){

        if(!list_gun.isEmpty() && !list_saat.isEmpty()){

            mDatabaseReference = mDatabase.getReference("aliskanliklar");
            String key = mDatabaseReference.push().getKey();
            ModelAliskanlik aliskanlik = new ModelAliskanlik();

            aliskanlik.setAliskanlikId(key);
            aliskanlik.setAliskanlikAltKategori(alt_kategori_adi);
            aliskanlik.setAliskanlikDetay(detay);
            aliskanlik.setAliskanlikDurum(0);
            aliskanlik.setAliskanlikEtiket(etiket);
            aliskanlik.setAliskanlikGizlilik(gizlilik);
            aliskanlik.setAliskanlikGunler(list_gun);
            aliskanlik.setAliskanlikKategori(kategori_adi);
            aliskanlik.setAliskanlikKullaniciId(mCurrentUser.getUid());
            aliskanlik.setAliskanlikOlusturulmaTarihi(tarih);
            aliskanlik.setAliskanlikSaatler(list_saat);
            aliskanlik.setAliskanlikSeviye(0);
            aliskanlik.setAlislanlikOnHatirlatici(hatirlatici);

            mDatabaseReference.child(key).setValue(aliskanlik);

            list_saat.clear();
            list_gun.clear();
            pazartesi.setBackgroundResource(R.drawable.circle);
            sali.setBackgroundResource(R.drawable.circle);
            carsamba.setBackgroundResource(R.drawable.circle);
            persembe.setBackgroundResource(R.drawable.circle);
            cuma.setBackgroundResource(R.drawable.circle);
            cumartesi.setBackgroundResource(R.drawable.circle);
            pazar.setBackgroundResource(R.drawable.circle);

        }
        else{
            Toast.makeText(getContext(), "Gün ve saat boş bırakılamaz", Toast.LENGTH_SHORT).show();
        }

    }

    private void kategoriGetir(){

        Kategori_spinner = (MaterialSpinner) view.findViewById(R.id.Kategori_spinner);

        kDatabaseReference = mDatabase.getReference("kategoriler");
        Query query = kDatabaseReference.orderByChild("ustKategoriAdi").equalTo("Yok");

        listKategori = new ArrayList<String>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listKategori.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String kategoriAdi=postSnapshot.child("kategoriAdi").getValue(String.class);
                    listKategori.add(kategoriAdi);
                    Kategori_spinner.setItems(listKategori);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
            }
        });

        Kategori_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                kategori_adi=item;
                altKategoriGetir();
            }
        });

    }


    private void altKategoriGetir(){

        listAltKategori = new ArrayList<String>();
        Alt_Kategori_spinner = (MaterialSpinner) view.findViewById(R.id.Alt_Kategori_spinner);

        Query queryy = kDatabaseReference.orderByChild("ustKategoriAdi").equalTo(kategori_adi);
        queryy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAltKategori.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String AltKategoriAdi=postSnapshot.child("kategoriAdi").getValue(String.class);
                    listAltKategori.add(AltKategoriAdi);
                    Alt_Kategori_spinner.setItems(listAltKategori);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
            }
        });


        Alt_Kategori_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                alt_kategori_adi=item;
            }
        });
    }

}
