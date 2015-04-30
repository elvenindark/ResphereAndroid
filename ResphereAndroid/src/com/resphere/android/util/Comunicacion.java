package com.resphere.android.util;

import java.util.ArrayList;

public class Comunicacion<T> {
	
	private Class<T> entityClass;
	private Class clazz;
	private String nombreClase;
	private String ip;
	private String port;

	public Comunicacion(Class<T> entityClass, String nombreClase, String ip, String port){
		this.entityClass = entityClass;
		this.nombreClase = nombreClase;
		this.ip = ip;
		this.port = port;
	}
	
	public Boolean enviarObjeto(T e){
				
		clazz = e.getClass();				
		Reflection clase = new Reflection(clazz);	
		ArrayList<String> atributos = new ArrayList<String>();	
		ArrayList<String> values = new ArrayList<String>();
		values = (ArrayList) clase.returnDatos(e);		
		atributos = (ArrayList) clase.returnFields().clone();	
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, nombreClase);
		enviar.execute(atributos, values);
		return true;
	}
	
	public Boolean enviarListaObjetos(ArrayList<T> lista){		
		
		ArrayList<ArrayList<String>> listofobjects = new ArrayList<ArrayList<String>>();		
		ArrayList<String> atributos = new ArrayList<String>();					
		ArrayList<String> values = new ArrayList<String>();
		for(int i = 0; i < lista.size(); i++){
			clazz = lista.get(i).getClass();
			Reflection clase = new Reflection(clazz);			
			values = (ArrayList) clase.returnDatos(lista.get(i));		
			atributos = (ArrayList) clase.returnFields().clone();
			listofobjects.add(values);
		}
		AsyncTaskSend enviar = new AsyncTaskSend(ip, port, nombreClase, 1, listofobjects);
		enviar.execute(atributos);	
		return true;
	}
	
	public ArrayList<T> getListaObjetos(){
		return null;
	}
	
	public T getObjeto(){
		return null;
	}
	
}
