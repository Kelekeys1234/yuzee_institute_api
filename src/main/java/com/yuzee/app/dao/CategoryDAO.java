package com.yuzee.app.dao;import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Category;
import com.yuzee.app.dto.CategoryDto;
import com.yuzee.app.repository.CategoryRepository;

@Repository
@SuppressWarnings("unchecked")
public class CategoryDAO implements ICategoryDAO {
	@Autowired
	private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> getAll = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
        CategoryDto categoryDto = null;
        for (Category row : getAll) {
            categoryDto = new CategoryDto();
            categoryDto.setId(row.getId());
            categoryDto.setName(row.getName());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
      Category category = categoryRepository.findById(categoryId).orElse(new Category());
        CategoryDto categoryDto = null;
        if (category != null) {
            categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
        }
        return categoryDto;
    }

    @Override
    public Category findCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public boolean saveCategory(Category category) {
        boolean status = true;
        try {
        	categoryRepository.save(category);
        } catch (Exception exception) {
            status = false;
        }
        return status;
    }
}
