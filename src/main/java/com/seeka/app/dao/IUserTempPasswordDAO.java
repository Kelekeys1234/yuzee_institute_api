package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserTempPassword;

public interface IUserTempPasswordDAO {
	public void save(UserTempPassword UserTempPassword);
	public void update(UserTempPassword UserTempPassword);
	public UserTempPassword get(BigInteger userId);
	public UserTempPassword getUserByEmail(String email);
	public List<UserTempPassword> getActiveTempPasswordUserId(BigInteger userId);
}
