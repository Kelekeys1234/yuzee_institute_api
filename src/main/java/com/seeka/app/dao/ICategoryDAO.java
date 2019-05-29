package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryDAO {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(UUID categoryId);

    public Category findCategoryById(UUID category);

    public Category findById(UUID id);

    public boolean saveCategory(Category category);

}
