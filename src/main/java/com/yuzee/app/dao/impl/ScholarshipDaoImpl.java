package com.yuzee.app.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipIntakes;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dto.ScholarshipResponseDTO;
import com.yuzee.app.util.CommonUtil;

@Component
@SuppressWarnings({ "deprecation", "unchecked" })
public class ScholarshipDaoImpl implements ScholarshipDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Scholarship saveScholarship(final Scholarship scholarship) {
		Session session = sessionFactory.getCurrentSession();
		session.save(scholarship);
		Scholarship scholarships = session.get(Scholarship.class, scholarship.getName());
		return scholarships;
	}

	@Override
	public void saveScholarshipIntake(final ScholarshipIntakes scholarshipIntakes) {
		Session session = sessionFactory.getCurrentSession();
		session.save(scholarshipIntakes);
	}

	@Override
	public void saveScholarshipLanguage(final ScholarshipLanguage scholarshipLanguage) {
		Session session = sessionFactory.getCurrentSession();
		session.save(scholarshipLanguage);
	}

	@Override
	public Scholarship getScholarshipById(final String id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Scholarship.class, id);
	}

	@Override
	public List<ScholarshipIntakes> getIntakeByScholarship(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ScholarshipIntakes.class, "intake");
		criteria.createAlias("intake.scholarship", "scholarship");
		if (id != null) {
			criteria.add(Restrictions.eq("scholarship.id", id));
		}
		return criteria.list();
	}

	@Override
	public List<ScholarshipLanguage> getLanguageByScholarship(final String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ScholarshipLanguage.class, "language");
		criteria.createAlias("language.scholarship", "scholarship");
		if (id != null) {
			criteria.add(Restrictions.eq("scholarship.id", id));
		}
		return criteria.list();
	}

	@Override
	public void deleteScholarshipIntakes(final String scholarShipId) {
		Session session = sessionFactory.getCurrentSession();
		List<ScholarshipIntakes> intakes = getIntakeByScholarship(scholarShipId);
		for (ScholarshipIntakes scholarshipIntakes : intakes) {
			session.delete(scholarshipIntakes);
		}
	}

	@Override
	public void deleteScholarshipLanguage(final String scholarShipId) {
		Session session = sessionFactory.getCurrentSession();
		List<ScholarshipLanguage> languages = getLanguageByScholarship(scholarShipId);
		for (ScholarshipLanguage scholarshipLanguage : languages) {
			session.delete(scholarshipLanguage);
		}
	}

	@Override
	public void updateScholarship(final Scholarship scholarship) {
		Session session = sessionFactory.getCurrentSession();
		session.update(scholarship);
	}

	@Override
	public List<ScholarshipResponseDTO> getScholarshipList(final Integer startIndex, final Integer pageSize, final String countryId,
			final String instituteId, final String validity, final Boolean isActive, final Date updatedOn, final String searchKeyword,
			final String sortByField, String sortByType) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sch.id, sch.name, sch.course_name, sch.description, sch.scholarship_award, sch.country_name, level.id as level_id,"
				+ " sch.number_of_avaliability, sch.scholarship_amount,"
				+ " sch.validity, sch.how_to_apply, sch.gender, sch.eligible_nationality, sch.headquaters, sch.email, sch.address, sch.created_on,"
				+ " sch.updated_on, sch.created_by,sch.updated_by,"
				+ " sch.deleted_on, sch.is_active, sch.website, sch.institute_name, sch.application_deadline, level.name as level_name,"
				+ " sch.currency,sch.requirements,level.code as level_code"
				+ " from scholarship sch inner join level level on sch.level_id = level.id";
		if (null != countryId) {
			sqlQuery += " and sch.country_name ='" + countryId + "'";
		}
		if (null != instituteId) {
			sqlQuery += " and sch.institute_name ='" + instituteId + "'";
		}
		if (null != validity) {
			sqlQuery += " and sch.validity =" + validity;
		}
		if (null != isActive) {
			sqlQuery += " and sch.is_active =" + isActive;
		} else {
			sqlQuery += " and sch.is_active =1";
		}
		if (null != updatedOn) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			sqlQuery += " and sch.updated_on >=" + fromDate;
			sqlQuery += " and sch.updated_on <=" + toDate;
		}
		if (searchKeyword != null) {
			sqlQuery += " and ( sch.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or sch.course_name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or sch.validity like '%" + searchKeyword.trim() + "%' )";
		}
		sqlQuery += " ";
		String sortingQuery = "";
		if (sortByType == null) {
			sortByType = "DESC";
		}
		if (sortByField != null) {
			if ("name".equals(sortByField)) {
				sortingQuery = sortingQuery + " ORDER BY sch.name " + sortByType + " ";
			} else if ("offeredBy".equals(sortByField)) {
				sortingQuery = sortingQuery + " ORDER BY sch.course_name " + sortByType + " ";
			} else if ("countryId".equals(sortByField)) {
				sortingQuery = sortingQuery + " ORDER BY sch.country_name " + sortByType + " ";
			} else if ("validity".equals(sortByField)) {
				sortingQuery = sortingQuery + " ORDER BY sch.validity " + sortByType + " ";
			} else {
				sortingQuery = sortingQuery + " ORDER BY sch.id " + sortByType + " ";
			}
		} else {
			sortingQuery = sortingQuery + " ORDER BY sch.id " + sortByType + " ";
		}

		if (startIndex != null && pageSize != null) {
			sqlQuery += sortingQuery + " LIMIT " + startIndex + " ," + pageSize;
		} else {
			sqlQuery += sortingQuery;
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ScholarshipResponseDTO> list = new ArrayList<>();
		for (Object[] row : rows) {
			ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
			scholarshipResponseDTO.setId(String.valueOf(row[0]));
			scholarshipResponseDTO.setName(String.valueOf(row[1]));
			scholarshipResponseDTO.setCourseName(String.valueOf(row[2]));
			scholarshipResponseDTO.setDescription(String.valueOf(row[3]));
			scholarshipResponseDTO.setScholarshipAward(String.valueOf(row[4]));
			if (row[5] != null) {
				scholarshipResponseDTO.setCountryName(String.valueOf(row[5]));
			} else {
				scholarshipResponseDTO.setCountryName(null);
			}
			if (row[6] != null) {
				scholarshipResponseDTO.setLevelId(String.valueOf(row[6]));
			} else {
				scholarshipResponseDTO.setLevelId(null);
			}
			if (row[7] != null) {
				scholarshipResponseDTO.setNumberOfAvaliability(((Integer) row[7]).intValue());
			} else {
				scholarshipResponseDTO.setNumberOfAvaliability(null);
			}
			scholarshipResponseDTO.setScholarshipAmount((Double) row[8]);
			scholarshipResponseDTO.setValidity(String.valueOf(row[9]));
			scholarshipResponseDTO.setHowToApply(String.valueOf(row[10]));
			scholarshipResponseDTO.setGender(String.valueOf(row[11]));
			scholarshipResponseDTO.setEligibleNationality(String.valueOf(row[12]));
			scholarshipResponseDTO.setHeadquaters(String.valueOf(row[13]));
			scholarshipResponseDTO.setEmail(String.valueOf(row[14]));
			scholarshipResponseDTO.setAddress(String.valueOf(row[15]));
			scholarshipResponseDTO.setCreatedOn((Date) row[16]);
			scholarshipResponseDTO.setUpdatedOn((Date) row[17]);
			scholarshipResponseDTO.setCreatedBy(String.valueOf(row[18]));
			scholarshipResponseDTO.setUpdatedBy(String.valueOf(row[19]));
			scholarshipResponseDTO.setDeletedOn((Date) row[20]);
			if (String.valueOf(row[21]) != null && String.valueOf(row[21]).equals("1")) {
				scholarshipResponseDTO.setIsActive(true);
			} else {
				scholarshipResponseDTO.setIsActive(false);
			}

			scholarshipResponseDTO.setWebsite(String.valueOf(row[22]));
			if (row[23] != null) {
				scholarshipResponseDTO.setInstituteName(String.valueOf(row[23]));
			} else {
				scholarshipResponseDTO.setInstituteName(null);
			}
			scholarshipResponseDTO.setApplicationDeadline((Date) row[24]);
			scholarshipResponseDTO.setLevelName(String.valueOf(row[25]));
			scholarshipResponseDTO.setCurrency(String.valueOf(row[26]));
			scholarshipResponseDTO.setRequirements(String.valueOf(row[27]));
			scholarshipResponseDTO.setLevelCode(String.valueOf(row[28]));
			list.add(scholarshipResponseDTO);
		}
		return list;
	}

	@Override
	public int countScholarshipList(final String countryId, final String instituteId, final String validity, final Boolean isActive,
			final Date updatedOn, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select sch.id from scholarship sch inner join level level on sch.level_id = level.id inner join"
				+ " institute ins on sch.institute_name = ins.name where 1 = 1";
		if (null != countryId) {
			sqlQuery += " and sch.country_name ='" + countryId + "'";
		}
		if (null != instituteId) {
			sqlQuery += " and ins.id ='" + instituteId + "'";
		}
		if (null != validity) {
			sqlQuery += " and sch.validity =" + validity;
		}
		if (null != isActive) {
			sqlQuery += " and sch.is_active =" + isActive;
		} else {
			sqlQuery += " and sch.is_active =1";
		}
		if (null != updatedOn) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			sqlQuery += " and sch.updated_on >=" + fromDate;
			sqlQuery += " and sch.updated_on <=" + toDate;
		}
		if (searchKeyword != null) {
			sqlQuery += " and ( sch.name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or sch.course_name like '%" + searchKeyword.trim() + "%'";
			sqlQuery += " or sch.country_name like '%" + searchKeyword.trim() + "%' )";
			sqlQuery += " or sch.validity like '%" + searchKeyword.trim() + "%' )";
		}
		System.out.println(sqlQuery);
		Query query = session.createSQLQuery(sqlQuery);
		return query.list().size();
	}

	@Override
	public List<String> getRandomScholarShipsForCountry(final List<String> countryId, final Integer limit) {

		Session session = sessionFactory.getCurrentSession();
		String ids = countryId.stream().map(String::toString).collect(Collectors.joining(","));
		String query = "Select id from scholarship where country_name in ('" + ids.replace("'", "") + "') order by Rand() LIMIT ?";
		List<String> scholarshipIds = session.createNativeQuery(query).setParameter(1, limit).getResultList();
		return scholarshipIds;
	}

	@Override
	public List<Scholarship> getAllScholarshipDetailsFromId(final List<String> recommendedScholarships) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Scholarship.class, "scholarship");
		criteria.add(Restrictions.in("id", recommendedScholarships));
		return criteria.list();
	}

	@Override
	public List<String> getRandomScholarships(final int i) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select id from scholarship order by Rand() LIMIT ?";
		List<String> scholarshipIds = session.createNativeQuery(query).setParameter(1, i).getResultList();
		return scholarshipIds;
	}

	@Override
	public BigInteger getScholarshipCountByLevelId(String levelId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder query = new StringBuilder("Select count(*) from scholarship where level_id='" + levelId + "'");
		BigInteger count = (BigInteger) session.createNativeQuery(query.toString()).uniqueResult();
		return count;
	}
}
