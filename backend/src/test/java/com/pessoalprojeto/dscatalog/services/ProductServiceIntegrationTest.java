package com.pessoalprojeto.dscatalog.services;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
/*@SpringBootTest é uma anotação do Spring Boot usada para testes de unidade e integração de aplicativos Java. Ela 
configura o ambiente de teste para carregar o contexto de aplicativo completo do Spring Boot, incluindo configurações, 
beans e componentes necessários. Essa anotação permite testar a funcionalidade de um aplicativo Spring Boot de forma 
semelhante ao ambiente de produção, possibilitando escrever testes de integração mais realistas. Também oferece recursos 
adicionais, como suporte à injeção de dependência e carregamento automático de configurações.*/
public class ProductServiceIntegrationTest {
	
	@Autowired
	private ProductService productService;
	
	private Long idExistente;
	private Long idNaoExistente;
	private Long totalElementsProdutos;
	
	@BeforeEach
	 void setUp() throws Exception  {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalElementsProdutos = 25L;
		
	}

}
