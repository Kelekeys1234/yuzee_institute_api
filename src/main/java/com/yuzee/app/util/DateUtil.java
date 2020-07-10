package com.yuzee.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
    
    public static Date getUTCdatetimeAsDate() {
        return stringDateToDate(getUTCdatetimeAsString());
    }

    public static String getUTCdatetimeAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }
    
    public static Date getUTCdatetimeAsOnlyDate() {
        return stringDateToOnlyDate(getUTCdateAsString());
    }

    public static String getUTCdateAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(ONLY_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }

    public static Date stringDateToOnlyDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(ONLY_DATE_FORMAT);
        try {
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }
    
    public static Date stringDateToDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }

    public static Date stringDateToDateYYYY_MM_DD(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }

    public static String getUTCdatetimeAsStringYYYY_MM_DD() {
        final SimpleDateFormat sdf = new SimpleDateFormat(IConstant.DATE_FORMAT_YYYY_MM_DD);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }

    public static String getStringDateFromDate(Date datePostedUpdated) {
        final SimpleDateFormat sdf = new SimpleDateFormat(IConstant.DATE_FORMAT_YYYY_MM_DD);
        return sdf.format(datePostedUpdated);
    }

    public static Date convertStringDateToDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }

    public static String convertDateToString(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}
