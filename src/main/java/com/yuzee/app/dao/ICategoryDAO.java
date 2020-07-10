package com.yuzee.app.dao;import java.util.List;

import com.yuzee.app.bean.Category;
import com.yuzee.app.dto.CategoryDto;

public interface ICategoryDAO {

     List<CategoryDto> getAllCategories();

     CategoryDto getCategoryById(String categoryId);

     Category findCategoryById(String category);

     Category findById(String id);

     boolean saveCategory(Category category);

}
