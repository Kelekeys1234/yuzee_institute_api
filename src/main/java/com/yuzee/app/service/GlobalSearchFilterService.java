package com.yuzee.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.GlobalFilterSearchDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntityType;
import com.yuzee.app.enumeration.ImageCategory;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.processor.StorageProcessor;

@Service
@Transactional(rollbackFor = Throwable.class)
public class GlobalSearchFilterService implements IGlobalSearchFilterService {

	@Autowired
	private CourseDao icourseDao;

	@Autowired
	private StorageProcessor iStorageService;

	@Override
	public Map<String, Object> filterByEntity(GlobalFilterSearchDto globalSearchFilterDto) throws ValidationException, CommonInvokeException {
		if (EntityType.COURSE.equals(globalSearchFilterDto.getEntityType())) {
			AdvanceSearchDto advanceSearchDto = new AdvanceSearchDto();
			BeanUtils.copyProperties(globalSearchFilterDto, advanceSearchDto);
			return filterCoursesByParameters(globalSearchFilterDto, advanceSearchDto);
		} else {
			throw new ValidationException("Illegal Entity Type");
		}
	}

	private Map<String, Object> filterCoursesByParameters(GlobalFilterSearchDto globalSearchFilterDto, AdvanceSearchDto advacneSearchDto)
			throws ValidationException, CommonInvokeException {

		List<CourseResponseDto> courseList = icourseDao.advanceSearch(null, advacneSearchDto, globalSearchFilterDto);
		for (CourseResponseDto courseResponseDto : courseList) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseResponseDto.getInstituteId(), ImageCategory.INSTITUTE.toString(), null, "en");
			courseResponseDto.setStorageList(storageDTOList);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("entity", courseList);
		returnMap.put("count", courseList.get(0).getTotalCount());
		return returnMap;
	}
}
