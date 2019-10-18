package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.AuditErrorReport;
import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.exception.NotFoundException;

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
    public ErrorReport getErrorReportById(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReport.class);
        crit.add(Restrictions.eq("id", id)).add(Restrictions.eq("isActive", true));
        return (ErrorReport) crit.uniqueResult();
    }

    @Override
    public void update(ErrorReport errorReport) {
        Session session = sessionFactory.getCurrentSession();
        session.update(errorReport);
    }

    @Override
    public List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReportCategory.class);
        crit.add(Restrictions.eq("isActive", true));
        crit.add(Restrictions.eq("errorCategoryType", errorCategoryType));
        return crit.list();
    }

    @Override
    public void addErrorRepoerAudit(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ErrorReport.class);
        System.out.println("The errorReport Id" + id);
        crit.add(Restrictions.eq("id", id)).add(Restrictions.eq("isActive", true));
        ErrorReport errorReport2 = (ErrorReport) crit.uniqueResult();
        AuditErrorReport auditErrorReport = new AuditErrorReport();
        auditErrorReport.setDescription(errorReport2.getDescription());
        auditErrorReport.setErrorReportCategory(errorReport2.getErrorReportCategory());
        auditErrorReport.setUserId(errorReport2.getUserId());
        auditErrorReport.setCreatedBy(errorReport2.getCreatedBy());
        auditErrorReport.setCreatedOn(errorReport2.getCreatedOn());
        auditErrorReport.setUpdatedBy(errorReport2.getUpdatedBy());
        auditErrorReport.setUpdatedOn(errorReport2.getUpdatedOn());
        auditErrorReport.setIsActive(errorReport2.getIsActive());
        auditErrorReport.setCaseNumber(errorReport2.getCaseNumber());
        auditErrorReport.setStatus(errorReport2.getStatus());
        auditErrorReport.setCourseArticleId(errorReport2.getCourseArticleId());
        auditErrorReport.setDueDate(errorReport2.getDueDate());
        auditErrorReport.setAssigneeUserId(errorReport2.getAssigneeUserId());
        auditErrorReport.setErrorReportId(errorReport2.getId());
        auditErrorReport.setAuditCreatedBy("");
        auditErrorReport.setAuditUpdatedBy("");
        session.save(auditErrorReport);
    }

	@Override
	public List<ErrorReport> getAllErrorReportForUser(BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ErrorReport.class,"error_report");
		criteria.add(Restrictions.eq("userId", userId));
		
		return criteria.list();
	}

	@Override
	public int getErrorReportCountForUser(BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) from error_report where user_id = ? order by is_favourite desc";
		BigInteger count = (BigInteger)session.createSQLQuery(sqlQuery).setParameter(1, userId).uniqueResult();
		return count !=null ? count.intValue():0;
	}

	@Override
	public void setIsFavouriteFlag(BigInteger errorReportId, boolean isFavourite) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select count(*) from error_report where user_id = ? order by is_favourite desc";
		int updateCount = session.createNativeQuery(sqlQuery).setParameter(1, isFavourite).setParameter(2, errorReportId).executeUpdate();
		if(updateCount == 0) {
			throw new NotFoundException("No Error Report Found with Id : "+errorReportId);
		}
	}

}
