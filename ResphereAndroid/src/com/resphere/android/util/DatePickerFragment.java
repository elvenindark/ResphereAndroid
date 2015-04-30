package com.resphere.android.util;

import java.util.Calendar;

import com.resphere.android.vista.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		//valor actual para el valor del picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
	    int month = c.get(Calendar.MONTH);
	    int day = c.get(Calendar.DAY_OF_MONTH);
		
		//nueva instancia timepickerdialog
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		Log.w("DatePicker","Date = " + year);
		((TextView)getActivity().findViewById(R.id.textFechaEq)).setText(year+"/"+month+"/"+day);
	}

}
