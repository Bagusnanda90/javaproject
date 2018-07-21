/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util;

import java.util.Hashtable;

/**
 * Description : Dynamic Field is use to store data by dynamic.
 * Date : 2015-09-30
 * @author Hendra Putu
 */
public class DynamicField {

    private Hashtable<String, String> fields = new Hashtable<String, String>();

    public void setFields(String key, String val) {
        this.fields.put(key, val);
    }

    public String getField(String key) {
        return this.fields.get(key);
    }
}