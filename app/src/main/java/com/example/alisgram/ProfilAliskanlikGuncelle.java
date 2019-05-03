package com.example.alisgram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class ProfilAliskanlikGuncelle extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ModelAliskanlik aliskanlik;
    private MaterialEditText aliskanlikEtiket,aliskanlikDetay;
    private Button Saat_Ekle,kaydet_guncelle,pazartesi,sali,carsamba,persembe,cuma,cumartesi,pazar;
    private TextView saat_text_1,saat_text_2,saat_text_3;
    private Switch hatirlatici_switch,gizlilik_switch;
    private ArrayList<String> list_gun,list_saat;
    private ArrayList<TextView> list_saatler=new ArrayList<TextView>();
    private EditText on_hatirlatici_gunle;
    private ImageView saat_sil_1,saat_sil_2,saat_sil_3;
    private ArrayList<ImageView> list_sil_butonlar=new ArrayList<ImageView>();
    private boolean gizlilik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_aliskanlik_guncelle);

        aliskanlikEtiket = findViewById(R.id.Etiket_gunle);
        aliskanlikDetay = findViewById(R.id.Detay_gunle);
        hatirlatici_switch = findViewById(R.id.hatirlatici_switch_gunle);
        gizlilik_switch = findViewById(R.id.gizlilik_switch_gunle);
        on_hatirlatici_gunle = findViewById(R.id.hatirlatici_text_gunle);
        saat_text_1 = findViewById(R.id.saat_text_1_gunle);
        saat_text_2 = findViewById(R.id.saat_text_2_gunle);
        saat_text_3 = findViewById(R.id.saat_text_3_gunle);
        saat_sil_1 = findViewById(R.id.saat_sil_1);
        saat_sil_2 = findViewById(R.id.saat_sil_2);
        saat_sil_3 = findViewById(R.id.saat_sil_3);
        pazartesi = findViewById(R.id.Pazartesi_button_gunle);
        sali = findViewById(R.id.Sali_Button_gunle);
        carsamba = findViewById(R.id.Carsamba_button_gunle);
        persembe = findViewById(R.id.Persembe_button_gunle);
        cuma = findViewById(R.id.Cuma_button_gunle);
        cumartesi = findViewById(R.id.Cumartesi_button_gunle);
        pazar = findViewById(R.id.Pazar_button_gunle);
        kaydet_guncelle = findViewById(R.id.kaydet_guncelle);
        Saat_Ekle = findViewById(R.id.Saat_Ekle_gunle);


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

        hatirlatici_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    on_hatirlatici_gunle.setVisibility(View.VISIBLE);
                } else {
                    on_hatirlatici_gunle.setVisibility(View.INVISIBLE);
                }
            }
        });

        saat_sil_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saat_text_1.setText("");
                saat_text_1.setVisibility(View.INVISIBLE);
                saat_sil_1.setVisibility(View.INVISIBLE);
            }
        });

        saat_sil_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saat_text_2.setText("");
                saat_text_2.setVisibility(View.INVISIBLE);
                saat_sil_2.setVisibility(View.INVISIBLE);
            }
        });

        saat_sil_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saat_text_3.setText("");
                saat_text_3.setVisibility(View.INVISIBLE);
                saat_sil_3.setVisibility(View.INVISIBLE);
            }
        });

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

        Saat_Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment saat_sec = new SaatSecGuncelle();
                saat_sec.show(ProfilAliskanlikGuncelle.this.getSupportFragmentManager(),"Saat seç");

            }
        });


        list_saatler.add(saat_text_1);
        list_saatler.add(saat_text_2);
        list_saatler.add(saat_text_3);
        list_sil_butonlar.add(saat_sil_1);
        list_sil_butonlar.add(saat_sil_2);
        list_sil_butonlar.add(saat_sil_3);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        final String aliskanlikId = extras.getString("aliskanlikId");

        mDatabase = FirebaseDatabase.getInstance().getReference("aliskanliklar").child(aliskanlikId);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //alışkanlık bilgileri
                aliskanlik = dataSnapshot.getValue(ModelAliskanlik.class);
                if (aliskanlik==null)
                    return;

                list_gun = aliskanlik.getAliskanlikGunler();
                list_saat = aliskanlik.getAliskanlikSaatler();

                //alışkanlık bilgilerini ekrana doldurma
                aliskanlikEtiket.setText(aliskanlik.getAliskanlikEtiket());
                aliskanlikDetay.setText(aliskanlik.getAliskanlikDetay());

                if (aliskanlik.isAliskanlikGizlilik()==true)
                    gizlilik_switch.setChecked(true);

                if (!aliskanlik.getAlislanlikOnHatirlatici().equals("0")){
                    hatirlatici_switch.setChecked(true);
                    on_hatirlatici_gunle.setVisibility(View.VISIBLE);
                    on_hatirlatici_gunle.setText(aliskanlik.getAlislanlikOnHatirlatici());
                }

                if (list_saat!=null){
                    for (int i=0;i<list_saat.size();i++) {
                        list_saatler.get(i).setVisibility(View.VISIBLE);
                        list_sil_butonlar.get(i).setVisibility(View.VISIBLE);
                        list_saatler.get(i).setText(list_saat.get(i));
                    }
                }

                if (list_gun!=null){
                    for (int i=0;i<list_gun.size();i++){
                        switch (list_gun.get(i)){
                            case "Pazartesi":
                                pazartesi.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Salı":
                                sali.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Çarşamba":
                                carsamba.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Perşembe":
                                persembe.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Cuma":
                                cuma.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Cumartesi":
                                cumartesi.setBackgroundResource(R.drawable.selected_circle);
                                break;
                            case "Pazar":
                                pazar.setBackgroundResource(R.drawable.selected_circle);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        kaydet_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aliskanlik.setAliskanlikDetay(aliskanlikDetay.getText().toString());
                aliskanlik.setAliskanlikEtiket(aliskanlikEtiket.getText().toString());

                if (hatirlatici_switch.isChecked())
                    aliskanlik.setAlislanlikOnHatirlatici(on_hatirlatici_gunle.getText().toString());
                else
                    aliskanlik.setAlislanlikOnHatirlatici("0");

                if (gizlilik_switch.isChecked())
                    aliskanlik.setAliskanlikGizlilik(true);
                else
                    aliskanlik.setAliskanlikGizlilik(false);

                aliskanlik.setAliskanlikGizlilik(gizlilik);

                mDatabase.setValue(aliskanlik);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.9));
    }
}
