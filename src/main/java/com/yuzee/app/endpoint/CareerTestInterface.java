package com.yuzee.app.endpoint;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/career/job")
public interface CareerTestInterface {

	@GetMapping("/skill/{levelId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobSkills(@PathVariable String levelId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@GetMapping("/working/style/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobWorkingStyles(@RequestParam List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@GetMapping("/subject/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobSubjects(@RequestParam List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@GetMapping("/type/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobTypes(@RequestParam List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobs(@RequestParam List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@GetMapping("/related/career/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getRelatedCourseBasedOnCareerTest(@RequestParam List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
}
