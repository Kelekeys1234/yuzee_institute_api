package com.seeka.app.dao;import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.UserDeviceInfo;

@Repository
public class UserDeviceInfoDAO implements IUserDeviceInfoDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void save(UserDeviceInfo userDeviceInfo) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(userDeviceInfo);	   					
	}
	
}
