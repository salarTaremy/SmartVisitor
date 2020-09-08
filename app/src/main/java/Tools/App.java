package Tools;

import android.app.Application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static Tools.Convert.ToGariruran;

public class App  extends Application {
    public static final int RequestCodeBasket  = 1;
    public static final int RequestCodeProductList  = 2;
    public static final int RequestCodeDescription = 3;
    public static final int RequestCodeOrder = 4;
    public static final int RequestCodeSuccess  = 1;


    public static boolean isNull(Object obj) {
        return obj == null;
    }
    public static Object isNull(Object obj,Object value) {
        if( obj == null){
            return value;
        }
        else {
            return obj;
        }
    }
}
