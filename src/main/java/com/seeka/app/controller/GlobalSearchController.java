package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.IInstituteKeywordService;

@RestController
@RequestMapping("/global")
public class GlobalSearchController {

    @Autowired
    private ICourseKeywordService courseKeywordService;

    @Autowired
    private IInstituteKeywordService instituteKeywordService;

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

        String[] splittedKeywords = keyword.split("\\s+");

        List<CourseKeywords> searchkeywordList = courseKeywordService.searchCourseKeyword(keyword);
        response.put("status", 1);
        response.put("searchkeywordList", searchkeywordList);
        response.put("message", "Success");
        return ResponseEntity.accepted().body(response);
    }

}
