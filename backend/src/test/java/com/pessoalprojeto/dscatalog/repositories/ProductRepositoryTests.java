package com.pessoalprojeto.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pessoalprojeto.dscatalog.entities.Product;
import com.pessoalprojeto.dscatalog.tests.Factory;

@DataJpaTest
/*
 * Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é
 * transacional e dá rollback ao final. (teste de unidade: repository)
 */
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private Long idExistente;
	private Long idNaoExistente;
	private Long totalProductsNoBanco;

	@BeforeEach
	/*
	 * @BeforeEach é uma anotação usada no JUnit 5 para indicar que um método deve
	 * ser executado antes de cada teste na classe de teste. Ele é usado para
	 * configurar o ambiente de teste para cada caso de teste, garantindo que cada
	 * teste comece com um estado consistente.
	 */
	void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalProductsNoBanco = 25L;
	}
	
	@Test
	public void saveShouldPersistirComAutoincrementoWhenIdNulo() {
		Product produto = Factory.criarProduto();
		
		produto.setId(null);
		
		produto = repository.save(produto);
		
		Assertions.assertNotNull(produto.getId());
		Assertions.assertEquals(totalProductsNoBanco + 1, produto.getId());
	}

	@Test
	public void deleteShouldDeletarObjectWhenIdExistir() {
		repository.deleteById(idExistente);

		Optional<Product> result = repository.findById(idExistente);

		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldLancarEmptyResultDataAccessExceptionWhenIdNaoExistir() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(idNaoExistente);
		});
	}
	
	@Test
	public void findByIdShouldRetornarOptionalProductNaoVazioWhenIdExisitir() {
		
		Optional <Product> result = repository.findById(idExistente);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldRetornarOptionalProductVazioWhenIdNaoExisitir() {
		
		Optional <Product> result = repository.findById(idNaoExistente);
		
		Assertions.assertTrue(result.isEmpty());

}
	
}
