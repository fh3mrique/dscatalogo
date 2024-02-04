package com.pessoalprojeto.dscatalog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pessoalprojeto.dscatalog.dto.ProductDTO;
import com.pessoalprojeto.dscatalog.services.ProductService;

@RestController
@RequestMapping(value = "products")
public class ProductController {

	// ATRIBUTOS

	@Autowired
	private ProductService service;

	// MÉTODOS //implementações do método de acesso ao service do Controller

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPaged(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
			Pageable pageable) {
		
		//PARÂMETROS : page, size, sort para postman

		Page<ProductDTO> lista = service.findAllPaged(name.trim() ,categoryId, pageable);

		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

		ProductDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> insert( @Valid @RequestBody ProductDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
