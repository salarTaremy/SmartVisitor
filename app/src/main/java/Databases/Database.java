package Databases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static SQLiteDatabase mydb;
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "SmartVisitorDB.db";
    private static final String DB_PATH = "/databases/";
    static Context ctx;


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }


    public Database(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DB_NAME);
// Path to the just created empty db
        String outFileName = getDatabasePath();
// if the path doesn't exist first, create it


        File outFile = new File(outFileName);
        if (outFile.exists()){
            return;
        }

        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH);
        if (!f.exists())
            f.mkdir();
// Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
// transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
// Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public void open() {
        File dbFile = ctx.getDatabasePath(DB_NAME);
    }
    public void close() {
        mydb.close();
    }

    private static String getDatabasePath() {

        return ctx.getApplicationInfo().dataDir + DB_PATH
                + DB_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS |
                SQLiteDatabase.CREATE_IF_NECESSARY);
    }
}