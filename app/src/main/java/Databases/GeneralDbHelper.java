package Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import DataTransferObjects.CustomerPathDTO;
import DataTransferObjects.OrderDTO;
import DataTransferObjects.ProductDTO;
import Entities.Calendar;
import Entities.Customer;
import Entities.Order;
import Entities.PriceList;
import Entities.PriceListDetail;

import static Tools.MyDateTime.CurrentDate;


public class GeneralDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static String dbName = "SmartVisitorDB.db";
    private static int dbVersion = 1;
    //===========================================================================
    public GeneralDbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public Calendar GetCalendar(){
        String Date = CurrentDate();
        Calendar calendar = new Calendar();
        String Qry =    "select c.Persian_Date,Gregorian_Date,Pr_DateDisplay,Gr_DateDisplay,Pr_Year,Pr_Month,Pr_Day,DayOfWeek ,w.DayTitle,d.DayName,m.MonthName\n" +
                "from Calendar c\n" +
                "INNER JOIN Weeks w on w.ID = c.DayOfWeek\n" +
                "inner join Days d on d.ID = c.Pr_Day\n" +
                "inner join Month m on m.ID = c.Pr_Month\n" +
                "where Gregorian_Date = " +  Date ;
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor  =db.rawQuery(Qry, null);
        if (cursor.moveToFirst()) {
            do {
                calendar.Persian_Date = cursor.getInt(0);
                calendar.Gregorian_Date = cursor.getInt(1);
                calendar.Pr_DateDisplay = cursor.getString(2);
                calendar.Gr_DateDisplay = cursor.getString(3);
                calendar.Pr_Year = cursor.getInt(4);
                calendar.Pr_Month = cursor.getInt(5);
                calendar.Pr_Day = cursor.getInt(6);
                calendar.DayOfWeek = cursor.getInt(7);
                calendar.DayTitle = cursor.getString(8);
                calendar.DayName = cursor.getString(9);
                calendar.MonthName = cursor.getString(10);


            } while (cursor.moveToNext());
            return  calendar ;
        }
        cursor.close();
        return   null ;
    }
    public Calendar GetCalendar(int Date){

        String FieldName = "";
        if (Date < 20000101) {
            FieldName = "Gregorian_Date";
        }else {
            FieldName = "Persian_Date";
        }

        Calendar calendar = new Calendar();
        String Qry =    "select c.Persian_Date,Gregorian_Date,Pr_DateDisplay,Gr_DateDisplay,Pr_Year,Pr_Month,Pr_Day,DayOfWeek ,w.DayTitle,d.DayName,m.MonthName\n" +
                "from Calendar c\n" +
                "INNER JOIN Weeks w on w.ID = c.DayOfWeek\n" +
                "inner join Days d on d.ID = c.Pr_Day\n" +
                "inner join Month m on m.ID = c.Pr_Month\n" +
                "where " +  FieldName  + " = " +  Date ;
        SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor  =db.rawQuery(Qry, null);
        if (cursor.moveToFirst()) {
            do {
                calendar.Persian_Date = cursor.getInt(0);
                calendar.Gregorian_Date = cursor.getInt(1);
                calendar.Pr_DateDisplay = cursor.getString(2);
                calendar.Gr_DateDisplay = cursor.getString(3);
                calendar.Pr_Year = cursor.getInt(4);
                calendar.Pr_Month = cursor.getInt(5);
                calendar.Pr_Day = cursor.getInt(6);
                calendar.DayOfWeek = cursor.getInt(7);
                calendar.DayTitle = cursor.getString(8);
                calendar.DayName = cursor.getString(9);
                calendar.MonthName = cursor.getString(10);


            } while (cursor.moveToNext());
            return  calendar ;
        }
        cursor.close();
        return   null ;
    }
    public PriceList GetPriceListByCustomer(Customer customer) {
        PriceList priceList  = new PriceList() ;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query =  "select p.ID,P.BeginDate ,P.EndDate,P.Description\n" +
                    "from PriceList p\n" +
                    "inner join LinkPriceListCustomer Lnk on Lnk.ID_PriceList  =  p.ID\n" +
                    "where Lnk.ID_CustomerType = "+ customer.idType +" and Lnk.ID_CustomerGroup = "+customer.idGroup+"  \n" +
                    "LIMIT 1";
            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                priceList = new PriceList();
                do {
                    priceList.ID=cr.getInt(0);
                    priceList.BeginDate=cr.getInt(1);
                    priceList.EndDate=cr.getInt(2);
                    priceList.Description=cr.getString(3);
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            priceList = null;
            Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return priceList;
    }
    public List<CustomerPathDTO> GetCustomerPathDTOList() {
        List<CustomerPathDTO>  Lst= new ArrayList<CustomerPathDTO>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query =          "select PathName , count(PathName)  Count\n" +
                                    "from Customer\n" +
                                    "GROUP BY PathName";

            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                do {
                    Lst.add( new CustomerPathDTO(
                            cr.getString(0) ,
                            cr.getInt(1)
                    ));
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            Lst = null;
            Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Lst;
    }
    public PriceListDetail GetPriceListDetailByProductIDAndPriceID(int ID_priceList , int  ID_Product ) {
        PriceListDetail Prd  = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query =  "select ID ,ID_PriceList,ID_Product,Price,ConsumerPrice,Tax,Duration \n" +
                            "from PriceListDetail \n" +
                            "WHERE ID_Product ="+ ID_Product +"\n" +
                            "And ID_PriceList = " +ID_priceList ;
            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                Prd = new PriceListDetail();
                do {
                    Prd.ID=cr.getInt(0);
                    Prd.ID_PriceList=cr.getInt(1);
                    Prd.ID_Product=cr.getLong(2);
                    Prd.Price=cr.getInt(3);
                    Prd.ConsumerPrice=cr.getInt(4);
                    Prd.Tax=cr.getInt(5);
                    Prd.Duration=cr.getInt(6);

                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            Prd = null;
            Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Prd;
    }
    public List<ProductDTO> GetProductDTOList(int ID_Customer , String Keyword) {
        List<ProductDTO>  Lst= new ArrayList<ProductDTO>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query =  "SELECT\n" +
                            "p.id, \n" +
                            "p.code, \n" +
                            "p.name, \n" +
                            "dt.Tax, \n" +
                            "dt.ConsumerPrice, \n" +
                            "dt.Price, \n" +
                            "dt.Duration\n" +
                            "FROM Customer c\n" +
                            "INNER JOIN CustomerType ct             ON ct.id = c.IdType\n" +
                            "INNER JOIN CustomerGroup cg            ON cg.id = c.IdGroup\n" +
                            "INNER JOIN LinkPriceListCustomer lnk   on lnk.ID_CustomerGroup = cg.id and lnk.ID_CustomerType =  ct.id\n" +
                            "INNER JOIN PriceList pl                On Pl.ID =  lnk.ID_PriceList   \n" +
                            "INNER JOIN PriceListDetail dt          on dt.ID_PriceList = pl.id\n" +
                            "inner JOIN Product p                   on p.id = dt.ID_Product\n" +
                            "WHERE c.id = "+  String.valueOf(ID_Customer) +" \n" +
                            "and (p.name like '%"+Keyword+"%' or p.code  like '"+Keyword+"%') \n" +
                            "\n";

            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                do {

                    ProductDTO Obj = new ProductDTO();
                    Obj.id = cr.getLong(0);
                    Obj.code =cr.getString(1);
                    Obj.name = cr.getString(2);
                    Obj.Tax = cr.getInt(3);
                    Obj.ConsumerPrice = cr.getInt(4);
                    Obj.Price = cr.getInt(5);
                    Obj.Duration = cr.getInt(6);

                    Lst.add( Obj);
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            Lst = null;
            Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Lst;
    }
    public List<OrderDTO> GetOrderDTOList() {
        List<OrderDTO>  Lst= new ArrayList<OrderDTO>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            String query =  "" +
                    "SELECT o.id " +
                    ",o.UUID " +
                    ",C.ID As ID_Customer" +
                    ",o.type" +
                    ",O.ID_PaymentType" +
                    ",o.ClientTime" +
                    ",o.Description" +
                    ",c.name As CustomerName" +
                    ",p.name As PaymentType" +
                    ",o.ClientDate" +
                    ",o.ID_ServerResult" +
                    ",cl.Pr_DateDisplay As PersianDate\n" +
                    ", it.TolalPrice Amount\n" +
                    ", it.TolalOfferPrice \n" +
                    ", it.tax\n" +
                    "from Orders  O\n" +
                    "inner join Customer C on C.ID = O.ID_Customer\n" +
                    "inner join (" +
                    "select   ID_order\n" +
                    ", Sum(Price * Qty) As TolalPrice\n" +
                    ", Sum(Price * Offer) As TolalOfferPrice\n" +
                    ", Sum((Price * Qty ) * tax /100) As tax\n" +
                    "from OrderItem \n" +
                    "GROUP BY ID_order) "+
                    "it on it.ID_order = O.id\n" +
                    "inner join calendar Cl on cl.Persian_Date = O.ClientDate\n" +
                    "inner join PaymentType P on p.id = O.ID_PaymentType\n" +
                    "Where 1=1 " +
                    "Order By o.id desc" ;
            Cursor cr = db.rawQuery(query, null);
            if (cr.moveToFirst()) {
                do {

                    OrderDTO Obj = new OrderDTO();
                    Obj.id = cr.getInt(0);
                    Obj.UUID =cr.getString(1);
                    Obj.ID_Customer =cr.getInt(2);
                    Obj.ID_OrderType =cr.getInt(3);
                    Obj.ID_PaymentType =cr.getInt(4);
                    Obj.ClientTime = cr.getInt(5);
                    Obj.Description = cr.getString(6);
                    Obj.CustomerName = cr.getString(7);
                    Obj.PaymentType = cr.getString(8);
                    Obj.ClientDate = cr.getInt(9);
                    Obj.ID_ServerResult = cr.getInt(10);
                    Obj.PersianDate = cr.getString(11);
                    Obj.Amount = cr.getLong(12);
                    Obj.Offer = cr.getLong(13);
                    Obj.Tax = cr.getLong(14);
                    Lst.add( Obj);
                } while (cr.moveToNext());
            }
        } catch (Exception e) {
            Lst = null;
            Toast.makeText(this.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Lst;
    }


}
