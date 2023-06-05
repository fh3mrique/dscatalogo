package com.pessoalprojeto.dscatalog.controller.exceptions;


import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

/*@ControllerAdvice é uma anotação usada no Spring Framework para definir um controlador global que gerencia 
exceções em toda a aplicação. Esse controlador é responsável por interceptar exceções lançadas por qualquer 
controlador na aplicação e processá-las de maneira consistente.

Ao anotar uma classe com @ControllerAdvice, você pode definir métodos que lidam com exceções específicas ou 
genéricas. Esses métodos podem retornar uma resposta personalizada ou fazer um redirecionamento para uma 
página de erro.*/
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundExceptions.class)
	public ResponseEntity<StandardErro> entityNotFoundException(EntityNotFoundExceptions e, HttpServletRequest request ){
		StandardErro err = new StandardErro();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Controller not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err) ;
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardErro> database (DatabaseException e, HttpServletRequest request ){
		StandardErro err = new StandardErro();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err) ;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErro> database (MethodArgumentNotValidException e, HttpServletRequest request ){
		StandardErro err = new StandardErro();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err) ;
	}


}
