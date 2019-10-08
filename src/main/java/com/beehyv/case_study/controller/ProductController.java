package com.beehyv.case_study.controller;

import com.beehyv.case_study.dto.ProductDTO;
import com.beehyv.case_study.services.ProductManager;
import com.beehyv.case_study.utilities.ObjectMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {
    //TODO fill apis

    @Autowired
    ProductManager productManager;

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody String json) {
        try {
            ProductDTO productDTO = ObjectMapperImpl.getObjectFromJson(json, ProductDTO.class);
            productDTO = productManager.addProduct(productDTO);
            if (productDTO != null) {
                return ResponseEntity.ok().body(productDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update")
    public ResponseEntity updateProduct(@RequestBody String json) {
        try {
            ProductDTO productDTO = ObjectMapperImpl.getObjectFromJson(json, ProductDTO.class);
            productDTO = productManager.updateProduct(productDTO);
            if (productDTO != null) {
                return ResponseEntity.ok().body(productDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity getProductById(@PathVariable String productId) {
        try {
            ProductDTO productDTO = productManager.getProductById(Integer.parseInt(productId));
            if (productDTO != null) {
                return ResponseEntity.ok().body(productDTO);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity getProductByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok().body(
                productManager.searchByCategory(categoryName)
        );
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity search(@PathVariable String searchString) {
        Set<ProductDTO> productDTOs = productManager.searchByString(searchString);
        return ResponseEntity.ok().body(productDTOs);
    }

    @PostMapping("/{categoryName}/getFilteredProducts")
    public ResponseEntity getFilteredProducts(@PathVariable String categoryName, @RequestBody String json){
        return ResponseEntity.ok().build();
    }
}
