package com.seeka.app.service;import org.springframework.beans.factory.annotation.Autowired;
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
	public UserEducationDetails getUserEducationDetails(String userId) {
		return iUserEducationDetailDAO.getUserEducationDetails(userId);
	}
	
	@Override
	public UserEducationDetails get(String id) {
		return iUserEducationDetailDAO.get(id);
	}
	
}
