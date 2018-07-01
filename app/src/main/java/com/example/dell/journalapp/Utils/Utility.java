package com.example.dell.journalapp.Utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.journalapp.model.User;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class Utility  extends AppCompatActivity{

    public static String formatMoneyNumber(Long money){
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###.00");
        return "# "+ String.valueOf(numberFormat.format(money));

    }

    public static  <T> T convertJsonToObject(Class<T> tclass, String jsonString){

        T t = new Gson().fromJson(jsonString, tclass);
        return t;
    }


    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static Boolean  chechIfUserIsLoggedIn(Context context) {
        List<User> userList;
        Boolean msg = false;
        try {
            DBService dbService = DBService.getInstance(context);
            Dao<User, Long> dao = DaoManager.createDao(dbService.getConnectionSource(), User.class);
            userList = dao.queryForAll();
            if (!userList.isEmpty()) {
                msg = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return msg;
    }
    public  static  boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
