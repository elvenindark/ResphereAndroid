package com.resphere.android.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.content.Context;
import android.content.res.AssetManager;

public class LoadUbicacion {
	
	private Context context;
	
	public LoadUbicacion(Context context){
		this.context = context;
	}
	
	public String read(String file){
		AssetManager assetManager = context.getAssets();
		//String[] files = assetManager.list("");
		InputStream fin = null;
		 Scanner sc = null;
		 StringBuilder sb = new StringBuilder();
		 try{
		     fin = assetManager.open(file);	    
		     sc = new Scanner(fin, "Latin-1");
		     String temp="";
		     
		     while( sc.hasNextLine()){
		    	 sb.append(sc.nextLine());
		     }	  
		     // note that Scanner suppresses exceptions
		     if (sc.ioException() != null) {
		         throw sc.ioException();
		     }
		     temp = sb.toString();
		    return temp;
		 } catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {
			    if (fin != null) {
			        try {
						fin.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
			    }
			    if (sc != null) {
			        sc.close();
			    }
		}
	}
}
