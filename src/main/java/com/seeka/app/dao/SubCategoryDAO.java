package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.SubCategory;
import com.seeka.app.dto.SubCategoryDto;

@Repository
@SuppressWarnings("unchecked")
public class SubCategoryDAO implements ISubCategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SubCategoryDto> getAllSubCategories() {
        List<SubCategoryDto> subCategoryDtos = new ArrayList<SubCategoryDto>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(
                        "SELECT sc.id, sc.name as name, sc.category_id, c.name as catName FROM subcategory sc with(nolock) inner join category c with(nolock) on sc.category_id = c.id where sc.is_deleted=1");
        List<Object[]> rows = query.list();
        SubCategoryDto subCategoryDto = null;
        for (Object[] row : rows) {
            subCategoryDto = new SubCategoryDto();
            subCategoryDto.setId(UUID.fromString((row[0].toString())));
            subCategoryDto.setName(row[1].toString());
            subCategoryDto.setCategoryId(UUID.fromString((row[2].toString())));
            subCategoryDto.setCategoryName(row[3].toString());
            subCategoryDtos.add(subCategoryDto);
        }
        return subCategoryDtos;
    }

    @Override
    public List<SubCategoryDto> getSubCategoryByCategory(UUID categoryId) {
        List<SubCategoryDto> subCategoryDtos = new ArrayList<SubCategoryDto>();
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(SubCategory.class);
        criteria.add(Restrictions.eq("category.id", categoryId));
        List<SubCategory> subCategories = criteria.list();
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
        subCategoryDto.setId(UUID.fromString("CB52B698-98A4-4336-BCC6-98CC1F05EA66"));
        subCategoryDto.setName("All");
        subCategoryDtos.add(subCategoryDto);
        return subCategoryDtos;
    }

    @Override
    public SubCategoryDto getSubCategoryById(UUID subCategoryId) {
        Session session = sessionFactory.getCurrentSession();
        SubCategory subCategory = session.get(SubCategory.class, subCategoryId);
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
            Session session = sessionFactory.getCurrentSession();
            session.save(subCategory);
        } catch (Exception exception) {
            status = false;
        }
        return status;
    }

    @Override
    public SubCategory findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        SubCategory subCategory = session.get(SubCategory.class, id);
        return subCategory;
    }
}
