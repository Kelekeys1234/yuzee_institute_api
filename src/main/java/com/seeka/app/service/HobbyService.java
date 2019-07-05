package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Hobbies;
import com.seeka.app.dao.IHobbyDAO;

@Service
@Transactional
public class HobbyService implements IHobbyService {

	@Autowired
	private IHobbyDAO dao;

	@Override
	public void save(Hobbies hobbiesObj) {
		dao.save(hobbiesObj);
	}
	
	@Override
	public void update(Hobbies hobbiesObj) {
		dao.update(hobbiesObj);
	}
	
	@Override
	public List<Hobbies> searchByHobbies(String hobbyTxt) {
		return dao.searchByHobbies(hobbyTxt);
	}
	
	@Override
	public Hobbies get(BigInteger id) {
		return dao.get(id);
	}
	
}
