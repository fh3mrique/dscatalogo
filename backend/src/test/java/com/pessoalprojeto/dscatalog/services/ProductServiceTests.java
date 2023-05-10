package com.pessoalprojeto.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pessoalprojeto.dscatalog.repositories.ProductRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;


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
	
	private Long idExistente;
	private Long idNaoExistente;
	private Long idDependente;
	
	@BeforeEach
	 void setUp() throws Exception  {
		idExistente = 1L;
		idNaoExistente = 1000L;
		idDependente = 4L;
		
		//comportamento simulado repositorio
		
		Mockito.doNothing().when(repositorio).deleteById(idExistente);
		/*indica que, quando o método deleteById() do objeto repositorio for chamado com o argumento idExistente, 
		nenhum comportamento especial será simulado e o método não lançará nenhuma exceção. Ou seja, 
		a execução do método será normal.*/
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repositorio).deleteById(idNaoExistente);
		/* indica que, quando o método deleteById() do objeto repositorio for chamado com o argumento idNaoExistente, 
		 será simulada uma exceção do tipo EmptyResultDataAccessException*/
		Mockito.doThrow(DataIntegrityViolationException.class).when(repositorio).deleteById(idDependente);
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
		
		
	}

