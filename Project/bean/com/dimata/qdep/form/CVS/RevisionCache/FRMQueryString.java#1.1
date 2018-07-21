/*
 * FRMControl.java
 *
 * Created on March 14, 2002, 3:06 PM
 */


/**
 *
 * @author  gmudiasa
 * @version 
 */


package com.dimata.qdep.form;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class FRMQueryString {

    public static final String YR = "_yr";
    public static final String MN = "_mn";
    public static final String DY = "_dy";
    public static final String MM = "_mm";
    public static final String HH = "_hh";    
    
    public static final String CMD = "command";
    
    //added by eka,
    //secure from any injection
    private static String[] specialChar = {"+or+", "+and+", "<", ">", "'", "/*", "\"", "--", ";"};
    private static String[] specialCharReplace = {" or ", " and ", "&lt;", "&gt;", "", "", "", "", ""};
    
    
    /** Creates new FRMControl */
    public FRMQueryString() {
    }
    

    public static String requestString(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "" : ((String)(req.getParameter(paramName)));
//		System.out.println("--FRMQueryString sParam : " + sParam);
        sParam = checkValueForInjection(sParam);
//        System.out.println("--FRMQueryString sParam : " + sParam);
        return sParam;
    }

    
    public static String[] requestStringValues(HttpServletRequest req, String paramName) {
        String[] sParams = req.getParameterValues(paramName);
        if(sParams!=null && sParams.length>0){
            for(int i=0;i< sParams.length;i++){
                sParams[i] = checkValueForInjection(sParams[i]);
            }
        }
        return sParams;
    }
    

    public static String checkValueForInjection(String value){
        if(value!=null && value.length()>0){
            String specChar = "";
            int index = -1;
            boolean stop = false;
            for(int i=0; i<specialChar.length; i++){
                specChar =  specialChar[i];
                stop = false;

                //System.out.println("specChar : "+specChar);

                while(!stop){
	                index = value.indexOf(specChar);
                    //System.out.println("index : "+index);
	                //if exist
	                if(index>-1){
						value = replaceString(index, value, specChar, specialCharReplace[i]);
	                }
                    else{
                        stop = true;
                    }
                }
            }
        }

        return value;
    }

    public static String replaceString(int index, String value, String specChar, String charReplace){

        //System.out.println("--index : "+index);
        //System.out.println("--value : "+value);
        //System.out.println("--specChar : "+specChar);
        //System.out.println("--charReplace : "+charReplace);

        if(index==0){
            value = charReplace + value.substring(index+specChar.length(), value.length());
        }else{
            String st1 = "";
	        String st2 = "";
            st1 = value.substring(0, index);
            st2 = value.substring(index + specChar.length(), value.length());
            value = st1 + charReplace + st2;
            //System.out.println("--st1 : "+st1);
	        //System.out.println("--st2 : "+st2);
        }

        return value;
    }

    
    public static int requestInt(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "0" : ((String)(req.getParameter(paramName)));
        try {
            int iParam = Integer.parseInt(sParam);        
            return iParam;
        }catch(Exception e) {
            return 0;
        }
    }

    public static long requestLong(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "0" : ((String)(req.getParameter(paramName)));
        try {
            long lParam = Long.parseLong(sParam);        
            return lParam;
        }catch(Exception e) {
            return 0;
        }
    }

    public static float requestFloat(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "0" : ((String)(req.getParameter(paramName)));
        try {
            float lParam = Float.parseFloat(sParam);
            return lParam;
        }catch(Exception e) {
            return 0;
        }
    }

    public static double requestDouble(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "0" : ((String)(req.getParameter(paramName)));
        sParam = FRMHandler.deFormatStringDecimal(sParam);
        try {
            double lParam = Double.parseDouble(sParam);
            return lParam;
        }catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public static boolean requestBoolean(HttpServletRequest req, String paramName) {
        String sParam = (((String)req.getParameter(paramName)) == null) ? "false" : ((String)(req.getParameter(paramName)));
        try {
            sParam = sParam.trim();
            
            if(sParam.equalsIgnoreCase("true") || sParam.equals("1")) {
               return true;
            }else {
                return false;
            }           
            
        }catch(Exception e) {
            return false;
        }
    }
    

    public static Date requestDate(HttpServletRequest req, String paramName) {
        try {    
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + HH);
            int mm = requestInt(req, paramName + MM);

            Date dt = yr==0 && mn==0 ? new Date() :new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
            
        }catch(Exception e) {
            return new Date();
        }
    }
    /**
     * create by satrya 2013-08-15
     * @param req
     * @param paramName
     * @return 
     */
 public static Date requestDateIfYearMonthNol(HttpServletRequest req, String paramName) {
        try {    
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + HH);
            int mm = requestInt(req, paramName + MM);

            Date dt = yr==0 && mn==0 ? null :new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
            
        }catch(Exception e) {
            return new Date();
        }
    }
