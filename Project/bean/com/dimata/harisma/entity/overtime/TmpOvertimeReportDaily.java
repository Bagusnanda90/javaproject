/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class TmpOvertimeReportDaily {
    private Vector vOvForm = new Vector();
    private Vector vAllowance = new Vector();
    private Vector vPaidBy  = new Vector();
    private Vector vNetOv = new Vector();
    private Vector vOvTotIdx = new Vector();

    /**
     * @return the vOvForm
     */
    public int getvOvForm(int idx) {
        if(idx>=vOvForm.size()){
            return -1;
        }
        
        return (Integer)vOvForm.get(idx);
        //return vOvForm;
    }

    /**
     * @param vOvForm the vOvForm to set
     */
    public void AddvOvForm(int vOvForm) {
        this.vOvForm.add(vOvForm);
    }

    /**
     * @return the vAllowance
     */
    public int getvAllowance(int idx) {
        if(idx>=vAllowance.size()){
            return -1;
        }
        
        return (Integer)vAllowance.get(idx);
        //return vAllowance;
    }

    /**
     * @param vAllowance the vAllowance to set
     */
    public void addvAllowance(int vAllowance) {
        this.vAllowance.add(vAllowance);
    }

    /**
     * @return the vPaidBy
     */
    public int getvPaidBy(int idx) {
        if(idx>=vPaidBy.size()){
            return -1;
        }
        
        return (Integer)vPaidBy.get(idx);
        //return vPaidBy;
    }

    /**
     * @param vPaidBy the vPaidBy to set
     */
    public void addvPaidBy(int vPaidBy) {
        this.vPaidBy.add(vPaidBy);
    }

    /**
     * @return the vNetOv
     */
    public int getvNetOv(int idx) {
         if(idx>=vNetOv.size()){
            return -1;
        }
        
        return (Integer)vNetOv.get(idx);
        //return vNetOv;
    }

    /**
     * @param vNetOv the vNetOv to set
     */
    public void addvNetOv(double vNetOv) {
        this.vNetOv.add(vNetOv);
    }

    /**
     * @return the vOvTotIdx
     */
    public int getvOvTotIdx(int idx) {
        if(idx>=vOvTotIdx.size()){
            return -1;
        }
        
        return (Integer)vOvTotIdx.get(idx);
        //return vOvTotIdx;
    }

    /**
     * @param vOvTotIdx the vOvTotIdx to set
     */
    public void addvOvTotIdx(double vOvTotIdx) {
        this.vOvTotIdx.add(vOvTotIdx);
    }
}
