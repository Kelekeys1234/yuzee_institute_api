package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseAdditionalInfo;

@Repository
public interface CourseAdditionalInfoRepository extends JpaRepository<CourseAdditionalInfo, String>{

	public List<CourseAdditionalInfo> findByCourseId(String courseId);
}
