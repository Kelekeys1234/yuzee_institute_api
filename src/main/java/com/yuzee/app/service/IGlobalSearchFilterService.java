package com.yuzee.app.service;

import java.util.Map;

import com.yuzee.app.dto.GlobalFilterSearchDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ValidationException;

public interface IGlobalSearchFilterService {

	Map<String,Object> filterByEntity(GlobalFilterSearchDto globalSearchFilterDto) throws ValidationException, CommonInvokeException;
}
