package com.seeka.app.service;import java.math.BigInteger;




import com.seeka.app.bean.UserEducationDetails;

public interface IUserEducationDetailService {
	public void save(UserEducationDetails hobbiesObj);
	public void update(UserEducationDetails hobbiesObj);
	public UserEducationDetails get(BigInteger id);
	public UserEducationDetails getUserEducationDetails(BigInteger userId);
}

