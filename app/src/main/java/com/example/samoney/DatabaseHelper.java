package com.example.samoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static DatabaseHelper sInstance;
    private static final String DATABASE_NAME = "Samoney.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BILLS = "bills";
    private static final String TABLE_OPERATIONS = "operations";
    private static final String TABLE_LIMITS = "limits";

    private static final String KEY_BILL_ID = "_id";
    private static final String KEY_BILL_CATEGORY = "bill_category";
    private static final String KEY_BILL_NAME = "bill_name";
    private static final String KEY_BILL_BALANCE = "bill_balance";
    private static final String KEY_BILL_FROM = "bill_from";
//    private static final String KEY_BILL_LAST_OPERATIONS = "";

    private static final String KEY_OPERATION_ID = "_id";
    private static final String KEY_OPERATION_NAME = "operation_name";
    private static final String KEY_OPERATION_SUM = "operation_sum";
//    private static final String KEY_OPERATION_BILL = "_id";
    private static final String KEY_OPERATION_DATE = "operation_date";
    private static final String KEY_OPERATION_COMMENTS = "operation_comments";
    private static final String KEY_OPERATION_OBJECT = "operation_object";

    private static final String KEY_LIMIT_ID = "_id";
    private static final String KEY_LIMIT_NAME = "limit_name";
    private static final String KEY_LIMIT_SUM = "limit_sum";
    //    private static final String KEY_OPERATION_BILL = "_id";
    private static final String KEY_LIMIT_PERIOD_COUNT = "limit_period_count";
    private static final String KEY_LIMIT_PERIOD_UNIT = "limit_period_unit";
    private static final String KEY_LIMIT_TYPE = "limit_type";
    private static final String KEY_LIMIT_OBJECT = "limit_object";


    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

//        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BILLS_TABLE = "CREATE TABLE " + TABLE_BILLS +
                " (" +
                    KEY_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_BILL_NAME + " TEXT, " +
                    KEY_BILL_CATEGORY + " TEXT, " +
                    KEY_BILL_BALANCE + " INTEGER, " +
                    KEY_BILL_FROM + " INTEGER " + ");";

        String CREATE_OPERATIONS_TABLE = "CREATE TABLE " + TABLE_OPERATIONS +
                " (" +
                    KEY_OPERATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_OPERATION_NAME + " TEXT, " +
                    KEY_OPERATION_SUM + " INTEGER, " +
                    KEY_OPERATION_COMMENTS + " TEXT, " +
                    KEY_OPERATION_OBJECT + " TEXT " +
                ");";

        String CREATE_LIMITS_TABLE = "CREATE TABLE " + TABLE_LIMITS +
                " (" +
                    KEY_LIMIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_LIMIT_NAME + " TEXT, " +
                    KEY_LIMIT_SUM + " TEXT, " +
                    KEY_LIMIT_OBJECT + " TEXT " +
                ");";

        db.execSQL(CREATE_BILLS_TABLE);
        db.execSQL(CREATE_OPERATIONS_TABLE);
        db.execSQL(CREATE_LIMITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIMITS);
            onCreate(db);
        }
    }

    void addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_BILL_NAME, bill.name);
        cv.put(KEY_BILL_CATEGORY, bill.category);
        cv.put(KEY_BILL_BALANCE, bill.balance);
        cv.put(KEY_BILL_FROM, bill.from);


        long result = db.insert(TABLE_BILLS,null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addOperation(Operation operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_OPERATION_NAME, operation.name);
        cv.put(KEY_OPERATION_SUM, operation.sum);
        cv.put(KEY_OPERATION_COMMENTS, operation.comments);
        cv.put(KEY_OPERATION_OBJECT, operation.object);

        long result = db.insert(TABLE_OPERATIONS,null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addLimit(Limit limit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_LIMIT_NAME, limit.name);
        cv.put(KEY_LIMIT_SUM, limit.sum);
        cv.put(KEY_LIMIT_OBJECT, limit.object);

        long result = db.insert(TABLE_LIMITS,null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getBillById(String id) {
        String query = "SELECT * FROM " + TABLE_BILLS + " WHERE _id = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor getAllBills() {
        String query = "SELECT * FROM " + TABLE_BILLS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor getAllOperations() {
        String query = "SELECT * FROM " + TABLE_OPERATIONS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor getOperationsByBill(String billId) {
        String query = "SELECT * FROM " + TABLE_OPERATIONS + " WHERE operation_object = '" + billId + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor getLimitsByBill(String billId) {
        String query = "SELECT * FROM " + TABLE_LIMITS + " WHERE limit_object = '" + billId + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor getAllLimits() {
        String query = "SELECT * FROM " + TABLE_LIMITS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_BILL_NAME, bill.name);
        cv.put(KEY_BILL_CATEGORY, bill.category);
        cv.put(KEY_BILL_BALANCE, bill.balance);
        cv.put(KEY_BILL_FROM, bill.from);

        long result = db.update(TABLE_BILLS, cv,"_id=?", new String[]{ bill._id });
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void updateOperation(Operation operation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_OPERATION_NAME, operation.name);
        cv.put(KEY_OPERATION_SUM, operation.sum);
        cv.put(KEY_OPERATION_COMMENTS, operation.comments);
        cv.put(KEY_OPERATION_OBJECT, operation.object);

        long result = db.update(TABLE_OPERATIONS, cv,"_id=?", new String[]{ operation._id });
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

//    void updateLimit(Limit limit) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(KEY_LIMIT_SUM, limit.sum);
//        cv.put(KEY_LIMIT_PERIOD_COUNT, limit.periodCount);
//        cv.put(KEY_LIMIT_PERIOD_UNIT, limit.periodUnit);
//        cv.put(KEY_LIMIT_TYPE, limit.type);
//        cv.put(KEY_LIMIT_OBJECT, limit.object);
//
//        long result = db.update(TABLE_LIMITS, cv,"_id=?", new String[]{ limit._id });
//        if (result == -1) {
//            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
//        }
//    }

    void deleteOneBill(String billId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BILLS, "_id=?", new String[]{ billId });
    }

    void deleteOneOperation(String operationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OPERATIONS, "_id=?", new String[]{ operationId });
    }

    void deleteOneLimit(String limitId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIMITS, "_id=?", new String[]{ limitId });
    }
}
