package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="salud")
public class Salud extends Entity {
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="sifunciona", datatype = Entity.DATATYPE_STRING)
	private String sifunciona;
	@TableField(name ="idtiposalud", datatype = Entity.DATATYPE_STRING)
	private String idtiposalud;
	@TableField(name ="porcentaje", datatype = Entity.DATATYPE_STRING)
	private String porcentaje;
	@TableField(name ="observaciones", datatype = Entity.DATATYPE_STRING)
	private String observaciones;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getSifunciona() {
		return sifunciona;
	}
	public void setSifunciona(String sifunciona) {
		this.sifunciona = sifunciona;
	}
	public String getIdtiposalud() {
		return idtiposalud;
	}
	public void setIdtiposalud(String idtiposalud) {
		this.idtiposalud = idtiposalud;
	}
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
