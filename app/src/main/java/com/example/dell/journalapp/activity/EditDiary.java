package com.example.dell.journalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.journalapp.R;
import com.example.dell.journalapp.Utils.DBService;
import com.example.dell.journalapp.model.Diary;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.List;

public class EditDiary extends AppCompatActivity {
    Diary diary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diary);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("journalapp", Context.MODE_PRIVATE);
         diary =new Gson().fromJson(sharedPreferences.getString("diary", ""),Diary.class);
        TextView diaryTextView =findViewById(R.id.add_dairy_edit_text);
        Button buttonDiary =findViewById(R.id.delete_dairy_btn);
        diaryTextView.setText(diary.getDiaryContent());
        buttonDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DBService dbService = DBService.getInstance(getApplicationContext());
                    Dao<Diary, Long> dao = DaoManager.createDao(dbService.getConnectionSource(), Diary.class);
                    int msg =dao.delete(diary);
                    Toast.makeText(EditDiary.this, "Deleted successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditDiary.this, MainActivity.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}
