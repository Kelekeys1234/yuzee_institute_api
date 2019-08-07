package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class ErrorReportDAO implements IErrorReportDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(ErrorReport errorReport) {
        Session session = sessionFactory.getCurrentSession();
        session.save(errorReport);
    }

    @Override
    public ErrorReportCategory getErrorCategory(BigInteger errorReportCategoryId) {
        ErrorReportCategory errorReportCategory = null;
        if (errorReportCategoryId != null) {
            Session session = sessionFactory.getCurrentSession();
            errorReportCategory = session.get(ErrorReportCategory.class, errorReportCategoryId);
        }
        return errorReportCategory;
    }

    @Override
    public List<ErrorReport> getAllErrorReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReport.class);
        crit.add(Restrictions.eq("isActive", true));
        return crit.list();
    }

    @Override
    public List<ErrorReport> getErrorReportByUserId(BigInteger userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReport.class);
        crit.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("isActive", true));
        return crit.list();
    }

    @Override
    public void update(ErrorReport errorReport) {
        Session session = sessionFactory.getCurrentSession();
        session.update(errorReport);
    }

    @Override
    public List<ErrorReportCategory> getAllErrorCategory() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReportCategory.class);
        crit.add(Restrictions.eq("isActive", true));
        return crit.list();
    }
}
