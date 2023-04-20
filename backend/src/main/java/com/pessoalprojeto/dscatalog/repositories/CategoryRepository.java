package com.pessoalprojeto.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pessoalprojeto.dscatalog.entities.Category;

@Repository
/*A anotação @Repository do Spring indica que uma classe é um repositório responsável por acessar e manipular dados
armazenados em um banco de dados ou outro tipo de armazenamento persistente. Ela permite o gerenciamento de 
transações de banco de dados e o tratamento de exceções de maneira mais adequada, e é comumente usada em 
conjunto com a camada de serviço para separar as responsabilidades de acesso a dados e lógica de negócio. 
A anotação @Repository é uma especialização da anotação @Component e é detectada automaticamente pelo 
mecanismo de component scanning do Spring.*/
public interface CategoryRepository  extends JpaRepository<Category, Long> {
/*A interface JpaRepository<T, ID> é uma extensão da interface CrudRepository da biblioteca Spring Data JPA, que 
oferece métodos adicionais para acesso a dados de entidades JPA (Java Persistence API). Com ela, é possível 
implementar automaticamente a lógica de acesso a dados para uma entidade, sem a necessidade de escrever SQL. 
Os parâmetros genéricos T e ID representam o tipo da entidade JPA e o tipo da chave primária da entidade, 
respectivamente. Através da interface JpaRepository, é possível realizar operações CRUD e outras operações 
como paginação, ordenação e pesquisa por critérios específicos.*/
}
