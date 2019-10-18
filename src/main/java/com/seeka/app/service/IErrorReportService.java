package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportService {

    public ResponseEntity<?> save(ErrorReportDto errorReport);
    
    public ResponseEntity<?> update(ErrorReportDto errorReport, BigInteger id);

    public ResponseEntity<?> getErrorReportByUserId(BigInteger userId);
    
    public ResponseEntity<?> getErrorReportById(BigInteger id);

    public ResponseEntity<?> getAllErrorReport();

    public ResponseEntity<?> deleteByUserId(@Valid BigInteger userId);

    public ResponseEntity<?> getAllErrorCategory(String errorCategoryType);

	List<ErrorReport> getAllErrorReportForUser(BigInteger userId);

	int getErrorReportCount(BigInteger userId);

	void setIsFavouriteFlag(BigInteger errorRepoetId, boolean isFavourite) throws NotFoundException;

}
