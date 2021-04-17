package com.example.evcharger.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ChargerSQliteHelper extends SQLiteOpenHelper {
    public static final String CREAT_USERLOGIN = "create table Charger(" +
            "chargerid integer primary key," +
            "chargername varchar(36)," +
            "brand varchar(36)," +
            "longitude double," +
            "latitude double," +
            "state varchar(36))";

    private Context mcontext;
    public ChargerSQliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_USERLOGIN);
//        Toast.makeText(mcontext,"退出登陆成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
