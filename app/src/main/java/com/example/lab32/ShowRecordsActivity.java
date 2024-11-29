package com.example.lab32;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowRecordsActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        dbHelper = new DBHelper(this);
        TextView recordsView = findViewById(R.id.records_view);

        Cursor cursor = null;
        StringBuilder builder = new StringBuilder();

        try {
            cursor = dbHelper.getAllGroupmates();

            if (cursor != null && cursor.moveToFirst()) {
                do {

                    String lastName = cursor.getString(cursor.getColumnIndexOrThrow("LastName"));
                    String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FirstName"));
                    String middleName = cursor.getString(cursor.getColumnIndexOrThrow("MiddleName"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("AddTime"));

                    builder.append(lastName).append(" ")
                            .append(firstName).append(" ")
                            .append(middleName).append(" - ")
                            .append(time).append("\n");
                } while (cursor.moveToNext());
            } else {
                builder.append("Записей не найдено.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.append("Ошибка при чтении базы данных.");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        recordsView.setText(builder.toString());
    }
}


