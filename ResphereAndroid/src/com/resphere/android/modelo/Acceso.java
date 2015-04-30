package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="acceso")
public class Acceso extends Entity {
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="transporte", datatype = Entity.DATATYPE_STRING)
	private String transporte;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getTransporte() {
		return transporte;
	}
	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}
}
