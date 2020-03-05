package com.seeka.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Top10Course;
import com.seeka.app.dto.GlobalData;

@Service
public class UploadService implements IUploadService {

	@Value("${upload.directory}")
	private String uploadDirectory;
	
	@Autowired
	private ITop10CourseService top10CourseService;
	@Autowired
	private IGlobalStudentData globalStudentDataService;
	
	@Override
	public String importTop10Courses(MultipartFile multipartFile) throws IOException{
		// TODO Auto-generated method stub
		saveFile(multipartFile,"Top10Courses");
		List<Top10Course> listOffacultyWiseTop10Courses = readTop10CoursesPerFaculty(multipartFile);
		top10CourseService.deleteAllTop10Courses();
		for (Top10Course top10Course : listOffacultyWiseTop10Courses) {
			top10CourseService.saveTop10Courses(top10Course);
		}
		return "successful";
	}
	
	@Override
	public String importGlobalFlowOfTertiaryLevelStudents(MultipartFile multipartFile) throws IOException{
		saveFile(multipartFile,"GlobalFlowOfTertiaryLevelStudents");
        List<GlobalData> globalStudentList = readGlobalFlowOfTertiaryLevelStudents(multipartFile);
		globalStudentDataService.deleteAllGlobalStudentData();
		for (GlobalData globalStudentData : globalStudentList) {
			globalStudentDataService.saveGlobalStudentData(globalStudentData);
		}
		return "Done";
	}
	
	private String saveFile(MultipartFile file, String directory) throws IOException {
		
		final String uploadLocation = uploadDirectory+directory; 
		byte[] bytes = file.getBytes();
		Path path = Paths.get(uploadLocation + "/" +LocalDateTime.now().getYear()+"_"+LocalDateTime.now().getMonth()+"_"+LocalDateTime.now().getDayOfMonth()+"_"+LocalDateTime.now().getHour()+"_"+LocalDateTime.now().getMinute()+"_"+LocalDateTime.now().getSecond()+"_"+file.getOriginalFilename()/* +"_"+LocalDate.now() */);
		Files.write(path, bytes);
		return path.toString();
	}
	
	private List<GlobalData> readGlobalFlowOfTertiaryLevelStudents(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		Workbook workbook = new XSSFWorkbook(inputStream);
        Map<String, List<GlobalData>> countryWiseStudentMap = new HashMap<>();
        Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Map<Integer,String> country= new HashMap<Integer, String>();
		if (iterator.hasNext()) {
			Iterator<Cell> cellIterator = iterator.next().iterator();
			int i =0;
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				if (currentCell.getStringCellValue() != null && !currentCell.getStringCellValue().isEmpty() && 
						!(currentCell.getStringCellValue().equalsIgnoreCase("Total Stude0ts") || currentCell.getStringCellValue().equalsIgnoreCase("Total Students") )) {
					String countryName = currentCell.getStringCellValue();
					countryWiseStudentMap.put(countryName, new ArrayList<GlobalData>());
					country.put(i, countryName);
					i++;
				}
			}
			System.out.println(country);
		} 
		// For rest of rows
		while (iterator.hasNext()) {
			int i = 0;
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				GlobalData globalDataDto = new GlobalData();
				Cell currentCell = cellIterator.next();
				if (currentCell.getStringCellValue() != null && !currentCell.getStringCellValue().isEmpty()) {
					globalDataDto.setDestinationCountry(currentCell.getStringCellValue());
				} else {
					continue;
				}
				if(cellIterator.hasNext()) {
					Cell currCell = cellIterator.next();
					try {
						globalDataDto.setTotalNumberOfStudent(currCell.getNumericCellValue());
					} catch(IllegalStateException e) {
						globalDataDto.setTotalNumberOfStudent(0D);
					}
					} else {
					break;
				}
				if(cellIterator.hasNext()) {
					cellIterator.next();
				} else {
					break;
				}
				globalDataDto.setSourceCountry(country.get(i));
				System.out.println(i);
				System.out.println(country.get(i));
				countryWiseStudentMap.get(country.get(i)).add(globalDataDto);
				i++;
			}
		}
		
		List<GlobalData> listOfGlobalData = new ArrayList<>();
		
		for (Entry<String, List<GlobalData>> globalData : countryWiseStudentMap.entrySet()) {
			listOfGlobalData.addAll(globalData.getValue());
		}
		
		return listOfGlobalData;
	}

	private List<Top10Course> readTop10CoursesPerFaculty(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		Workbook workbook = new XSSFWorkbook(inputStream);
        Map<String, List<String>> facultyCourseMap = new HashMap<>();
        for(int i=1; i<=14; i++) {
        	Sheet sheet = workbook.getSheetAt(i);
        	Iterator<Row> iterator = sheet.iterator();
        	String facultyName=null; 
        	if(iterator.hasNext()) {
        		Iterator<Cell> cellIterator = iterator.next().iterator();
        		 while (cellIterator.hasNext()) {
                     Cell currentCell = cellIterator.next();
                     if(currentCell.getStringCellValue() != null && !currentCell.getStringCellValue().isEmpty()) {
	                     facultyName = currentCell.getStringCellValue().replace("Faculty Of ", "");
	                     facultyName = currentCell.getStringCellValue().replace("Faculty of ", "");
	                     facultyCourseMap.put(facultyName, new ArrayList<String>());
                     }
                 }
        	} else {
        		continue;
        	}

        	// For rest of rows
        	while(iterator.hasNext()) {
        		Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if(currentCell.getStringCellValue() !=null && !currentCell.getStringCellValue().isEmpty()) {
                    	facultyCourseMap.get(facultyName).add(currentCell.getStringCellValue());
                    }
                    }
        	}
        	System.out.println("Individual............");
        	System.out.println(facultyCourseMap.get(facultyName));
        }
        
        List<Top10Course> listOfCourses = new ArrayList<>();
        Top10Course top10Course = null;
        for (Entry<String, List<String>> facultyCourse : facultyCourseMap.entrySet()) {
			for (String course : facultyCourse.getValue()) {
				top10Course = new Top10Course(course, facultyCourse.getKey());
				listOfCourses.add(top10Course);
			}
		}
        
		return listOfCourses;
	}
}
