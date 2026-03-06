package com.projedata.project.presentation.controller;

import com.projedata.project.domain.services.ProductionSuggestionService;
import com.projedata.project.presentation.dto.ProductionSuggestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production")
@RequiredArgsConstructor
public class ProductionSuggestionController {

    private final ProductionSuggestionService productionSuggestionService;

    @GetMapping("/suggestions")
    public List<ProductionSuggestionDTO> suggestProduction() {
        return productionSuggestionService.suggestProduction();
    }
}