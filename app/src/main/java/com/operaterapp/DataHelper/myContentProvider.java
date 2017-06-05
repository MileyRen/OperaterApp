package com.operaterapp.DataHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.operaterapp.util.BaseConst;

public class myContentProvider extends ContentProvider {
    public static final int VERSION_DIR = 0;//查询表中单条数据

    public static final String AUTHORITY="com.operaterapp.DataHelper.provider";
    private static UriMatcher uriMatcher;
    private DataHelper dbHelper;

    public myContentProvider() {
    }

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY , BaseConst.VERSION_TABLE_NAME, VERSION_DIR);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case VERSION_DIR:
                long id = db.insert(BaseConst.VERSION_TABLE_NAME, null, values);
                uriReturn = Uri.parse("content://"+ AUTHORITY +"/"+BaseConst.VERSION_TABLE_NAME+"/"+id);
                break;
            default:break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataHelper(getContext(), BaseConst.VERSION_DB_NAME ,null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case VERSION_DIR:
                cursor = db.query(BaseConst.VERSION_TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
