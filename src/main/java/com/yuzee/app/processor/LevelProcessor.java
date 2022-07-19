package com.yuzee.app.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.common.lib.dto.institute.LevelDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class LevelProcessor {

    @Autowired
    private LevelDao levelDao;

    @Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("importlevelJob")
	private Job job;
	
	@Transactional
    public void addUpdateLevel(LevelDto levelDto) {
    	Level level = new Level(null, levelDto.getName(), levelDto.getCode(), null,levelDto.getSequenceNo(), true, new Date(), null, null, "API", null, null);
    	levelDao.addUpdateLevel(level);
    }

	@Transactional
	public Level getLevel(String id) {
        Optional<Level> optLevel = levelDao.getLevel(UUID.fromString(id));
        if (optLevel.isPresent()) {
        	return optLevel.get();
        }
        return null;
        
    }
    
	@Transactional
    public List<LevelDto> getAllLevels() {
    	log.debug("Inside getAllLevels() method");
    	List<LevelDto> levelDtos = new ArrayList<>();
    	log.info("Fetching all levels from DB");
    	List<Level> levelsFromDB = levelDao.getAll();
    	if(!CollectionUtils.isEmpty(levelsFromDB)) {
    		log.info("Levels fetched from DB, start iterating data to make response");
    		levelsFromDB.stream().forEach(level -> {
    			var levelDto = new LevelDto(level.getId().toString(), level.getName(), level.getCode(), level.getDescription(), level.getSequenceNo());
    			levelDtos.add(levelDto);
    		});
    	}
        return levelDtos;
    }

	@CacheEvict(cacheNames = {"cacheLevelMap"}, allEntries = true)
	public void importLevel(final MultipartFile multipartFile) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.debug("Inside importLevel() method");
		log.info("Calling methiod to save level data");
		
		File f = File.createTempFile("levels", ".csv");
		multipartFile.transferTo(f);
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("csv-file", f.getAbsolutePath());
		jobParametersBuilder.addString("execution-id", UUID.randomUUID().toString());
		jobLauncher.run(job, jobParametersBuilder.toJobParameters());
	}

	public LevelDto getLevelById(String levelId) {
		Level level = getLevel(levelId);
		if (!ObjectUtils.isEmpty(level)) {
			return new LevelDto(level.getId().toString(), level.getName(), level.getCode(), level.getDescription(), level.getSequenceNo());
		}
		return new LevelDto();
	}
}
