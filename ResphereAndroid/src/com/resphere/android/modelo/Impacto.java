package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="impacto")
public class Impacto extends Entity {

	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtipoimpacto", datatype = Entity.DATATYPE_STRING)
	private String idtipoimpacto;
	
	public Impacto(){
		
	}
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdtipoimpacto() {
		return idtipoimpacto;
	}
	public void setIdtipoimpacto(String idtipoimpacto) {
		this.idtipoimpacto = idtipoimpacto;
	}
	
	
}
