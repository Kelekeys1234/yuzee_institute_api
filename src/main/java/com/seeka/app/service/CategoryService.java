package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dto.CategoryDto;

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
    public CategoryDto getCategoryById(UUID categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }
}
