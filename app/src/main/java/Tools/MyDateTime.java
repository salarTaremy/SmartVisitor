package Tools;

import android.content.Context;
import android.provider.Settings;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Databases.GeneralDbHelper;

import static Tools.Convert.ToGariruran;

public class MyDateTime {
    public static String CurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp ts=new Timestamp(currentTime.getTime());
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        String Ret = formatter.format(ts);
        return Ret;
    }
    public static String CurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp ts=new Timestamp(currentTime.getTime());
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String Ret = formatter.format(ts);
        Ret = ToGariruran(Ret);
        return Ret;
    }
    public static String GetDateTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
    public static int  GeorgianToPersian(Context ctx, int date){
        GeneralDbHelper db = new GeneralDbHelper( ctx );
        Entities.Calendar calendar =  db.GetCalendar(date);
        return calendar.Persian_Date;
    }
    public static int  PersianToGeorgian(Context ctx, int date){
        GeneralDbHelper db = new GeneralDbHelper( ctx );
        Entities.Calendar calendar =  db.GetCalendar(date);
        return calendar.Gregorian_Date;
    }
}
