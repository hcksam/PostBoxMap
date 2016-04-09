package hkpu.postboxmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hck on 9/4/2016.
 */
public class PostBoxLocationDAO {
    public final static String TABLE_NAME = "PostBoxLocation";
    public final static String KEY_ID = "id";
    public final static String ADDRESS_COLUMN = "address";
    public final static String LAT_COLUMN = "latitude";
    public final static String LON_COLUMN = "longitude";

    public final static String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADDRESS_COLUMN + " TEXT, " +
                    LAT_COLUMN + " REAL NOT NULL, " +
                    LON_COLUMN + " REAL NOT NULL) ";

    private SQLiteDatabase db;

    public PostBoxLocationDAO(Context context, String dbPath) {
        init(context, dbPath);
    }

    public PostBoxLocationDAO(Context context) {
        init(context, null);
    }

    public void init(Context context, String dbPath){
        if (dbPath != null) {
            db = context.openOrCreateDatabase(dbPath + "/"+FixData.AppName+"/"+FixData.DBName, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }else {
            db = context.openOrCreateDatabase(context.getDatabasePath(FixData.DBName).getAbsolutePath(), SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }

        if (!isTableExists()){
            db.execSQL(CREATE_TABLE_SQL);
            insertSample();
        }
    }

    public void close() {
        db.close();
    }

    public PostBoxLocationBean insert(PostBoxLocationBean bean) {
        ContentValues cv = convertContentValues(bean);

        long id = db.insert(TABLE_NAME, null, cv);

        bean.setId(id);
        return bean;
    }

    public boolean update(PostBoxLocationBean bean) {
        ContentValues cv = convertContentValues(bean);

        String where = KEY_ID + "=" + bean.getId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public List<PostBoxLocationBean> listAll() {
        List<PostBoxLocationBean> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(convertBean(cursor));
        }

        cursor.close();
        return result;
    }

    public PostBoxLocationBean get(long id) {
        PostBoxLocationBean bean = null;
        String where = KEY_ID + "=" + id;

        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            bean = convertBean(result);
        }

        result.close();
        return bean;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        cursor.close();
        return result;
    }

    public boolean isTableExists(){
        String result = null;

        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+TABLE_NAME+"';";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            result = cursor.getString(0);
        }

        cursor.close();
        return (result != null && result.equals(TABLE_NAME));
    }

    public PostBoxLocationBean convertBean(Cursor cursor) {
        PostBoxLocationBean result = new PostBoxLocationBean();

        int c = 0;
        result.setId(cursor.getLong(c++));
        result.setAddress(cursor.getString(c++));
        result.setLatitude(cursor.getDouble(c++));
        result.setLongitude(cursor.getDouble(c++));

        return result;
    }

    public ContentValues convertContentValues(PostBoxLocationBean bean){
        ContentValues cv = new ContentValues();

        cv.put(ADDRESS_COLUMN, bean.getAddress());
        cv.put(LAT_COLUMN, bean.getLatitude());
        cv.put(LON_COLUMN, bean.getLongitude());

        return cv;
    }

    public void insertSample() {
        PostBoxLocationBean bean = new PostBoxLocationBean(0, "九龍塘達之路80號", 22.337338, 114.1728202);
        PostBoxLocationBean bean2 = new PostBoxLocationBean(1, "", 22.395002, 114.198034);
        PostBoxLocationBean bean3 = new PostBoxLocationBean(2, "海洋公園", 22.2602496, 114.1653555);
        PostBoxLocationBean bean4 = new PostBoxLocationBean(3, "機場", 22.316810, 113.924025);

        insert(bean);
        insert(bean2);
        insert(bean3);
        insert(bean4);
    }
}
