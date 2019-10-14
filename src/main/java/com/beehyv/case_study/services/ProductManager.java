package com.beehyv.case_study.services;

import com.beehyv.case_study.dto.ProductDTO;
import com.beehyv.case_study.entities.Product;
import com.beehyv.case_study.repositories.ProductRepo;
import com.beehyv.case_study.utilities.FilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductManager {
    @Autowired
    ProductRepo productRepo;

    public ProductDTO addProduct(ProductDTO productDTO) {
        if (productRepo.existsByProductId(productDTO.getProductId())) {
            return null;
        }
        Product product = new Product();
        product.setDTO(productDTO);
        try {
            return productRepo.save(product).getDTO();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        if (productRepo.existsByProductId(productDTO.getProductId())) {
            Product product = productRepo.findByProductId(productDTO.getProductId());
            product.setDTO(productDTO);
            try {
                return productRepo.save(product).getDTO();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ProductDTO getProductById(long productId) {
        if (productRepo.existsByProductId(productId)) {
            return productRepo.findByProductId(productId).getDTO();
        }
        return null;
    }

    public List<ProductDTO> searchBySubCategory(String searchString) {
        return productRepo.findAllBySubcategoryContaining(searchString)
                .stream()
                .filter(Objects::nonNull)
                .map(Product::getDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchByCategory(String searchString) {
        return productRepo.findAllByCategoryContaining(searchString)
                .stream()
                .filter(Objects::nonNull)
                .map(Product::getDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchByName(String searchString) {
        return productRepo.findAllByNameContaining(searchString)
                .stream()
                .filter(Objects::nonNull)
                .map(Product::getDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchByDetail(String searchString) {
        return productRepo.findAllByDetailsContaining(searchString)
                .stream()
                .filter(Objects::nonNull)
                .map(Product::getDTO)
                .collect(Collectors.toList());
    }

    public Set<ProductDTO> searchByString(String searchString) {

        return Stream.of(
                searchByName(searchString),
                searchByCategory(searchString),
                searchBySubCategory(searchString),
                searchByDetail(searchString))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<ProductDTO> searchWithFilters(Map<String, String> map) {
        FilterSpecification filterSpecification = new FilterSpecification(map);
        return productRepo.findAll(filterSpecification)
                .stream()
                .filter(Objects::nonNull)
                .map(Product::getDTO)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllCategories(){
        Set<String> categories = new HashSet<>();
        for (Product product: productRepo.findAll()){
            categories.add(product.getCategory());
        }
        return categories;
    }
}
