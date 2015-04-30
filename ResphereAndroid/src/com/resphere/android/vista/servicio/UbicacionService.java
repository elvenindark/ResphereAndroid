package com.resphere.android.vista.servicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Canton;
import com.resphere.android.modelo.Parroquia;
import com.resphere.android.modelo.Provincia;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.JsonCanton;
import com.resphere.android.util.JsonParroquia;
import com.resphere.android.util.JsonProvincia;
import com.resphere.android.util.LoadUbicacion;

public class UbicacionService extends IntentService {

	public static final String ACTION_PROGRESO = "com.resphere.android.action.PROGRESO";
	public static final String ACTION_FIN = "com.resphere.android.action.FIN";
	private String file1 = "provincias.txt";
	private String file2 = "cantones.txt";
	private String file3 = "parroquias.txt";
	private String data;
	
	public UbicacionService() {
		super("UbicacionService");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {			
					
			ConfiguracionPreferencias preferencias = new ConfiguracionPreferencias(this);
			LoadUbicacion lu = new LoadUbicacion(this);
			int option = intent.getIntExtra("iteraciones", 1);
			int i = 2;
			Intent bcIntent = new Intent();
			bcIntent.setAction(ACTION_PROGRESO);
			bcIntent.putExtra("progreso", i*10);
			Log.d(UbicacionService.class.getName(),String.valueOf(option));
			switch(option){
				case 2:					
					ApplicationDataContext db = DataContext.getInstance(this);					
					db.deleteDatabase();
					preferencias.setUbicacionesBDPref("borrado");
					preferencias.setDBPref("no existe");
					bcIntent.setAction(ACTION_PROGRESO);
					bcIntent.putExtra("progreso", i*20);
					sendBroadcast(bcIntent);
					Log.d(UbicacionService.class.getName(),"borrado efectivo");
				break;
				case 1:		
					List<Provincia> provincias;
					List<Canton> cantones;
					List<Parroquia> parroquias;	
					provincias = loadProvincias(lu.read(file1));
					cantones = loadCantones(lu.read(file2));
					parroquias = loadParroquias(lu.read(file3));
					if(saveProvincias(provincias)){
						//Comunicamos el progreso
						i+=2;				
						bcIntent.setAction(ACTION_PROGRESO);
						bcIntent.putExtra("progreso", i*10);
						sendBroadcast(bcIntent);
						Log.d(UbicacionService.class.getName(),"succesfull provincias save");
					}else{
						Log.d(UbicacionService.class.getName(),"error on provincias save");
					}
					if(saveCantones(cantones)){
						//Comunicamos el progreso
						i+=2;
						//Intent bcIntent = new Intent();
						bcIntent.setAction(ACTION_PROGRESO);
						bcIntent.putExtra("progreso", i*10);
						sendBroadcast(bcIntent);
						Log.d(UbicacionService.class.getName(),"succesfull cantones save");
					}else{
						Log.d(UbicacionService.class.getName(),"error on cantones save");
					}
					if(saveParroquias(parroquias)){
						//Comunicamos el progreso
						i+=2;
						//Intent bcIntent = new Intent();
						bcIntent.setAction(ACTION_PROGRESO);
						bcIntent.putExtra("progreso", i*10);
						sendBroadcast(bcIntent);
						Log.d(UbicacionService.class.getName(),"succesfull parroquias save");
						//ConfiguracionPreferencias preferencias = new ConfiguracionPreferencias(this);
						preferencias.setUbicacionesBDPref("cargado");
						preferencias.setDBPref("cargado");
						if(preferencias.isUbicacionesBDPref())
							Log.d(UbicacionService.class.getName(),"se guardo correctamente archivo preferencias guardar flag");
						else
							Log.d(UbicacionService.class.getName(),"problemas en archivo preferencias guardar flag");
						}else{
							Log.d(UbicacionService.class.getName(),"error on parroquias save");
						}
					break;
					default:
						Log.d(UbicacionService.class.getName(),"no se escogio opcion");
					break;
						
				}
		//Se comunica el fin de la tarea
		//Intent bcIntent = new Intent();
        bcIntent.setAction(ACTION_FIN);
        sendBroadcast(bcIntent);
	}

	public Boolean saveProvincias(List<Provincia> provincias){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		
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
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		
		dbManager.getCantonDAO().addAll(cantones);
		try {
			dbManager.getCantonDAO().save();
			Intent bcIntent = new Intent();
			bcIntent.setAction(ACTION_PROGRESO);
			bcIntent.putExtra("progreso", 7);
			sendBroadcast(bcIntent);
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean saveParroquias(List<Parroquia> parroquias){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
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
			Log.d(UbicacionService.class.getName(),"loading provincias ");
			return provincias;
		}catch(Exception e){
			Log.d(UbicacionService.class.getName(),e.getMessage());
			return null;
		}
	}
	
	public List<Canton> loadCantones(String entrada){
		JsonCanton getter = new JsonCanton(Canton.class);
		List<Canton> cantones;
		try{
			cantones = getter.getCantones(entrada);
			Log.d(UbicacionService.class.getName(),"loading cantones ");
			return cantones;
		}catch(Exception e){
			Log.d(UbicacionService.class.getName(),e.getMessage());
			return null;
		}
	}
	
	public List<Parroquia> loadParroquias(String entrada){
		JsonParroquia getter = new JsonParroquia(Parroquia.class);
		List<Parroquia> parroquias;
		try{
			parroquias = getter.getParroquias(entrada);
			Log.d(UbicacionService.class.getName(),"loading parroquias ");
			return parroquias;
		}catch(Exception e){
			Log.d(UbicacionService.class.getName(),e.getMessage());
			return null;
		}
	}
	
	 @SuppressWarnings("deprecation")
	public void save(String file){
	      data = "{abas:primetime}";
	      try {
	         FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
	         fOut.write(data.getBytes());
	         fOut.close();	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	 
	 public String read(String file) {
		 FileInputStream fin = null;
		 Scanner sc = null;
		 StringBuilder sb = new StringBuilder();
		 try{
	         fin = openFileInput(file);	    
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
