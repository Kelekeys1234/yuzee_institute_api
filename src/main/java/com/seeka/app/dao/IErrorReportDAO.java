package com.seeka.app.dao;

import java.util.Date;
import java.util.List;

import com.seeka.app.bean.AuditErrorReport;
import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportDAO {

	void save(ErrorReport errorReport);

	ErrorReportCategory getErrorCategory(String errorReportCategoryId);

	List<ErrorReport> getAllErrorReport(String userId, Integer startIndex, Integer pageSize, String errorReportCategoryId, String errorReportStatus,
			Date updatedOn, Boolean isFavourite, Boolean isArchive, String sortByField, String sortByType, String searchKeyword);

	ErrorReport getErrorReportById(String id);

	void update(ErrorReport errorReport);

	List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType);

	void addErrorRepoerAudit(AuditErrorReport auditErrorReport);

	int getErrorReportCountForUser(String userId, String errorReportCategoryId, String errorReportStatus, Date updatedOn, Boolean isFavourite,
			Boolean isArchive, String searchKeyword);

	void setIsFavouriteFlag(String errorRepoetId, boolean isFavourite) throws NotFoundException;

	void saveErrorReportCategory(ErrorReportCategory errorReportCategory);

	List<AuditErrorReport> getAuditListByErrorReport(String errorReportId);
}
