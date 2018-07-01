package com.example.dell.journalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.journalapp.R;
import com.example.dell.journalapp.Utils.DBService;
import com.example.dell.journalapp.model.Diary;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDiary.class);
                startActivity(intent);
                finish();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        checkIfDiaryExist();
    }


    public void checkIfDiaryExist(){

        try {
            DBService dbService = DBService.getInstance(getApplicationContext());
            Dao<Diary, Long> dao = DaoManager.createDao(dbService.getConnectionSource(), Diary.class);
            List<Diary> diaryList=dao.queryForAll();
            if (!diaryList.isEmpty()){
                recyclerView = findViewById(R.id.mian_content_recycler_view);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                DiaryAdapter ordersAdapter = new DiaryAdapter(this, diaryList);
                recyclerView.setAdapter(ordersAdapter);
            }
            else{
                RelativeLayout no_journal_layout= findViewById(R.id.no_journal_entry);
                no_journal_layout.setVisibility(View.VISIBLE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class DiaryAdapter extends RecyclerView.Adapter<diaryViewHolder> {
        Context context;
        List<Diary> diaryList;
        DBService dbService;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");

        public DiaryAdapter(Context context, List<Diary> diaryList) {
            this.context = context;
            this.diaryList = diaryList;
            dbService = DBService.getInstance(context);
        }

        @Override
        public diaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_recycler_content, null);
            return new diaryViewHolder(context, view);
        }

        @Override
        public void onBindViewHolder(diaryViewHolder holder, int position) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyy ");
            SimpleDateFormat timeFormat = new SimpleDateFormat(" HH:mm");
            Diary diary = diaryList.get(position);
            holder.dateTime.setText(dateFormat.format(diary.getDateTime()).toString());
            holder.diary.setText(diary.getDiaryContent().toString());
            holder.time.setText(timeFormat.format(diary.getDateTime()).toString());
            holder.diary_content_card.setTag(diary);
            holder.diary_content_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Diary dairy= (Diary)view.getTag();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("journalapp", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("diary", new Gson().toJson(dairy)).commit();
                    Intent intent = new Intent(MainActivity.this, EditDiary.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return diaryList.size();
        }


    }





    public static class diaryViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTime;
        private TextView time;
        private TextView diary;
        private CardView diary_content_card;


        public diaryViewHolder(Context context, View itemView) {
            super(itemView);
            dateTime = itemView.findViewById(R.id.diary_date);
            time = itemView.findViewById(R.id.diary_time);
            diary = itemView.findViewById(R.id.add_dairy_edit_text);
            diary_content_card=itemView.findViewById(R.id.diary_content_card);

        }

    }


}
