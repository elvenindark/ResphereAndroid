package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.resphere.android.util.ConfiguracionPreferencias;
import com.resphere.android.vista.adapter.ExpandableListAdapter;

public class PrincipalActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	private int lastExpandedPosition = -1;
	
	protected int REQUEST_CODE;
	private String identificador;
	
	private ConfiguracionPreferencias preferencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
		    return;
		    }
		// Get data via the key
		identificador = extras.getString("identificador");
		if (identificador != null) {
		  // do something with the data
			//Toast.makeText(this, identificador, Toast.LENGTH_SHORT).show();
		} 
		
		preferencias = new ConfiguracionPreferencias(this);
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        
        LinearLayout footerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item,null);
        TextView footer = (TextView) footerLayout.findViewById(R.id.lblListItem);
        //Mostrar pie o footer del listview
        //expListView.addFooterView(footerLayout);
        footer.setText("Mostrar más resultados");
        
        footer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "No existen mas resultados por el momento", Toast.LENGTH_SHORT).show();
			}
        	
        });
        // setting list adapter
        expListView.setAdapter(listAdapter);       
        
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                    if (lastExpandedPosition != -1
                            && groupPosition != lastExpandedPosition) {
                        expListView.collapseGroup(lastExpandedPosition);
                    }
                    lastExpandedPosition = groupPosition;
            }
        });
		
		expListView.setOnChildClickListener(new OnChildClickListener() {
			 
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int position, long id) {
              
             // ListView Clicked item index
             int itemPosition     = position;
             int sposition 		= groupPosition*10 + position;            
             
     		if(preferencias.isIpPortPref())             
             switch(sposition){
             	case 0: 
             		Intent intent0 = new Intent(PrincipalActivity.this, EventoActivity.class);
             		intent0.putExtra("index", itemPosition);  
             		intent0.putExtra("identificador", identificador);
             		startActivity(intent0);             		
             		break;
             	case 1:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent1 = new Intent(PrincipalActivity.this, UbicacionActivity.class);
	             		intent1.putExtra("index", itemPosition);         
	             		intent1.putExtra("identificador", identificador);             		
	             		startActivity(intent1);
             		}
             		break;
             	case 10:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent2 = new Intent(PrincipalActivity.this, AfectacionPoblacionActivity.class);
	             		intent2.putExtra("index", itemPosition);             		 
	             		intent2.putExtra("identificador", identificador);             		
	             		startActivity(intent2);
             		}
             		break;
             	case 11:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent3 = new Intent(PrincipalActivity.this, AfectacionMediosVidaActivity.class);
	             		intent3.putExtra("index", itemPosition);         
	             		intent3.putExtra("identificador", identificador);             		
	             		startActivity(intent3);
             		}
             		break;
             	case 12:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent4 = new Intent(PrincipalActivity.this, AfectacionViviendasActitivity.class);
	             		intent4.putExtra("index", itemPosition);             		
	             		intent4.putExtra("identificador", identificador);             		
	             		startActivity(intent4);
             		}
             		break;
             	case 20:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent5 = new Intent(PrincipalActivity.this, AfectacionServiciosActivity.class);
	             		intent5.putExtra("index", itemPosition);             		
	             		intent5.putExtra("identificador", identificador);
	             		startActivity(intent5);
             		}
             		break;
             	case 21:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent6 = new Intent(PrincipalActivity.this, AfectacionAccesabilidadActivity.class);
	             		intent6.putExtra("index", itemPosition);             		
	             		intent6.putExtra("identificador", identificador);
	             		startActivity(intent6);
             		}
             		break;
             	case 22:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent7 = new Intent(PrincipalActivity.this, AfectacionSaludActivity.class);
	             		intent7.putExtra("index", itemPosition);             		
	             		intent7.putExtra("identificador", identificador);
	             		startActivity(intent7);
             		}
             		break;
             	case 30:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent8 = new Intent(PrincipalActivity.this, AyudaHumanitariaActivity.class);
	             		intent8.putExtra("index", itemPosition);             		
	             		intent8.putExtra("identificador", identificador);
	             		startActivity(intent8);
             		}
             		break;
             	case 31:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent9 = new Intent(PrincipalActivity.this, ImpactoEventoActivity.class);
	             		intent9.putExtra("index", itemPosition);             		
	             		intent9.putExtra("identificador", identificador);
	             		startActivity(intent9);
             		}
             		break;
             	case 40:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent10 = new Intent(PrincipalActivity.this, NecesidadesUrgentesActivity.class);
	             		intent10.putExtra("index", itemPosition);             		
	             		intent10.putExtra("identificador", identificador);
	             		startActivity(intent10);
             		}
             		break;
             	case 41:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent11 = new Intent(PrincipalActivity.this, NecesidadesRRHHActivity.class);
	             		intent11.putExtra("index", itemPosition);             		
	             		intent11.putExtra("identificador", identificador);
	             		startActivity(intent11);
             		}
             		break;
             	case 42:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent12 = new Intent(PrincipalActivity.this, NecesidadesRecuperacionActivity.class);
	             		intent12.putExtra("identificador", identificador);
	             		intent12.putExtra("index", itemPosition);             		
	             		startActivity(intent12);
             		}
             		break;
             	case 50:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent13 = new Intent(PrincipalActivity.this, ComentariosActivity.class);
	             		intent13.putExtra("index", itemPosition);             		
	             		intent13.putExtra("identificador", identificador);
	             		startActivity(intent13);
             		}
             		break;
             	case 51:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent14 = new Intent(PrincipalActivity.this, EquipoEvaluacionActivity.class);
	             		intent14.putExtra("identificador", identificador);
	             		intent14.putExtra("index", itemPosition);             		
	             		startActivity(intent14);
             		}
             		break;
             	case 15:
             		if(preferencias.isNewEventoPref()){
             			Toast.makeText(view.getContext(),"Guarde o envie el evento antes de usar esta opcion" , Toast.LENGTH_SHORT).show();
             		}else{
	             		Intent intent15 = new Intent(PrincipalActivity.this, InicioActivity.class);
	             		intent15.putExtra("index", itemPosition);             		
	             		startActivity(intent15);
             		}
             		break;
             	default: break;             		
             }
     		else{
     			Toast.makeText(getApplicationContext(), "Ingresar IP y puerto", Toast.LENGTH_SHORT).show();
     			Intent intent14 = new Intent(PrincipalActivity.this, ConfiguracionActivity.class);
         		intent14.putExtra("index", itemPosition);             		
         		startActivity(intent14);
     		}
     		return false;
           }
       }); 
	}
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        String[] menuPrincipal = getResources().getStringArray(R.array.menuprincipal);
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0 ; i < menuPrincipal.length ; i++){
			list.add(menuPrincipal[i]);
		}
 
        // Adding child data
        listDataHeader.add("Descripcion del evento");
        listDataHeader.add("Afectación en la población");
        listDataHeader.add("Afectación en la infraestructura");
        listDataHeader.add("Impacto del evento");
        listDataHeader.add("Necesidades de Respuesta");
        listDataHeader.add("Información adicional");
 
        // Adding child data
        List<String> primero = new ArrayList<String>();
        primero.add(list.get(0));
        primero.add(list.get(1));
        
     // Adding child data
        List<String> segundo = new ArrayList<String>();
        segundo.add(list.get(2));
        segundo.add(list.get(3));
        segundo.add(list.get(4));
        
     // Adding child data
        List<String> tercero = new ArrayList<String>();
        tercero.add(list.get(5));
        tercero.add(list.get(6));
        tercero.add(list.get(7));
        
     // Adding child data
        List<String> cuarto = new ArrayList<String>();
        cuarto.add(list.get(8));
        cuarto.add(list.get(9));        
        
     // Adding child data
        List<String> quinto = new ArrayList<String>();
        quinto.add(list.get(10));
        quinto.add(list.get(11));
        quinto.add(list.get(12));
        
        List<String> sexto = new ArrayList<String>();
        sexto.add(list.get(13));
        sexto.add(list.get(14));
       

        listDataChild.put(listDataHeader.get(0), primero); // Header, Child data
        listDataChild.put(listDataHeader.get(1), segundo);
        listDataChild.put(listDataHeader.get(2), tercero);
        listDataChild.put(listDataHeader.get(3), cuarto);
        listDataChild.put(listDataHeader.get(4), quinto);
        listDataChild.put(listDataHeader.get(5), sexto);
    }
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_lista:
			//Intent intent_configuracion = new Intent(this, ConfiguracionActivity.class);
			Intent intent_configuracion = new Intent(this, ConfiguracionActivity.class);
			this.startActivity(intent_configuracion);
			break;
		
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	    if (data.hasExtra("idu")) {
	      Toast.makeText(this, data.getExtras().getString("idu"),
	        Toast.LENGTH_SHORT).show();
	    }
	  }
	} 
}
