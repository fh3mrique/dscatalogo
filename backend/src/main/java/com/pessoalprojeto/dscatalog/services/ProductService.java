package com.pessoalprojeto.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.entities.Product;
import com.pessoalprojeto.dscatalog.repositories.ProductRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

@Service

public class ProductService {

	// ATRIBUTOS

	@Autowired
	private ProductRepository repository;

	// MÉTODOS

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> listaProduct = repository.findAll(pageRequest);

		return listaProduct.map(x -> new ProductDTO(x));

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

		// entity.setName(dto.getName());

		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {

			Product entity = repository.getOne(id);

			// entity.setName(dto.getName());

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

}
