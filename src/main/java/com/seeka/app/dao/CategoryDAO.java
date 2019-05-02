package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Category;
import com.seeka.app.dto.CategoryDto;

@Repository
@SuppressWarnings("unchecked")
public class CategoryDAO implements ICategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CategoryDto> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT c.id, c.name as name FROM category c");
        List<Object[]> rows = query.list();
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
        CategoryDto categoryDto = null;
        for (Object[] row : rows) {
            categoryDto = new CategoryDto();
            categoryDto.setId(UUID.fromString((row[0].toString())));
            categoryDto.setName(row[1].toString());
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(UUID categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.get(Category.class, categoryId);
        CategoryDto categoryDto = null;
        if (category != null) {
            categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
        }
        return categoryDto;
    }
}
