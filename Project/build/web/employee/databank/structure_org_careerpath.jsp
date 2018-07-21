<%-- 
    Document   : structure_org_careerpath
    Created on : Dec 8, 2015, 11:44:03 AM
    Author     : Dimata 007
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
I_Dictionary dictionaryD = userSession.getUserDictionary();
%>
<tr align="left" valign="top">
    <td valign="top" width="17%"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%> </td>
    <td width="83%">
        <%   /*Vector division_value = new Vector(1, 1);
             Vector division_key = new Vector(1, 1);
             Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");
             for (int i = 0; i < listDivision.size(); i++) {
             Division division = (Division) listDivision.get(i);
             division_value.add("" + division.getOID());
             division_key.add(division.getDivision());
             }*/
            Vector division_value = new Vector(1, 1);
            Vector division_key = new Vector(1, 1);
            if (careerPath.getDivisionId() != 0) {
                oidHistoryDiv = careerPath.getDivisionId();
            }
            String whereDivision = "";
            if (!(isHRDLogin || isEdpLogin || isGeneralManager)) {
                whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "='" + emplx.getDivisionId() + "'";
                oidHistoryDiv = emplx.getDivisionId();
            } else {
                division_value.add("0");
                division_key.add("select ...");
            }
            if (whereDivision != null && whereDivision.length() > 0 && oidHistoryComp != 0) {
                whereDivision = whereDivision + " AND " + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidHistoryComp;
            } else if (oidHistoryComp != 0) {
                whereDivision = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidHistoryComp;
            }
            Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
            for (int i = 0; i < listDiv.size(); i++) {
                Division div = (Division) listDiv.get(i);
                division_key.add(div.getDivision());
                division_value.add(String.valueOf(div.getOID()));
            }
        %>
        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + (careerPath.getDivisionId() != 0 ? careerPath.getDivisionId() : oidHistoryDiv), division_value, division_key, "onChange=\"javascript:cmdUpdateDep()\"")%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DIVISION_ID)%></td>
</tr>

<!-- } -->
<tr align="left" valign="top">
    <td valign="top" width="17%">Department
    </td>
    <td width="83%">
        <%   /*Vector department_value = new Vector(1, 1);
             Vector department_key = new Vector(1, 1);

             for (int i = 0; i < listDepartment.size(); i++) {
             Department department = (Department) listDepartment.get(i);
             department_value.add("" + department.getOID());
             department_key.add(department.getDepartment());
             }

             String selDept = "" + careerPath.getDepartmentId();
             if (careerPath.getDepartmentId() == 0) {
             selDept = "" + oidHistoryDept;
             }*/
            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            if (careerPath.getDepartmentId() != 0) {
                oidHistoryDept = careerPath.getDepartmentId();
            }
            Vector listDept = new Vector();
            Position position = new Position();
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        String strWhere = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv;
                        dept_value.add("0");
                        dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");

                    } else {
                        position = new Position();
                        try {
                            position = PstPosition.fetchExc(emplx.getPositionId());
                        } catch (Exception exc) {
                        }

                        String whereClsDep = "(((hr_department.DEPARTMENT_ID = " + departmentOid + ") "
                                + "AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv + ") OR "
                                + "(hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid + "))";

                        if (position.getOID() != 0 && position.getDisabedAppDivisionScope() == 0) {
                            whereClsDep = " ( hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv + ") ";
                        }

                        Vector SectionList = new Vector();
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                            String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                            Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();

                            int grpSecIdx = -1;
                            int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();

                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;

                            int countIdxSec = 0;
                            int MAX_LOOPSec = 10;
                            int curr_loopSec = 0;

                            do { // find group department belonging to curretn user base in departmentOid
                                curr_loop++;
                                String[] grp = (String[]) depGroup.get(countIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                        grpIdx = countIdx;   // A ha .. found here                                       
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit                            

                            Vector idxSecGroup = new Vector();

                            for (int x = 0; x < maxGrpSec; x++) {

                                String[] grp = (String[]) depSecGroup.get(x);
                                for (int j = 0; j < 1; j++) {

                                    String comp = grp[j];
                                    if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                        Counter counter = new Counter();
                                        counter.setCounter(x);
                                        idxSecGroup.add(counter);
                                    }
                                }
                            }

                            for (int s = 0; s < idxSecGroup.size(); s++) {

                                Counter counter = (Counter) idxSecGroup.get(s);

                                String[] grp = (String[]) depSecGroup.get(counter.getCounter());

                                Section sec = new Section();
                                sec.setDepartmentId(Long.parseLong(grp[0]));
                                sec.setOID(Long.parseLong(grp[2]));
                                SectionList.add(sec);


                            }

                            // compose where clause
                            if (grpIdx >= 0) {
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep = whereClsDep + " OR (j.DEPARTMENT_ID = " + comp + ")";
                                }
                            }
                            whereClsDep = " (" + whereClsDep + ") AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv;
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        //dept_value.add("0");
                        //dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0, whereClsDep, "");

                        for (int idx = 0; idx < SectionList.size(); idx++) {

                            Section sect = (Section) SectionList.get(idx);

                            long sectionOid = 0;

                            for (int z = 0; z < listDept.size(); z++) {

                                Department dep = new Department();

                                dep = (Department) listDept.get(z);

                                if (sect.getDepartmentId() == dep.getOID()) {

                                    sectionOid = sect.getOID();

                                }
                            }

                            if (sectionOid != 0) {

                                Section lstSection = new Section();
                                Department lstDepartment = new Department();

                                try {
                                    lstSection = PstSection.fetchExc(sectionOid);
                                } catch (Exception e) {
                                    System.out.println("Exception " + e.toString());
                                }

                                try {
                                    lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                } catch (Exception e) {
                                    System.out.println("Exception " + e.toString());
                                }

                                listDept.add(lstDepartment);

                            }
                        }
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, (PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv), "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidHistoryDiv), "DEPARTMENT");
            }

            for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }
        %>
        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + (careerPath.getDepartmentId() != 0 ? careerPath.getDepartmentId() : oidHistoryDept), dept_value, dept_key, "onchange='javascript:cmdUpdateSection()'")%> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_DEPARTMENT_ID)%> </td>
</tr>

<tr align="left" valign="top">
    <td valign="top" width="17%"><%=dictionaryD.getWord("SECTION")%>
    </td>
    <td width="83%">


        <!-- /** Ari_20110903
            /* Memperbaiki Section {-->
        <%
            /*Vector section_value = new Vector(1, 1);
             Vector section_key = new Vector(1, 1);
             Vector listSection = PstSection.list(0, 0, "", "SECTION");
             for (int i = 0; i < listSection.size(); i++) {
             Section section = (Section) listSection.get(i);
             section_value.add("" + section.getOID());
             section_key.add(section.getSection());
             }*/
            Vector sec_value = new Vector(1, 1);
            Vector sec_key = new Vector(1, 1);
            sec_value.add("0");
            sec_key.add("select ...");
//Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidHistoryDept;
            Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
            for (int i = 0; i < listSec.size(); i++) {
                Section sec = (Section) listSec.get(i);
                sec_key.add(sec.getSection());
                sec_value.add(String.valueOf(sec.getOID()));
            }

        %>
        <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_SECTION_ID], "formElemen", null, "" + (careerPath.getSectionId() != 0 ? careerPath.getSectionId() : oidHistorySection), sec_value, sec_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_SECTION_ID)%>
        <!-- } -->
    </td>
</tr>
