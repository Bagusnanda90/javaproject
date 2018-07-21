/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.entity.dataupload;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author khirayinnura
 */
public class DataUploadGroup extends Entity {

    private long dataGroupId = 0;
    private String dataGroupTitle = "";
    private String dataGroupDesc = "";
    private int dataGroupTipe = 0;
    private int systemName = 0;
    private int modul = 0;

    public long getDataGroupId() {
        return dataGroupId;
    }

    public void setDataGroupId(long dataGroupId) {
        this.dataGroupId = dataGroupId;
    }

    public String getDataGroupTitle() {
        return dataGroupTitle;
    }

    public void setDataGroupTitle(String dataGroupTitle) {
        this.dataGroupTitle = dataGroupTitle;
    }

    public String getDataGroupDesc() {
        return dataGroupDesc;
    }

    public void setDataGroupDesc(String dataGroupDesc) {
        this.dataGroupDesc = dataGroupDesc;
    }

    public int getDataGroupTipe() {
        return dataGroupTipe;
    }

    public void setDataGroupTipe(int dataGroupTipe) {
        this.dataGroupTipe = dataGroupTipe;
    }

    public int getSystemName() {
        return systemName;
    }

    public void setSystemName(int systemName) {
        this.systemName = systemName;
    }

    public int getModul() {
        return modul;
    }

    public void setModul(int modul) {
        this.modul = modul;
    }
}
