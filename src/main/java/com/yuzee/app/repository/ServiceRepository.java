package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
	public Service findByNameIgnoreCase(String name);
}
