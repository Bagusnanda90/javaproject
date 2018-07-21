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
public class DataUploadMain extends Entity {

    private long dataMainId = 0;
    private long objectId = 0;
    private String objectClass = "";
    private String dataMainTitle = "";
    private String dataMainDesc = "";
    private long dataGroupId = 0;

    public long getDataMainId() {
        return dataMainId;
    }

    public void setDataMainId(long dataMainId) {
        this.dataMainId = dataMainId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getDataMainTitle() {
        return dataMainTitle;
    }

    public void setDataMainTitle(String dataMainTitle) {
        this.dataMainTitle = dataMainTitle;
    }

    public String getDataMainDesc() {
        return dataMainDesc;
    }

    public void setDataMainDesc(String dataMainDesc) {
        this.dataMainDesc = dataMainDesc;
    }

    public long getDataGroupId() {
        return dataGroupId;
    }

    public void setDataGroupId(long dataGroupId) {
        this.dataGroupId = dataGroupId;
    }
}
