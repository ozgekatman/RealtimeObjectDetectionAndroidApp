package com.pinarakdag.detectiondictionary.AssetPhotoWithWordList;

import android.graphics.Bitmap;

public class DbModelClass {
    String en;
    String tr;
    String sentence;
    Bitmap ourImage;

    public DbModelClass(String en, String tr, String sentence, Bitmap ourImage) {
        this.en = en;
        this.tr = tr;
        this.sentence = sentence;
        this.ourImage = ourImage;
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

    public Bitmap getOurImage() {
        return ourImage;
    }

    public void setOurImage(Bitmap ourImage) {
        this.ourImage = ourImage;
    }
}
