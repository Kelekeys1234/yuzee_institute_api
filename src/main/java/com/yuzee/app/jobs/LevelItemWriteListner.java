package com.yuzee.app.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.yuzee.app.bean.Level;
import com.yuzee.app.processor.LogFileProcessor;
import com.yuzee.common.lib.dto.institute.LevelDto;
import com.yuzee.common.lib.handler.ElasticHandler;
import com.yuzee.common.lib.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevelItemWriteListner implements ItemWriteListener<Level> {
	
	@Value("#{jobParameters['execution-id']}")
	private String executionId;
	
	@Autowired
	private LogFileProcessor logFileProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ElasticHandler elasticHander;

	@Override
	public void afterWrite(List<? extends Level> items) {
		log.debug("After writing level item to db {} ",items.size());	
		elasticHander.saveUpdateLevels(
				items.stream().map(e -> modelMapper.map(e, LevelDto.class)).collect(Collectors.toList()));
	}

	@Override
	public void beforeWrite(List<? extends Level> items) {
		log.debug("Before writting level item to db {}",items.size());
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Level> items) {
		List<String> errors = new ArrayList<>();
		items.stream().forEach(item -> 
			errors.add(String.format("%s,%s,%s",item.getName(),item.getCode(), ExceptionUtil.findCauseUsingPlainJava(exception).getMessage()))
		);
		try {
			logFileProcessor.appendToLogFile(executionId, errors);
		} catch (IOException e) {
			log.error("Exception from onWriteError Event Listener");
		}
	}		
}
