package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="parroquia")
public class Parroquia extends Entity {

	@TableField(name ="idparroquia", datatype = Entity.DATATYPE_STRING)
	private String idparroquia;
	@TableField(name ="idcanton", datatype = Entity.DATATYPE_STRING)
	private String idcanton;
	@TableField(name ="idprovincia", datatype = Entity.DATATYPE_STRING)
	private String idprovincia;
	@TableField(name ="parroquia", datatype = Entity.DATATYPE_STRING)
	private String parroquia;
	
	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public Parroquia(){
		
	}
	
	public String getIdparroquia() {
		return idparroquia;
	}
	public void setIdparroquia(String idparroquia) {
		this.idparroquia = idparroquia;
	}
	public String getIdcanton() {
		return idcanton;
	}
	public void setIdcanton(String idcanton) {
		this.idcanton = idcanton;
	}
	public String getParroquia() {
		return parroquia;
	}
	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}
	
	
}
