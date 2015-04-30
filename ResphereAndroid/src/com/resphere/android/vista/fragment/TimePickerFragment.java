package com.resphere.android.vista.fragment;

import java.util.Calendar;

import com.resphere.android.vista.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.EditText;

public class TimePickerFragment extends DialogFragment implements 
	TimePickerDialog.OnTimeSetListener{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		//valor actual para el valor del picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		//nueva instancia timepickerdialog
		return new TimePickerDialog(getActivity(), this, hour, minute, 
				DateFormat.is24HourFormat(getActivity()));
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		Log.w("DatePicker","Date = " + hourOfDay);
		((EditText)getActivity().findViewById(R.id.txtTiempo)).setText(hourOfDay+":"+minute);
	}

}
