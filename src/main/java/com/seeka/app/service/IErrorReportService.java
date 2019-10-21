package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportService {

	ResponseEntity<?> save(ErrorReportDto errorReport);

	ResponseEntity<?> update(ErrorReportDto errorReport, BigInteger id);

//    public ResponseEntity<?> getErrorReportByUserId(BigInteger userId);

	ResponseEntity<?> getErrorReportById(BigInteger id);

	ResponseEntity<?> getAllErrorReport();

	ResponseEntity<?> deleteByUserId(@Valid BigInteger userId);

	ResponseEntity<?> getAllErrorCategory(String errorCategoryType);

	List<ErrorReport> getAllErrorReportForUser(BigInteger userId, Integer startIndex, Integer pageSize);

	int getErrorReportCount(BigInteger userId);

	void setIsFavouriteFlag(BigInteger errorRepoetId, boolean isFavourite) throws NotFoundException;

	void deleteByErrorReportId(BigInteger errorReportId);

}
