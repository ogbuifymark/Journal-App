package com.example.dell.journalapp.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dell.journalapp.R;
import com.example.dell.journalapp.model.Diary;
import com.example.dell.journalapp.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



import java.sql.SQLException;

public class DBService extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "journal_application.db";
    private static final int DATABASE_VERSION = 1;

    private DBService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,R.raw.orm_config);
    }

    public static DBService getInstance(Context context) {
        return new DBService(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
//              TableUtils.createTable(connectionSource,User.class);
            TableUtils.createTable(connectionSource,Diary.class);

 } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
