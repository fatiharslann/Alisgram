package com.example.alisgram;

public class ModelKategori  {
    private int KategoriId;
    private String KategoriAdi;
    private String UstKategoriAdi;

    public ModelKategori(int kategoriId, String kategoriAdi, String UstKategoriAdi) {
        this.KategoriId = kategoriId;
        this.KategoriAdi = kategoriAdi;
        this.UstKategoriAdi = UstKategoriAdi;
    }

    public ModelKategori(){

    }

    public String getUstKategoriAdi() {
        return UstKategoriAdi;
    }

    public void setUstKategoriAdi(String UstKategoriAdi) {
        this.UstKategoriAdi = UstKategoriAdi;
    }

    public int getKategoriId() {
        return KategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.KategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return KategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.KategoriAdi = kategoriAdi;
    }
}
