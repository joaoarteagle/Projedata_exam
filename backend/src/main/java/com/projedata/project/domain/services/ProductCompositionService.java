package com.projedata.project.domain.services;
import com.projedata.project.domain.model.Composition;
import com.projedata.project.domain.model.Product;
import com.projedata.project.domain.model.RawMaterial;
import com.projedata.project.domain.repository.CompositionRepository;
import com.projedata.project.domain.repository.ProductRepository;
import com.projedata.project.domain.repository.RawMaterialRepository;
import com.projedata.project.presentation.dto.CompositionRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCompositionService {


        private final CompositionRepository compositionRepository;
        private final ProductRepository productRepository;
        private final RawMaterialRepository rawMaterialRepository;


    public ProductCompositionService(
            CompositionRepository repository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository
    ) {
        this.compositionRepository = repository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public void addRawMaterial(Long productId, CompositionRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(request.rawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        Composition composition = new Composition();
        composition.setProduct(product);
        composition.setRawMaterial(rawMaterial);
        composition.setRequiredQuantity(request.requiredQuantity());

        compositionRepository.save(composition);
    }

    public List<RawMaterial> listByProduct(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }

        return compositionRepository.findByProductId(productId)
                .stream()
                .map(Composition::getRawMaterial)
                .toList();
    }



    public void remove(Long productId, Long compositionId) {

        Composition composition = compositionRepository.findById(compositionId)
                .orElseThrow(() -> new RuntimeException("Composition not found"));

        if (!composition.getProduct().getId().equals(productId)) {
            throw new RuntimeException("Composition does not belong to product");
        }

        compositionRepository.delete(composition);
    }


    }


