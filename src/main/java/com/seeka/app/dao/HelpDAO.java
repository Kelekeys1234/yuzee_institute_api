package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.HelpAnswer;
import com.seeka.app.bean.HelpCategory;
import com.seeka.app.bean.HelpSubCategory;
import com.seeka.app.bean.SeekaHelp;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class HelpDAO implements IHelpDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(SeekaHelp seekaHelp) {
        Session session = sessionFactory.getCurrentSession();
        session.save(seekaHelp);
    }

    @Override
    public HelpCategory getHelpCategory(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        HelpCategory obj = session.get(HelpCategory.class, id);
        return obj;
    }

    @Override
    public HelpSubCategory getHelpSubCategory(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        HelpSubCategory obj = session.get(HelpSubCategory.class, id);
        return obj;
    }

    @Override
    public SeekaHelp get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        SeekaHelp obj = session.get(SeekaHelp.class, id);
        return obj;
    }

    @Override
    public void update(SeekaHelp seekaHelp) {
        Session session = sessionFactory.getCurrentSession();
        session.update(seekaHelp);
    }

    @Override
    public int findTotalHelpRecord() {
        int status = 1;
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select count(*) from seeka_help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    @Override
    public List<SeekaHelp> getAll(int pageNumber, Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sh.id , sh.category_id, sh.sub_category_id, sh.title , sh.descritpion , sh.is_questioning FROM seeka_help sh "
                        + " where sh.is_active = 1 and sh.deleted_on IS NULL ORDER BY sh.created_on DESC ";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<SeekaHelp> helps = new ArrayList<>();
        SeekaHelp help = null;
        for (Object[] row : rows) {
            help = new SeekaHelp();
            help.setId(new BigInteger(row[0].toString()));
            if (row[1] != null) {
                help.setCategory(getHelpCategory(new BigInteger(row[1].toString()), session));
            }
            if (row[2] != null) {
                help.setSubCategory(getHelpSubCategory(new BigInteger(row[2].toString()), session));
            }
            if (row[3] != null) {
                help.setTitle(row[3].toString());
            }
            if (row[4] != null) {
                help.setDescritpion(row[4].toString());
            }
            if (row[5] != null) {
                help.setIsQuestioning(Boolean.valueOf(row[5].toString()));
            }
            helps.add(help);
        }
        return helps;
    }

    private HelpSubCategory getHelpSubCategory(BigInteger id, Session session) {
        HelpSubCategory subCategory = null;
        if (id != null) {
            subCategory = session.get(HelpSubCategory.class, id);
        }
        return subCategory;
    }

    private HelpCategory getHelpCategory(BigInteger id, Session session) {
        HelpCategory category = null;
        if (id != null) {
            category = session.get(HelpCategory.class, id);
        }
        return category;
    }

    @Override
    public void save(HelpCategory helpCategory) {
        Session session = sessionFactory.getCurrentSession();
        session.save(helpCategory);
    }

    @Override
    public void save(HelpSubCategory helpSubCategory) {
        Session session = sessionFactory.getCurrentSession();
        session.save(helpSubCategory);
    }

    @Override
    public List<HelpSubCategory> getSubCategoryByCategory(BigInteger categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(HelpSubCategory.class, "helpSubCategory");
        crit.createAlias("helpSubCategory.categoryId", "users");
        return crit.add(Restrictions.eq("users.id", categoryId)).list();
    }

    @Override
    public List<SeekaHelp> getHelpByCategory(BigInteger categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SeekaHelp.class, "seekaHelp");
        crit.createAlias("seekaHelp.category", "category");
        return crit.add(Restrictions.eq("category.id", categoryId)).add(Restrictions.eq("isActive", true)).list();
    }

    @Override
    public Integer findTotalHelpRecordBySubCategory(BigInteger sub_category_id) {
        int status = 1;
        Integer helpCount = null;
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select count(*) from seeka_help sa where sa.is_active = " + status + " and sa.deleted_on IS NULL and sub_category_id=" + sub_category_id;
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        BigInteger count = (BigInteger) query.uniqueResult();
        if (count != null) {
            helpCount = count.intValue();
        }
        return helpCount;
    }

    @Override
    public List<HelpSubCategory> getAllHelpSubCategories() {
        Session session = sessionFactory.getCurrentSession();
        List<HelpSubCategory> list = session.createCriteria(HelpSubCategory.class).list();
        return list;
    }

    @Override
    public void save(HelpAnswer helpAnswer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(helpAnswer);
    }

    @Override
    public List<HelpAnswer> getAnswerByHelpId(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(HelpAnswer.class, "helpAnswer");
        crit.createAlias("helpAnswer.seekaHelp", "seekaHelp");
        return crit.add(Restrictions.eq("seekaHelp.id", userId)).list();
    }

    @Override
    public List<HelpCategory> getAllCategory() {
        Session session = sessionFactory.getCurrentSession();
        List<HelpCategory> list = session.createCriteria(HelpCategory.class).list();
        return list;
    }
}
