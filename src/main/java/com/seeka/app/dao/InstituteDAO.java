package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.InstituteSearchResultDto;

@Repository
public class InstituteDAO implements IInstituteDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Institute obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Institute obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Institute get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Institute obj = session.get(Institute.class, id);
		return obj;
	}
	
	@Override
	public List<Institute> getAllInstituteByCountry(Integer countryId) {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Institute.class);
		crit.add(Restrictions.eq("countryObj.id",countryId));
		return crit.list();
	}
	
	@Override
	public List<Institute> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Institute.class); 
		return crit.list();
	}
	
	/*@Override
	public Institute getUserByEmail(String email) {	
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("emailId",email));
		List<User> users = crit.list();
		return users !=null && !users.isEmpty()?users.get(0):null;
	}*/
	
	/*private void retrieveEmployee() {
		
	    try{
		    String sqlQuery="select e from Employee e inner join e.addList";
		    Session session=sessionFactory.getCurrentSession();
		    Query query=session.createQuery(sqlQuery);
		    List<Institute> list=query.list();
		    list.stream().forEach((p)->{System.out.println(p.getName());});     
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	}*/
	
	
	@Override
	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey) {
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createSQLQuery(
				"select i.id,i.name,c.name as countryName,ci.name as cityName from institute i with(nolock) inner join country c with(nolock) on c.id = i.country_id "
				+ "inner join city ci with(nolock) on ci.id = i.city_id  where i.name like '%"+searchKey+"%'");
		List<Object[]> rows = query.list();
		List<InstituteSearchResultDto> instituteList = new ArrayList<InstituteSearchResultDto>();
		InstituteSearchResultDto obj = null;
		for(Object[] row : rows){
			obj = new InstituteSearchResultDto();
			obj.setInstituteId(Integer.parseInt(row[0].toString()));
			obj.setInstituteName(row[1].toString());
			obj.setLocation(row[2].toString()+", "+row[3].toString());
			instituteList.add(obj);
		}
		return instituteList;
	} 
	

}
