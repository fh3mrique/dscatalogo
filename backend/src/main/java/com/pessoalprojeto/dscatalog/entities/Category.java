package com.pessoalprojeto.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
	
	//Atriburtos de auditória
	
	/*A anotação @Column é utilizada para definir a coluna do banco de dados para o campo createAt. No caso, a 
	definição da coluna indica que o campo armazena um valor de data e hora sem informações de fuso horário. 
	O tipo de dado utilizado para representar o valor do campo é Instant, que é uma classe do pacote java.time que 
	representa um instante na linha do tempo em UTC (Tempo Universal Coordenado).*/
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt; //instante que essse registro foi criado
	private Instant updateAt; //instante que essse registro foi criado
	
	
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
	
	//GETTERS dados de auditória(NÃO precisamos de set pq em teoria não podemos setar um dado de auditoria)
	public Instant getCreateAt() {
		return createdAt;
	}

	public Instant getUpdatAT() {
		return updateAt;
	}

	//MÉTODOS de auditória
	
	@PrePersist
	/*A anotação @PrePersist é usada para indicar que um método de uma classe de entidade JPA deve ser 
	executado antes que a entidade seja persistida no banco de dados.*/
	public void registerCreateAT() {
		
		/*utiliza a classe Instant do pacote java.time para obter o instante atual na linha do tempo em UTC, usando o 
		 método now(). Esse valor é atribuído ao campo createAT da classe.*/
		createdAt = Instant.now();
	}
	
	@PreUpdate
	/*A anotação @PreUpdate é usada em uma classe de entidade JPA (Java Persistence API) para indicar que um 
	método deve ser executado antes que a entidade seja atualizada no banco de dados.*/
	public void registerUpdateAt() {
		updateAt = Instant.now();
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
