package com.pinarakdag.detectiondictionary.PhotoWithWordList;

public class Model {
    private int id;
    private String en;
    private String tr;
    private String sentence;
    private byte[] image;

    public Model(int id, String en, String tr, String sentence, byte[] image) {
        this.id = id;
        this.en = en;
        this.tr = tr;
        this.sentence = sentence;
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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
