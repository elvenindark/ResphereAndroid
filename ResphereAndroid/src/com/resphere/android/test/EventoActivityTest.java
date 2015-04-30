package com.resphere.android.test;

import java.util.List;

import junit.framework.TestCase;

import com.resphere.android.modelo.Evento;
import com.resphere.android.modelo.TipoEvento;
import com.resphere.android.vista.EventoActivity;

public class EventoActivityTest extends TestCase {

	protected Evento e;
	protected List<TipoEvento> lista;
	EventoActivity actividad;
	
	public void init(){
		e = new Evento();
		actividad = new EventoActivity();
	}
	
	public void initList(){
		TipoEvento x = new TipoEvento();
		lista.add(x);
	}
	
	public void testGuardarDatosEvento() {
		init();
		
		assertTrue(actividad.guardarDatos(e));
		//fail("Not yet implemented");
	}

	public void testGuardarDatosListOfTipoEvento() {

		for(int i = 0; i < 10; i ++)
			initList();
		//fail("Not yet implemented");
		assertTrue(actividad.guardarDatos(lista));
	}

}
