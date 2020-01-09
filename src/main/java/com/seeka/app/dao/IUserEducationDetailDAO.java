package com.seeka.app.dao;import java.math.BigInteger;




import com.seeka.app.bean.UserEducationDetails;

public interface IUserEducationDetailDAO {
	public void save(UserEducationDetails hobbiesObj);
	public void update(UserEducationDetails hobbiesObj);
	public UserEducationDetails get(BigInteger id);
	public UserEducationDetails getUserEducationDetails(BigInteger userId);
	
}
