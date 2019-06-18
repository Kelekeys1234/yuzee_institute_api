package com.seeka.app.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Currency;
import com.seeka.app.service.ICityService;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.ICurrencyService;

@RestController
@RequestMapping("/migrate")
public class MigrateController {

    @Autowired
    ICountryService countryService;

    @Autowired
    ICityService cityService;

    @Autowired
    ICurrencyService currencyService;

    public static Map<String, Country> getCountryMasterData() {
        String csvFile = "E:\\Softwares\\Seeka\\March-2019\\Country\\Country.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Country countryObj = null;
        Map<String, Country> countryMap = new HashMap<String, Country>();
        try {
            // ID COUNTRY_TXT CNT_DESC COUNTRY_CODE
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                System.out.println(country[0] + " , " + country[1] + ", " + country[2] + ", " + country[3]);
                String countryName = country[1];
                String countryCode = country[3];
                countryObj = new Country();
                countryObj.setName(countryName);
                countryObj.setCountryCode(countryCode);

                countryCode = countryCode.replaceAll("[^\\w]", "");

                countryMap.put(countryCode, countryObj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return countryMap;
    }

    public static Map<String, Country> getCountryDetailsData() throws Exception {
        File myFile = new File("E:\\\\Softwares\\\\Seeka\\\\March-2019\\\\Country\\\\country_details.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();

        Map<String, Country> countryDetailsMap = new HashMap<String, Country>();
        Country countryObj = null;
        while (rowIterator.hasNext()) {

            org.apache.poi.ss.usermodel.Row row = rowIterator.next();
            Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

            int i = 0;

            String cd_id = "";
            String country_facts = "";
            String short_skilled_industry = "";
            String Int_health_cover = "";
            String cntry_safety_rating = "";
            String visa = "";
            String job_prospect = "";
            String edu_recognition = "";
            String work_and_study_rules = "";
            String living_n_acomd = "";
            String cost_of_living = "";
            String climate = "";
            String country_code = "";

            String pol_help = "";
            String fire_help = "";
            String amb_help = "";
            String edu_recg_link = "";
            String student_visa_link = "";
            String area = "";
            String population = "";
            String People = "";
            String Language = "";
            String Religion = "";
            String hog = "";
            String gdp_pc = "";
            String anual_growth = "";
            String inflation = "";
            String major_indus = "";
            String major_trd_partnr = "";
            String country_video = "";
            String capital_city = "";
            String gdp = "";
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
                        break;
                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        cellBooleanValue = cell.getBooleanCellValue();
                        break;
                    default:
                    }

                    if (i == 0) {
                        cd_id = cellStringValue;
                    }

                    if (i == 1) {
                        country_facts = cellStringValue;
                    }

                    if (i == 2) {
                        short_skilled_industry = cellStringValue;
                    }

                    if (i == 3) {
                        Int_health_cover = cellStringValue;
                    }

                    if (i == 4) {
                        cntry_safety_rating = cellStringValue;
                    }

                    if (i == 5) {
                        visa = cellStringValue;
                    }

                    if (i == 6) {
                        job_prospect = cellStringValue;
                    }

                    if (i == 7) {
                        edu_recognition = cellStringValue;
                    }

                    if (i == 8) {
                        work_and_study_rules = cellStringValue;
                    }

                    if (i == 9) {
                        living_n_acomd = cellStringValue;
                    }

                    if (i == 10) {
                        cost_of_living = cellStringValue;
                    }

                    if (i == 11) {
                        climate = cellStringValue;
                    }

                    if (i == 12) {
                        country_code = cellStringValue;
                    }

                    if (i == 13) {
                        pol_help = cellStringValue;
                    }

                    if (i == 14) {
                        fire_help = cellStringValue;
                    }

                    if (i == 15) {
                        amb_help = cellStringValue;
                    }

                    if (i == 16) {
                        edu_recg_link = cellStringValue;
                    }

                    if (i == 17) {
                        student_visa_link = cellStringValue;
                    }

                    if (i == 18) {
                        area = cellStringValue;
                    }

                    if (i == 19) {
                        population = cellStringValue;
                    }

                    if (i == 20) {
                        People = cellStringValue;
                    }

                    if (i == 21) {
                        Language = cellStringValue;
                    }

                    if (i == 22) {
                        Religion = cellStringValue;
                    }

                    if (i == 23) {
                        hog = cellStringValue;
                    }

                    if (i == 24) {
                        gdp_pc = cellStringValue;
                    }

                    if (i == 25) {
                        anual_growth = cellStringValue;
                    }

                    if (i == 26) {
                        inflation = cellStringValue;
                    }

                    if (i == 27) {
                        major_indus = cellStringValue;
                    }

                    if (i == 28) {
                        major_trd_partnr = cellStringValue;
                    }

                    if (i == 29) {
                        country_video = cellStringValue;
                    }

                    if (i == 30) {
                        capital_city = cellStringValue;
                    }

                    if (i == 31) {
                        gdp = cellStringValue;
                    }
                    i++;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                countryObj = new Country();
                /*
                 * countryObj.setAmbulanceHelp(amb_help); countryObj.setAnualGrowth(anual_growth); countryObj.setArea(area); countryObj.setCapitalCity(capital_city);
                 * countryObj.setClimate(climate); countryObj.setCostOfLiving(cost_of_living); countryObj.setCountryCode(country_code); countryObj.setCountryFacts(country_facts);
                 * countryObj.setCountrySafetyRating(cntry_safety_rating); countryObj.setCountryVideo(country_video);
                 */
                countryObj.setDescription("");
                /*
                 * countryObj.setEduRecgLink(edu_recg_link); countryObj.setEduRecognition(edu_recognition); countryObj.setFireHelp(fire_help); countryObj.setGdp(gdp);
                 * countryObj.setGdpPercentage(gdp_pc); countryObj.setHog(hog); countryObj.setInflation(inflation); countryObj.setIntHealthCover(Int_health_cover);
                 */
                countryObj.setIsDeleted(false);
                /*
                 * countryObj.setJobProspect(job_prospect); countryObj.setLanguage(Language); countryObj.setLivingAndAccommodation(living_n_acomd);
                 * countryObj.setMajorIndustries(major_indus); countryObj.setMajorTrdPartnr(major_trd_partnr); countryObj.setPeople(People); countryObj.setPoliceHelp(pol_help);
                 * countryObj.setPopulation(population); countryObj.setReligion(Religion); countryObj.setShortSkilledIndustry(short_skilled_industry);
                 * countryObj.setStudentVisaLink(student_visa_link); countryObj.setVisa(visa); countryObj.setWorkAndStudyRules(work_and_study_rules);
                 */
                country_code = country_code.replaceAll("[^\\w]", "");
                countryDetailsMap.put(country_code, countryObj);
            }
        }

        System.out.println(countryDetailsMap.size());

        return countryDetailsMap;
    }

    @RequestMapping(value = "/country", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> migrateCountryData() throws Exception {

        Map<String, Country> countryMasterMap = getCountryMasterData();
        Map<String, Country> countryDetailsMap = getCountryDetailsData();

        int i = 0, j = 0;
        for (String countryCode : countryMasterMap.keySet()) {
            try {
                Country masterObj = countryMasterMap.get(countryCode);
                Country detailObj = countryDetailsMap.get(countryCode);
                System.out.println("Code: " + countryCode + ", masterObj: " + masterObj + ", detailObj: " + detailObj);
                if (null == detailObj) {
                    masterObj.setCreatedBy("AUTO");
                    masterObj.setCreatedOn(new Date());
                    // countryService.save(masterObj);
                    j++;
                } else {
                    detailObj.setCreatedBy("AUTO");
                    detailObj.setCreatedOn(new Date());
                    detailObj.setName(masterObj.getName());
                    // countryService.save(detailObj);
                    i++;
                }
                System.out.println(i + "-------------------------------------------------------------" + j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", 1);
        response.put("message", "Success.!");
        return ResponseEntity.accepted().body(response);
    }

    public static Map<String, City> getCityData() {
        String csvFile = "E:\\Softwares\\Seeka\\March-2019\\City\\country_city_activity_map.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        City cityObj = null;
        Map<String, City> cityMap = new HashMap<String, City>();
        try {
            // ID COUNTRY_TXT CNT_DESC COUNTRY_CODE
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                String cityName = country[1];
                cityObj = new City();
                cityObj.setName(country[1]);
                cityObj.setTripAdvisorLink(country[0]);
                cityObj.setDescription(country[2]);
                cityName = cityName.replaceAll("[^\\w]", "");
                cityMap.put(cityName, cityObj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cityMap;
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> migrateCityData() throws Exception {
        Map<String, City> cityMap = getCityData();
        int i = 0, j = 0, k = 0;

        List<Country> countryList = countryService.getAll();
        Map<String, Country> countryMap = new HashMap<String, Country>();
        for (Country country : countryList) {
            countryMap.put(country.getCountryCode().toLowerCase().replaceAll("[^\\w]", ""), country);
            countryMap.put(country.getName().toLowerCase().replaceAll("[^\\w]", ""), country);
        }

        for (String cityName : cityMap.keySet()) {
            try {
                City masterObj = cityMap.get(cityName);
                if (null == masterObj) {
                    j++;
                } else {
                    System.out.println("Code: " + cityName + ", CountryCode: " + masterObj.getDescription());
                    Country country = countryMap.get(masterObj.getDescription().toLowerCase().replaceAll("[^\\w]", ""));
                    if (null == country) {
                        k++;
                    } else {
                        masterObj.setCityImgCnt(0);
                        masterObj.setCountry(country);
                        masterObj.setDescription("");
                        cityService.save(masterObj);
                        i++;
                    }
                }
                System.out.println(i + "-------------------------------" + j + "--------------------------------" + k);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", 1);
        response.put("message", "Success.!");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/currency", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> migrateCurrencyData() throws Exception {
        Map<String, Currency> currencyMap = getCurrencyMasterData();
        Map<String, Currency> jsonMap = getCurrencyFromJsonData();
        Date updatedDate = new Date();
        int i = 0, j = 0, k = currencyMap.size();
        for (String key : currencyMap.keySet()) {

            Currency obj = currencyMap.get(key);
            Currency jsonObj = jsonMap.get(key);

            try {

                if (null != jsonObj) {
                    obj.setName(jsonObj.getName());
                    obj.setSymbol(jsonObj.getSymbol());
                }
                obj.setBaseCurrency("USD");
                obj.setUpdatedDate(updatedDate);
                currencyService.save(obj);
                i++;
                System.out.println(i + "-------------------------------" + j + "--------------------------------" + k);
            } catch (Exception e) {
                e.printStackTrace();
                j++;
            }
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", 1);
        response.put("message", "Success.!");
        return ResponseEntity.accepted().body(response);
    }

    public static Map<String, Currency> getCurrencyMasterData() {
        String csvFile = "E:\\Softwares\\Seeka\\March-2019\\currency\\currency.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Currency currency = null;
        Map<String, Currency> currencyMap = new HashMap<String, Currency>();
        try {
            // CUR_ID,CUR_NAME,CUR_RATE
            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    i++;
                    continue;
                }
                String[] country = line.split(cvsSplitBy);
                System.out.println(country[0] + " , " + country[1] + ", " + country[2]);
                String countryName = country[1];
                String countryRate = country[2];
                currency = new Currency();
                currency.setCode(countryName);
                currency.setConversionRate(Double.valueOf(countryRate.trim()));
                countryName = countryName.replaceAll("[^\\w]", "");
                currencyMap.put(countryName.toLowerCase(), currency);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return currencyMap;
    }

    public static Map<String, Currency> getCurrencyFromJsonData() throws Exception {
        File file = new File("E:\\Softwares\\Seeka\\March-2019\\currency\\currency_v1.json");
        org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
        if (null != file && file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String responseStr = "";
            while ((st = br.readLine()) != null) {
                responseStr += st;
                // System.out.println(responseStr);

            }
            br.close();
            JSONParser parser = new JSONParser();
            jsonArray = (org.json.simple.JSONArray) parser.parse(responseStr);
        }
        System.out.println(jsonArray);

        Map<String, Currency> map = new HashMap<>();
        Currency currency = null;
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            String code = (String) jsonObject.get("cc");
            String name = (String) jsonObject.get("name");
            String symbol = (String) jsonObject.get("symbol");
            currency = new Currency();
            currency.setName(name);
            currency.setCode(code);
            currency.setSymbol(symbol);
            map.put(code.toLowerCase(), currency);
        }
        System.out.println(map.size());
        return map;
    }

    public static void main(String[] args) throws Exception {
        getCurrencyFromJsonData();
    }

}
