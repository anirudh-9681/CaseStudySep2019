package com.beehyv.case_study.data_initialization;

import com.beehyv.case_study.dto.ProductDTO;
import com.beehyv.case_study.entities.Product;
import com.beehyv.case_study.repositories.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class ProductDataManager {

    private ResourceLoader resourceLoader;
    private ProductRepo productRepo;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductDTO[] readFromJsonFile() throws IOException {
        System.out.println(resourceLoader);
        Resource resource = resourceLoader.getResource("classpath:products.json");
        InputStream inputStream = resource.getInputStream();
        ProductDTO[] productDTOs = new ObjectMapper().readValue(inputStream, ProductDTO[].class);
        return productDTOs;
    }

    public void saveToRepo(ProductDTO[] productDTOs) {
        if (productRepo.count() != 0) {
            return;
        }
        for (ProductDTO productDTO : productDTOs) {
            Product product = new Product();
            product.setDTO(productDTO);
            productRepo.save(product);
        }
    }

    public void run() {
        try {
            saveToRepo(readFromJsonFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
