package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Subject;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {
	Page<Subject> findByNameContainingIgnoreCaseAndEducationSystemId(String name, String educationSutemId,
			Pageable pageable);

	Subject findByNameAndEducationSystemId(String name, String educationSystemId);
}
