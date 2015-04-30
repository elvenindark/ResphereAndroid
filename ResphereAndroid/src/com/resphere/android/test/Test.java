package com.resphere.android.test;

import java.util.ArrayList;

import com.resphere.android.modelo.Evento;
import com.resphere.android.util.Reflection;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Reflection r;
		Evento e = new Evento();
		r = new Reflection(e.getClass());
		ArrayList<String> atributos = new ArrayList<String>();
		ArrayList<String> valores = new ArrayList<String>();
		atributos = r.returnGetters();
		valores = r.returnDatos(e);
		for(int i = 0; i < atributos.size(); i++){
			System.out.println("atributo "+i+"> "+atributos.get(i)+" valor "+i+"> "+valores.get(i) );
		}
	}

}
