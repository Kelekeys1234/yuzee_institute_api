package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.State;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.StateDto;
import com.seeka.app.service.IStateService;
import com.seeka.app.util.IConstant;

@RestController("stateControllerV1")
@RequestMapping("/api/v1/state")
public class StateController {

	@Autowired
	private IStateService iStateService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<State> stateList = null;
		try {
			stateList = iStateService.getAll();
			if ((stateList != null) && !stateList.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.STATE_GET_SUCCESS);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.STATE_NOT_FOUND);
			}
		} catch (Exception exception) {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", exception.getCause());
		}
		response.put("data", stateList);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllStatesByCountry(@PathVariable final String countryId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<State> stateList = null;
		try {
			stateList = iStateService.getStateByCountryId(countryId);
			if ((stateList != null) && !stateList.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.STATE_GET_SUCCESS);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.STATE_NOT_FOUND);
			}
		} catch (Exception exception) {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", exception.getCause());
		}
		response.put("data", stateList);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable final String id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		State state = null;
		try {
			state = iStateService.get(id);
			if (state != null) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", IConstant.STATE_GET_SUCCESS);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", IConstant.STATE_NOT_FOUND);
			}
		} catch (Exception exception) {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", exception.getCause());
		}
		response.put("data", state);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveState(@RequestBody final StateDto state) throws Exception {
		return ResponseEntity.accepted().body(iStateService.save(state));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> update(@PathVariable final String id, @RequestBody final StateDto stateDto)
			throws Exception {
		State state = iStateService.update(id, stateDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(state)
				.setMessage("State Updated Successfully").create();
	}
}
