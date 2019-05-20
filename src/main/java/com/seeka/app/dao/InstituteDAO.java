package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
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
	public Institute get(UUID id) {	
		Session session = sessionFactory.getCurrentSession();		
		Institute obj = session.get(Institute.class, id);
		return obj;
	}
	
	@Override
	public List<Institute> getAllInstituteByCountry(UUID countryId) {
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
			obj.setInstituteId(UUID.fromString((row[0].toString())));
			obj.setInstituteName(row[1].toString());
			obj.setLocation(row[2].toString()+", "+row[3].toString());
			instituteList.add(obj);
		}
		return instituteList;
	}
	
	@Override
	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();	
		
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.stars,crs.totalCourse "
				+ "from institute inst with(nolock) inner join country ctry with(nolock) "
				+ "on ctry.id = inst.country_id inner join city ci with(nolock) on ci.id = inst.city_id "
				+ "inner join faculty_level f WITH(NOLOCK) on f.institute_id = inst.id "
				+ "inner join institute_level l WITH(NOLOCK) on l.institute_id = inst.id "
				+ "CROSS APPLY ( select count(c.id) as totalCourse, MIN(c.world_ranking) as world_ranking, MIN(c.stars) as stars from "
				+ "course c WITH(NOLOCK) where "
				+ "c.institute_id = inst.id group by c.institute_id ) crs "
				+ "where 1=1 ";
		
		
		if(null != filterObj.getCountryIds() && !filterObj.getCountryIds().isEmpty()) {  
			String value = "";
			int  i =0;
			for (UUID key : filterObj.getCountryIds()) {
				if(i == 0) {
					value = "'"+key+"'";
				}else {
					value = value +","+"'"+key+"'";
				}
				i++;
			}
			sqlQuery += " and inst.country_id in ("+value+")";
			//sqlQuery += " and inst.country_id in ("+StringUtils.join(filterObj.getCountryIds(), ',')+")";
		}
		
		if(null != filterObj.getLevelIds() && !filterObj.getLevelIds().isEmpty()) {
			String value = "";
			int  i =0;
			for (UUID key : filterObj.getLevelIds()) {
				if(i == 0) {
					value = "'"+key+"'";
				}else {
					value = value +","+"'"+key+"'";
				}
				i++;
			}
			sqlQuery += " and l.level_id in ("+value+")";
			//sqlQuery += " and l.level_id in ("+StringUtils.join(filterObj.getLevelIds(), ',')+")";
		}
		
		if(null != filterObj.getFacultyIds() && !filterObj.getFacultyIds().isEmpty()) {
			String value = "";
			int  i =0;
			for (UUID key : filterObj.getFacultyIds()) {
				if(i == 0) {
					value = "'"+key+"'";
				}else {
					value = value +","+"'"+key+"'";
				}
				i++;
			}
			sqlQuery += " and f.faculty_id in ("+value+")";
			//sqlQuery += " and f.faculty_id in ("+StringUtils.join(filterObj.getFacultyIds(), ',')+")";
		}
		  
		if(null != filterObj.getSearchKey() && !filterObj.getSearchKey().isEmpty()) {
			sqlQuery += " and inst.name like '%"+filterObj.getSearchKey().trim()+"%'";
		}
		
		sqlQuery += ") A ";
		
		String sortingQuery = "";
		if(null != filterObj.getSortingObj()) {
			CourseSearchFilterDto sortingObj = filterObj.getSortingObj();
			if(null != sortingObj.getWorldRanking() && !sortingObj.getWorldRanking().isEmpty()) {
				 if(sortingObj.getWorldRanking().equals("ASC")) {
					 sortingQuery = " order by A.world_ranking asc";
				 }else {
					 sortingQuery = " order by A.world_ranking desc";
				 }
			}  
		}else {
			sortingQuery = " order by A.instName asc";
		}
		
		
		sqlQuery += sortingQuery+" OFFSET ("+filterObj.getPageNumber()+"-1)*"+filterObj.getMaxSizePerPage()+" ROWS FETCH NEXT "+filterObj.getMaxSizePerPage()+" ROWS ONLY"; 
		
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> list = new ArrayList<InstituteResponseDto>();
		InstituteResponseDto obj = null;			
		for(Object[] row : rows){
			obj = new InstituteResponseDto();	
			obj.setInstituteId(UUID.fromString((String.valueOf(row[0]))));
			obj.setInstituteName(String.valueOf(row[1]));
			obj.setLocation(String.valueOf(row[2])+", "+String.valueOf(row[3]));
			obj.setCityName(String.valueOf(row[2]));
			obj.setCountryName(String.valueOf(row[3]));
			Integer worldRanking = 0;
			if(null != row[4]) {
				worldRanking = Double.valueOf(String.valueOf(row[4])).intValue();
			}
			obj.setWorldRanking(worldRanking.toString());
			obj.setStars(String.valueOf(row[5]));
			obj.setTotalCourses(Integer.parseInt(String.valueOf(row[6])));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[7])));
			list.add(obj);
		}   
	    return list;	   
	}
	
	
	@Override
	public InstituteResponseDto getInstituteByID(UUID instituteId) {
		
		Session session = sessionFactory.getCurrentSession();	
		
		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.stars,crs.totalCourse "
				+ "from institute inst with(nolock) inner join country ctry with(nolock) "
				+ "on ctry.id = inst.country_id inner join city ci with(nolock) on ci.id = inst.city_id "
				+ "CROSS APPLY ( select count(c.id) as totalCourse, MIN(c.world_ranking) as world_ranking, MIN(c.stars) as stars from "
				+ "course c WITH(NOLOCK) where "
				+ "c.institute_id = inst.id group by c.institute_id ) crs "
				+ "where 1=1 and inst.id ='"+instituteId+"'";
		 
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> list = new ArrayList<InstituteResponseDto>();
		InstituteResponseDto obj = null;			
		for(Object[] row : rows){
			obj = new InstituteResponseDto();	
			obj.setInstituteId(UUID.fromString((String.valueOf(row[0]))));
			obj.setInstituteName(String.valueOf(row[1]));
			obj.setLocation(String.valueOf(row[2])+", "+String.valueOf(row[3]));
			obj.setWorldRanking(String.valueOf(row[4]));
			obj.setStars(String.valueOf(row[5]));
			obj.setTotalCourses(Integer.parseInt(String.valueOf(row[6])));
		}   
	    return obj;	   
	}

    @Override
    public List<InstituteResponseDto> getInstitudeByCityId(UUID cityId) {
        Session session = sessionFactory.getCurrentSession();

        String sqlQuery = "select distinct inst.id as instId,inst.name as instName,inst.institute_type_id as institudeTypeId "
                        + "from institute_level instLevel with(nolock) inner join institute inst with(nolock) " + "on inst.id = instLevel.institute_id "
                        + "where instLevel.city_id ='" + cityId + "' ORDER BY inst.name";

        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.list();
        List<InstituteResponseDto> instituteResponseDtos = new ArrayList<InstituteResponseDto>();
        InstituteResponseDto instituteResponseDto = null;
        for (Object[] row : rows) {
            instituteResponseDto = new InstituteResponseDto();
            instituteResponseDto.setInstituteId(UUID.fromString((String.valueOf(row[0]))));
            instituteResponseDto.setInstituteName(String.valueOf(row[1]));
            instituteResponseDtos.add(instituteResponseDto);
        }
        return instituteResponseDtos;
    }
}
