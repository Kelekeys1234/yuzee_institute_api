package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportDAO {

	void save(ErrorReport errorReport);

	ErrorReportCategory getErrorCategory(BigInteger errorReportCategoryId);

	List<ErrorReport> getAllErrorReport();

	List<ErrorReport> getErrorReportByUserId(BigInteger userId, Integer startIndex, Integer pageSize);

	ErrorReport getErrorReportById(BigInteger id);

	void update(ErrorReport errorReport);

	List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType);

	void addErrorRepoerAudit(BigInteger id);

	int getErrorReportCountForUser(BigInteger userId);

	void setIsFavouriteFlag(BigInteger errorRepoetId, boolean isFavourite) throws NotFoundException;

}
