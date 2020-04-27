package com.seeka.app.dao;import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteService;
import com.seeka.app.repository.InstituteServiceRepository;

@Component
public class InstituteServiceDetailsDAO implements IInstituteServiceDetailsDAO {
	
	@Autowired
	private InstituteServiceRepository instituteServiceRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(InstituteService obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(InstituteService obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public InstituteService get(String id) {	
		Session session = sessionFactory.getCurrentSession();		
		InstituteService obj = session.get(InstituteService.class, id);
		return obj;
	}
	
	@Override
	public List<InstituteService> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(InstituteService.class); 
		return crit.list();
	}
	
	@Override
	public List<String> getAllServices(String instituteId) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select distinct i.service_name from institute_service i where i.institute_id = '"+instituteId+"'");
		List<String> rows = query.list();
		return rows;
	}

	@Override
	public List<InstituteService> getAllInstituteService(String instituteId) {
		return instituteServiceRepository.findByInstituteId(instituteId);
	}

	@Override
	public void saveInstituteServices(List<InstituteService> listOfInstituteService) {
		instituteServiceRepository.saveAll(listOfInstituteService);
	}

	@Override
	public void deleteServiceByIdAndInstituteId(String id, String instituteId) {
		instituteServiceRepository.deleteByIdAndInstituteId(id, instituteId);
	}
	
}
