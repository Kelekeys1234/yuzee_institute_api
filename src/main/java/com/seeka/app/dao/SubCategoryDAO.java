package com.seeka.app.dao;import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Subcategory;
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
                        "SELECT sc.id, sc.name as name, sc.category_id, c.name as catName FROM subcategory sc  inner join category c  on sc.category_id = c.id where sc.is_deleted=1");
        List<Object[]> rows = query.list();
        SubCategoryDto subCategoryDto = null;
        for (Object[] row : rows) {
            subCategoryDto = new SubCategoryDto();
            subCategoryDto.setId(new BigInteger((row[0].toString())));
            subCategoryDto.setName(row[1].toString());
            subCategoryDto.setCategoryId(new BigInteger((row[2].toString())));
            subCategoryDto.setCategoryName(row[3].toString());
            subCategoryDtos.add(subCategoryDto);
        }
        return subCategoryDtos;
    }

    @Override
    public List<SubCategoryDto> getSubCategoryByCategory(BigInteger categoryId) {
        List<SubCategoryDto> subCategoryDtos = new ArrayList<SubCategoryDto>();
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Subcategory.class);
        criteria.add(Restrictions.eq("category.id", categoryId));
        criteria.addOrder(Order.asc("name"));
        List<Subcategory> subCategories = criteria.list();
        if (subCategories != null && !subCategories.isEmpty()) {
            for (Subcategory sc : subCategories) {
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
        subCategoryDto.setId(new BigInteger("111111"));
        subCategoryDto.setName("All");
        subCategoryDtos.add(subCategoryDto);
        return subCategoryDtos;
    }

    @Override
    public SubCategoryDto getSubCategoryById(BigInteger subCategoryId) {
        Session session = sessionFactory.getCurrentSession();
        Subcategory subCategory = session.get(Subcategory.class, subCategoryId);
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
    public boolean saveSubCategory(Subcategory subCategory) {
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
    public Subcategory findById(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Subcategory subCategory = session.get(Subcategory.class, id);
        return subCategory;
    }
}
