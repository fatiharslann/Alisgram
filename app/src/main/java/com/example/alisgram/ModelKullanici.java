package com.example.alisgram;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ModelKullanici {

    String email = "?";
    String isim = "?";
    String soyisim = "?";
    String cinsiyet = "?";
    String dogumTarihi = "?";
    String uuid = "?";

    public ModelKullanici() {
    }

    public ModelKullanici(String email, String isim, String soyisim, String cinsiyet, String dogumTarihi, String uuid) {
        this.email = email;
        this.isim = isim;
        this.soyisim = soyisim;
        this.cinsiyet = cinsiyet;
        this.dogumTarihi = dogumTarihi;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isNullText(isim) ? "?" : isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = isNullText(soyisim) ? "?" : soyisim;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = isNullText(cinsiyet) ? "?" : cinsiyet;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = isNullText(dogumTarihi) ? "?" : dogumTarihi;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private Boolean isNullText(String veri) {
        return veri.trim().equals("") ? true : false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", getUuid());
        result.put("isim", getIsim());
        result.put("soyisim", getSoyisim());
        result.put("email", getEmail());
        result.put("cinsiyet", getCinsiyet());
        result.put("dogumtarihi", getDogumTarihi());

        return result;
    }
}
