package com.resphere.android.vista;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.util.Preferencias;
import com.resphere.android.vista.adapter.CustomList;
import com.resphere.android.vista.adapter.FilaItem;
import com.resphere.android.vista.evolucion.ListaEventos;

public class InicioActivity extends Activity {
	
	private String[] titles;	
	private String[] descriptions;
	
	//Deben ser el mismo numero de items que los titulos y subtitulos
	public static final Integer[] images = { R.drawable.gecheo,
        R.drawable.open, R.drawable.sphere, R.drawable.monitoreo, R.drawable.settings };

	ListView listView;
    List<FilaItem> filaItems;
    String identificador;
    
    private ConfiguracionPreferencias preferencias;
    private Preferencias pref;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		preferencias = new ConfiguracionPreferencias(this);
		pref = new Preferencias();
		pref.init(this);
		titles = getResources().getStringArray(R.array.menuInicioTitles);	
		descriptions = getResources().getStringArray(R.array.menuInicioSubtitles);	
		preferencias = new ConfiguracionPreferencias(this);
		CustomList adapter = new CustomList(InicioActivity.this, titles, descriptions, images);
        listView=(ListView)findViewById(R.id.listInicio1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    	if(preferencias.isIpPortPref())
                    	switch(position){
                    	case 0:                    		
                    		if(preferencias.isDBPref()){
                    			identificador = String.valueOf(System.currentTimeMillis()/1000);
                    			preferencias.setEventoPref("nuevo");
                    			pref.setNuevo(true);
	                    		Intent intent1 = new Intent(InicioActivity.this, PrincipalActivity.class);
	                     		intent1.putExtra("identificador", identificador);             		 
	                     		startActivity(intent1);
                     		}
                    		else{
                    			Toast.makeText(view.getContext(), "Revise la configuracion inicial", Toast.LENGTH_SHORT).show();
                    		}
                     		break;
                    	case 1:
                    		if(preferencias.isDBPref()){                    			
                    			preferencias.setEventoPref("actual");
                    			pref.setNuevo(false);
	                    		Intent intent1 = new Intent(InicioActivity.this, ListaEventos.class);	                     		             		 
	                     		startActivity(intent1);
                     		}
                    		else{
                    			Toast.makeText(view.getContext(), "Configure la configuracion inicial", Toast.LENGTH_SHORT).show();
                    		}
                     		break;
                    	case 2:
                    		if(preferencias.isDBPref()){ 
                    			//if(preferencias.isIpPortPref()){
                    			//preferencias.setEventoPref("actual");
	                    		Intent intent1 = new Intent(InicioActivity.this, RespuestaHumanitariaActivity.class);	                     	             		 
	                     		startActivity(intent1);
                    			//}else{
                    				//Toast.makeText(view.getContext(), "Configure la configuracion inicial", Toast.LENGTH_SHORT).show();
                    			//}                    			
                     		}
                    		else{
                    			Toast.makeText(view.getContext(), "Configure la configuracion inicial", Toast.LENGTH_SHORT).show();
                    		}
                     		break;                    	
                    	case 3:
                    		identificador = String.valueOf(System.currentTimeMillis()/1000);
                    		preferencias.setEventoPref("nuevo");
                    		Intent intent4 = new Intent(InicioActivity.this, ConfiguracionActivity.class);
                     		intent4.putExtra("identificador", identificador);             		 
                     		startActivity(intent4);
                     		break;
                    	default: break;
                    	}else{
                    		Intent intent14 = new Intent(InicioActivity.this, ConfiguracionActivity.class);
                     		//intent14.putExtra("index", itemPosition);             		
                     		startActivity(intent14);
                    	}
                    	
                    }
                });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_inicio, menu);
		return true;
	}

}
