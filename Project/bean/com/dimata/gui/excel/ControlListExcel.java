/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.gui.excel;

import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author RAMAYU
 */
public class ControlListExcel {

    private Vector headerRowspanStart = new Vector();
    private Vector headerColspanStart = new Vector();
    private Vector headerRowspanEnd = new Vector();
    private Vector headerColspanEnd = new Vector();
    private Vector headerName = new Vector();
    private Vector numberOfRow = new Vector();
    private Vector styleHeader = new Vector();
//    private int rowspanStart=1;
//    private int rowspanEnd=1;
//    private int colspanStart=0;
//    private int colspanEnd=0;
    private Vector data = new Vector();
    private Vector dataRowspanStart = new Vector();
    private Vector dataRowspanEnd = new Vector();
    private Vector dataColspanStart = new Vector();
    private Vector dataColspanEnd = new Vector();
    private Vector numberOfRowData = new Vector();
    private Vector numberOfColom = new Vector();
    private Vector styleData = new Vector();
    private Vector dataFormula = new Vector();
    private String dataHeightRow;

    /**
     * Berfungsi untuk menghapus data header agar objek" yg lama terhapus
     */
    public void resetHeader() {

        headerRowspanStart.removeAllElements();
        headerColspanStart.removeAllElements();
        headerRowspanEnd.removeAllElements();
        headerColspanEnd.removeAllElements();
        headerName.removeAllElements();
        numberOfRow.removeAllElements();
        styleHeader.removeAllElements();
    }

    /**
     * erfungsi untuk menghapus data row agar objek" yg lama terhapus
     */
    public void resetData() {

        data.removeAllElements();
        dataRowspanStart.removeAllElements();
        dataRowspanEnd.removeAllElements();
        dataColspanStart.removeAllElements();
        dataColspanEnd.removeAllElements();
        numberOfRowData.removeAllElements();
        styleData.removeAllElements();
        dataFormula.removeAllElements();
    }

    /**
     * create by satrya 2014-05-07
     *
     * @param value : nilai atau nama dari headernya
     * @param rowspanStart : first row (0-based)
     * @param rowspanEnd : last row (0-based)
     * @param colspanStart : first column (0-based)
     * @param colspanEnd : last column (0-based)
     */
    public void addHeader(String value, int numberRow, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle) {
        this.headerName.add(value);
        this.headerRowspanStart.add(rowspanStart);
        this.headerRowspanEnd.add(rowspanEnd);
        this.headerColspanStart.add(colspanStart);
        this.headerColspanEnd.add(colspanEnd);
        this.numberOfRow.add(numberRow);
        this.styleHeader.add(cellStryle);
    }

