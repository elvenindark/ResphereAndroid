package com.resphere.android.modelo.sqlite;

import android.content.Context;

import com.resphere.android.modelo.Evento;

public class EventoFacade extends AbstractFacade<Evento>{

	private DatabaseManager database;
	
	public EventoFacade(Context context, String dbName, int version) {
		super(Evento.class);
		//this.database = new DatabaseManager(context, dbName, version); 
		// TODO Auto-generated constructor stub
	}

}
