/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

/**
 * Description : Date :
 *
 * @author Hendra Putu
 */
public class SessBenefitLevel {

    private long levelId = 0;
    private String level = "";
    private int levelPoint = 0;
    private int countPoint = 0;
    private int sumPoint = 0;

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLevelPoint() {
        return levelPoint;
    }

    public void setLevelPoint(int levelPoint) {
        this.levelPoint = levelPoint;
    }

    public int getCountPoint() {
        return countPoint;
    }

    public void setCountPoint(int countPoint) {
        this.countPoint = countPoint;
    }

    public int getSumPoint() {
        return sumPoint;
    }

    public void setSumPoint(int sumPoint) {
        this.sumPoint = sumPoint;
    }
}
