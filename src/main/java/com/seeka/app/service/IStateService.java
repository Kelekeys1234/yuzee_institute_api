package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.State;

public interface IStateService {

	public List<State> getStateByCountryId(String countryId);
}
