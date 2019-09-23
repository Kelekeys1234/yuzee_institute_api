package com.seeka.app.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.service.IHelpService;

@RestController
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private IHelpService helpService;

    @RequestMapping(value = "pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAll(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(helpService.getAll(pageNumber, pageSize));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody final HelpDto helpDto) throws Exception {
        return ResponseEntity.accepted().body(helpService.save(helpDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable BigInteger id, @RequestBody final HelpDto helpDto) {
        return ResponseEntity.accepted().body(helpService.update(helpDto, id));
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody final HelpCategoryDto categoryDto) throws Exception {
        return ResponseEntity.accepted().body(helpService.save(categoryDto));
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCatgeory(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.getCategory(id));
    }

    @RequestMapping(value = "/subCategory", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveSubCategory(@Valid @RequestBody final HelpSubCategoryDto subCategoryDto) throws Exception {
        return ResponseEntity.accepted().body(helpService.save(subCategoryDto));
    }

    @RequestMapping(value = "/subCategory/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getSubCatgeory(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.getSubCategory(id));
    }
}
