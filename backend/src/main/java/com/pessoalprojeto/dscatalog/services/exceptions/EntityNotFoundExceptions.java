package com.pessoalprojeto.dscatalog.services.exceptions;

/*Este é um exemplo de uma classe de exceção personalizada que estende a classe RuntimeException do Java.*/

/*A classe EntityNotFoundExceptions é usada para lançar uma exceção personalizada quando uma entidade 
(objeto) não é encontrada em uma determinada operação em uma aplicação.*/
public class EntityNotFoundExceptions  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/*Essa exceção personalizada possui um construtor que recebe uma mensagem de erro como argumento e chama 
	 o construtor da classe pai (RuntimeException) com essa mensagem. Isso permite que a mensagem de erro seja 
	 exibida quando a exceção é lançada, fornecendo informações úteis ao usuário ou desenvolvedor sobre o que 
	 aconteceu e o que precisa ser corrigido.*/
	public EntityNotFoundExceptions(String msg) {
		super(msg);
	}

}
