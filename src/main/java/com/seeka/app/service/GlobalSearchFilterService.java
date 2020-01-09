package com.seeka.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.GlobalFilterSearchDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.enumeration.SeekaEntityType;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class GlobalSearchFilterService implements IGlobalSearchFilterService {

	@Autowired
	private ICourseDAO icourseDao;

	@Autowired
	private IStorageService iStorageService;

	@Override
	public Map<String, Object> filterByEntity(GlobalFilterSearchDto globalSearchFilterDto) throws ValidationException {
		if (SeekaEntityType.COURSE.equals(globalSearchFilterDto.getSeekaEntityType())) {
			AdvanceSearchDto advanceSearchDto = new AdvanceSearchDto();
			BeanUtils.copyProperties(globalSearchFilterDto, advanceSearchDto);
			return filterCoursesByParameters(globalSearchFilterDto, advanceSearchDto);
		} else {
			throw new ValidationException("Illegal Entity Type");
		}
	}

	private Map<String, Object> filterCoursesByParameters(GlobalFilterSearchDto globalSearchFilterDto, AdvanceSearchDto advacneSearchDto)
			throws ValidationException {

		List<CourseResponseDto> courseList = icourseDao.advanceSearch(advacneSearchDto, globalSearchFilterDto);
		for (CourseResponseDto courseResponseDto : courseList) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(courseResponseDto.getInstituteId(), ImageCategory.INSTITUTE.toString(),
					null, "en");
			courseResponseDto.setStorageList(storageDTOList);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("entity", courseList);
		returnMap.put("count", courseList.get(0).getTotalCount());
		return returnMap;
	}
}
