package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;

@Repository
public class CourseDAO implements ICourseDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public void save(Course obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.save(obj);	   					
	}
	
	@Override
	public void update(Course obj) {	
		Session session = sessionFactory.getCurrentSession();		
		session.update(obj);	   					
	}
	
	@Override
	public Course get(Integer id) {	
		Session session = sessionFactory.getCurrentSession();		
		Course obj = session.get(Course.class, id);
		return obj;
	}
	
	@Override
	public List<Course> getAll() {
		Session session = sessionFactory.getCurrentSession();		
		Criteria crit = session.createCriteria(Course.class); 
		return crit.list();
	} 

	@Override
	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();	
		
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName, cp.cost_range, "
				+ "cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition "
				+ "from course crs with(nolock) inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst "
				+ "with(nolock) on crs.institute_id = inst.id inner join country ctry with(nolock) on ctry.id = crs.country_id inner join "
				+ "city ci with(nolock) on ci.id = crs.city_id inner join faculty f with(nolock) on f.id = crs.faculty_id where 1=1";
		
		
		if(null != filterObj.getCountryIds() && !filterObj.getCountryIds().isEmpty()) {         
			sqlQuery += " and crs.country_id in ("+StringUtils.join(filterObj.getCountryIds(), ',')+")";
		}
		
		if(null != filterObj.getLevelIds() && !filterObj.getLevelIds().isEmpty()) {
			sqlQuery += " and f.level_id in ("+StringUtils.join(filterObj.getLevelIds(), ',')+")";
		}
		
		if(null != filterObj.getFacultyIds() && !filterObj.getFacultyIds().isEmpty()) {
			sqlQuery += " and crs.faculty_id in ("+StringUtils.join(filterObj.getFacultyIds(), ',')+")";
		}
		
		if(null != filterObj.getCourseKeys() && !filterObj.getCourseKeys().isEmpty()) {
			String value = "";
			int  i =0;
			for (String key : filterObj.getCourseKeys()) {
				if(null == key || key.isEmpty()) {
					continue;
				}
				if(i == 0) {
					value = "'"+key.trim()+"'";
				}else {
					value = value +","+"'"+key.trim()+"'";
				}
				i++;
			}
			sqlQuery += " and crs.name in ("+value+")";
		}
		
		if(null != filterObj.getServiceIds() && !filterObj.getServiceIds().isEmpty()) {
			//sqlQuery += " and crs.faculty_id in ("+filterObj.getCountryIds()+")";
		}
		
		if(null != filterObj.getMinCost() && filterObj.getMinCost() >= 0) {
			sqlQuery += " and cp.cost_range >= "+filterObj.getMinCost();
		}
		
		if(null != filterObj.getMaxCost() && filterObj.getMaxCost() >= 0) {
			sqlQuery += " and cp.cost_range <= "+filterObj.getMaxCost();
		}
		
		if(null != filterObj.getMinDuration() && filterObj.getMinDuration() >= 0) {
			sqlQuery += " and cast(crs.duration as DECIMAL(9,2)) >= "+filterObj.getMinDuration();
		}
		
		if(null != filterObj.getMaxDuration() && filterObj.getMaxDuration() >= 0) {
			sqlQuery += " and cast(crs.duration as DECIMAL(9,2)) <= "+filterObj.getMaxDuration();
		}
		
		if(null != filterObj.getSearchKey() && !filterObj.getSearchKey().isEmpty()) {
			sqlQuery += " and crs.name like '%"+filterObj.getSearchKey().trim()+"%'";
		}
		
		sqlQuery += ") A ";
		
		
		String sortingQuery = "";
		if(null != filterObj.getSortingObj()) {
			CourseSearchFilterDto sortingObj = filterObj.getSortingObj();
			if(null != sortingObj.getPrice() && !sortingObj.getPrice().isEmpty()) {
				 if(sortingObj.getPrice().equals("ASC")) {
					 sortingQuery = " order by A.cost_range asc";
				 }else {
					 sortingQuery = " order by A.cost_range desc";
				 }
			}
			
			if(null != sortingObj.getLocation() && !sortingObj.getLocation().isEmpty()) {
				 if(sortingObj.getLocation().equals("ASC")) {
					 sortingQuery = " order by A.countryName, A.cityName asc";
				 }else {
					 sortingQuery = " order by A.countryName, A.cityName desc";
				 }
			}
			
			if(null != sortingObj.getDuration() && !sortingObj.getDuration().isEmpty()) {
				 if(sortingObj.getDuration().equals("ASC")) {
					 sortingQuery = " order by A.duration asc";
				 }else {
					 sortingQuery = " order by A.duration desc";
				 }
			}
			
			if(null != sortingObj.getRecognition() && !sortingObj.getRecognition().isEmpty()) {
				 if(sortingObj.getRecognition().equals("ASC")) {
					 sortingQuery = " order by A.recognition asc";
				 }else {
					 sortingQuery = " order by A.recognition desc";
				 }
			}
		}else {
			sortingQuery = " order by A.cost_range asc";
		}
		sqlQuery += sortingQuery+" OFFSET ("+filterObj.getPageNumber()+"-1)*"+filterObj.getMaxSizePerPage()+" ROWS FETCH NEXT "+filterObj.getMaxSizePerPage()+" ROWS ONLY"; 
		
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
		CourseResponseDto obj = null;			
		for(Object[] row : rows){
			obj = new CourseResponseDto();	
			obj.setCourseId(Integer.parseInt(String.valueOf(row[0])));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setInstituteId(Integer.parseInt(String.valueOf(row[2])));
			obj.setInstituteName(String.valueOf(row[3]));
			obj.setCost(String.valueOf(row[4]) +" "+String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			obj.setCityId(Integer.parseInt(String.valueOf(row[8])));
			obj.setCountryId(Integer.parseInt(String.valueOf(row[9])));
			obj.setLocation(String.valueOf(row[10])+", "+String.valueOf(row[11]));
			obj.setWorldRanking(String.valueOf(row[12]));
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[16])));
			obj.setInstituteImageUrl("https://www.adelaide.edu.au/front/images/mo-orientation.jpg");
			obj.setInstituteLogoUrl("https://global.adelaide.edu.au/v/style-guide2/assets/img/logo.png");
			list.add(obj);
		}   
	    return list;	   
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		
		list.add(14);
		list.add(450);
		list.add(780);
		System.out.println(list);
	}
	 
}
