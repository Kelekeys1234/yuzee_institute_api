package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dto.LevelDto;
import com.yuzee.app.endpoint.LevelInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.LevelProcessor;

@RestController("levelControllerV1")
public class LevelController implements LevelInterface {

    @Autowired
    private LevelProcessor levelProcessor;

    @Override
    public ResponseEntity<?> saveLevel(LevelDto levelDto) throws Exception {
        levelProcessor.addUpdateLevel(levelDto);
        return new GenericResponseHandlers.Builder()
				.setMessage("Level saved successfully").setStatus(HttpStatus.OK).create();
    }
    
    @Override
    public ResponseEntity<?> getAll() throws Exception {
        List<LevelDto> levelList = levelProcessor.getAllLevels();
        return new GenericResponseHandlers.Builder().setData(levelList)
				.setMessage("Level saved successfully").setStatus(HttpStatus.OK).create();
    }
    
    @Override
    @Deprecated
    public ResponseEntity<?> getLevelByCountry(String countryId) throws Exception {
        List<Level> levelList = levelProcessor.getLevelByCountryId(countryId);
        return new GenericResponseHandlers.Builder().setData(levelList)
				.setMessage("Level fetched successfully").setStatus(HttpStatus.OK).create();
    }
 
    @Override
    @Deprecated
    public ResponseEntity<?> getCountryLevel(String countryId) throws Exception {
        return ResponseEntity.accepted().body(levelProcessor.getCountryLevel(countryId));
    }
}
