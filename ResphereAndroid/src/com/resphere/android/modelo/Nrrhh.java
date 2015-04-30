package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="nrrhh")
public class Nrrhh extends Entity {

	@TableField(name ="idtiponrrhh", datatype = Entity.DATATYPE_STRING)
	private String idtiporrhh;
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	@TableField(name ="aplica", datatype = Entity.DATATYPE_STRING)
	private String aplica;
	@TableField(name ="requerimiento", datatype = Entity.DATATYPE_STRING)
	private String requerimiento;
	
	public String getIdtiporrhh() {
		return idtiporrhh;
	}
	public void setIdtiporrhh(String idtiporrhh) {
		this.idtiporrhh = idtiporrhh;
	}
	public String getIdevento() {
		return idevento;
	}
	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}
	public String getAplica() {
		return aplica;
	}
	public void setAplica(String aplica) {
		this.aplica = aplica;
	}
	public String getRequerimiento() {
		return requerimiento;
	}
	public void setRequerimiento(String requerimiento) {
		this.requerimiento = requerimiento;
	}	
	
}
