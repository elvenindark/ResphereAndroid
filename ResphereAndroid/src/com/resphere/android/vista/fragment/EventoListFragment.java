package com.resphere.android.vista.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.resphere.android.vista.R;

public class EventoListFragment extends DialogFragment {

	private Button guardar;
	private Button cancelar;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private String[] resultado;
	private ArrayList seleccionados;
	
	public EventoListFragment(){
		
	}
	
	public interface ListEventoListener{
		public void onFinishEventoList(String[] lista);
		
	}	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
		
		final String[] eventos = getResources().getStringArray(R.array.eventos);
		
		seleccionados = new ArrayList<String>();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Seleccione evento generador")
	    	.setMultiChoiceItems(R.array.eventos, null,
	    			new DialogInterface.OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							if(isChecked)
								seleccionados.add(which);
							else if(seleccionados.contains(which))
								seleccionados.remove(Integer.valueOf(which));
						}
					}).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							ListEventoListener activity = (ListEventoListener)getActivity();							
							String[] selectedItems = new String[seleccionados.size()];
							for(int i = 0; i < seleccionados.size(); i ++)
								selectedItems[i] = eventos[(Integer) seleccionados.get(i)];
							activity.onFinishEventoList(selectedItems);
							getDialog().dismiss();
						}
					}).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							getDialog().dismiss();
						}
					});
	    
		
		return builder.create();
		
	}
}
