package com.pessoalprojeto.dscatalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoalprojeto.dscatalog.dto.ProductDTO;

import com.pessoalprojeto.dscatalog.services.ProductService;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;
import com.pessoalprojeto.dscatalog.tests.Factory;

@WebMvcTest(ProductController.class)
/*Carrega o contexto, porém somente da camada web (teste de unidade: controlador)*/
public class ProductControllerTests {
	
	/*MockMvc é uma classe do Spring Framework que fornece uma maneira de realizar testes unitários para 
	 aplicações Spring MVC. Ela permite simular requisições HTTP e receber as respostas correspondentes sem 
	 precisar iniciar um servidor web real.*/
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	/*O ObjectMapper é uma biblioteca Java usada para mapear objetos Java para JSON e vice-versa. Faz parte da 
	biblioteca Jackson, que é amplamente usada para processamento de JSON em aplicativos Java. Ele oferece recursos 
	avançados para personalização da serialização e desserialização, tratamento de vários tipos de dados e gerenciamento 
	de estruturas de objetos complexas.*/
	private ObjectMapper objectMapper;
	
	@MockBean
	/*A anotação @MockBean é usada em testes de integração no Spring para substituir uma bean existente por 
	 um mock. Essa anotação é útil quando se deseja simular o comportamento de uma dependência externa da 
	 classe em teste. O @MockBean é injetado no contexto de teste do Spring e substitui qualquer bean existente 
	 do mesmo tipo, permitindo que você controle o comportamento do mock durante a execução do teste.*/
	private ProductService productService;
	
	private PageImpl<ProductDTO> page;
	private ProductDTO produtoDto;
	private Long idExistente;
	private Long idNaoExistente;
	private Long idDepedente;

	
	@BeforeEach
	 void setUp() throws Exception  {
		
		idExistente = 1L;
		idNaoExistente = 1000L;
		idDepedente = 4L;
		produtoDto = Factory.criarProdutoDTO();
		
		
		page = new PageImpl<>(List.of(produtoDto));
		
		
		//● SIMULANDO COMPORTAMENTOS DO MÉTODO DO MOCK productService
		
		//comportamento simulado productService.findAllPaged
		Mockito.when(productService.findAllPaged(categoryId, ArgumentMatchers.any())).thenReturn(page);
		
		//comportamento simulado productService.findById
		Mockito.when(productService.findById(idExistente)).thenReturn(produtoDto);
		Mockito.when(productService.findById(idNaoExistente)).thenThrow(EntityNotFoundExceptions.class);
		
		//comportamento simulado productService.update
		Mockito.when(productService.update(ArgumentMatchers.eq(idExistente), ArgumentMatchers.any())).thenReturn(produtoDto);
		Mockito.when(productService.update(ArgumentMatchers.eq(idNaoExistente), ArgumentMatchers.any())).thenThrow(EntityNotFoundExceptions.class);
	
		//comportamento simulado productService.insert
		Mockito.when(productService.insert(ArgumentMatchers.any())).thenReturn(produtoDto);
				
		//comportamento simulado productService.update(como ele retorna void a muda, AÇÃO --> CONSEQUENCIA)
		Mockito.doNothing().when(productService).delete(idExistente);
		Mockito.doThrow(EntityNotFoundExceptions.class).when(productService).delete(idNaoExistente);
		Mockito.doThrow(DatabaseException.class).when(productService).delete(idDepedente);
	}
	
	
	//●	TESTES DA CAMADA WEB COM  da MockMvc

	//TESTE MÉTODO ProductController.findAllPaged
	@Test
	public void findAllPagedShouldPage() throws Exception {
		/*Essa linha de código simula uma requisição GET para o endpoint "/products" e define que o tipo de conteúdo aceito 
		na resposta deve ser JSON. O resultado da requisição é armazenado em result para posterior verificação ou asserção.*/
		ResultActions result = mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));
		
		/*A chamada result.andExpect(status().isOk()) é usada para realizar uma asserção na resposta recebida. Nesse caso, 
		 status().isOk() verifica se o código de status da resposta é 200 (OK).*/
		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldRetornaProductWhenIdExistir() throws Exception {
		ResultActions result = mockMvc.perform(get("/products/{id}", idExistente)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		/*Essas linhas de código estão usando o método andExpect() do objeto ResultActions para realizar asserções no 
		 conteúdo da resposta da requisição simulada.

		A expressão jsonPath("$.id") é usada para selecionar o campo "id" no JSON da resposta. O método exists() é então 
		chamado para verificar se esse campo existe no JSON.*/
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		
	}
	
	@Test
	public void findByIdShouldRetornaNotFoundWhenIdNaoExistir() throws Exception {
		ResultActions result = mockMvc.perform(get("/products/{id}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
			
	}
	
	//TESTE MÉTODO ProductController.update
	@Test
	public void updateShouldRetornaProductWhenIdExistir() throws Exception {
		
		/*Serialização: Para converter um objeto Java em JSON, você pode usar o método writeValueAsString()*/
		String jsonbody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/products/{id}", idExistente)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());	
	}
	
	@Test
	public void updateShouldRetornaNotFoundWhenIdNaoExistir() throws Exception {
		String jsonbody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/products/{id}", idNaoExistente)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	
	}
	
	//TESTE MÉTODO ProductController.insert
		@Test
		public void insertShouldProductDtoCreated() throws Exception {
			
			String jsonbody = objectMapper.writeValueAsString(produtoDto);
			
			ResultActions result = mockMvc.perform(post("/products")
					.content(jsonbody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isCreated());
			result.andExpect(jsonPath("$.id").exists());
			result.andExpect(jsonPath("$.name").exists());
			result.andExpect(jsonPath("$.description").exists());	
		}
		
		//TESTE MÉTODO ProductController.delete
		@Test
		public void deleteShouldNoContentWhenIdExistir() throws Exception {
			
			ResultActions result = mockMvc.perform(delete("/products/{id}", idExistente)
					.accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isNoContent());
		}
		
		@Test
		public void deleteShouldNotFoundWhenIdNaoExistir() throws Exception {
			
			ResultActions result = mockMvc.perform(delete("/products/{id}", idNaoExistente)
					.accept(MediaType.APPLICATION_JSON));
			
			result.andExpect(status().isNotFound());	
		}
	

}
