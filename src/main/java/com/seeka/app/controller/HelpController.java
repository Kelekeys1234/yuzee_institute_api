package com.seeka.app.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.HelpAnswerDto;
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
    public ResponseEntity<?> save(@Valid @RequestBody final HelpDto helpDto, @RequestHeader BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(helpService.save(helpDto, userId));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.get(id));
    }

    @RequestMapping(value = "/by/category/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getHelpByCategoryId(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.getHelpByCategory(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable BigInteger id, @RequestBody final HelpDto helpDto, @RequestHeader BigInteger userId) {
        return ResponseEntity.accepted().body(helpService.update(helpDto, id, userId));
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

    @RequestMapping(value = "/category/{categoryId}/subCategory", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getSubCatgeoryByCategory(@PathVariable BigInteger categoryId) throws Exception {
        return ResponseEntity.accepted().body(helpService.getSubCategoryByCategory(categoryId));
    }

    @RequestMapping(value = "/subCategory/count", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getSubCategoryCount() throws Exception {
        return ResponseEntity.accepted().body(helpService.getSubCategoryCount());
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveAnswer(@RequestBody HelpAnswerDto helpAnswerDto) throws Exception {
        return ResponseEntity.accepted().body(helpService.saveAnswer(helpAnswerDto));
    }

    @RequestMapping(value = "/answer/{helpId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAnswerByHelpId(@PathVariable BigInteger helpId) throws Exception {
        return ResponseEntity.accepted().body(helpService.getAnswerByHelpId(helpId));
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCategory() throws Exception {
        return ResponseEntity.accepted().body(helpService.getCategory());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.delete(id));
    }

    @RequestMapping(value = "/status/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> updateStatus(@PathVariable BigInteger id, @RequestHeader(required = false) BigInteger userId, @RequestParam String status) throws Exception {
        return ResponseEntity.accepted().body(helpService.updateStatus(id, userId, status));
    }
}