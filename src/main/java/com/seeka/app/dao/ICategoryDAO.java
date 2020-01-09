package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryDAO {

     List<CategoryDto> getAllCategories();

     CategoryDto getCategoryById(BigInteger categoryId);

     Category findCategoryById(BigInteger category);

     Category findById(BigInteger id);

     boolean saveCategory(Category category);

}
