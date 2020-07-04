package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Level;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.LevelDto;
import com.seeka.app.endpoint.LevelInterface;
import com.seeka.app.processor.LevelProcessor;

@RestController("levelControllerV1")
public class LevelController implements LevelInterface {

    @Autowired
    private LevelProcessor levelProcessor;

    @Override
    public ResponseEntity<?> saveLevel(LevelDto levelDto) throws Exception {
        levelProcessor.saveLevel(levelDto);
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
