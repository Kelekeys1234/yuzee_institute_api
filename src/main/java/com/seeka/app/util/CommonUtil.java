package com.seeka.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Todo;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.InstituteCampusDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.TodoDto;

public class CommonUtil {

	/*public static Country convertDTOToBean(final CountryRequestDto countryRequestDto) {
		ObjectMapper mapper = new ObjectMapper();
		Country country = null;
		try {
			country = mapper.readValue(mapper.writeValueAsString(countryRequestDto), Country.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return country;
	}

	public static CountryDetails convertCountryDetailsDTOToBean(final CountryDetailsDto countryDetailsDto, final Country country) {
		ObjectMapper mapper = new ObjectMapper();
		CountryDetails countryDetails = null;
		try {
			countryDetails = mapper.readValue(mapper.writeValueAsString(countryDetailsDto), CountryDetails.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countryDetails;
	}

	public static CountryImages convertCountryImageDTOToBean(final CountryImageDto dto, final Country country) {
		ObjectMapper mapper = new ObjectMapper();
		CountryImages countryImages = null;
		try {
			countryImages = mapper.readValue(mapper.writeValueAsString(dto), CountryImages.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countryImages;
	}

	public static City convertCityDTOToBean(final CityDto cityObj, final Country country) {
		City city = new City();
		city.setCountry(country);
		city.setName(cityObj.getName());
		city.setCreatedOn(new Date());
		city.setUpdatedOn(new Date());
		return city;
	}*/

	public static InstituteRequestDto convertInstituteBeanToInstituteRequestDto(final Institute institute) {
		InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
		instituteRequestDto.setAvgCostOfLiving(institute.getAvgCostOfLiving());
		instituteRequestDto.setCityName(institute.getCityName());
		instituteRequestDto.setCountryName(institute.getCountryName());
		instituteRequestDto.setDescription(institute.getDescription());
		instituteRequestDto.setId(institute.getId());
		instituteRequestDto.setWebsite(institute.getWebsite());
		if (institute.getInstituteType() != null) {
			instituteRequestDto.setInstituteTypeId(institute.getInstituteType().getId());
		}
		if (institute.getLatitute() != null) {
			instituteRequestDto.setLatitude(institute.getLatitute());
		}
		if (institute.getLongitude() != null) {
			instituteRequestDto.setLongitude(institute.getLongitude());
		}
		instituteRequestDto.setAddress(institute.getAddress());
		instituteRequestDto.setPhoneNumber(institute.getPhoneNumber());
		instituteRequestDto.setOpeningFrom(institute.getOpeningFrom());
		instituteRequestDto.setOpeningTo(institute.getOpeningTo());
		instituteRequestDto.setTotalStudent(institute.getTotalStudent());
		instituteRequestDto.setWorldRanking(institute.getWorldRanking());
		instituteRequestDto.setName(institute.getName());
		instituteRequestDto.setCampusType(institute.getCampusType());
		instituteRequestDto.setCampusName(institute.getCampusName());
		instituteRequestDto.setEmail(institute.getEmail());
		instituteRequestDto.setEnrolmentLink(institute.getEnrolmentLink());
		instituteRequestDto.setScholarshipFinancingAssistance(institute.getScholarshipFinancingAssistance());
		instituteRequestDto.setTuitionFessPaymentPlan(institute.getTuitionFessPaymentPlan());
		instituteRequestDto.setWhatsNo(institute.getWhatsNo());
		instituteRequestDto.setAboutInfo(institute.getAboutInfo());
		instituteRequestDto.setCourseStart(institute.getCourseStart());
		return instituteRequestDto;
	}

