package com.resphere.android.modelo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.resphere.android.util.Reflection;

public class DBManager extends SQLiteOpenHelper {

	private static DBManager mInstance = null;

    private static final String DATABASE_NAME = "Respherelite";
    private static String DATABASE_TABLE;
    private static int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    
    private Context mCtx;
    
    private Reflection r;
	
	/**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    
    private DBManager(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCtx = ctx;        
    }
    
    public static DBManager getInstance(Context ctx) {
        /** 
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information: 
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBManager(ctx.getApplicationContext());
        }
        return mInstance;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void onCreateTable(Class entity, SQLiteDatabase db){
		r = new Reflection(entity);
		String statement = "id INTEGER PRIMARY KEY, ";
		for (int i = 0; i < r.returnAtributos().size(); i++){
			statement = statement + r.returnAtributos().get(i);
			if(i != r.returnAtributos().size()-1)
				statement = statement + ",";
		}
		String sqlStatement = "CREATE TABLE IF NOT EXISTS " + entity.getSimpleName() + " ("
				+ statement + ")";
		db.execSQL(sqlStatement);
	}

}
