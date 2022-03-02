package com.pinarakdag.detectiondictionary.Game;

import android.provider.BaseColumns;

public final class GameContract {

    private GameContract(){}

    public static class WordsTable implements BaseColumns{
        public static final String TABLE_NAME=" game_word";
        public static final String COLUMN_WORD="word";
        public static final String COLUMN_INFO="info";
        public static final String COLUMN_TYPE="type";
    }
}
