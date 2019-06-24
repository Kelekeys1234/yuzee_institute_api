package com.seeka.app.service;import java.math.BigInteger;




import com.seeka.app.bean.UserInfo;
import com.seeka.app.enumeration.SignUpType;

public interface IUserService {
	public void save(UserInfo user);
	public void update(UserInfo user);
	public UserInfo get(BigInteger userId);
	public UserInfo getUserByEmail(String email);
	public UserInfo getUserBySocialAccountId(String accountId, SignUpType signUpType);
}

