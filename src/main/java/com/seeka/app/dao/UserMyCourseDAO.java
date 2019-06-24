package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserMyCourse;

@Repository
public class UserMyCourseDAO implements IUserMyCourseDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserMyCourse reviewObj) {	
		Session session =  sessionFactory.getCurrentSession();	
		session.save(reviewObj);	   					
	}
	
	@Override
	public void update(UserMyCourse reviewObj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(reviewObj);	   					
	}
	
	@Override
	public UserMyCourse get(BigInteger userId) {	
		Session session = sessionFactory.getCurrentSession();		
		UserMyCourse user = session.get(UserMyCourse.class, userId);
		return user;
	}
	
	@Override
	public UserMyCourse getDataByUserIDAndCourseID(BigInteger userId,BigInteger courseId) {	
		Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(UserMyCourse.class);
	    crit.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("courseId", courseId));
	    List<UserMyCourse> list = crit.list();
	    if(null != list && !list.isEmpty()) {
	    	return list.get(0);
	    }
	    return null;
	}
	
	@Override
	public List<UserMyCourse> getDataByUserID(BigInteger userId){
		Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(UserMyCourse.class);
	    crit.add(Restrictions.eq("userInfo.userId", userId)).add(Restrictions.eq("isActive", true));
	    return crit.list();
	}
	
	
	@Override
	public List<BigInteger> getAllCourseIdsByUser(BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();	
		String sqlQuery = "select course_id from user_my_course where is_active = 1 and user_id ='"+userId+"'";
		Query query = session.createSQLQuery(sqlQuery);
		List<BigInteger> rows = query.list();
	    return rows;	   
	}
	 
}
