package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="nurgente")
public class NUrgente extends Entity {

	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtiponurgente", datatype = Entity.DATATYPE_STRING)
	private String idtipourgente;
	@TableField(name ="hogar", datatype = Entity.DATATYPE_STRING)
	private String numero;
	@TableField(name ="especificacion", datatype = Entity.DATATYPE_STRING)
	private String especificacion;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}	
	public String getEspecificacion() {
		return especificacion;
	}
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getIdtipourgente() {
		return idtipourgente;
	}
	public void setIdtipourgente(String idtipourgente) {
		this.idtipourgente = idtipourgente;
	}	
	
}
