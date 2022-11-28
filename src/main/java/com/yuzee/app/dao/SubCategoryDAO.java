package com.yuzee.app.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.SubCategory;
import com.yuzee.app.dto.SubCategoryDto;
import com.yuzee.app.repository.SubCategoryRepository;

@Repository
@SuppressWarnings("unchecked")
public class SubCategoryDAO implements ISubCategoryDAO {

	@Autowired
	private SubCategoryRepository subCategoryRepository;
	private MongoTemplate mongoTemplate;

	@Override
	public List<SubCategoryDto> getAllSubCategories() {
		List<SubCategory> rows = subCategoryRepository.findAll();
		List<SubCategoryDto> subCategoryDtos = new ArrayList<>();
		SubCategoryDto subCategoryDto = null;
		for (SubCategory row : rows) {
			subCategoryDto = new SubCategoryDto();
			subCategoryDto.setId(row.getId());
			subCategoryDto.setName(row.getName());
			subCategoryDto.setCategoryId(row.getCategory().getId());
			subCategoryDto.setCategoryName(row.getCategory().getName());
			subCategoryDtos.add(subCategoryDto);
		}
		return subCategoryDtos;
	}

	@Override
	public List<SubCategoryDto> getSubCategoryByCategory(String categoryId) {
		List<SubCategoryDto> subCategoryDtos = new ArrayList<SubCategoryDto>();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("category.id").is(categoryId));
		List<SubCategory> subCategories =mongoTemplate.find(query, SubCategory.class,"subcategory");
		if (subCategories != null && !subCategories.isEmpty()) {
			for (SubCategory sc : subCategories) {
				SubCategoryDto subCategoryDto = new SubCategoryDto();
				subCategoryDto.setId(sc.getId());
				subCategoryDto.setName(sc.getName());
				if (sc.getCategory() != null) {
					subCategoryDto.setCategoryId(sc.getCategory().getId());
				}
				subCategoryDtos.add(subCategoryDto);
			}
		}
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		subCategoryDto.setId("111111");
		subCategoryDto.setName("All");
		subCategoryDtos.add(subCategoryDto);
		return subCategoryDtos;
	}

	@Override
	public SubCategoryDto getSubCategoryById(String subCategoryId) {
		SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElse(null);
		SubCategoryDto subCategoryDto = null;
		if (subCategory != null) {
			subCategoryDto = new SubCategoryDto();
			subCategoryDto.setId(subCategory.getId());
			subCategoryDto.setName(subCategory.getName());
			if (subCategory.getCategory() != null) {
				subCategoryDto.setCategoryId(subCategory.getCategory().getId());
			}
		}
		return subCategoryDto;
	}

	@Override
	public boolean saveSubCategory(SubCategory subCategory) {
		boolean status = true;
		try {
			subCategoryRepository.save(subCategory);
		} catch (Exception exception) {
			status = false;
		}
		return status;
	}

	@Override
	public SubCategory findById(String id) {
		return subCategoryRepository.findById(id).orElse(null);
	}
}
