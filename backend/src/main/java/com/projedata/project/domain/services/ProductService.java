package com.projedata.project.domain.services;
import com.projedata.project.domain.model.Composition;
import com.projedata.project.domain.model.Product;
import com.projedata.project.domain.model.RawMaterial;
import com.projedata.project.domain.repository.ProductRepository;
import com.projedata.project.domain.repository.RawMaterialRepository;
import com.projedata.project.presentation.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductService(ProductRepository productRepository,
                          RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public Product create(ProductRequest request){

        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());

        List<Composition> compositions = request.composition()
                .stream()
                .map(item -> {

                    RawMaterial rawMaterial = rawMaterialRepository
                            .findById(item.rawMaterialId())
                            .orElseThrow();

                    Composition composition = new Composition();
                    composition.setProduct(product);
                    composition.setRawMaterial(rawMaterial);
                    composition.setRequiredQuantity(item.requiredQuantity());

                    return composition;

                }).toList();

        product.setComposition(compositions);

        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public Product update(Long id, Product updateProduct){
        Product product = findById(id);
        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());

        return productRepository.save(product);

    }

    public void deleteProduct(Long id){
        Product product = findById(id);
        productRepository.delete(product);
    }


}
