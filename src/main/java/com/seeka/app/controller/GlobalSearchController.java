package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.util.IConstant;

@RestController
@RequestMapping("/global")
public class GlobalSearchController {

    @Autowired
    private ICourseKeywordService courseKeywordService;

    public static void main(String[] args) {
        String str = "Hello I'm your String";
        String[] splited = str.split("\\s+");
        for (String string : splited) {
            System.out.println(string);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") String keyword) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CourseKeywords> searchkeywordList = courseKeywordService.searchCourseKeyword(keyword);
        if (searchkeywordList != null && !searchkeywordList.isEmpty()) {
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Course fetched successfully");
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.COURSE_GET_NOT_FOUND);
        }
        response.put("data", searchkeywordList);
        return ResponseEntity.accepted().body(response);
    }
}
