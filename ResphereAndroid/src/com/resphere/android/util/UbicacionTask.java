package com.resphere.android.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.resphere.android.modelo.Ubicacion;
import com.resphere.android.vista.UbicacionActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class UbicacionTask extends AsyncTask<Ubicacion, Void, Boolean> {

	private Ubicacion ubicacion;
	private Context context = null;
	UbicacionActivity ig;
	public AsynRespuesta response = null;
	private String ip;
	private String port;
	
	public UbicacionTask(String ip, String port){
		this.ip = ip;
		this.port = port;
	}
	
	@Override
    protected void onPreExecute() {
            Log.d("app","onPreExecute");
    }
	
	@Override
	protected Boolean doInBackground(Ubicacion... arg0) {
		// TODO Auto-generated method stub
		
		Log.d("app","doInBackground");
		try{
			ubicacion = arg0[0];
			
			JSONObject jsonObj = new JSONObject();			
			
			try {
				jsonObj.put("provincia", ubicacion.getProvincia());
				jsonObj.put("canton", ubicacion.getCanton());
				jsonObj.put("parroquia", ubicacion.getParroquia());
				jsonObj.put("tipo", ubicacion.getTipo());
				jsonObj.put("sector", ubicacion.getSector());
				jsonObj.put("distancia", ubicacion.getDistancia());
				jsonObj.put("tiempo", ubicacion.getTiempo());
				jsonObj.put("referencia", ubicacion.getReferencia());
				jsonObj.put("latitud", ubicacion.getLatitud());
				jsonObj.put("longitud", ubicacion.getLongitud());
				jsonObj.put("altitud", ubicacion.getAltitud());
				jsonObj.put("idubicacion", ubicacion.getIdevento());
				//http://10.0.2.2:8080/ResphereRest/webresources/com.resphere.jee.modelo.ubicacion/
				//http://186.4.179.159:8181/respherers/webresources/com.resphere.server.model.ubicaciongeografica/
				//HttpPost httpPost = new HttpPost("http://186.47.36.93/ResphereRest/webresources/com.resphere.jee.modelo.ubicacion/");				
				String restweb = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model.ubicacion/";
				Log.e("ip+port", restweb);
				HttpPost httpPost = new HttpPost(restweb);
				StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(httpPost);
				return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
				return false;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		catch(Exception e){
			
		}
		return false;
	}

	public void onPostExecute(Boolean respuesta) {
	        super.onPostExecute(respuesta);	        
	        response.resul(respuesta);
	}
	
	public Context getContext() {
	        return context;
	}
	
	public void setContext(Context context) {
	        this.context = context;
	}
}
