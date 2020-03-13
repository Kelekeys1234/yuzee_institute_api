package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.State;
import com.seeka.app.dto.StateDto;

public interface IStateService {

	List<State> getAll();

	public List<State> getStateByCountryId(String countryId);

	State get(String id);

	Map<String, Object> save(StateDto state);

	State update(String id, StateDto state);
}
