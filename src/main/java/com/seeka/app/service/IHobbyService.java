package com.seeka.app.service;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.Hobbies;

public interface IHobbyService {
	public void save(Hobbies hobbiesObj);
	public void update(Hobbies hobbiesObj);
	public Hobbies get(BigInteger id);
	public List<Hobbies> searchByHobbies(String hobbyTxt);
	
}

