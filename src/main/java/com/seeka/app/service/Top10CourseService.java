package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Top10Course;
import com.seeka.app.dao.ITop10CourseDAO;

@Service
@Transactional(rollbackFor = Throwable.class)
public class Top10CourseService implements ITop10CourseService {

	@Autowired
	private ITop10CourseDAO iTop10CourseDao;

	@Autowired
	private IFacultyService iFacultyService;

	@Override
	public void saveTop10Courses(final Top10Course top10Course) {
		top10Course.setUpdatedBy("API");
		top10Course.setCreatedBy("API");
		top10Course.setCreatedOn(new Date());
		top10Course.setUpdatedOn(new Date());
		iTop10CourseDao.save(top10Course);
	}

	@Override
	public void deleteAllTop10Courses() {
		iTop10CourseDao.deleteAll();
	}

	@Override
	public List<String> getAllDistinctFaculty() {
		return iTop10CourseDao.getAllDistinctFaculty();
	}

	@Override
	public List<String> getTop10CourseKeyword(final BigInteger facultyId) {
		Faculty faculty = iFacultyService.get(facultyId);
		return iTop10CourseDao.getTop10CourseKeyword(faculty.getName()).stream().map(Top10Course::getCourse).collect(Collectors.toList());
	}
}
