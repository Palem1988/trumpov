package com.danklemme.trumpov;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}



    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "markov_text";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_TYPE = "type";
    }
}
