package com.lms.configs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JavaDateMySqlDate {

    public static final String MYSQL_DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";

    public static String  getDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATE_FORMAT);
        String dateTime = sdf.format(date);
        return dateTime;
    }

    public static String getReturnDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        System.out.println(sdf.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_MONTH,Constants.MAX_LENDING_DAYS);
        String returnDate = sdf.format(cal.getTime());
        System.out.println(returnDate);
        return returnDate;
    }

}
