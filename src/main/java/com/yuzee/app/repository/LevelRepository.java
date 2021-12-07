package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.Level;

public interface LevelRepository extends JpaRepository<Level, String> {
	List<Level> findAllByOrderBySequenceNoAsc();
}
