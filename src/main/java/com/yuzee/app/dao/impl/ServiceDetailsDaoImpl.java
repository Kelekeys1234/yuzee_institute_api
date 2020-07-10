package com.yuzee.app.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDetailsDao;

@Component
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class ServiceDetailsDaoImpl implements ServiceDetailsDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Service obj) {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void update(Service obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public Service get(String id) {
        Session session = sessionFactory.getCurrentSession();
        Service obj = session.get(Service.class, id);
        return obj;
    }

    @Override
    public List<Service> getAllInstituteByCountry(String countryId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Service.class);
        crit.add(Restrictions.eq("countryObj.id", countryId));
        return crit.list();
    }

    @Override
    public List<Service> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Service.class).addOrder(Order.asc("name"));
        return crit.list();
    }

    public List<String> getServices(String id) {
        List<String> list = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select s.id, s.name as name, s.description from service s");
        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            list.add(row[0].toString());
        }
        return list;
    }

    public Service getServiceById(String id) {
        Service service = new Service();
        service.setId(id);
        return service;
    }

    @Override
    public List<String> getServicesById(String id) {
        List<String> list = new ArrayList<String>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select s.id, s.service_name from institute_service s where s.institute_id='" + id +"'");
        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            list.add(new String(row[1].toString()));
        }
        return list;
    }
    
    @Override
    public List<String> getServiceNameByInstituteId(String id) {
        List<String> list = new ArrayList<String>();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("select s.id, s.`name` from service s inner join institute_service i on s.name = i.service_name where i.institute_id='"+id+"'");
        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            list.add(new String(row[1].toString()));
        }
        return list;
    }

}
