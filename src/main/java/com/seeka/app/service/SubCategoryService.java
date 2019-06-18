package com.seeka.app.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Subcategory;
import com.seeka.app.dao.ICategoryDAO;
import com.seeka.app.dao.ISubCategoryDAO;
import com.seeka.app.dto.SubCategoryDto;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    private ISubCategoryDAO subCategoryDAO;

    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    public List<SubCategoryDto> getAllSubCategories() {
        return subCategoryDAO.getAllSubCategories();
    }

    @Override
    public List<SubCategoryDto> getSubCategoryByCategory(BigInteger categoryId) {
        return subCategoryDAO.getSubCategoryByCategory(categoryId);
    }

    @Override
    public SubCategoryDto getSubCategoryById(BigInteger subCategoryId) {
        return subCategoryDAO.getSubCategoryById(subCategoryId);
    }

    @Override
    public Map<String, Object> saveSubCategory(SubCategoryDto subCategoryDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        Subcategory subCategory = null;
        boolean status = true;
        if (subCategoryDto != null) {
            subCategory = new Subcategory();
            if (subCategoryDto.getId() != null) {
                subCategory = subCategoryDAO.findById(subCategoryDto.getId());
            }
            subCategory.setName(subCategoryDto.getName());
            subCategory.setCategory(categoryDAO.findCategoryById(subCategoryDto.getCategoryId()));
            subCategory.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
            subCategory.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
            subCategory.setIsDeleted(true);
            status = subCategoryDAO.saveSubCategory(subCategory);
            if (status) {
                response.put("status", IConstant.SUCCESS_CODE);
                response.put("message", IConstant.SUCCESS_MESSAGE);
            } else {
                response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.put("message", IConstant.SUB_CATEGORY_ERROR);
            }
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.SUB_CATEGORY_NOT_FOUND);
        }
        return response;
    }

    @Override
    public boolean deleteSubCategory(BigInteger subCategoryId) {
        boolean status = true;
        try {
            if (subCategoryId != null) {
                Subcategory subCategory = subCategoryDAO.findById(subCategoryId);
                if (subCategory != null) {
                    subCategory.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                    subCategory.setIsDeleted(false);
                    subCategoryDAO.saveSubCategory(subCategory);
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;
    }
}
