package com.yuzee.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.AuditErrorReport;
import com.yuzee.app.bean.ErrorReport;
import com.yuzee.app.bean.ErrorReportCategory;
import com.yuzee.app.dao.IErrorReportDAO;
import com.yuzee.app.dto.ErrorReportCategoryDto;
import com.yuzee.app.dto.ErrorReportDto;
import com.yuzee.app.dto.ErrorReportResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.IdentityHandler;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.util.DateUtil;

@Service
@Transactional
public class ErrorReportService implements IErrorReportService {

	@Autowired
	private IErrorReportDAO errorReportDAO;

	@Autowired
	private IdentityHandler identityHandler;

	@Autowired
	private StorageHandler storageHandler;

	@Override
	public void saveErrorReportCategory(final ErrorReportCategoryDto errorReportCategoryDto) {
		ErrorReportCategory errorReportCategory = new ErrorReportCategory();
		BeanUtils.copyProperties(errorReportCategoryDto, errorReportCategory);
		errorReportCategory.setIsActive(true);
		errorReportCategory.setCreatedOn(new Date());
		errorReportCategory.setUpdatedOn(new Date());
		errorReportCategory.setCreatedBy("API");
		errorReportCategory.setUpdatedBy("API");
		errorReportDAO.saveErrorReportCategory(errorReportCategory);
	}

	@Override
	public void save(final ErrorReportDto errorReportDto) throws ValidationException {
		ErrorReport errorReport = new ErrorReport();
		BeanUtils.copyProperties(errorReportDto, errorReport);
		if (errorReportDto.getErrorReportCategoryId() != null) {
			errorReport.setErrorReportCategory(errorReportDAO.getErrorCategory(errorReportDto.getErrorReportCategoryId()));
		} else {
			throw new ValidationException("Error category is required.");
		}
		errorReport.setStatus("PENDING");
		errorReport.setCreatedBy("API");
		errorReport.setUpdatedBy("API");
		errorReport.setCreatedOn(new Date());
		errorReport.setUpdatedOn(new Date());
		errorReport.setIsActive(true);
		errorReportDAO.save(errorReport);
		errorReport.setCaseNumber("ERROR_" + errorReport.getId());
		errorReportDAO.update(errorReport);
	}

	@Override
	public void update(final ErrorReportDto errorReportDto, final String id) throws ValidationException {
		if (null == errorReportDto.getStatus()) {
			throw new ValidationException("Status is required.");
		}
		ErrorReport errorReport = errorReportDAO.getErrorReportById(id);
		String caseNumber = errorReport.getCaseNumber();
		BeanUtils.copyProperties(errorReportDto, errorReport);
		if (errorReportDto.getErrorReportCategoryId() != null) {
			errorReport.setErrorReportCategory(errorReportDAO.getErrorCategory(errorReportDto.getErrorReportCategoryId()));
		} else {
			throw new ValidationException("Error category is required.");
		}
		errorReport.setId(id);
		errorReport.setUpdatedBy("API");
		errorReport.setUpdatedOn(new Date());
		errorReport.setCaseNumber(caseNumber);
		errorReportDAO.update(errorReport);
		AuditErrorReport auditErrorReport = new AuditErrorReport();
		auditErrorReport.setErrorReport(errorReport);
		auditErrorReport.setNote(errorReportDto.getNote());
		auditErrorReport.setCreatedBy("API");
		auditErrorReport.setCreatedOn(new Date());
		errorReportDAO.addErrorRepoerAudit(auditErrorReport);
	}

	@Override
	public ErrorReportResponseDto getErrorReportById(final String id) throws ValidationException {
		ErrorReport errorReport = errorReportDAO.getErrorReportById(id);
		ErrorReportResponseDto errorReportResponseDto = new ErrorReportResponseDto();
		BeanUtils.copyProperties(errorReport, errorReportResponseDto);
		errorReportResponseDto.setErrorReportCategoryName(errorReport.getErrorReportCategory().getName());
		errorReportResponseDto.setErrorReportCategoryId(errorReport.getErrorReportCategory().getId());
		UserDto userDto = identityHandler.getUserById(errorReport.getUserId());
		errorReportResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		errorReportResponseDto.setUserEmail(userDto.getEmail());
		if (errorReport.getAssigneeUserId() != null) {
			UserDto assignUserDto = identityHandler.getUserById(errorReport.getAssigneeUserId());
			errorReportResponseDto.setAssigneeUserName(assignUserDto.getFirstName() + " " + assignUserDto.getLastName());
		}
		return errorReportResponseDto;
	}

