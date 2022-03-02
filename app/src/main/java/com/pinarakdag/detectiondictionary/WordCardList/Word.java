package com.pinarakdag.detectiondictionary.WordCardList;

public class Word {

    private int id;
    private String en;
    private String tr;
    private byte[] image;

    public Word(int id, String en, String tr, byte[] image) {
        this.id = id;
        this.en = en;
        this.tr = tr;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
