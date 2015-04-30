package com.resphere.android.util;

import java.util.ArrayList;

import android.os.AsyncTask;

public class AsyncTaskComunicacion extends AsyncTask<ArrayList<String>, Void, Boolean> {

	public interface OnSendListener{
		public void response(Boolean respuesta);
	}
	
	public AsyncTaskComunicacion(String url){
		
	}
	
	@Override
	protected Boolean doInBackground(ArrayList<String>... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
