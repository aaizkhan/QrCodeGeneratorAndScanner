package com.encoderbytes.qrcodeScannerAndGenerator.models;

public class History_model {

    int id;
    String title;
    String desc;

    String type;

    public History_model(int id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public History_model() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
