package com.seeka.app.service;import com.seeka.app.bean.UserEducationDetails;

public interface IUserEducationDetailService {
	public void save(UserEducationDetails hobbiesObj);
	public void update(UserEducationDetails hobbiesObj);
	public UserEducationDetails get(String id);
	public UserEducationDetails getUserEducationDetails(String userId);
}

