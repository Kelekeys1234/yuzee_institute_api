package com.seeka.app.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipFilterDto;
import com.seeka.app.util.DateUtil;

@Repository
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class ScholarshipDao implements IScholarshipDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Scholarship obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public Scholarship get(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        Scholarship scholarship = session.get(Scholarship.class, id);
        return scholarship;
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

    private Country getCountry(BigInteger id, Session session) {
        return session.get(Country.class, id);
    }

    private Institute getInstitute(BigInteger id, Session session) {
        return session.get(Institute.class, id);
    }

    private Level getLevel(BigInteger id, Session session) {
        return session.get(Level.class, id);
    }

    @Override
    public List<Scholarship> getAll(Integer pageNumber, Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by_institution_id, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted, sch.offer_by_course_id  FROM scholarship as sch where sch.is_active = 1 and sch.is_deleted IS NULL ORDER BY sch.created_on DESC";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Scholarship> scholarshipList = getScholarshipData(rows, session);
        return scholarshipList;
    }

    private List<Scholarship> getScholarshipData(List<Object[]> rows, Session session) {
        List<Scholarship> scholarshipList = new ArrayList<Scholarship>();
        Scholarship obj = null;
        for (Object[] row : rows) {
            obj = new Scholarship();
            obj.setId(new BigInteger((row[0].toString())));
            if (row[1] != null) {
                obj.setCountry(getCountry(new BigInteger((row[1].toString())), session));
            }
            if (row[2] != null) {
                obj.setInstitute(getInstitute(new BigInteger((row[2].toString())), session));
            }
            if (row[3] != null) {
                obj.setLevel(getLevel(new BigInteger((row[3].toString())), session));
            }
            if (row[4] != null) {
                obj.setName(row[4].toString());
            }
            if (row[5] != null) {
                obj.setAmount(row[5].toString());
            }
            if (row[6] != null) {
                obj.setStudent(row[6].toString());
            }
            if (row[7] != null) {
                obj.setDescription(row[7].toString());
            }
            if (row[8] != null) {
                obj.setWebsite(row[8].toString());
            }
            if (row[9] != null) {
                try {
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                    Date createdOn = formatter1.parse(row[9].toString());
                    obj.setCreatedOn(createdOn);
                } catch (Exception exception) {

                }
            }
            if (row[10] != null) {
                try {
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                    Date updatedon = formatter1.parse(row[10].toString());
                    obj.setUpdatedOn(updatedon);
                    Date updatedDate = (Date) row[10];
                    String dateResult = formatter1.format(updatedDate);
                    obj.setLastUpdated(dateResult);
                } catch (Exception exception) {

                }
            }

            if (row[11] != null) {
                obj.setCreatedBy(row[11].toString());
            }
            if (row[12] != null) {
                obj.setUpdatedBy(row[12].toString());
            }
            if (row[13] != null) {
                obj.setScholarshipTitle(row[13].toString());
            }
            if (row[14] != null) {
                obj.setOfferedByInstitute(new BigInteger(row[14].toString()));
            }
            if (row[15] != null) {
                obj.setBenefits(row[15].toString());
            }
            if (row[16] != null) {
                obj.setRequirements(row[16].toString());
            }
            if (row[17] != null) {
                obj.setEligibility(row[17].toString());
            }
            if (row[18] != null) {
                obj.setIntake(row[18].toString());
            }
            if (row[19] != null) {
                obj.setLanguage(row[19].toString());
            }
            if (row[20] != null) {
                obj.setValidity(row[20].toString());
            }
            if (row[21] != null) {
                obj.setGender(row[21].toString());
            }
            if (row[22] != null) {
                obj.setApplicationDeadline(row[22].toString());
            }
            if (row[23] != null) {
                obj.setScholarshipAmount(Double.parseDouble(row[23].toString()));
            }
            if (row[24] != null) {
                obj.setNumberOfAvaliability(Integer.parseInt(row[24].toString()));
            }
            if (row[25] != null) {
                obj.setHeadquaters(row[25].toString());
            }
            if (row[26] != null) {
                obj.setEmail(row[26].toString());
            }
            if (row[27] != null) {
                obj.setAddress(row[27].toString());
            }
            if (row[28] != null) {
                obj.setIsActive(Boolean.parseBoolean(row[28].toString()));
            }
            if (row[29] != null) {
                obj.setIsDeleted(Boolean.parseBoolean(row[29].toString()));
            }
            if (row[30] != null) {
                obj.setOfferedByCourse(new BigInteger(row[30].toString()));
            }
            if (obj.getOfferedByCourse() != null) {
                obj.setOfferByCourseName(getCourseName(obj.getOfferedByCourse()));
            }
            if (obj.getOfferedByInstitute() != null) {
                obj.setOfferedByInstituteName(getInstituteName(obj.getOfferedByInstitute()));
            }

            scholarshipList.add(obj);
        }
        return scholarshipList;
    }

    private String getInstituteName(BigInteger id) {
        String name = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Institute institute = session.get(Institute.class, id);
            if (institute != null) {
                name = institute.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getCourseName(BigInteger id) {
        String name = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Course course = session.get(Course.class, id);
            if (course != null) {
                name = course.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public Scholarship findById(BigInteger id) {
        Scholarship scholarship = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            scholarship = session.get(Scholarship.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scholarship;
    }

    @Override
    public void deleteScholarship(Scholarship scholarship) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(scholarship);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScholarship(Scholarship scholarship, ScholarshipDto scholarshipdto) {
        Session session = sessionFactory.getCurrentSession();
        try {
            scholarship.setCountry(getCountry(scholarshipdto.getCountryId(), session));
            scholarship.setInstitute(getInstitute(scholarshipdto.getInstituteId(), session));
            scholarship.setLevel(getLevel(scholarshipdto.getLevelId(), session));
            scholarship.setName(scholarshipdto.getScholarshipTitle());
            scholarship.setAmount(scholarshipdto.getScholarshipAmount());
            scholarship.setDescription(scholarshipdto.getDescription());
            scholarship.setStudent(scholarshipdto.getStudent());
            scholarship.setWebsite(scholarshipdto.getWebsite());
            scholarship.setScholarshipTitle(scholarshipdto.getScholarshipTitle());
            scholarship.setOfferedByInstitute(scholarshipdto.getOfferedByInstitute());
            scholarship.setOfferedByCourse(scholarshipdto.getOfferedByCourse());
            scholarship.setBenefits(scholarshipdto.getBenefits());
            scholarship.setRequirements(scholarshipdto.getRequirements());
            scholarship.setEligibility(scholarshipdto.getEligibility());
            scholarship.setIntake(scholarshipdto.getIntake());
            scholarship.setLanguage(scholarshipdto.getLanguage());
            scholarship.setValidity(scholarshipdto.getValidity());
            scholarship.setGender(scholarshipdto.getGender());
            scholarship.setApplicationDeadline(scholarshipdto.getApplicationDeadline());
            scholarship.setNumberOfAvaliability(scholarshipdto.getNumberOfAvaliability());
            scholarship.setHeadquaters(scholarshipdto.getHeadquaters());
            scholarship.setEmail(scholarshipdto.getEmail());
            scholarship.setAddress(scholarship.getAddress());
            scholarship.setIsActive(scholarshipdto.getIsActive());
            scholarship.setIsDeleted(scholarship.getIsDeleted());
            scholarship.setUpdatedOn(new Date());
            scholarship.setCoverage(scholarship.getCoverage());
            scholarship.setType(scholarship.getType());
            scholarship.setAward(scholarship.getAward());
            scholarship.setHowToApply(scholarship.getHowToApply());
            session.update(scholarship);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public List<ScholarshipDto> getScholarshipBySearchKey(String searchKey) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(
                        "select shc.id, shc.name, c.name as countryName, i.name as instituteName, l.name as levelName from scholarship shc  inner join country c  on c.id = shc.country_id "
                                        + "inner join institute i  on i.id = i.institute_id" + "inner join level l  on l.id = l.level_id where shc.name like '%" + searchKey
                                        + "%'");
        List<Object[]> rows = query.list();
        List<ScholarshipDto> scholarshipList = new ArrayList<ScholarshipDto>();
        ScholarshipDto obj = null;
        for (Object[] row : rows) {
            obj = new ScholarshipDto();
            // obj.setInstituteId(new BigInteger((row[0].toString())));
            // obj.setInstituteName(row[1].toString());
            // obj.setLocation(row[2].toString() + ", " + row[3].toString());
            scholarshipList.add(obj);
        }
        return scholarshipList;
    }

    @Override
    public List<Scholarship> scholarshipFilter(int pageNumber, Integer pageSize, ScholarshipFilterDto scholarshipFilterDto) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted, sch.offer_by_course_id  FROM scholarship as sch "
                        + " where sch.is_deleted IS NULL ";

        sqlQuery = addSchoolarFilter(sqlQuery, scholarshipFilterDto);
        sqlQuery += " ";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Scholarship> scholarshipList = new ArrayList<Scholarship>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Scholarship obj = null;
        for (Object[] row : rows) {
            obj = new Scholarship();
            obj.setId(new BigInteger((row[0].toString())));
            if (row[1] != null) {
                obj.setCountry(getCountry(new BigInteger((row[1].toString())), session));
            }
            if (row[2] != null) {
                obj.setInstitute(getInstitute(new BigInteger((row[2].toString())), session));
            }
            if (row[3] != null) {
                obj.setLevel(getLevel(new BigInteger((row[3].toString())), session));
            }
            if (row[4] != null) {
                obj.setName(row[4].toString());
            }
            if (row[5] != null) {
                obj.setAmount(row[5].toString());
            }
            if (row[6] != null) {
                obj.setStudent(row[6].toString());
            }
            if (row[7] != null) {
                obj.setDescription(row[7].toString());
            }
            if (row[8] != null) {
                obj.setWebsite(row[8].toString());
            }
            try {
                if (row[9] != null) {
                    Date createdOn = formatter.parse(row[9].toString());
                    obj.setCreatedOn(createdOn);
                }
                if (row[10] != null) {
                    Date updatedon = formatter.parse(row[10].toString());
                    obj.setUpdatedOn(updatedon);
                    Date updatedDate = (Date) row[10];
                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                    String dateResult = formatter1.format(updatedDate);
                    obj.setLastUpdated(dateResult);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (row[11] != null) {
                obj.setCreatedBy(row[11].toString());
            }
            if (row[12] != null) {
                obj.setUpdatedBy(row[12].toString());
            }
            if (row[13] != null) {
                obj.setScholarshipTitle(row[13].toString());
            }
            if (row[14] != null) {
                obj.setOfferedByInstitute(new BigInteger(row[14].toString()));
            }
            if (row[15] != null) {
                obj.setBenefits(row[15].toString());
            }
            if (row[16] != null) {
                obj.setRequirements(row[16].toString());
            }
            if (row[17] != null) {
                obj.setEligibility(row[17].toString());
            }
            if (row[18] != null) {
                obj.setIntake(row[18].toString());
            }
            if (row[19] != null) {
                obj.setLanguage(row[19].toString());
            }
            if (row[20] != null) {
                obj.setValidity(row[20].toString());
            }
            if (row[21] != null) {
                obj.setGender(row[21].toString());
            }
            if (row[22] != null) {
                obj.setApplicationDeadline(row[22].toString());
            }
            if (row[23] != null) {
                obj.setScholarshipAmount(Double.parseDouble(row[23].toString()));
            }
            if (row[24] != null) {
                obj.setNumberOfAvaliability(Integer.parseInt(row[24].toString()));
            }
            if (row[25] != null) {
                obj.setHeadquaters(row[25].toString());
            }
            if (row[26] != null) {
                obj.setEmail(row[26].toString());
            }
            if (row[27] != null) {
                obj.setAddress(row[27].toString());
            }
            scholarshipList.add(obj);
            if (row[28] != null) {
                obj.setIsActive(Boolean.parseBoolean(row[28].toString()));
            }
            scholarshipList.add(obj);
            if (row[29] != null) {
                obj.setIsDeleted(Boolean.parseBoolean(row[29].toString()));
            }
            if (row[30] != null) {
                obj.setOfferedByCourse(new BigInteger(row[30].toString()));
            }
            if (obj.getOfferedByCourse() != null) {
                obj.setOfferByCourseName(getCourseName(obj.getOfferedByCourse()));
            }
            if (obj.getOfferedByInstitute() != null) {
                obj.setOfferedByInstituteName(getInstituteName(obj.getOfferedByInstitute()));
            }
            scholarshipList.add(obj);
        }
        return scholarshipList;
    }

    private String addSchoolarFilter(String sqlQuery, ScholarshipFilterDto scholarshipFilterDto) {
        if (null != scholarshipFilterDto.getCountryId() && scholarshipFilterDto.getCountryId().intValue() > 0) {
            sqlQuery += " and sch.country_id = " + scholarshipFilterDto.getCountryId() + " ";
        }
        if (null != scholarshipFilterDto.getInstituteId() && scholarshipFilterDto.getInstituteId().intValue() > 0) {
            sqlQuery += " and sch.institute_id = " + scholarshipFilterDto.getInstituteId() + " ";
        }
        if (null != scholarshipFilterDto.getFacultyId() && scholarshipFilterDto.getFacultyId().intValue() > 0) {
        }
        if (null != scholarshipFilterDto.getLevelId() && scholarshipFilterDto.getLevelId().intValue() > 0) {
            sqlQuery += " and sch.level_id = " + scholarshipFilterDto.getLevelId() + " ";
        }
        if (null != scholarshipFilterDto.getCoverage() && !scholarshipFilterDto.getCoverage().isEmpty()) {
            sqlQuery += " and sch.coverage = '" + scholarshipFilterDto.getCoverage() + "' ";
        }
        if (null != scholarshipFilterDto.getType() && !scholarshipFilterDto.getType().isEmpty()) {
            sqlQuery += " and sch.type = '" + scholarshipFilterDto.getType() + "' ";
        }
        if (null != scholarshipFilterDto.getDatePosted() && !scholarshipFilterDto.getDatePosted().isEmpty()) {
            Date datePosted = DateUtil.convertStringDateToDate(scholarshipFilterDto.getDatePosted());
            Calendar c = Calendar.getInstance();
            c.setTime(datePosted);
            c.add(Calendar.DATE, 1);
            Date datePostedUpdated = c.getTime();
            String oneDateAfterDate = DateUtil.getStringDateFromDate(datePostedUpdated);
            sqlQuery += " and sch.created_on >='" + scholarshipFilterDto.getDatePosted() + "' and sch.created_on < '" + oneDateAfterDate + "'";
        }
        return sqlQuery;
    }

    @Override
    public int findTotalCountOfSchoolarship(ScholarshipFilterDto scholarshipFilterDto) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted  FROM scholarship as sch "
                        + " where sch.is_deleted IS NULL ";

        sqlQuery = addSchoolarFilter(sqlQuery, scholarshipFilterDto);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    @Override
    public List<Scholarship> autoSearch(int pageNumber, Integer pageSize, String searchKey) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by_institution_id, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted ,sch.offer_by_course_id FROM scholarship as sch "
                        + " where sch.is_deleted IS NULL and (sch.name like '%" + searchKey + "%' or sch.description like '%" + searchKey + "%') ORDER BY sch.created_on DESC";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<Scholarship> scholarshipList = getScholarshipData(rows, session);
        return scholarshipList;
    }

    @Override
    public int findTotalCountOfScholarshipAutoSearch(String searchKey) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by_institution_id, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted ,sch.offer_by_course_id FROM scholarship as sch "
                        + " where sch.is_deleted IS NULL and (sch.name like '%" + searchKey + "%' or sch.description like '%" + searchKey + "%') ";
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

	@Override
	public List<BigInteger> getRandomScholarShipsForCountry(List<BigInteger> countryId, Integer limit) {

		Session session = sessionFactory.getCurrentSession();
		String query = "Select id from scholarship where country_id in (?) order by Rand() LIMIT ?";
		List<BigInteger> scholarshipIds = (List<BigInteger>)session.createNativeQuery(query).setParameter(1, countryId).setParameter(2, limit).getResultList();
		return scholarshipIds;
	}

	@Override
	public List<Scholarship> getAllScholarshipDetailsFromId(List<BigInteger> recommendedScholarships) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Scholarship.class,"scholarship");
		criteria.add(Restrictions.in("id", recommendedScholarships));
		return (List<Scholarship>)criteria.list();
	}

	@Override
	public List<BigInteger> getRandomScholarships(int i) {
		Session session = sessionFactory.getCurrentSession();
		String query = "Select id from scholarship order by Rand() LIMIT ?";
		List<BigInteger> scholarshipIds = (List<BigInteger>)session.createNativeQuery(query).setParameter(1, i).getResultList();
		return scholarshipIds;
	}
}
