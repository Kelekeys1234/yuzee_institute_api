package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.dto.GlobalDataDto;

@Repository
public class GlobalStudentDataDAO implements IGlobalStudentDataDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final GlobalDataDto globalDataDato) {
		Session session = sessionFactory.getCurrentSession();
		session.save(globalDataDato);
	}
	@Override
	public void deleteAll() {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from global_student_data").executeUpdate();
	}
	@Override
	public List<GlobalDataDto> getCountryWiseStudentList(String countryName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(GlobalDataDto.class, "global_data");
		crit.add(Restrictions.eq("sourceCountry", countryName));
		crit.add(Restrictions.ne("totalNumberOfStudent", 0D));
		crit.addOrder(Order.desc("totalNumberOfStudent"));
		return (List<GlobalDataDto>) crit.list();
	}
	@Override
	public long getNonZeroCountOfStudentsForCountry(String countryName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(GlobalDataDto.class, "global_data");
		crit.add(Restrictions.eq("sourceCountry", countryName));
		crit.setProjection(Projections.rowCount());
		return (long)crit.uniqueResult();
	}
	@Override
	public List<String> getDistinctMigratedCountryForStudentCountry(String countryName) {
	
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(GlobalDataDto.class,"global_data");
		crit.add(Restrictions.eq("sourceCountry", countryName));
		crit.setProjection(Projections.property("destinationCountry"));
		return (List<String>)(crit.list()!=null ? crit.list() : new ArrayList<>());
	}
}
