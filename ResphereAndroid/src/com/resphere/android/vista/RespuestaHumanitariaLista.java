package com.resphere.android.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.resphere.android.modelo.Respuesta;
import com.resphere.android.modelo.context.ApplicationDataContext;
import com.resphere.android.modelo.context.DataContext;
import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.server.model.Respuestabysectorv;
import com.resphere.service.RespuestabysectorvFacadeREST;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RespuestaHumanitariaLista extends FragmentActivity {

	private TextView titulo;
	private String idevento;
	private String sector;
	private ListView listRespuesta;
	private ArrayList<Respuesta> respuestasSave;
	private int progressStatus;
	private ProgressDialog progressGR;	
	private ArrayList<String> respuestasString;
	private List<Respuestabysectorv> respuestas;
	
	String [] sectorh = {"Respuesta Agua","Respuesta Alimentación","Respuesta Alojamiento","Respuesta Salud"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_respuesta_humanitaria_lista);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			idevento = (String) extras.get("identificador");
			sector = (String) extras.get("sector");			
		}		
		init();
		setProgress(this);
		getRespuesta(idevento, sector);
	}
	
	public void getRespuesta(final String idevento, final String sector){
		new Thread(new Runnable() {
			  public void run() {					
				  progressStatus = getRespuestas(idevento, sector);
				  if(progressStatus == -1)
					  Log.d("progressStatus", "no hay datos de comunicacion");
				  else if (progressStatus == 100)
					  Log.d("getting Resumen respuestas", "completado");
				  				  
				  progressGR.dismiss();
			  }				  
			}).start();	
		while(progressStatus != 100){			
		}
		if(progressStatus == 100)
			setRespuesta();
	}
	
	public void setRespuesta(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, respuestasString);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listRespuesta.setAdapter(adapter);
	}
	
	public int getRespuestas(String idevento, String sector){
		ConfiguracionPreferencias preferencias = new ConfiguracionPreferencias(this);
		String ip = preferencias.getIpPref();
		String port = preferencias.getPortPref();
		String url = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model.respuestabysectorv";		
		RespuestabysectorvFacadeREST respuesta = new RespuestabysectorvFacadeREST(Respuestabysectorv.class, url);
		try {
			respuestas = respuesta.getAllByIds(idevento, sector);
			Log.d("size: ", String.valueOf(respuestas.size()));
			respuestasString = new ArrayList();
			respuestasSave = new ArrayList();
			Respuesta respu = new Respuesta();
			if(respuestas!=null && respuestas.size()>0){				
				String resp = null;
				for(Respuestabysectorv item:respuestas){					
					resp = item.getIdindicadorclave() + " " + item.getIdsectorhumanitario() + " " + item.getObservacion();
					respu.setIdevento(item.getIdevento());
					respu.setIdindicadorclave(item.getIdindicadorclave());
					respu.setIdrespuesta(item.getIdrespuesta());
					respu.setIdsectorhumanitario(item.getIdsectorhumanitario());
					respu.setObservacion(item.getObservacion());
					respuestasSave.add(respu);
					respuestasString.add(resp);
					Log.d("respuestas: ", resp);			
				}	
				if(guardarRespuestas(respuestasSave))
					Log.d("guardar respuestas", "se guardo correctamente");
				else
					Log.d("guardar respuestas", "no se guardo correctamente");
			}else{
				respuestasString.add("no se encontró respuesta para este evento");				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 100;	
	}
	
	public Boolean guardarRespuestas(ArrayList<Respuesta> respuestasSave){
		ApplicationDataContext dbManager = DataContext.getInstance(this);
		dbManager.getRespuestaDAO().addAll(respuestasSave);
		try {
			dbManager.getRespuestaDAO().save();
			return true;
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	public void setProgress(Context context){
		progressGR = new ProgressDialog(context);
		progressGR.setCancelable(true);
		progressGR.setMessage("Descargando respuesta ... ");
		progressGR.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressGR.setProgress(0);
		progressGR.setMax(100);
		progressGR.show();
	}
	
	public void init(){
		listRespuesta = (ListView)this.findViewById(R.id.listViewRespuesta);
		titulo = (TextView)this.findViewById(R.id.txtRespuestaTitle);
		
		titulo.setText(sectorh[Integer.valueOf(sector)-1]);
		progressStatus = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.respuesta_detalle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
