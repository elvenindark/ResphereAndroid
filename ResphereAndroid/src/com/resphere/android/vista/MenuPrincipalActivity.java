package com.resphere.android.vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.resphere.android.vista.adapter.ExpandableListAdapter;

public class MenuPrincipalActivity extends Activity {

	 ExpandableListAdapter listAdapter;
	 ExpandableListView expListView;
	 List<String> listDataHeader;
	 HashMap<String, List<String>> listDataChild;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
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
        cuarto.add(list.get(10));
        
     // Adding child data
        List<String> quinto = new ArrayList<String>();
        quinto.add(list.get(11));
        quinto.add(list.get(12));
 
       

        listDataChild.put(listDataHeader.get(0), primero); // Header, Child data
        listDataChild.put(listDataHeader.get(1), segundo);
        listDataChild.put(listDataHeader.get(2), tercero);
        listDataChild.put(listDataHeader.get(3), cuarto);
        listDataChild.put(listDataHeader.get(4), quinto);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu_principal, menu);
		return true;
	}

}
