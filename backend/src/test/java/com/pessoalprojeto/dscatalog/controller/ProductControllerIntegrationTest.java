package com.pessoalprojeto.dscatalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.tests.Factory;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
/*A anotação @AutoConfigureMockMvc no Spring Framework configura automaticamente o MockMvc para testes de 
integração em uma aplicação Spring MVC. O MockMvc é uma classe do Spring Test que simula chamadas HTTP aos 
endpoints da aplicação durante os testes de integração. Ao usar @AutoConfigureMockMvc, o Spring Boot configura 
automaticamente o MockMvc para ser injetado no contexto de teste. Isso facilita a simulação de chamadas HTTP e a 
validação dos resultados nos testes de integração.*/
public class ProductControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long idExistente;
	private Long idNaoExistente;
	private Long totalElementsProdutos;
	
	@BeforeEach
	 void setUp() throws Exception  {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalElementsProdutos = 25L;
		
	}
	
	@Test
	public void findAllPagedShouldRetornarPaginaOrdenadaWhenSortByName() throws Exception {
		ResultActions result = mockMvc.perform(get("/products?page=0&size=10&sort=name")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(totalElementsProdutos));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
				
	}
	
	@Test
	public void updateShouldRetornaProductWhenIdExistir() throws Exception {
		
		ProductDTO produtoDto = Factory.criarProdutoDTO();
		
		String jsonbody = objectMapper.writeValueAsString(produtoDto);
		String nomeEsperado = produtoDto.getName();
		String descriptionEsperado = produtoDto.getDescription();
		
		ResultActions result = mockMvc.perform(put("/products/{id}", idExistente)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(idExistente));
		result.andExpect(jsonPath("$.name").value(nomeEsperado));
		result.andExpect(jsonPath("$.description").value(descriptionEsperado));	
	}
	
	@Test
	public void updateShouldRetornaNotFoundWhenIdNaoExistir() throws Exception {
		
		ProductDTO produtoDto = Factory.criarProdutoDTO();
		
		String jsonbody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/products/{id}", idNaoExistente)
				.content(jsonbody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}

}
