/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;

/**
 *
 * @author Satrya Ramayu
 */
public class JComboBoxItem {

    private String key;
    private String value;
    private String tooltips;
    private int index;

    public JComboBoxItem() {
    }

    public JComboBoxItem(String key, String value, int idx) {
        this.key = key;
        this.value = value;
        this.index = idx;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     * create by satrya Ramayu 2014-06-29 <p>membuat combo box di Desktop</p>
     *
     * @param javax.swing.JComboBox cbxForm
     * @param selectValue
     * @param keys
     * @param vals
     * @param tooltip
     */
    public void drawCombobox(javax.swing.JComboBox cbxForm, String selectValue, Vector keys, Vector vals) {
        try {
            DefaultComboBoxModel defaultComboBoxModel = (DefaultComboBoxModel) cbxForm.getModel();
            if (keys != null && keys.size() > 0 && vals != null && vals.size() > 0) {
                for (int i = 0; i < keys.size(); i++) {
                    String key = (String) keys.get(i);
                    String val = (String) vals.get(i);
                    // getting exiting combo box model
                    // model.addElement(new JComboBoxItem(key, val, i));
                    cbxForm.addItem(new JComboBoxItem(key, val, i));
                    if (selectValue != null && selectValue.length() > 0) {
                        if (val.equals(selectValue)) {
                            defaultComboBoxModel.setSelectedItem(new JComboBoxItem(key, val, i));
                            //hanya untuk test saja
                            //Object itemSch = cbxForm.getSelectedItem();
                        }
                    }
                }
            }
        } catch (Exception exc) {
            System.err.println("Error" + exc);
        }

    }

    /**
     * @return the tooltips
     */
    public String getTooltips() {
        return tooltips;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}
