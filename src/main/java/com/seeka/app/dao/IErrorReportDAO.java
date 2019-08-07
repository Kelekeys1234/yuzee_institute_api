package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;

public interface IErrorReportDAO {

    public void save(ErrorReport errorReport);

    public ErrorReportCategory getErrorCategory(BigInteger errorReportCategoryId);

    public List<ErrorReport> getAllErrorReport();

    public List<ErrorReport> getErrorReportByUserId(BigInteger userId);

    public void update(ErrorReport errorReport);

    public List<ErrorReportCategory> getAllErrorCategory();

}
