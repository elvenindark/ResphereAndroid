package com.resphere.android.modelo;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RegularExpressionValidation;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.resphere.android.vista.R;

@Table(name="ubicacion")
public class Ubicacion extends Entity{
	//@Databinding(ViewId = R.id.spinnerProvincia)
	@TableField(name ="provincia", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Provincia requerida")
	private String provincia;
	//@Databinding(ViewId = R.id.spinnerCanton)
	@TableField(name ="canton", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Canton requerido")
	private String canton;
	//@Databinding(ViewId = R.id.spinnerParroquia)
	@TableField(name ="parroquia", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Parroquia requerida")
	private String parroquia;
	public static int tipo_parroquia = R.id.textTipoParroquia;
	@Databinding(ViewId = 0x7f09009e)
	@TableField(name ="tipo", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Tipo de parroquia requerido")
	private String tipo;
	public static int txt_sector = R.id.txtSector;
	@Databinding(ViewId =  0x7f0900a0)
	@TableField(name ="sector", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Sector requerido")
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |,|.|Ò—|·ÈÌÛ˙]{3,100}", message = "Sector formato: 5 a 100 caracteres o caracter")
	private String sector;
	public static int txt_distancia = R.id.txtDistancia;
	@Databinding(ViewId = 0x7f0900a2)
	@TableField(name ="distancia", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Distancia requerida")
	@RegularExpressionValidation(expression = "[0-9]{1,4}", message = "Distancia demasiado grande")
	private String distancia;
	public static int txt_tiempo = R.id.txtTiempo;
	@Databinding(ViewId = 0x7f0900a3)
	@TableField(name ="tiempo", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Tiempo requerido")
	@RegularExpressionValidation(expression = "[0-9]{1,4}", message = "Tiempo demasiado grande")
	private String tiempo;
	public static int txt_referencia = R.id.txtReferencia;
	@Databinding(ViewId = 0x7f0900a4)
	@TableField(name ="referencia", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Referencia requerida")
	@RegularExpressionValidation(expression = "[a-z|0-9|A-Z| |,|.|Ò—|·ÈÌÛ˙]{5,100}", message = "Referencia formato: 5 a 100 caracteres o caracter")
	private String referencia;
	public static int txt_latitud = R.id.txtLatitud;
	@Databinding(ViewId = 0x7f0900a5)
	@TableField(name ="latitud", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Latitud requerida")
	//@RegularExpressionValidation(expression = "[0-9|.|-|0-9]{1,30}", message = "Problemas en el formato latitud: 1 a 30 numeros")
	private String latitud;
	public static int txt_longitud = R.id.txtLongitud;
	@Databinding(ViewId = 0x7f0900a8)
	@TableField(name ="longitud", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "Longitud requerida")
	//@RegularExpressionValidation(expression = "[0-9|.|-|0-9]{1,30}", message = "Problemas en el formato longitud: 1 a 30 numeros")
	private String longitud;
	public static int txt_altitud = R.id.txtAltitud;
	@TableField(name ="altitud", datatype = Entity.DATATYPE_STRING)
	@Databinding(ViewId = 0x7f0900a9)	
	@RegularExpressionValidation(expression = "[0-9|.|0-9]{1,20}", message = "altitud formato : 1 a 10 numeros")
	private String altitud;
	@TableField(name = "idevento", datatype = Entity.DATATYPE_STRING, required = true)
	@RequiredFieldValidation(message = "identificador requerido")
	private String idevento;
	@TableField(name = "idubicacion", datatype = Entity.DATATYPE_STRING, required = true)
	private String idubicacion;
	
	public String getIdubicacion() {
		return idubicacion;
	}

	public void setIdubicacion(String idubicacion) {
		this.idubicacion = idubicacion;
	}

	public String getIdevento() {
		return idevento;
	}

	public void setIdevento(String idevento) {
		this.idevento = idevento;
	}

	public Ubicacion(){
		
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getParroquia() {
		return parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getAltitud() {
		return altitud;
	}

	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}

}
