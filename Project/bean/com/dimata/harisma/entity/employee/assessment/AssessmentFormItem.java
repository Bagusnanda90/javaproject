/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee.assessment;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Artha
 */
public class AssessmentFormItem extends Entity{
    private long assFormSection;
    private String title;
    private String title_L2;
    private String itemPoin1;
    private String itemPoin2;
    private String itemPoin3;
    private String itemPoin4;
    private String itemPoin5;
    private String itemPoin6;
    private int type;
    private int orderNumber;
    private int number;
    private int height;
    private int page;
    private long kpiListId =0;
    private float weightPoint=0.0f;
    private float kpiTarget =0.0f;
    private String kpiUnit ="";
    private String kpiNote ="";
    private String entryUnit =""; // hanya untuk list tidak disimpan di database, diset dari section

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    
    
    public long getAssFormSection() {
        return assFormSection;
    }

    public void setAssFormSection(long assFormSection) {
        this.assFormSection = assFormSection;
    }

    public String getItemPoin1() {
        return itemPoin1;
    }

    public void setItemPoin1(String itemPoin1) {
        this.itemPoin1 = itemPoin1;
    }

    public String getItemPoin2() {
        return itemPoin2;
    }

    public void setItemPoin2(String itemPoin2) {
        this.itemPoin2 = itemPoin2;
    }

    public String getItemPoin3() {
        return itemPoin3;
    }

    public void setItemPoin3(String itemPoin3) {
        this.itemPoin3 = itemPoin3;
    }

    public String getItemPoin4() {
        return itemPoin4;
    }

    public void setItemPoin4(String itemPoin4) {
        this.itemPoin4 = itemPoin4;
    }

    public String getItemPoin5() {
        return itemPoin5;
    }

    public void setItemPoin5(String itemPoin5) {
        this.itemPoin5 = itemPoin5;
    }

    public String getItemPoin6() {
        return itemPoin6;
    }

    public void setItemPoin6(String itemPoin6) {
        this.itemPoin6 = itemPoin6;
    }

    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_L2() {
        return title_L2;
    }

    public void setTitle_L2(String title_L2) {
        this.title_L2 = title_L2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the kpiListId
     */
    public long getKpiListId() {
        return kpiListId;
    }

    /**
     * @param kpiListId the kpiListId to set
     */
    public void setKpiListId(long kpiListId) {
        this.kpiListId = kpiListId;
    }

    /**
     * @return the weightPoint
     */
    public float getWeightPoint() {
        return weightPoint;
    }

    /**
     * @param weightPoint the weightPoint to set
     */
    public void setWeightPoint(float weightPoint) {
        this.weightPoint = weightPoint;
    }

    /**
     * @return the kpiTarget
     */
    public float getKpiTarget() {
        return kpiTarget;
    }

    /**
     * @param kpiTarget the kpiTarget to set
     */
    public void setKpiTarget(float kpiTarget) {
        this.kpiTarget = kpiTarget;
    }

    /**
     * @return the kpiUNit
     */
    public String getKpiUnit() {
        return kpiUnit==null? "": kpiUnit;
    }

    /**
     * @param kpiUNit the kpiUNit to set
     */
    public void setKpiUnit(String kpiUNit) {
        this.kpiUnit = kpiUNit;
    }

    /**
     * @return the kpiNote
     */
    public String getKpiNote() {
        return kpiNote==null? "": kpiNote;
    }

    /**
     * @param kpiNote the kpiNote to set
     */
    public void setKpiNote(String kpiNote) {
        this.kpiNote = kpiNote;
    }

    /**
     * @return the entryUnit
     */
    public String getEntryUnit() {
        return entryUnit;
    }

    /**
     * @param entryUnit the entryUnit to set
     */
    public void setEntryUnit(String entryUnit) {
        this.entryUnit = entryUnit;
    }
    
    
}
