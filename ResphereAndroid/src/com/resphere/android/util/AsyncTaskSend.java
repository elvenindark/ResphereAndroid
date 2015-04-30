package com.resphere.android.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class AsyncTaskSend extends AsyncTask<ArrayList<String>, Void, Boolean> {

	public interface OnSendListener{
		public void response(Boolean respuesta);
	}
	
	public AsynRespuesta respuesta = null;
	private String ip;
	private String port;
	private String objeto;
	private int estado;
	Field[] properties;
	Method[] methods;
	
	ArrayList<ArrayList<String>> listofobjects;
			
	public AsyncTaskSend(String ip, String port, String objeto){
		this.ip = ip;
		this.port = port;
		this.objeto = objeto;		
	}
	
	public AsyncTaskSend(String ip, String port, String objeto, int estado, ArrayList<ArrayList<String>> listofobjects){
		this.ip = ip;
		this.port = port;
		this.objeto = objeto;
		this.estado = estado;
		this.listofobjects = listofobjects;
	}
	
	public interface OnTaskCompleted{
	    void onTaskCompleted(boolean value);
	}
	
	@Override
    protected void onPreExecute() {
            Log.d("app","onPreExecute");
    }
	
	@Override
	protected Boolean doInBackground(ArrayList<String>... arg0) {
		// TODO Auto-generated method stub
			
		Log.d("app","doInBackground");
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> valores = new ArrayList<String>();
		atributos.addAll(arg0[0]);		
		
		if(estado == 1){
			return sendArray(atributos);
		}else if(estado == 2){
			return null;
		}else{
			valores.addAll(arg0[1]);
			return sendObject(atributos, valores);
		}
	}

	public void onPostExecute(Boolean respuesta) {
	        super.onPostExecute(respuesta);
	        	  
	}
	
	public boolean sendObject(ArrayList<String> atributos, ArrayList<String> valores){
		
		JSONObject jsonObj = new JSONObject();
		try {
			for(int i = 0; i < atributos.size(); i++){			
				jsonObj.put(atributos.get(i), valores.get(i));
				Log.d("app",atributos.get(i)+";"+valores.get(i));
			}
			String restweb = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model."+objeto+"/";
			Log.e("ip+port", restweb);
			HttpPost httpPost = new HttpPost(restweb);
			StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpPost);
			Log.d("app",jsonObj.toString());						
			client.getConnectionManager().shutdown();	
			return true;
		}
		 catch (JSONException e) {
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
	
	public boolean sendArray(ArrayList<String> atributos){
			
		JSONArray jsonArray = new JSONArray();
		
		//codigo anadido, peligroso
		String restweb = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model."+objeto+"/list";
		Log.e("ip+port", restweb);
		
		for(int j = 0; j < listofobjects.size(); j++){
			JSONObject jsonObj = new JSONObject();
			try {
				
				for(int i = 0; i < atributos.size(); i++){
					//Log.d("atributo", " "+atributos.get(i));
					jsonObj.put(atributos.get(i), listofobjects.get(j).get(i));					
					//Log.d("app",atributos.get(i)+";"+listofobjects.get(j).get(i));
				}
				jsonArray.put(jsonObj);	
				Log.d("jsonArray object", jsonArray.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}				
		}
		HttpPost httpPost = new HttpPost(restweb);
		try {
			StringEntity entity = new StringEntity(jsonArray.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpPost);
			Log.d("app",jsonArray.toString());
			client.getConnectionManager().shutdown();
			return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return true;
	}

	
}
