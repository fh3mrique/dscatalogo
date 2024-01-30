package com.pessoalprojeto.dscatalog.repositories;

import com.pessoalprojeto.dscatalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoalprojeto.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT obj FROM Product obj INNER JOIN obj.categories cats WHERE " +
            ":category IN cats")
    Page<Product> find(Category category, Pageable pageable);
}
