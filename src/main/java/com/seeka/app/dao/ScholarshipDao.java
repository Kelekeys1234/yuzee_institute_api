package com.seeka.app.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.SeekaArticles;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.ScholarshipDto;

@Repository
@SuppressWarnings("unchecked")
public class ScholarshipDao implements IScholarshipDAO{
	
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
	        String sqlQuery = "select sch.id, sch.country_id , sch.institute_id, sch.level_id, sch.name ,sch.amount, sch.student, sch.description, sch.website, sch.created_on, sch.updated_on, sch.created_by, sch.updated_by, sch.scholarship_title, sch.offered_by, sch.benefits, sch.requirements, sch.eligibility, sch.intake, sch.language, sch.validity, sch.gender, sch.application_deadline, sch.scholarship_amount, sch.number_of_avaliability, sch.headquaters, sch.email, sch.address, sch.is_active, sch.is_deleted  FROM scholarship as sch where sch.is_active = 1 and sch.is_deleted IS NULL ORDER BY sch.created_on DESC";
	        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
	        Query query = session.createSQLQuery(sqlQuery);
	        List<Object[]> rows = query.list();
	        List<Scholarship> scholarshipList = new ArrayList<Scholarship>();
	        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");  
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
	            	if (row[9] != null) { Date createdOn = formatter.parse(row[9].toString()); 
	            		obj.setCreatedOn(createdOn);
	            	}
					if (row[10] != null) {Date updatedon = formatter.parse(row[10].toString()); 
						obj.setUpdatedOn(updatedon);
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
	            	 obj.setOfferedBy(row[14].toString());
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
	            scholarshipList.add(obj);
	        }
	        return scholarshipList;
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
        	scholarship.setName(scholarshipdto.getName());
        	scholarship.setAmount(scholarshipdto.getAmount());
        	scholarship.setDescription(scholarshipdto.getDescription());
        	scholarship.setStudent(scholarshipdto.getStudent());
        	scholarship.setWebsite(scholarshipdto.getWebsite());
        	scholarship.setScholarshipTitle(scholarshipdto.getScholarshipTitle());
        	scholarship.setOfferedBy(scholarshipdto.getOfferedBy());
        	scholarship.setBenefits(scholarshipdto.getBenefits());
        	scholarship.setRequirements(scholarshipdto.getRequirements());
        	scholarship.setEligibility(scholarshipdto.getEligibility());
        	scholarship.setIntake(scholarshipdto.getIntake());
        	scholarship.setLanguage(scholarshipdto.getLanguage());
        	scholarship.setValidity(scholarshipdto.getValidity());
        	scholarship.setGender(scholarshipdto.getGender());
        	scholarship.setApplicationDeadline(scholarshipdto.getApplicationDeadline());
        	scholarship.setScholarshipAmount(scholarshipdto.getScholarshipAmount());
        	scholarship.setNumberOfAvaliability(scholarshipdto.getNumberOfAvaliability());
        	scholarship.setHeadquaters(scholarshipdto.getHeadquaters());
        	scholarship.setEmail(scholarshipdto.getEmail());
        	scholarship.setAddress(scholarship.getAddress());
        	scholarship.setIsActive(scholarshipdto.getIsActive());
        	scholarship.setIsDeleted(scholarship.getIsDeleted());
        	scholarship.setUpdatedOn(new Date());
        	session.update(scholarship);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
    public List<ScholarshipDto> getScholarshipBySearchKey(String searchKey) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select shc.id, shc.name, c.name as countryName, i.name as instituteName, l.name as levelName from scholarship shc  inner join country c  on c.id = shc.country_id "
                        + "inner join institute i  on i.id = i.institute_id"
                        +"inner join level l  on l.id = l.level_id where shc.name like '%" + searchKey + "%'");
        List<Object[]> rows = query.list();
        List<ScholarshipDto> scholarshipList = new ArrayList<ScholarshipDto>();
        ScholarshipDto obj = null;
        for (Object[] row : rows) {
            obj = new ScholarshipDto();
            //obj.setInstituteId(new BigInteger((row[0].toString())));
            //obj.setInstituteName(row[1].toString());
            //obj.setLocation(row[2].toString() + ", " + row[3].toString());
            scholarshipList.add(obj);
        }
        return scholarshipList;
    }


}
