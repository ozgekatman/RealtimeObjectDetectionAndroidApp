package com.pinarakdag.detectiondictionary.AssetWordList;

public class DbModelClass {
    String en;
    String tr;


    public DbModelClass(String en, String tr) {
        this.en = en;
        this.tr = tr;

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
}

