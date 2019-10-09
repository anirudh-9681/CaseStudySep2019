package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {
    boolean existsByProductId(int productId);
    Product findByProductId(int productId);
    List<Product> findAllBySubcategoryContaining(String subcategoryName);
    List<Product> findAllByCategoryContaining(String categoryName);
    List<Product> findAllByNameIgnoreCase(String productName);
    List<Product> findAllByDetailsContaining(String detailWord);
    List<Product> findAll(Specification<Product> specification);
}
