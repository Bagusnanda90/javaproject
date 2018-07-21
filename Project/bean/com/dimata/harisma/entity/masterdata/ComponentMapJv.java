/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;

public class ComponentMapJv extends Entity {

    private long componentId = 0;
    private String componentName = "";
    private long compCodeId = 0;

    public long getComponentId() {
        return componentId;
    }

    public void setComponentId(long componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public long getCompCodeId() {
        return compCodeId;
    }

    public void setCompCodeId(long compCodeId) {
        this.compCodeId = compCodeId;
    }
}