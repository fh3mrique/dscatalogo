package com.pessoalprojeto.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.repositories.ProductRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

@SpringBootTest
/*@SpringBootTest é uma anotação do Spring Boot usada para testes de unidade e integração de aplicativos Java. Ela 
configura o ambiente de teste para carregar o contexto de aplicativo completo do Spring Boot, incluindo configurações, 
beans e componentes necessários. Essa anotação permite testar a funcionalidade de um aplicativo Spring Boot de forma 
semelhante ao ambiente de produção, possibilitando escrever testes de integração mais realistas. Também oferece recursos 
adicionais, como suporte à injeção de dependência e carregamento automático de configurações.*/
@Transactional
/*A anotação @Transactional é usada em contexto de testes de integração para garantir a execução de cada teste em uma 
 transação isolada no banco de dados. Ao marcar um método de teste com @Transactional, o Spring cria uma transação no 
 início do teste e a reverte ao finalizá-lo. Isso garante que qualquer modificação feita no banco de dados durante o teste 
 seja desfeita, mantendo o estado original do banco de dados intacto após a conclusão do teste.*/
public class ProductServiceIntegrationTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	private Long idExistente;
	private Long idNaoExistente;
	private Long totalElementsProdutos;
	
	@BeforeEach
	 void setUp() throws Exception  {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalElementsProdutos = 25L;
		
	}
	
	//testes de integração deve ser indepedente um do outro, o banco sempre 
	//tem que dá rollback quando o escopo do teste termina
	
	@Test
	public void deleteShouldDeletarRecursoWhenIdExistir() {
		
		productService.delete(idExistente);
		
		Assertions.assertEquals(totalElementsProdutos - 1, productRepository.count());
	}
	
	@Test
	public void deleteShouldEntityNotFoundExceptionsWhenIdNaoExistir() {
		
		Assertions.assertThrows(EntityNotFoundExceptions.class, ()->{
			productService.delete(idNaoExistente);
		});
	}
	
	@Test
	public void findAllPagedShouldRetornaPageWhenPage0Size10() {
		/*A classe PageRequest do Spring Data é usada para definir informações de paginação em consultas com o JPA. 
		Ela permite especificar o número e o tamanho da página para recuperar um subconjunto de resultados de uma 
		consulta em bancos de dados.*/
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = productService.findAllPaged("", 0L, pageRequest);
		
		//testando se a pagina é vazia
		Assertions.assertFalse(result.isEmpty());
		//testando se o número página é 0
		Assertions.assertEquals(0, result.getNumber());
		//testando se a página tem  10 elementos
		Assertions.assertEquals(10, result.getSize());
		//testando se o tatal de elementos retornados pela página é 25
		Assertions.assertEquals(totalElementsProdutos, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldRetornarPaginaVaziaWhenPaginaNaoExistir() {

		//não existe página 50
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ProductDTO> result = productService.findAllPaged("", 0L, pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldRetornarPaginaOrdenadaWhenPaginaOrdenadaPorName() {

		//requisição de páginas do postman
		//																								criterio de ordenação			
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> result = productService.findAllPaged("", 0L, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer Boo", result.getContent().get(3).getName());
		Assertions.assertEquals("PC Gamer Er", result.getContent().get(5).getName());
	}

}
