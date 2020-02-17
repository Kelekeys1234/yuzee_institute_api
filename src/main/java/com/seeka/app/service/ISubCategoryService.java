package com.seeka.app.service;import java.util.List;
import java.util.Map;

import com.seeka.app.dto.SubCategoryDto;

public interface ISubCategoryService {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(String categoryId);

    public SubCategoryDto getSubCategoryById(String subCategoryId);

    public Map<String, Object> saveSubCategory(SubCategoryDto subCategoryDto);

    public boolean deleteSubCategory(String subCategoryId);

}
