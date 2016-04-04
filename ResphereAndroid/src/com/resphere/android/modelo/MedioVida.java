package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="mediovida")
public class MedioVida extends Entity {
	@TableField(name ="idevento", datatype = Entity.DATATYPE_STRING)
	private String idevento;
	
	@TableField(name = "idtipomediovida", datatype = Entity.DATATYPE_STRING)
	private String idtipomediovida;
	
	@TableField(name = "idtipodano", datatype = Entity.DATATYPE_STRING)
	private String idtipodano;
	
	@TableField(name = "siaplica", datatype = Entity.DATATYPE_STRING)
	private String siaplica;
	
	@TableField(name = "observacion", datatype = Entity.DATATYPE_STRING)
	private String observacion;
	
	@TableField(name = "hombres", datatype = Entity.DATATYPE_STRING)
	private String hombres;
	
	@TableField(name = "mujeres", datatype = Entity.DATATYPE_STRING)
	private String mujeres;

	public String getHombres() {
		return hombres;
	}

	public void setHombres(String hombres) {
		this.hombres = hombres;
	}

	public String getMujeres() {
		return mujeres;
	}

	public void setMujeres(String mujeres) {
		this.mujeres = mujeres;
	}

	public String getIdevento() {
		return idevento;
	}

	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}

	public String getIdtipomediovida() {
		return idtipomediovida;
	}

	public void setIdtipomediovida(String idtipomediovida) {
		this.idtipomediovida = idtipomediovida;
	}

	public String getIdtipodano() {
		return idtipodano;
	}

	public void setIdtipodano(String idtipodano) {
		this.idtipodano = idtipodano;
	}

	public String getSiaplica() {
		return siaplica;
	}

	public void setSiaplica(String siaplica) {
		this.siaplica = siaplica;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
