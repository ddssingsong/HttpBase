/**
 * Copyright © YOLANDA. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yolanda.nohttp.cache;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.db.Field;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>Cache database operation class.</p>
 * Created in Jan 10, 2016 12:39:15 AM.
 *
 * @author YOLANDA;
 */
class CacheDisk extends SQLiteOpenHelper implements Field {

    public static final String DB_CACHE_NAME = "_nohttp_cache_db.db";
    public static final int DB_CACHE_VERSION = 1;

    public static final String TABLE_NAME = "cache_table";
    public static final String KEY = "key";
    public static final String HEAD = "head";
    public static final String DATA = "data";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE cache_table(_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT, head TEXT, data BLOB)";
    private static final String SQL_CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX cache_unique_index ON cache_table(\"key\")";
    private static final String SQL_DELETE_TABLE = "DROP TABLE cache_table";
    private static final String SQL_DELETE_UNIQUE_INDEX = "DROP UNIQUE INDEX cache_unique_index";

    public CacheDisk() {
        super(NoHttp.getContext(), DB_CACHE_NAME, null, DB_CACHE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(SQL_CREATE_TABLE);
            db.execSQL(SQL_CREATE_UNIQUE_INDEX);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.beginTransaction();
            try {
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_DELETE_UNIQUE_INDEX);
                db.execSQL(SQL_CREATE_TABLE);
                db.execSQL(SQL_CREATE_UNIQUE_INDEX);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
