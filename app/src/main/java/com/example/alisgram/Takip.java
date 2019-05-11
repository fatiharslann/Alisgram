package com.example.alisgram;

public class Takip {

    private String from;
    private String to;
    private String takip_id;

    public Takip(String from, String to, String takip_id) {
        this.from = from;
        this.to = to;
        this.takip_id = takip_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTakip_id() {
        return takip_id;
    }

    public void setTakip_id(String takip_id) {
        this.takip_id = takip_id;
    }

    public Takip(){}
}
