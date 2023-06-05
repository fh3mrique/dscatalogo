package com.pessoalprojeto.dscatalog.controller.exceptions;

import java.util.ArrayList;
import java.util.List;

/*A classe de validação tem todas as messagens padrão de StandardErro
 * mas também tem um campo exclusivo que é uma lista de contraints dos campos de que tem algum tipo de validação*/
public class ValidationError extends StandardErro {
	private static final long serialVersionUID = 1L;
	
	private List<FildMessage> errors = new ArrayList<>();

	public List<FildMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String message) {
		 errors.add(new FildMessage(fieldName, message));
	}

}
