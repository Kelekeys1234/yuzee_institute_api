package com.seeka.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.State;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IStateDao;
import com.seeka.app.dto.StateDto;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class StateService implements IStateService {

	@Autowired
	private IStateDao iStateDao;

	@Autowired
	private ICountryDAO iCountryDAO;

	@Override
	public List<State> getAll() {
		return iStateDao.getAll();
	}

	@Override
	public List<State> getStateByCountryId(String countryId) {
		List<State> states = iStateDao.getStateByCountryId(countryId);
		return states;
	}

	@Override
	public State get(String id) {
		return iStateDao.get(id);
	}

	@Override
	public Map<String, Object> save(StateDto stateObj) {
		Map<String, Object> response = new HashMap<String, Object>();
		boolean status = true;
		try {
			Country country = null;
			if (stateObj.getCountry() != null) {
				country = iCountryDAO.get(stateObj.getCountry().getId());
			}
			State state = CommonUtil.convertStateDTOToBean(stateObj, country);
			iStateDao.save(state);
		} catch (Exception exception) {
			status = false;
			exception.printStackTrace();
		}
		if (status) {
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.STATE_SUCCESS_MESSAGE);
		} else {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		return response;
	}

	@Override
	public State update(String id, StateDto stateObj) {
		State state = iStateDao.get(id);
		if (!ObjectUtils.isEmpty(state)) {
			state.setId(id);
			Country country = new Country();
			country.setId(stateObj.getCountry().getId());
			country.setName(stateObj.getCountry().getName());
			state.setCountry(country);
			state.setName(stateObj.getName());
			state.setCreatedBy(stateObj.getCreatedBy());
			state.setUpdatedBy(stateObj.getUpdatedBy());
			state.setUpdatedDate(new Date());
			iStateDao.update(state);
		}
		return state;
	}
}
