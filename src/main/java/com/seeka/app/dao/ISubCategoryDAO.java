package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.dto.SubCategoryDto;

public interface ISubCategoryDAO {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(UUID categoryId);

    public SubCategoryDto getSubCategoryById(UUID subCategoryId);

}
