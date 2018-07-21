/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.EmployeeSchedule;
import com.dimata.harisma.entity.attendance.mappingoutlet.MappingOutlet;
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
public class FrmMappingOutlet extends FRMHandler implements I_FRMInterface, I_FRMType {

    private MappingOutlet mappingOutlet;
    //private Hashtable<String, String> hashCheckRequeredSchedule = new Hashtable();
    private Hashtable<String, String> hashCheckRequered = new Hashtable();
    private Vector mappingOutletList = new Vector();
    private String msgFrm = "";
    public static final String FRM_MAPPING_OUTLET = "FRM_MAPPING_OUTLET";
    public static final int FRM_FIELD_OUTLET_EMPLOYEE_ID = 0;
    public static final int FRM_FIELD_LOCATION_ID = 1;
    public static final int FRM_FIELD_DATE_FROM = 2;
    public static final int FRM_FIELD_DATE_TO = 3;
    public static final int FRM_FIELD_POSITION_ID = 4;
    public static final int FRM_FIELD_EMPLOYEE_ID = 5;
    public static final int FRM_FIELD_SCHEDULE_SYMBOLE_ID = 6;
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
        "FRM_FIELD_SCHEDULE_SYMBOLE_ID"
    //"FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        //  TYPE_INT + ENTRY_REQUIRED
        //TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED
    };

    /**
     * Creates a new instance of FrmAlStockTaken
     */
    public FrmMappingOutlet() {
    }

    public FrmMappingOutlet(MappingOutlet objMappingOutlet) {
        this.mappingOutlet = objMappingOutlet;
    }

    public FrmMappingOutlet(HttpServletRequest request, MappingOutlet objMappingOutlet) {
        super(new FrmMappingOutlet(objMappingOutlet), request);
        this.mappingOutlet = objMappingOutlet;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_MAPPING_OUTLET;
    }

    public String[] getFieldNames() {

        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public MappingOutlet getEntityObject() {
        return mappingOutlet;
    }

    /**
     * pengecekan jika ada requered Schedule
     *
     * @param oidEmployeeOutlet
     * @return
     */
//    public String getCheckRequeredSchedule(long oidEmployeeOutlet) {
//        String msgError = "";
//        if (hashCheckRequeredSchedule != null && hashCheckRequeredSchedule.containsKey("" + oidEmployeeOutlet)) {
//            msgError = "<font color=\"#FF0000\">" + (String) hashCheckRequeredSchedule.get("" + oidEmployeeOutlet) + "</font>";
//        }
//        return msgError;
//    }
    /**
     * requered muliple check input
     * @param oidEmployeeOutlet
     * @return 
     */
    public String getCheckRequered(String employeeIds) {
        String msgError = "";
        if (hashCheckRequered != null && hashCheckRequered.containsKey(employeeIds)) {
            msgError = "<font color=\"#FF0000\">" + (String) hashCheckRequered.get(employeeIds) + "</font>";
        }
        return msgError;
    }
    
     public boolean isCheckRequered(String employeeIds) {
        boolean msgError = false;
        if (hashCheckRequered != null && hashCheckRequered.containsKey(employeeIds)) {
            return true;
        }
        return msgError;
    }
    
    public int getCheckRequeredSize(){
        return hashCheckRequered.size();
    }

    public void requestEntityObject(MappingOutlet mappingOutlet) {
        try {
            this.requestParam();
            mappingOutlet.setDtStart(getDate(FRM_FIELD_DATE_FROM));
            mappingOutlet.setDtdEnd(getDate(FRM_FIELD_DATE_TO));
            mappingOutlet.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            mappingOutlet.setLocationOutletId(getLong(FRM_FIELD_LOCATION_ID));
            mappingOutlet.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            // obEmployeeOutlet.setScheduleType(getInt(FRM_FIELD_SCHEDULE_TYPE));
            mappingOutlet.setScheduleId(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID));
            //obEmployeeOutlet.setSchedleSymbolId2nd(getLong(FRM_FIELD_SCHEDULE_SYMBOLE_ID_2ND));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    public Vector<MappingOutlet> requestEntityObjectMultiple(Hashtable hashSchedule, Hashtable hashScheduleSymbol) {
        try {
            this.requestParam();

            String[] userSelect = null;
            userSelect = this.getParamsStringValues("userSelected");
           // String[] scheduleChecked = null;
           // scheduleChecked = this.getParamsStringValues("selectedSchedule");
            
            //ScheduleSymbol scheduleSymbol = null;
            if (userSelect != null && userSelect.length > 0) {
                //Hashtable<String, Boolean> hashScheduleChecked = new Hashtable();
                /*if (scheduleChecked != null && scheduleChecked.length > 0) {
                    for (int x = 0; x < scheduleChecked.length; x++) {
                        try {
                            long oidEmployeeOutlet = Long.parseLong((scheduleChecked[x]));
                            hashScheduleChecked.put("" + oidEmployeeOutlet, true);
                            
                        } catch (Exception exc) {
                            System.out.println("Exc" + exc);
                        }
                    }
                }*/
                try {
                    for (int i = 0; i < userSelect.length; i++) {
                        //long empId = 0;
                        //empId = Long.parseLong(userSelect[i]);
                        long empId = Long.parseLong((userSelect[i].split("_")[0]));
                        long OID = Long.parseLong((userSelect[i].split("_")[1]));
                        if (empId != 0) {

                            try {
                                MappingOutlet mappingOutlet = new MappingOutlet();
                                mappingOutlet.setOID(OID);
                                mappingOutlet.setEmployeeId(empId);
                                
                                Date selectedDateFrom = getParamDateVer2(fieldNames[FRM_FIELD_DATE_FROM]+"_"+empId);
                                Date selectedDateTo = getParamDateVer2(fieldNames[FRM_FIELD_DATE_TO]+"_"+empId);
                                if (selectedDateFrom != null && selectedDateTo != null && selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                                    Date tempFromDate = selectedDateFrom;
                                    Date tempToDate = selectedDateTo;
                                    selectedDateFrom = tempToDate;
                                    selectedDateTo = tempFromDate;
                                }

                                mappingOutlet.setLocationOutletId(getParamLong(fieldNames[FRM_FIELD_LOCATION_ID]+"_"+empId));
                                mappingOutlet.setPositionId(getParamLong(fieldNames[FRM_FIELD_POSITION_ID]+"_"+empId));
                                mappingOutlet.setScheduleId(getParamLong(fieldNames[FRM_FIELD_SCHEDULE_SYMBOLE_ID]+"_"+empId));
                                //pengecekan jika user chentang
                                /*if (hashScheduleChecked != null && hashScheduleChecked.size() > 0 && hashScheduleChecked.containsKey("" + empId)) {
                                    mappingOutlet.addHashCheckedSchedule(empId); 
                                    //jika di centang
                                    if (mappingOutlet.getScheduleId() == 0) {
                                        hashCheckRequered.put(""+mappingOutlet.getEmployeeId()+"_schedule", "requeired data");
                                    }
                                }else{
                                    //alasan di kasi 0 karena user memilih schedule tetapi tidak di centang
                                    mappingOutlet.setScheduleId(0);
                                }*/
                               
                                if (mappingOutlet.getScheduleId() == 0) {
                                    String sDtStart = "";
                                    String sDtENd = "";
                                    if (selectedDateFrom != null) {
                                        //tmpStart.setHours(0);tmpStart.setMinutes(0);
                                        sDtStart = Formater.formatDate(selectedDateFrom, "yyyy-MM-dd");
                                    }
                                    if (selectedDateTo != null) {
                                        //tmpEnd.setHours(0);tmpEnd.setMinutes(0);
                                        sDtENd = Formater.formatDate(selectedDateTo, "yyyy-MM-dd");
                                    }
                                    if (hashSchedule != null && hashSchedule.get("" + mappingOutlet.getEmployeeId()) != null) {
                                        EmployeeSchedule employeeSchedule = (EmployeeSchedule) hashSchedule.get("" + mappingOutlet.getEmployeeId());
                                        long oidScheduleWithDtStart1st = employeeSchedule.getEmpSchedulesId1st("" + sDtStart + "_" + mappingOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtFrom() + "_" + employeeOutlet.getEmployeeId());
                                        long oidScheduleWithDtEnd1st = employeeSchedule.getEmpSchedulesId1st("" + sDtENd + "_" + mappingOutlet.getEmployeeId());//(Long) hashObjEmployeeSchedule.get(""+employeeOutlet.getDtTo() + "_" + employeeOutlet.getEmployeeId());
                                        if (hashScheduleSymbol != null && hashScheduleSymbol.get("" + oidScheduleWithDtStart1st) != null && hashScheduleSymbol.get("" + oidScheduleWithDtEnd1st) != null) {
                                            ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get("" + oidScheduleWithDtStart1st);
                                            if (symbol != null && symbol.getTimeIn() != null) {
                                                //selectedDateFrom.setHours(symbol.getTimeIn().getHours());
                                                //selectedDateFrom.setMinutes(symbol.getTimeIn().getMinutes());
                                                //update by satrya 2014-11-16 nnati hasilnya double di tanggalnya
                                                selectedDateFrom.setHours(0);
                                                selectedDateFrom.setMinutes(0);
                                            }
                                            ScheduleSymbol symbolOut = (ScheduleSymbol) hashScheduleSymbol.get("" + oidScheduleWithDtEnd1st);
                                            if (symbolOut != null && symbolOut.getTimeOut() != null) {
//                                                selectedDateTo.setHours(symbolOut.getTimeOut().getHours());
//                                                selectedDateTo.setMinutes(symbolOut.getTimeOut().getMinutes());
                                                //update by satrya 2014-11-16 nnati hasilnya double di tanggalnya
                                                selectedDateTo.setHours(0);
                                                selectedDateTo.setMinutes(0);
                                            }
                                        }
                                    }
                                }else if (selectedDateFrom != null && selectedDateTo != null && hashScheduleSymbol != null && hashScheduleSymbol.get("" + mappingOutlet.getScheduleId()) != null) {
                                    ScheduleSymbol symbol = (ScheduleSymbol) hashScheduleSymbol.get("" + mappingOutlet.getScheduleId());
                                    if (symbol != null && symbol.getTimeIn() != null) {
                                        //update by satrya 2014-11-16 nnati hasilnya double di tanggalnya
                                        //selectedDateFrom.setHours(symbol.getTimeIn().getHours());
                                        //selectedDateFrom.setMinutes(symbol.getTimeIn().getMinutes());
                                         selectedDateFrom.setHours(0);
                                         selectedDateFrom.setMinutes(0);
                                    }
                                    ScheduleSymbol symbolOut = (ScheduleSymbol) hashScheduleSymbol.get("" + mappingOutlet.getScheduleId());
                                    if (symbolOut != null && symbolOut.getTimeOut() != null) {
//                                        selectedDateTo.setHours(symbolOut.getTimeOut().getHours());
//                                        selectedDateTo.setMinutes(symbolOut.getTimeOut().getMinutes());
                                         //update by satrya 2014-11-16 nnati hasilnya double di tanggalnya
                                                selectedDateTo.setHours(0);
                                                selectedDateTo.setMinutes(0);
                                    }
                                }
                                mappingOutlet.setDtStart(selectedDateFrom);
                                mappingOutlet.setDtdEnd(selectedDateTo);
                                 if(mappingOutlet.getLocationOutletId()==0|| mappingOutlet.getPositionId()==0 || mappingOutlet.getDtStart()==null || mappingOutlet.getDtdEnd()==null){
                                    if(mappingOutlet.getLocationOutletId()==0){
                                         hashCheckRequered.put(""+mappingOutlet.getEmployeeId()+"_outlet", "requeired data");
                                    }
                                    if(mappingOutlet.getPositionId()==0){
                                         hashCheckRequered.put(""+mappingOutlet.getEmployeeId()+"_position", "requeired data");
                                    }
                                    if(mappingOutlet.getDtStart()==null || mappingOutlet.getDtdEnd()==null){
                                         hashCheckRequered.put(""+mappingOutlet.getEmployeeId()+"_dt", "requeired data");
                                    }
                                    
                                }
                                 
                                getMappingOutletList().add(mappingOutlet);
                            } catch (Exception e) {
                                System.out.println("Exception " + e.toString());
                            }

                        }
                    }
                } catch (Exception exc) {
                    System.out.println("Exc frmMappingOutlet" + exc);
                }
            } else {
                this.setMsgFrm("Employee can not selected");
            }
        } catch (Exception exc) {
            System.out.println("Exception rs to frmEmployeeOutlet" + exc);
        }
        return getMappingOutletList();
    }

    /**
     * @return the employeeOutletList
     */
    public Vector getMappingOutletList() {
        return mappingOutletList;
    }

    /**
     * @param employeeOutletList the employeeOutletList to set
     */
    public void setEmployeeOutletList(Vector mappOutletList) {
        this.mappingOutletList = mappOutletList;
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
