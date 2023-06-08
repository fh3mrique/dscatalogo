package com.pessoalprojeto.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.pessoalprojeto.dscatalog.controller.exceptions.FildMessage;
import com.pessoalprojeto.dscatalog.dto.UserUpdateDTO;
import com.pessoalprojeto.dscatalog.entities.User;
import com.pessoalprojeto.dscatalog.repositories.UserRepository;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	/*A classe HttpServletRequest é uma classe do pacote javax.servlet.http que representa uma requisição HTTP 
	recebida pelo servidor. Ela fornece métodos para acessar informações sobre a requisição, como parâmetros, 
	cabeçalhos, método HTTP, URL, entre outros.*/
	private HttpServletRequest request;

	@Override
	public void initialize(UserUpdateValid ann) {
	}

	
	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars =(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long userId = Long.parseLong(uriVars.get("id"));
		
		List<FildMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		if (user != null && userId != user.getId()) {
			list.add(new FildMessage("email", "Esse email já existe no banco"));
		}
		
		
		for (FildMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}