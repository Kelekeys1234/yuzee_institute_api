package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Currency;
import com.seeka.app.dto.CourseFilterCostResponseDto;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.GlobalSearchResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.util.ConvertionUtil;
import com.seeka.app.util.GlobalSearchWordUtil;

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
	public Course get(UUID id) {	
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
	public List<CourseResponseDto> getAllCoursesByFilter(CourseSearchDto filterObj,Currency currency, UUID userCountryId) {
		Session session = sessionFactory.getCurrentSession();	
		
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName, cp.cost_range, "
				+ "cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition, cp.local_fees, cp.intl_fees,crs.remarks "
				+ "from course crs with(nolock) inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst "
				+ "with(nolock) on crs.institute_id = inst.id inner join country ctry with(nolock) on ctry.id = crs.country_id inner join "
				+ "city ci with(nolock) on ci.id = crs.city_id inner join faculty f with(nolock) on f.id = crs.faculty_id "
				+ "left join institute_service iis with(nolock) on iis.institute_id = inst.id where 1=1";
		
		boolean showIntlCost = false;
		
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
			sqlQuery += " and crs.country_id in ("+value+")";
			
			if(null != userCountryId && filterObj.getCountryIds().size() == 1 && !filterObj.getCountryIds().get(0).equals(userCountryId)) {
				showIntlCost = true;
			}
			
			//sqlQuery += " and crs.country_id in ("+StringUtils.join(filterObj.getCountryIds(), ',')+")";
		}
		
		if(null != filterObj.getCityIds() && !filterObj.getCityIds().isEmpty()) {  
			
			String value = "";
			int  i =0;
			for (UUID key : filterObj.getCityIds()) {
				if(i == 0) {
					value = "'"+key+"'";
				}else {
					value = value +","+"'"+key+"'";
				}
				i++;
			}
			sqlQuery += " and crs.city_id in ("+value+")";
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
			sqlQuery += " and f.level_id in ("+value+")";
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
			sqlQuery += " and crs.faculty_id in ("+value+")";
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
			sqlQuery += " and iis.service_id in ("+value+")";
			//sqlQuery += " and iis.service_id in ("+StringUtils.join(filterObj.getServiceIds(), ',')+")";
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
		
		if(null != filterObj.getCourseName() && !filterObj.getCourseName().isEmpty()) {
			sqlQuery += " and crs.name like '%"+filterObj.getCourseName().trim()+"%'";
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
			
			if(null != sortingObj.getCourse() && !sortingObj.getCourse().isEmpty()) {
				 if(sortingObj.getCourse().equals("ASC")) {
					 sortingQuery = " order by A.instName asc";
				 }else {
					 sortingQuery = " order by A.instName desc";
				 }
			}
			
			if(null != sortingObj.getInstitute() && !sortingObj.getInstitute().isEmpty()) {
				 if(sortingObj.getInstitute().equals("ASC")) {
					 sortingQuery = " order by A.courseName asc";
				 }else {
					 sortingQuery = " order by A.courseName desc";
				 }
			}  
			
			if(null != sortingObj.getLatestCourse() && !sortingObj.getLatestCourse().isEmpty()) {
				 if(sortingObj.getLatestCourse().equals("ASC")) {
					 sortingQuery = " order by A.courseId asc";
				 }else {
					 sortingQuery = " order by A.courseId desc";
				 }
			}   
		}else {
			sortingQuery = " order by A.intl_fees asc";
		}
		sqlQuery += sortingQuery+" OFFSET ("+filterObj.getPageNumber()+"-1)*"+filterObj.getMaxSizePerPage()+" ROWS FETCH NEXT "+filterObj.getMaxSizePerPage()+" ROWS ONLY"; 
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
		CourseResponseDto obj = null;		
		Currency oldCurrency = null;
		Double usdConv = 0.00;
		Long cost = 0l,localFees = 0l,intlFees = 0l;
		String newCurrencyCode = ""; 
		for(Object[] row : rows){
			try {
				Double costRange = Double.valueOf(String.valueOf(row[4]));
				Double localFeesD = Double.valueOf(String.valueOf(row[16]));
				Double intlFeesD = Double.valueOf(String.valueOf(row[17]));
				
				newCurrencyCode = String.valueOf(row[5]);
				if(null != currency) {
					String oldCurrencyCode = String.valueOf(row[5]);
					oldCurrency = CurrencyUtil.getCurrencyObjByCode(oldCurrencyCode);
					usdConv = 1 / oldCurrency.getConversionRate();
					newCurrencyCode = currency.getCode();
					costRange = costRange * usdConv * currency.getConversionRate();
					localFeesD = localFeesD  * usdConv * currency.getConversionRate();
					intlFeesD = intlFeesD  * usdConv * currency.getConversionRate();
				}
				cost = ConvertionUtil.roundOffToUpper(costRange);
				localFees = ConvertionUtil.roundOffToUpper(localFeesD);
				intlFees = ConvertionUtil.roundOffToUpper(intlFeesD);
				
				obj = new CourseResponseDto();	
				if(showIntlCost) {
					obj.setCost(intlFees +" "+newCurrencyCode);
				}else {
					obj.setCost(localFees +" "+newCurrencyCode);
				}
				obj.setLocalFees(localFees +" "+newCurrencyCode);
				obj.setIntlFees(intlFees +" "+newCurrencyCode);
				obj.setCourseId(UUID.fromString((String.valueOf(row[0]))));
				obj.setCourseName(String.valueOf(row[1]));
				obj.setInstituteId(UUID.fromString((String.valueOf(row[2]))));
				obj.setInstituteName(String.valueOf(row[3]));
				obj.setDuration(String.valueOf(row[6]));
				obj.setDurationTime(String.valueOf(row[7]));
				obj.setCityId(UUID.fromString((String.valueOf(row[8]))));
				obj.setCountryId(UUID.fromString((String.valueOf(row[9]))));
				obj.setLocation(String.valueOf(row[10])+", "+String.valueOf(row[11]));
				
				Integer worldRanking = 0;
				if(null != row[4]) {
					worldRanking = Double.valueOf(String.valueOf(row[12])).intValue();
				}
				obj.setWorldRanking(worldRanking.toString());
				obj.setCourseLanguage(String.valueOf(row[13]));
				obj.setLanguageShortKey(String.valueOf(row[13]));
				obj.setStars(String.valueOf(row[14])); 
				obj.setRequirements(String.valueOf(row[18]));
				obj.setTotalCount(Integer.parseInt(String.valueOf(row[19])));
				obj.setInstituteImageUrl("https://www.adelaide.edu.au/front/images/mo-orientation.jpg");
				obj.setInstituteLogoUrl("https://global.adelaide.edu.au/v/style-guide2/assets/img/logo.png");
				list.add(obj);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}   
	    return list;	   
	}
	
	 
	
	
	@Override
	public CourseFilterCostResponseDto getAllCoursesFilterCostInfo(CourseSearchDto filterObj,Currency currency, String oldCurrencyCode) {
		
		Session session = sessionFactory.getCurrentSession();	
		
		String sqlQuery = "select  min(cp.local_fees) as minLocalFees,max(cp.local_fees) as maxLocalFees,"
				+ "min(cp.intl_fees) as minIntlFees ,max(cp.intl_fees ) as maxIntlFees " + 
				"from course crs with(nolock) inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst " + 
				"with(nolock) on crs.institute_id = inst.id inner join country ctry with(nolock) on ctry.id = crs.country_id inner join " + 
				"city ci with(nolock) on ci.id = crs.city_id inner join faculty f with(nolock) on f.id = crs.faculty_id left join  " + 
				"institute_service iis with(nolock) on iis.institute_id = inst.id where 1=1 ";
		
		
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
			sqlQuery += " and crs.country_id in ("+value+")"; 
		}
		
		if(null != filterObj.getCityIds() && !filterObj.getCityIds().isEmpty()) {  
			
			String value = "";
			int  i =0;
			for (UUID key : filterObj.getCityIds()) {
				if(i == 0) {
					value = "'"+key+"'";
				}else {
					value = value +","+"'"+key+"'";
				}
				i++;
			}
			sqlQuery += " and crs.city_id in ("+value+")";
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
			sqlQuery += " and f.level_id in ("+value+")"; 
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
			sqlQuery += " and crs.faculty_id in ("+value+")"; 
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
			sqlQuery += " and iis.service_id in ("+value+")";
		}
		
		if(null != filterObj.getSearchKey() && !filterObj.getSearchKey().isEmpty()) {
			sqlQuery += " and crs.name like '%"+filterObj.getSearchKey().trim()+"%'";
		}
		  
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		CourseFilterCostResponseDto responseDto = new CourseFilterCostResponseDto();
		
		Currency oldCurrency = null;
		Double usdConv = 0.00;
		Long minLocalFeesl = 0l,maxLocalFeesl = 0l,minIntlFeesl = 0l,maxIntlFeesl = 0l;
		String newCurrencyCode = ""; 
		
		for(Object[] row : rows){
			try {
				Double minLocalFees = Double.valueOf(String.valueOf(row[0]));
				Double maxLocalFees = Double.valueOf(String.valueOf(row[1]));
				Double minIntlFees = Double.valueOf(String.valueOf(row[2]));
				Double maxIntlFees = Double.valueOf(String.valueOf(row[3]));
				
				if(!currency.getCode().toLowerCase().equals(oldCurrencyCode.toLowerCase())) {
					oldCurrency = CurrencyUtil.getCurrencyObjByCode(oldCurrencyCode);
					usdConv = 1 / oldCurrency.getConversionRate();
					newCurrencyCode = currency.getCode();
					minLocalFees = minLocalFees * usdConv * currency.getConversionRate();
					maxLocalFees = maxLocalFees  * usdConv * currency.getConversionRate();
					minIntlFees = minIntlFees  * usdConv * currency.getConversionRate();
					maxIntlFees = maxIntlFees  * usdConv * currency.getConversionRate();
				}
				
				minLocalFeesl = ConvertionUtil.roundOffToUpper(minLocalFees);
				maxLocalFeesl = ConvertionUtil.roundOffToUpper(maxLocalFees);
				minIntlFeesl = ConvertionUtil.roundOffToUpper(minIntlFees);
				maxIntlFeesl = ConvertionUtil.roundOffToUpper(maxIntlFees);
				responseDto.setMinCost(minLocalFeesl);
				responseDto.setMaxCost(maxLocalFeesl);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}   
	    return responseDto;	   
	}
	
	
	//@Override
	public List<GlobalSearchResponseDto> getAllCourseAndInstituteBySearchKeywords(String[] keywords,Integer pageNumber, Integer maxRows) {
		Session session = sessionFactory.getCurrentSession();	
		
		String courseQuery = "",insituteQuery = "";
		if(null != keywords && keywords.length > 0) {
			int  i =0;
			for (String key : keywords) {
				if(null == key || key.isEmpty()) {
					continue;
				}
				boolean toBeRemoved = GlobalSearchWordUtil.isToBeRemovedWord(key);
				if(toBeRemoved) {
					continue;
				}
				if(i == 0) {
					courseQuery = "ci.name like '%"+key+"%'";
				}else {
					courseQuery = courseQuery +" and ci.name like '%"+key+"%'";
				}
				i++;
			}
		}
		
		String sqlQuery = "select * from (select distinct f.id as id, f.name as name,'COURSE' as type "
				+ "from course crs with(nolock) left join country ctry with(nolock) on ctry.id = crs.country_id "
				+ "left join city ci with(nolock) on ci.id = crs.city_id left join faculty f on f.id =crs.faculty_id "
				+ "where ci.name like '%perth%' "
				+ "union all "
				+ "select distinct inst.id as id,inst.name as name,'INSTITUTE' as type from "
				+ "institute inst with(nolock) left join country ctry with(nolock) on ctry.id = inst.country_id "
				+ "left join city ci with(nolock) on ci.id = inst.city_id left join faculty_level fl  "
				+ "on fl.institute_id = inst.id left join faculty f on f.id =fl.faculty_id "
				+ "where ci.name like '%perth%' ) A "
				+ "order by A.name";
		 
		sqlQuery += ") A ";
		
		
		String 	sortingQuery = " order by A.cost_range asc";
	 
		sqlQuery += sortingQuery+" OFFSET ("+pageNumber+"-1)*"+maxRows+" ROWS FETCH NEXT "+maxRows+" ROWS ONLY"; 
		
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CourseResponseDto> list = new ArrayList<CourseResponseDto>();
		CourseResponseDto obj = null;			
		for(Object[] row : rows){
			obj = new CourseResponseDto();	
			obj.setCourseId(UUID.fromString((String.valueOf(row[0]))));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setInstituteId(UUID.fromString((String.valueOf(row[2]))));
			obj.setInstituteName(String.valueOf(row[3]));
			obj.setCost(String.valueOf(row[4]) +" "+String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			obj.setCityId(UUID.fromString((String.valueOf(row[8]))));
			obj.setCountryId(UUID.fromString((String.valueOf(row[9]))));
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
	    return null;	   
	}
	
	
	@Override
	public List<CourseResponseDto> getAllCoursesByInstitute(UUID instituteId, CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();	
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName,"
				+ " cp.cost_range,cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,cp.local_fees,cp.intl_fees "
				+ "from course crs with(nolock) inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst "
				+ "with(nolock) on crs.institute_id = inst.id inner join country ctry with(nolock) on ctry.id = crs.country_id inner join "
				+ "city ci with(nolock) on ci.id = crs.city_id inner join faculty f with(nolock) on f.id = crs.faculty_id "
				+ "left join institute_service iis with(nolock) on iis.institute_id = inst.id where crs.institute_id = '"+instituteId+"'";
		
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
			obj.setCourseId(UUID.fromString((String.valueOf(row[0]))));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) +" "+String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			Integer worldRanking = 0;
			if(null != row[12]) {
				worldRanking = Double.valueOf(String.valueOf(row[12])).intValue();
			}
			obj.setWorldRanking(worldRanking.toString());
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setLocalFees(String.valueOf(row[16]) +" "+String.valueOf(row[5]));
			obj.setIntlFees(String.valueOf(row[17]) +" "+String.valueOf(row[5]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[18])));
			list.add(obj);
		}   
	    return list;	   
	}
	
	
	public CourseResponseDto getCourse(UUID instituteId, CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();	
		String sqlQuery = "select A.*,count(1) over () totalRows from  (select distinct crs.id as courseId,crs.name as courseName,"
				+ "inst.id as instId,inst.name as instName,"
				+ " cp.cost_range,cp.currency,crs.duration,crs.duration_time,ci.id as cityId,ctry.id as countryId,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.course_lang,crs.stars,crs.recognition,cp.local_fees,cp.intl_fees "
				+ "from course crs with(nolock) inner join course_pricing cp with(nolock) on cp.course_id = crs.id inner join institute inst "
				+ "with(nolock) on crs.institute_id = inst.id inner join country ctry with(nolock) on ctry.id = crs.country_id inner join "
				+ "city ci with(nolock) on ci.id = crs.city_id inner join faculty f with(nolock) on f.id = crs.faculty_id "
				+ "left join institute_service iis with(nolock) on iis.institute_id = inst.id where crs.institute_id = "+instituteId;
		
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
			obj.setCourseId(UUID.fromString((String.valueOf(row[0]))));
			obj.setCourseName(String.valueOf(row[1]));
			obj.setCost(String.valueOf(row[4]) +" "+String.valueOf(row[5]));
			obj.setDuration(String.valueOf(row[6]));
			obj.setDurationTime(String.valueOf(row[7]));
			obj.setWorldRanking(String.valueOf(row[12]));
			obj.setCourseLanguage(String.valueOf(row[13]));
			obj.setLanguageShortKey(String.valueOf(row[13]));
			obj.setStars(String.valueOf(row[14]));
			obj.setLocalFees(String.valueOf(row[16]) +" "+String.valueOf(row[5]));
			obj.setIntlFees(String.valueOf(row[17]) +" "+String.valueOf(row[5]));
			obj.setTotalCount(Integer.parseInt(String.valueOf(row[18])));
			list.add(obj);
		}   
	    return obj;	   
	}
	
	@Override
	public Map<String, Object> getCourse(UUID courseid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select crs.id as crs_id,crs.stars as crs_stars,crs.name as crs_name,crs.course_lang as crs_cour_lang,crs.description as crs_desc,crs.duration as crs_dur,crs.duration_time as crs_du_time,crs.world_ranking as crs_word_ranking, "
						+ "ins.id as ins_id,ins.name as ins_name,crs.world_ranking as ins_wrld_rank,ins.int_emails as ins_int_emails,ins.int_ph_num as ins_int_ph_num,ins.longitude as ins_longitude,ins.latitude as ins_latitude,ins.t_num_of_stu as ins_t_num_of_stu,ins.website as ins_website,ins.address as ins_address, "
						+"id.about_us_info as ab_us_info,id.closing_hour as close_hour,id.opening_hour as open_hour, "
						+"cp.currency,cp.cost_range,cp.intl_fees,cp.local_fees, "
						+"cty.name as city_name,cntry.name as country_name,fty.name as faulty_name,le.id as le_id,le.name as le_name,cty.id as cityid,cntry.id as countryid,cntry.visa as visa from course crs with(nolock) "
						+ "inner join institute ins with(nolock) on ins.id = crs.institute_id inner join country cntry with(nolock) on cntry.id = crs.country_id "
						+"inner join city cty with(nolock) on cty.id = crs.city_id inner join faculty fty with(nolock) on fty.id = crs.faculty_id inner join institute_details id with(nolock) on id.institute_id = crs.institute_id "
				        +"inner join level le with(nolock) on fty.level_id = le.id inner join course_pricing cp with(nolock) on cp.course_id = crs.id where "
				        + "crs.id = '"+courseid+"'");
		
		List<Object[]> rows = query.list();
		InstituteResponseDto instituteObj = null;
		CourseDto courseObj = null;	
		Map<String, Object> map = new HashMap<>();
		for(Object[] row : rows){
			courseObj = new CourseDto();	
			courseObj.setCourseId(UUID.fromString((String.valueOf(row[0]))));
			courseObj.setStars(String.valueOf(row[1]));
			courseObj.setCourseName(String.valueOf(row[2]));
			courseObj.setCourseLanguage(String.valueOf(row[3]));
			courseObj.setDescription(String.valueOf(row[4]));
			courseObj.setDuration(String.valueOf(row[5]));
			courseObj.setDurationTime(String.valueOf(row[6]));
			courseObj.setWorldRanking(String.valueOf(row[7]));
			courseObj.setIntlFees(String.valueOf(row[23])+" "+ String.valueOf(row[21]));
			courseObj.setLocalFees(String.valueOf(row[24])+" "+ String.valueOf(row[21]));
			courseObj.setCost(String.valueOf(row[22])+" "+ String.valueOf(row[21]));
			courseObj.setFacultyName(String.valueOf(row[27]));
			courseObj.setLevelId(UUID.fromString((String.valueOf(row[28]))));
			courseObj.setLevelName(String.valueOf(row[29]));
			
			instituteObj = new InstituteResponseDto();	
			instituteObj.setInstituteId(UUID.fromString((String.valueOf(row[8]))));	
			instituteObj.setStars(String.valueOf(row[1]));
			instituteObj.setInstituteName(String.valueOf(row[9]));
			instituteObj.setWorldRanking(String.valueOf(row[7]));
			instituteObj.setInterEmail(String.valueOf(row[11]));
			instituteObj.setInterPhoneNumber(String.valueOf(row[12]));
			instituteObj.setLongitude(String.valueOf(row[13]));
			instituteObj.setLatitute(String.valueOf(row[14]));
			instituteObj.setTotalNoOfStudents(Integer.parseInt(String.valueOf(row[15])));
			instituteObj.setWebsite(String.valueOf(row[16]));
			instituteObj.setAddress(String.valueOf(row[17]));
			instituteObj.setVisaRequirement(String.valueOf(row[32]));
			instituteObj.setAboutUs(String.valueOf(row[18]));
			instituteObj.setClosingHour(String.valueOf(row[19]));
			instituteObj.setOpeningHour(String.valueOf(row[20]));
			instituteObj.setLocation(String.valueOf(row[25])+","+String.valueOf(row[26]));
			instituteObj.setCityId(UUID.fromString((String.valueOf(row[30]))));
			instituteObj.setCountryId(UUID.fromString((String.valueOf(row[31]))));
			
			map.put("courseObj", courseObj);
			map.put("instituteObj", instituteObj);
		}
	    return map;	
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		
		list.add(14);
		list.add(450);
		list.add(780);
		System.out.println(list);
	}


	 
}
