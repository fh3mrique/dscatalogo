package com.pessoalprojeto.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.repositories.CategoryRepository;

@Service
/*A anotação @Service é do Spring Framework e indica que uma classe é um serviço, responsável por implementar a 
lógica de negócio de uma aplicação. Ao usar a anotação, é possível aproveitar as funcionalidades do Spring para 
gerenciar transações de banco de dados e tratar exceções de forma adequada. Serviços são comumente usados com 
a camada de acesso a dados (repository) para separar as responsabilidades. A anotação é uma especialização de
@Component e é detectada automaticamente pelo mecanismo de component scanning do Spring.*/
public class CategoryService {
	
	//ATRIBUTOS
	@Autowired/*A anotação @Autowired é uma anotação do Spring Framework que permite injetar automaticamente 
	as dependências de um componente em tempo de execução, sem a necessidade de criar manualmente as 
	instâncias das dependências. É uma forma de realizar a inversão de controle e tornar o código mais flexível e 
	modular.*/
	private CategoryRepository repository;

	//MÉTODOS
	
	public List<Category>  findAll(){
		
		/*O método findAll() da JPA é um método que retorna uma lista de todos os registros de uma tabela do banco de 
		 dados que correspondem a uma entidade JPA*/
		return repository.findAll();
		
	}
}
