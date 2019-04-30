package com.example.alisgram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitEkrani extends AppCompatActivity {

    //...
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        auth = FirebaseAuth.getInstance();
    }

    public void btn_kayit_click(View view) {

        EditText et_email = findViewById(R.id.et_email);
        EditText et_sifre = findViewById(R.id.et_sifre);

        EditText et_isim = findViewById(R.id.et_isim);

        String email = et_email.getText().toString();
        String parola = et_sifre.getText().toString();
        final String isim = et_isim.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(parola)) {
            Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (parola.length() < 6) {
            Toast.makeText(getApplicationContext(), "Parola en az 6 haneli olmalıdır", Toast.LENGTH_SHORT).show();
            return;
        }

        //FirebaseAuth ile email,parola parametrelerini kullanarak yeni bir kullanıcı oluşturuyoruz.
        auth.createUserWithEmailAndPassword(email, parola)
                .addOnCompleteListener(KayitEkrani.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        //İşlem başarısız olursa kullanıcıya bir Toast mesajıyla bildiriyoruz.
                        if (!task.isSuccessful()) {
                            Toast.makeText(KayitEkrani.this, "Yetkilendirme Hatası",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                        else {
                            FirebaseHelper.ekleKullanici(isim);
                            startActivity(new Intent(KayitEkrani.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }
}
