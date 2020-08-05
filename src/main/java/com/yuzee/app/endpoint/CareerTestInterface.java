package com.yuzee.app.endpoint;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/career/job")
public interface CareerTestInterface {

	@GetMapping("/skill/{levelId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobSkills(@PathVariable String levelId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@PostMapping("/working/style/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobWorkingStyles(@RequestBody List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@PostMapping("/subject/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobSubjects(@RequestBody List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@PostMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getCareerJobs(@RequestBody List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
	
	@PostMapping("/related/course/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getRelatedCourseBasedOnCareerTest(@RequestBody List<String> jobIds, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize);
}
