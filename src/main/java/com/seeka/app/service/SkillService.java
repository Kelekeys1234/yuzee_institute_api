package com.seeka.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.seeka.app.bean.Institute;
import com.seeka.app.dao.ISkillDao;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.SkillDto;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
public class SkillService implements ISkillService {

	@Autowired
	private ISkillDao iSkillDao;

	/*
	 * @Override public List<SkillDto> autoSearch(String searchKey, Integer
	 * pageNumber, Integer pageSize) { // Map<String, Object> response = new
	 * HashMap<>(); List<SkillDto> skills = new ArrayList<>(); // try { skills =
	 * iSkillDao.autoSearch(pageNumber, pageSize, searchKey);
	 * 
	 * if (skills != null && !skills.isEmpty()) { response.put("status",
	 * HttpStatus.OK.value()); response.put("message",
	 * "Skills fetched successfully"); response.put("skills", skills); } else {
	 * response.put("status", HttpStatus.NOT_FOUND.value()); response.put("message",
	 * "Skills not found"); }
	 * 
	 * // } catch (Exception exception) { // response.put("message",
	 * exception.getCause()); // response.put("status",
	 * HttpStatus.INTERNAL_SERVER_ERROR.value()); // } return skills; }
	 */

	@Override
	public Map<String, Object> autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		Map<String, Object> response = new HashMap<>();
		List<SkillDto> skills = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = iSkillDao.getAllSkillNamesCount(searchKey);
			int startIndex = (pageNumber - 1) * pageSize;
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			List<SkillDto> skillDtos = iSkillDao.autoSearch(startIndex, pageSize, searchKey);
			for (SkillDto skillDto : skillDtos) {
				skills.add(getSkill(skillDto));
			}
			if (skillDtos != null && !skillDtos.isEmpty()) {
				response.put("message", "Skills fetched successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", "Skills not found");
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("data", skills);
		response.put("totalCount", totalCount);
		response.put("pageNumber", paginationUtilDto.getPageNumber());
		response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		response.put("hasNextPage", paginationUtilDto.isHasNextPage());
		response.put("totalPages", paginationUtilDto.getTotalPages());
		return response;
	}

	@Override
	public Integer getAllSkillNamesCount(String searchString) {
		return iSkillDao.getAllSkillNamesCount(searchString);
	}

	private SkillDto getSkill(final SkillDto skillDto) {
		SkillDto dto = new SkillDto();
		dto.setId(skillDto.getId());
		dto.setSkillName(skillDto.getSkillName());
		dto.setCreatedBy(skillDto.getCreatedBy());
		dto.setUpdatedBy(skillDto.getUpdatedBy());
		return dto;
	}
}
