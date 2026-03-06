package com.projedata.project.presentation.controller;

import com.projedata.project.domain.model.RawMaterial;
import com.projedata.project.domain.services.ProductCompositionService;
import com.projedata.project.presentation.dto.CompositionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products/{productId}/composition")
@CrossOrigin(origins = "*")
public class ProductCompositionController {


    private final ProductCompositionService compositionService;

    public ProductCompositionController(ProductCompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @PostMapping
    public ResponseEntity<Void> addRawMaterial(
            @PathVariable Long productId,
            @RequestBody @Validated CompositionRequest request) {

        compositionService.addRawMaterial(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RawMaterial>> list(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                compositionService.listByProduct(productId)
        );
    }

    @DeleteMapping("/{compositionId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long productId,
            @PathVariable Long compositionId) {

        compositionService.remove(productId, compositionId);
        return ResponseEntity.noContent().build();
    }


    }


