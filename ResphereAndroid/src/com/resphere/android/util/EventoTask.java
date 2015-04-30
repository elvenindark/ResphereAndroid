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

import com.resphere.android.modelo.Evento;

import android.os.AsyncTask;
import android.util.Log;

public class EventoTask extends AsyncTask<Evento, Void, Boolean> {

	String ip;
	String port;
	Evento evento;
	
	public EventoTask(String ip, String port){
		this.ip = ip;
		this.port = port;
	}
	
	@Override
    protected void onPreExecute() {
            Log.d("app","onPreExecute");
    }
	
	@Override
	protected Boolean doInBackground(Evento... arg0) {
		// TODO Auto-generated method stub
		Log.d("app","doInBackground");
		try{
			evento = arg0[0];
			
			JSONObject jsonObj = new JSONObject();			
			
			try {
				jsonObj.put("fecha", evento.getFecha());
				jsonObj.put("hora", evento.getHora());
				jsonObj.put("descripcion", evento.getDescripcion());
				jsonObj.put("efectos", evento.getEfectos());
				jsonObj.put("amenazas", evento.getAmenazas());
				jsonObj.put("idu", evento.getIdevento());							
				String restweb = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model.evento/";
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

}