    /**
     * Keterangan: untuk membentuk header <p>create by satrya 2014-05-07</p>
     * <p>jika ingin melakukan marge data A4:F4 maka rawStart dan rawEnd'nya
     * harus sama</p> <p>jika ingin melakukan marge data A5:A6 maka numberRow
     * dan rowspanStart'nya harus sama</p>
     *
     * @param sheet
     * @param cellStryle
     * @param row
     */
    public void drawHeaderExcel(HSSFSheet sheet) {
        HSSFRow row = null;
        HSSFCell cell = null;
        if (sheet != null) {
            int tmpRow = -1;
            //int tmpNumRow = 0;
            if (headerName != null && headerName.size() > 0) {
                for (int x = 0; x < headerName.size(); x++) {
                    int rowspanStart = headerRowspanStart != null && headerRowspanStart.size() > 0 ? (Integer) headerRowspanStart.get(x) : 0;
                    int rowspanEnd = headerRowspanEnd != null && headerRowspanEnd.size() > 0 ? (Integer) headerRowspanEnd.get(x) : 0;
                    int colspanStart = headerColspanStart != null && headerColspanStart.size() > 0 ? (Integer) headerColspanStart.get(x) : 0;
                    int colspanEnd = headerColspanEnd != null && headerColspanEnd.size() > 0 ? (Integer) headerColspanEnd.get(x) : 0;
                    int numberRow = numberOfRow != null && numberOfRow.size() > 0 ? (Integer) numberOfRow.get(x) : 0;
                    HSSFCellStyle cellStryle = (styleHeader != null && styleHeader.size() > 0 ? (HSSFCellStyle)styleHeader.get(x) : null); 
                    if (rowspanStart != 0 || rowspanEnd != 0 || colspanStart != 0 || colspanEnd != 0) {
                        //tmpNumRow = x;
                        if (tmpRow != numberRow) {
                            row = sheet.createRow((short) numberRow);
                            tmpRow = numberRow;
                        }

                        if (row != null) {
                            cell = row.createCell((short) x);
                            if (((String) headerName.get(x)) != null) {
                                cell.setCellValue(String.valueOf((String) headerName.get(x)));
                                //int letakRowNilaiValue = findRow(sheet, String.valueOf((String) headerName.get(x)));
                                //cell.getCellNum();
//                                if (letakRowNilaiValue > 0) {
//                                    boolean okde = true;
//                                    row = sheet.getRow(letakRowNilaiValue);
//                                }
                            }
                            sheet.autoSizeColumn(x);//untuk sizenya agar otomatis
                            if (cellStryle != null) {
                                cell.setCellStyle(cellStryle);
                            }

                        }//end  

//                        sheet.addMergedRegion(new Region(
//                                rowspanStart,//row form
//                                (short) colspanStart, //cols from
//                                rowspanEnd, //row end
//                                (short) colspanEnd //cols end
//                                ));
                        sheet.addMergedRegion(new CellRangeAddress(
                                rowspanStart,//row form
                                rowspanEnd,//row end
                                colspanStart, //cols from
                                colspanEnd //cols end
                                ));
                        //sheet.addMergedRegion(CellRangeAddress.valueOf("A2:F2")); 



                    } else {
                        if (tmpRow != numberRow) {
                            row = sheet.createRow((short) numberRow);
                            tmpRow = numberRow;
                        }

                        if (row != null) {
                            cell = row.createCell((short) x);
                            if (((String) headerName.get(x)) != null) {
                                cell.setCellValue(String.valueOf((String) headerName.get(x)));
//                                int letakRowNilaiValue = findRow(sheet, String.valueOf((String) headerName.get(x)));
//                                //cell.getCellNum();
//                                if (letakRowNilaiValue > 0) {
//                                    boolean okde = true;
//                                    row = sheet.getRow(letakRowNilaiValue);
//                                }
                            }
                            sheet.autoSizeColumn(x);//untuk sizenya agar otomatis
                            if (cellStryle != null) {
                                cell.setCellStyle(cellStryle);
                            }

                        }//e

                    }


                }
            }
        }
    }

//    private static int findRow(HSSFSheet sheet, String cellContent) {
//        for (Row row : sheet) {
//            for (Cell cell : row) {
//                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//                    if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
//                        return row.getRowNum();
//                    }
//                }
//            }
//        }
//        return 0;
//    }

  

    /**
     * add data String
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
     * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataString(String value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
        this.styleData.add(cellStryle);
        this.dataFormula.add(dataFormulas);
    }

    /**
     * add data Integer
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
       * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataInteger(int value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
         this.styleData.add(cellStryle);
         this.dataFormula.add(dataFormulas);
    }

    /**
     * add data long
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
       * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataLong(long value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
         this.styleData.add(cellStryle);
         this.dataFormula.add(dataFormulas);
    }

    /**
     * add data Boolean
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
       * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataBoolean(boolean value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
         this.styleData.add(cellStryle);
         this.dataFormula.add(dataFormulas);
    }

    /**
     * add data Double
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
       * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataDouble(double value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
         this.styleData.add(cellStryle);
         this.dataFormula.add(dataFormulas);
    }

    /**
     * add data Float
     *
     * @param value
     * @param numRow
     * @param numColom
     * @param rowspanStart
     * @param rowspanEnd
     * @param colspanStart
     * @param colspanEnd
       * @param cellStryle : HSSFCellStyle
     * @param dataFormulas : String
     */
    public void addDataFloat(float value, int numRow,int numColom, int rowspanStart, int rowspanEnd, int colspanStart, int colspanEnd,HSSFCellStyle cellStryle,String dataFormulas) {
        this.data.add(value);
        this.dataRowspanStart.add(rowspanStart);
        this.dataRowspanEnd.add(rowspanEnd);
        this.dataColspanStart.add(colspanStart);
        this.dataColspanEnd.add(colspanEnd);
        this.numberOfRowData.add(numRow);
        this.numberOfColom.add(numColom);
         this.styleData.add(cellStryle);
         this.dataFormula.add(dataFormulas);
    }

