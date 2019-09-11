package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentImage;
import com.seeka.app.bean.EnrollmentStatus;

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
	public List<Enrollment> getEnrollmentList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Enrollment.class, "enrollment");
		return crit.list();
	}

	@Override
	public void removeEnrollmentImage(final BigInteger enrollmentImageId) {
		Session session = sessionFactory.getCurrentSession();
		EnrollmentImage enrollmentImage = session.get(EnrollmentImage.class, enrollmentImageId);
		session.remove(enrollmentImage);
	}
}
