package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.State;

public interface IStateDao {

	public List<State> getStateByCountryId(String countryId);
}