	@Override
	public List<ErrorReportResponseDto> getAllErrorReport(final String userId, final Integer startIndex, final Integer pageSize,
			final String errorReportCategoryId, final String errorReportStatus, final Date updatedOn, final Boolean isFavourite, final Boolean isArchive,
			final String sortByField, final String sortByType, final String searchKeyword) throws ValidationException, NotFoundException, InvokeException {
		List<ErrorReport> errorReports = errorReportDAO.getAllErrorReport(userId, startIndex, pageSize, errorReportCategoryId, errorReportStatus, updatedOn,
				isFavourite, isArchive, sortByField, sortByType, searchKeyword);
		List<ErrorReportResponseDto> errorReportResponseDtos = new ArrayList<>();
		for (ErrorReport errorReport : errorReports) {
			ErrorReportResponseDto errorReportResponseDto = new ErrorReportResponseDto();
			BeanUtils.copyProperties(errorReport, errorReportResponseDto);
			errorReportResponseDto.setErrorReportCategoryName(errorReport.getErrorReportCategory().getName());
			errorReportResponseDto.setErrorReportCategoryId(errorReport.getErrorReportCategory().getId());
			UserDto userDto = identityHandler.getUserById(errorReport.getUserId());
			errorReportResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			errorReportResponseDto.setUserEmail(userDto.getEmail());
			if (errorReport.getAssigneeUserId() != null) {
				UserDto assignUserDto = identityHandler.getUserById(errorReport.getAssigneeUserId());
				errorReportResponseDto.setAssigneeUserName(assignUserDto.getFirstName() + " " + assignUserDto.getLastName());
				List<StorageDto> storageDTOList = storageHandler.getStorages(errorReport.getAssigneeUserId(), EntityTypeEnum.USER,EntitySubTypeEnum.PROFILE);
				if (storageDTOList != null && !storageDTOList.isEmpty()) {
					errorReportResponseDto.setAssigneeUserImageUrl(storageDTOList.get(0).getFileURL());
				}
			}

			errorReportResponseDtos.add(errorReportResponseDto);
		}
		return errorReportResponseDtos;
	}

	@Override
	public ResponseEntity<?> deleteByUserId(@Valid final String userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<ErrorReport> errorReports = errorReportDAO.getAllErrorReport(userId, null, null, null, null, null, null, null, null, null, null);
			if (errorReports != null && !errorReports.isEmpty()) {
				for (ErrorReport errorReport : errorReports) {
					errorReport.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
					errorReport.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
					errorReport.setIsActive(false);
					errorReportDAO.update(errorReport);
				}
				response.put("message", "Error deleted successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", "Error not found");
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public List<ErrorReportCategory> getAllErrorCategory(final String errorCategoryType) {
		return errorReportDAO.getAllErrorCategory(errorCategoryType);
	}

	@Override
	public int getErrorReportCount(final String userId, final String errorReportCategoryId, final String errorReportStatus, final Date updatedOn,
			final Boolean isFavourite, final Boolean isArchive, final String searchKeyword) {
		return errorReportDAO.getErrorReportCountForUser(userId, errorReportCategoryId, errorReportStatus, updatedOn, isFavourite, isArchive, searchKeyword);
	}

	@Override
	public void setIsFavouriteFlag(final String errorRepoetId, final boolean isFavourite) throws NotFoundException {
		errorReportDAO.setIsFavouriteFlag(errorRepoetId, isFavourite);
	}

	@Override
	public void deleteByErrorReportId(final String errorReportId) {
		ErrorReport errorReport = errorReportDAO.getErrorReportById(errorReportId);
		errorReport.setIsActive(false);
		errorReport.setDeletedOn(new Date());
		errorReport.setUpdatedOn(new Date());
		errorReportDAO.update(errorReport);
	}

	@Override
	public List<AuditErrorReport> getAuditListByErrorReport(final String errorReportId) {
		return errorReportDAO.getAuditListByErrorReport(errorReportId);
	}

	@Override
	public void archiveErrorReport(final String errorReportId, final boolean isArchive) throws ValidationException {
		ErrorReport errorReport = errorReportDAO.getErrorReportById(errorReportId);
		if (errorReport == null) {
			throw new ValidationException("Error Report not found for id :" + errorReportId);
		}
		errorReport.setIsArchive(isArchive);
		errorReport.setUpdatedBy("API");
		errorReport.setUpdatedOn(new Date());
		errorReportDAO.update(errorReport);
	}

}
