package com.seeka.app.dao;import com.seeka.app.bean.UserEducationDetails;

public interface IUserEducationDetailDAO {
	public void save(UserEducationDetails hobbiesObj);
	public void update(UserEducationDetails hobbiesObj);
	public UserEducationDetails get(String id);
	public UserEducationDetails getUserEducationDetails(String userId);
	
}
