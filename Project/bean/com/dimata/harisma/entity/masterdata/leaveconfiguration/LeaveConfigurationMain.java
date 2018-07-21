/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.leaveconfiguration;

import com.dimata.gui.jsp.ControlCombo;
import com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationDepartment;
import com.dimata.harisma.form.masterdata.leaveconfiguration.FrmLeaveConfigurationPosition;
import com.dimata.qdep.entity.Entity;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class LeaveConfigurationMain extends Entity {

    private long employeeId;
    private String namaEmployee;
    private String emailEmployee;
    private String plusNoteApprovall;
    private String strTable;
    private String strTableAfterSave;

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the plusNoteApprovall
     */
    public String getPlusNoteApprovall() {
        if (plusNoteApprovall == null) {
            return "";
        }
        return plusNoteApprovall;
    }

    /**
     * @param plusNoteApprovall the plusNoteApprovall to set
     */
    public void setPlusNoteApprovall(String plusNoteApprovall) {
        this.plusNoteApprovall = plusNoteApprovall;
    }

    /**
     * @return the strTable
     */
    public String getStrTable(long oidEmployee,Vector listDepartment,Vector listSection, LeaveConfigurationDetailDepartement objConfigurationDetailDepartement,LeaveConfigurationDetailPosition objConfigurationDetailPosition,Vector listPosition,String source) {
        Vector deptValue = new Vector();//1
        Vector deptKey = new Vector();//2
        if (listDepartment != null && listDepartment.size() > 1) {
            try {
                deptValue = (Vector) listDepartment.get(0);
                deptKey = (Vector) listDepartment.get(1);
            } catch (Exception exc) {
            }
        }
        Vector sec_value = new Vector(1, 1);
        Vector sec_key = new Vector(1, 1);
        if(listSection!=null && listSection.size()>0){
            try {
                sec_value = (Vector) listSection.get(0);
                sec_key = (Vector) listSection.get(1);
            } catch (Exception exc) {
            }
        }
        
        Vector posValue = new Vector();//1
        Vector posKey = new Vector();//2
        if (listPosition != null && listPosition.size() > 1) {
            try {
                posValue = (Vector) listPosition.get(0);
                posKey = (Vector) listPosition.get(1);
            } catch (Exception exc) {
            }
        }
//        String strWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + 0;
//        Vector listSec = PstSection.list(0, 0, strWhere, " DEPARTMENT_ID, SECTION ");
//        for (int i = 0; i < listSec.size(); i++) {
//            Section sec = (Section) listSec.get(i);
//            sec_key.add(sec.getSection());
//            sec_value.add(String.valueOf(sec.getOID()));
//        }
        String attTagDept =  source==null || source.length()==0? "data-placeholder=\"\" style=\"width:350px;\"  tabindex=\"4\"   multiple": "data-placeholder=\"\" style=\"width:350px;\" onChange=\"javascript:cmdUpdateDep()\" tabindex=\"4\"   multiple"; //onChange=\"javascript:cmdUpdateDep()\"
        strTable = "<table width=\"100%\">"
                + "<tr>"
                + "<td colspan=\"2\">Department</td>"
                + "<td width=\"2%\">&nbsp;</td>"
                //+ "<td colspan=\"2\">Section</td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan=\"2\">"
                +ControlCombo.drawStringArraySelected(FrmLeaveConfigurationDepartment.fieldNames[FrmLeaveConfigurationDepartment.FRM_FIELD_DEPARTEMENT_ID], "chosen-select", null, objConfigurationDetailDepartement.getDepartementIds(), deptKey,deptValue,null, attTagDept)
                //+ ControlCombo.draw(FrmLeaveConfigurationDepartment.fieldNames[FrmLeaveConfigurationDepartment.FRM_FIELD_DEPARTEMENT_ID], "chosen-select", "-Selected-", "" + objConfigurationDetailDepartement.getDepartementId(), deptKey, deptValue, null, " onChange=\"javascript:cmdUpdateDep()\"")
                + "</td>"
                + "<td>&nbsp;</td>"
                + "<td colspan=\"2\">"
                + ControlCombo.draw(FrmLeaveConfigurationDepartment.fieldNames[FrmLeaveConfigurationDepartment.FRM_FIELD_SECTION_ID], "chosen-select", "-Selected-", "" + objConfigurationDetailDepartement.getSectionId(), sec_key, sec_value, null, "")
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan=\"5\">Position :</td>"
                + "</tr>"
                + "<tr>"
                + "<td width=\"6%\">Min : </td>"
                + "<td width=\"39%\">"
                + ControlCombo.draw(FrmLeaveConfigurationPosition.fieldNames[FrmLeaveConfigurationPosition.FRM_FIELD_POSITION_MIN_ID], "chosen-select", "-Selected-", "" + objConfigurationDetailPosition.getPositionMin(), posKey, posValue, null, " onChange=\"javascript:cmdUpdatePos('"+oidEmployee+"')\"")
                +"</td>"
                + "<td>&nbsp;</td>"
                + "<td width=\"6%\">Max : </td>"
                + "<td width=\"47%\">"
                + ControlCombo.draw(FrmLeaveConfigurationPosition.fieldNames[FrmLeaveConfigurationPosition.FRM_FIELD_POSITION_MAX_ID], "chosen-select", "-Selected-", "" + objConfigurationDetailPosition.getPositionMax(), posKey, posValue, null, "")
                +"</td>"
                + "</tr>"
                + "</table>";
        return strTable;
    }

    /**
     * @return the strTableAfterSave
     */
    public String getStrTableAfterSave(LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement, LeaveConfigurationDetailPosition leaveConfigurationDetailPosition) {
        strTableAfterSave = "<table width=\"100%\">"
  +"<tr>"
    +"<td width=\"5%\">Dept:</td>"
    +"<td width=\"31%\">"+leaveConfigurationDetailDepartement.getDepartementNama()+"</td>"
    +"<td width=\"5%\">Section:</td>"
    +"<td width=\"59%\">"+leaveConfigurationDetailDepartement.getSectionName()+"</td>"
  +"</tr>"
  +"<tr>"
    +"<td>Position:</td>"
    +"<td colspan=\"3\">"+leaveConfigurationDetailPosition.getNamaPosMin() +" until " + leaveConfigurationDetailPosition.getNamaPosMax() +"</td>"
  +"</tr>"
+"</table>";
        return strTableAfterSave;
    }

    /**
     * @return the namaEmployee
     */
    public String getNamaEmployee() {
        if(namaEmployee==null){
            return "";
        }
        return namaEmployee;
    }

    /**
     * @param namaEmployee the namaEmployee to set
     */
    public void setNamaEmployee(String namaEmployee) {
        this.namaEmployee = namaEmployee;
    }

    /**
     * @return the emailEmployee
     */
    public String getEmailEmployee() {
        return emailEmployee;
    }

    /**
     * @param emailEmployee the emailEmployee to set
     */
    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }

    
}
