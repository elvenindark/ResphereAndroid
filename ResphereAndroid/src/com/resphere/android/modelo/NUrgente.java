package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="nurgente")
public class NUrgente extends Entity {

	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtiponurgente", datatype = Entity.DATATYPE_STRING)
	private String idtiponurgente;
	@TableField(name ="hogar", datatype = Entity.DATATYPE_STRING)
	private String hogar;
	@TableField(name ="especificacion", datatype = Entity.DATATYPE_STRING)
	private String especificacion;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdtiponurgente() {
		return idtiponurgente;
	}
	public void setIdtiponurgente(String idtiponurgente) {
		this.idtiponurgente = idtiponurgente;
	}
	public String getHogar() {
		return hogar;
	}
	public void setHogar(String hogar) {
		this.hogar = hogar;
	}
	public String getEspecificacion() {
		return especificacion;
	}
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}	
	
}
