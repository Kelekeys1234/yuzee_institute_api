package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.SubCategoryDto;
import com.seeka.app.service.ISubCategoryService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

    @Autowired
    private ISubCategoryService subCategoryService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(value = IConstant.CORRELATION_ID, required = false, defaultValue = "") String correlationId,
                    @RequestHeader(value = IConstant.USER_ID, required = false) String userId, @RequestHeader(value = IConstant.SESSION_ID, required = false) String sessionId,
                    @RequestHeader(value = IConstant.TENANT_CODE, required = false) String tenantCode) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<SubCategoryDto> subCategoryDtos = subCategoryService.getAllSubCategories();
        if (subCategoryDtos != null && !subCategoryDtos.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.SUB_CATEGORY_NOT_FOUND);
        }
        response.put("subCategories", subCategoryDtos);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/getSubCategoryByCategory/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSubCategoryByCategory(@RequestHeader(value = IConstant.CORRELATION_ID, required = false, defaultValue = "") String correlationId,
                    @RequestHeader(value = IConstant.USER_ID, required = false) String userId, @RequestHeader(value = IConstant.SESSION_ID, required = false) String sessionId,
                    @RequestHeader(value = IConstant.TENANT_CODE, required = false) String tenantCode, @PathVariable UUID categoryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<SubCategoryDto> subCategoryDtos = subCategoryService.getSubCategoryByCategory(categoryId);
        if (subCategoryDtos != null && !subCategoryDtos.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.SUB_CATEGORY_NOT_FOUND);
        }
        response.put("subCategories", subCategoryDtos);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/get/{subCategoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSubCategoryById(@RequestHeader(value = IConstant.CORRELATION_ID, required = false, defaultValue = "") String correlationId,
                    @RequestHeader(value = IConstant.USER_ID, required = false) String userId, @RequestHeader(value = IConstant.SESSION_ID, required = false) String sessionId,
                    @RequestHeader(value = IConstant.TENANT_CODE, required = false) String tenantCode, @PathVariable UUID subCategoryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategoryDto != null) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.SUB_CATEGORY_NOT_FOUND);
        }
        response.put("subCategory", subCategoryDto);
        return ResponseEntity.accepted().body(response);
    }
    
    @RequestMapping(value = "/saveSubCategory", method = RequestMethod.POST)
    public ResponseEntity<?> saveArticle(@RequestBody SubCategoryDto subCategoryDto) {
        return ResponseEntity.accepted().body(subCategoryService.saveSubCategory(subCategoryDto));
    }
    
    @RequestMapping(value = "/deleteSuCategory/{subCategoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSuCategory(@PathVariable UUID subCategoryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean status = subCategoryService.deleteSubCategory(subCategoryId);
        if (status) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.SUB_CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.accepted().body(response);
    }
}
