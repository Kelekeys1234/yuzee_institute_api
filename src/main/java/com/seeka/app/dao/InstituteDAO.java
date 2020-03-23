package com.seeka.app.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteService;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Service;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Repository
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class InstituteDAO implements IInstituteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final Institute obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public void update(final Institute obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}

	@Override
	public void delete(final Institute obj) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("DELETE FROM institute_campus WHERE institute_id =" + obj.getId());
		query.executeUpdate();
	}

	@Override
	public Institute get(final String instituteName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Institute.class, "institute");
		criteria.add(Restrictions.eq("institute.id", instituteName));
		return (Institute) criteria.uniqueResult();
	}

	@Override
	public List<String> getTopInstituteByCountry(final String countryId) {
		Session session = sessionFactory.getCurrentSession();
		List<String> idList = session.createNativeQuery("select id from institute where country_name = ? order by world_ranking").setParameter(1, countryId)
				.getResultList();
		return idList;
	}

	@Override
	public List<Institute> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class);
		return crit.list();
	}

	@Override
	public List<InstituteSearchResultDto> getInstitueBySearchKey(final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select i.id,i.name,c.name as countryName,ci.name as cityName from institute i  inner join country c  on c.name = i.country_name "
						+ "inner join city ci  on ci.id = i.city_id  where i.name like '%" + searchKey + "%'");
		List<Object[]> rows = query.list();
		List<InstituteSearchResultDto> instituteList = new ArrayList<>();
		InstituteSearchResultDto obj = null;
		for (Object[] row : rows) {
			obj = new InstituteSearchResultDto();
			obj.setInstituteId(row[0].toString());
			obj.setInstituteName(row[1].toString());
			obj.setLocation(row[2].toString() + ", " + row[3].toString());
			instituteList.add(obj);
		}
		return instituteList;
	}

	@Override
	public int getCountOfInstitute(final CourseSearchDto courseSearchDto, final String searchKeyword, final String cityId, final String instituteId,
			final Boolean isActive, final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String campusType) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select count(distinct inst.id) from institute inst  inner join country ctry  on ctry.name = inst.country_name inner join city ci  on ci.id = inst.city_id "
				+ "left join faculty_level f on f.institute_id = inst.id left join institute_level l on l.institute_id = inst.id "
				+ "left join course c  on c.institute_id=inst.id inner join institute_type it on inst.institute_type_id=it.id where 1=1 ";

		if (null != courseSearchDto.getCountryIds() && !courseSearchDto.getCountryIds().isEmpty()) {
			sqlQuery += " and inst.country_name in ('" + courseSearchDto.getCountryIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and l.level_id in ('" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and f.faculty_id in ('" + courseSearchDto.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getSearchKey() && !courseSearchDto.getSearchKey().isEmpty()) {
			sqlQuery += " and inst.name like '%" + courseSearchDto.getSearchKey().trim() + "%'";
		}
		if (null != cityId) {
			sqlQuery += " and ci.id ='" + cityId + "'";
		}
		if (null != isActive) {
			sqlQuery += " and inst.is_active =" + isActive;
		}
		if (null != campusType) {
			sqlQuery += " and inst.campus_type ='" + campusType + "'";
		}
		if (null != updatedOn) {
			sqlQuery += " and Date(inst.updated_on) ='" + new java.sql.Date(updatedOn.getTime()).toLocalDate() + "'";
		}
		if (null != fromWorldRanking && null != toWorldRanking) {
			sqlQuery += " and inst.world_ranking between " + fromWorldRanking + " and " + toWorldRanking;
		}

		if (null != searchKeyword && !searchKeyword.isEmpty()) {
			sqlQuery += " and ( inst.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or ctry.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or ci.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or it.name like '%" + searchKeyword.trim() + "%' )";
		}
		Query query1 = session.createSQLQuery(sqlQuery);
		return ((Number) query1.uniqueResult()).intValue();
	}

	@Override
	public List<InstituteResponseDto> getAllInstitutesByFilter(final CourseSearchDto courseSearchDto, final String sortByField, String sortByType,
			final String searchKeyword, final Integer startIndex, final String cityId, final String instituteId, final Boolean isActive,
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String campusType) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,count(c.id) as courses, inst.world_ranking as world_ranking, MIN(c.stars) as stars "
				+ " ,ctry.id as countryId, ci.id as cityId,inst.updated_on as updatedOn, it.name as instituteType, inst.campus_type,"
				+ " inst.is_active, inst.domestic_ranking, inst.latitute,inst.longitute "
				+ " from institute inst  inner join country ctry  on ctry.name = inst.country_name inner join city ci  on ci.id = inst.city_id "
				+ "left join faculty_level f on f.institute_id = inst.id left join institute_level l on l.institute_id = inst.id "
				+ "left join course c  on c.institute_id=inst.id inner join institute_type it on inst.institute_type_id=it.id where 1=1 ";

		if (null != courseSearchDto.getCountryIds() && !courseSearchDto.getCountryIds().isEmpty()) {
			sqlQuery += " and inst.country_name in ('" + courseSearchDto.getCountryIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getLevelIds() && !courseSearchDto.getLevelIds().isEmpty()) {
			sqlQuery += " and l.level_id in ('" + courseSearchDto.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getFacultyIds() && !courseSearchDto.getFacultyIds().isEmpty()) {
			sqlQuery += " and f.faculty_id in ('" + courseSearchDto.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + "')";
		}

		if (null != courseSearchDto.getSearchKey() && !courseSearchDto.getSearchKey().isEmpty()) {
			sqlQuery += " and inst.name like '%" + courseSearchDto.getSearchKey().trim() + "%'";
		}
		if (null != cityId) {
			sqlQuery += " and ci.id ='" + cityId +"'";
		}
		if (null != isActive) {
			sqlQuery += " and inst.is_active =" + isActive;
		}
		if (null != campusType) {
			sqlQuery += " and inst.campus_type ='" + campusType + "'";
		}
		if (null != updatedOn) {
			sqlQuery += " and Date(inst.updated_on) ='" + new java.sql.Date(updatedOn.getTime()).toLocalDate() + "'";
		}
		if (null != fromWorldRanking && null != toWorldRanking) {
			sqlQuery += " and inst.world_ranking between " + fromWorldRanking + " and " + toWorldRanking;
		}

		if (null != searchKeyword && !searchKeyword.isEmpty()) {
			sqlQuery += " and ( inst.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or ctry.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or ci.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or it.name like '%" + searchKeyword.trim() + "%' )";
		}
		sqlQuery += " group by inst.id ";

		String sortingQuery = "";
		if (sortByType == null) {
			sortByType = "DESC";
		}
		if (null != sortByField) {
			if (sortByField.equalsIgnoreCase("name")) {
				sortingQuery = " order by inst.name " + sortByType.toLowerCase();
			} else if (sortByField.equalsIgnoreCase("countryName")) {
				sortingQuery = " order by ctry.name " + sortByType.toLowerCase();
			} else if (sortByField.equalsIgnoreCase("cityName")) {
				sortingQuery = " order by ci.name " + sortByType.toLowerCase();
			} else if (sortByField.equalsIgnoreCase("instituteType")) {
				sortingQuery = " order by it.name " + sortByType.toLowerCase();
			} else {
				sortingQuery = " order by inst.id " + sortByType.toLowerCase();
			}

		} else {
			sortingQuery = " order by inst.id " + sortByType.toLowerCase();
		}

		if (startIndex != null && courseSearchDto.getMaxSizePerPage() != null) {
			sqlQuery += sortingQuery + " LIMIT " + startIndex + " ," + courseSearchDto.getMaxSizePerPage();
		}

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> list = new ArrayList<>();
		InstituteResponseDto instituteResponseDto = null;
		for (Object[] row : rows) {
			instituteResponseDto = new InstituteResponseDto();
			instituteResponseDto.setId(String.valueOf(row[0]));
			instituteResponseDto.setName(String.valueOf(row[1]));
			instituteResponseDto.setLocation(String.valueOf(row[2]) + ", " + String.valueOf(row[3]));
			instituteResponseDto.setCityName(String.valueOf(row[2]));
			instituteResponseDto.setCountryName(String.valueOf(row[3]));
			instituteResponseDto.setTotalCourses(Integer.parseInt(String.valueOf(row[4])));
			Integer worldRanking = 0;
			if (null != row[5]) {
				worldRanking = Integer.parseInt(String.valueOf(row[5]));
			}
			instituteResponseDto.setWorldRanking(worldRanking);
			if (null != row[6]) {
				instituteResponseDto.setStars(Integer.parseInt(String.valueOf(row[6])));
			} else {
				instituteResponseDto.setStars(0);
			}

			instituteResponseDto.setCountryId(String.valueOf(row[7]));
			instituteResponseDto.setCityId(String.valueOf(row[8]));
			instituteResponseDto.setUpdatedOn((Date) row[9]);
			instituteResponseDto.setInstituteType(String.valueOf(row[10]));
			instituteResponseDto.setCampusType(String.valueOf(row[11]));
			instituteResponseDto.setIsActive(Boolean.valueOf(String.valueOf(row[12])));
			if (row[13] != null) {
				instituteResponseDto.setDomesticRanking(Integer.valueOf(String.valueOf(row[13])));
			}
			if (row[14] != null) {
				instituteResponseDto.setLatitute(Double.valueOf(row[14].toString()));
			}
			if (row[15] != null) {
				instituteResponseDto.setLongitude(Double.valueOf(row[15].toString()));
			}
			list.add(instituteResponseDto);
		}
		return list;
	}

	@Override
	public InstituteResponseDto getInstituteByID(final String instituteId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.stars,crs.totalCourse from institute inst  inner join country ctry  "
				+ "on ctry.name = inst.country_name inner join city ci  on ci.id = inst.city_id "
				+ "CROSS APPLY ( select count(c.id) as totalCourse, MIN(c.world_ranking) as world_ranking, MIN(c.stars) as stars from " + "course c where "
				+ "c.institute_id = inst.id group by c.institute_id ) crs where 1=1 and inst.id ='" + instituteId + "'";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		InstituteResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new InstituteResponseDto();
			obj.setId(String.valueOf(row[0]));
			obj.setName(String.valueOf(row[1]));
			obj.setLocation(String.valueOf(row[2]) + ", " + String.valueOf(row[3]));
			if (row[4] != null) {
				obj.setWorldRanking(Integer.valueOf(String.valueOf(row[4])));
			}
			obj.setTotalCourses(Integer.parseInt(String.valueOf(row[6])));
		}
		return obj;
	}

	@Override
	public List<Institute> getAllInstituteByID(final Collection<String> instituteId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class, "institute");
		crit.add(Restrictions.in("id", instituteId));
		return crit.list();
	}

	@Override
	public List<InstituteResponseDto> getInstitudeByCityId(final String cityId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,inst.institute_type_id as institudeTypeId "
				+ "from institute_level instLevel  inner join institute inst " + "on inst.id = instLevel.institute_id " + "where instLevel.city_id ='" + cityId
				+ "' ORDER BY inst.name";

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> instituteResponseDtos = new ArrayList<>();
		InstituteResponseDto instituteResponseDto = null;
		for (Object[] row : rows) {
			instituteResponseDto = new InstituteResponseDto();
			instituteResponseDto.setId(String.valueOf(row[0]));
			instituteResponseDto.setName(String.valueOf(row[1]));
			instituteResponseDtos.add(instituteResponseDto);
		}
		return instituteResponseDtos;
	}

	@Override
	public List<InstituteResponseDto> getInstituteByListOfCityId(final String citisId) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,inst.institute_type_id as institudeTypeId "
				+ "from institute_level instLevel  inner join institute inst  " + "on inst.id = instLevel.institute_id " + "where instLevel.city_id in ("
				+ citisId + ")  ORDER BY inst.name";

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> instituteResponseDtos = new ArrayList<>();
		InstituteResponseDto instituteResponseDto = null;
		for (Object[] row : rows) {
			instituteResponseDto = new InstituteResponseDto();
			instituteResponseDto.setId(String.valueOf(row[0]));
			instituteResponseDto.setName(String.valueOf(row[1]));
			instituteResponseDtos.add(instituteResponseDto);
		}
		InstituteResponseDto instituteResponseDto1 = new InstituteResponseDto();
		instituteResponseDto1.setId(("111111"));
		instituteResponseDto1.setName("All");
		instituteResponseDtos.add(instituteResponseDto1);
		return instituteResponseDtos;
	}

	@Override
	public List<Institute> searchInstitute(final String sqlQuery) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = new ArrayList<>();
		Institute obj = null;
		for (Object[] row : rows) {
			obj = new Institute();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountry(getCountry(row[2].toString(), session));
			}
			if (row[3] != null) {
				obj.setCity(getCity(row[3].toString(), session));
			}
			if (row[4] != null) {
				obj.setInstituteType(getInstituteType(row[4].toString(), session));
			}
			instituteList.add(obj);
		}
		return instituteList;
	}

	private InstituteType getInstituteType(final String id, final Session session) {
		return session.get(InstituteType.class, id);
	}

	private City getCity(final String id, final Session session) {
		return session.get(City.class, id);
	}

	private Country getCountry(final String id, final Session session) {
		return session.get(Country.class, id);
	}

	@Override
	public int findTotalCount() {
		int status = 1;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from institute sa where sa.is_active = " + status + " and sa.deleted_on IS NULL";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public List<Institute> getAll(final Integer pageNumber, final Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_id, inst.institute_type_id, inst.description, inst.updated_on , inst.campus_type FROM institute as inst where inst.is_active = 1 and inst.deleted_on IS NULL ORDER BY inst.created_on DESC";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = getInstituteData(rows, session);
		return instituteList;
	}

	private List<Institute> getInstituteData(final List<Object[]> rows, final Session session) {
		List<Institute> instituteList = new ArrayList<>();
		Institute obj = null;
		for (Object[] row : rows) {
			obj = new Institute();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setName(row[1].toString());
			}
			if (row[2] != null) {
				obj.setCountry(getCountry(row[2].toString(), session));
			}
			if (row[3] != null) {
				obj.setCity(getCity(row[3].toString(), session));
			}
			if (row[4] != null) {
				obj.setInstituteType(getInstituteType(row[4].toString(), session));
			}
			if (row[5] != null) {
				obj.setDescription(row[5].toString());
			}
			if (row[6] != null) {
				Date createdDate = (Date) row[6];
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);
			}
			if (row[7] != null) {
				obj.setCampusType(row[7].toString());
			}
			instituteList.add(obj);
		}
		return instituteList;
	}

	@Override
	public List<Service> getAllServices() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select inst.id, inst.name , inst.description FROM service as inst where inst.is_active = 1 ORDER BY inst.created_on DESC";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Service> services = new ArrayList<>();
		Service obj = null;
		for (Object[] row : rows) {
			obj = new Service();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setDescription(row[2].toString());
			}
			obj.setIcon(CDNServerUtil.getServiceIconUrl(row[1].toString()));
			services.add(obj);
		}
		return services;
	}

	@Override
	public List<Institute> instituteFilter(final int pageNumber, final Integer pageSize, final InstituteFilterDto instituteFilterDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = getFilterInstituteSqlQuery(instituteFilterDto);
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = new ArrayList<>();
		Institute obj = null;
		for (Object[] row : rows) {
			obj = new Institute();
			obj.setId(row[0].toString());
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountry(getCountry(row[2].toString(), session));
			}
			if (row[3] != null) {
				obj.setCity(getCity(row[3].toString(), session));
			}
			if (row[4] != null) {
				obj.setInstituteType(getInstituteType(row[4].toString(), session));
			}
			if (row[5] != null) {
				obj.setDescription(row[5].toString());
			}
			if (row[6] != null) {
				System.out.println(row[6].toString());
				Date createdDate = (Date) row[6];
				System.out.println(createdDate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);

			}
			if (row[7] != null) {
				obj.setCampusType(row[7].toString());
			}
			if (row[8] != null) {
				obj.setDomesticRanking((Integer) row[8]);
			}
			instituteList.add(obj);
		}
		return instituteList;
	}

	private Integer getCourseCount(final String id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sa.id from course sa where sa.institute_id=" + id;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public int findTotalCountFilterInstitute(final InstituteFilterDto instituteFilterDto) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = getFilterInstituteSqlQuery(instituteFilterDto);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	private String getFilterInstituteSqlQuery(final InstituteFilterDto instituteFilterDto) {
		String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_id, inst.institute_type_id,"
				+ " inst.description, inst.updated_on, inst.campus_type, inst.domestic_ranking FROM institute as inst "
				+ " left join country ctry  on ctry.name = inst.country_name left join city ci  on ci.id = inst.city_id "
				+ "left join faculty_level f on f.institute_id = inst.id left join institute_level l on l.institute_id = inst.id "
				+ "left join course c  on c.institute_id=inst.id where inst.is_active = 1 and inst.deleted_on IS NULL  ";

		if (instituteFilterDto.getCountryId() != null) {
			sqlQuery += " and inst.country_name = " + instituteFilterDto.getCountryId() + " ";
		}

		if (instituteFilterDto.getCityId() != null) {
			sqlQuery += " and inst.city_id = " + instituteFilterDto.getCityId() + " ";
		}

		if (instituteFilterDto.getInstituteId() != null) {
			sqlQuery += " and inst.id =" + instituteFilterDto.getInstituteId() + " ";
		}

		if (instituteFilterDto.getWorldRanking() != null && instituteFilterDto.getWorldRanking() > 0) {
			sqlQuery += " and inst.world_ranking = " + instituteFilterDto.getWorldRanking() + " ";
		}

		if (instituteFilterDto.getInstituteTypeId() != null) {
			sqlQuery += " and inst.institute_category_type_id = " + instituteFilterDto.getInstituteTypeId() + " ";
		}
		if (instituteFilterDto.getDatePosted() != null && !instituteFilterDto.getDatePosted().isEmpty()) {
			Date postedDate = DateUtil.convertStringDateToDate(instituteFilterDto.getDatePosted());
			Calendar c = Calendar.getInstance();
			c.setTime(postedDate);
			c.add(Calendar.DATE, 1);
			postedDate = c.getTime();
			String updatedDate = DateUtil.getStringDateFromDate(postedDate);
			sqlQuery += " and (inst.created_on >= '" + instituteFilterDto.getDatePosted() + "' and inst.created_on < '" + updatedDate + "')";
		}
		return sqlQuery;
	}

	@Override
	public List<Institute> getInstituteCampusWithInstitue() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_id, inst.institute_type_id, inst.description, inst.updated_on , inst.campus_type FROM institute as inst where inst.is_active = 1 and inst.deleted_on IS NULL ORDER BY inst.created_on DESC";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = new ArrayList<>();
		Institute obj = null;
		for (Object[] row : rows) {
			obj = new Institute();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setName(row[1].toString());
			}
			if (row[5] != null) {
				obj.setDescription(row[5].toString());
			}
			if (row[6] != null) {
				Date createdDate = (Date) row[6];
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String dateResult = formatter.format(createdDate);
				obj.setLastUpdated(dateResult);
			}
			if (row[7] != null) {
				obj.setCampusType(row[7].toString());
			}
			instituteList.add(obj);
		}
		return instituteList;
	}

	@Override
	public List<Institute> autoSearch(final int pageNumber, final Integer pageSize, final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_id, inst.institute_type_id, inst.description, inst.updated_on, inst.campus_type, inst.domestic_ranking FROM institute as inst  "
				+ " inner join country ctry  on ctry.name = inst.country_name inner join city ci  on ci.id = inst.city_id "
				+ " inner join institute_type instType on instType.id = inst.institute_type_id " + " where  inst.deleted_on IS NULL and (inst.name like '%"
				+ searchKey + "%' or inst.description like '%" + searchKey + "%' or ctry.name like '%" + searchKey + "%' or ci.name like '%" + searchKey
				+ "%' or instType.name like '%" + searchKey + "%') " + " ORDER BY inst.created_on DESC";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = getInstituteData(rows, session);
		return instituteList;
	}

	@Override
	public int findTotalCountForInstituteAutosearch(final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_id, inst.institute_type_id, inst.description, inst.updated_on FROM institute as inst  "
				+ " inner join country ctry  on ctry.name = inst.country_name inner join city ci  on ci.id = inst.city_id "
				+ " inner join institute_type instType on instType.id = inst.institute_type_id " + " where inst.deleted_on IS NULL and (inst.name like '%"
				+ searchKey + "%' or inst.description like '%" + searchKey + "%' or ctry.name like '%" + searchKey + "%' or ci.name like '%" + searchKey
				+ "%' or instType.name like '%" + searchKey + "%') " + " ";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		return rows.size();
	}

	@Override
	public InstituteCategoryType getInstituteCategoryType(final String instituteCategoryTypeId) {
		InstituteCategoryType obj = null;
		if (instituteCategoryTypeId != null) {
			Session session = sessionFactory.getCurrentSession();
			obj = session.get(InstituteCategoryType.class, instituteCategoryTypeId);
		}
		return obj;
	}

	@Override
	public List<Institute> getSecondayCampus(final String countryId, final String cityId, final String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class);
		crit.add(Restrictions.eq("country.id", countryId));
		crit.add(Restrictions.eq("city.id", cityId));
		crit.add(Restrictions.eq("name", name));
		crit.add(Restrictions.eq("campusType", "SECONDARY"));
		return crit.list();
	}

	@Override
	public void saveInstituteserviceDetails(final InstituteService instituteServiceDetails) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteServiceDetails);
	}

	@Override
	public void deleteInstituteService(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete from InstituteService where institute_id =" + id);
		q.executeUpdate();

	}

	@Override
	public void saveInstituteIntake(final InstituteIntake instituteIntake) {
		Session session = sessionFactory.getCurrentSession();
		session.save(instituteIntake);

	}

	@Override
	public void deleteInstituteIntakeById(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("DELETE FROM institute_intake WHERE entity_id =" + id);
		query.executeUpdate();
	}

	@Override
	public List<String> getIntakesById(@Valid final String id) {
		List<String> list = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteIntake.class);
		crit.add(Restrictions.eq("entityId", id));
		List<InstituteIntake> accreditedInstituteDetails = crit.list();
		for (InstituteIntake bean : accreditedInstituteDetails) {
			list.add(bean.getIntake());
		}
		return list;
	}

	@Override
	public List<InstituteCategoryType> getAllCategories() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(InstituteCategoryType.class);
		return crit.list();
	}

	@Override
	public List<Institute> ratingWiseInstituteListByCountry(final Country country) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class, "institute");

		crit.add(Restrictions.eq("country", country));
		crit.addOrder(Order.asc("worldRanking"));
		return crit.list();
	}

	@Override
	public List<String> getInstituteIdsBasedOnGlobalRanking(final Long startIndex, final Long pageSize) {
		Session session = sessionFactory.getCurrentSession();
		List<String> insituteIds = session.createNativeQuery("SELECT ID FROM INSTITUTE ORDER BY WORLD_RANKING LIMIT ?,?").setParameter(1, startIndex)
				.setParameter(2, pageSize).getResultList();
		return insituteIds;
	}

	@Override
	public List<String> getInstitudeByCountry(final List<String> distinctCountryIds) {
		Session session = sessionFactory.getCurrentSession();
		String ids = distinctCountryIds.stream().map(String::toString).collect(Collectors.joining(","));
		List<String> instituteIds = session.createNativeQuery("SELECT ID FROM INSTITUTE WHERE COUNTRY_NAME IN (" + ids + ")").getResultList();
		return instituteIds;
	}

	@Override
	public List<String> getRandomInstituteByCountry(final List<String> countryIdList) {
		Session session = sessionFactory.getCurrentSession();

		String countryIds = countryIdList.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
		List<String> idList = session.createNativeQuery("select id from institute where country_name in (?) order by Rand() LIMIT ?")
				.setParameter(1, countryIds).setParameter(2, IConstant.TOTAL_INSTITUTES_PER_PAGE).getResultList();
		return idList;
	}

	@Override
	public Map<String, Integer> getDomesticRanking(final List<String> courseIdList) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Course.class, "course");
		criteria.createAlias("institute", "institute");
		criteria.add(Restrictions.in("course.id", courseIdList));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("course.id"));
		projectionList.add(Projections.property("institute.domesticRanking"));
		criteria.setProjection(projectionList);

		List<Object[]> resultList = criteria.list();
		Map<String, Integer> courseDomesticRanking = new HashMap<>();
		for (Object[] result : resultList) {
			courseDomesticRanking.put((String) result[0], (Integer) result[1]);
		}

		return courseDomesticRanking;
	}

	@Override
	public List<NearestInstituteDTO> getNearestInstituteList(final Integer startIndex, final Integer pageSize, final Double latitude, final Double longitude) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "SELECT institute.id,institute.name,count(course.id),min(course.usd_international_fee),max(course.usd_international_fee),institute.latitute,institute.longitute, 111.045 * DEGREES(ACOS(COS(RADIANS('"
				+ latitude + "'))" + "* COS(RADIANS(institute.latitute))\r\n" + "* COS(RADIANS(institute.longitute) - RADIANS('" + longitude + "'))\r\n"
				+ "+ SIN(RADIANS('" + latitude + "'))\r\n" + "* SIN(RADIANS(institute.latitute))))\r\n" + "AS distance_in_km\r\n"
				+ "FROM institute inner join course on institute.id = course.institute_id where institute.latitute is not null and institute.longitute is not null \r\n"
				+ "and institute.latitute!=" + latitude + " and institute.longitute!=" + longitude + "\r\n" + "group by institute.id\r\n"
				+ "ORDER BY distance_in_km ASC\r\n" + "LIMIT " + startIndex + "," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();

		List<NearestInstituteDTO> nearestInstituteDTOs = new ArrayList<>();
		for (Object[] row : rows) {
			NearestInstituteDTO nearestInstituteDTO = new NearestInstituteDTO();
			nearestInstituteDTO.setInstituteId((String.valueOf(row[0])));
			nearestInstituteDTO.setInstituteName(String.valueOf(row[1]));
			nearestInstituteDTO.setTotalCourseCount(((BigInteger) row[2]).intValue());
			nearestInstituteDTO.setMinPriceRange((Double) row[3]);
			nearestInstituteDTO.setMaxPriceRange((Double) row[4]);
			nearestInstituteDTO.setLatitute((Double) row[5]);
			nearestInstituteDTO.setLongitude((Double) row[6]);
			nearestInstituteDTOs.add(nearestInstituteDTO);
		}
		return nearestInstituteDTOs;
	}

	@Override
	public List<String> getUserSearchInstituteRecommendation(final Integer startIndex, final Integer pageSize, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Institute.class);
		criteria.setProjection(Projections.property("name"));
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			criteria.add(Restrictions.ilike("name", searchKeyword, MatchMode.ANYWHERE));
		}
		if (startIndex != null && pageSize != null) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

	@Override
	public List<InstituteResponseDto> getDistinctInstituteListByName(Integer startIndex, Integer pageSize, String instituteName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Institute.class)
				.setProjection(Projections.projectionList().add(Projections.groupProperty("name").as("name"))
						.add(Projections.property("id").as("id"))
						.add(Projections.property("worldRanking").as("worldRanking"))
						.add(Projections.property("city.id").as("cityId"))
						.add(Projections.property("country.id").as("countryId"))
						.add(Projections.property("website").as("website"))
						.add(Projections.property("aboutInfo").as("aboutUs"))
						.add(Projections.property("openingFrom").as("openingFrom"))
						.add(Projections.property("openingTo").as("openingTo"))
						.add(Projections.property("totalStudent").as("totalStudent"))
						.add(Projections.property("latitute").as("latitute"))
						.add(Projections.property("longitude").as("longitude"))
						.add(Projections.property("phoneNumber").as("phoneNumber"))
						.add(Projections.property("email").as("email"))
						.add(Projections.property("address").as("address"))
						.add(Projections.property("updatedOn").as("updatedOn"))
						.add(Projections.property("campusType").as("campusType"))
						.add(Projections.property("domesticRanking").as("domesticRanking")))
				.setResultTransformer(Transformers.aliasToBean(InstituteResponseDto.class));
		if (StringUtils.isNotEmpty(instituteName)) {
			criteria.add(Restrictions.like("name", instituteName, MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@Override
	public int getDistinctInstituteCountByName(String instituteName) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlQuery = new StringBuilder("select distinct i.name as instituteName from institute i");
		if (StringUtils.isNotEmpty(instituteName)) {
			sqlQuery.append(" where name like ('" + "%" + instituteName.trim() + "%')");
		}
		Query query = session.createSQLQuery(sqlQuery.toString());
		List<Object[]> rows = query.list();
		return rows.size();
	}
}
