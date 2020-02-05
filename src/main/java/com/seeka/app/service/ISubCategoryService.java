package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;
import java.util.Map;


import com.seeka.app.dto.SubCategoryDto;

public interface ISubCategoryService {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(BigInteger categoryId);

    public SubCategoryDto getSubCategoryById(BigInteger subCategoryId);

    public Map<String, Object> saveSubCategory(SubCategoryDto subCategoryDto);

    public boolean deleteSubCategory(String subCategoryId);

}
