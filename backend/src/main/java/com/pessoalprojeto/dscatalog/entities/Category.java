package com.pessoalprojeto.dscatalog.entities;

import java.io.Serializable;
import java.util.Objects;

/*A interface Serializable indica que um objeto pode ser convertido em um formato que pode ser
armazenado ou transmitido, e posteriormente convertido de volta para o seu estado original.*/
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Atributos
	private Long id; //id da categoria
	private String name; //nome da categoria
	
	//Construtores
	public Category() {
		
	}
	
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	//Getters and Setters
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

	//Equals e Hashcode
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
