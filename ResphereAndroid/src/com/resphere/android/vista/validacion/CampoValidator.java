package com.resphere.android.vista.validacion;

import java.lang.reflect.Field;

import com.mobandme.ada.Entity;
import com.mobandme.ada.validators.Validator;

public class CampoValidator extends Validator {
	
	@Override
	public Boolean Validate(Entity pEntity, Field pField, Object pAnnotation, Object pValue ){
		String value = (String)pValue;
		
		return value.length()>5 && value.length()<100;
		
	}

}
