package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobSubject;

@Repository
public interface CareerJobSubjectRepository extends JpaRepository<CareerJobSubject, String> {

	public List<CareerJobSubject> findByCareerJobsIdIn(List<String> jobIds, @PageableDefault Pageable pageable);
	
	public long countByCareerJobsIdIn(List<String> jobIds);
}
