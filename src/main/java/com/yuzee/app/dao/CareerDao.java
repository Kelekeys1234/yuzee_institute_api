package com.yuzee.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Careers;

public interface CareerDao {
	public Page<Careers> findByNameContainingIgnoreCase(String name, Pageable pageable);

	public List<Careers> findByIdIn(List<String> ids);
}
