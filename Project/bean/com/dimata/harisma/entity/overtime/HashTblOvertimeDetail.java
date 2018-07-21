/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class HashTblOvertimeDetail {
    //Hashtable AdaOvertime = new Hashtable(); // contain : key : date : dd-MM-yyyy -> Bolean (truw)

    Hashtable cekAdaOvertime = new Hashtable();

    public void addOvetime(long employeeId, Date stDt, Date endDt) {
        if (stDt != null && endDt != null) {
            // for()
            long diffStartToFinish = endDt.getTime() - stDt.getTime();
            if (diffStartToFinish >= 0) {
                int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));
                for (int i = 0; i <= itDate; i++) {
                    Date selectedDate = new Date(stDt.getYear(), stDt.getMonth(), (stDt.getDate() + i));
                    if (cekAdaOvertime.containsKey("" + employeeId)) {
                        Hashtable adaOvertime = (Hashtable) cekAdaOvertime.get("" + employeeId);
                        adaOvertime.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                    } else {
                        Hashtable adaOvertime = new Hashtable();
                        adaOvertime.put(Formater.formatDate(selectedDate, "dd-MM-yyyy"), new Boolean(true));
                        cekAdaOvertime.put("" + employeeId, adaOvertime);
                    }
                }
            }
        }

    }

    public boolean getCekingOvertime(long employeeId, Date dt) {
        if (dt != null) {
            if (cekAdaOvertime.containsKey("" + employeeId)) {
                Hashtable adaOvertime = (Hashtable) cekAdaOvertime.get("" + employeeId);
                if (adaOvertime.containsKey(Formater.formatDate(dt, "dd-MM-yyyy"))) {
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
}
