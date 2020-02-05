package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.AccreditedInstituteDetail;

@Repository
public class AccreditedInstituteDetailDao implements IAccreditedInstituteDetailDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addAccreditedInstituteDetail(final AccreditedInstituteDetail accreditedInstituteDetail) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(accreditedInstituteDetail);
    }

    @Override
    public List<AccreditedInstituteDetail> getAccreditedInstituteDetailList(final BigInteger entityId, final String entityType, final Integer startIndex, final Integer pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AccreditedInstituteDetail.class, "accreditedInstituteDetail");
        crit.add(Restrictions.eq("accreditedInstituteDetail.entityId", entityId));
        crit.add(Restrictions.eq("accreditedInstituteDetail.entityType", entityType));
        return crit.list();
    }

    @Override
    public List<AccreditedInstituteDetail> getAccreditedInstituteDetail(final String accreditedInstituteId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AccreditedInstituteDetail.class, "accreditedInstituteDetail");
        crit.add(Restrictions.eq("accreditedInstituteDetail.accreditedInstituteId", accreditedInstituteId));
        return crit.list();
    }

    @Override
    public AccreditedInstituteDetail getAccreditedInstituteDetailbasedOnParams(final String accreditedInstituteId, final String entityId, final String entityType) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AccreditedInstituteDetail.class, "accreditedInstituteDetail");
        crit.add(Restrictions.eq("accreditedInstituteDetail.entityId", entityId));
        crit.add(Restrictions.eq("accreditedInstituteDetail.entityType", entityType));
        crit.add(Restrictions.eq("accreditedInstituteDetail.accreditedInstituteId", accreditedInstituteId));
        return (AccreditedInstituteDetail) crit.uniqueResult();
    }

    @Override
    public void deleteAccreditedInstitueDetailByEntityId(String entityId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("delete from AccreditedInstituteDetail where entity_id =" + entityId);
        q.executeUpdate();

    }

    @Override
    public List<String> getAccreditation(@Valid String id) {
        List<String> list = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(AccreditedInstituteDetail.class);
        crit.add(Restrictions.eq("entityId", id));
        crit.add(Restrictions.eq("entityType", "INSTITUTE"));
        List<AccreditedInstituteDetail> accreditedInstituteDetails = crit.list();
        for (AccreditedInstituteDetail bean : accreditedInstituteDetails) {
            list.add(bean.getAccreditedInstituteId());
        }
        return list;
    }

}
