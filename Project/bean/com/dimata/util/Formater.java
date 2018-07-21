package com.dimata.util;

import com.dimata.harisma.utility.harisma.machine.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Formater {

    public static String FORMAT_DATE_SHORT_US = "MM/dd/yyyy";
    public static String FORMAT_DATE_SHORT_EUROPE = "dd/MM/yyyy";

    public Formater() {
    }

    public static String formatShortEuropeToUS(String strDateShortUS) {
        return formatDate(strDateShortUS, FORMAT_DATE_SHORT_EUROPE, FORMAT_DATE_SHORT_US);
    }

    /**
     * to formating date by outFormat pattern. If date is null or another
     * exception, outString will returned
     *
     * @param date Date will format
     * @param outFormat String pattern
     * @param outString String out if null or exception
     * @return
     */
    public static String formatDate(Date date, String outFormat, String outString) {
        String strRet;
        SimpleDateFormat outDF = null;

        try {
            outDF = (SimpleDateFormat) DateFormat.getDateInstance();
            outDF.applyPattern(outFormat);
            strRet = outDF.format(date).toString();

        } catch (Exception e) {
            strRet = outString;
        }
        return strRet;
    }

    public static String formatDate(String strDate, String inFormat, String outFormat) {
        String strRet = new String();
        SimpleDateFormat outDF = null;
        Date dt = null;

        try {
            SimpleDateFormat inDF = (SimpleDateFormat) DateFormat.getDateInstance();
            inDF.applyPattern(inFormat);
            dt = inDF.parse(strDate);

            outDF = (SimpleDateFormat) DateFormat.getDateInstance();
            outDF.applyPattern(outFormat);
            strRet = outDF.format(dt).toString();

        } catch (ParseException pe) {
            strRet = "ERROR::" + pe.toString();
        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        return strRet;
    }

    public static String formatDate(Date date) {
        if(date==null){
            return("");
        }
        String strRet = new String();

        int dtValue = Validator.getIntDate(date);
        int mnValue = Validator.getIntMonth(date);
        int yrValue = Validator.getIntYear(date);

        try {
            strRet = "" + dtValue + " " + YearMonth.getLongInaMonthName(mnValue) + " " + yrValue;
        } catch (Exception e) {
        }
        return strRet;
    }

    public static String formatDate(Date date, String outFormat) {
        if(date==null){
            return("");
        }
        String strRet = new String();
        SimpleDateFormat outDF = null;

        try {
            outDF = (SimpleDateFormat) DateFormat.getDateInstance();
            outDF.applyPattern(outFormat);
            strRet = outDF.format(date).toString();

        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        if (strRet != null) {
            strRet = strRet.replaceAll("24:00:00", "00:00:00");
        }
        return strRet;
    }

    /**/
    public static Date reFormatDate(Date date, String outFormat) {
        
        String strRet = new String();
        SimpleDateFormat outDF = null;
        SimpleDateFormat inDF = null;
        
        Date dt = null;
        try {
            outDF = (SimpleDateFormat) DateFormat.getDateInstance();
            outDF.applyPattern(outFormat);
            strRet = outDF.format(date).toString();

            inDF = (SimpleDateFormat) DateFormat.getDateInstance();
            inDF.applyPattern(outFormat);
            dt = inDF.parse(strRet);

        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        return dt;
    }

    public static String formatOnlyDateShortMonth(Date date) {
        String strRet = new String();

        try {
            strRet = Integer.toString(date.getDate()) + " " + YearMonth.getShortEngMonthName(date.getMonth() + 1);
        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        return strRet;
    }

    public static Date formatDate(String strDate, String inFormat) {
        Date dt = null;
        try {
            SimpleDateFormat inDF = (SimpleDateFormat) DateFormat.getDateInstance();
            inDF.applyPattern(inFormat);
            dt = inDF.parse(strDate);
        } catch (Exception e) {
            System.out.println("ERROR::" + e.toString());
        }
        return dt;
    }

    public static String formatOnlyDateMonth(Date date) {
        String strRet = new String();

        try {
            strRet = Integer.toString(date.getDate()) + " " + YearMonth.getLongEngMonthName(date.getMonth() + 1);
        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        return strRet;
    }

    public static String formatTimeLocale(Date dateTime) {
        return formatTimeLocale(dateTime, "");
    }

    public static String formatTimeLocale(Date dateTime, String outFormat) {
        String strRet = new String();
        SimpleDateFormat outDF = null;

        if ((outFormat == null) || (outFormat.length() == 0)) //update by satrya 2012-09-28
        {
            outFormat = "HH:mm";
        }

        try {
            outDF = (SimpleDateFormat) DateFormat.getDateInstance();
            outDF.applyPattern(outFormat);
            strRet = outDF.format(dateTime).toString();

        } catch (Exception e) {
            strRet = "ERROR::" + e.toString();
        }
        return strRet;

        //return Integer.toString(dateTime.getHours())+":"+ Integer.toString(dateTime.getMinutes());
    }

    public static String formatNumber(double number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(number).toString();
    }

    /**
     * mengganti koma menjadi titik 
     * create by satrya 2014-06-1
     * @param number
     * @param format
     * @return
     */
    public static String formatNumberVer1(double number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        String value = "";
        //jika user memakai koma maka akan di rubah menjadi koma
        if (format.split(",")!=null && format.split(",").length>1) {
            value = df.format(number).toString();
        } else {
           if(format!=null && format.length()>0){
            format = format.replace(".", ",");
            df = new DecimalFormat(format);
            value = df.format(number).toString();
            if (value != null && value.length() > 0) {
                value = value.replace(",", ".");
            }
           }
            
        }

        return value;
    }

    /**
     * untuk membuat decimal menjadi persence
     *
     * @param number
     * @param currentLocale: ex Locale.JAPANESE
     * @return
     */
    public static String formatDecimalToPercent(double number, Locale currentLocale) {
        NumberFormat percentFormatter;
        percentFormatter = NumberFormat.getPercentInstance(currentLocale);
        return percentFormatter.format(number).toString();
    }

    /**
     * convert string to double
     *
     * @param number
     * @param format
     * @return
     */
    public static double formatNumberDouble(double number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        double value = 0;
        if (df != null && format != null && format.length() > 0) {
            value = Double.parseDouble(df.format(number));
        }
        return value;
    }

    /**
     * convert string to int
     *
     * @param number
     * @param format
     * @return
     */
    public static int formatNumberInt(int number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        int value = 0;
        if (df != null && format != null && format.length() > 0) {
            value = Integer.parseInt(df.format(number));
        }
        return value;
    }

    public static String formatNumber(int number) {
        String strNumber = "";
        if (number > 9) {
            strNumber = strNumber + number;
        } else {
            strNumber = strNumber + "0" + number;
        }
        return strNumber;
    }

    public static String formatNumber(long number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(number).toString();
    }
    /**
     * Merubah Double menjadi mata Uang
     * @param number
     * @param mataUang
     * @return 
     */
    public static String formatNumberMataUang(double number,String mataUang) {
         DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol(mataUang+". ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        
        return kursIndonesia.format(number);
    }

    public static String formatNumber(float number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format((double) number).toString();
    }

    public static String formatLocale(long number, Locale local) {
        NumberFormat df = NumberFormat.getCurrencyInstance(local);
        return df.format(number).toString();
    }

    /**
     * modified by yogi
     */
    public static String formatDate(Date date, int languange, String outFormat) {
        String strRet = new String();

        Vector vect = Validator.tokenToVector(outFormat, " ");
        String strDt = "";
        String strMn = "";
        String strYr = "";
        if (vect != null && vect.size() > 0) {
            strDt = (String) vect.get(0);
            strMn = (String) vect.get(1);
            strYr = (String) vect.get(2);
        }

        int dtValue = 0;
        int mnValue = 0;
        int yrValue = 0;
        String strDtValue = "";

        // if language is default
        if (languange == com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT) {
            dtValue = Validator.getIntDate(date);
            mnValue = Validator.getIntMonth(date);
            yrValue = Validator.getIntYear(date);

            if (dtValue < 10) {
                strDtValue = "0" + dtValue;
            } else {
                strDtValue = "" + dtValue;
            }

            switch (strMn.length()) {
                case 1:
                    strRet = strDtValue + " " + YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 2:
                    strRet = strDtValue + " " + YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 3:
                    strRet = strDtValue + " " + YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 4:
                    strRet = strDtValue + " " + YearMonth.getLongInaMonthName(mnValue) + " " + yrValue;
                    break;
                default:
                    strRet = strDtValue + " " + YearMonth.getLongInaMonthName(mnValue) + " " + yrValue;
                    break;
            }

            // if language is foreign
        } else {
            strRet = formatDate(date, outFormat);
        }
        return strRet;
    }

    public static String formatMonthYear(Date date, int languange, String outFormat) {
        String strRet = new String();

        Vector vect = Validator.tokenToVector(outFormat, " ");
        String strDt = "";
        String strMn = "";
        String strYr = "";
        if (vect != null && vect.size() > 0) {
            strMn = (String) vect.get(0);
            strYr = (String) vect.get(1);
        }

        int dtValue = 0;
        int mnValue = 0;
        int yrValue = 0;
        String strDtValue = "";

        // if language is default
        if (languange == com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT) {
            mnValue = Validator.getIntMonth(date);
            yrValue = Validator.getIntYear(date);

            switch (strMn.length()) {
                case 1:
                    strRet = YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 2:
                    strRet = YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 3:
                    strRet = YearMonth.getShortInaMonthName(mnValue) + " " + yrValue;
                    break;
                case 4:
                    strRet = YearMonth.getLongInaMonthName(mnValue) + " " + yrValue;
                    break;
                default:
                    strRet = YearMonth.getLongInaMonthName(mnValue) + " " + yrValue;
                    break;
            }

            // if language is foreign
        } else {
            strRet = formatDate(date, outFormat);
        }
        return strRet;
    }

    /*
     public static String formatNumber(float dnumber, String format) {
     DecimalFormat df = new DecimalFormat(format);
     String number=df.format(dnumber).toString();  
        
     String numberSebenarnya="";
     String numberSebenarnya1="";
     String numberOutKoma="";
        
     try{
     StringTokenizer strToken=new StringTokenizer(format,".");
     String str="";
            
     while (strToken.hasMoreTokens()){
     str=strToken.nextToken();
     }
            
     if (format.length()==str.length())
     str="";
            
     // cek for fill decimal
     for (int zero=0;zero<str.length();zero++){
     numberOutKoma=numberOutKoma + "0";
     }
            
     df = new DecimalFormat("###." + numberOutKoma);
     String number1=df.format(dnumber).toString();
            
     // before dot
     try{
     long a=Long.parseLong(number);
     if (str.length()>0){
     numberSebenarnya1=a + "." +numberOutKoma;
     }else{
     numberSebenarnya1=a + numberOutKoma;
     }
     }catch (Exception e){
                
     for (int a=0;a<number.length();a++){
     String chr=number.substring(a,a+1);
     if (chr.equals(".")){
     numberOutKoma="" ;
     numberSebenarnya1=numberSebenarnya;
     numberOutKoma=number.substring(a+1,number.length());
                        
     for (int b=0;b<number1.length();b++){
     String chr1=number1.substring(b,b+1);
     if (chr1.equals(".")){
     numberOutKoma=number1.substring(b+1,number1.length());
                                
     if (numberOutKoma.length()>2){
     numberOutKoma=numberOutKoma.substring(0,str.length());
     break;
     }
     }
     }
     }else{
     numberSebenarnya=numberSebenarnya+number.substring(a,a+1);
     }
     }
                
     if (numberSebenarnya1.length()<1)
     numberSebenarnya1=number+ "." +numberOutKoma;
     else
     numberSebenarnya1=numberSebenarnya1+ "." +numberOutKoma;
                
     }
     }catch (Exception e){
     System.out.println(e.toString());
     }finally{
     return numberSebenarnya1;
            
     }
     }
     */
    public static String formatWorkDayHoursMinutes(float workdays, float hourDay, String numberFormat) {
        String sResult = "";
        int signWorkday = Math.abs(workdays) > 0.0000000000000000 ? (int) (workdays / Math.abs(workdays)) : 1;
        workdays = Math.abs(workdays);
        int days = (int) workdays; // cari hari bulat
        float hPecah = (workdays - (float) days) * hourDay; // cari sisa hari di kali jumlah jam per hari
        int hours = (int) hPecah;// cari jumlah jam bulat
        //int   hMinutes = (int) (hPecah - (float) hours) * 60f; // mencari menit
        //ini di bulatkan
        int hMinutes = (int) Math.round((hPecah - (float) hours) * 60f); // mencari menit
        if (days > 0) {
            sResult = "" + days + " d";
        }
        if (hours > 0) {
            sResult = sResult + " " + hours + " h";
        }
        if (hMinutes > 0) {
            sResult = sResult + " " + hMinutes + "'";
        }
        if (signWorkday >= 0) {
            //update by satrya 2012-10-23
            if (sResult == null || sResult.length() <= 0) {
                sResult = "0";
            }
            // System.out.println("nilai :"+sResult); 
            return sResult;

        } else {
            //   System.out.println(""+sResult);
            return "-" + sResult;
        }
    }

    // Update By Agus 10-02-2014
    public static String formatWorkDayHoursMinutesII(float workdays, float hourDay, String numberFormat) {
        String sResult = "";
        int signWorkday = Math.abs(workdays) > 0.0000000000000000 ? (int) (workdays / Math.abs(workdays)) : 1;
        workdays = Math.abs(workdays);
        int days = (int) workdays; // cari hari bulat
        float hPecah = (workdays - (float) days) * hourDay; // cari sisa hari di kali jumlah jam per hari
        int hours = (int) hPecah;// cari jumlah jam bulat
        //int   hMinutes = (int) (hPecah - (float) hours) * 60f; // mencari menit
        //ini di bulatkan
        int hMinutes = (int) Math.round((hPecah - (float) hours) * 60f); // mencari menit
        if (days > 0) {
            sResult = "" + days + " ";
        }
        if (hours > 0) {
            sResult = sResult + " " + hours + " h";
        }
        if (hMinutes > 0) {
            sResult = sResult + " " + hMinutes + "'";
        }
        if (signWorkday >= 0) {
            //update by satrya 2012-10-23
            if (sResult == null || sResult.length() <= 0) {
                sResult = "0";
            }
            // System.out.println("nilai :"+sResult); 
            return sResult;

        } else {
            //   System.out.println(""+sResult);
            return "-" + sResult;
        }
    }

    /**
     * create by satrya 2013-09-13 untuk format work hours
     *
     * @param workdays
     * @return
     */
    public static String formatWorkHoursMinutes(long workdays) {
        String sResult = "";
        if (workdays != 0) {
            workdays = workdays / 1000;
            long iDurationHour = (workdays - (workdays % 3600)) / 3600;
            long iDurationMin = workdays % 3600 / 60;

            if (!(iDurationHour == 0 && iDurationMin == 0)) {
                String strDurationHour = (iDurationHour != 0) ? iDurationHour + "h, " : "";
                String strDurationMin = (iDurationMin != 0) ? iDurationMin + "m" : "";
                sResult = strDurationHour + strDurationMin;
            }
        }
        return sResult;
    }
    //update by satrya 2012-11-29

    /**
     * Keterangan : return dalam hari kerja convert mili second
     *
     * @param workdays
     * @param hourDay
     * @return
     */
    public static long getWorkDayMiliSeconds(float workdays, float hourDay) {
        long result = 0;
        int signWorkday = Math.abs(workdays) > 0.0000000000000000 ? (int) (workdays / Math.abs(workdays)) : 1;
        workdays = Math.abs(workdays);
        int days = (int) workdays; // cari hari bulat
        float hPecah = (workdays - (float) days) * hourDay; // cari sisa hari di kali jumlah jam per hari
        int hours = (int) hPecah;// cari jumlah jam bulat
        int hMinutes = (int) ((hPecah - (float) hours) * 60f); // mencari menit
        if (days > 0) {
            result = days * (24 * 60 * 60 * 1000);
        }
        if (hours > 0) {
            result = result + hours * (60 * 60 * 1000);
        }
        if (hMinutes > 0) {
            result = result + (hMinutes * 60 * 1000);
        }
        if (signWorkday >= 0) {
            //update by satrya 2012-10-23
            if (result <= 0) {
                result = 0;
            }
            return result;
        }
        return result;
    }

    public static String formatDurationTime(Date startDateTime, Date toDateTime, boolean showSeconds) {

        /* days hh:mm format*/
        long duration = DateCalc.timeDifference(startDateTime, toDateTime);
        //System.out.println("duration : "+duration);

        long days = 0;
        long left = 0;
        if (duration > DateCalc.DAY_MILLI_SECONDS) {
            days = duration / DateCalc.DAY_MILLI_SECONDS;
            // System.out.println("days : "+days);
            left = duration % DateCalc.DAY_MILLI_SECONDS;
            //  System.out.println("left : "+left);
        } else {
            left = duration;
        }

        long hours = 0;
        //System.out.println("DateCalc.HOURS_MILLI_SECONDS : "+DateCalc.HOURS_MILLI_SECONDS);
        if (left > DateCalc.HOURS_MILLI_SECONDS) {
            hours = left / DateCalc.HOURS_MILLI_SECONDS;
            left = left % DateCalc.HOURS_MILLI_SECONDS;
        }

        //System.out.println("hours : "+hours);
        // System.out.println("left : "+left);

        // System.out.println("DateCalc.MINUTES_MILLI_SECONDS : "+DateCalc.MINUTES_MILLI_SECONDS);

        long minutes = 0;
        if (left > DateCalc.MINUTES_MILLI_SECONDS) {
            minutes = left / DateCalc.MINUTES_MILLI_SECONDS;
            left = left % DateCalc.MINUTES_MILLI_SECONDS;
        }

        // System.out.println("minutes : "+minutes);
        // System.out.println("left : "+left);

        //  System.out.println("DateCalc.SECONDS_MILLI_SECONDS : "+DateCalc.SECONDS_MILLI_SECONDS);

        long seconds = 0;
        if (left > DateCalc.SECONDS_MILLI_SECONDS) {
            seconds = left / DateCalc.SECONDS_MILLI_SECONDS;
        }

        // System.out.println("seconds : "+seconds);

        String durationStr = "";
        if (days > 0) {
            durationStr = days + " day(s)";
        }

        if (hours > 0) {
            durationStr = durationStr + " " + hours;
        } else {
            durationStr = durationStr + " 0";
        }

        if (minutes > 0) {
            durationStr = durationStr + ":" + getTimeString(minutes);
        } else {
            durationStr = durationStr + ":00";
        }

        /* days hh:mm:ss format*/
        if (showSeconds) {
            if (seconds > 0) {
                durationStr = durationStr + ":" + seconds;
            } else {
                durationStr = durationStr + ":00";
            }
        }

        return durationStr;
    }

    public static String getTimeString(long number) {
        if (("" + number).length() < 2) {
            return "0" + number;
        } else {
            return "" + number;
        }
    }

    public static void main(String args[]) {
        Date dt = new Date(104, 3, 1);
        String str = formatDate(dt, com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT, "dd MMM yyyy");
        String str2 = formatDate(dt, com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT, "dd MMMM yyyy");
        String str3 = formatDate(dt, com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN, "MMM dd, yyyy");
        String str4 = formatMonthYear(dt, com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT, "MMMM yyyy");
        System.out.println("str=" + str);
        System.out.println("str2=" + str2);
        System.out.println("str3=" + str3);
        System.out.println("str4=" + str4);
    }

    //Gede_5April2012
    /**
     * Menghitung selisih antara waktu / date
     *
     * @param startDateTime
     * @param toDateTime
     * @param showDays
     * @return selisih waktu dalam jam ( bisa pecahan )
     */
    public static double formatDurationTime2(Date startDateTime, Date toDateTime, boolean showDays) {

        /* days hh:mm format*/
        long duration = DateCalc.timeDifference(startDateTime, toDateTime);
        //System.out.println("duration : "+duration);

        long days = 0;
        long left = 0;
        if (duration > DateCalc.DAY_MILLI_SECONDS) {
            days = duration / DateCalc.DAY_MILLI_SECONDS;
            days = days * 24; // 24hours
            // System.out.println("days : "+days);
            left = duration % DateCalc.DAY_MILLI_SECONDS;
            //  System.out.println("left : "+left);
        } else {
            left = duration;
        }

        long hours = 0;
        //System.out.println("DateCalc.HOURS_MILLI_SECONDS : "+DateCalc.HOURS_MILLI_SECONDS);
        if (left > DateCalc.HOURS_MILLI_SECONDS) {
            hours = left / DateCalc.HOURS_MILLI_SECONDS;
            left = left % DateCalc.HOURS_MILLI_SECONDS;
        }

        //System.out.println("hours : "+hours);
        // System.out.println("left : "+left);

        // System.out.println("DateCalc.MINUTES_MILLI_SECONDS : "+DateCalc.MINUTES_MILLI_SECONDS);

        double minutes = 0;
        if (left > DateCalc.MINUTES_MILLI_SECONDS) {
            minutes = left / DateCalc.MINUTES_MILLI_SECONDS;
            minutes = minutes / 60;
            left = left % DateCalc.MINUTES_MILLI_SECONDS;
        }

        // System.out.println("minutes : "+minutes);
        // System.out.println("left : "+left);

        //  System.out.println("DateCalc.SECONDS_MILLI_SECONDS : "+DateCalc.SECONDS_MILLI_SECONDS);

        double seconds = 0;
        if (left > DateCalc.SECONDS_MILLI_SECONDS) {
            seconds = left / DateCalc.SECONDS_MILLI_SECONDS;
            seconds = seconds / 3600;
        }

        // System.out.println("seconds : "+seconds);
        double totalHours = 0;
        totalHours = hours + minutes + seconds;
        if (showDays) {
            totalHours = totalHours + days;
        }
        return totalHours;
    }
    //}

    /**
     * untuk menampilkan jam di java desktop create by satrya 2014-02-12
     *
     * @param LabelTime
     * @param LabelDate
     */
    public void getTimeInJavaDesktop(final JLabel LabelTime, final JLabel LabelDate) {
        if (LabelTime != null && LabelDate != null) {
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nol_jam = "";
                    String nol_menit = "";
                    String nol_detik = "";
                    // Membuat Date
                    Calendar dt = Calendar.getInstance();
                    // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                    int nilai_jam = dt.get(dt.HOUR_OF_DAY);
                    int nilai_menit = dt.get(dt.MINUTE);
                    int nilai_detik = dt.get(dt.SECOND);
                    if (nilai_jam <= 9) {
                        // Tambahkan "0" didepannya
                        nol_jam = "0";
                    }
                    // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                    if (nilai_menit <= 9) {
                        // Tambahkan "0" didepannya
                        nol_menit = "0";
                    }
                    // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                    if (nilai_detik <= 9) {
                        // Tambahkan "0" didepannya
                        nol_detik = "0";
                    }
                    // Membuat String JAM, MENIT, DETIK
                    String jam = nol_jam + Integer.toString(nilai_jam);
                    String menit = nol_menit + Integer.toString(nilai_menit);
                    String detik = nol_detik + Integer.toString(nilai_detik);
                    LabelTime.setText("Time : " + jam + ":" + menit + ":" + detik);
                    LabelDate.setText("" + Formater.formatDate(new Date(), "EE, dd MMMM yyyy"));
                    //Tanggal();
                }
            };
            new javax.swing.Timer(1000, taskPerformer).start();
        }
    }

    /**
     * pemberian message jika sudah 10 detik create by satrya 2014-02-21
     *
     * @param jOptionPane
     * @param login
     */
    public void getTimeLoginError(final JOptionPane jOptionPane, final Login login) {
        // boolean time=true;
        //login.setCheckTime(true);

        ActionListener taskPerformer = new ActionListener() {
            int tmpTime = 0;
            boolean checkTime = true;

            public void actionPerformed(ActionEvent e) {
                // Membuat Date

                Calendar dt = Calendar.getInstance();
                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                int nilai_jam = dt.get(dt.HOUR_OF_DAY);
                int nilai_menit = dt.get(dt.MINUTE);
                int nilai_detik = dt.get(dt.SECOND);

                tmpTime = tmpTime + nilai_detik;

                if (tmpTime % 10 == 0 && login.isCheckTime()) {
                    tmpTime = 0;
                    String user = login != null && login.getUser() != null && login.getUser().length() > 0 ? login.getUser() : "";
                    jOptionPane.showMessageDialog(null, "<html>NIK or Password are invalid <br>  <b>User \"" + user + "\"</b> Cannot Login</html>");
                    login.dispose();
                    new Login().setVisible(true);
                    checkTime = false;
                    login.setCheckTime(checkTime);

                }
            }
        };
        if (login.isCheckTime()) {
            new javax.swing.Timer(1000, taskPerformer).start();
        }
        //else{

        //}


        //return isTimeCheck();
    }
} // end of Formater

