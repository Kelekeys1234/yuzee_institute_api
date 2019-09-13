package com.seeka.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Top10Course;
import com.seeka.app.dao.Top10CourseDAO;

@Service
@Transactional(rollbackFor = Throwable.class)
public class Top10CourseService implements ITop10CourseService {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private Top10CourseDAO top10CourseDao;
	
	@Override
	public void saveTop10Courses(Top10Course top10Course) {
		// TODO Auto-generated method stub
		top10Course.setUpdatedBy("API");
		top10Course.setCreatedBy("API");
		top10Course.setCreatedOn(new java.util.Date(System.currentTimeMillis()));
		top10Course.setUpdatedOn(new java.util.Date(System.currentTimeMillis()));
		top10CourseDao.save(top10Course);
	}

	public void deleteAllTop10Courses() {
		// TODO Auto-generated method stub
		top10CourseDao.deleteAll();
	}

	@Override
	public List<String> getAllDistinctFaculty() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
    	
    	Criteria crit = session.createCriteria(Top10Course.class, "top10Course");
    	ProjectionList projList = Projections.projectionList();
    	projList.add(Projections.groupProperty("top10Course.faculty"));
		crit.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		crit.setProjection(projList);
		return (List<String>)crit.list();
	}
}
