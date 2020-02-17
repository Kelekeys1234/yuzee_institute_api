package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryDAO {

     List<CategoryDto> getAllCategories();

     CategoryDto getCategoryById(String categoryId);

     Category findCategoryById(String category);

     Category findById(String id);

     boolean saveCategory(Category category);

}