    /**
     * Add data
     *
     * @param sheet
     * @param cellStryle
     */
    public void drawDataExcel(HSSFSheet sheet) {
        HSSFRow row = null;
        HSSFCell cell = null;
        if (sheet != null) {
            int tmpRow = -1;
            if (data != null && data.size() > 0) {
                for (int x = 0; x < data.size(); x++) {
                    int rowspanStart = dataRowspanStart != null && dataRowspanStart.size() > 0 ? (Integer) dataRowspanStart.get(x) : 0;
                    int rowspanEnd = dataRowspanEnd != null && dataRowspanEnd.size() > 0 ? (Integer) dataRowspanEnd.get(x) : 0;
                    int colspanStart = dataColspanStart != null && dataColspanStart.size() > 0 ? (Integer) dataColspanStart.get(x) : 0;
                    int colspanEnd = dataColspanEnd != null && dataColspanEnd.size() > 0 ? (Integer) dataColspanEnd.get(x) : 0;
                    int numberRow = numberOfRowData != null && numberOfRowData.size() > 0 ? (Integer) numberOfRowData.get(x) : 0;
                    int numberColom = numberOfColom != null && numberOfColom.size() > 0 ? (Integer) numberOfColom.get(x) : 0;
                    String dtFormula = dataFormula != null && dataFormula.size() > 0 ? (String) dataFormula.get(x) : null;
                    HSSFCellStyle cellStryle = (styleData != null && styleData.size() > 0 ? (HSSFCellStyle)styleData.get(x) : null); 
                    if (tmpRow != numberRow) {
                        row = sheet.createRow((short) numberRow);
                        tmpRow = numberRow;
                    }
                    if (row != null) {
                        cell = row.createCell((short) numberColom);
                        if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
                            cell.setCellValue(Integer.valueOf((Integer) data.get(x)));
                           
                        } else if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.String")) {
                            cell.setCellValue(String.valueOf((String) data.get(x)));
                        } else if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.Double")) {
                            cell.setCellValue(Double.valueOf((Double) data.get(x)));
                        } else if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.Float")) {
                            cell.setCellValue(Float.valueOf((Float) data.get(x)));
                        } else if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.Long")) {
                            cell.setCellValue(Long.valueOf((Long) data.get(x)));
                        } else if (data.get(x).getClass().getName().equalsIgnoreCase("java.lang.Boolean")) {
                            cell.setCellValue(Boolean.valueOf((Boolean) data.get(x)));
                        }
                        if(dtFormula!=null){
                            cell.setCellFormula(dtFormula); 
                        }
                        sheet.autoSizeColumn(x);//untuk sizenya agar otomatis

