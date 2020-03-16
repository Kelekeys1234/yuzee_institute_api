package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.SkillDto;
import com.seeka.app.service.IEducationAgentService;
import com.seeka.app.service.ISkillService;
import com.seeka.app.util.PaginationUtil;

@RestController("skillControllerV1")
@RequestMapping("/api/v1/skill")
@Transactional
public class SkillController {

	@Autowired
	private IEducationAgentService educationService;

	@Autowired
	private ISkillService iSkillService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllSkill() throws Exception {
		return ResponseEntity.accepted().body(educationService.getAllSkill());
	}

	@RequestMapping(value = "/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDistinctSkills(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String skillName)
			throws Exception {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = iSkillService.getDistinctSkillsCount(skillName);
		List<SkillDto> couserList = iSkillService.getDistinctSkillsList(startIndex, pageSize, skillName);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Skills List Successfully");
		responseMap.put("data", couserList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
}
