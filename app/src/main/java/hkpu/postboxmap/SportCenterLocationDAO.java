package hkpu.postboxmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hck on 9/4/2016.
 */
public class SportCenterLocationDAO implements CommonDAO{
    public final static String TABLE_NAME = "Sport_Center";
    public final static String AREA_COLUMN = "Area";
    public final static String ADDRESS_COLUMN = "Address";
    public final static String LAT_COLUMN = "lat";
    public final static String LON_COLUMN = "lng";
    public final static String WEEKDAYS_COLUMN = "Weekdays";
    public final static String WEEKEND_COLUMN = "Weekend";

    private SQLiteDatabase db;

    public SportCenterLocationDAO(Context context, File dbFile)
    {
        db = context.openOrCreateDatabase(dbFile.getAbsolutePath(), SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }

    public SportCenterLocationDAO(Context context, String dbPath) {
        init(context, dbPath);
    }

    public SportCenterLocationDAO(Context context) {
        init(context, null);
    }

    public void init(Context context, String dbPath){
        if (dbPath != null) {
            db = context.openOrCreateDatabase(dbPath + "/"+FixData.AppName+"/"+FixData.DBName, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }else {
            db = context.openOrCreateDatabase(context.getDatabasePath(FixData.DBName).getAbsolutePath(), SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }
    }

    public List<SportCenterLocationBean> listAll() {
        List<SportCenterLocationBean> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(convertBean(cursor));
        }

        cursor.close();
        return result;
    }

    public SportCenterLocationBean convertBean(Cursor cursor) {
        SportCenterLocationBean result = new SportCenterLocationBean();

        int c = 0;
        result.setName(cursor.getString(c++));
        result.setLatitude(Double.valueOf(cursor.getString(c++)));
        result.setLongitude(Double.valueOf(cursor.getString(c++)));
        result.setType(cursor.getString(c++));
        result.setAddress(cursor.getString(c++));
        result.setArea(cursor.getString(c++));
        result.setPhoneNumber(cursor.getString(c++));
        result.setEmail(cursor.getString(c++));
        result.setWebSite(cursor.getString(c++));
        result.setWeekdays(cursor.getString(c++));
        result.setWeekend(cursor.getString(c++));

        return result;
    }

    public ContentValues convertContentValues(SportCenterLocationBean bean){
        ContentValues cv = new ContentValues();

        cv.put(AREA_COLUMN, bean.getArea());
        cv.put(ADDRESS_COLUMN, bean.getAddress());
        cv.put(LAT_COLUMN, bean.getLatitude());
        cv.put(LON_COLUMN, bean.getLongitude());
        cv.put(WEEKDAYS_COLUMN, bean.getWeekdays());
        cv.put(WEEKEND_COLUMN, bean.getWeekend());

        return cv;
    }
}
