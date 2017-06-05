package com.operaterapp.DataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.operaterapp.util.BaseConst;

/**
 * Created by Miley_Ren on 2017/6/3.
 */

public class DataHelper extends SQLiteOpenHelper {
    public String  CREATE_VERSION_SQL = BaseConst.CREATE_VERSION_SQL;

    private Context mContext;

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VERSION_SQL);
        Toast.makeText(mContext, "创建数据库成功_version",Toast.LENGTH_SHORT).show();
        Log.d("DataHelper","版本数据库创建成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BaseConst.DROP_TABLE_VERSION_SQL);
        onCreate(db);
    }
}
