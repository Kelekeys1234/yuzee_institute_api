package com.yuzee.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.GlobalFilterSearchDto;
import com.yuzee.app.service.IGlobalSearchFilterService;
import com.yuzee.common.lib.dto.PaginationUtilDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.PaginationUtil;

@RestController("globalSearchFilterControllerV1")
@RequestMapping("/api/v1/globalSearch")
public class GlobalSearchFilterController {

	@Autowired
	private IGlobalSearchFilterService globalSearchFilterService;
	
	@PostMapping(value = "/filter")
	public ResponseEntity<?> filterEntityBasedOnParameters(@RequestBody GlobalFilterSearchDto globalFilterSearchDto) 
			throws ValidationException, NotFoundException, InvokeException{
		Map<String,Object> responseEntityMap = globalSearchFilterService.filterByEntity(globalFilterSearchDto);
		if(globalFilterSearchDto == null || globalFilterSearchDto.getIds() == null || globalFilterSearchDto.getIds().isEmpty()) {
			throw new ValidationException("No Courses specified to Filter");
		}
		List<?> responseList = (List<?>)responseEntityMap.get("entity");
		Integer count = (Integer)responseEntityMap.get("count");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(PaginationUtil.getStartIndex(globalFilterSearchDto.getPageNumber(), globalFilterSearchDto.getMaxSizePerPage()), globalFilterSearchDto.getMaxSizePerPage(), count.intValue());
		Map<String, Object> responseMap = new HashMap<>(4);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Course List Display Successfully");
		responseMap.put("data", responseList);
		responseMap.put("totalCount", count);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		
		return new GenericResponseHandlers.Builder().setData(responseMap).setMessage("List Display Successfully").setStatus(HttpStatus.OK).create();
	}
}
