package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name="canton")
public class Canton extends Entity {

	@TableField(name ="idcanton", datatype = Entity.DATATYPE_STRING)
	private String idcanton;
	@TableField(name ="idprovincia", datatype = Entity.DATATYPE_STRING)
	private String idprovincia;
	@TableField(name ="canton", datatype = Entity.DATATYPE_STRING)
	private String canton;
	
	public Canton(){
		
	}

	public String getIdcanton() {
		return idcanton;
	}

	public void setIdcanton(String idcanton) {
		this.idcanton = idcanton;
	}

	public String getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}
	
}
