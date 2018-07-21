/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.employeeoutlet;

import com.dimata.harisma.entity.attendance.EmployeeSchedule;
import com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmEmployeeOutlet extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmployeeOutlet employeeOutlet;
    private Vector employeeOutletList = new Vector();
    private String msgFrm = "";
    public static final String FRM_EMPLOYEE_OUTLET = "FRM_EMPLOYEE_OUTLET";
    public static final int FRM_FIELD_OUTLET_EMPLOYEE_ID = 0;
    public static final int FRM_FIELD_LOCATION_ID = 1;
    public static final int FRM_FIELD_DATE_FROM = 2;
    public static final int FRM_FIELD_DATE_TO = 3;
    public static final int FRM_FIELD_POSITION_ID = 4;
    public static final int FRM_FIELD_EMPLOYEE_ID = 5;
    //public static final int FRM_FIELD_SCHEDULE_SYMBOLE_ID = 6;
    //public static final int FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND = 7;
    //public static final int FRM_FIELD_SCHEDULE_TYPE = 6;//ini termasuk normal schedule atau extra schedule
    public static String[] fieldNames = {
        "FRM_FIELD_OUTLET_EMPLOYEE_ID",
        "FRM_FIELD_LOCATION_ID",
        "FRM_FIELD_DATE_FROM",
        "FRM_FIELD_DATE_TO",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_EMPLOYEE_ID",
     //   "FRM_FIELD_SCHEDULE_TYPE"
    //"FRM_FIELD_SCHEDULE_SYMBOLE_ID"
    //"FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG
      //  TYPE_INT + ENTRY_REQUIRED
    //TYPE_LONG + ENTRY_REQUIRED,
      //  TYPE_LONG
    };

    /**
     * Creates a new instance of FrmAlStockTaken
     */
    public FrmEmployeeOutlet() {
    }

    public FrmEmployeeOutlet(EmployeeOutlet objEmployeeOutlet) {
        this.employeeOutlet = objEmployeeOutlet;
    }

    public FrmEmployeeOutlet(HttpServletRequest request, EmployeeOutlet objEmployeeOutlet) {
        super(new FrmEmployeeOutlet(objEmployeeOutlet), request);
        this.employeeOutlet = objEmployeeOutlet;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_EMPLOYEE_OUTLET;
    }

    public String[] getFieldNames() {

        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public EmployeeOutlet getEntityObject() {
        return employeeOutlet;
    }

    public void requestEntityObject(EmployeeOutlet obEmployeeOutlet) {
        try {
            this.requestParam();
            obEmployeeOutlet.setDtFrom(getDate(FRM_FIELD_DATE_FROM));
            obEmployeeOutlet.setDtTo(getDate(FRM_FIELD_DATE_TO));
            obEmployeeOutlet.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            obEmployeeOutlet.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
            obEmployeeOutlet.setPositionId(getLong(FRM_FIELD_POSITION_ID));
           // obEmployeeOutlet.setScheduleType(getInt(FRM_FIELD_SCHEDULE_TYPE));
            //obEmployeeOutlet.setSchedleSymbolId(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID)); 
            //obEmployeeOutlet.setSchedleSymbolId2nd(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    public Vector<EmployeeOutlet> requestEntityObjectMultiple(Hashtable hashSchedule, Hashtable hashScheduleSymbol) {
        try {
            this.requestParam();

            String[] userSelect = null;
            userSelect = this.getParamsStringValues("userSelect");
            ScheduleSymbol scheduleSymbol = null;
            if (userSelect != null && userSelect.length > 0) {

                for (int i = 0; i < userSelect.length; i++) {
                    long empId = 0;
                    empId = Long.parseLong(userSelect[i]);
                    if (empId != 0) {

                        try {
                            EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                            employeeOutlet.setEmployeeId(empId);
                            Date selectedDateFrom = getDate(FRM_FIELD_DATE_FROM);
                            Date selectedDateTo = getDate(FRM_FIELD_DATE_TO);
                            if (selectedDateFrom!=null && selectedDateTo!=null && selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                                Date tempFromDate = selectedDateFrom;
                                Date tempToDate = selectedDateTo;
                                selectedDateFrom = tempToDate;
                                selectedDateTo = tempFromDate;
                            }

                            employeeOutlet.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
                            employeeOutlet.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                            //employeeOutlet.setSchedleSymbolId(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID));
                          //  employeeOutlet.setScheduleType(getInt(FRM_FIELD_SCHEDULE_TYPE));

                            //employeeOutlet.setSchedleSymbolId2nd(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND));
                                //Date tmpStart =selectedDateFrom!=null?(Date)selectedDateFrom.clone():null;
                                //Date tmpEnd =selectedDateTo!=null?(Date)selectedDateTo.clone():null;
                                String sDtStart="";
                                String sDtENd="";
                                if(selectedDateFrom!=null){
                                    //tmpStart.setHours(0);tmpStart.setMinutes(0);
                                    sDtStart=Formater.formatDate(selectedDateFrom, "yyyy-MM-dd");
                                }
                                if(selectedDateTo!=null){
                                     //tmpEnd.setHours(0);tmpEnd.setMinutes(0);
                                     sDtENd=Formater.formatDate(selectedDateTo, "yyyy-MM-dd");
                                }
                                        if (hashSchedule != null && hashSchedule.get(""+employeeOutlet.getEmployeeId()) != null) {
                                            EmployeeSchedule employeeSchedule = (EmployeeSchedule)hashSchedule.get(""+employeeOutlet.getEmployeeId());
                                            long oidScheduleWithDtStart1st = employeeSchedule.getEmpSchedulesId1st(""+sDtStart + "_" + employeeOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtFrom() + "_" + employeeOutlet.getEmployeeId());
                                            long oidScheduleWithDtEnd1st = employeeSchedule.getEmpSchedulesId1st(""+sDtENd + "_" + employeeOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtTo() + "_" + employeeOutlet.getEmployeeId());
                                            //long oidScheduleWithDtStart2nd = employeeSchedule.getEmpSchedulesId2st(""+sDtStart + "_" + employeeOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtFrom() + "_" + employeeOutlet.getEmployeeId());
                                            //long oidScheduleWithDtEnd2nd = employeeSchedule.getEmpSchedulesId2st(""+sDtENd + "_" + employeeOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtFrom() + "_" + employeeOutlet.getEmployeeId());

                                            //Date TimeIn = null;
                                            //if (employeeOutlet.getScheduleType() == PstScheduleSymbol.SCHEDULE_TYPE_NORMAL) {
                                                if (hashScheduleSymbol != null && hashScheduleSymbol.get(""+oidScheduleWithDtStart1st) != null && hashScheduleSymbol.get(""+oidScheduleWithDtEnd1st) != null) {
                                                    ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get(""+oidScheduleWithDtStart1st);
                                                    if (symbol != null && symbol.getTimeIn() != null) {
                                                        selectedDateFrom.setHours(symbol.getTimeIn().getHours());
                                                        selectedDateFrom.setMinutes(symbol.getTimeIn().getMinutes());
                                                    }
                                                    ScheduleSymbol symbolOut = (ScheduleSymbol) hashScheduleSymbol.get(""+oidScheduleWithDtEnd1st);
                                                    if (symbolOut != null && symbolOut.getTimeOut() != null) {
                                                        selectedDateTo.setHours(symbolOut.getTimeOut().getHours());
                                                        selectedDateTo.setMinutes(symbolOut.getTimeOut().getMinutes());
                                                    }
                                                }
                                           /* } else {
                                                if (hashScheduleSymbol != null && hashScheduleSymbol.get(""+oidScheduleWithDtStart2nd) != null && hashScheduleSymbol.get(""+oidScheduleWithDtEnd2nd) != null) {
                                                    ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get(""+oidScheduleWithDtStart2nd);
                                                    if (symbol != null && symbol.getTimeIn() != null) {
                                                        selectedDateFrom.setHours(symbol.getTimeIn().getHours());
                                                        selectedDateFrom.setMinutes(symbol.getTimeIn().getMinutes());
                                                    }
                                                    ScheduleSymbol symbolOut = (ScheduleSymbol) hashScheduleSymbol.get(""+oidScheduleWithDtEnd2nd);
                                                    if (symbolOut != null && symbolOut.getTimeOut() != null) {
                                                        selectedDateTo.setHours(symbolOut.getTimeOut().getHours());
                                                        selectedDateTo.setMinutes(symbolOut.getTimeOut().getMinutes());
                                                    }
                                                }
                                            }*/
                                        }
                                       
                            /* if(employeeOutlet.getSchedleSymbolId2nd()!=0){
                             try{
                             scheduleSymbol = PstScheduleSymbol.fetchExc(employeeOutlet.getSchedleSymbolId2nd());
                             }catch(Exception exc){
                        
                             }
                             /*if(scheduleSymbol!=null && scheduleSymbol.getTimeIn2nd()!=null){
                             selectedDateFrom.setHours(scheduleSymbol.getTimeIn2nd().getHours());selectedDateFrom.setMinutes(scheduleSymbol.getTimeIn2nd().getMinutes());
                             }*/
//                        if(scheduleSymbol!=null &&  scheduleSymbol.getTimeOut2nd()!=null){
//                            selectedDateTo.setHours(scheduleSymbol.getTimeOut2nd().getHours());selectedDateTo.setMinutes(scheduleSymbol.getTimeOut2nd().getMinutes()); 
//                        }
//                    }
                            employeeOutlet.setDtFrom(selectedDateFrom);
                            employeeOutlet.setDtTo(selectedDateTo);
                            getEmployeeOutletList().add(employeeOutlet);
                        } catch (Exception e) { 
                            System.out.println("Exception " + e.toString());
                        }

                    }
                }
            } else {
                this.setMsgFrm("Employee can not selected");
            }
        } catch (Exception exc) {
            System.out.println("Exception rs to frmEmployeeOutlet" + exc);
        }
        return getEmployeeOutletList();
    }

    /**
     * @return the employeeOutletList
     */
    public Vector getEmployeeOutletList() {
        return employeeOutletList;
    }

    /**
     * @param employeeOutletList the employeeOutletList to set
     */
    public void setEmployeeOutletList(Vector employeeOutletList) {
        this.employeeOutletList = employeeOutletList;
    }

    /**
     * @return the msgFrm
     */
    public String getMsgFrm() {
        return msgFrm;
    }

    /**
     * @param msgFrm the msgFrm to set
     */
    public void setMsgFrm(String msgFrm) {
        this.msgFrm = msgFrm;
    }
}
