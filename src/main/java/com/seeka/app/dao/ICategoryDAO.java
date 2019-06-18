package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

public interface ICategoryDAO {

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(BigInteger categoryId);

    public Category findCategoryById(BigInteger category);

    public Category findById(BigInteger id);

    public boolean saveCategory(Category category);

}
