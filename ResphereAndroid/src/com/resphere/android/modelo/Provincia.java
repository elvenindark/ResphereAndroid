package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="provincia")
public class Provincia extends Entity {

	@TableField(name = "idprovincia", datatype = Entity.DATATYPE_STRING)
	private String idprovincia;
	@TableField(name = "provincia", datatype = Entity.DATATYPE_STRING)
	private String provincia;
	
	public Provincia(){
		
	}

	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
}
