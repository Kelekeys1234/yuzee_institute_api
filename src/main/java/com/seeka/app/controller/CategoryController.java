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

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;
import com.seeka.app.service.ICategoryService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestHeader(value = IConstant.CORRELATION_ID, required = false, defaultValue = "") String correlationId,
                    @RequestHeader(value = IConstant.USER_ID, required = false) String userId, @RequestHeader(value = IConstant.SESSION_ID, required = false) String sessionId,
                    @RequestHeader(value = IConstant.TENANT_CODE, required = false) String tenantCode) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        if (categoryDtos != null && !categoryDtos.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.CATEGORY_NOT_FOUND);
        }
        response.put("categories", categoryDtos);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/get/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoryById(@RequestHeader(value = IConstant.CORRELATION_ID, required = false, defaultValue = "") String correlationId,
                    @RequestHeader(value = IConstant.USER_ID, required = false) String userId, @RequestHeader(value = IConstant.SESSION_ID, required = false) String sessionId,
                    @RequestHeader(value = IConstant.TENANT_CODE, required = false) String tenantCode, @PathVariable UUID categoryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        if (categoryDto != null) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.CATEGORY_NOT_FOUND);
        }
        response.put("category", categoryDto);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        return ResponseEntity.accepted().body(categoryService.saveCategory(category));
    }

    @RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteSuCategory(@PathVariable UUID categoryId) {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean status = categoryService.deleteCategory(categoryId);
        if (status) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.accepted().body(response);
    }
}
