package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import Entities.PriceList;
import Entities.Warehouse;

public class PriceListDbHelpr extends DbHelper_Base implements IDbHelper<PriceList> {
    private static String dbName = "SmartVisitorDB.db";
    private static int dbVersion = 1;
    private static String TableName = "PriceList";
    //=============================================================
    private static String col_ID = "ID";
    private static String col_BeginDate = "BeginDate";
    private static String col_EndDate = "EndDate";
    private static String col_Description = "Description";
    public PriceListDbHelpr(Context context) {
        super(context, dbName, null, dbVersion,TableName);
    }
    public PriceListDbHelpr(Context context, String dbName, SQLiteDatabase.CursorFactory Factory, int dbVersion, String TableName) {
        super(context, dbName, Factory, dbVersion, TableName);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String Qry =MessageFormat.format("create table IF NOT EXISTS {0}( {1} INTEGER primary key , {2} INTEGER, {3} INTEGER, {4} TEXT )", TableName,col_ID,col_BeginDate,col_EndDate,col_Description);
        db.execSQL(Qry);
    }
    public boolean create(PriceList obj) {
        boolean result = true;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_ID, obj.ID);
            cv.put(col_BeginDate, obj.BeginDate);
            cv.put(col_EndDate, obj.EndDate);
            cv.put(col_Description, obj.Description);
            result = db.insert(TableName, null, cv) > 0;
            db.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    @Override
    public List<PriceList> search(String keyword) {
        return null;
    }

    @Override
    public List<PriceList> findAll() {
        List<PriceList> lst = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = MessageFormat.format("select {0},{1},{2} ,{3}  from {4}", col_ID, col_BeginDate,col_EndDate,col_Description, TableName);

            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                lst = new ArrayList<PriceList>();
                do {
                    PriceList Obj = new PriceList();
                    Obj.ID=cr.getInt(0);
                    Obj.BeginDate=cr.getInt(1);
                    Obj.EndDate=cr.getInt(2);
                    Obj.Description=cr.getString(3);
                    lst.add(Obj);
                } while (cr.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            lst = null;
        }
        return lst;
    }

    @Override
    public PriceList find(int id) {
        PriceList Obj = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = MessageFormat.format("select {0},{1},{2},{3} from {6}", col_ID,col_BeginDate,col_EndDate,col_Description, TableName);
            Cursor cr = db.rawQuery(MessageFormat.format("{0} where {1} = ?  ", query, col_ID), new String[] { Integer.toString(id)  });
            if (cr.moveToFirst()) {
                do {
                    Obj  = new PriceList();
                    Obj.ID=cr.getInt(0);
                    Obj.BeginDate=cr.getInt(1);
                    Obj.EndDate=cr.getInt(2);
                    Obj.Description=cr.getString(3);
                } while (cr.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            Obj = null;
        }
        return Obj;
    }

    @Override
    public boolean update(PriceList priceList) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = true;
        try {
            SQLiteDatabase db = getWritableDatabase();
            result = db.delete(TableName, col_ID + " = ?", new String[]{ String.valueOf(id) }) > 0;
            db.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

}
