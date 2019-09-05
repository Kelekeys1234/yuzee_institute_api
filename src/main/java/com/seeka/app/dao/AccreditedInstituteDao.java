package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.AccreditedInstitute;

@Repository
public class AccreditedInstituteDao implements IAccreditedInstituteDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final AccreditedInstitute accreditedInstitute) {
		Session session = sessionFactory.getCurrentSession();
		session.save(accreditedInstitute);
	}

	@Override
	public void update(final AccreditedInstitute accreditedInstitute) {
		Session session = sessionFactory.getCurrentSession();
		session.update(accreditedInstitute);
	}

	@Override
	public List<AccreditedInstitute> getAccreditedInstituteList(final Integer startIndex, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(AccreditedInstitute.class, "accreditedInstitute");
		return crit.list();
	}

	@Override
	public AccreditedInstitute getAccreditedInstituteDetail(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(AccreditedInstitute.class, "accreditedInstitute");
		crit.add(Restrictions.eq("accreditedInstitute.id", instituteId));
		return (AccreditedInstitute) crit.uniqueResult();
	}

	@Override
	public AccreditedInstitute getAccreditedInstituteDetailBasedOnName(final String name, final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(AccreditedInstitute.class, "accreditedInstitute");
		crit.add(Restrictions.eq("accreditedInstitute.name", name));
		if (instituteId != null) {
			crit.add(Restrictions.not(Restrictions.eq("accreditedInstitute.id", instituteId)));
		}
		return (AccreditedInstitute) crit.uniqueResult();
	}

    @Override
    public List<AccreditedInstitute> getAllAccreditedInstitutes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AccreditedInstitute.class);
        return crit.list();
    }

}
