package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dto.ScholarshipLevelCountDto;
import com.yuzee.app.repository.ScholarshipRepository;
import com.yuzee.app.specification.ScholarshipSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScholarshipDaoImpl implements ScholarshipDao {

	@Autowired
	private ScholarshipSpecification scholarshipSpecification;

	@Autowired
	private ScholarshipRepository scholarshipRepository;

	@Override
	public Scholarship saveScholarship(final Scholarship scholarship) {
		return scholarshipRepository.save(scholarship);
	}

	@Override
	public Optional<Scholarship> getScholarshipById(final String id) {
		return scholarshipRepository.findById(id);
	}

	@Override
	public Page<Scholarship> getScholarshipList(final String countryName, final String instituteId,
			final String searchKeyword, Pageable pageable) {
		return scholarshipRepository.findAll(
				scholarshipSpecification.getScholarshipsBasedOnFilters(countryName, instituteId, searchKeyword),
				pageable);
	}

	@Override
	public List<ScholarshipLevelCountDto> getScholarshipCountGroupByLevel() {
		return scholarshipRepository.getScholarshipCountGroupByLevel();
	}

	@Override
	public void deleteScholarship(String scholarshipId) {
		try {
			scholarshipRepository.deleteById(scholarshipId);
		} catch (EmptyResultDataAccessException ex) {
			log.error(ex.getMessage());
		}
	}

	@Override
	public Long getCountByInstituteId(String instituteId) {
		return scholarshipRepository.countByInstituteId(instituteId);
	}

	@Override
	public List<Scholarship> getScholarshipByIds(List<String> scholarshipIds) {
		return scholarshipRepository.findAllById(scholarshipIds);
	}
}
