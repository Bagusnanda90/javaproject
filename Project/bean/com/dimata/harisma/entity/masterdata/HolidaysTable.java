/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class HolidaysTable extends Entity {

    Hashtable national = new Hashtable(); // contain : key : date : dd-MM-yyyy -> Bolean (truw)
    Hashtable byReligion = new Hashtable();  // contain : hashtable:  religion_oid -> Hastable of key : dd-MM-yyyy -> Bolean (truw)
    Hashtable descHolidayReligion = new Hashtable();
    Hashtable descHolidayNational = new Hashtable();
    //Hashtable holStatus = new Hashtable();//menentukan dia apakah religionnya islam,hindu dll
    public void addNationalHoliday(Date dt) {
        if (dt != null) {
            national.put(Formater.formatDate(dt, "dd-MM-yyyy"), new Boolean(true));
        }
    } 

    public void addNationalHoliday(Date dtStart, Date dtEnd) {
        if (dtStart != null && dtEnd != null) {
            //for()
            long diffStartToFinish = dtEnd.getTime() - dtStart.getTime();
            if (diffStartToFinish >= 0) {
                int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                for (int i = 0; i <= itDate; i++) {
                    Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + i));
                    national.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                }
            }
        }
    }

    public boolean getNationalHoliday(Date dt) {
        if (dt != null && national.containsKey(Formater.formatDate(dt, "dd-MM-yyyy"))) {
            return true;
        }
        return false;
    }

    public void addReligionHoliday(long religionOid, Date dt) {
        if (dt != null) {
            if (byReligion.containsKey("" + religionOid)) {
                Hashtable aRelHolidays = (Hashtable) byReligion.get("" + religionOid);
                aRelHolidays.put(Formater.formatDate(dt, "dd-MM-yyyy"), new Boolean(true));
            } else {
                Hashtable aRelHolidays = new Hashtable();
                aRelHolidays.put(Formater.formatDate(dt, "dd-MM-yyyy"), new Boolean(true));
                byReligion.put("" + religionOid, aRelHolidays);
            }
        }
    }
    
     public void addDescHolidayReligion(long statusHoliday,Date dt, String desc) {
        if (dt != null) {
            if(descHolidayReligion.containsKey(""+statusHoliday)){
             Hashtable aDescHolidays = (Hashtable)descHolidayReligion.get(""+statusHoliday);
                aDescHolidays.put(Formater.formatDate(dt, "dd-MM-yyyy"), desc);
            }else{
                Hashtable aDescHolidays = new Hashtable();
                aDescHolidays.put(Formater.formatDate(dt, "dd-MM-yyyy"), desc);
                descHolidayReligion.put("" + statusHoliday, aDescHolidays);
            }
            //descHoliday.put(Formater.formatDate(dt,"dd-MM-yyyy"), desc);
        }
    }
    
    public void addDescHolidayNational(Date dt,Date dtTo, String desc) {
        if (dt != null && dtTo!=null) {
           
            long diffStartToFinish = dtTo.getTime() - dt.getTime();
            if (diffStartToFinish >= 0) {
                int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                for (int i = 0; i <= itDate; i++) {
                    Date selectedDate = new Date(dt.getYear(), dt.getMonth(), (dt.getDate() + i));
                    //national.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                     descHolidayNational.put(Formater.formatDate(selectedDate,"dd-MM-yyyy"), desc);
                }
            }
        
               
            
            //descHoliday.put(Formater.formatDate(dt,"dd-MM-yyyy"), desc);
        }
    }
     
public String getDescHoliday(long statusHolidays,Date dt) {
    String objDesc = "";
        if (dt != null) {
           try{
            // objDesc = (String) descHoliday.get(Formater.formatDate(dt,"dd-MM-yyyy")); 
              if(getNationalHoliday(dt)){
                objDesc = (String) descHolidayNational.get(Formater.formatDate(dt,"dd-MM-yyyy")); 
              }else{
                    if (descHolidayReligion.containsKey("" + statusHolidays)) {
                        Hashtable aDescHolidays = (Hashtable) descHolidayReligion.get("" + statusHolidays);
                        objDesc = (String) aDescHolidays.get(Formater.formatDate(dt,"dd-MM-yyyy")); 
                    }
              }
               
           }catch(Exception ex){
             return objDesc;
           }
        }
        return objDesc;
    }



     public boolean isHoliday(long religionOid, Date dt) {
      if(getNationalHoliday(dt)){
          return true;
      }else{
          return getReligionHoliday(religionOid, dt);
      }
     }

    /*   public void getNationalHoliday(Date dtStart, Date dtEnd ){
     if(dtStart!=null && dtEnd!=null){
     //for()
     national.put(Formater.formatDate(dt, "dd-MM-yyyy"),new Boolean(true));                  
     }    
     }*/
    public boolean getReligionHoliday(long religionOid, Date dt) {
        if (dt != null) {
            if (byReligion.containsKey("" + religionOid)) {
                Hashtable aRelHolidays = (Hashtable) byReligion.get("" + religionOid);
                if (aRelHolidays.containsKey(Formater.formatDate(dt, "dd-MM-yyyy"))) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return false;
    }
    public void addReligionHoliday(long religionOid, Date dtStart, Date dtEnd) {
        if (dtStart != null && dtEnd != null) {
            // for()
            long diffStartToFinish = dtEnd.getTime() - dtStart.getTime();
            if (diffStartToFinish >= 0) {
                int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                for (int i = 0; i <= itDate; i++) {
                    Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + i));
                    if (byReligion.containsKey("" + religionOid)) {
                        Hashtable aRelHolidays = (Hashtable) byReligion.get("" + religionOid);
                        aRelHolidays.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                    } else {
                        Hashtable aRelHolidays = new Hashtable();
                        aRelHolidays.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                        byReligion.put("" + religionOid, aRelHolidays);
                    }
                }
            }
        }
    }
}
