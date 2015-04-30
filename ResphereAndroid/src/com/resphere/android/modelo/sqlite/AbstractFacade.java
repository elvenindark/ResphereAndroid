package com.resphere.android.modelo.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resphere.android.util.Reflection;

public abstract class AbstractFacade<T> {
	private Class<T> entityClass;
	private Reflection r;
	private ArrayList<String> atributos;
	private ArrayList<String> valores;
	private String table;
	
	public AbstractFacade(Class<T> entityClass){
		this.entityClass = entityClass;
		r = new Reflection(entityClass);
		this.table = entityClass.getSimpleName();
		atributos = r.returnAtributos();
		//valores = r.returnDatos(entityClass);
	}
	
	public void createTable(){
		SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
		String statement = "id INTEGER PRIMARY KEY, ";
		for (int i = 0; i < r.returnAtributos().size(); i++){
			statement = statement + r.returnAtributos().get(i) + " TEXT";
			if(i != r.returnAtributos().size()-1)
				statement = statement + ",";
		}
		String sqlStatement = "CREATE TABLE IF NOT EXISTS " + entityClass.getSimpleName() + " ("
				+ statement + ")";
		db.execSQL(sqlStatement);
		DatabaseManager.getInstance().closeDatabase();
	}
	
	public void create(T entity){
		SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
		ContentValues values = new ContentValues();	
		valores = r.returnDatos(entity);
		for(int i = 0; i < r.returnAtributos().size(); i++){
			values.put(atributos.get(i), valores.get(i));
		}
		db.insert(table, null, values);
		DatabaseManager.getInstance().closeDatabase();
	}
	
	public T find(String id, String atributo){
		SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
		String[] search = new String[atributos.size()];
		atributos.toArray(search);
		Cursor cursor = db.query(table, search, atributo + "=?", new String[]{id}, null, null, null, null);
		if (cursor != null)
            cursor.moveToFirst();
		T entity = null;
		try {
			entity = entityClass.newInstance();
			//for(int i = 0;){
				
			//}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	public List<T> findAll(){
		return null;
	}
	
	public int edit(T entity){
		return 0;
	}
	
	public void delete(T entity){
		
	}
}
