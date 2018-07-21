/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.clinic;
import java.util.*;

/**
 *
 * @author Ketut Kartika T
 * @date
 * @description
 */

public class SumEmpMedExpense {
  private Date lastTaken = null;
  private double totalQty =0;
  private double totalAmount =0;
  private double totalTimes = 0;
  private Vector listMedRec = null;

    public Date getLastTaken() {
        return lastTaken;
    }

    public void setLastTaken(Date lastTaken) {
        this.lastTaken = lastTaken;
    }

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
