package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

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
	public Institute get(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		Institute obj = session.get(Institute.class, id);
		return obj;
	}

	@Override
	public List<Institute> getAllInstituteByCountry(final BigInteger countryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class);
		crit.add(Restrictions.eq("countryObj.id", countryId));
		return crit.list();
	}

	@Override
	public List<Institute> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Institute.class);
		return crit.list();
	}

	/*
	 * @Override public Institute getUserByEmail(String email) { Session session =
	 * sessionFactory.getCurrentSession(); Criteria crit =
	 * session.createCriteria(UserInfo.class);
	 * crit.add(Restrictions.eq("emailId",email)); List<UserInfo> users =
	 * crit.list(); return users !=null && !users.isEmpty()?users.get(0):null; }
	 */

	/*
	 * private void retrieveEmployee() {
	 *
	 * try{ String sqlQuery="select e from Employee e inner join e.addList"; Session
	 * session=sessionFactory.getCurrentSession(); Query
	 * query=session.createQuery(sqlQuery); List<Institute> list=query.list();
	 * list.stream().forEach((p)->{System.out.println(p.getName());});
	 * }catch(Exception e){ e.printStackTrace(); } }
	 */

	@Override
	public List<InstituteSearchResultDto> getInstitueBySearchKey(final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select i.id,i.name,c.name as countryName,ci.name as cityName from institute i  inner join country c  on c.id = i.country_id "
						+ "inner join city ci  on ci.id = i.city_id  where i.name like '%" + searchKey + "%'");
		List<Object[]> rows = query.list();
		List<InstituteSearchResultDto> instituteList = new ArrayList<>();
		InstituteSearchResultDto obj = null;
		for (Object[] row : rows) {
			obj = new InstituteSearchResultDto();
			obj.setInstituteId(new BigInteger(row[0].toString()));
			obj.setInstituteName(row[1].toString());
			obj.setLocation(row[2].toString() + ", " + row[3].toString());
			instituteList.add(obj);
		}
		return instituteList;
	}

	@Override
	public List<InstituteResponseDto> getAllInstitutesByFilter(final CourseSearchDto filterObj) {
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,count(c.id) as courses, MIN(c.world_ranking) as world_ranking, MIN(c.stars) as stars "
				+ "from institute inst  inner join country ctry  on ctry.id = inst.country_id inner join city ci  on ci.id = inst.city_id "
				+ "inner join faculty_level f on f.institute_id = inst.id inner join institute_level l on l.institute_id = inst.id "
				+ "inner join course c  on c.institute_id=inst.id where 1=1 ";

		if (null != filterObj.getCountryIds() && !filterObj.getCountryIds().isEmpty()) {
			sqlQuery += " and inst.country_id in (" + filterObj.getCountryIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != filterObj.getLevelIds() && !filterObj.getLevelIds().isEmpty()) {
			sqlQuery += " and l.level_id in (" + filterObj.getLevelIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != filterObj.getFacultyIds() && !filterObj.getFacultyIds().isEmpty()) {
			sqlQuery += " and f.faculty_id in (" + filterObj.getFacultyIds().stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
		}

		if (null != filterObj.getSearchKey() && !filterObj.getSearchKey().isEmpty()) {
			sqlQuery += " and inst.name like '%" + filterObj.getSearchKey().trim() + "%'";
		}
		sqlQuery += " group by inst.id ";

		String sortingQuery = "";
		if (null != filterObj.getSortingObj()) {
			CourseSearchFilterDto sortingObj = filterObj.getSortingObj();
			if (null != sortingObj.getWorldRanking() && !sortingObj.getWorldRanking().isEmpty()) {
				if (sortingObj.getWorldRanking().equals("ASC")) {
					sortingQuery = " order by crs.world_ranking asc";
				} else {
					sortingQuery = " order by crs.world_ranking desc";
				}
			}
		} else {
			sortingQuery = " order by inst.name asc";
		}
		String sizeQuery = sqlQuery + " " + sortingQuery;
		Query query1 = session.createSQLQuery(sizeQuery);
		List<Object[]> rows1 = query1.list();

		sqlQuery += sortingQuery + " LIMIT " + filterObj.getPageNumber() + " ," + filterObj.getMaxSizePerPage();

		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<InstituteResponseDto> list = new ArrayList<>();
		InstituteResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new InstituteResponseDto();
			obj.setInstituteId(new BigInteger(String.valueOf(row[0])));
			obj.setInstituteName(String.valueOf(row[1]));
			obj.setLocation(String.valueOf(row[2]) + ", " + String.valueOf(row[3]));
			obj.setCityName(String.valueOf(row[2]));
			obj.setCountryName(String.valueOf(row[3]));
			Integer worldRanking = 0;
			if (null != row[4]) {
				worldRanking = Double.valueOf(String.valueOf(row[4])).intValue();
			}
			obj.setWorldRanking(worldRanking.toString());
			obj.setStars(String.valueOf(row[5]));
			obj.setTotalCourses(Integer.parseInt(String.valueOf(row[6])));
			obj.setTotalCount(rows1.size());
			list.add(obj);
		}
		return list;
	}

	@Override
	public InstituteResponseDto getInstituteByID(final BigInteger instituteId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct inst.id as instId,inst.name as instName,ci.name as cityName,"
				+ "ctry.name as countryName,crs.world_ranking,crs.stars,crs.totalCourse " + "from institute inst  inner join country ctry  "
				+ "on ctry.id = inst.country_id inner join city ci  on ci.id = inst.city_id "
				+ "CROSS APPLY ( select count(c.id) as totalCourse, MIN(c.world_ranking) as world_ranking, MIN(c.stars) as stars from " + "course c where "
				+ "c.institute_id = inst.id group by c.institute_id ) crs " + "where 1=1 and inst.id ='" + instituteId + "'";
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		InstituteResponseDto obj = null;
		for (Object[] row : rows) {
			obj = new InstituteResponseDto();
			obj.setInstituteId(new BigInteger(String.valueOf(row[0])));
			obj.setInstituteName(String.valueOf(row[1]));
			obj.setLocation(String.valueOf(row[2]) + ", " + String.valueOf(row[3]));
			obj.setWorldRanking(String.valueOf(row[4]));
			obj.setStars(String.valueOf(row[5]));
			obj.setTotalCourses(Integer.parseInt(String.valueOf(row[6])));
		}
		return obj;
	}

	@Override
	public List<InstituteResponseDto> getInstitudeByCityId(final BigInteger cityId) {
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
			instituteResponseDto.setInstituteId(new BigInteger(String.valueOf(row[0])));
			instituteResponseDto.setInstituteName(String.valueOf(row[1]));
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
			instituteResponseDto.setInstituteId(new BigInteger(String.valueOf(row[0])));
			instituteResponseDto.setInstituteName(String.valueOf(row[1]));
			instituteResponseDtos.add(instituteResponseDto);
		}
		InstituteResponseDto instituteResponseDto1 = new InstituteResponseDto();
		instituteResponseDto1.setInstituteId(new BigInteger("111111"));
		instituteResponseDto1.setInstituteName("All");
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
			obj.setId(new BigInteger(row[0].toString()));
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountry(getCountry(new BigInteger(row[2].toString()), session));
			}
			if (row[3] != null) {
				obj.setCity(getCity(new BigInteger(row[3].toString()), session));
			}
			if (row[4] != null) {
				obj.setInstituteType(getInstituteType(new BigInteger(row[4].toString()), session));
			}
			instituteList.add(obj);
		}
		return instituteList;
	}

	private InstituteType getInstituteType(final BigInteger id, final Session session) {
		return session.get(InstituteType.class, id);
	}

	private City getCity(final BigInteger id, final Session session) {
		return session.get(City.class, id);
	}

	private Country getCountry(final BigInteger id, final Session session) {
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
		String sqlQuery = "select inst.id, inst.name , inst.country_id , inst.city_id, inst.institute_type_id, inst.description FROM institute as inst where inst.is_active = 1 and inst.deleted_on IS NULL ORDER BY inst.created_on DESC";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<Institute> instituteList = new ArrayList<>();
		Institute obj = null;
		for (Object[] row : rows) {
			obj = new Institute();
			obj.setId(new BigInteger(row[0].toString()));
			obj.setName(row[1].toString());
			if (row[2] != null) {
				obj.setCountry(getCountry(new BigInteger(row[2].toString()), session));
			}
			if (row[3] != null) {
				obj.setCity(getCity(new BigInteger(row[3].toString()), session));
			}
			if (row[4] != null) {
				obj.setInstituteType(getInstituteType(new BigInteger(row[4].toString()), session));
			}
			if (row[5] != null) {
				obj.setDescription(row[5].toString());
			}
			instituteList.add(obj);
		}
		return instituteList;
	}
}
