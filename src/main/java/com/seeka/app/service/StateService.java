package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.State;
import com.seeka.app.dao.IStateDao;

@Service
public class StateService implements IStateService {

	@Autowired
	private IStateDao iStateDao;
	
	@Override
	public List<State> getStateByCountryId(String countryId) {
		List<State> states = iStateDao.getStateByCountryId(countryId);
		return states;
	}

}
