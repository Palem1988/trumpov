package com.danklemme.trumpov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danklemme.trumpov.FeedReaderContract.FeedEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 10/27/2017.
 */

public class Database {
    private FeedReaderDbHelper mDbHelper;

    public Database(Context context) {
        mDbHelper = new FeedReaderDbHelper(context);
    }

    public void putText(String markovText, String type) {
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TEXT, markovText);
        values.put(FeedEntry.COLUMN_NAME_TYPE, type);
        long newRowId = mDbHelper.getWritableDatabase().insert(FeedEntry.TABLE_NAME, null, values);
    }

    public List<TrumpovPair> readAllText() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {FeedEntry._ID, FeedEntry.COLUMN_NAME_TEXT,
                FeedEntry.COLUMN_NAME_TYPE};
        Cursor cursor = db.query(FeedEntry.TABLE_NAME, projection, null, null, null, null, null);
        cursor.moveToFirst();

        List<TrumpovPair> trumpovPairs = new ArrayList<>();

        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry
                    .COLUMN_NAME_TEXT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry
                    .COLUMN_NAME_TYPE));
            trumpovPairs.add(new TrumpovPair(text, type));
        }
        cursor.close();
        return trumpovPairs;
    }

    public List<TrumpovPair> readTypeOfText(String type) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {FeedEntry._ID, FeedEntry.COLUMN_NAME_TEXT,
                FeedEntry.COLUMN_NAME_TYPE};
        String selection = FeedEntry.COLUMN_NAME_TYPE + " = ?";
        String[] selectionArgs = { type };
        Cursor cursor = db.query(FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null,
                null);
        cursor.moveToFirst();

        List<TrumpovPair> trumpovPairs = new ArrayList<>();

        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry
                    .COLUMN_NAME_TEXT));
            trumpovPairs.add(new TrumpovPair(text, type));
        }
        cursor.close();
        return trumpovPairs;
    }
}
