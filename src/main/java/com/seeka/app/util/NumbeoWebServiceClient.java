package com.seeka.app.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;

public class NumbeoWebServiceClient  {	
	
	//public static String fileDirectory ="D:\\Seeka\\files\\City\\";
	public static String fileDirectory ="//var//seeka//city_living_cost//";
	   
	public static boolean getCityPricing(String cityName , String cityId) throws Exception {
		try {
			 String url = "https://www.numbeo.com/api/city_prices?api_key=6m50upz1d0b6bq&query="+cityName+"&format=json";
		     URL obj = new URL(url);
		     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		     con.setRequestMethod("GET");
		     con.setRequestProperty("UserInfo-Agent", "Google Chrome/73.0.3683.86");
		     int responseCode = con.getResponseCode();
		     System.out.println("\nSending 'GET' request to URL : " + url);
		     System.out.println("Response Code : " + responseCode);
		     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
		     in.close();
		     JSONObject myResponse = new JSONObject(response.toString());
		     System.out.println("result after Reading JSON Response");
		     System.out.println("name- "+myResponse.getString("name"));
		     System.out.println("currency- "+myResponse.getString("currency"));
		     try (FileWriter file = new FileWriter(fileDirectory+cityId+".json")) {
				file.write(myResponse.toString());
				System.out.println("Successfully Copied JSON Object to File..."+myResponse);
			 }	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	     
	   }
}
