package com.pessoalprojeto.dscatalog.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
/*a anotação "@Entity" é usada no JPA para indicar que uma classe representa uma tabela em um banco de dados 
relacional. Ao marcar a classe com essa anotação, o JPA automaticamente cria uma tabela com o mesmo nome da 
classe no banco de dados e cada atributo da classe mapeado para uma coluna no banco de dados deve ser 
marcado com a anotação "@Column".*/
@Table(name =  "tb_category")
/*A anotação "@Table" é uma anotação do JPA (Java Persistence API) que é usada para especificar a tabela do banco
de dados na qual uma entidade será mapeada.
Ao usar a anotação "@Table", é possível definir o nome da tabela, o esquema (schema) da tabela, o catálogo (catalog)
da tabela, o nome da sequência usada para gerar valores para a chave primária e outras propriedades da tabela.*/
public class Category implements Serializable 
/*A interface Serializable indica que um objeto pode ser convertido em um formato que pode ser
armazenado ou transmitido, e posteriormente convertido de volta para o seu estado original.*/ {

	private static final long serialVersionUID = 1L;
	
	//ATRIBUTOS
	@Id
	/*A anotação "@Id" no JPA é usada para indicar que um atributo ou um conjunto de atributos representam a 
	chave primária de uma entidade. É necessário para que o JPA possa identificar a chave primária da entidade e 
	realizar operações de persistência no banco de dados. Também é possível especificar o tipo de geração da chave
	primária, como autoincrement, sequência ou outros tipos de geração suportados pelo banco de dados.*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
