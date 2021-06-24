package com.yuzee.app.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;
import com.yuzee.app.dao.EducationSystemDao;
import com.yuzee.app.dao.SubjectDao;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.institute.SubjectDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.util.PaginationUtil;

@Service
@Transactional
public class SubjectProcessor {

	public static final Logger LOGGER = LoggerFactory.getLogger(SubjectProcessor.class);

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private EducationSystemDao educationSystemDao;

	@Autowired
	private ModelMapper modelMapper;

	public void importSubject(MultipartFile multipartFile) {
		try {
			InputStream inputStream = multipartFile.getInputStream();
			LOGGER.info("Start reading data from inputStream using CSV reader");
			CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
			Map<String, String> columnMapping = new HashMap<>();
			LOGGER.info("Start mapping columns to bean variables");

			columnMapping.put("Subject Name", "subjectName");

			HeaderColumnNameTranslateMappingStrategy<Subject> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
			beanStrategy.setType(Subject.class);
			beanStrategy.setColumnMapping(columnMapping);
			CsvToBean<Subject> csvToBean = new CsvToBean<>();
			LOGGER.info("Start parsing CSV to bean");
			List<Subject> subjectList = csvToBean.parse(beanStrategy, reader);

			LOGGER.info("Going to getAll educationSystem in DB");
			List<EducationSystem> educationSystems = educationSystemDao.getAll();
			if (educationSystems != null) {
				LOGGER.info("Going to save subjects in DB");
				subjectDao.saveSubjects(subjectList, educationSystems);
			}
		} catch (IOException e) {
			LOGGER.error("Exception in uploading subjects", e);
			e.printStackTrace();
		}
	}

	public PaginationResponseDto<List<SubjectDto>> getAllSubjects(final Integer pageNumber, final Integer pageSize,
			String name, String educationSystemId) throws NotFoundException, InvokeException {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Subject> subjectPage = subjectDao.findByNameContainingIgnoreCaseAndEducationSystemId(name,
				educationSystemId, pageable);
		List<SubjectDto> subjectDtos = subjectPage.getContent().stream().map(e -> modelMapper.map(e, SubjectDto.class))
				.collect(Collectors.toList());
		return PaginationUtil.calculatePaginationAndPrepareResponse(subjectPage, subjectDtos);
	}

}
