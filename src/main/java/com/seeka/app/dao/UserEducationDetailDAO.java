package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.GradeDetails;
import com.seeka.app.bean.UserEducationDetails;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class UserEducationDetailDAO implements IUserEducationDetailDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final UserEducationDetails userEducationDetails) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userEducationDetails);
	}

	@Override
	public void update(final UserEducationDetails hobbiesObj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(hobbiesObj);
	}

	@Override
	public UserEducationDetails get(final String id) {
		UserEducationDetails educationDetails = null;
		if (id != null) {
			Session session = sessionFactory.getCurrentSession();
			educationDetails = session.get(UserEducationDetails.class, id);
		}
		return educationDetails;
	}

	@Override
	public UserEducationDetails getUserEducationDetails(final String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEducationDetails.class, "userEducationDetails");
		criteria.add(Restrictions.eq("userEducationDetails.userId", userId));
		criteria.add(Restrictions.eq("userEducationDetails.isActive", true));
		return (UserEducationDetails) criteria.uniqueResult();
	}

	public String getGradeDetails(final String countryId, final String educationSystemId, final String grade) {
		String gpaGrade = "0.0";
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(GradeDetails.class);
		crit.add(Restrictions.eq("countryName", countryId)).add(Restrictions.eq("educationSystemId", educationSystemId)).add(Restrictions.eq("grade", grade));
		List<GradeDetails> details = crit.list();
		System.out.println("The List: " + details.size());
		ArrayList<GradeDetails> min = new ArrayList<>();
		for (GradeDetails x : details) {
			if (min.size() == 0 || Double.valueOf(x.getGpaGrade()) == Double.valueOf(min.get(0).getGpaGrade())) {
				min.add(x);
			} else if (Double.valueOf(x.getGpaGrade()) < Double.valueOf(min.get(0).getGpaGrade())) {
				min.clear();
				min.add(x);
			}
		}
		if (min != null && !min.isEmpty()) {
			gpaGrade = min.get(0).getGpaGrade();
		}
		return gpaGrade;
	}

	public List<GradeDetails> getGrades(final String countryId, final String systemId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(GradeDetails.class);
		crit.add(Restrictions.eq("countryName", countryId));
		crit.add(Restrictions.eq("educationSystemId", systemId));
		return crit.list();
	}
}