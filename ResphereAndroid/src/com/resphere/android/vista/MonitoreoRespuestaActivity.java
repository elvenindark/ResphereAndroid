package com.resphere.android.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.vista.adapter.CustomList;
import com.resphere.android.vista.adapter.FilaItem;
import com.resphere.server.model.Respuestav;
import com.resphere.service.RespuestavFacadeREST;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MonitoreoRespuestaActivity extends Activity {

	private String[] titles;	
	private String[] descriptions;
	
	public static final Integer[] images = { R.drawable.agua,
        R.drawable.alimentacion, R.drawable.alojamiento, R.drawable.salud, R.drawable.settings };
	
	ListView listView;
    List<FilaItem> filaItems;
    String identificador;
    ProgressDialog progressGE;
    int progressStatus = 0;
    List<Respuestav> respuestas;
    ArrayList<String> respuestasString;
    
    private ConfiguracionPreferencias preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoreo_respuesta);
		
		preferencias = new ConfiguracionPreferencias(this);
		titles = getResources().getStringArray(R.array.MonitoreoRespuesta);
		descriptions = getResources().getStringArray(R.array.MonitoreoRespuestaDescriptions);
		preferencias = new ConfiguracionPreferencias(this);		
		CustomList adapter = new CustomList(MonitoreoRespuestaActivity.this, titles, descriptions, images);
        listView=(ListView)findViewById(R.id.listMonitoreo);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, final View v, final int arg2, long arg3) {	
				//Toast.makeText(getBaseContext(), "Click on " + String.valueOf(arg2), Toast.LENGTH_SHORT).show();
				setProgress(v.getContext());
				progressStatus = 0;	 
	 
				new Thread(new Runnable() {
				  public void run() {					
					  progressStatus = getEventos(arg2 + 1);
					  if(progressStatus == -1)
						  Log.d("progressStatus", "no hay datos de comunicacion");
					  else if (progressStatus == 100)
						  Log.d("getting Resumen eventos", "completado");					  
					  progressGE.dismiss();
				  }				  
				}).start();	
				while(progressStatus!=100){
					//Log.d("progressStatus:", String.valueOf(progressStatus));
				}
				Log.d("progressStatus:", String.valueOf(progressStatus));
				if(progressStatus == 100)
					setEventos(v.getContext(), arg2 + 1);
			}
        });
	}
	
	public void setProgress(Context context){
		progressGE = new ProgressDialog(context);
		progressGE.setCancelable(true);
		progressGE.setMessage("Descargando respuesta ... ");
		progressGE.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressGE.setProgress(0);
		progressGE.setMax(100);
		progressGE.show();
	}

	public int getEventos(int id) {				
		String ip = preferencias.getIpPref();
		String port = preferencias.getPortPref();
		String url = "http://"+ip+":"+port+"/respherers/webresources/com.resphere.server.model.respuestav";		
		RespuestavFacadeREST respuesta = new RespuestavFacadeREST(Respuestav.class, url);
		try {
			respuestas = respuesta.getAll();
			respuestasString = new ArrayList();
			if(respuestas!=null){				
				String evento = null;
				for(Respuestav item:respuestas){
					evento = item.getEvento() + " " + item.getProvincia() + " " + item.getFecha();					
					respuestasString.add(evento);
					Log.d("eventos: ", evento);
				}		
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
	
	public void setEventos(final Context context, final int indice){		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, respuestasString);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		new AlertDialog.Builder(context)
		  .setTitle("Eventos reportados")
		  .setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				Intent intent = new Intent(MonitoreoRespuestaActivity.this, RespuestaHumanitariaLista.class);
				intent.putExtra("identificador", respuestas.get(which).getIdevento()); 
				intent.putExtra("sector", String.valueOf(indice));
				startActivity(intent);
			}		   
		  }).create().show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_monitoreo_respuesta, menu);
		return true;
	}

}
