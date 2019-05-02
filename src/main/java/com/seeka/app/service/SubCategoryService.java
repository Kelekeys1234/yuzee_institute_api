package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.ISubCategoryDAO;
import com.seeka.app.dto.SubCategoryDto;

@Service
@Transactional
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    private ISubCategoryDAO subCategoryDAO;

    @Override
    public List<SubCategoryDto> getAllSubCategories() {
        return subCategoryDAO.getAllSubCategories();
    }

    @Override
    public List<SubCategoryDto> getSubCategoryByCategory(UUID categoryId) {
        return subCategoryDAO.getSubCategoryByCategory(categoryId);
    }

    @Override
    public SubCategoryDto getSubCategoryById(UUID subCategoryId) {
        return subCategoryDAO.getSubCategoryById(subCategoryId);
    }
}
