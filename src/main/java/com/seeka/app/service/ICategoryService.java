package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.dto.CategoryDto;

public interface ICategoryService {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(UUID categoryId);

}
