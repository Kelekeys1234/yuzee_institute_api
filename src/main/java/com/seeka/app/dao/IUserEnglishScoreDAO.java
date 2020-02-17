package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.UserEnglishScore;

public interface IUserEnglishScoreDAO {
	public List<UserEnglishScore> getAll();
	public UserEnglishScore get(String id);
	public void save(UserEnglishScore obj);
	public void update(UserEnglishScore obj);
	public List<UserEnglishScore> getEnglishEligibiltyByUserID(String userId);
}
