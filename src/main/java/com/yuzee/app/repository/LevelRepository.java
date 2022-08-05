package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.Level;

public interface LevelRepository extends MongoRepository<Level, String> {

	List<Level> findAllByOrderBySequenceNoAsc();

	Level findByCode(String code);
}
