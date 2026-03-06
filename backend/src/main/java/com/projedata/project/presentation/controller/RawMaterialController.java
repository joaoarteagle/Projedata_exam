package com.projedata.project.presentation.controller;

import com.projedata.project.domain.model.RawMaterial;
import com.projedata.project.domain.services.RawMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rawMaterial")
@CrossOrigin(origins = "*")
public class RawMaterialController {

        private final RawMaterialService rawMaterialService;

        public RawMaterialController(RawMaterialService rawMaterialService) {
            this.rawMaterialService = rawMaterialService;
        }

        @GetMapping
        public ResponseEntity<List<RawMaterial>> getAllRawMaterials(){
            return new ResponseEntity<List<RawMaterial>>(rawMaterialService.findAll(), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<RawMaterial> getRawMaterialByID(@PathVariable Long id){
            return new ResponseEntity<RawMaterial>(rawMaterialService.findById(id), HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<RawMaterial> post(@RequestBody RawMaterial rawMaterial){
            return new ResponseEntity<RawMaterial>(rawMaterialService.create(rawMaterial), HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<RawMaterial> updateRawMaterial(@PathVariable Long id, @RequestBody RawMaterial rawMaterial){
            return new ResponseEntity<RawMaterial>(rawMaterialService.update(id, rawMaterial), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long id){
            rawMaterialService.deleteRawMaterial(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }


    }


