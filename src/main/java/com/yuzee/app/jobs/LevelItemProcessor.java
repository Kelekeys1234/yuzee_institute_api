package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dto.LevelDto;
import com.yuzee.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevelItemProcessor implements ItemProcessor<LevelDto, Level>{

	@Override
	public Level process(LevelDto levelDto) throws Exception {
		log.info("Creating level model for level code {} and level name {}", levelDto.getCode(), levelDto.getName());
		return new Level(levelDto.getName(), levelDto.getCode(), levelDto.getDescription(), true, DateUtil.getUTCdatetimeAsDate(), DateUtil.getUTCdatetimeAsDate(), null, "AUTO", "AUTO", false);
	}
}
