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
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService; 
 

@RestController
@RequestMapping("/migrate")
public class MigrateInstituteController {
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ICityService cityService;
	
	@Autowired
	IInstituteDetailsService instituteDetailsService;
	
	@Autowired
	IInstituteService instituteService;
	
	
	
	public Map<String, Institute> get() throws Exception{
		File myFile = new File("E:\\Softwares\\Seeka\\March-2019\\Course\\institue\\university_names.xlsx"); 
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
		XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();
		
		Map<String, Institute> map = new HashMap<String, Institute>();
		Institute object = null;
		
		List<Country> countryList = countryService.getAll();
		Map<String, Country> countryMap =  new HashMap<String, Country>();
		for (Country country : countryList) {
			countryMap.put(country.getCountryCode().toLowerCase().replaceAll("[^\\w]", ""), country);
			countryMap.put(country.getName().toLowerCase().replaceAll("[^\\w]", ""), country);
		}
		
		List<City> list = cityService.getAll();
		Map<String, City> cityMap =  new HashMap<String, City>();
		for (City city : list) {
			cityMap.put(city.getName().toLowerCase().replaceAll("[^\\w]", ""), city);
		}
		
		
		while (rowIterator.hasNext()) { 
			
			org.apache.poi.ss.usermodel.Row row = rowIterator.next(); 
			Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator(); 
			
			int i =0;
			
			String Univeristy_Name = "";
			String Country = "";
			String Accredited = "";	
			String Int_Ph_num = "";	
			String Int_Emails = "";	
			String Website	 = "";
			String T_num_of_stu	 = "";
			String Latitude	 = "";
			String Longitude = "";	
			String Address	 = "";
			String Schlr_finan_asst	 = "";
			String Type	 = "";
			String YOUTUBE_Link	 = "";
			String Ttion_fees_p_plan	 = "";
			String Personal_Coun_acad	 = "";
			String Visa_Work_Benefits	 = "";
			String Emp_career_dev = "";	
			String course_start	 = "";
			String Study_Library_Support	 = "";
			String Health_services	 = "";
			String Disability_Support	 = "";
			String Childcare_Centre	 = "";
			String Cult_incl_antiracism_prg	 = "";
			String Tech_Serv	 = "";
			String Accommodation	 = "";
			String Medical	 = "";
			String Leg_Service	 = "";
			String Acct_Serv	 = "";
			String Bus	 = "";
			String Train	 = "";
			String Airport_Pickup	 = "";
			String Swimming_pool	 = "";
			String Sports_Center	 = "";
			String Sport_Teams	 = "";
			String Housing_Services	 = "";
			String Opening_hour	 = "";
			String Closing_hour	 = "";
			String Enrolment_Link	 = "";
			String About_Us_Info	 = "";
			String IMG_COUNT	 = "";
			String scholarship	 = "";
			String govt_loan	 = "";
			String paymt_plan	 = "";
			String climate_2	 = "";
			String avg_cost_of_living	 = "";
			String whatsapp_no	 = "";
			String english_partners = "";
			
			
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
						case  org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC: 
							System.out.print(cell.getNumericCellValue() + "\t"); 
							cellNumericValue = cell.getNumericCellValue();
							break; 
						case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN: 
							System.out.print(cell.getBooleanCellValue() + "\t"); 
							cellBooleanValue = cell.getBooleanCellValue();
							break; 
						default : 
					} 
					
					if(i == 0) {
						//d_id = cellStringValue;
					}
					
					if(i == 1) {
						Univeristy_Name = cellStringValue;
					}
					
					if(i == 2) {
						Country = cellStringValue;
					}
					
					if(i == 3) {
						Accredited = cellStringValue;
					}
					
					if(i == 4) {
						Int_Ph_num = cellStringValue;
					}
					
					if(i == 5) {
						Int_Emails = cellStringValue;
					}
					
					if(i == 6) {
						Website = cellStringValue;
					}
					
					if(i == 7) {
						T_num_of_stu = cellStringValue;
					} 
					
					if(i == 8) {
						Latitude = cellStringValue;
					} 
					
					if(i == 9) {
						Longitude = cellStringValue;
					} 
					
					if(i == 10) {
						Address = cellStringValue;
					} 
					
					if(i == 11) {
						Schlr_finan_asst = cellStringValue;
					} 
					
					if(i == 12) {
						Type = cellStringValue;
					} 
					
					if(i == 13) {
						YOUTUBE_Link = cellStringValue;
					} 
					
					if(i == 14) {
						Ttion_fees_p_plan = cellStringValue;
					} 
					
					if(i == 15) {
						Personal_Coun_acad = cellStringValue;
					} 
					
					if(i == 16) {
						Visa_Work_Benefits = cellStringValue;
					} 
					
					if(i == 17) {
						Emp_career_dev = cellStringValue;
					} 
					
					if(i == 18) {
						course_start = cellStringValue;
					} 
					
					if(i == 19) {
						Study_Library_Support = cellStringValue;
					} 
					
					if(i == 20) {
						Health_services = cellStringValue;
					} 
					
					if(i == 21) {
						Disability_Support = cellStringValue;
					} 
					
					if(i == 22) {
						Childcare_Centre = cellStringValue;
					} 
					
					if(i == 23) {
						Cult_incl_antiracism_prg = cellStringValue;
					} 
					
					if(i == 24) {
						Tech_Serv = cellStringValue;
					} 
					
					if(i == 25) {
						Accommodation = cellStringValue;
					} 
					
					
					if(i == 26) {
						Medical = cellStringValue;
					} 
					
					
					if(i == 27) {
						Leg_Service = cellStringValue;
					} 
					
					if(i == 28) {
						Acct_Serv = cellStringValue;
					} 
					
					if(i == 29) {
						Bus = cellStringValue;
					} 
					
					if(i == 30) {
						Train = cellStringValue;
					} 
					
					if(i == 31) {
						Airport_Pickup = cellStringValue;
					}  
					
					
					if(i == 32) {
						Swimming_pool = cellStringValue;
					} 
					
					if(i == 33) {
						Sports_Center = cellStringValue;
					} 
					
					if(i == 34) {
						Sport_Teams = cellStringValue;
					} 
					
					if(i == 35) {
						Housing_Services = cellStringValue;
					} 
					
					if(i == 36) {
						Opening_hour = cellStringValue;
					} 
					
					if(i == 37) {
						Closing_hour = cellStringValue;
					} 
					
					if(i == 38) {
						Enrolment_Link = cellStringValue;
					} 
					if(i == 39) {
						About_Us_Info = cellStringValue;
					} 
					if(i == 40) {
						IMG_COUNT = cellStringValue;
					} 
					if(i == 41) {
						scholarship = cellStringValue;
					} 
					if(i == 42) {
						govt_loan = cellStringValue;
					} 
					if(i == 43) {
						paymt_plan = cellStringValue;
					} 
					if(i == 44) {
						climate_2 = cellStringValue;
					} 
					if(i == 45) {
						avg_cost_of_living = cellStringValue;
					} 
					if(i == 46) {
						whatsapp_no = cellStringValue;
					} 
					if(i == 47) {
						english_partners = cellStringValue;
					} 
					
					i++;
					
					object = new Institute();
					
					Country countryObj = countryMap.get(Country.toLowerCase().replaceAll("[^\\w]", ""));
					
					if(null == countryObj) {
						continue;
					}
					object.setCountryObj(countryObj);
					object.setAccredited(Accredited);
					object.setAddress(Address);
					
					/*City cityObj = new City();
					cityObj.setName("CITYNAME");
					object.setCityObj(cityObj);*/
					//object.setDescription("");
					
					if(null != IMG_COUNT && !IMG_COUNT.isEmpty()) {
						object.setInsImageCount(Integer.parseInt(IMG_COUNT));
					}else {
						object.setInsImageCount(0);
					}
					InstituteType instituteTypeObj = new InstituteType();
					instituteTypeObj.setId(1);
					
					object.setInstituteTypeObj(instituteTypeObj);
					object.setInterEmail(Int_Emails);
					object.setInterPhoneNumber(Int_Ph_num);
					object.setIsActive(true);
					object.setIsDeleted(false);
					object.setLatitude(Latitude);
					object.setLongitude(Longitude);
					object.setName(Univeristy_Name);
					object.setCreatedBy("AUTO");
					object.setCreatedOn(new Date());
					if(null != T_num_of_stu && !T_num_of_stu.isEmpty()) {
						object.setTotalNoOfStudent(Integer.parseInt(T_num_of_stu));
					}else{
						object.setTotalNoOfStudent(0);
					}
					object.setWebsite(Website); 
					
					InstituteDetails instituteDetails = new InstituteDetails();
					instituteDetails.setAboutUsInfo(About_Us_Info);
					instituteDetails.setAverageCostOfLiving(avg_cost_of_living);
					instituteDetails.setClimate2(climate_2);
					instituteDetails.setClosingHour(Closing_hour);
					instituteDetails.setCourseStart(course_start);
					instituteDetails.setEnglishPartners(english_partners);
					instituteDetails.setEnrolmentLink(Enrolment_Link);
					instituteDetails.setOpeningHour(Opening_hour);
					instituteDetails.setTutionFeesPaymentPlan(Ttion_fees_p_plan);
					instituteDetails.setType(Type);
					instituteDetails.setWhatsappNo(whatsapp_no);
					instituteDetails.setYoutubeLink(YOUTUBE_Link);
					object.setInstituteDetailsObj(instituteDetails);
					 
	                String univName = Univeristy_Name.replaceAll("[^\\w]", "");
	                map.put(univName, object);
				}catch(Exception e) {
					e.printStackTrace();
				}
			} 
			System.out.println(""); 
		}
		System.out.println(map.size());
		return map;
	}
	
	
	@RequestMapping(value = "/institue", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> migrateCityData() throws Exception {
		Map<String, Institute> map = get();
		int i =0;
		for(String name : map.keySet()) {
			try {
				i++;
				Institute masterObj = map.get(name);
				InstituteDetails instituteDetails =  masterObj.getInstituteDetailsObj();
				instituteService.save(masterObj);
				
				instituteDetails.setInstituteId(masterObj.getId());
				instituteDetailsService.save(instituteDetails);
				System.out.println("---------------------------------------------------------->"+i);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		Map<String,Object> response = new HashMap<String, Object>();
        response.put("status", 1);
		response.put("message","Success.!");
		return ResponseEntity.accepted().body(response);
	}

}
