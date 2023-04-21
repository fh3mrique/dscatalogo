package com.pessoalprojeto.dscatalog.dto;

import java.io.Serializable;

import com.pessoalprojeto.dscatalog.entities.Category;

public class CategoryDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*ATRIBUTOS: Vai ter basicamente as os mesmos atributos que a entidade JPA original*/
	private Long id;
	private String name;
	
	//CONSTRUTORES
	//construtor vazio
	public CategoryDTO() {
		
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/*<<<um construtor especial que vai receber a entidade original>>>*/
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	
	//GETTERS AND SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
