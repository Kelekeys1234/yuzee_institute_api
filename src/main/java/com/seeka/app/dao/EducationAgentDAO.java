package com.seeka.app.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentAccomplishment;
import com.seeka.app.bean.EducationAgentPartnerships;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.bean.Service;
import com.seeka.app.dto.EducationAgentGetAllDto;

@Repository
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class EducationAgentDAO implements IEducationAgentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveEducationAgent(EducationAgent educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(educationAgent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateEducationAgent(EducationAgent educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(educationAgent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveEducationAgentSkill(EducationAgentSkill agentSkill) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(agentSkill);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void saveAgentServiceOffered(AgentServiceOffered agentServiceOffered) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(agentServiceOffered);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveAgentEducationDetail(AgentEducationDetail agentEducationDetail) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(agentEducationDetail);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void saveAgentMediaDocumentation(AgentMediaDocumentation agentMediaDocumentation) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(agentMediaDocumentation);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void deleteSkill(String educationAgentId) {

    }

    @Override
    public void deleteEducationAgentSkill(String educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM education_agent_skill WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentServiceOffered(String educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_service_offered WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentEducationDetail(String educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_education_detail WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentMediaDocumentation(String educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_media_documentation WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public EducationAgent get(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(EducationAgent.class, id);
    }

    @Override
    public int findTotalCount() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select ea.id from education_agent ea where ea.deleted_on IS NULL";
        System.out.println(sqlQuery);
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        return rows.size();
    }

    @Override
    public List<EducationAgentGetAllDto> getAll(int pageNumber, Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "select inst.id, inst.first_name , inst.description , inst.city_name, inst.country_name, inst.email , inst.phone_number, inst.updated_on FROM education_agent as inst where inst.deleted_on IS NULL ORDER BY inst.created_on DESC";
        sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
        Query query = session.createSQLQuery(sqlQuery);
        List<Object[]> rows = query.list();
        List<EducationAgentGetAllDto> instituteList = getEducationAgentDetails(rows, session);
        return instituteList;
    }

    private List<EducationAgentGetAllDto> getEducationAgentDetails(List<Object[]> rows, Session session) {
        List<EducationAgentGetAllDto> educationAgentGetAllDtos = new ArrayList<>();
        EducationAgentGetAllDto educationAgent = null;
        for (Object[] row : rows) {
            educationAgent = new EducationAgentGetAllDto();
            educationAgent.setId(row[0].toString());
            educationAgent.setAgentName(row[1].toString());
            educationAgent.setDescription(row[2].toString());
            if (row[3] != null) {
                educationAgent.setCity(row[3].toString());
            }
            if (row[4] != null) {
                educationAgent.setCountry(row[4].toString());
            }
            String contact = null;
            if (row[6] != null) {
                contact = row[6].toString();
            }
            if (row[5] != null) {
                if (contact != null) {
                    contact = contact + ", " + row[5].toString();
                } else {
                    contact = row[5].toString();
                }
            }
            educationAgent.setContact(contact);
            educationAgent.setStatus("ACTIVE");
            if (row[7] != null) {
                Date updatedDate = (Date) row[7];
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                String dateResult = formatter.format(updatedDate);
                educationAgent.setLastUpdated(dateResult);
            }
            educationAgentGetAllDtos.add(educationAgent);
        }
        return educationAgentGetAllDtos;
    }

//    private String getCountry(String id) {
//        String name = null;
//        if (id != null) {
//            Session session = sessionFactory.getCurrentSession();
//            Country country = session.get(Country.class, id);
//            if (country != null) {
//                name = country.getName();
//            }
//        }
//        return name;
//    }

//    private String getCity(String id) {
//        String name = null;
//        if (id != null) {
//            Session session = sessionFactory.getCurrentSession();
//            City city = session.get(City.class, id);
//            if (city != null) {
//                name = city.getName();
//            }
//        }
//        return name;
//    }

    @Override
    public EducationAgent fetchEducationAgent(String id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(EducationAgent.class, "skill");
        crit.add(Restrictions.eq("EducationAgent.id", id));
        return (EducationAgent) crit.uniqueResult();
    }

    @Override
    public List<EducationAgentSkill> fetchEducationAgentSkillByEducationAgentId(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(EducationAgentSkill.class);
        List<EducationAgentSkill> agentSkills = crit.add(Restrictions.eq("educationAgent.id", educationAgent)).list();
        return agentSkills;
    }

    @Override
    public List<AgentServiceOffered> fetchAgentServiceOffered(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        List<AgentServiceOffered> agentServiceOffereds = new ArrayList<>();
        List<Object[]> agentService = session
                        .createSQLQuery("SELECT aso.amount, aso.service, aso.currency_id FROM agent_service_offered as aso WHERE education_agent =" + educationAgent + "").list();
        for (Object[] obj : agentService) {
            AgentServiceOffered agentServiceOffered = new AgentServiceOffered();
            Service service = new Service();
            agentServiceOffered.setAmount(Double.parseDouble(obj[0].toString()));
            service.setId(obj[1].toString());
            agentServiceOffered.setService(service);
            agentServiceOffered.setCurrency(obj[2].toString());
            agentServiceOffereds.add(agentServiceOffered);
        }
        return agentServiceOffereds;
    }

    @Override
    public List<AgentEducationDetail> fetchAgentEducationDetail(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AgentEducationDetail.class);
        List<AgentEducationDetail> agentEducationDetails = crit.add(Restrictions.eq("educationAgent.id", educationAgent)).list();
        return agentEducationDetails;
    }

    @Override
    public List<AgentMediaDocumentation> fetchAgentMediaDocumentation(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AgentMediaDocumentation.class);
        List<AgentMediaDocumentation> agentMediaDocumentations = crit.add(Restrictions.eq("educationAgent.id", educationAgent)).list();
        return agentMediaDocumentations;
    }

    @Override
    public void saveEducationAgentPartnerships(EducationAgentPartnerships educationAgentPartnerships) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(educationAgentPartnerships);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteEducationAgent(String id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM education_agent WHERE id =" + id);
        query.executeUpdate();
    }

    @Override
    public void deleteEducationAgentPartnerships(String educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM education_agent_partnerships WHERE education_agent =" + educationAgentId);
        query.executeUpdate();

    }

    @Override
    public void saveEducationAgentAccomplishment(EducationAgentAccomplishment educationAgentAccomplishment) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(educationAgentAccomplishment);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteEducationAgentAccomplishment(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM education_agent_accomplishment WHERE education_agent =" + educationAgent);
        query.executeUpdate();
    }

    @Override
    public List<EducationAgentAccomplishment> fetcheducationAgentAccomplishment(String educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(EducationAgentAccomplishment.class);
        List<EducationAgentAccomplishment> agentAccomplishments = crit.add(Restrictions.eq("educationAgent.id", educationAgent)).list();
        return agentAccomplishments;
    }
}
