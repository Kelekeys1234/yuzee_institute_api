package com.seeka.app.service;

import java.util.Map;

import com.seeka.app.dto.GlobalFilterSearchDto;
import com.seeka.app.exception.CommonInvokeException;
import com.seeka.app.exception.ValidationException;

public interface IGlobalSearchFilterService {

	Map<String,Object> filterByEntity(GlobalFilterSearchDto globalSearchFilterDto) throws ValidationException, CommonInvokeException;
}
