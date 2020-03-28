package nz.dcoder.covidtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.util.Deque;
import java.util.UUID;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "locations";
    private static final String TABLE_locations = "locations";
    public DbHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_locations + "(" +
                "uuid TEXT PRIMARY KEY," +
                "lon REAL," +
                "lat REAL," +
                "fulllocationinfo TEXT" +
                ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL("CREATE TABLE userdata (" +
                "uuid TEXT PRIMARY KEY, " +
                "fullname TEXT DEFAULT NULL, " +
                "creationtime DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "updatetime DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "hascovid INTEGER DEFAULT 0" +
                ")");
//        db.close();
    }
    public synchronized void addLocations(Deque<Location> locationQueue) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (Location location : locationQueue) {
                double lon = location.getLongitude();
                double lat = location.getLatitude();
                String fullLocationInfo = location.toString();
                UUID uuid = UUID.randomUUID();
                ContentValues cValues = new ContentValues();
                String uuidString = uuid.toString();
                cValues.put("uuid", uuidString);
                cValues.put("lon", ""+ lon);
                cValues.put("lat", ""+ lat);
                cValues.put("fulllocationinfo", fullLocationInfo);
                db.insert(TABLE_locations, null, cValues);
            }
            db.close();
        } catch (Exception e) {
            throw e;
        }
    }
    public String addUser() {
        return addUser(null);
    }
    public String addUser(String fullName) {
        try {
            ContentValues cValues = new ContentValues();
            String uuidString = UUID.randomUUID().toString();
            cValues.put("uuid", uuidString);
            cValues.put("fullname", fullName);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("userdata", null, cValues);
//            db.close();
            return uuidString;
        } catch (Exception e) {
            throw e;
        } finally {
            return "";
        }
    }
    public String addOrGetUser() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query("userdata", new String[]{"uuid"}, null, null, null, null, null);
            return cursor.getString(1);
        } catch (Exception e) {
            throw e;
        } finally {
            return "";
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Not yet, first DB version!

        // Drop older table if exist
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_locations);
        // Create tables again
        // onCreate(db);
    }
    public void closeDb() {
        this.getWritableDatabase().close();
    }
}