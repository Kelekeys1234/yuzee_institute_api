package com.seeka.app.service;import java.math.BigInteger;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserEducationDetails;
import com.seeka.app.dao.IUserEducationDetailDAO;

@Service
@Transactional
public class UserEducationDetailService implements IUserEducationDetailService {

	@Autowired
	private IUserEducationDetailDAO iUserEducationDetailDAO;

	@Override
	public void save(UserEducationDetails hobbiesObj) {
		iUserEducationDetailDAO.save(hobbiesObj);
	}
	
	@Override
	public void update(UserEducationDetails hobbiesObj) {
		iUserEducationDetailDAO.update(hobbiesObj);
	}
	
	@Override
	public UserEducationDetails getUserEducationDetails(BigInteger userId) {
		return iUserEducationDetailDAO.getUserEducationDetails(userId);
	}
	
	@Override
	public UserEducationDetails get(BigInteger id) {
		return iUserEducationDetailDAO.get(id);
	}
	
}
