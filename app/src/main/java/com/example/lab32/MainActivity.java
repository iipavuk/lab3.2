package com.example.lab32;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        Button showRecords = findViewById(R.id.show_records);
        Button addRecord = findViewById(R.id.add_record);
        Button updateRecord = findViewById(R.id.update_record);

        // Кнопка для отображения всех записей
        showRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowRecordsActivity.class);
            startActivity(intent);
        });

        // Кнопка для добавления новой записи
        addRecord.setOnClickListener(v -> {
            // Добавляем новую запись с произвольными данными
            dbHelper.insertGroupmate("Петров", "Петр", "Петрович");
        });

        // Кнопка для обновления последней записи
        updateRecord.setOnClickListener(v -> {
            // Обновляем последнюю запись
            dbHelper.updateLastRecord("Иванов", "Иван", "Иванович");
        });
    }
}

