package com.resphere.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfiguracionPreferencias {
	
	private Context context;
	public ConfiguracionPreferencias(Context context){
		this.context = context;
	}
	
	public void setEventoPref(String msg){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();        
        editor.putString("evento", msg);
        editor.commit(); 
	}
	
	public String getEventoPref(){
		SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		return prefe.getString("evento", "");
	}
	
	public Boolean isNewEventoPref(){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		if(preferencias.getString("evento", "").equalsIgnoreCase("nuevo"))      
			return true;			
		return false;
	}
	
	public Boolean isDBPref(){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		if(preferencias.getString("db", "").equalsIgnoreCase("no existe"))      
			return false;			
		return true;
	}
	
	public String getDBPref(){
		SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		return prefe.getString("db", "");
	}
	
	public void setDBPref(String msg){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();        
        editor.putString("db", msg);
        editor.commit(); 
	}
	
	public String getIpPref(){
		SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		return prefe.getString("ip", "");
	}
	
	public String getPortPref(){
		SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
		return prefe.getString("port", "");
	}
	
	public Boolean isUbicacionesPref(){
		  SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
	      if(prefe.getString("ubicaciones","").equalsIgnoreCase("cargado"))
	    	  return true;
	      return false;
	}
	
	public void setUbicacionesPref(String msg){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();        
        editor.putString("ubicaciones", msg);
        editor.commit();        
	}
	
	public Boolean isIpPortPref(){
		 SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
	      if(prefe.getString("ip","") == null || prefe.getString("ip","").equals(""))
	    	  if(prefe.getString("port","") == null || prefe.getString("port","").equals(""))
	    		  return false;
	      return true;
	}
	
	public Boolean isIpPref(){
		 SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
	      if(prefe.getString("ip","") != null || !prefe.getString("ip","").equals(""))	    	 
	    		  return true;
	      return false;
	}
	
	public Boolean isPortPref(){
		 SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
	     if(prefe.getString("port","") != null || !prefe.getString("port","").equals(""))
	    		  return true;
	      return false;
	}
	
	public void setIpPuertoPref(String ip, String port){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();
        editor.putString("ip", ip);
        editor.putString("port", port);        
        editor.commit();        
	}
	
	public void setIpPref(String ip){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();
        editor.putString("ip", ip);                
        editor.commit();   
	}
	
	public void setPortPref(String port){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();
        editor.putString("port", port);                
        editor.commit(); 
	}
	
	public Boolean isUbicacionesBDPref(){
		 SharedPreferences prefe = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
	      if(prefe.getString("db","").equalsIgnoreCase("cargado"))
	    	  return true;
	      return false;
	}
	
	public void setUbicacionesBDPref(String msg){
		SharedPreferences preferencias = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();        
        editor.putString("db", msg);
        editor.commit(); 
	}	
}
