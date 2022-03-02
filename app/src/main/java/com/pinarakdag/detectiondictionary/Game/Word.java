package com.pinarakdag.detectiondictionary.Game;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {

    public static final String TYPE_ANIMAL="Animals";
    public static final String TYPE_ACTION="Most Common Action Verbs";
    public static final String TYPE_OBJECT="Most Common Adjectives";

    private String word;
    private String info;
    private String type;

    public Word(){}

    protected Word(Parcel in) {
        word = in.readString();
        info = in.readString();
        type = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(info);
        dest.writeString(type);
    }

    public Word(String word, String info, String type) {
        this.word = word;
        this.info = info;
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String[] getAllTypes(){
        return new String[]{
                TYPE_ANIMAL,
                TYPE_ACTION,
                TYPE_OBJECT
        };
    }

}
