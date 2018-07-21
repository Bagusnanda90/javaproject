/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
/**
 *
 * @author GUSWIK
 */
public class DocType extends Entity { 

   
	private int doc_type_id ;
	private String type_name = "";
        private String description = "";

    /**
     * @return the doc_type_id
     */
    public int getDoc_type_id() {
        return doc_type_id;
    }

    /**
     * @param doc_type_id the doc_type_id to set
     */
    public void setDoc_type_id(int doc_type_id) {
        this.doc_type_id = doc_type_id;
    }

    /**
     * @return the type_name
     */
    public String getType_name() {
        return type_name;
    }

    /**
     * @param type_name the type_name to set
     */
    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
        
}
