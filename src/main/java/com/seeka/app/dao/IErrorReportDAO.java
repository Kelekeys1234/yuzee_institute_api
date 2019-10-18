package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.exception.NotFoundException;

public interface IErrorReportDAO {

    public void save(ErrorReport errorReport);

    public ErrorReportCategory getErrorCategory(BigInteger errorReportCategoryId);

    public List<ErrorReport> getAllErrorReport();

    public List<ErrorReport> getErrorReportByUserId(BigInteger userId);
    
    public ErrorReport getErrorReportById(BigInteger id);

    public void update(ErrorReport errorReport);

    public List<ErrorReportCategory> getAllErrorCategory(String errorCategoryType);

    public void addErrorRepoerAudit(BigInteger id);

	List<ErrorReport> getAllErrorReportForUser(BigInteger userId);

	int getErrorReportCountForUser(BigInteger userId);

	void setIsFavouriteFlag(BigInteger errorRepoetId, boolean isFavourite) throws NotFoundException;

}
