package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
	public Page<Subject> findBySubjectNameContainingIgnoreCaseAndEducationSystemId(String name, String educationSutemId,
			Pageable pageable);
}