                        if (cellStryle != null) {
                            cell.setCellStyle(cellStryle);
                        }
                        if (rowspanStart != 0 || rowspanEnd != 0 || colspanStart != 0 || colspanEnd != 0) {
                            sheet.addMergedRegion(new Region(
                                    rowspanStart,//row form
                                    (short) colspanStart, //cols from
                                    rowspanEnd, //row end
                                    (short) colspanEnd //cols end
                                    ));


                        }
                         data.remove(x);
                        dataRowspanStart.remove(x);
                        dataRowspanEnd.remove(x);
                        dataColspanStart.remove(x);
                        dataColspanEnd.remove(x);
                        numberOfRowData.remove(x);
                        styleData.remove(x);
                        dataFormula.remove(x);
                        numberOfColom.remove(x);
                         x= x-1;
                    }
                }
            }
        }
    }

    /**
     * @return the data
     */
    public Vector getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Vector data) {
        this.data = data;
    }

    /**
     * @return the dataRowspanStart
     */
    public Vector getDataRowspanStart() {
        return dataRowspanStart;
    }

    /**
     * @param dataRowspanStart the dataRowspanStart to set
     */
    public void setDataRowspanStart(Vector dataRowspanStart) {
        this.dataRowspanStart = dataRowspanStart;
    }

    /**
     * @return the dataRowspanEnd
     */
    public Vector getDataRowspanEnd() {
        return dataRowspanEnd;
    }

    /**
     * @param dataRowspanEnd the dataRowspanEnd to set
     */
    public void setDataRowspanEnd(Vector dataRowspanEnd) {
        this.dataRowspanEnd = dataRowspanEnd;
    }

    /**
     * @return the dataColspanStart
     */
    public Vector getDataColspanStart() {
        return dataColspanStart;
    }

    /**
     * @param dataColspanStart the dataColspanStart to set
     */
    public void setDataColspanStart(Vector dataColspanStart) {
        this.dataColspanStart = dataColspanStart;
    }

    /**
     * @return the dataColspanEnd
     */
    public Vector getDataColspanEnd() {
        return dataColspanEnd;
    }

    /**
     * @param dataColspanEnd the dataColspanEnd to set
     */
    public void setDataColspanEnd(Vector dataColspanEnd) {
        this.dataColspanEnd = dataColspanEnd;
    }

    /**
     * @return the dataHeightRow
     */
    public String getDataHeightRow() {
        return dataHeightRow;
    }

    /**
     * @param dataHeightRow the dataHeightRow to set
     */
    public void setDataHeightRow(String dataHeightRow) {
        this.dataHeightRow = dataHeightRow;
    }

    /**
     * @return the numberOfRow
     */
    public Vector getNumberOfRow() {
        return numberOfRow;
    }

    /**
     * @param numberOfRow the numberOfRow to set
     */
    public void setNumberOfRow(Vector numberOfRow) {
        this.numberOfRow = numberOfRow;
    }

    /**
     * @return the numberOfRowData
     */
    public Vector getNumberOfRowData() {
        return numberOfRowData;
    }

    /**
     * @param numberOfRowData the numberOfRowData to set
     */
    public void setNumberOfRowData(Vector numberOfRowData) {
        this.numberOfRowData = numberOfRowData;
    }
