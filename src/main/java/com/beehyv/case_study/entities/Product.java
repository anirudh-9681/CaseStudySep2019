package com.beehyv.case_study.entities;

import com.beehyv.case_study.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private int price;
    private String details;
    private String category;
    private String subcategory;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setDTO(ProductDTO productDTO){
        if (Objects.nonNull(productDTO.getName())) name = productDTO.getName();
        if (productDTO.getPrice() != 0) price = productDTO.getPrice();
        if (Objects.nonNull(productDTO.getDetails())) details = productDTO.getDetails();
        if (Objects.nonNull(productDTO.getCategory())) category = productDTO.getCategory();
        if (Objects.nonNull(productDTO.getSubcategory())) subcategory = productDTO.getSubcategory();
    }
    @JsonIgnore
    public ProductDTO getDTO(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productId);
        productDTO.setName(name);
        productDTO.setPrice(price);
        productDTO.setDetails(details);
        productDTO.setCategory(category);
        productDTO.setSubcategory(subcategory);
        return productDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
