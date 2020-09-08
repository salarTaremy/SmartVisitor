package Tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.text.MessageFormat;

public class Convert  {
    public static String toSeprate(int no){
        return String.format("%,d", no);
        // or use :
        //return  MessageFormat.format("{0}" , no);
    }
    public static String toSeprate(long no){
        return String.format("%,d", no);
        // or use :
        //return  MessageFormat.format("{0}" , no);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public  static  String ToPersian(String Str ){
        Str = Str.replace("0","۰");
        Str = Str.replace("1","۱");
        Str = Str.replace("2","۲");
        Str = Str.replace("3","۳");
        Str = Str.replace("4","۴");
        Str = Str.replace("5","۵");
        Str = Str.replace("6","۶");
        Str = Str.replace("7","۷");
        Str = Str.replace("8","۸");
        Str = Str.replace("9","۹");
        return  Str;
    }
    public  static  String ToGariruran(String Str ){
        Str = Str.replace("۰","0");
        Str = Str.replace("۱","1");
        Str = Str.replace("۲","2");
        Str = Str.replace("۳","3");
        Str = Str.replace("۴","4");
        Str = Str.replace("۵","5");
        Str = Str.replace("۶","6");
        Str = Str.replace("۷","7");
        Str = Str.replace("۸","8");
        Str = Str.replace("۹","9");
        return  Str;
    }
}