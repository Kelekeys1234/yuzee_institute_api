package com.seeka.app.controller.v1;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.service.IGlobalSearchKeywordService;


@RestController("globalSearchKeywordControllerV1")
@RequestMapping("/v1/globalSearch/keyword")
public class GlobalSearchKeywordController {

	@Autowired
	private IGlobalSearchKeywordService iGlobalSearchKeywordService;
	
	@PutMapping("/add/{searchKeyWord}")
	public ResponseEntity<?> addKeyword(@RequestHeader("userId") BigInteger userId, @PathVariable(name="searchKeyWord") String searchKeyword){
		if(searchKeyword != null) {
			iGlobalSearchKeywordService.addGlobalSearhcKeyForUser(searchKeyword, userId);
		}	
		return new GenericResponseHandlers.Builder().setMessage("Keyword added successfully").setStatus(HttpStatus.OK).setData(null).create();
	}
	
	@GetMapping("/getTopSearched")
	public ResponseEntity<?> getOtherUsersTopSearchedKeywords(@RequestHeader("userId") BigInteger userId){
		List<String> globalKeywordList = iGlobalSearchKeywordService.getOtherUsersTopSearchedKeywords(userId);
		return new GenericResponseHandlers.Builder().setMessage("Keyword list displayed successfully").setStatus(HttpStatus.OK).setData(globalKeywordList).create();
	}
}
