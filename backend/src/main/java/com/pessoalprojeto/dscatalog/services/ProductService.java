package com.pessoalprojeto.dscatalog.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.CategoryDTO;
import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.entities.Category;
import com.pessoalprojeto.dscatalog.entities.Product;
import com.pessoalprojeto.dscatalog.repositories.CategoryRepository;
import com.pessoalprojeto.dscatalog.repositories.ProductRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

@Service
public class ProductService {

	// ATRIBUTOS

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	// MÉTODOS
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(String name, Long categoryId, Pageable pageable) {
		List<Category> categories = (categoryId == 0) ? null: Arrays.asList(categoryRepository.getOne(categoryId));

		Page<Product> pageProducts = repository.find(name, categories ,pageable);
		repository.findProductsWithCategories(pageProducts.getContent());

		return pageProducts.map(x -> new ProductDTO(x, x.getCategories()));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);

		Product entity = obj.orElseThrow(() -> new EntityNotFoundExceptions("Entidade não encontrada"));

		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional()
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		/*
		 * No método "insert" e no método "update", é necessário definir os atributos de
		 * uma entidade do tipo "Product" com base em um parâmetro DTO e, em seguida,
		 * passar essa entidade como argumento para o construtor de "ProductDTO", que
		 * recebe uma entidade como parâmetro. Para evitar repetição de código em ambos
		 * os métodos, podemos criar um método privado auxiliar que copia os atributos
		 * de "Product" para "ProductDTO". copyDTOtoEntity (ultimo método dessa classe)
		 */

		copyDTOtoEnrity(dto, entity);

		// entity.setName(dto.getName());

		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {

			Product entity = repository.getOne(id);

			// entity.setName(dto.getName());
			copyDTOtoEnrity(dto, entity);

			entity = repository.save(entity);

			return new ProductDTO(entity);
		}

		catch (EntityNotFoundException e)

		{
			throw new EntityNotFoundExceptions("Id não encontrado");
		}
	}
	

	public void delete(Long id) {
		try {

			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {

			throw new EntityNotFoundExceptions("id não encontrado");

		} catch (DataIntegrityViolationException e) {

			throw new DatabaseException("Violação Integridade do banco ");

		}

	}

	private void copyDTOtoEnrity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());

		entity.getCategories().clear();

		for (CategoryDTO catDTO : dto.getCategories()) {
			Category cat = categoryRepository.getOne(catDTO.getId());
			entity.getCategories().add(cat);
		}

	}

}
