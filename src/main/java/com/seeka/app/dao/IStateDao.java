package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.State;

public interface IStateDao {

	public List<State> getAll();

	public List<State> getStateByCountryId(String countryId);

	public State get(String id);

	void save(State obj);

	void update(State obj);
}
