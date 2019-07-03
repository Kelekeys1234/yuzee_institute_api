package com.seeka.app.dao;import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteDetails;

@Repository
public class InstituteDetailsDAO implements IInstituteDetailsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(InstituteDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteDetails obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteDetails get(BigInteger id) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteDetails.class);
		crit.add(Restrictions.eq("instituteId",id));
		List<InstituteDetails> list = crit.list();
		return list !=null && !list.isEmpty()?list.get(0):null;
		
//		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//		CriteriaQuery<InstituteDetails> criteriaQuery = criteriaBuilder.createQuery(InstituteDetails.class);
//		Root<InstituteDetails> root = criteriaQuery.from(InstituteDetails.class);
//		criteriaQuery.select(root);
//		 
//		Query<InstituteDetails> query = session.createQuery(criteriaQuery);
//		List<InstituteDetails> results = query.getResultList();
//		return results !=null && !results.isEmpty()?results.get(0):null;
		
	}
	
	@Override
	public List<InstituteDetails> getAllInstituteByCountry(BigInteger countryId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteDetails.class);
		crit.add(Restrictions.eq("countryObj.id",countryId));
		return crit.list();
	}
	
	@Override
	public List<InstituteDetails> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteDetails.class); 
		return crit.list();
	}
	
	/*@Override
	public Institute getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(UserInfo.class);
		crit.add(Restrictions.eq("emailId",email));
		List<UserInfo> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}*/
	
}
