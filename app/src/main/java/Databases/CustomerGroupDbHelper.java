package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import Entities.CustomerGroup;

public class CustomerGroupDbHelper extends DbHelper_Base implements IDbHelper<CustomerGroup> {
    private static String dbName = "SmartVisitorDB.db";
    private static int dbVersion = 1;
    private static String TableName = "CustomerGroup";
    //=============================================================
    private static String col_id = "id";
    private static String col_name = "name";
    public CustomerGroupDbHelper(Context context) {
        super(context, dbName, null, dbVersion,TableName);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        db.execSQL("create table IF NOT EXISTS " + TableName + "(" +
            col_id  + " integer primary key , " +
            col_name + " text" +
            ")"
        );
    }
    public boolean create(CustomerGroup obj) {
        boolean result = true;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_id, obj.getId());
            cv.put(col_name , obj.getName());
            result = db.insert(TableName, null, cv) > 0;
            db.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    @Override
    public List<CustomerGroup> search(String keyword) {
        return null;
    }
    public List<CustomerGroup> findAll() {
        List<CustomerGroup> lst = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = "select " +
                col_id  + "," +
                col_name + " from " +  TableName ;

            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                lst = new ArrayList<CustomerGroup>();
                do {
                    CustomerGroup Obj = new CustomerGroup();
                    Obj.setId(cr.getInt(0));
                    Obj.setName(cr.getString(1));
                    lst.add(Obj);
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            lst = null;
        }
        return lst;
    }
    public boolean delete(int id) {
        boolean result = true;
        try {
            SQLiteDatabase db = getWritableDatabase();
            result = db.delete(TableName, col_id + " = ?", new String[]{ String.valueOf(id) }) > 0;
            db.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    public CustomerGroup find(int id) {
        CustomerGroup obj = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = "select " +
                col_id  + "," +
                col_name +  " from " +  TableName ;
            Cursor cursor = db.rawQuery(query + " where " + col_id + " = ?  " , new String[] { Integer.toString(id)  });
            if (cursor.moveToFirst()) {
                do {
                    obj  = new CustomerGroup();
                    obj.setId(cursor.getInt(0));
                    obj.setName(cursor.getString(1));
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }
    @Override
    public boolean update(CustomerGroup productBrand) {
        return false;
    }
}
