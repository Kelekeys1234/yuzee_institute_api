package com.yuzee.app.service;

import java.util.Map;

import com.yuzee.app.dto.GlobalFilterSearchDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

public interface IGlobalSearchFilterService {

	Map<String,Object> filterByEntity(GlobalFilterSearchDto globalSearchFilterDto) throws ValidationException, NotFoundException, InvokeException;
}
