package com.example.alisgram;

import java.util.ArrayList;

public class ModelAliskanlik {
    private String AliskanlikId;
    private String AliskanlikKategori;
    private String AliskanlikAltKategori;
    private String AliskanlikEtiket;
    private String AliskanlikDetay;
    private ArrayList<String> AliskanlikGunler;
    private ArrayList<String> AliskanlikSaatler;
    private String AlislanlikOnHatirlatici;
    private boolean AliskanlikGizlilik;
    private String AliskanlikKullaniciId;
    private String AliskanlikOlusturulmaTarihi;
    private int AliskanlikSeviye;
    private int AliskanlikDurum;

    public ModelAliskanlik(String aliskanlikId, String aliskanlikKategori, String aliskanlikAltKategori, String aliskanlikEtiket, String aliskanlikDetay, ArrayList<String> aliskanlikGunler, ArrayList<String> aliskanlikSaatler, String alislanlikOnHatirlatici, boolean aliskanlikGizlilik, String aliskanlikKullaniciId, String aliskanlikOlusturulmaTarihi, int aliskanlikSeviye, int aliskanlikDurum) {
        AliskanlikId = aliskanlikId;
        AliskanlikKategori = aliskanlikKategori;
        AliskanlikAltKategori = aliskanlikAltKategori;
        AliskanlikEtiket = aliskanlikEtiket;
        AliskanlikDetay = aliskanlikDetay;
        AliskanlikGunler = aliskanlikGunler;
        AliskanlikSaatler = aliskanlikSaatler;
        AlislanlikOnHatirlatici = alislanlikOnHatirlatici;
        AliskanlikGizlilik = aliskanlikGizlilik;
        AliskanlikKullaniciId = aliskanlikKullaniciId;
        AliskanlikOlusturulmaTarihi = aliskanlikOlusturulmaTarihi;
        AliskanlikSeviye = aliskanlikSeviye;
        AliskanlikDurum = aliskanlikDurum;
    }

    public ModelAliskanlik(){}

    public int getAliskanlikDurum() {
        return AliskanlikDurum;
    }

    public void setAliskanlikDurum(int aliskanlikDurum) {
        AliskanlikDurum = aliskanlikDurum;
    }

    public String getAliskanlikOlusturulmaTarihi() {
        return AliskanlikOlusturulmaTarihi;
    }

    public void setAliskanlikOlusturulmaTarihi(String aliskanlikOlusturulmaTarihi) {
        AliskanlikOlusturulmaTarihi = aliskanlikOlusturulmaTarihi;
    }

    public int getAliskanlikSeviye() {
        return AliskanlikSeviye;
    }

    public void setAliskanlikSeviye(int aliskanlikSeviye) {
        AliskanlikSeviye = aliskanlikSeviye;
    }

    public String getAliskanlikKullaniciId() {
        return AliskanlikKullaniciId;
    }

    public void setAliskanlikKullaniciId(String aliskanlikKullaniciId) {
        AliskanlikKullaniciId = aliskanlikKullaniciId;
    }

    public String getAliskanlikId() {
        return AliskanlikId;
    }

    public void setAliskanlikId(String aliskanlikId) {
        AliskanlikId = aliskanlikId;
    }

    public String getAliskanlikKategori() {
        return AliskanlikKategori;
    }

    public void setAliskanlikKategori(String aliskanlikKategori) {
        AliskanlikKategori = aliskanlikKategori;
    }
    public String getAliskanlikAltKategori() {
        return AliskanlikAltKategori;
    }

    public void setAliskanlikAltKategori(String aliskanlikAltKategori) {
        AliskanlikAltKategori = aliskanlikAltKategori;
    }

    public String getAliskanlikEtiket() {
        return AliskanlikEtiket;
    }

    public void setAliskanlikEtiket(String aliskanlikEtiket) {
        AliskanlikEtiket = aliskanlikEtiket;
    }

    public String getAliskanlikDetay() {
        return AliskanlikDetay;
    }

    public void setAliskanlikDetay(String aliskanlikDetay) {
        AliskanlikDetay = aliskanlikDetay;
    }

    public ArrayList<String> getAliskanlikGunler() {
        return AliskanlikGunler;
    }

    public void setAliskanlikGunler(ArrayList<String> aliskanlikGunler) {
        AliskanlikGunler = aliskanlikGunler;
    }

    public ArrayList<String> getAliskanlikSaatler() {
        return AliskanlikSaatler;
    }

    public void setAliskanlikSaatler(ArrayList<String> aliskanlikSaatler) {
        AliskanlikSaatler = aliskanlikSaatler;
    }

    public String getAlislanlikOnHatirlatici() {
        return AlislanlikOnHatirlatici;
    }

    public void setAlislanlikOnHatirlatici(String alislanlikOnHatirlatici) {
        AlislanlikOnHatirlatici = alislanlikOnHatirlatici;
    }

    public boolean isAliskanlikGizlilik() {
        return AliskanlikGizlilik;
    }

    public void setAliskanlikGizlilik(boolean aliskanlikGizlilik) {
        AliskanlikGizlilik = aliskanlikGizlilik;
    }

}
