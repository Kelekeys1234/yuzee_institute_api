package com.yuzee.app.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.repository.ServiceRepository;

@Component
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class ServiceDetailsDaoImpl implements ServiceDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ServiceRepository serviceRepository;
    
    @Override
    public void addUpdateServiceDetails(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public Optional<Service> getServiceById(String id) {
        return serviceRepository.findById(id);
    }

    @Override
    public Page<Service> getAllServices(Pageable pageable) {
    	return serviceRepository.findAll(pageable);
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
