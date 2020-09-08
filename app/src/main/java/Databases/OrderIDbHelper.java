package Databases;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Entities.Order;
import Tools.Logcat;

public class OrderIDbHelper extends DbHelper_Base implements IDbHelper<Order> {
    private static String dbName = "SmartVisitorDB.db";
    private static int dbVersion = 1;
    private static String TableName = "Orders";
    //=============================================================
    private static String col_UUID = "UUID";
    private static String col_id = "id";
    private static String col_PaymentType = "ID_PaymentType";
    private static String col_Type = "Type";
    private static String col_ID_Customer = "ID_Customer";
    private static String col_ClientDate = "ClientDate";
    private static String col_ClientTime = "ClientTime";
    private static String col_Description = "Description";
    private static String col_Latitude = "Latitude";
    private static String col_Longitude = "Longitude";
    private static String col_ID_ServerResult = "ID_ServerResult";



    public OrderIDbHelper(Context context) {
        super(context, dbName, null, dbVersion,TableName);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        //integer primary key AUTOINCREMENT
        String Qry =MessageFormat.format("create table IF NOT EXISTS {0}(  {1} integer primary key , {2} integer ,{3} integer ,{4} integer ,{5} integer ,{6} integer,{7} text ,{8} BLOB,{9} Real,{10} Real,{11} integer)"
                                        , TableName, col_id, col_PaymentType,col_Type, col_ID_Customer, col_ClientDate, col_ClientTime, col_Description,col_UUID,col_Latitude,col_Longitude,col_ID_ServerResult) ;
        //db.execSQL("drop table Orders");
        db.execSQL(Qry);
    }
    public boolean create(Order obj) {
        boolean result = true;
        try {
            UUID UUID = java.util.UUID.randomUUID();
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(col_id, obj.id);
            cv.put(col_PaymentType, obj.ID_PaymentType);
            cv.put(col_Type, obj.ID_OrderType);
            cv.put(col_ID_Customer, obj.customer.id);
            cv.put(col_ClientDate, obj.ClientDate);
            cv.put(col_ClientTime, obj.ClientTime);
            cv.put(col_Description, obj.Description);
            cv.put(col_UUID, String.valueOf(UUID));
            cv.put(col_Latitude, obj.Latitude);
            cv.put(col_Longitude, obj.Longitude);
            cv.put(col_ID_ServerResult, obj.ID_ServerResult);
            result = db.insert(TableName, null, cv) > 0;
            db.close();
        } catch (Exception e) {
            result = false;
            Logcat.e(e.getMessage());
        }
        return result;
    }
    @Override
    public List<Order> search(String keyword) {
        return null;
    }
    public List<Order> findAll() {
        List<Order> lst = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = MessageFormat.format("select {0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10} from {11}", col_id, col_PaymentType,col_Type, col_ID_Customer, col_ClientDate, col_ClientTime, col_Description,col_UUID,col_Latitude,col_Longitude,col_ID_ServerResult, TableName);
            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                lst = new ArrayList<Order>();
                do {
                    Order Obj = new Order();
                    Obj.id=cr.getInt(0);
                    Obj.ID_PaymentType = cr.getInt(1);
                    Obj.ID_OrderType = cr.getInt(2);
                    Obj.customer.id = cr.getInt(3);
                    Obj.ClientDate = cr.getInt(4);
                    Obj.ClientTime = cr.getInt(5);
                    Obj.Description = cr.getString(6);
                    Obj.UUID = cr.getString(7);
                    Obj.Latitude = cr.getDouble (8);
                    Obj.Longitude = cr.getDouble(9);
                    Obj.ID_ServerResult = cr.getInt(10);
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
    public Order find(int id) {
        Order Obj = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = MessageFormat.format("select {0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10} from {11}", col_id, col_PaymentType,col_Type, col_ID_Customer, col_ClientDate, col_ClientTime, col_Description, col_UUID,col_Latitude,col_Longitude, col_ID_ServerResult,TableName);
            Cursor cr = db.rawQuery(MessageFormat.format("{0} where {1} = ?  ", query, col_id), new String[] { Integer.toString(id)  });
            if (cr.moveToFirst()) {
                do {
                    Obj  = new Order();
                    Obj.id=cr.getInt(0);
                    Obj.ID_PaymentType = cr.getInt(1);
                    Obj.ID_OrderType = cr.getInt(2);
                    Obj.customer.id = cr.getInt(3);
                    Obj.ClientDate = cr.getInt(4);
                    Obj.ClientTime = cr.getInt(5);
                    Obj.Description = cr.getString(6);
                    Obj.UUID = cr.getString(7);
                    Obj.Latitude = cr.getDouble (8);
                    Obj.Longitude = cr.getDouble(9);
                    Obj.ID_ServerResult = cr.getInt(10);

                } while (cr.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            Obj = null;
        }
        return Obj;
    }
    @Override
    public boolean update(Order order) {
        return false;
    }
    public boolean updateServerResultID(int ID_Order , int  ID_ServerResult) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query = MessageFormat.format("update {0} set  {1} = {2} where {3} = {4} " ,TableName,col_ID_ServerResult,String.valueOf(ID_ServerResult),col_id,String.valueOf(ID_Order));
            db.execSQL(query);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}