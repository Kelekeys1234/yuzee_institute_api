package com.seeka.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.seeka.app.bean.AuditErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.dto.ErrorReportCategoryDto;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.dto.ErrorReportResponseDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface IErrorReportService {

	void save(ErrorReportDto errorReport) throws ValidationException;

	void update(ErrorReportDto errorReport, String id) throws ValidationException;

	ErrorReportResponseDto getErrorReportById(String id) throws ValidationException;

	List<ErrorReportResponseDto> getAllErrorReport(String userId, Integer startIndex, Integer pageSize, String errorReportCategoryId,
			String errorReportStatus, Date updatedOn, Boolean isFavourite, Boolean isArchive, String sortByField, String sortByType, String searchKeyword)
			throws ValidationException;

	ResponseEntity<?> deleteByUserId(String userId);

	List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType);

	int getErrorReportCount(String userId, String errorReportCategoryId, String errorReportStatus, Date updatedOn, Boolean isFavourite,
			Boolean isArchive, String searchKeyword);

	void setIsFavouriteFlag(String errorRepoetId, boolean isFavourite) throws NotFoundException;

	void deleteByErrorReportId(String errorReportId);

	void saveErrorReportCategory(ErrorReportCategoryDto errorReportCategoryDto);

	List<AuditErrorReport> getAuditListByErrorReport(String errorReportId);

	void archiveErrorReport(String errorReportId, boolean isArchive) throws ValidationException;
}
