package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.ScholarshipIntakes;
import com.seeka.app.bean.ScholarshipLanguage;
import com.seeka.app.util.CommonUtil;

@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class ScholarshipDao implements IScholarshipDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveScholarship(final Scholarship scholarship) {
		Session session = sessionFactory.getCurrentSession();
		session.save(scholarship);
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
	public Scholarship getScholarshipById(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Scholarship.class, id);
	}

	@Override
	public List<ScholarshipIntakes> getIntakeByScholarship(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ScholarshipIntakes.class, "intake");
		criteria.createAlias("intake.scholarship", "scholarship");
		if (id != null) {
			criteria.add(Restrictions.eq("scholarship.id", id));
		}
		return criteria.list();
	}

	@Override
	public List<ScholarshipLanguage> getLanguageByScholarship(final BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ScholarshipLanguage.class, "language");
		criteria.createAlias("language.scholarship", "scholarship");
		if (id != null) {
			criteria.add(Restrictions.eq("scholarship.id", id));
		}
		return criteria.list();
	}

	@Override
	public void deleteScholarshipIntakes(final BigInteger scholarShipId) {
		Session session = sessionFactory.getCurrentSession();
		List<ScholarshipIntakes> intakes = getIntakeByScholarship(scholarShipId);
		for (ScholarshipIntakes scholarshipIntakes : intakes) {
			session.delete(scholarshipIntakes);
		}
	}

	@Override
	public void deleteScholarshipLanguage(final BigInteger scholarShipId) {
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
	public List<Scholarship> getScholarshipList(final Integer startIndex, final Integer pageSize, final BigInteger countryId, final BigInteger instituteId,
			final String validity, final Boolean isActive, final Date updatedOn, final String searchKeyword, final String sortByField, String sortByType) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Scholarship.class, "scholarship");
		criteria.createAlias("scholarship.country", "country");
		criteria.createAlias("scholarship.institute", "institute");

		if (countryId != null) {
			criteria.add(Restrictions.eq("country.id", countryId));
		}
		if (instituteId != null) {
			criteria.add(Restrictions.eq("institute.id", instituteId));
		}
		if (validity != null) {
			criteria.add(Restrictions.eq("scholarship.validity", validity));
		}
		if (isActive != null) {
			criteria.add(Restrictions.eq("scholarship.isActive", isActive));
		} else {
			criteria.add(Restrictions.eq("scholarship.isActive", true));
		}
		if (updatedOn != null) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			criteria.add(Restrictions.ge("scholarship.updatedOn", fromDate));
			criteria.add(Restrictions.le("scholarship.updatedOn", toDate));
		}
		if (searchKeyword != null) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("scholarship.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("scholarship.offeredBy", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("country.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("scholarship.validity", searchKeyword, MatchMode.ANYWHERE)));
		}
		if (sortByType == null) {
			sortByType = "DESC";
		}
		if (sortByField != null) {
			if ("name".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("scholarship.name"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("scholarship.name"));
				}
			} else if ("offeredBy".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("scholarship.offeredBy"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("scholarship.offeredBy"));
				}
			} else if ("countryId".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("country.name"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("country.name"));
				}
			} else if ("validity".equals(sortByField)) {
				if ("ASC".equals(sortByType)) {
					criteria.addOrder(Order.asc("scholarship.validity"));
				} else if ("DESC".equals(sortByType)) {
					criteria.addOrder(Order.desc("scholarship.validity"));
				}
			} else {
				criteria.addOrder(Order.desc("scholarship.id"));
			}
		} else {
			criteria.addOrder(Order.desc("scholarship.id"));
		}
		if ((startIndex != null) && (pageSize != null)) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
		}
		return criteria.list();
	}

	@Override
	public int countScholarshipList(final BigInteger countryId, final BigInteger instituteId, final String validity, final Boolean isActive,
			final Date updatedOn, final String searchKeyword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Scholarship.class, "scholarship");
		criteria.createAlias("scholarship.country", "country");
		criteria.createAlias("scholarship.institute", "institute");
		if (countryId != null) {
			criteria.add(Restrictions.eq("country.id", countryId));
		}
		if (instituteId != null) {
			criteria.add(Restrictions.eq("institute.id", instituteId));
		}
		if (validity != null) {
			criteria.add(Restrictions.eq("scholarship.validity", validity));
		}
		if (isActive != null) {
			criteria.add(Restrictions.eq("scholarship.isActive", isActive));
		} else {
			criteria.add(Restrictions.eq("scholarship.isActive", true));
		}
		if (updatedOn != null) {
			Date fromDate = CommonUtil.getDateWithoutTime(updatedOn);
			Date toDate = CommonUtil.getTomorrowDate(updatedOn);
			criteria.add(Restrictions.ge("scholarship.updatedOn", fromDate));
			criteria.add(Restrictions.le("scholarship.updatedOn", toDate));
		}
		if (searchKeyword != null) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("scholarship.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("scholarship.offeredBy", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("country.name", searchKeyword, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("scholarship.validity", searchKeyword, MatchMode.ANYWHERE)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public List<BigInteger> getRandomScholarShipsForCountry(final List<BigInteger> countryId, final Integer limit) {

		Session session = sessionFactory.getCurrentSession();
		String ids = countryId.stream().map(BigInteger::toString).collect(Collectors.joining(","));
		String query = "Select id from scholarship where country_id in (" + ids + ") order by Rand() LIMIT ?";
		List<BigInteger> scholarshipIds = session.createNativeQuery(query).setParameter(1, limit).getResultList();
		return scholarshipIds;
	}

	@Override
	public List<Scholarship> getAllScholarshipDetailsFromId(final List<BigInteger> recommendedScholarships) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Scholarship.class, "scholarship");
		criteria.add(Restrictions.in("id", recommendedScholarships));
		return criteria.list();
	}

	@Override
	public List<BigInteger> getRandomScholarships(final int i) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select id from scholarship order by Rand() LIMIT ?";
		List<BigInteger> scholarshipIds = session.createNativeQuery(query).setParameter(1, i).getResultList();
		return scholarshipIds;
	}
}
