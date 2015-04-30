package com.resphere.android.vista.servicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Canton;
import com.resphere.android.modelo.Parroquia;
import com.resphere.android.modelo.Provincia;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.JsonCanton;
import com.resphere.android.util.JsonParroquia;
import com.resphere.android.util.JsonProvincia;
import com.resphere.android.util.LoadUbicacion;
import com.resphere.android.util.Preferencias;

public class UbicacionDAO {

	private List<Provincia> provincias;
	private List<Canton> cantones;
	private List<Parroquia> parroquias;
	private ApplicationDataContext dbManager;
	private LoadUbicacion lu;
	private String data;
	private Context context;
	private Preferencias preferencias;
	
	public UbicacionDAO(Context context){
		dbManager = DataContext.getInstance(context);
		lu = new LoadUbicacion(context);
		this.context = context;
		preferencias = new Preferencias();
		preferencias.init(context);
	}
	
	public Boolean loadUbicacion(){
		List<Provincia> provincias;
		List<Canton> cantones;
		List<Parroquia> parroquias;	
		provincias = loadProvincias(read(preferencias.getProvincia()));
		cantones = loadCantones(read(preferencias.getCanton()));
		parroquias = loadParroquias(read(preferencias.getParroquia()));
		if(provincias != null && provincias.size() > 0){
			if(cantones != null && cantones.size() > 0){
				if(parroquias != null && parroquias.size() > 0){
					if(saveProvincias(provincias)){						
						Log.d(UbicacionDAO.class.getName(),"succesfull provincias save");
						if(saveCantones(cantones)){
							Log.d(UbicacionDAO.class.getName(),"succesfull cantones save");
							if(saveParroquias(parroquias)){
								Log.d(UbicacionDAO.class.getName(),"succesfull parroquias save");
								preferencias.setLoadUbicacion();
								return true;
							}else{
								Log.d(UbicacionDAO.class.getName(),"error on parroquias save");
								return false;
							}
						}else{
							Log.d(UbicacionDAO.class.getName(),"error on cantones save");
							return false;
						}
					}
					else{
						Log.d(UbicacionDAO.class.getName(),"error on provincias save");
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public Boolean saveProvincias(List<Provincia> provincias){
		dbManager.getProvinciaDAO().addAll(provincias);
		try {
			dbManager.getProvinciaDAO().save();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean saveCantones(List<Canton> cantones){
		dbManager.getCantonDAO().addAll(cantones);
		try {
			dbManager.getCantonDAO().save();			
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean saveParroquias(List<Parroquia> parroquias){
		dbManager.getParroquiaDAO().addAll(parroquias);		
		try{
			dbManager.getParroquiaDAO().save();
		}catch(AdaFrameworkException e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	public List<Provincia> loadProvincias(String entrada){
		JsonProvincia getter = new JsonProvincia(Provincia.class);
		List<Provincia> provincias;
		try{
			provincias = getter.getProvincias(entrada);
			Log.d(UbicacionDAO.class.getName(),"loading provincias ");
			return provincias;
		}catch(Exception e){
			Log.d(UbicacionDAO.class.getName()+";archivo no encontrado",e.getMessage());
			return null;
		}
	}
	
	public List<Canton> loadCantones(String entrada){
		JsonCanton getter = new JsonCanton(Canton.class);
		List<Canton> cantones;
		try{
			cantones = getter.getCantones(entrada);
			Log.d(UbicacionDAO.class.getName(),"loading cantones ");
			return cantones;
		}catch(Exception e){
			Log.d(UbicacionDAO.class.getName()+";archivo no encontrado",e.getMessage());
			return null;
		}
	}
	
	public List<Parroquia> loadParroquias(String entrada){
		JsonParroquia getter = new JsonParroquia(Parroquia.class);
		List<Parroquia> parroquias;
		try{
			parroquias = getter.getParroquias(entrada);
			Log.d(UbicacionDAO.class.getName(),"loading parroquias ");
			return parroquias;
		}catch(Exception e){
			Log.d(UbicacionDAO.class.getName()+";archivo no encontrado",e.getMessage());
			return null;
		}
	}
	
	public void save(String file){
	      data = "{abas:primetime}";
	      try {
	         FileOutputStream fOut = context.openFileOutput(file, context.MODE_WORLD_READABLE);
	         fOut.write(data.getBytes());
	         fOut.close();	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	
	 public String read(String file) {
		 //FileInputStream fin = null;
		 InputStream fin = null;
		 Scanner sc = null;
		 StringBuilder sb = new StringBuilder();
		 try{
	         //fin = context.openFileInput(file);	    
	         fin = context.getAssets().open(file);
	         sc = new Scanner(fin, "Latin-1");
	         String temp="";
	         
	         while( sc.hasNextLine()){
	        	 sb.append(sc.nextLine());
	         }	  
	         // note that Scanner suppresses exceptions
	         if (sc.ioException() != null) {
	             throw sc.ioException();
	         }
	         temp = sb.toString();
	        return temp;
	     } catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {
	    	    if (fin != null) {
	    	        try {
						fin.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
	    	    }
	    	    if (sc != null) {
	    	        sc.close();
	    	    }
	    }		 
	 }	 	 
	
}
