package com.resphere.android.util;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.resphere.android.vista.UbicacionActivity;

public class GPSTask extends AsyncTask<Void, Void, Posicion> {

	public AsynRespuesta response = null;		
	private LocationManager locManager;
	private LocationListener locListener;
    Posicion posicion;
    boolean providerEnable = false;    
    Context context = null;    
    public GPSTask(Context context){
    	this.context = context;
    }
	
	@Override
    protected void onPreExecute() {
            Log.d("app","onPreExecute");
            //comenzarLocalizacion();
            localizacion();
    }
		
	@Override
	protected Posicion doInBackground(Void... params) {
		// TODO Auto-generated method stub		
		return posicion;
	}

	public void onPostExecute(Posicion respuesta) {
        super.onPostExecute(respuesta);
        response.resul(respuesta);
        //locManager.removeUpdates(locListener);
	}
	
	private void localizacion(){
		try{					
			locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			locListener = new LocationListener() {
		    	public void onLocationChanged(Location loc) {
		    		posicion = new Posicion();
		    		posicion.setLatitud(String.valueOf(loc.getLatitude()));
		            posicion.setLongitud(String.valueOf(loc.getLongitude()));
		            posicion.setAltitud(String.valueOf(loc.getAltitude()));
		    	}
		    	public void onProviderDisabled(String provider){
		    		//lblEstado.setText("Provider OFF");
		    	}
		    	public void onProviderEnabled(String provider){
		    		//lblEstado.setText("Provider ON ");
		    	}
		    	public void onStatusChanged(String provider, int status, Bundle extras){
		    		Log.i("", "Provider Status: " + status);
		    		//lblEstado.setText("Provider Status: " + status);
		    	}
	    	};
	    	
			if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
				 providerEnable=true;
	             //Obtenemos la localizaci—n actual al iniciar
	             Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	             posicion = new Posicion();
	             posicion.setLatitud(String.valueOf(loc.getLatitude()));
		         posicion.setLongitud(String.valueOf(loc.getLongitude()));
		         posicion.setAltitud(String.valueOf(loc.getAltitude()));
			}
			else if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
	            // Location updates provided by the NETWORK_PROVIDER should be handled by the provide listener
	            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, locListener);                        
	            providerEnable=true;
	            
	            //Obtenemos la localizaci—n actual al iniciar
	            Location loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	            posicion = new Posicion();
	            posicion.setLatitud(String.valueOf(loc.getLatitude()));
	            posicion.setLongitud(String.valueOf(loc.getLongitude()));
	            posicion.setAltitud(String.valueOf(loc.getAltitude()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}

}
