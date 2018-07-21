/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Satrya Ramayu
 */
public class JtableDestop {

    /**
     *  //jika colom yg pertama maka otomatis widthnya 0 berguna untuk hidden Id
     *
     * @param table
     */
    public void resizeColumnWidth(JTable table) {
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 10; // Min width
            if (column != 0) {
                for (int row = 0; row < table.getRowCount(); row++) {
                    TableCellRenderer renderer = table.getCellRenderer(row, column);
                    Component comp = table.prepareRenderer(renderer, row, column);
                    width = Math.max(comp.getPreferredSize().width, width);
                    columnModel.getColumn(column).setPreferredWidth(width);
                    table.getColumnModel().getColumn(column).setWidth(width);
                }
            } else {
                //jika colom yg pertama maka otomatis widthnya 0 berguna untuk hidden Id
                width = 0;
                table.getColumnModel().getColumn(column).setMinWidth(0);
                table.getColumnModel().getColumn(column).setMaxWidth(0);
                table.getColumnModel().getColumn(column).setWidth(0);
            }


            //table.setColumnModel(columnModel);
            //this.setColumnWidths(this.columnWidths);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.doLayout();
    }
}
