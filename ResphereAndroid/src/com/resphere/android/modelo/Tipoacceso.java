package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="tipoacceso")
public class Tipoacceso extends Entity {
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="idtipoacceso", datatype = Entity.DATATYPE_STRING)
	private String idtipoacceso;
	@TableField(name ="tipoacceso", datatype = Entity.DATATYPE_STRING)
	private String tipoacceso;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getIdtipoacceso() {
		return idtipoacceso;
	}
	public void setIdtipoacceso(String idtipoacceso) {
		this.idtipoacceso = idtipoacceso;
	}
	public String getTipoacceso() {
		return tipoacceso;
	}
	public void setTipoacceso(String tipoacceso) {
		this.tipoacceso = tipoacceso;
	}
}
