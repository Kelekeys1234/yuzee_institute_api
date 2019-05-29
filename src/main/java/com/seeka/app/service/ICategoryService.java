package com.seeka.app.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(UUID categoryId);

    public Map<String, Object> saveCategory(Category category);

    public boolean deleteCategory(UUID categoryId);

}
