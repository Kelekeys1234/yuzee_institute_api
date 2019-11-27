package com.seeka.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDetails;
import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.bean.Level;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.ICourseDetailsService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IFacultyLevelService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.IInstituteLevelService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.ILevelService;

@RestController
@RequestMapping("/migrate")
public class MigrateCourseController {

	@Autowired
	private ICountryService countryService;

	@Autowired
	private ICityService cityService;

	@Autowired
	private IInstituteService instituteService;

	@Autowired
	private IFacultyService facultyService;

	@Autowired
	private ILevelService levelService;

	@Autowired
	private IInstituteLevelService instituteLevelService;

	@Autowired
	private IFacultyLevelService facultyLevelService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ICourseDetailsService courseDetailsService;

	@Autowired
	private ICoursePricingService coursePricingService;

	@RequestMapping(value = "/course", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> migrateCityData() throws Exception {
		Map<String, CourseDetails> map = get();

		Integer size = map.size(), i = 0;

		for (String key : map.keySet()) {
			i++;

			try {
				CourseDetails courseDetails = map.get(key);
				Course course = courseDetails.getCourse();

				System.out.println(size + "----" + i + ", Key: " + key + ", Name: " + course.getName());

				Faculty faculty = courseDetails.getFacultyObj();
				if (null != faculty) {
					course.setFaculty(faculty);
				}
				courseService.save(course);

				courseDetails.setCourse(course);
				courseDetailsService.save(courseDetails);

				FacultyLevel facultyLevel = courseDetails.getFacultyLevelObj();
				if (null != facultyLevel) {
					// facultyLevel.setId(BigInteger.randomBigInteger());
					facultyLevelService.save(facultyLevel);
				}

				InstituteLevel instituteLevel = courseDetails.getInstituteLevelObj();
				if (null != instituteLevel) {
					instituteLevelService.save(instituteLevel);
				}

				CoursePricing coursePricing = courseDetails.getCoursePricingObj();
				coursePricing.setCourse(course);
				coursePricingService.save(coursePricing);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("status", 1);
		response.put("message", "Success.!");
		return ResponseEntity.accepted().body(response);
	}

	public Map<String, CourseDetails> get() throws Exception {
		File myFile = new File("D:\\SeekaNew\\files\\course\\university_course_attribute_Singapore.xlsx");
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();

		List<Faculty> faculties = facultyService.getAll();
		Map<String, Faculty> facultyMap = new HashMap<>();
		for (Faculty faculty : faculties) {
			try {
				facultyMap.put(faculty.getName().toLowerCase().replaceAll("[^\\w]", "") + "-" + faculty.getLevel().getId(), faculty);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<Level> levels = levelService.getAll();
		Map<String, Level> levelMap = new HashMap<>();
		for (Level levelObj : levels) {
			levelMap.put(levelObj.getName().toLowerCase().replaceAll("[^\\w]", ""), levelObj);
		}

		List<Country> countryList = countryService.getAll();
		Map<String, Country> countryMap = new HashMap<>();
		for (Country country : countryList) {
			countryMap.put(country.getCountryCode().toLowerCase().replaceAll("[^\\w]", ""), country);
			countryMap.put(country.getName().toLowerCase().replaceAll("[^\\w]", ""), country);
		}

		List<City> list = cityService.getAll();
		Map<String, City> cityMap = new HashMap<>();
		for (City city : list) {
			cityMap.put(city.getName().toLowerCase().replaceAll("[^\\w]", ""), city);
		}

		List<Institute> instituteList = instituteService.getAll();
		Map<String, Institute> instituteMap = new HashMap<>();
		for (Institute ins : instituteList) {
			instituteMap.put(ins.getName().toLowerCase().replaceAll("[^\\w]", ""), ins);
		}

		Map<String, FacultyLevel> facultyLevelMap = new HashMap<>();
		Map<String, InstituteLevel> instituteLevelMap = new HashMap<>();

		Map<String, CourseDetails> map = new HashMap<>();
		Country country = null;
		City city = null;
		Course course = null;
		CourseDetails courseDetails = null;
		CoursePricing coursePricing = null;
		CourseGradeEligibility courseGradeEligibility = null;

		int rowIndex = 0;

		while (rowIterator.hasNext()) {

			org.apache.poi.ss.usermodel.Row row = rowIterator.next();
			Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

			if (rowIndex == 0) {
				rowIndex++;
				continue;
			}

			rowIndex++;

			System.out.println(rowIndex);

			int i = 0;

			String C_ID = "", Courses = "", Course_Type = "", University = "", City = "", Country = "", Faculty = "", Recognition = "", Recognition_Type = "",
					Int_Fees = "", Currency = "", Currency_Time = "", Duration_Time = "", IELTS_Overall_Score = "", IELTS_Reading = "", IELTS_Writing = "",
					IELTS_Listening = "", IELTS_Speaking = "", TOFEL_Overall_Score = "", TOFEL_Reading = "", TOFEL_Writing = "", TOFEL_Listening = "",
					TOFEL_Speaking = "";

			Double Duration = null;
			Integer World_Ranking = null;
			Integer stars = null;
			String Cost_Savings = "", Cost_Range = "", Remarks_ = "", Twinning_Program = "", UK_A_LEV_1 = "", UK_A_LEV_2 = "", UK_A_LEV_3 = "", UK_A_LEV_4 = "",
					UK_A_LEV_5 = "", UK_O_LEV1 = "", IND_HSC_ISC_SSC = "", CHINA_GAOKAO_Gao_San = "", CHINA_GAO_ER = "", MAL_STPM_1 = "", MAL_STPM_2 = "",
					MAL_STPM_3 = "", MAL_STPM_4 = "", MAL_STPM_5 = "", MAL_SPM_1 = "", GLOB_A_LEV_1 = "", GLOB_A_LEV_2 = "", GLOB_A_LEV_3 = "",
					GLOB_A_LEV_4 = "", GLOB_A_LEV_5 = "", GLOB_O_LEV1 = "", SGP_A_LEVE_1 = "", SGP_A_LEVE_2 = "", SGP_A_LEVE_3 = "", SGP_A_LEVE_4 = "",
					SGP_A_LEVE_5 = "", SGP_O_LEV1 = "", US_GPA = "", GLOB_IB = "", THA_MathayomSuksa_5_6 = "", PHILIP_HSC = "", NEP_HSC = "", PAK_HSC = "",
					INDO_SMU = "", JAPAN_KSS = "", SRI_A_LEV_1 = "", SRI_A_LEV_2 = "", SRI_A_LEV_3 = "", SRI_A_LEV_4 = "", SRI_A_LEV_5 = "", SRI_O_LEV1 = "",
					GLOB_GPA = "", BAN_HSC = "", IRAN_DM_HSC = "", KSA_DM_HSC = "", UAE_REG_DM_HSC = "", GLOB_DIPLOMA = "", GLOB_IF = "", GLOB_FIRST_YEAR = "",
					GLOB_SECOND_YEAR = "", GLOB_DEGREE = "", WR_Range = "", Website = "", Local_Fees = "", UNION_FEES = "", Rec_Date = "", SAT_USA = "",
					UK_O_LEV2 = "", UK_O_LEV3 = "", UK_O_LEV4 = "", UK_O_LEV5 = "", GLOB_O_LEV2 = "", GLOB_O_LEV3 = "", GLOB_O_LEV4 = "", GLOB_O_LEV5 = "",
					SG_O_LEV2 = "", SG_O_LEV3 = "", SG_O_LEV4 = "", SG_O_LEV5 = "", SRI_O_LEV2 = "", SRI_O_LEV3 = "", SRI_O_LEV4 = "", SRI_O_LEV5 = "",
					MAL_SPM2 = "", MAL_SPM3 = "", MAL_SPM4 = "", MAL_SPM5 = "", CITY_IMG_CNT = "", UNI_IMG_CNT = "", language = "", availbilty = "",
					part_full = "", study_mode = "", Cost_of_living = "", description = "", inst_id = "", abbreviation = "";

			while (cellIterator.hasNext()) {

				org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

				String cellStringValue = "";
				Double cellNumericValue = null;
				Boolean cellBooleanValue = null;

				Integer colunmIndex = cell.getColumnIndex();

				try {
					switch (cell.getCellType()) {
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t");
						cellStringValue = cell.getStringCellValue();
						break;
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t");
						cellNumericValue = cell.getNumericCellValue();
						cellStringValue = String.valueOf(cellNumericValue);
						break;
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "\t");
						cellBooleanValue = cell.getBooleanCellValue();
						cellStringValue = String.valueOf(cellBooleanValue);
						break;
					default:
					}

					if (i == 0) {
						C_ID = cellStringValue;
					}

					if (i == 1) {
						Courses = cellStringValue;
					}

					if (i == 2) {
						Course_Type = cellStringValue;
					}

					if (i == 3) {
						University = cellStringValue;
					}

					if (i == 4) {
						City = cellStringValue;
					}

					if (i == 5) {
						Country = cellStringValue;
					}

					if (i == 6) {
						Faculty = cellStringValue;
					}

					if (i == 7) {
						// World_Ranking = cellStringValue;
					}

					if (i == 8) {
						Recognition = cellStringValue;
					}

					if (i == 9) {
						Recognition_Type = cellStringValue;
					}

					if (i == 10) {
						Int_Fees = cellStringValue;
					}

					if (i == 11) {
						Currency = cellStringValue;
					}

					if (i == 12) {
						Currency_Time = cellStringValue;
					}

					if (i == 13) {
						Duration = Double.valueOf(cellStringValue);
					}

					if (i == 14) {
						Duration_Time = cellStringValue;
					}

					if (i == 15) {
						IELTS_Overall_Score = cellStringValue;
					}

					if (i == 16) {
						IELTS_Reading = cellStringValue;
					}

					if (i == 17) {
						IELTS_Writing = cellStringValue;
					}

					if (i == 18) {
						IELTS_Listening = cellStringValue;
					}

					if (i == 19) {
						IELTS_Speaking = cellStringValue;
					}

					if (i == 20) {
						TOFEL_Overall_Score = cellStringValue;
					}

					if (i == 21) {
						TOFEL_Reading = cellStringValue;
					}

					if (i == 22) {
						TOFEL_Writing = cellStringValue;
					}

					if (i == 23) {
						TOFEL_Listening = cellStringValue;
					}

					if (i == 24) {
						TOFEL_Speaking = cellStringValue;
					}

					if (i == 25) {
						Cost_Savings = cellStringValue;
					}

					if (i == 26) {
						Cost_Range = cellStringValue;
					}

					if (i == 27) {
						Remarks_ = cellStringValue;
					}

					if (i == 28) {
						Twinning_Program = cellStringValue;
					}

					if (i == 29) {
						UK_A_LEV_1 = cellStringValue;
					}

					if (i == 30) {
						UK_A_LEV_2 = cellStringValue;
					}

					if (i == 31) {
						UK_A_LEV_3 = cellStringValue;
					}

					if (i == 32) {
						UK_A_LEV_4 = cellStringValue;
					}

					if (i == 33) {
						UK_A_LEV_5 = cellStringValue;
					}

					if (i == 34) {
						UK_O_LEV1 = cellStringValue;
					}

					if (i == 35) {
						IND_HSC_ISC_SSC = cellStringValue;
					}

					if (i == 36) {
						CHINA_GAOKAO_Gao_San = cellStringValue;
					}

					if (i == 37) {
						CHINA_GAO_ER = cellStringValue;
					}

					if (i == 38) {
						MAL_STPM_1 = cellStringValue;
					}
					if (i == 39) {
						MAL_STPM_2 = cellStringValue;
					}
					if (i == 40) {
						MAL_STPM_3 = cellStringValue;
					}
					if (i == 41) {
						MAL_STPM_4 = cellStringValue;
					}
					if (i == 42) {
						MAL_STPM_5 = cellStringValue;
					}
					if (i == 43) {
						MAL_SPM_1 = cellStringValue;
					}
					if (i == 44) {
						GLOB_A_LEV_1 = cellStringValue;
					}
					if (i == 45) {
						GLOB_A_LEV_2 = cellStringValue;
					}
					if (i == 46) {
						GLOB_A_LEV_3 = cellStringValue;
					}
					if (i == 47) {
						GLOB_A_LEV_4 = cellStringValue;
					}

					if (i == 48) {
						GLOB_A_LEV_5 = cellStringValue;
					}

					if (i == 49) {
						GLOB_O_LEV1 = cellStringValue;
					}

					if (i == 50) {
						SGP_A_LEVE_1 = cellStringValue;
					}

					if (i == 51) {
						SGP_A_LEVE_2 = cellStringValue;
					}

					if (i == 52) {
						SGP_A_LEVE_3 = cellStringValue;
					}

					if (i == 53) {
						SGP_A_LEVE_4 = cellStringValue;
					}

					if (i == 54) {
						SGP_A_LEVE_5 = cellStringValue;
					}

					if (i == 55) {
						SGP_O_LEV1 = cellStringValue;
					}

					if (i == 56) {
						US_GPA = cellStringValue;
					}

					if (i == 57) {
						GLOB_IB = cellStringValue;
					}

					if (i == 58) {
						THA_MathayomSuksa_5_6 = cellStringValue;
					}

					if (i == 59) {
						PHILIP_HSC = cellStringValue;
					}

					if (i == 60) {
						NEP_HSC = cellStringValue;
					}

					if (i == 61) {
						PAK_HSC = cellStringValue;
					}

					if (i == 62) {
						INDO_SMU = cellStringValue;
					}

					if (i == 63) {
						JAPAN_KSS = cellStringValue;
					}

					if (i == 64) {
						SRI_A_LEV_1 = cellStringValue;
					}

					if (i == 65) {
						SRI_A_LEV_2 = cellStringValue;
					}

					if (i == 66) {
						SRI_A_LEV_3 = cellStringValue;
					}

					if (i == 67) {
						SRI_A_LEV_4 = cellStringValue;
					}

					if (i == 68) {
						SRI_A_LEV_5 = cellStringValue;
					}

					if (i == 69) {
						SRI_O_LEV1 = cellStringValue;
					}

					if (i == 70) {
						GLOB_GPA = cellStringValue;
					}

					if (i == 71) {
						BAN_HSC = cellStringValue;
					}

					if (i == 72) {
						IRAN_DM_HSC = cellStringValue;
					}

					if (i == 73) {
						KSA_DM_HSC = cellStringValue;
					}

					if (i == 74) {
						UAE_REG_DM_HSC = cellStringValue;
					}

					if (i == 75) {
						GLOB_DIPLOMA = cellStringValue;
					}

					if (i == 76) {
						GLOB_IF = cellStringValue;
					}

					if (i == 77) {
						GLOB_FIRST_YEAR = cellStringValue;
					}

					if (i == 78) {
						GLOB_SECOND_YEAR = cellStringValue;
					}

					if (i == 79) {
						GLOB_DEGREE = cellStringValue;
					}

					if (i == 80) {
						WR_Range = cellStringValue;
					}

					if (i == 81) {
						Website = cellStringValue;
					}

					if (i == 82) {
						Local_Fees = cellStringValue;
					}

					if (i == 83) {
						UNION_FEES = cellStringValue;
					}

					if (i == 84) {
						Rec_Date = cellStringValue;
					}

					if (i == 85) {
						SAT_USA = cellStringValue;
					}

					if (i == 86) {
						UK_O_LEV2 = cellStringValue;
					}
					if (i == 87) {
						UK_O_LEV3 = cellStringValue;
					}
					if (i == 88) {
						UK_O_LEV4 = cellStringValue;
					}
					if (i == 89) {
						UK_O_LEV5 = cellStringValue;
					}
					if (i == 90) {
						GLOB_O_LEV2 = cellStringValue;
					}
					if (i == 91) {
						GLOB_O_LEV3 = cellStringValue;
					}
					if (i == 92) {
						GLOB_O_LEV4 = cellStringValue;
					}
					if (i == 93) {
						GLOB_O_LEV5 = cellStringValue;
					}
					if (i == 94) {
						SG_O_LEV2 = cellStringValue;
					}
					if (i == 95) {
						SG_O_LEV3 = cellStringValue;
					}
					if (i == 96) {
						SG_O_LEV4 = cellStringValue;
					}
					if (i == 97) {
						SG_O_LEV5 = cellStringValue;
					}
					if (i == 98) {
						SRI_O_LEV2 = cellStringValue;
					}
					if (i == 99) {
						SRI_O_LEV3 = cellStringValue;
					}
					if (i == 100) {
						SRI_O_LEV4 = cellStringValue;
					}
					if (i == 101) {
						SRI_O_LEV5 = cellStringValue;
					}
					if (i == 102) {
						MAL_SPM2 = cellStringValue;
					}
					if (i == 103) {
						MAL_SPM3 = cellStringValue;
					}
					if (i == 104) {
						MAL_SPM4 = cellStringValue;
					}

					if (i == 105) {
						MAL_SPM5 = cellStringValue;
					}
					if (i == 106) {
						CITY_IMG_CNT = cellStringValue;
					}
					if (i == 107) {
						stars = Integer.valueOf(cellStringValue);
					}
					if (i == 108) {
						UNI_IMG_CNT = cellStringValue;
					}
					if (i == 109) {
						language = cellStringValue;
					}
					if (i == 110) {
						availbilty = cellStringValue;
					}
					if (i == 111) {
						part_full = cellStringValue;
					}
					if (i == 112) {
						study_mode = cellStringValue;
					}
					if (i == 113) {
						Cost_of_living = cellStringValue;
					}
					if (i == 114) {
						description = cellStringValue;
					}
					if (i == 115) {
						inst_id = cellStringValue;
					}
					if (i == 116) {
						inst_id = abbreviation;
					}
					i++;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Date createdOn = new Date();
			String createdBy = "AUTO";

			course = new Course();
			courseDetails = new CourseDetails();

			Institute institute = instituteMap.get(University.toLowerCase().replaceAll("[^\\w]", ""));
			if (null == institute) {
				continue;
			}

			Country countryObj = countryMap.get(Country.toLowerCase().replaceAll("[^\\w]", ""));
			if (null == countryObj) {
				continue;
			}

			City cityObj = cityMap.get(City.toLowerCase().replaceAll("[^\\w]", ""));
			if (null == cityObj) {
				continue;
			}

			if (null == institute.getCity().getId()) {
				institute.setCity(cityObj);
				instituteMap.put(University.toLowerCase().replaceAll("[^\\w]", ""), institute);
				institute.setUpdatedBy(createdBy);
				institute.setUpdatedOn(createdOn);
				instituteService.update(institute);
			}

			course.setInstitute(institute);
			course.setCity(cityObj);
			course.setCountry(countryObj);

			Level level = levelMap.get(Course_Type.trim().toLowerCase().replaceAll("[^\\w]", ""));
			boolean isNewLevel = false;
			if (level == null) {
				level = new Level();
				level.setDescription(description);
				level.setIsActive(true);
				level.setIsDeleted(false);
				level.setCode(Course_Type);
				level.setName(Course_Type);
				level.setCreatedBy(createdBy);
				level.setCreatedOn(createdOn);
				levelService.save(level);
				isNewLevel = true;
				courseDetails.setLevelObj(level);
				levelMap.put(Course_Type.trim().toLowerCase().replaceAll("[^\\w]", ""), level);
			}

			Faculty facultyObj = null;
			if (isNewLevel) {
				facultyObj = new Faculty();
				facultyObj.setCreatedBy(createdBy);
				facultyObj.setCreatedOn(createdOn);
				facultyObj.setIsDeleted(false);
				facultyObj.setIsActive(true);
				facultyObj.setName(Faculty.trim());
				facultyObj.setDescription(Faculty.trim());
				facultyObj.setLevel(level);
				facultyService.save(facultyObj);
				courseDetails.setFacultyObj(facultyObj);
				facultyMap.put(Faculty.trim().toLowerCase().replaceAll("[^\\w]", "") + "-" + level.getId(), facultyObj);
			} else {
				facultyObj = facultyMap.get(Faculty.trim().toLowerCase().replaceAll("[^\\w]", "") + "-" + level.getId());
				if (null == facultyObj) {
					facultyObj = new Faculty();
					facultyObj.setCreatedBy(createdBy);
					facultyObj.setCreatedOn(createdOn);
					facultyObj.setIsDeleted(false);
					facultyObj.setIsActive(true);
					facultyObj.setName(Faculty.trim());
					facultyObj.setDescription(Faculty.trim());
					facultyObj.setLevel(level);
					facultyService.save(facultyObj);
					courseDetails.setFacultyObj(facultyObj);
					facultyMap.put(Faculty.toLowerCase().replaceAll("[^\\w]", "") + "-" + level.getId(), facultyObj);
				} else {
					course.setFaculty(facultyObj);
					courseDetails.setFacultyObj(facultyObj);
				}
			}

			InstituteLevel instituteLevel = instituteLevelMap.get(institute.getId() + "-" + level.getId());
			if (null == instituteLevel) {
				instituteLevel = new InstituteLevel();
				instituteLevel.setCity(cityObj);
				instituteLevel.setCountry(countryObj);
				instituteLevel.setCreatedBy(createdBy);
				instituteLevel.setCreatedOn(createdOn);
				instituteLevel.setIsDeleted(false);
				instituteLevel.setIsActive(true);
				instituteLevel.setInstitute(institute);
				instituteLevel.setLevel(level);
				// instituteLevelService.save(instituteLevel);
				instituteLevelMap.put(institute.getId() + "-" + level.getId(), instituteLevel);
				courseDetails.setInstituteLevelObj(instituteLevel);
			}

			FacultyLevel facultyLevel = facultyLevelMap.get(institute.getId() + "-" + facultyObj.getId());
			if (null == facultyLevel) {
				facultyLevel = new FacultyLevel();
				facultyLevel.setCreatedBy(createdBy);
				facultyLevel.setCreatedOn(createdOn);
				facultyLevel.setIsDeleted(false);
				facultyLevel.setIsActive(true);
				facultyLevel.setFaculty(facultyObj);
				facultyLevel.setInstitute(institute);
				// facultyLevelService.save(facultyLevel);
				facultyLevelMap.put(institute.getId() + "-" + facultyObj.getId(), facultyLevel);
				courseDetails.setFacultyLevelObj(facultyLevel);
			}
			course.setAbbreviation(abbreviation);
			course.setLanguage(language);
			course.setDescription(description);
			course.setDuration(Duration);
			course.setDurationTime(Duration_Time);
			// course.setFacultyObj(facultyObj);
			// course.setInstituteObj(instituteObj);
			course.setIsActive(true);
			course.setIsDeleted(false);
			course.setName(Courses);
			course.setRecognition(Recognition);
			course.setRecognitionType(Recognition_Type);
			// course.setRecordedDate(Rec_Date);
			course.setRemarks(Remarks_);
			if (null != stars) {
				course.setStars(stars);
			} else {
				course.setStars(0);
			}

			course.setWebsite(Website);

			courseDetails.setAvailbilty(availbilty);
			// courseDetails.setCourseId(courseId);
			courseDetails.setDescription(description);

			if (null != part_full && !part_full.equals("") && !part_full.isEmpty()) {
				courseDetails.setPartFull(part_full);
			}
			if (null != study_mode && !study_mode.equals("") && !study_mode.isEmpty()) {
				courseDetails.setStudyMode(study_mode);
			}
			if (null != WR_Range && !WR_Range.equals("") && !WR_Range.isEmpty()) {
				courseDetails.setWrRange(WR_Range);
			}

			coursePricing = new CoursePricing();
			if (null != Local_Fees && !Local_Fees.isEmpty()) {
				coursePricing.setLocalFees(Double.valueOf(Local_Fees));
			}
			if (null != UNION_FEES && !UNION_FEES.equals("") && !UNION_FEES.isEmpty()) {
				coursePricing.setUnionFees(Double.valueOf(UNION_FEES));
			}
			if (null != Int_Fees && !Int_Fees.equals("") && !Int_Fees.isEmpty()) {
				coursePricing.setIntlFees(Double.valueOf(Int_Fees));
			}
			if (null != Cost_Range && !Cost_Range.equals("") && !Cost_Range.isEmpty()) {
				coursePricing.setCostRange(Double.valueOf(Cost_Range));
			}

			if (null != Cost_Savings && !Cost_Savings.equals("") && !Cost_Savings.isEmpty()) {
				coursePricing.setCostSavings(Double.valueOf(Cost_Savings));
			}
			coursePricing.setCurrency(Currency);
			coursePricing.setCurrencyTime(Currency_Time);
			coursePricing.setIsActive(true);
			coursePricing.setIsDeleted(false);
			coursePricing.setCreatedBy(createdBy);
			coursePricing.setCreatedOn(createdOn);
			courseDetails.setCourse(course);
			courseDetails.setCoursePricingObj(coursePricing);

			CourseGradeEligibility gradeObj = new CourseGradeEligibility();
			// gradeObj.setGlobalALevel1(GLOB_A_LEV_1);
			// gradeObj.setGlobalALevel2(GLOB_A_LEV_2);
			// gradeObj.setGlobalALevel3(GLOB_A_LEV_3);
			// gradeObj.setGlobalALevel4(GLOB_A_LEV_4);
			// gradeObj.setGlobalALevel5(GLOB_A_LEV_5);
			// gradeObj.setGlobalGpa(Double.valueOf(GLOB_GPA));
			// gradeObj.setIsActive(true);

			String courseName = University.toLowerCase().replaceAll("[^\\w]", "") + Courses.toLowerCase().replaceAll("[^\\w]", "");
			map.put(courseName, courseDetails);
			System.out.println("");
		}
		System.out.println("Total Course: " + map.size());
		return map;
	}

}