//    /** contoh penggunaan
//     *  int countRow = 0;
//
//            /**
//             * @DESC : TITTLE
//             */
//            for (int k = 0; k < tableTitle.length; k++) {
//                //row = sheet.createRow((short) (countRow));
//                //ctControlListExcel.addHeader(sheet, countRow, 0, tableTitle[k], styleTitle, "", 0, 0, 0, 0);
//
//                ctControlListExcel.addHeader(tableTitle[k], countRow, countRow, countRow, 0, 5);
//                ctControlListExcel.drawHeaderExcel(sheet, styleTitle);
//                ctControlListExcel.resetHeader();
//                countRow++;
//
//            }
//            int numColum = 65;
//            for (int j = 1; j <= numColum; j++) {
//                ctControlListExcel.addHeader(null, countRow, 0, 0, 0, 0);
//
//                //ctControlListExcel.resetHeader();
//            }
//            //ctControlListExcel.drawHeaderExcel(sheet, style);
//            ctControlListExcel.drawHeaderExcel(sheet, style);
//            ctControlListExcel.resetHeader();
//
//
//            countRow = countRow + 1;
//
//            /**
//             * @DESC : HEADER
//             */
//            //String[] tableHeader = new String[(showOvertime == 0?46:31) + (vSpesialLeaveSymbole.size() * 2) + vReason.size() * 2];
////            row = sheet.createRow((short) (countRow));
////            ctControlListExcel.addHeader( "Test", 0, 0, 0, 1);
////            int countRowPrev = countRow;
////            int countRowNew=countRow+1;
////            row = sheet.createRow((short) (countRowNew));
//            //ctControlListExcel.addHeader( "Test",countRow, 0, 0, 0, 1);
//            ctControlListExcel.addHeader("NO", countRow, 4, 5, 0, 0);
//            //ctControlListExcel.addHeader( "Test",countRow, 0, 0, 0, 0);
////            ctControlListExcel.addHeader("Payroll Number", (countRow+1), 0, 0, 0, 0);
////            ctControlListExcel.addHeader("Full Name", (countRow+1), 0, 0, 0, 0);
//            ctControlListExcel.addHeader("Payroll Number", (countRow), (countRow), (countRow), 1, 2);
//            ctControlListExcel.addHeader("", (countRow), 0, 0, 0, 0);
//            //ctControlListExcel.addHeader("test cuy", countRow, 0, 0, 2, 2);
////            ctControlListExcel.addHeader( "Ok", 0, 0, 3, 4);
//
//            ctControlListExcel.addHeader("Division", countRow, 4,5, 3, 3);
//            ctControlListExcel.addHeader("Departement", countRow, 0, 0, 0, 0);
//            ctControlListExcel.addHeader("Section", countRow, 0, 0, 0, 0);
//            
//           ctControlListExcel.addHeader("x", (countRow+1), 0, 0, 0, 0);
//           
//            int no = 0;
//            try {
//
//                if (listEmployee != null && listEmployee.size() > 0) {
//                    ctControlListExcel.drawHeaderExcel(sheet, null);
//                    ctControlListExcel.resetData();
//                    countRow = countRow + 2;
//                    for (int idxRecord = 0; idxRecord < listEmployee.size(); idxRecord++) {
//                        float sumLeaveTotal = 0;
//                        no++;
//                        ctControlListExcel = new ControlListExcel();
//                        SummaryEmployeeAttendance summaryEmployeeAttendance = (SummaryEmployeeAttendance) listEmployee.get(idxRecord);
//
//
//
//                        sheet.createFreezePane(5, 6);
//                       // row = sheet.createRow((short) (countRow));
//                      
//
//                        ctControlListExcel.addDataInteger(no,countRow, 0, 0, 0, 0);
//                        ctControlListExcel.addDataString(summaryEmployeeAttendance.getEmployeeNum(),countRow, 0, 0, 0, 0);
//                        ctControlListExcel.addDataString(summaryEmployeeAttendance.getFullName(),countRow, 0, 0, 0, 0);
//                        ctControlListExcel.addDataString(summaryEmployeeAttendance.getDivision(),countRow, 0, 0, 0, 0);
//                        ctControlListExcel.addDataString(summaryEmployeeAttendance.getDepartment(),countRow, 0, 0, 0, 0);
//                        ctControlListExcel.addDataString(summaryEmployeeAttendance.getSection(),countRow, 0, 0, 0, 0);
//                        
//                        ctControlListExcel.drawDataExcel(sheet, null);
//                         countRow++;
//                    }//end LOOP
//                }
//            } catch (Exception ex) {
//                System.out.println("Exception export summary Attd" + ex);
//            }
//            ServletOutputStream sos = response.getOutputStream();
//            wb.write(sos);
//            sos.close();
//     */

    /**
     * @return the styleHeader
     */
    public Vector getStyleHeader() {
        return styleHeader;
    }

    /**
     * @param styleHeader the styleHeader to set
     */
    public void setStyleHeader(Vector styleHeader) {
        this.styleHeader = styleHeader;
    }

    /**
     * @return the styleData
     */
    public Vector getStyleData() {
        return styleData;
    }

    /**
     * @param styleData the styleData to set
     */
    public void setStyleData(Vector styleData) {
        this.styleData = styleData;
    }

    /**
     * @return the dataFormula
     */
    public Vector getDataFormula() {
        return dataFormula;
    }

    /**
     * @param dataFormula the dataFormula to set
     */
    public void setDataFormula(Vector dataFormula) {
        this.dataFormula = dataFormula;
    }

    /**
     * @return the numberOfColom
     */
    public Vector getNumberOfColom() {
        return numberOfColom;
    }

    /**
     * @param numberOfColom the numberOfColom to set
     */
    public void setNumberOfColom(Vector numberOfColom) {
        this.numberOfColom = numberOfColom;
    }
}
