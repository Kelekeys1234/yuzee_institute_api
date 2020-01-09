package com.seeka.app.service;import java.math.BigInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Category;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dto.CategoryDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public CategoryDto getCategoryById(BigInteger categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }

    @Override
    public Map<String, Object> saveCategory(Category categoryRequest) {
        Map<String, Object> response = new HashMap<String, Object>();
        Category category = null;
        boolean status = true;
        if (categoryRequest != null) {
            category = new Category();
            if (categoryRequest.getId() != null) {
                category = categoryDAO.findById(categoryRequest.getId());
            } else {
                category.setActive(true);
            }
            category.setName(categoryRequest.getName());
            status = categoryDAO.saveCategory(category);
            if (status) {
                response.put("status", IConstant.SUCCESS_CODE);
                response.put("message", IConstant.SUCCESS_MESSAGE);
            } else {
                response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.put("message", IConstant.SUB_CATEGORY_ERROR);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.CATEGORY_NOT_FOUND);
        }
        return response;
    }

    @Override
    public boolean deleteCategory(BigInteger categoryId) {
        boolean status = true;
        try {
            if (categoryId != null) {
                Category subCategory = categoryDAO.findById(categoryId);
                if (subCategory != null) {
                    subCategory.setActive(false);
                    categoryDAO.saveCategory(subCategory);
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;
    }
}
