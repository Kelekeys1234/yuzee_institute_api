package com.seeka.app.dao;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.bean.AgentEducationDetail;
import com.seeka.app.bean.AgentMediaDocumentation;
import com.seeka.app.bean.AgentServiceOffered;
import com.seeka.app.bean.EducationAgent;
import com.seeka.app.bean.EducationAgentSkill;
import com.seeka.app.bean.Skill;

@Repository
public class EducationAgentDAO implements IEducationAgentDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveEducationAgent(EducationAgent educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(educationAgent);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void updateEducationAgent(EducationAgent educationAgent) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.update(educationAgent);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    
    @Override
    public void saveSkill(Skill skill) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(skill);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveEducationAgentSkill(EducationAgentSkill agentSkill) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(agentSkill);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        
    }

    @Override
    public void saveAgentServiceOffered(AgentServiceOffered agentServiceOffered) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(agentServiceOffered);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveAgentEducationDetail(AgentEducationDetail agentEducationDetail) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(agentEducationDetail);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        
    }

    @Override
    public void saveAgentMediaDocumentation(AgentMediaDocumentation agentMediaDocumentation) {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.save(agentMediaDocumentation);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        
    }

    @Override
    public void deleteSkill(BigInteger educationAgentId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteEducationAgentSkill(BigInteger educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM education_agent_skill WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentServiceOffered(BigInteger educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_service_offered WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentEducationDetail(BigInteger educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_education_detail WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public void deleteAgentMediaDocumentation(BigInteger educationAgentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("DELETE FROM agent_media_documentation WHERE education_agent =" + educationAgentId);
        query.executeUpdate();
    }

    @Override
    public Skill fetchSkill(BigInteger id) {
            Session session = sessionFactory.getCurrentSession();
            Criteria crit = session.createCriteria(Skill.class, "skill");
            crit.add(Restrictions.eq("skill.id", id));
            return (Skill) crit.uniqueResult();
    }

}
