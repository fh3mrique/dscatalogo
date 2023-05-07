package com.pessoalprojeto.dscatalog.tests;

import java.time.Instant;

import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.entities.Product;

public class Factory {
	
	public static Product criarProduto () {
		Product product = new Product(1L, "Phone", "Goodphone", 800.0, "img/10-big.jpg", Instant.parse("2020-07-14T10:00:00Z"));
		product.getCategories().add(new Category(2l, "eletronics"));
		
		return product;
	}
	
	public static ProductDTO criarProdutoDTO() {
		Product produtos = criarProduto();
		
		return new ProductDTO(produtos, produtos.getCategories());
	}
	
	

}
