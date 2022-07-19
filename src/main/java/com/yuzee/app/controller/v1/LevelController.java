package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.Level;
import com.yuzee.app.endpoint.LevelInterface;
import com.yuzee.app.processor.LevelProcessor;
import com.yuzee.common.lib.dto.institute.LevelDto;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

@RestController("levelControllerV1")
public class LevelController implements LevelInterface {

    @Autowired
    private LevelProcessor levelProcessor;
    
    @Autowired
	private MessageTranslator messageTranslator;
    
    @Override
    public ResponseEntity<?> saveLevel(LevelDto levelDto) throws Exception {
        levelProcessor.addUpdateLevel(levelDto);
        return new GenericResponseHandlers.Builder()
				.setMessage(messageTranslator.toLocale("level.added")).setStatus(HttpStatus.OK).create();
    }
    
    @Override
    public ResponseEntity<?> getAll() throws Exception {
        List<LevelDto> levelList = levelProcessor.getAllLevels();
        return new GenericResponseHandlers.Builder().setData(levelList)
				.setMessage(messageTranslator.toLocale("level.list.retrieved")).setStatus(HttpStatus.OK).create();
    }
    
   /* @Override
    @Deprecated
    public ResponseEntity<?> getLevelByCountry(String countryId) throws Exception {
        List<Level> levelList = levelProcessor.getLevelByCountryId(countryId);
        return new GenericResponseHandlers.Builder().setData(levelList)
				.setMessage(messageTranslator.toLocale("level.retrieved")).setStatus(HttpStatus.OK).create();
    }
 
    @Override
    @Deprecated
    public ResponseEntity<?> getCountryLevel(String countryId) throws Exception {
        return ResponseEntity.accepted().body(levelProcessor.getCountryLevel(countryId));
    } */

	@Override
	public ResponseEntity<?> getById(String levelId) throws Exception {
        return new GenericResponseHandlers.Builder().setData(levelProcessor.getLevelById(levelId))
				.setMessage(messageTranslator.toLocale("level.retrieved")).setStatus(HttpStatus.OK).create();
	}
}
