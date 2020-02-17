package com.seeka.app.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.util.CommonUtil;

@Repository
public class EnrollmentDao implements IEnrollmentDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addEnrollment(final Enrollment enrollment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollment);

	}

	@Override
	public void updateEnrollment(final Enrollment enrollment) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(enrollment);
	}

	@Override
	public void saveEnrollmentStatus(final EnrollmentStatus enrollmentStatus) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentStatus);
	}

	@Override
	public Enrollment getEnrollment(final String enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Enrollment.class, enrollmentId);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<EnrollmentStatus> getEnrollmentStatusDetail(final String enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentStatus.class, "enrollmentStatus");
		crit.createAlias("enrollmentStatus.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		return crit.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public EnrollmentStatus getEnrollmentStatusDetailBasedOnFilter(final String enrollmentId, final String status) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentStatus.class, "enrollmentStatus");
		crit.createAlias("enrollmentStatus.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		crit.add(Restrictions.eq("enrollmentStatus.status", status));
		return (EnrollmentStatus) crit.uniqueResult();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Enrollment> getEnrollmentList(final String userId, final String courseId, final String instituteId, final String enrollmentId,
			final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize, final Boolean isArchive, final String sortByField,
			String sortByType, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Enrollment.class, "enrollment");
		crit.createAlias("enrollment.institute", "institute");
		crit.createAlias("enrollment.course", "course");
		if (instituteId != null) {
			crit.add(Restrictions.eq("institute.id", instituteId));
		}
		if (courseId != null) {
			crit.add(Restrictions.eq("course.id", courseId));
		}
		if (enrollmentId != null) {
			crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		}
		if (status != null && !status.isEmpty()) {
			crit.add(Restrictions.eq("enrollment.status", status));
		}
		if (updatedOn != null) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			crit.add(Restrictions.ge("enrollment.updatedOn", fromDate));
			crit.add(Restrictions.le("enrollment.updatedOn", toDate));
		}
		if (userId != null) {
			crit.add(Restrictions.eq("enrollment.userId", userId));
		}
		if (isArchive != null) {
			crit.add(Restrictions.eq("enrollment.isArchive", isArchive));
		}
		if (searchKeyword != null) {
			crit.add(Restrictions.disjunction().add(Restrictions.ilike("course.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("institute.name", searchKeyword, MatchMode.ANYWHERE)));
		}
		if (sortByType == null) {
			sortByType = "DESC";
		}
		if (sortByField != null) {
			if ("courseName".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					crit.addOrder(Order.asc("course.name"));
				} else if ("DESC".equals(sortByType)) {
					crit.addOrder(Order.desc("course.name"));
				}
			} else if ("instituteName".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					crit.addOrder(Order.asc("institute.name"));
				} else if ("DESC".equals(sortByType)) {
					crit.addOrder(Order.desc("institute.name"));
				}
			}
		} else {
			if ("ASC".equals(sortByType)) {
				crit.addOrder(Order.asc("institute.id"));
			} else if ("DESC".equals(sortByType)) {
				crit.addOrder(Order.desc("institute.id"));
			}
		}

		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int countOfEnrollment(final String userId, final String courseId, final String instituteId, final String enrollmentId,
			final String status, final Date updatedOn, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Enrollment.class, "enrollment");
		crit.createAlias("enrollment.institute", "institute");
		crit.createAlias("enrollment.course", "course");
		if (instituteId != null) {
			crit.add(Restrictions.eq("institute.id", instituteId));
		}
		if (courseId != null) {
			crit.add(Restrictions.eq("course.id", courseId));
		}
		if (enrollmentId != null) {
			crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		}
		if (status != null && !status.isEmpty()) {
			crit.add(Restrictions.eq("enrollment.status", status));
		}
		if (updatedOn != null) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			crit.add(Restrictions.ge("enrollment.updatedOn", fromDate));
			crit.add(Restrictions.le("enrollment.updatedOn", toDate));
		}
		if (searchKeyword != null) {
			crit.add(Restrictions.disjunction().add(Restrictions.ilike("course.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("institute.name", searchKeyword, MatchMode.ANYWHERE)));
		}
		if (userId != null) {
			crit.add(Restrictions.eq("enrollment.userId", userId));
		}
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

	@Override
	public List<Enrollment> getAllEnrollment() {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Enrollment.class);
        return crit.list();
	}

}
