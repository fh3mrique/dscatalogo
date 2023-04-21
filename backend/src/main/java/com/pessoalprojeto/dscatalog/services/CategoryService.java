package com.pessoalprojeto.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.CategoryDTO;
import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.repositories.CategoryRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

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
	
	@Transactional(readOnly = true)
	public List<CategoryDTO>  findAll(){
		
		/*O método findAll() da JPA é um método que retorna uma lista de todos os registros de uma tabela do banco de 
		 dados que correspondem a uma entidade JPA*/
		List<Category> listaCategory = repository.findAll();
		
		/*cria uma nova lista vazia do tipo CategoryDTO. Essa lista será preenchida com objetos do tipo CategoryDTO, 
		 que é um objeto que representa uma versão simplificada da entidade Category e é utilizado para transferir dados entre a 
		 camada de serviço e a camada de controle*/
		List<CategoryDTO> listaDto = new ArrayList<>();
		
		for (Category cat : listaCategory)//- inicia um loop que itera sobre cada objeto Category da lista listaCategory
		{
			/*- cria um novo objeto CategoryDTO a partir do objeto cat atualmente em loop e adiciona o novo objeto à lista listaDto.*/
			listaDto.add(new CategoryDTO(cat));
		}
		
		return listaDto;
		
	}

	/*@Transactional : Anotação que indica que o método será executado dentro de uma transação. O parâmetro 
	 readOnly = true define que a transação será apenas de leitura, ou seja, não permitirá alterações no banco de dados.*/
	@Transactional(readOnly = true)
	/*Este trecho de código é um método que faz parte de uma classe de serviço que retorna um objeto CategoryDTO 
	 a partir de um ID passado como parâmetro.*/
	public CategoryDTO findById(Long id) {
		
		/*O objeto Optional é utilizado para evitar valores nulos. Neste caso, a busca pelo ID é realizada no banco de 
		 dados através do método findById() do repositório, que retorna um objeto do tipo Optional<Category>.*/
		Optional<Category> obj = repository.findById(id);
		
		/*O método get() do objeto Optional retorna o objeto Category encontrado no banco de dados, que será 
		 usado para construir o objeto CategoryDTO.*/
		Category entity = obj.orElseThrow( () -> new EntityNotFoundExceptions("Entidade não encontrada"));
		
		/*O objeto Category é utilizado para construir um objeto CategoryDTO, que é retornado pelo método. A 
		conversão é feita através do construtor da classe CategoryDTO, que recebe um objeto do tipo Category 
		como parâmetro.*/
		return new CategoryDTO(entity);
	}
}
