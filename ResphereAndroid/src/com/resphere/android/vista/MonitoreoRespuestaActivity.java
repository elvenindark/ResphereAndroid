package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.List;

import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.vista.adapter.CustomList;
import com.resphere.android.vista.adapter.FilaItem;
import com.resphere.server.model.Respuestav;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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
				Toast.makeText(getBaseContext(), "Click on " + String.valueOf(arg2), Toast.LENGTH_SHORT).show();
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_monitoreo_respuesta, menu);
		return true;
	}

}
