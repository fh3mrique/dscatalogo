package com.pessoalprojeto.dscatalog.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/*A anotação de validação personalizada é uma forma de criar suas próprias regras de validação e aplicá-las a 
campos, métodos ou parâmetros de um objeto. Ao criar uma anotação de validação personalizada, você pode 
definir a lógica de validação específica e usá-la em conjunto com o mecanismo de validação do Bean Validation.*/


@Constraint(validatedBy = UserInsertValidator.class)
/*@Constraint: Essa anotação é usada para marcar sua anotação de validação personalizada e indicar que ela 
é uma restrição de validação.*/
@Target({ ElementType.TYPE })
/*@Target: Essa anotação é usada para especificar os elementos de programação aos quais a anotação de 
validação personalizada pode ser aplicada. Por exemplo, você pode definir que a anotação se aplica a campos 
(ElementType.FIELD), métodos */
@Retention(RetentionPolicy.RUNTIME)
/*@Retention: Essa anotação define como a anotação de validação personalizada é armazenada e acessível. 
Por exemplo, RetentionPolicy.RUNTIME permite que a anotação seja acessada em tempo de execução.*/
/* anotação de validação */

public @interface UserInsertValid {
	String message() default "Validation error";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}