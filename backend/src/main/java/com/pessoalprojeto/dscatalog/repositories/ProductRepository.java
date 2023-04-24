package com.pessoalprojeto.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoalprojeto.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
