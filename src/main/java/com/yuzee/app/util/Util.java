package com.yuzee.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class Util {
	
	public static Integer perPageCount = 10;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
	 
	public static String getOneTimePassword() throws Exception {
		Random random = new Random();
		return ""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9);
	}
	
	
	public static String getOneTimePasswordForReset() throws Exception {
		Random random = new Random();
		return ""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9);
	}
	
	public static int getYearsBetweenDates(Date first, Date second) {
	    Calendar firstCal = GregorianCalendar.getInstance();
	    Calendar secondCal = GregorianCalendar.getInstance();
	    firstCal.setTime(first);
	    secondCal.setTime(second);
	    secondCal.add(Calendar.DAY_OF_YEAR, 1 - firstCal.get(Calendar.DAY_OF_YEAR));
	    return secondCal.get(Calendar.YEAR) - firstCal.get(Calendar.YEAR);
	}
}
