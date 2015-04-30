package com.resphere.android.modelo.context;

import android.content.Context;

import com.mobandme.ada.exceptions.AdaFrameworkException;

public class DataContext  {
	
		public static ApplicationDataContext appDataContext;
		
		public DataContext(Context context){			
			try {
				appDataContext = new ApplicationDataContext(context);
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		public static ApplicationDataContext getInstance(Context context){
			if(appDataContext == null){
				try {
					appDataContext = new ApplicationDataContext(context);
				} catch (AdaFrameworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return appDataContext;
		}

}
