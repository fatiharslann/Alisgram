package com.example.alisgram;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    int RC_SIGN_IN = 101;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = mDatabase.getReference("aliskanliklar");
    private NotificationManagerCompat notificationManagerCompat;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleAlarm();

        String evet = getIntent().getStringExtra("evet");
        String uid = getIntent().getStringExtra("uid");
        String hayir = getIntent().getStringExtra("hayir");

        if(uid != null){
            notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            if(evet != null){
                myRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           ModelAliskanlik aliskanlik = dataSnapshot.getValue(ModelAliskanlik.class);
                           int progress = aliskanlik.getAliskanlikSeviye();
                           myRef.child(aliskanlik.getAliskanlikId()).child("aliskanlikSeviye").setValue(progress+1);
                           notificationManagerCompat.cancel(1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else if(hayir != null){
                Toast.makeText(MainActivity.this, "Alışkanlık ilerletilmedi :((", Toast.LENGTH_SHORT).show();
                notificationManagerCompat.cancel(2);
            }
        }//*/


        dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();

        //alt menu kapatma
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        signInButton = findViewById(R.id.bt_GoogleSign);

        mAuth = FirebaseAuth.getInstance();

        //Google Sign in Options Yapılandırması
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity.this, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Geçerli bir yetkilendirme olup olmadığını kontrol ediyoruz.
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,Profil.class));
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }


    //Google ile Oturum acma islemleri
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In basarili oldugunda Firebase ile yetkilendir
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In hatası.
                //Log.e(TAG, "Google Sign In hatası.");
            }
        }
    }

    //GoogleSignInAccount nesnesinden ID token'ı alıp, bu Firebase güvenlik referansını kullanarak
    // Firebase ile yetkilendirme işlemini gerçekleştiriyoruz
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //             Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            //               Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Yetkilendirme hatası.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            toastBasarili();
                            FirebaseHelper.ekleKullanici();
                            startActivity(new Intent(MainActivity.this, Profil.class));
                            finish();
                        }
                    }
                });}

    private void firebaseEkle(String uuid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Kullanicilar");
        ref.setValue(uuid);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
        toastBasarisiz();
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void toastBasarili() {
        CustomToast.successful(MainActivity.this);
    }

    private void toastBasarisiz() {
        CustomToast.error(MainActivity.this);
    }

    public void bt_giris_click(View view) {
        EditText et_email = findViewById(R.id.et_email);
        EditText et_sifre = findViewById(R.id.et_sifre);

        String email = et_email.getText().toString();
        String parola = et_sifre.getText().toString();

        //Email girilmemiş ise kullanıcıyı uyarıyoruz.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }
        //Parola girilmemiş ise kullanıcıyı uyarıyoruz.
        if (TextUtils.isEmpty(parola)) {
            Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
            return;
        }

        //Firebase üzerinde kullanıcı doğrulamasını başlatıyoruz
        //Eğer giriş başarılı olursa task.isSuccessful true dönecek ve MainActivity e geçilecek
        mAuth.signInWithEmailAndPassword(email, parola)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            CustomToast.successful(MainActivity.this);
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        } else {
                            Log.e("Giriş Hatası", task.getException().getMessage());
                            CustomToast.error(MainActivity.this);
                        }
                    }
                });
    }

    public void bt_kayit_click(View view) {
        startActivity(new Intent(MainActivity.this, KayitEkrani.class));
        finish();
    }
    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), BootReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, BootReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1000, pIntent);
    }

}
