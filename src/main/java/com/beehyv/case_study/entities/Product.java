package com.beehyv.case_study.entities;

import com.beehyv.case_study.dto.ProductDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private String price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
        name = productDTO.getName();
        price = productDTO.getPrice();
        details = productDTO.getDetails();
        category = productDTO.getCategory();
        subcategory = productDTO.getSubcategory();
    }

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
}
