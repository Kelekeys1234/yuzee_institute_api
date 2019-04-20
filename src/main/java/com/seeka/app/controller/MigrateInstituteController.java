package com.seeka.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.seeka.app.bean.InstituteServiceDetails;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.ServiceDetails;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IServiceDetailsService; 
 

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
	
	@Autowired
	IServiceDetailsService serviceDetailsService;
	
	@Autowired
	IInstituteServiceDetailsService instituteServiceDetailsService;
	
	
	
	public Map<String, Institute> get() throws Exception{
		File myFile = new File("E:\\Softwares\\Seeka\\March-2019\\Course\\University\\university_names_malaysia.xlsx"); 
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
		XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();
		
		Map<String, Institute> map = new HashMap<String, Institute>();
		Institute object = null;
		
		
		List<ServiceDetails> serviceDetailList = serviceDetailsService.getAll();
		Map<String, ServiceDetails> serviceMap =  new HashMap<String, ServiceDetails>();
		
		for (ServiceDetails serviceObj : serviceDetailList) {
			String key = serviceObj.getName().replaceAll("[^\\w]", "").toLowerCase();
			serviceMap.put(key, serviceObj);
		}
		
		
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
		
		int rowCount = 0;
		while (rowIterator.hasNext()) { 
			rowCount++;
			if(rowCount == 1) {
				continue;
			}
			
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
							cellStringValue = String.valueOf(cellNumericValue);
							break; 
						case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN: 
							System.out.print(cell.getBooleanCellValue() + "\t"); 
							cellBooleanValue = cell.getBooleanCellValue();
							cellStringValue = String.valueOf(cellNumericValue);
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
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
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
				object.setInsImageCount(Double.valueOf(IMG_COUNT).intValue());
			}else {
				object.setInsImageCount(0);
			}
			InstituteType instituteTypeObj = new InstituteType();
			instituteTypeObj.setId(UUID.randomUUID());
			
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
				object.setTotalNoOfStudent(Double.valueOf(T_num_of_stu).intValue()); 
			}else{
				object.setTotalNoOfStudent(0);
			}
			object.setWebsite(Website); 
	 
			List<InstituteServiceDetails> insServiceList = new ArrayList<>();
			InstituteServiceDetails insServiceDetailObj = null;
			 
			
			if(null != Visa_Work_Benefits && !Visa_Work_Benefits.isEmpty() && Visa_Work_Benefits.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "visaworkbenefits", Visa_Work_Benefits);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Emp_career_dev && !Emp_career_dev.isEmpty() && Emp_career_dev.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "employmentandcareerdevelopment", Emp_career_dev);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Personal_Coun_acad && !Personal_Coun_acad.isEmpty() && Personal_Coun_acad.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "counsellingpersonalandacademic", Personal_Coun_acad);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Study_Library_Support && !Study_Library_Support.isEmpty() && Study_Library_Support.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "studylibrarysupport", Study_Library_Support);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Health_services && !Health_services.isEmpty() && Health_services.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "healthservices", Health_services);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Disability_Support && !Disability_Support.isEmpty() && Disability_Support.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "disabilitysupport", Disability_Support);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Childcare_Centre && !Childcare_Centre.isEmpty() && Childcare_Centre.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "childcarecentre", Childcare_Centre);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Cult_incl_antiracism_prg && !Cult_incl_antiracism_prg.isEmpty() && Cult_incl_antiracism_prg.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "culturalinclusionantiracismprograms", Cult_incl_antiracism_prg);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Tech_Serv && !Tech_Serv.isEmpty() && Tech_Serv.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "technologyservices", Tech_Serv);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Accommodation && !Accommodation.isEmpty() && Accommodation.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "accommodation", Accommodation);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Medical && !Medical.isEmpty() && Medical.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "Medical", Medical);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Leg_Service && !Leg_Service.isEmpty() && Leg_Service.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "legalservices", Leg_Service);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Acct_Serv && !Acct_Serv.isEmpty() && Acct_Serv.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "accountingservices", Acct_Serv);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Bus && !Bus.isEmpty() && Bus.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "bus", Bus);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Train && !Train.isEmpty() && Train.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "train", Train);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Airport_Pickup && !Airport_Pickup.isEmpty() && Airport_Pickup.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "airportpickup", Airport_Pickup);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Swimming_pool && !Swimming_pool.isEmpty() && Swimming_pool.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "swimmingpool", Swimming_pool);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Sports_Center && !Sports_Center.isEmpty() && Sports_Center.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "sportscenter", Sports_Center);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Sport_Teams && !Sport_Teams.isEmpty() && Sport_Teams.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "sportteams", Sport_Teams);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != Housing_Services && !Housing_Services.isEmpty() && Housing_Services.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "housingservices", Housing_Services);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != scholarship && !scholarship.isEmpty() && scholarship.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "scholarship", scholarship);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != govt_loan && !govt_loan.isEmpty() && govt_loan.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "govtloan", govt_loan);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
			if(null != paymt_plan && !paymt_plan.isEmpty() && paymt_plan.length() > 0 ) {
				insServiceDetailObj = getInsService(serviceMap, "paymentplan", paymt_plan);
				if(insServiceDetailObj != null) {
					insServiceList.add(insServiceDetailObj);
				}
			}
			
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
			object.setServiceList(insServiceList);
			
            String univName = Univeristy_Name.replaceAll("[^\\w]", "");
            map.put(univName, object);
			System.out.println(""); 
		}
		System.out.println(map.size());
		return map;
	}
	
	public InstituteServiceDetails getInsService(Map<String, ServiceDetails> serviceMap, String serviceName, String field) {
		try {
			ServiceDetails details = serviceMap.get(serviceName);
			if(null != details) {
				InstituteServiceDetails serviceDetails = new InstituteServiceDetails();
				serviceDetails.setServiceObj(details);
				serviceDetails.setIsActive(Boolean.valueOf(String.valueOf(Double.valueOf(field).intValue())));
				return serviceDetails;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = "/get/service", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllService() throws Exception {
		List<ServiceDetails> serviceDetailList = serviceDetailsService.getAll();
		List<String> serviceName = new ArrayList<>();
		
		for (ServiceDetails serviceObj : serviceDetailList) {
			String key = serviceObj.getName().replaceAll("[^\\w]", "").toLowerCase();
			serviceName.add(key);
		}
		Map<String,Object> response = new HashMap<String, Object>();
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("serviceNames",serviceName);
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/institue", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> migrateInsituteData() throws Exception {
		Map<String, Institute> map = get();
		int i =0;
		for(String name : map.keySet()) {
			try {
				i++;
				Institute masterObj = map.get(name);
				
				InstituteDetails instituteDetails =  masterObj.getInstituteDetailsObj();
				instituteService.save(masterObj);
				
				List<InstituteServiceDetails> serviceList = masterObj.getServiceList();
				if(null != serviceList && !serviceList.isEmpty() && serviceList.size() >0) {
					for (InstituteServiceDetails obj : serviceList) {
						try {
							obj.setInstituteObj(masterObj);
							obj.setCreatedBy("AUTO");
							obj.setCreatedOn(new Date());
							obj.setIsDeleted(false);
							instituteServiceDetailsService.save(obj);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
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
