package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.Hobbies;

public interface IHobbyDAO {
	public void save(Hobbies hobbiesObj);
	public void update(Hobbies hobbiesObj);
	public Hobbies get(BigInteger id);
	public List<Hobbies> searchByHobbies(String hobbyTxt);
	
}
