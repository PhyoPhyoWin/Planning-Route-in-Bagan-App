package com.bagan.test.bagan;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "finalbagan.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}


