package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Careers;

public interface CareerDao {
	public List<Careers> findByIdIn(List<String> ids);
}
