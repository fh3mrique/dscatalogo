package com.pessoalprojeto.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pessoalprojeto.dscatalog.controller.exceptions.FildMessage;
import com.pessoalprojeto.dscatalog.dto.UserInsertDTO;
import com.pessoalprojeto.dscatalog.entities.User;
import com.pessoalprojeto.dscatalog.repositories.UserRepository;

/*A interface ConstraintValidator é uma parte fundamental do mecanismo de validação do Bean Validation no 
Spring Framework. Ela permite que você implemente lógicas de validação personalizadas para suas anotações 
de validação customizadas.*/

/*Tipo genérico: A interface ConstraintValidator é um tipo genérico com dois parâmetros: A e T. Esses parâmetros 
 representam respectivamente a anotação de validação personalizada e o tipo do elemento que está sendo 
 validado.*/
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository;

	
	/*Método initialize(): Esse método é chamado uma vez durante a inicialização do validador de restrição. É 
	usado para realizar qualquer inicialização necessária antes que a validação ocorra. O parâmetro 
	constraintAnnotation é a instância da anotação de validação personalizada e pode ser usada para acessar 
	seus atributos.*/
	@Override
	public void initialize(UserInsertValid ann) {
	}

	/*Método isValid(): Esse método é responsável por realizar a lógica de validação personalizada. Ele é invocado 
	 para cada elemento que está sendo validado. O parâmetro */
	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		User user = repository.findByEmail(dto.getEmail());
		
		List<FildMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		if (user != null) {
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