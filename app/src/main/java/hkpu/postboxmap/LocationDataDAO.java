package hkpu.postboxmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hck on 2/5/2016.
 */
public class LocationDataDAO implements CommonDAO{
    public final static String TABLE_NAME = "LocationData";
    public final static String AREA_COLUMN = "address2";
    public final static String ADDRESS_COLUMN = "address";
    public final static String LAT_COLUMN = "lat";
    public final static String LON_COLUMN = "lng";
    public final static String TYPE_COLUMN = "features";

    private SQLiteDatabase db;

    public LocationDataDAO(Context context, File dbFile)
    {
        db = context.openOrCreateDatabase(dbFile.getAbsolutePath(), SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }

    public List<LocationDataBean> listAll() {
        List<LocationDataBean> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(convertBean(cursor));
        }

        cursor.close();
        return result;
    }

    public List<LocationDataBean> listByType(String type) {
        List<LocationDataBean> result = new ArrayList<>();
        String query = "select * from "+TABLE_NAME+" where "+TYPE_COLUMN+" = '"+type+"'";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            result.add(convertBean(cursor));
        }

        cursor.close();
        return result;
    }

    public String[] listAllType() {
        LinkedList<String> result = new LinkedList<>();
        String query = "select distinct "+TYPE_COLUMN+" from "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(TYPE_COLUMN)));
        }

        cursor.close();

        String[] returnResult = new String[result.size()];
        returnResult = result.toArray(returnResult);
        return returnResult;
    }

    public LocationDataBean convertBean(Cursor cursor) {
        LocationDataBean result = new LocationDataBean();

        result.setLatitude(Double.valueOf(cursor.getString(cursor.getColumnIndex(LAT_COLUMN))));
        result.setLongitude(Double.valueOf(cursor.getString(cursor.getColumnIndex(LON_COLUMN))));
        result.setType(cursor.getString(cursor.getColumnIndex(TYPE_COLUMN)));
        result.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS_COLUMN)));
        result.setArea(cursor.getString(cursor.getColumnIndex(AREA_COLUMN)));

        return result;
    }

    public ContentValues convertContentValues(LocationDataBean bean){
        ContentValues cv = new ContentValues();

        cv.put(AREA_COLUMN, bean.getArea());
        cv.put(ADDRESS_COLUMN, bean.getAddress());
        cv.put(LAT_COLUMN, bean.getLatitude());
        cv.put(LON_COLUMN, bean.getLongitude());
        cv.put(TYPE_COLUMN, bean.getType());

        return cv;
    }
}
