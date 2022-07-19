package com.yuzee.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.Level;

public interface LevelRepository extends MongoRepository<Level, UUID> {
	
	List<Level> findAllByOrderBySequenceNoAsc();
	
	Level findByCode(String code);
}
