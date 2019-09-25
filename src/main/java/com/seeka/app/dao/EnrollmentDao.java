package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentImage;
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
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(enrollment);
		tx.commit();
		session.close();
	}

	@Override
	public void saveEnrollmentImage(final EnrollmentImage enrollmentImage) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentImage);
	}

	@Override
	public void saveEnrollmentStatus(final EnrollmentStatus enrollmentStatus) {
		Session session = sessionFactory.getCurrentSession();
		session.save(enrollmentStatus);
	}

	@Override
	public Enrollment getEnrollment(final BigInteger enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Enrollment.class, enrollmentId);
	}

	@Override
	public List<EnrollmentImage> getEnrollmentImageList(final BigInteger enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentImage.class, "enrollmentImage");
		crit.createAlias("enrollmentImage.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		return crit.list();
	}

	@Override
	public List<EnrollmentStatus> getEnrollmentStatusDetail(final BigInteger enrollmentId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentStatus.class, "enrollmentStatus");
		crit.createAlias("enrollmentStatus.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		return crit.list();
	}

	@Override
	public EnrollmentStatus getEnrollmentStatusDetailBasedOnFilter(final BigInteger enrollmentId, final String status) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(EnrollmentStatus.class, "enrollmentStatus");
		crit.createAlias("enrollmentStatus.enrollment", "enrollment");
		crit.add(Restrictions.eq("enrollment.id", enrollmentId));
		crit.add(Restrictions.eq("enrollmentStatus.status", status));
		return (EnrollmentStatus) crit.uniqueResult();
	}

	@Override
	public List<Enrollment> getEnrollmentList(final BigInteger userId, final BigInteger courseId, final BigInteger instituteId, final BigInteger enrollmentId,
			final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Enrollment.class, "enrollment");
		if (instituteId != null) {
			crit.createAlias("enrollment.institute", "institute");
			crit.add(Restrictions.eq("institute.id", instituteId));
		}
		if (courseId != null) {
			crit.createAlias("enrollment.course", "course");
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

		if (startIndex != null && pageSize != null) {
			crit.setFirstResult(startIndex);
			crit.setMaxResults(pageSize);
		}
		return crit.list();
	}

	@Override
	public String removeEnrollmentImage(final BigInteger enrollmentImageId) {
		Session session = sessionFactory.getCurrentSession();
		EnrollmentImage enrollmentImage = session.get(EnrollmentImage.class, enrollmentImageId);
		session.remove(enrollmentImage);
		return enrollmentImage.getImageName();
	}

	@Override
	public int countOfEnrollment() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Enrollment.class, "enrollment");
		crit.setProjection(Projections.rowCount());
		return ((Long) crit.uniqueResult()).intValue();
	}

}
