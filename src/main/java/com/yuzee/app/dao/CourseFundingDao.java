package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.exception.ValidationException;

public interface CourseFundingDao {
	public List<CourseFunding> saveAll(List<CourseFunding> courseFundings) throws ValidationException;
}
