package com.resphere.android.modelo.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.resphere.android.modelo.Evento;
import com.resphere.android.util.Reflection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {

	/* // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    
    private ArrayList<String> atributos;
	private ArrayList<String> valores;
    
    private Reflection r;
    
 // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";*/
	
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }
    
    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
    }

	/**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 /*
    // Adding new contact
    public void addContact(Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, object.toString()); // Contact Name
        values.put(KEY_PH_NO, object.toString()); // Contact Phone
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
   
 // Getting single contact
    public Object getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
       Object object = null;
		 Object object = new Object(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return object;
    }
    
 // Getting All Contacts
    public List<Object> getAllContacts() {
        List<Object> contactList = new ArrayList<Object>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Object contact = new Object();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
    
 // Updating single contact
    public int updateContact(Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, object.toString());
        values.put(KEY_PH_NO, object.toString());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(object.toString()) });
    }
    
    // Deleting single contact
    public void deleteContact(Object object) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(object.toString()) });
        db.close();
    }
    
 // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }*/
}
