package Tools;

import android.util.Log;

public class Logcat {
    private static String TAG = "salar";

    public  static void send(String Msg){
        Log.i(TAG , Msg   );
    }
    public  static void send(double Number){
        Log.i(TAG , String.valueOf(Number)   );
    }
    public  static void send(int Number){
        Log.i(TAG , String.valueOf(Number)   );
    }
    public  static void send(float Number){
        Log.i(TAG , String.valueOf(Number)   );
    }

    public  static void i(String Msg){
        Log.i(TAG , Msg   );
    }
    public  static void d(String Msg){
        Log.d(TAG , Msg   );
    }
    public  static void e(String Msg){
        Log.e(TAG , Msg   );
    }
    public  static void v(String Msg){
        Log.v(TAG , Msg   );
    }
    public  static void w(String Msg){
        Log.w(TAG , Msg   );
    }

}