	public static CourseRequest convertCourseDtoToCourseRequest(final Course course) {
		CourseRequest courseRequest = new CourseRequest();
		courseRequest.setId(course.getId());
		if (course.getStars() != null) {
			courseRequest.setStars(String.valueOf(course.getStars()));
		}
		if (course.getDuration() != null) {
			courseRequest.setDuration(String.valueOf(course.getDuration()));
		}
		if (course.getWorldRanking() != null) {
			courseRequest.setWorldRanking(String.valueOf(course.getWorldRanking()));
		}
		if (course.getDescription() != null) {
			courseRequest.setDescription(course.getDescription());
		} else {
			courseRequest.setDescription(IConstant.COURSE_DEFAULT_DESCRPTION);
		}

		courseRequest.setDurationTime(course.getDurationTime());

		courseRequest.setName(course.getName());
		courseRequest.setLink(course.getLink());
		if (course.getFaculty() != null) {
			courseRequest.setFacultyId(course.getFaculty().getId());
			courseRequest.setFacultyName(course.getFaculty().getName());
		}
		courseRequest.setDomasticFee(course.getUsdDomasticFee());
		courseRequest.setInternationalFee(course.getUsdInternationFee());
		courseRequest.setGrades(course.getGrades());
		courseRequest.setContact(course.getContact());
		courseRequest.setCampusLocation(course.getCampusLocation());
		courseRequest.setCurrency(course.getCurrency());
		courseRequest.setWebsite(course.getWebsite());
		courseRequest.setPartFull(course.getPartFull());
		courseRequest.setStudyMode(course.getStudyMode());
		courseRequest.setAvailbility(course.getAvailbilty());
		courseRequest.setJobFullTime(course.getJobFullTime());
		courseRequest.setJobPartTime(course.getJobPartTime());
		courseRequest.setOpeningHourFrom(course.getOpeningHourFrom());
		courseRequest.setOpeningHourTo(course.getOpeningHourTo());
		if (course.getInstitute() != null) {
			courseRequest.setInstituteId(course.getInstitute().getId());
		}
		if (course.getLevel() != null) {
			courseRequest.setLevelId(course.getLevel().getId());
		}
		return courseRequest;
	}

