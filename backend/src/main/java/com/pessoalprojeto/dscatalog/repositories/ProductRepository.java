package com.pessoalprojeto.dscatalog.repositories;

import com.pessoalprojeto.dscatalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoalprojeto.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE " +
            "(COALESCE(:categories) IS NULL OR cats IN :categories) AND " +
            "(LOWER(obj.name) LIKE LOWER(CONCAT('%', :name, '%')) )")
    Page<Product> find(String name, List<Category> categories, Pageable pageable);

    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj in :products")
    List<Product> findProductsWithCategories(List<Product> products);


}
