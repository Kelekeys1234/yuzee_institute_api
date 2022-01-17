package com.yuzee.app.repository.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.dto.CourseRequest;

public interface CourseDocumentRepository extends MongoRepository<CourseRequest, String> {
	Page<CourseRequest> findByNameContainingIgnoreCaseAndInstituteId(String name, String instituteId, Pageable pageable);
}
