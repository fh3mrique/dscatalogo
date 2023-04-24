package com.pessoalprojeto.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.CategoryDTO;
import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.repositories.CategoryRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
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
	
	// 1° implemnetação do método "findAll" do controller
	@Transactional(readOnly = true)
	public Page<CategoryDTO>  findAllPaged(PageRequest pageRequest)
	{
		/*O método findAll() da JPA é um método que retorna uma lista de todos os registros de uma tabela do banco de 
		 dados que correspondem a uma entidade JPA*/
		Page<Category> listaCategory = repository.findAll(pageRequest);
		
		return listaCategory.map(x -> new CategoryDTO(x));
		
	}

	//2° implementação do método "findById" chamado no controller
	
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

	//3° implementação do método "insert" chamado no controller
	@Transactional()
	public CategoryDTO insert(CategoryDTO dto) {
		
		Category entity = new Category();
		
		entity.setName(dto.getName());
		
		entity = repository.save(entity);
		
		return new CategoryDTO(entity);
	}
	
	//4° implementação do método "update" chamado no controller
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		
	try {
		/*Tanto o método getById() quanto o método getOne() são usados para buscar um registro em um banco de 
		dados pelo seu ID único. A diferença principal é que getById() é fornecido por muitas bibliotecas de persistência 
		de dados e retorna o registro encontrado ou nulo, enquanto getOne() é específico do Spring Data JPA e retorna 
		uma referência ao objeto do registro sem executar a consulta imediatamente, sendo mais eficiente em termos 
		de desempenho, mas pode gerar exceção se o registro não for encontrado.*/
		Category entity = repository.getOne(id);
		
		/*altera o nome da categoria com base nos dados fornecidos no objeto dto.*/
		entity.setName(dto.getName());
		
		/*salva as alterações feitas no objeto entity no banco de dados.*/
		entity = repository.save(entity);
		
		/*retorna um objeto CategoryDTO criado a partir do objeto entity.*/
		return new CategoryDTO(entity);
	}
	
	catch (EntityNotFoundException e) 
	/*: caso a consulta com o getOne() não retorne um registro correspondente ao ID fornecido, uma exceção de 
	 EntityNotFoundException será lançada.*/
	{
		/* lança uma exceção personalizada com a mensagem "Id não encontrado".*/
		throw new  EntityNotFoundExceptions("Id não encontrado");
	}
	
	}

	public void delete(Long id) {
		
		try {
			
			repository.deleteById(id);
			
		}
		catch(EmptyResultDataAccessException e) {
			
			throw new EntityNotFoundExceptions("id não encontrado");
		}
		catch(DataIntegrityViolationException e) {
			
			throw new DatabaseException("Violação Integridade do banco ");
			
		}
		
	}

	
}
