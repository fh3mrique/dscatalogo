package com.pessoalprojeto.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.entities.Product;
import com.pessoalprojeto.dscatalog.repositories.CategoryRepository;
import com.pessoalprojeto.dscatalog.repositories.ProductRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;
import com.pessoalprojeto.dscatalog.tests.Factory;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	/*A anotação @InjectMocks é usada em conjunto com a anotação @Mock no framework Mockito para criar e 
	 injetar automaticamente objetos simulados em uma classe de teste com base em suas dependências declaradas. 
	 Isso evita a necessidade de criar instâncias reais de objetos dependentes para testar uma classe.*/
	private ProductService service;
	
	/*Na situação em que uma classe depende de outras classes, mas não queremos interagir com um banco 
	de dados real durante os testes, podemos usar a anotação @Mock do Mockito para criar objetos simulados 
	das dependências e a anotação @InjectMocks para criar uma instância real da classe e injetar automaticamente 
	os mocks criados. Dessa forma, podemos controlar o comportamento das dependências simuladas durante os testes 
	da classe, tornando-os mais isolados e confiáveis.*/
	@Mock
	private ProductRepository repositorio;
	@Mock
	/*Sempre que criamos um mock  não podemos esquecer que simular o comportamentos desse mock*/
	private CategoryRepository categoryRepository;
	
	private Long idExistente;
	private Long idNaoExistente;
	private Long idDependente;
	/*o tipo PageImpl<T> é uma classe genérica do Spring Framework que implementa a interface Page<T>, 
	 utilizada para representar uma página de resultados de uma consulta que retorna uma lista grande de itens. 
	 A classe PageImpl<T> é uma implementação padrão da interface Page<T> que pode ser usada para criar objetos 
	 de página.*/
	private PageImpl<Product> page;
	Product produto;
	ProductDTO dto;
	Category categoria;
	
	
	@BeforeEach
	 void setUp() throws Exception  {
		idExistente = 1L;
		idNaoExistente = 1000L;
		idDependente = 4L;
		produto = Factory.criarProduto();
		page = new PageImpl<>(List.of(produto));
		categoria = Factory.criarCategory();
		dto = Factory.criarProdutoDTO();
		
		//SIMULANDO MÉTODOS DO MOCK ProductRepository
		
		//comportamento simulado repository.findbyid(pageble)
		/*QUANDO chamar o findAll passando qualquer valor como argumento(tive que fazer um cast para Pageable pq o findAll
		 tem varias sobrecargas, e a nois interessa  a que retorna um pageable, logo é obrigatório fazer um cast ) 
		 ENTAO RETORNE um page do PageImp */
		Mockito.when(repositorio.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		
		//comportamento simulado repository.save
		Mockito.when(repositorio.save(ArgumentMatchers.any())).thenReturn(produto);
		
		//comportamento simulado repository.findById
		Mockito.when(repositorio.findById(idExistente)).thenReturn(Optional.of(produto));
		Mockito.when(repositorio.findById(idNaoExistente)).thenReturn(Optional.empty());
		
		//comportamento simulado repository.getOne
		Mockito.when(repositorio.getOne(idExistente)).thenReturn(produto);
		Mockito.when(repositorio.getOne(idNaoExistente)).thenThrow(EntityNotFoundException.class);
		
		//comportamento simulado repository.getOne
		Mockito.when(categoryRepository.getOne(idExistente)).thenReturn(categoria);
		Mockito.when(categoryRepository.getOne(idNaoExistente)).thenThrow(EntityNotFoundException.class);
	
		//comportamento simulado método repository.findById 
		/*deletebyid retorna um void logo o jeito de escrever o teste é: AÇÃO: doNothing(),doThrow etc. ---> when 
		 * "faça isso quando acontecer isso."*/
		Mockito.doNothing().when(repositorio).deleteById(idExistente);
		/*indica que, quando o método deleteById() do objeto repositorio for chamado com o argumento idExistente, 
		nenhum comportamento especial será simulado e o método não lançará nenhuma exceção. Ou seja, 
		a execução do método será normal.*/
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repositorio).deleteById(idNaoExistente);
		/* indica que, quando o método deleteById() do objeto repositorio for chamado com o argumento idNaoExistente, 
		 será simulada uma exceção do tipo EmptyResultDataAccessException*/
		Mockito.doThrow(DataIntegrityViolationException.class).when(repositorio).deleteById(idDependente);
	}
	
	
	
	//TESTANDO MÉTODOS DO INJECT MOCK ProductService 
	
	//TESTANDO MÉTODO "findAllPaged" DO SERVICE
	@Test
	public void FindAllPagedShouldRetornaPage() {
		
		Pageable Pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged("", 0L, Pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repositorio, Mockito.times(1)).findAll(Pageable);
	}
	
	//TESTANDO MÉTODO "delete" DO SERVICE
	@Test
	public void deleteByIdShouldFazerNadaWhenIdExistir() {
		//A assertion vai passar por que ela espera nenhuma excessão, o que foi simulado no comportamento simulado
		Assertions.assertDoesNotThrow(()->{
			repositorio.deleteById(idExistente);
		});
		
		/*O método Mockito.verify() é utilizado para verificar se um determinado método de um objeto 
		 mock foi invocado durante a execução de um teste. Ele permite verificar se um método específico 
		 foi chamado com os argumentos corretos, quantas vezes foi chamado, entre outras verificações.*/
		Mockito.verify(repositorio, Mockito.times(1)).deleteById(idExistente);
		}
	
	@Test
	public void deleteShouldLancaDatabaseExceptionWhenIdForDependente() {
		Assertions.assertThrows(DatabaseException.class, () -> {
				service.delete(idDependente);
		});
		
		Mockito.verify(repositorio, Mockito.times(1)).deleteById(idDependente);
		}
	
	@Test
	public void deleteShouldLancaEmptyResultDataAccessExceptionWhenIdNaoExistir() {
		
		Assertions.assertThrows(EntityNotFoundExceptions.class, () -> {
				service.delete(idNaoExistente);
		});
		
		Mockito.verify(repositorio, Mockito.times(1)).deleteById(idNaoExistente);
		}
	
	//TESTANDO MÉTODO "findAllPaged" DO SERVICE
	@Test
	public void findByIdShouldRetornaProductDtoWhenIdExistir() {
	
		ProductDTO result = service.findById(idExistente);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repositorio, Mockito.times(1)).findById(idExistente);
	}
	
	@Test
	public void findByIdShouldLancaEntityNotFoundExceptionsWhenIdNaoExistir() {
		Assertions.assertThrows(EntityNotFoundExceptions.class, ()->{
			service.findById(idNaoExistente);
		});
		
		Mockito.verify(repositorio, Mockito.times(1)).findById(idNaoExistente);
	}
	
	//TESTANDO MÉTODO "update" DO SERVICE
	@Test
	public void updateShouldProductDtoWhenIdExistir() {
		
		ProductDTO result = service.update(idExistente, dto);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldLancaEntityNotFoundExceptionsWhenIdNaoExistir() {
		Assertions.assertThrows(EntityNotFoundExceptions.class, ()->{
			service.update(idNaoExistente, dto);
		});
	}
	
	
		
	
	}

