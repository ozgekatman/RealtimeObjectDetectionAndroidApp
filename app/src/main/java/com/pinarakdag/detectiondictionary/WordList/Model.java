package com.pinarakdag.detectiondictionary.WordList;

public class Model {
    private int id;
    private String en;
    private String tr;

    public Model(int id, String en, String tr) {
        this.id = id;
        this.en = en;
        this.tr = tr;
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


}

