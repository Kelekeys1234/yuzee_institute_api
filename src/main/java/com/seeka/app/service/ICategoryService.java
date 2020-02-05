package com.seeka.app.service;import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryService {

     List<CategoryDto> getAllCategories();

     CategoryDto getCategoryById(String categoryId);

     Map<String, Object> saveCategory(Category category);

     boolean deleteCategory(String categoryId);

}