public static Date requestDateVer5(HttpServletRequest req, String paramName) {
     try {    
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + "_hr");
            int mm = requestInt(req, paramName + "_mi");
            if(yr==0 && mn==0 && hh==0 && mm==0 ){
                return null;
            }
            Date dt = new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
            
        }catch(Exception e) {
            return null;
        }
    }


/**
 * contoh : 2015-06-01 
 * @param req
 * @param paramName
 * @return 
 */
public static Date requestDateYYYYMMDD(HttpServletRequest req, String paramName, String splitBy) {
     try {    
            String dateString = requestString(req, paramName);
            String[] dateStr = dateString.split(splitBy);
            if(dateStr==null || dateStr.length!=3 ){
                return null;
            }
                    
            int yr = Integer.parseInt(dateStr[0].trim());
            String strMn = dateStr[1].trim();
            if(strMn.length()>1 && strMn.startsWith("0")){
                strMn= strMn.substring(1,2);
            }
            String strDy = dateStr[2].trim();
            if(strDy.length()>1 && strDy.startsWith("0")){
                strDy= strDy.substring(1,2);
            }
            
            int mn = Integer.parseInt(strMn);
            int dy = Integer.parseInt(strDy);
            if(yr==0 ){
                return null;
            }
            Date dt = new Date(yr - 1900, mn-1, dy, 0, 0);
            return dt;            
        }catch(Exception e) {
            return null;
        }
    }


//update by satrya 2013-03-29
     public static Date requestDateVer4(HttpServletRequest req, String paramName) {
        try {    
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + HH);
            int mm = requestInt(req, paramName + MM);

            Date dt = yr==0 && mn==0 ? new Date() :new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
            
        }catch(Exception e) {
            return null;
        }
    }
    /**
     * Get date and time which generated by : ControlDate.drawDate  and ControlDate.drawTime
     * @param req
     * @param paramName
     * @return 
     */
    public static Date requestDateVer2(HttpServletRequest req, String paramName) {
        try {    
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + "_hr");
            int mm = requestInt(req, paramName + "_mi");

            Date dt = new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
            
        }catch(Exception e) {
            return new Date();
        }
    }
     //update by satrya 2012-11-28
    /**
     * 
     * @param req
     * @param paramName
     * @return 
     */
    public static Date requestDateVer3(HttpServletRequest req, String paramName){
        try{
            int yr = requestInt(req, paramName + YR);
            int mn = requestInt(req, paramName + MN);
            int dy = requestInt(req, paramName + DY);
            int hh = requestInt(req, paramName + HH)!=0? requestInt(req, paramName + HH) :requestInt(req, paramName + "_hr"); 
            int mm = requestInt(req, paramName + MM)!=0? requestInt(req, paramName + MM):requestInt(req, paramName + "_mi") ;
            
            //if(yr<1 || mn<1 || dy<1){
            //update by satrya 2012-11-27
            
            if((yr<1 || mn<1 || dy<1) && hh<1){
                Date dtY = new Date();
                dtY.setHours(hh);
                dtY.setMinutes(mn);
                dtY.setSeconds(0);
                return dtY;
            }
            //update by satrya 2012-11-27
            else if(((yr<1 || mn<1 || dy<1) && hh>1)){
                Date dtX= new Date();
                yr= dtX.getYear();
                mn= dtX.getMonth();
                dy=dtX.getDate();
            }
            
            
            Date dt = new Date(yr - 1900, mn -1, dy, hh, mm);
            return dt;
        }catch(Exception e) {
            return new Date();
        }
    }
    
     
    public static long requestDateLong(HttpServletRequest req, String paramName) {
        Date dt = requestDate(req, paramName);        
        return dt.getTime();
    }

    
    public static String requestDateString(HttpServletRequest req, String paramName) {
        Date dt = requestDate(req, paramName);        
        return String.valueOf(dt.getTime());
    }
    
 
    
    
    
    public static int requestCommand(HttpServletRequest req) {
        return requestInt(req, CMD);
    }
    
    
}
