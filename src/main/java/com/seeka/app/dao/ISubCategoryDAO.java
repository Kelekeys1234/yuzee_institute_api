package com.seeka.app.dao;import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.SubCategory;
import com.seeka.app.dto.SubCategoryDto;

public interface ISubCategoryDAO {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(BigInteger categoryId);

    public SubCategoryDto getSubCategoryById(BigInteger subCategoryId);

    public boolean saveSubCategory(SubCategory subCategory);

    public SubCategory findById(BigInteger id);

}
