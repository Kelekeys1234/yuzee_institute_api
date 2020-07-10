package com.yuzee.app.endpoint;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.EducationAgentDto;
import com.yuzee.app.dto.EducationAgentPartnershipsDto;
import com.yuzee.app.exception.NotFoundException;

@RequestMapping("/api/v1/educationAgent")
public interface EducationAgentInterface {

	@PostMapping
	public ResponseEntity<?> saveEducationAgent(@RequestBody final EducationAgentDto educationAgentDto);

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEducationAgent(@PathVariable String id, @RequestBody final EducationAgentDto educationAgentDto);

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllEducationAgent(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception;

	@GetMapping("/{id}")
	public ResponseEntity<?> getEducationAgent(@PathVariable final String id) throws Exception;

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEducationAgent(@PathVariable String id) throws NotFoundException;

	@PostMapping("/partnership")
	public ResponseEntity<?> saveEducationAgentPartnership(@RequestBody final List<EducationAgentPartnershipsDto> agentPartnershipsDto) throws Exception;
}
