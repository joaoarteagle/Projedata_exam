package com.projedata.project.domain.services;

import com.projedata.project.domain.model.Product;
import com.projedata.project.domain.model.RawMaterial;
import com.projedata.project.domain.repository.ProductRepository;
import com.projedata.project.domain.repository.RawMaterialRepository;
import com.projedata.project.presentation.dto.ProductionSuggestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductionSuggestionService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public List<ProductionSuggestionDTO> suggestProduction() {

        List<Product> products = productRepository.findAll();
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();

        Map<Long, Double> stock = new HashMap<>();

        for (RawMaterial rm : rawMaterials) {
            stock.put(rm.getId(), rm.getStockQuantity());
        }

        products.sort((a, b) -> b.getPrice().compareTo(a.getPrice()));

        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();

        for (Product product : products) {

            int maxUnits = Integer.MAX_VALUE;

            for (var comp : product.getComposition()) {

                double available = stock.get(comp.getRawMaterial().getId());
                int possible = (int) (available / comp.getRequiredQuantity());

                if (possible < maxUnits) {
                    maxUnits = possible;
                }
            }

            if (maxUnits <= 0) continue;

            for (var comp : product.getComposition()) {

                Long id = comp.getRawMaterial().getId();
                double used = comp.getRequiredQuantity() * maxUnits;

                stock.put(id, stock.get(id) - used);
            }

            suggestions.add(
                    new ProductionSuggestionDTO(
                            product.getId(),
                            product.getName(),
                            maxUnits,
                            product.getPrice().doubleValue(),
                            product.getPrice().doubleValue() * maxUnits
                    )
            );

            System.out.println("Product: " + product.getName());

            for (var comp : product.getComposition()) {

                System.out.println("Material: " + comp.getRawMaterial().getName());
                System.out.println("Available: " + stock.get(comp.getRawMaterial().getId()));
                System.out.println("Required: " + comp.getRequiredQuantity());
            }
        }

        return suggestions;
    }
}