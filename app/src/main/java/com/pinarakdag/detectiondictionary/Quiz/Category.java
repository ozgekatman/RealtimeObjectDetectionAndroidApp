package com.pinarakdag.detectiondictionary.Quiz;

public class Category {
    public static final int FLAGS = 1;
    public static final int WORDS = 2;

    private int id;
    private String name;

    public Category(){
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName();
    }

}
