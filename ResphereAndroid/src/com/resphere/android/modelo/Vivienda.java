package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="vivienda")
public class Vivienda extends Entity {
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="sindano", datatype = Entity.DATATYPE_STRING)
	private String sindano;
	@TableField(name ="temporalnh", datatype = Entity.DATATYPE_STRING)
	private String temporalnh;
	@TableField(name ="danoparcialh", datatype = Entity.DATATYPE_STRING)
	private String danoparcialh;
	@TableField(name ="danototalnh", datatype = Entity.DATATYPE_STRING)
	private String danototalnh;
	@TableField(name ="totalv", datatype = Entity.DATATYPE_STRING)
	private String totalv;
	@TableField(name ="observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getSindano() {
		return sindano;
	}
	public void setSindano(String sindano) {
		this.sindano = sindano;
	}
	public String getTemporalnh() {
		return temporalnh;
	}
	public void setTemporalnh(String temporalnh) {
		this.temporalnh = temporalnh;
	}
	public String getDanoparcialh() {
		return danoparcialh;
	}
	public void setDanoparcialh(String danoparcialh) {
		this.danoparcialh = danoparcialh;
	}
	public String getDanototalnh() {
		return danototalnh;
	}
	public void setDanototalnh(String danototalnh) {
		this.danototalnh = danototalnh;
	}
	public String getTotalv() {
		return totalv;
	}
	public void setTotalv(String totalv) {
		this.totalv = totalv;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
}
