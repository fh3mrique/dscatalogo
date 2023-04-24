package com.pessoalprojeto.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	/*A anotação "@Column(columnDefinition = "TEXT")" é usada para definir uma coluna no banco de dados para um atributo de 
	uma entidade JPA. Quando usada com a definição "TEXT", o atributo é mapeado para um tipo de dados de texto longo no 
	banco de dados, permitindo que ele possa armazenar uma grande quantidade de texto, sem restrições de tamanho.*/
	private String description;
	private Double price;
	private String imgUrl;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant date;
	
	@ManyToMany
	/*A anotação "@ManyToMany" é utilizada para representar o relacionamento muitos-para-muitos entre duas entidades JPA. 
	Esse tipo de relacionamento indica que uma instância de uma entidade pode estar relacionada com várias instâncias da outra
	entidade e vice-versa. Para permitir esse tipo de relacionamento, é necessário criar uma tabela de junção no banco de dados 
	que armazene as chaves primárias de ambas as entidades relacionadas. Essa tabela de junção pode ser gerenciada pelo 
	próprio JPA ou pode ser definida manualmente utilizando a anotação "@JoinTable".*/
	@JoinTable
	/*A anotação "@JoinTable" é usada em conjunto com a anotação "@ManyToMany" em um relacionamento muitos-para-muitos 
	 entre duas entidades JPA. Ela é usada para especificar a tabela de junção que será usada para armazenar as chaves primárias
	 de ambas as entidades relacionadas.*/
	(
			/*name: define o nome da tabela de junção no banco de dados. Se não for especificado, o JPA gera um nome para a 
			 tabela automaticamente*/
			name = "tb_product_category",
			/*joinColumns: define as colunas da tabela de junção que se relacionam com a entidade atual. É uma matriz de objetos da 
			 classe @JoinColumn, que contém os parâmetros */
			joinColumns = @JoinColumn(name  = "product_id"),
			/*inverseJoinColumns: define as colunas da tabela de junção que se relacionam com a entidade oposta. É uma matriz de 
			 objetos da classe @JoinColumn, que contém os mesmos parâmetros que o joinColumns.*/
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	Set<Category> categories = new HashSet<>();
	
	public Product() {
		
	}

	public Product(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Set<Category> getCategories() {
		return categories;
	}

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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

		
	
	
	

}
