package com.example.lab32;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "groupmates.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "Groupmates";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TIME = "AddTime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание новой таблицы
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "LastName TEXT, " +
                "FirstName TEXT, " +
                "MiddleName TEXT, " +
                COLUMN_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createTable);

        // Добавление начальных данных
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        for (int i = 1; i <= 5; i++) {
            ContentValues values = new ContentValues();
            values.put("LastName", "Фамилия " + i);
            values.put("FirstName", "Имя " + i);
            values.put("MiddleName", "Отчество " + i);
            db.insert(TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Удаление старой таблицы
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // Создание новой таблицы с измененной структурой
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "LastName TEXT, " +
                    "FirstName TEXT, " +
                    "MiddleName TEXT, " +
                    COLUMN_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            db.execSQL(createTable);

            // Добавление начальных данных
            insertInitialData(db);
        }
    }

    // Метод для вставки новой записи
    public void insertGroupmate(String lastName, String firstName, String middleName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LastName", lastName);
        values.put("FirstName", firstName);
        values.put("MiddleName", middleName);
        db.insert(TABLE_NAME, null, values);
    }

    // Метод для обновления последней записи
    public void updateLastRecord(String lastName, String firstName, String middleName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET LastName = ?, FirstName = ?, MiddleName = ? " +
                "WHERE " + COLUMN_ID + " = (SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME + ")";
        db.execSQL(query, new String[]{lastName, firstName, middleName});
    }

    // Метод для получения всех записей
    public Cursor getAllGroupmates() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
