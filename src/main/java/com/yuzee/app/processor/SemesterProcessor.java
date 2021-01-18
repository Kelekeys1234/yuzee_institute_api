package com.yuzee.app.processor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Semester;
import com.yuzee.app.dao.SemesterDao;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.SemesterDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SemesterProcessor {

	@Autowired
	private SemesterDao semesterDao;

	@Autowired
	private ModelMapper modelMapper;

	public List<SemesterDto> saveAllSemesters(String userId, List<SemesterDto> semesterDtos)
			throws ValidationException {
		log.debug("inside SemesterProcessor.saveAllSemesters method");
		List<Semester> semesters = semesterDtos.stream().map(e -> modelMapper.map(e, Semester.class))
				.collect(Collectors.toList());
		semesters.stream().forEach(e -> e.setAuditFields(userId));
		return semesterDao.saveAll(semesters).stream().map(e -> modelMapper.map(e, SemesterDto.class))
				.collect(Collectors.toList());
	}

	public SemesterDto updateSemester(String userId, String semesterId, SemesterDto semesterDto)
			throws ValidationException, ForbiddenException {
		log.debug("inside SemesterProcessor.updateSemester method");
		Semester semester = getSemester(semesterId);
		validateAccess(userId, semester);
		semester.setName(semesterDto.getName());
		semester.setAuditFields(userId);
		return modelMapper.map(semesterDao.save(semester), SemesterDto.class);
	}

	public PaginationResponseDto getAllSemesters(final Integer pageNumber, final Integer pageSize) {
		log.debug("inside SemesterProcessor.getAllSemesters method");
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Semester> smestersPage = semesterDao.getAll(pageable);
		List<SemesterDto> semesterDtos = smestersPage.getContent().stream()
				.map(e -> modelMapper.map(e, SemesterDto.class)).collect(Collectors.toList());
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) smestersPage.getTotalElements()).intValue(), semesterDtos);
	}

	public void deleteSemester(String userId, String semesterId) throws ValidationException, ForbiddenException {
		log.debug("inside SemesterProcessor.deleteSemester method");
		Semester semester = getSemester(semesterId);
		validateAccess(userId, semester);
		semesterDao.delete(semesterId);
	}

	private Semester getSemester(String id) throws ValidationException {
		log.debug("inside SemesterProcessor.getSemester method");
		Optional<Semester> semeterOptional = semesterDao.getById(id);
		if (semeterOptional.isPresent()) {
			return semeterOptional.get();
		} else {
			log.error("semester not found against id: " + id);
			throw new ValidationException("semester not found against id: " + id);
		}
	}

	private void validateAccess(String userId, Semester semester) throws ForbiddenException {
		log.debug("inside SemesterProcessor.validateAccess method");
		if (!semester.getCreatedBy().equals(userId)) {
			log.error("user has no access to edit the semester");
			throw new ForbiddenException("user has no access to edit the semester");
		}
	}
}
