package com.yuzee.app.dao;import java.util.List;

import com.yuzee.app.bean.SubCategory;
import com.yuzee.app.dto.SubCategoryDto;

public interface ISubCategoryDAO {

    public List<SubCategoryDto> getAllSubCategories();

    public List<SubCategoryDto> getSubCategoryByCategory(String categoryId);

    public SubCategoryDto getSubCategoryById(String subCategoryId);

    public boolean saveSubCategory(SubCategory subCategory);

    public SubCategory findById(String id);

}
