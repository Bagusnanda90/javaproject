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
public class DataUploadDetail extends Entity {

    private long dataDetailId = 0;
    private String dataDetailTitle = "";
    private String dataDetailDesc = "";
    private long dataMainId = 0;
    private String filename = "";

    public long getDataDetailId() {
        return dataDetailId;
    }

    public void setDataDetailId(long dataDetailId) {
        this.dataDetailId = dataDetailId;
    }

    public String getDataDetailTitle() {
        return dataDetailTitle;
    }

    public void setDataDetailTitle(String dataDetailTitle) {
        this.dataDetailTitle = dataDetailTitle;
    }

    public String getDataDetailDesc() {
        return dataDetailDesc;
    }

    public void setDataDetailDesc(String dataDetailDesc) {
        this.dataDetailDesc = dataDetailDesc;
    }

    public long getDataMainId() {
        return dataMainId;
    }

    public void setDataMainId(long dataMainId) {
        this.dataMainId = dataMainId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
