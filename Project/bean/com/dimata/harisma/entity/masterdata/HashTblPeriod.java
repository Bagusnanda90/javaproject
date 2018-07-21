/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class HashTblPeriod {
    Hashtable period = new Hashtable();
    
    public void addPeriod(Date dtStart, Date dtEnd,Period period1) {
        if (dtStart != null && dtEnd != null) {
            //for()
            long diffStartToFinish = dtEnd.getTime() - dtStart.getTime();
            if (diffStartToFinish >= 0) {
                int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                for (int i = 0; i <= itDate; i++) {
                    Date selectedDate = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate() + i));
                    period.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), period1);
                }
            }
        }
    }
    /**
     * Keterangan: mengambil obj period
     * @param dt
     * @return 
     */
    public Period getPeriod(Date dt) {
        Period period1 = new Period();
        if (dt != null && period.containsKey(Formater.formatDate(dt, "dd-MM-yyyy"))) {
            period1 = (Period)period.get(Formater.formatDate(dt, "dd-MM-yyyy"));
            return period1;
        }
        return period1;
    }
}
