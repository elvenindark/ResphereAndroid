package com.resphere.android.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import android.widget.Toast;

public class Reflection {

	private static Class aClass;
	private static Object object;
	public Reflection(Class aClass){
		this.aClass = aClass;
	}
	
	public Reflection(Class aClass, Object o){
		this.object = o;
		aClass.cast(this.object);
	}
	
	public ArrayList<String> returnDatos(Object o){
		this.object = o;
		aClass.cast(this.object);
		ArrayList<String> metodos = new ArrayList<String>();
		ArrayList<String> valores = new ArrayList<String>();
		metodos.addAll(returnGetters());
		Method[] metods = new Method[metodos.size()];		
		for(int i = 0; i < metodos.size(); i++){
			try {
				metods[i] = aClass.getDeclaredMethod(metodos.get(i));					
				valores.add(String.valueOf(metods[i].invoke(object)));
			} catch (NoSuchMethodException e) {
				e.printStackTrace(); return null;
			} catch (IllegalArgumentException e) {
				e.printStackTrace(); return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace(); return null;
			} catch (InvocationTargetException e) {
				e.printStackTrace(); return null;
			}			
		}	
		return valores;
	}
	
	public Boolean set(Object object, String atributo, String value){
		Class<?> clazz = object.getClass();
		while(clazz!=null){
			try {
				Field field = aClass.getDeclaredField(atributo);
				field.setAccessible(true);
				field.set(object, value);
				return true;
			} catch (NoSuchFieldException e) {
				e.printStackTrace(); return false;
			} catch (IllegalArgumentException e) {
				e.printStackTrace(); return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace(); return false;
			}
		}		
		return false;
	}	
			
	public ArrayList<String> returnGetters(){
		Method[] methods = aClass.getMethods();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> fields = new ArrayList<String>();
		fields.addAll(returnFields());
		int flag = 0;
		  
		if(flag == 1)
		for(Method method : methods){
			if(isGetter(method)) list.add(method.getName());		    
		 }
		else{
			for(Method method : methods){
				  
				for(int i = 0; i < fields.size(); i++){
					if(method.getName().toLowerCase().contains(fields.get(i)) && method.getName().startsWith("get")){
						list.add(method.getName());
						break;
					}
				}
			}
		}
		return list;
	}
	
	public ArrayList<String> returnSetters(){
		Method[] methods = aClass.getMethods();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> fields = new ArrayList<String>();
		fields.addAll(returnFields());
		int flag = 0;
		  
		if(flag == 1)
		for(Method method : methods){
			if(isSetter(method)) list.add(method.getName());		    
		 }
		else{
			for(Method method : methods){
				  
				for(int i = 0; i < fields.size(); i++){
					if(method.getName().toLowerCase().contains(fields.get(i)) && method.getName().startsWith("set")){
						list.add(method.getName());
						break;
					}
				}
			}
		}
		return list;
	}

	public ArrayList<String> returnFields(){
		ArrayList<String> privateFields = new ArrayList<String>();
	    Field[] allFields = aClass.getDeclaredFields();
	    for (Field field : allFields) {
	        if (Modifier.isPrivate(field.getModifiers())&&!field.getName().startsWith("serial")) {
	            privateFields.add(field.getName());
	        }
	    }
	    return privateFields;
	}
	
	public ArrayList<String> returnAtributos(){
		ArrayList<String> privateFields = new ArrayList<String>();
		ArrayList<String> getters = returnGetters();
		for(int i = 0; i < getters.size(); i++){
			privateFields.add(getters.get(i).toLowerCase().replace("get", ""));
		}
		return privateFields;
	}
	
	public static boolean isGetter(Method method){
		if(!method.getName().startsWith("get"))      return false;
		if(method.getParameterTypes().length != 0)   return false;  
		if(void.class.equals(method.getReturnType())) return false;
			return true;
	}	
	
	public static boolean isSetter(Method method){
	  if(!method.getName().startsWith("set")) return false;
	  if(method.getParameterTypes().length != 1) return false;
	  	return true;
	}
}
