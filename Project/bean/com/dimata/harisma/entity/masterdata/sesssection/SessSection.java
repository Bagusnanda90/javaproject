/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.sesssection;

import com.dimata.harisma.entity.masterdata.Section;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessSection {
      private Vector listSection= new Vector();

    /**
     * @return the listSection
     */
    public Vector getListSection() {
        return listSection;
    }

    /**
     * @param listSection the listSection to set
     */
    public void addObjSection(Section section) {
        if(section!=null){
            this.listSection.add(section);
        }
        
    }
}
