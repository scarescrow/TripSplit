package library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "trips";

    private static final String TABLE_TRIP = "trip_table";
    private static final String TABLE_PEOPLE = "trip_people";
    private static final String TABLE_TRANSACTIONS = "trip_transactions";

    private static final String KEY_TRIP_ID = "trip_id";
    private static final String KEY_TRIP_NAME = "trip_name";
    private static final String KEY_TRIP_STATUS = "trip_status";

    private static final String KEY_PEOPLE_ID = "people_id";
    private static final String KEY_PEOPLE_NAME = "people_name";

    private static final String KEY_ID = "id";
    private static final String KEY_TRANSACTION_ID = "transaction_id";
    private static final String KEY_TRANSACTION_NAME = "transaction_name";
    private static final String KEY_TRANSACTION_AMOUNT = "transaction_amount";
    private static final String KEY_TRANSACTION_DATE = "transaction_date";
    private static final String KEY_TRANSACTION_TIME = "transaction_time";


    /*
     * Required Functions
     */

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TRIP_TABLE = "CREATE TABLE " + TABLE_TRIP + "("
                + KEY_TRIP_ID + " INTEGER PRIMARY KEY,"
                + KEY_TRIP_NAME + " TEXT,"
                + KEY_TRIP_STATUS + " INTEGER" + ")";

        String CREATE_TRIP_PEOPLE_TABLE = "CREATE TABLE " + TABLE_PEOPLE + "("
                + KEY_PEOPLE_ID + " INTEGER PRIMARY KEY, "
                + KEY_TRIP_ID + " INTEGER, "
                + KEY_PEOPLE_NAME + " TEXT" + ")";

        String CREATE_TRIP_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TRANSACTION_ID + " INTEGER,"
                + KEY_TRIP_ID + " INTEGER, "
                + KEY_PEOPLE_ID + " INTEGER, "
                + KEY_TRANSACTION_NAME + " TEXT, "
                + KEY_TRANSACTION_AMOUNT + " TEXT, "
                + KEY_TRANSACTION_DATE + " TEXT, "
                + KEY_TRANSACTION_TIME + " TEXT " + ")";

        db.execSQL(CREATE_TRIP_TABLE);
        db.execSQL(CREATE_TRIP_PEOPLE_TABLE);
        db.execSQL(CREATE_TRIP_TRANSACTION_TABLE);

        initialise(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);

    }

    public void resetTables(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRIP, null, null);
        db.delete(TABLE_PEOPLE, null, null);
        db.delete(TABLE_TRANSACTIONS, null, null);

        db.close();

    }

    /*
     * Custom Functions
     */

    public void initialise(SQLiteDatabase db) {

        ContentValues values;

        //Add data for Trip table

        values = new ContentValues();
        values.put(KEY_TRIP_ID, 0);
        values.put(KEY_TRIP_NAME, "Initialising");
        values.put(KEY_TRIP_STATUS, 0);

        db.insert(TABLE_TRIP, null, values);

        //Add data for People table

        values = new ContentValues();
        values.put(KEY_PEOPLE_ID, 0);
        values.put(KEY_TRIP_ID, 0);
        values.put(KEY_PEOPLE_NAME, "Initialising");

        db.insert(TABLE_PEOPLE, null, values);

        //Add data for Transactions table

        values = new ContentValues();
        values.put(KEY_ID, 0);
        values.put(KEY_TRANSACTION_ID, 0);
        values.put(KEY_TRIP_ID, 0);
        values.put(KEY_PEOPLE_ID, 0);
        values.put(KEY_TRANSACTION_NAME, "Initialising");
        values.put(KEY_TRANSACTION_AMOUNT, 0);
        values.put(KEY_TRANSACTION_DATE, "00-00-00");
        values.put(KEY_TRANSACTION_TIME, "00:00:00");

        db.insert(TABLE_TRANSACTIONS, null, values);

    }

    public int getTripCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        int trips = 0;
        Cursor cursor = db.query(TABLE_TRIP, null, null,
                null, null, null, null, null);;
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {

            do {
                trips++;
            } while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return trips;

    }

    /*public void addUser(String name, String email, String username, String year, int role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_USERNAME, username);
        values.put(KEY_YEAR, year);
        values.put(KEY_ROLE, role);

        db.insert(TABLE_LOGIN, null, values);

        values = new ContentValues();
        values.put(KEY_ID, 1);
        values.put(KEY_CHECK, 0);
        values.put(KEY_JSON, "");

        db.insert(TABLE_JSON, null, values);

        db.close();
    }

    public int getRole() {

        SQLiteDatabase db = this.getReadableDatabase();
        int role = 0;
        Cursor cursor = db.query(TABLE_LOGIN, new String[] { KEY_ROLE }, null,
                null, null, null, null, null);;
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            role = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return role;

    } */

}