	public static Map<String, Double> getCurrencyDetails(final String baseCurrency) {
		String currencyResponse = null;
		URL url = null;
		try {
			url = new URL(IConstant.CURRENCY_URL + "latest?access_key=" + IConstant.API_KEY + "&base=" + baseCurrency);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonobj = root.getAsJsonObject();
			currencyResponse = jsonobj.toString();
			JsonObject rateObject = jsonobj.get("rates").getAsJsonObject();
			ObjectMapper mapper = new ObjectMapper();

			Map<String, Double> ratesMap = mapper.readValue(rateObject.getAsString(), HashMap.class);
			return /* currencyResponse */ratesMap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return new HashMap<>();

	}

	public static InstituteCampusDto convertInstituteCampusToInstituteCampusDto(final Institute campus) {
		InstituteCampusDto campusDto = new InstituteCampusDto();
		campusDto.setAddress(campus.getAddress());
		campusDto.setEmail(campus.getEmail());
		campusDto.setLatitute(campus.getLatitute());
		campusDto.setLongitute(campus.getLongitude());
		campusDto.setOpeningFrom(campus.getOpeningFrom());
		campusDto.setOpeningTo(campus.getOpeningTo());
		campusDto.setPhoneNumber(campus.getPhoneNumber());
		campusDto.setTotalStudent(campus.getTotalStudent());
		campusDto.setId(campus.getId());
		campusDto.setCampusType(campus.getCampusType());
		return campusDto;
	}

	public static Todo convertTodoDtoIntoTodo(final TodoDto todoDto) {
		Todo todo = new Todo();
		todo.setDescription(todoDto.getDescription());
		todo.setTitle(todoDto.getTitle());
		todo.setUserId(todoDto.getUserId());
		todo.setStatus(todoDto.getStatus());
		todo.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		todo.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		todo.setFolderId(todoDto.getFolderId());
		todo.setCreatedBy(todoDto.getCreatedBy());
		todo.setUpdatedBy(todoDto.getUpdatedBy());
		todo.setIsActive(true);
		if (todoDto.getDueDate() != null) {
			todo.setDueDate(DateUtil.convertStringDateToDate(todoDto.getDueDate()));
		}
		return todo;
	}

	public static TodoDto convertTodoIntoTodoDto(final Todo todo) {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(todo.getId());
		todoDto.setDescription(todo.getDescription());
		todoDto.setTitle(todo.getTitle());
		todoDto.setUserId(todo.getUserId());
		todoDto.setStatus(todo.getStatus());
		todoDto.setFolderId(todo.getFolderId());
		todoDto.setCreatedBy(todo.getCreatedBy());
		todoDto.setUpdatedBy(todo.getUpdatedBy());
		if (todo.getDueDate() != null) {
			todoDto.setDueDate(DateUtil.convertDateToString(todo.getDueDate()));
		}
		return todoDto;
	}

	public static Double foundOff2Digit(final Double convertedRate) {
		System.out.println("double : " + convertedRate);
		BigDecimal bd = new BigDecimal(convertedRate).setScale(2, RoundingMode.HALF_UP);
		Double roundedDigit = bd.doubleValue();
		System.out.println("rounded digit : " + roundedDigit);
		return roundedDigit;
	}

	public static Date getDateWithoutTime(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getTomorrowDate(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static final Map<String, String> currencyNameMap = new HashMap<>();

	public static void setCurrencyNames() {
		currencyNameMap.put("FJD", "Fijian Dollar");
		currencyNameMap.put("MXN", "Mexican Peso");
		currencyNameMap.put("STD", "São Tomé and Príncipe Dobra");
		currencyNameMap.put("LVL", "Latvian Lats");
		currencyNameMap.put("SCR", "Seychellois Rupee");
		currencyNameMap.put("CDF", "Congolese Franc");
		currencyNameMap.put("BBD", "Barbadian Dollar");
		currencyNameMap.put("GTQ", "Guatemalan Quetzal");
		currencyNameMap.put("CLP", "Chilean Peso");
		currencyNameMap.put("HNL", "Honduran Lempira");
		currencyNameMap.put("UGX", "Ugandan Shilling");
		currencyNameMap.put("ZAR", "South African Rand");
		currencyNameMap.put("TND", "Tunisian Dinar");
		currencyNameMap.put("CUC", "Cuban Convertible Peso");
		currencyNameMap.put("BSD", "Bahamian Dollar");
		currencyNameMap.put("SLL", "Sierra Leonean Leone");
		currencyNameMap.put("SDG", "Sudanese Pound");
		currencyNameMap.put("IQD", "Iraqi Dinar");
		currencyNameMap.put("CUP", "Cuban Peso");
		currencyNameMap.put("GMD", "Gambian Dalasi");
		currencyNameMap.put("TWD", "New Taiwan Dollar");
		currencyNameMap.put("RSD", "Serbian Dinar");
		currencyNameMap.put("DOP", "Dominican Peso");
		currencyNameMap.put("KMF", "Comorian Franc");
		currencyNameMap.put("MYR", "Malaysian Ringgit");
		currencyNameMap.put("FKP", "Falkland Islands Pound");
		currencyNameMap.put("XOF", "CFA Franc BCEAO");
		currencyNameMap.put("GEL", "Georgian Lari");
		currencyNameMap.put("BTC", "Bitcoin");
		currencyNameMap.put("UYU", "Uruguayan Peso");
		currencyNameMap.put("MAD", "Moroccan Dirham");
		currencyNameMap.put("CVE", "Cape Verdean Escudo");
		currencyNameMap.put("TOP", "Tongan Paʻanga");
		currencyNameMap.put("AZN", "Azerbaijani Manat");
		currencyNameMap.put("OMR", "Omani Rial");
		currencyNameMap.put("PGK", "Papua New Guinean Kina");
		currencyNameMap.put("KES", "Kenyan Shilling");
		currencyNameMap.put("SEK", "Swedish Krona");
		currencyNameMap.put("BTN", "Bhutanese Ngultrum");
		currencyNameMap.put("UAH", "Ukrainian Hryvnia");
		currencyNameMap.put("GNF", "Guinean Franc");
		currencyNameMap.put("ERN", "Eritrean Nakfa");
		currencyNameMap.put("MZN", "Mozambican Metical");
		currencyNameMap.put("SVC", "Salvadoran Colón");
		currencyNameMap.put("ARS", "Argentine Peso");
		currencyNameMap.put("QAR", "Qatari Rial");
		currencyNameMap.put("IRR", "Iranian Rial");
		currencyNameMap.put("MRO", "Mauritanian Ouguiya");
		currencyNameMap.put("CNY", "Chinese Yuan");
		currencyNameMap.put("THB", "Thai Baht");
		currencyNameMap.put("UZS", "Uzbekistan Som");
		currencyNameMap.put("XPF", "CFP Franc");
		currencyNameMap.put("BDT", "Bangladeshi Taka");
		currencyNameMap.put("LYD", "Libyan Dinar");
		currencyNameMap.put("BMD", "Bermudan Dollar");
		currencyNameMap.put("KWD", "Kuwaiti Dinar");
		currencyNameMap.put("PHP", "Philippine Peso");
		currencyNameMap.put("RUB", "Russian Ruble");
		currencyNameMap.put("PYG", "Paraguayan Guarani");
		currencyNameMap.put("ISK", "Icelandic Króna");
		currencyNameMap.put("JMD", "Jamaican Dollar");
		currencyNameMap.put("COP", "Colombian Peso");
		currencyNameMap.put("MKD", "Macedonian Denar");
		currencyNameMap.put("USD", "United States Dollar");
		currencyNameMap.put("DZD", "Algerian Dinar");
		currencyNameMap.put("PAB", "Panamanian Balboa");
		currencyNameMap.put("GGP", "Guernsey Pound");
		currencyNameMap.put("SGD", "Singapore Dollar");
		currencyNameMap.put("ETB", "Ethiopian Birr");
		currencyNameMap.put("JEP", "Jersey Pound");
		currencyNameMap.put("KGS", "Kyrgystani Som");
		currencyNameMap.put("SOS", "Somali Shilling");
		currencyNameMap.put("VEF", "Venezuelan Bolívar Fuerte");
		currencyNameMap.put("VUV", "Vanuatu Vatu");
		currencyNameMap.put("LAK", "Laotian Kip");
		currencyNameMap.put("BND", "Brunei Dollar");
		currencyNameMap.put("ZMK", "Zambian Kwacha (pre-2013)");
		currencyNameMap.put("XAF", "CFA Franc BEAC");
		currencyNameMap.put("LRD", "Liberian Dollar");
		currencyNameMap.put("XAG", "Silver (troy ounce)");
		currencyNameMap.put("CHF", "Swiss Franc");
		currencyNameMap.put("HRK", "Croatian Kuna");
		currencyNameMap.put("ALL", "Albanian Lek");
		currencyNameMap.put("DJF", "Djiboutian Franc");
		currencyNameMap.put("ZMW", "Zambian Kwacha");
		currencyNameMap.put("TZS", "Tanzanian Shilling");
		currencyNameMap.put("VND", "Vietnamese Dong");
		currencyNameMap.put("XAU", "Gold (troy ounce)");
		currencyNameMap.put("AUD", "Australian Dollar");
		currencyNameMap.put("ILS", "Israeli New Sheqel");
		currencyNameMap.put("GHS", "Ghanaian Cedi");
		currencyNameMap.put("GYD", "Guyanaese Dollar");
		currencyNameMap.put("KPW", "North Korean Won");
		currencyNameMap.put("BOB", "Bolivian Boliviano");
		currencyNameMap.put("KHR", "Cambodian Riel");
		currencyNameMap.put("MDL", "Moldovan Leu");
		currencyNameMap.put("IDR", "Indonesian Rupiah");
		currencyNameMap.put("KYD", "Cayman Islands Dollar");
		currencyNameMap.put("AMD", "Armenian Dram");
		currencyNameMap.put("BWP", "Botswanan Pula");
		currencyNameMap.put("SHP", "Saint Helena Pound");
		currencyNameMap.put("TRY", "Turkish Lira");
		currencyNameMap.put("LBP", "Lebanese Pound");
		currencyNameMap.put("TJS", "Tajikistani Somoni");
		currencyNameMap.put("JOD", "Jordanian Dinar");
		currencyNameMap.put("AED", "United Arab Emirates Dirham");
		currencyNameMap.put("HKD", "Hong Kong Dollar");
		currencyNameMap.put("RWF", "Rwandan Franc");
		currencyNameMap.put("EUR", "Euro");
		currencyNameMap.put("LSL", "Lesotho Loti");
		currencyNameMap.put("DKK", "Danish Krone");
		currencyNameMap.put("CAD", "Canadian Dollar");
		currencyNameMap.put("BGN", "Bulgarian Lev");
		currencyNameMap.put("MMK", "Myanma Kyat");
		currencyNameMap.put("MUR", "Mauritian Rupee");
		currencyNameMap.put("NOK", "Norwegian Krone");
		currencyNameMap.put("SYP", "Syrian Pound");
		currencyNameMap.put("IMP", "Manx pound");
		currencyNameMap.put("ZWL", "Zimbabwean Dollar");
		currencyNameMap.put("GIP", "Gibraltar Pound");
		currencyNameMap.put("RON", "Romanian Leu");
		currencyNameMap.put("LKR", "Sri Lankan Rupee");
		currencyNameMap.put("NGN", "Nigerian Naira");
		currencyNameMap.put("CRC", "Costa Rican Colón");
		currencyNameMap.put("CZK", "Czech Republic Koruna");
		currencyNameMap.put("PKR", "Pakistani Rupee");
		currencyNameMap.put("XCD", "East Caribbean Dollar");
		currencyNameMap.put("ANG", "Netherlands Antillean Guilder");
		currencyNameMap.put("HTG", "Haitian Gourde");
		currencyNameMap.put("BHD", "Bahraini Dinar");
		currencyNameMap.put("KZT", "Kazakhstani Tenge");
		currencyNameMap.put("SRD", "Surinamese Dollar");
		currencyNameMap.put("SZL", "Swazi Lilangeni");
		currencyNameMap.put("LTL", "Lithuanian Litas");
		currencyNameMap.put("SAR", "Saudi Riyal");
		currencyNameMap.put("TTD", "Trinidad and Tobago Dollar");
		currencyNameMap.put("YER", "Yemeni Rial");
		currencyNameMap.put("MVR", "Maldivian Rufiyaa");
		currencyNameMap.put("AFN", "Afghan Afghani");
		currencyNameMap.put("INR", "Indian Rupee");
		currencyNameMap.put("AWG", "Aruban Florin");
		currencyNameMap.put("KRW", "South Korean Won");
		currencyNameMap.put("NPR", "Nepalese Rupee");
		currencyNameMap.put("JPY", "Japanese Yen");
		currencyNameMap.put("MNT", "Mongolian Tugrik");
		currencyNameMap.put("AOA", "Angolan Kwanza");
		currencyNameMap.put("PLN", "Polish Zloty");
		currencyNameMap.put("GBP", "British Pound Sterling");
		currencyNameMap.put("SBD", "Solomon Islands Dollar");
		currencyNameMap.put("BYN", "New Belarusian Ruble");
		currencyNameMap.put("HUF", "Hungarian Forint");
		currencyNameMap.put("BYR", "Belarusian Ruble");
		currencyNameMap.put("BIF", "Burundian Franc");
		currencyNameMap.put("MWK", "Malawian Kwacha");
		currencyNameMap.put("MGA", "Malagasy Ariary");
		currencyNameMap.put("XDR", "Special Drawing Rights");
		currencyNameMap.put("BZD", "Belize Dollar");
		currencyNameMap.put("BAM", "Bosnia-Herzegovina Convertible Mark");
		currencyNameMap.put("EGP", "Egyptian Pound");
		currencyNameMap.put("MOP", "Macanese Pataca");
		currencyNameMap.put("NAD", "Namibian Dollar");
		currencyNameMap.put("NIO", "Nicaraguan Córdoba");
		currencyNameMap.put("PEN", "Peruvian Nuevo Sol");
		currencyNameMap.put("NZD", "New Zealand Dollar");
		currencyNameMap.put("WST", "Samoan Tala");
		currencyNameMap.put("TMT", "Turkmenistani Manat");
		currencyNameMap.put("CLF", "Chilean Unit of Account (UF)");
		currencyNameMap.put("BRL", "Brazilian Real");
	}

/*	public static State convertStateDTOToBean(final StateDto stateObj, final Country country) {
		State state = new State();
		state.setCountry(country);
		state.setName(stateObj.getName());
		state.setCreatedBy(stateObj.getCreatedBy());
		state.setUpdatedBy(stateObj.getUpdatedBy());
		state.setCreatedDate(new Date());
		state.setUpdatedDate(new Date());
		return state;
	}*/
}
