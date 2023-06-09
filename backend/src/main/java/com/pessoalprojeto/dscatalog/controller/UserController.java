package com.pessoalprojeto.dscatalog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pessoalprojeto.dscatalog.dto.UserDTO;
import com.pessoalprojeto.dscatalog.dto.UserInsertDTO;
import com.pessoalprojeto.dscatalog.dto.UserUpdateDTO;
import com.pessoalprojeto.dscatalog.services.UserService;

@RestController
@RequestMapping(value = "users")
public class UserController {

	// ATRIBUTOS

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable) {
		
		Page<UserDTO> lista = service.findAllPaged(pageable);

		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

		UserDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert( @Valid @RequestBody UserInsertDTO dto) {
		UserDTO newDto =  service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {

		UserDTO newdto = service.update(id, dto);

		return ResponseEntity.ok().body(newdto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
