package com.example.dell.journalapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.journalapp.R;
import com.example.dell.journalapp.Utils.DBService;
import com.example.dell.journalapp.model.Diary;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AddDiary extends AppCompatActivity {
    Button add_diary_btn;
    EditText add_dairy_edit_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dairy);
        add_diary_btn =findViewById(R.id.add_dairy_btn);
        add_dairy_edit_text=findViewById(R.id.add_dairy_edit_text);
        addDiary();

    }


    public void addDiary(){
        add_diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String diaryText= add_dairy_edit_text.getText().toString().trim();

                try {
                    Diary diary = new Diary();
                    DBService dbService = DBService.getInstance(getApplicationContext());
                    Dao<Diary, Long> dao = DaoManager.createDao(dbService.getConnectionSource(), Diary.class);
                    diary.setDateTime(new Date().getTime());
                    diary.setDiaryContent(diaryText);
                    dao.create(diary);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddDiary.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
