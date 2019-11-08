package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.AuditErrorReport;
import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportDAO {

	void save(ErrorReport errorReport);

	ErrorReportCategory getErrorCategory(BigInteger errorReportCategoryId);

	List<ErrorReport> getAllErrorReport(BigInteger userId, Integer startIndex, Integer pageSize, BigInteger errorReportCategoryId, String errorReportStatus,
			Date updatedOn, Boolean isFavourite, Boolean isArchive);

	ErrorReport getErrorReportById(BigInteger id);

	void update(ErrorReport errorReport);

	List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType);

	void addErrorRepoerAudit(AuditErrorReport auditErrorReport);

	int getErrorReportCountForUser(BigInteger userId, BigInteger errorReportCategoryId, String errorReportStatus, Date updatedOn, Boolean isFavourite,
			Boolean isArchive);

	void setIsFavouriteFlag(BigInteger errorRepoetId, boolean isFavourite) throws NotFoundException;

	void saveErrorReportCategory(ErrorReportCategory errorReportCategory);

	List<AuditErrorReport> getAuditListByErrorReport(BigInteger errorReportId);
}
