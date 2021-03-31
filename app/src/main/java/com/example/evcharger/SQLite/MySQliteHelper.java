package com.example.evcharger.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySQliteHelper extends SQLiteOpenHelper {
    public static final String CREAT_USERLOGIN = "create table User(" +
            "userid integer primary key autoincrement," +
            "username varchar(36)," +
            "pwd varchar(36))";

    private Context mcontext;
    public MySQliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_USERLOGIN);
        Toast.makeText(mcontext,"退出登陆成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
