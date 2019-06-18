package com.seeka.app.dao;import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Subcategory;
import com.seeka.app.dto.SubCategoryDto;

public interface ISubCategoryDAO {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(BigInteger categoryId);

    public SubCategoryDto getSubCategoryById(BigInteger subCategoryId);

    public boolean saveSubCategory(Subcategory subCategory);

    public Subcategory findById(BigInteger id);

}
