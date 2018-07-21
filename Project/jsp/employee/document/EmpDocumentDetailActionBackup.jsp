<%-- 
    Document   : Doc Master Action
    Created on : Sep 12, 2015, 3:56:51 PM
    Author     : Priska Suryana
--%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PstSalaryLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryLevel"%>
<%@page import="com.dimata.qdep.db.DBException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="org.apache.poi.ss.formula.functions.Hlookup"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../../main/checkuser.jsp" %>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    String msgStringAll = "";
    long oidEmpDoc = FRMQueryString.requestLong(request, "oidEmpDoc");
    long oidEmpDocAction = FRMQueryString.requestLong(request, "oidEmpDocAction");
    int saveType = FRMQueryString.requestInt(request, "saveType");
    long oidDocAction = FRMQueryString.requestLong(request, "oidDocAction");


    long oidCustomFieldSkNumber = 504404604604804494l;
    long oidCustomFieldSkDate = 504404604604883215l;
    long oidCustomFieldEmpNum = 504404615079209747l;

    EmpDoc empDocX = new EmpDoc();
    try {
        empDocX = PstEmpDoc.fetchExc(oidEmpDoc);
    } catch (Exception e) {
    }


    CtrlEmpDocAction ctrlEmpDocAction = new CtrlEmpDocAction(request);
    ControlLine ctrLine = new ControlLine();
    /*switch statement */

    /* end switch*/
    FrmEmpDocAction frmEmpDocAction = ctrlEmpDocAction.getForm();
    EmpDocAction empDocAction = ctrlEmpDocAction.getdEmpDocAction();
    msgString = ctrlEmpDocAction.getMessage();

    //untuk mengambil list param
    DocMasterAction docMasterAction = new DocMasterAction();
    try {
        docMasterAction = PstDocMasterAction.fetchExc(oidDocAction);
    } catch (Exception e) {
    }

    Hashtable hDocMasterAction = new Hashtable();
    if (docMasterAction != null) {
        String whereClause = PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_ID] + " = " + docMasterAction.getOID();
        hDocMasterAction = PstDocMasterActionParam.hList(0, 0, whereClause, "");


    }

    //untuk employee Mutation
    DocMasterActionParam docMasterActionParam1 = new DocMasterActionParam();
    Vector listEmp = new Vector();

    //untuk mutasi
    String newCompanyS = "";
    String newDivisionS = "";
    String newDepartmentS = "";
    String newEmpCatS = "";
    String newLevelS = "";
    String newPositionS = "";
    String newSectionS = "";
    String workFromS = "";

    //untuk upsalary
    String newSalaryLevel = "";



    boolean valid = true;

    if (docMasterAction.getActionName().equals(DocMasterActionClass.actionKey[0])) {
        docMasterActionParam1 = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][0]);


        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + docMasterActionParam1.getObjectName() + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
        listEmp = PstEmpDocList.list(0, 0, whereC, ""); //ini daftar orangnya 

        CareerPath careerPathTemp = new CareerPath();

        DocMasterActionParam docMasterActionParamForCompany = new DocMasterActionParam();
        docMasterActionParamForCompany = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][1]);
        String newObjectNameForCompany = (String) docMasterActionParamForCompany.getObjectName();
        String newValueForCompany = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForCompany, oidEmpDoc);
        String getClassNameForCompany = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForCompany, oidEmpDoc);
        if (getClassNameForCompany.equals("COMPANY")) {
            long newValuetoLong = Long.parseLong(newValueForCompany);
            careerPathTemp.setCompanyId(newValuetoLong);
            try {
                Company company = PstCompany.fetchExc(newValuetoLong);
                newCompanyS = company.getCompany();
                careerPathTemp.setCompany(company.getCompany());
            } catch (Exception e) {
            }

        }



        //cari tahu ini untuk perubahan apa Divisi
        DocMasterActionParam docMasterActionParamForDivision = new DocMasterActionParam();
        docMasterActionParamForDivision = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][2]);
        String newObjectNameForDivision = (String) docMasterActionParamForDivision.getObjectName();
        String newValueForDivision = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForDivision, oidEmpDoc);
        String getClassNameForDivision = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForDivision, oidEmpDoc);
        if (getClassNameForDivision.equals("DIVISION")) {
            long newValuetoLong = Long.parseLong(newValueForDivision);
            careerPathTemp.setDivisionId(newValuetoLong);
            try {
                Division division = PstDivision.fetchExc(newValuetoLong);
                newDivisionS = division.getDivision();
                careerPathTemp.setDivision(division.getDivision());
            } catch (Exception e) {
            }

        }


        //cari tahu ini untuk perubahan apa
        DocMasterActionParam docMasterActionParamForDepartment = new DocMasterActionParam();
        docMasterActionParamForDepartment = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][3]);
        String newObjectNameForDepartment = (String) docMasterActionParamForDepartment.getObjectName();
        String newValueForDepartment = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForDepartment, oidEmpDoc);
        String getClassNameForDepartment = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForDepartment, oidEmpDoc);
        if (getClassNameForDepartment.equals("DEPARTMENT")) {
            long newValuetoLong = Long.parseLong(newValueForDepartment);
            careerPathTemp.setDepartmentId(newValuetoLong);
            try {
                Department department = PstDepartment.fetchExc(newValuetoLong);
                newDepartmentS = department.getDepartment();
                careerPathTemp.setDepartment(department.getDepartment());
            } catch (Exception e) {
            }

        }

        //cari tahu ini untuk perubahan apa 
        DocMasterActionParam docMasterActionParamForEmpCat = new DocMasterActionParam();
        docMasterActionParamForEmpCat = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][4]);
        String newObjectNameForEmpCat = (String) docMasterActionParamForEmpCat.getObjectName();
        String newValueForEmpCat = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForEmpCat, oidEmpDoc);
        String getClassNameForEmpCat = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForEmpCat, oidEmpDoc);
        if (getClassNameForEmpCat.equals("EMPCAT")) {
            long newValuetoLong = Long.parseLong(newValueForEmpCat);
            careerPathTemp.setEmpCategoryId(newValuetoLong);
            try {
                EmpCategory empCategory = PstEmpCategory.fetchExc(newValuetoLong);
                newEmpCatS = empCategory.getEmpCategory();
                careerPathTemp.setEmpCategoryId(newValuetoLong);
            } catch (Exception e) {
            }

        }

        //cari tahu ini untuk perubahan apa 
        DocMasterActionParam docMasterActionParamForLevel = new DocMasterActionParam();
        docMasterActionParamForLevel = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][5]);
        String newObjectNameForLevel = (String) docMasterActionParamForLevel.getObjectName();
        String newValueForLevel = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForLevel, oidEmpDoc);
        String getClassNameForLevel = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForLevel, oidEmpDoc);
        if (getClassNameForLevel.equals("LEVEL")) {
            long newValuetoLong = Long.parseLong(newValueForLevel);
            careerPathTemp.setLevelId(newValuetoLong);
            try {
                Level level = PstLevel.fetchExc(newValuetoLong);
                newLevelS = level.getLevel();
                careerPathTemp.setLevel(level.getLevel());
            } catch (Exception e) {
            }

        }


        //cari tahu ini untuk perubahan apa
        DocMasterActionParam docMasterActionParamForPosition = new DocMasterActionParam();
        docMasterActionParamForPosition = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][6]);
        String newObjectNameForPosition = (String) docMasterActionParamForPosition.getObjectName();
        String newValueForPosition = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForPosition, oidEmpDoc);
        String getClassNameForPosition = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForPosition, oidEmpDoc);
        if (getClassNameForPosition.equals("POSITION")) {
            long newValuetoLong = Long.parseLong(newValueForPosition);
            careerPathTemp.setPositionId(newValuetoLong);
            try {
                Position position = PstPosition.fetchExc(newValuetoLong);
                newPositionS = position.getPosition();
                careerPathTemp.setPosition(position.getPosition());
            } catch (Exception e) {
            }

        }

        DocMasterActionParam docMasterActionParamForSection = new DocMasterActionParam();
        docMasterActionParamForSection = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][7]);
        String newObjectNameForSection = (String) docMasterActionParamForSection.getObjectName();
        String newValueForSection = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForSection, oidEmpDoc);
        String getClassNameForSection = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForSection, oidEmpDoc);
        if (getClassNameForSection.equals("SECTION")) {
            long newValuetoLong = Long.parseLong(newValueForSection);
            careerPathTemp.setSectionId(newValuetoLong);
            try {
                Section section = PstSection.fetchExc(newValuetoLong);
                newSectionS = section.getSection();
                careerPathTemp.setSection(section.getSection());
            } catch (Exception e) {
            }
        }

        DocMasterActionParam docMasterActionParamForWorkTo = new DocMasterActionParam();
        docMasterActionParamForWorkTo = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][8]);
        String newObjectNameForWorkTo = (String) docMasterActionParamForWorkTo.getObjectName();
        String newValueForWorkTo = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForWorkTo, oidEmpDoc);
        //String getClassNameForWorkTo = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForWorkTo, oidEmpDoc);
        Date dateF = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateF = formatter.parse(newValueForWorkTo);
            careerPathTemp.setWorkFrom(dateF);
            workFromS = "" + dateF;
        } catch (Exception e) {
        }




        if (iCommand == Command.SAVE) {
            for (int list = 0; list < listEmp.size(); list++) {

                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());

                CareerPath careerPath = new CareerPath();

                careerPath.setCompanyId(employeeFetch.getCompanyId());
                try {
                    Company company = PstCompany.fetchExc(employeeFetch.getCompanyId());
                    careerPath.setCompany(company.getCompany());
                } catch (Exception e) {
                }

                careerPath.setDivisionId(employeeFetch.getDivisionId());
                try {
                    Division division = PstDivision.fetchExc(employeeFetch.getDivisionId());
                    careerPath.setDivision(division.getDivision());
                } catch (Exception e) {
                }

                careerPath.setDepartmentId(employeeFetch.getDepartmentId());
                try {
                    Department department = PstDepartment.fetchExc(employeeFetch.getDepartmentId());
                    careerPath.setDepartment(department.getDepartment());
                } catch (Exception e) {
                }

                careerPath.setSectionId(employeeFetch.getSectionId());
                try {
                    Department department = PstDepartment.fetchExc(employeeFetch.getSectionId());
                    careerPath.setDepartment(department.getDepartment());
                } catch (Exception e) {
                }

                careerPath.setEmpCategoryId(employeeFetch.getEmpCategoryId());
                try {
                    EmpCategory empCategory = PstEmpCategory.fetchExc(employeeFetch.getEmpCategoryId());
                    careerPath.setEmpCategory(empCategory.getEmpCategory());
                } catch (Exception e) {
                }

                careerPath.setPositionId(employeeFetch.getPositionId());
                try {
                    Position position = PstPosition.fetchExc(employeeFetch.getPositionId());
                    careerPath.setPosition(position.getPosition());
                } catch (Exception e) {
                }

                careerPath.setLevelId(employeeFetch.getLevelId());
                try {
                    Level level = PstLevel.fetchExc(employeeFetch.getLevelId());
                    careerPath.setLevel(level.getLevel());
                } catch (Exception e) {
                }


//mencari work from
                String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + employeeFetch.getOID();
                String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
                Vector objectClass = PstCareerPath.list(0, 0, whereClause, orderClause);
                CareerPath careerPath1 = new CareerPath();
                if (objectClass.size() > 0) {
                    careerPath1 = (CareerPath) objectClass.get(objectClass.size() - 1);
                    Date fromWork = careerPath1.getWorkTo();
                    fromWork.setDate(fromWork.getDate() + 1);
                    String str_dt_WorkFrom = "";
                    try {
                        Date dt_WorkFrom = fromWork;
                        if (dt_WorkFrom == null) {
                            dt_WorkFrom = new Date();
                        }
                        careerPath.setWorkFrom(dt_WorkFrom);
                        // str_dt_WorkFrom = Formater.formatDate(dt_WorkFrom, "dd MMMM yyyy");
                    } catch (Exception e) {
                        //str_dt_WorkFrom = "";
                    }



                } else {
                    careerPath.setWorkFrom(employeeFetch.getCommencingDate());
                    //rowx.add(""+employee.getCommencingDate());
                }

                careerPath.setEmployeeId(employeeFetch.getOID());
                if (careerPath.getWorkFrom() != null) {
                    Date careerDateWorkTo = careerPath.getWorkFrom();
                    careerDateWorkTo.setDate(careerDateWorkTo.getDate() - 1);
                    careerPath.setWorkTo(careerDateWorkTo);
                } else {
                    careerPath.setWorkTo(new Date());
                }

                careerPath.setEmployeeId(employeeFetch.getOID());


                long oidCareerPath = 0;

                Vector data = new Vector();
                int cheked = 0;
                try {
                    cheked = FRMQueryString.requestInt(request, "userSelect" + employeeFetch.getOID());
                } catch (Exception e) {
                }
                if ((employeeFetch.getOID() != 0) && (cheked > 0)) {
                    long oidEmp = employeeFetch.getOID();
                    data = PstCareerPath.dateCareerPath(oidEmp);

                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            CareerPath care = (CareerPath) data.get(i);
                            if (careerPath != null && care != null && care.getWorkFrom() != null && care.getWorkTo() != null && careerPath.getWorkFrom() != null && careerPath.getWorkTo() != null) {
                                java.util.Date newStartDate = care.getWorkFrom();
                                java.util.Date newEndDate = care.getWorkTo();
                                java.util.Date startDate = careerPath.getWorkFrom();
                                java.util.Date endDate = careerPath.getWorkTo();
                                String sTanggalTo = Formater.formatDate(newStartDate, "dd-MM-yyyy");
                                String sTanggalFrom = Formater.formatDate(newEndDate, "dd-MM-yyyy");
                                String Error = "" + sTanggalTo + " TO " + sTanggalFrom;
                                if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.after(careerPath.getWorkFrom()) && newStartDate.before(careerPath.getWorkTo())) {
                                    valid = false;
                                    msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                    valid = false;
                                    msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                    valid = false;
                                    msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                    valid = false;
                                    msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                    valid = false;
                                    msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                }


                            }
                        }
                    }

                    if (valid) {
                        try {
                            long oid = PstCareerPath.insertExc(careerPath);
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        } catch (DBException dbexc) {
                        }
                    }
                    employeeFetch.setCompanyId(careerPathTemp.getCompanyId());
                    employeeFetch.setDivisionId(careerPathTemp.getDivisionId());
                    employeeFetch.setDepartmentId(careerPathTemp.getDepartmentId());
                    employeeFetch.setSectionId(careerPathTemp.getSectionId());
                    employeeFetch.setEmpCategoryId(careerPathTemp.getEmpCategoryId());
                    employeeFetch.setLevelId(careerPathTemp.getLevelId());
                    employeeFetch.setPositionId(careerPathTemp.getPositionId());


                    if (valid) {
                        try {
                            long oid = PstEmployee.updateExc(employeeFetch);
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        } catch (DBException dbexc) {
                        }
                    }
                }


            }
        }
    } else if (docMasterAction.getActionName().equals(DocMasterActionClass.actionKey[1])) {

        docMasterActionParam1 = (DocMasterActionParam) hDocMasterAction.get("Employee to Update Salary Level");


        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + docMasterActionParam1.getObjectName() + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
        listEmp = PstEmpDocList.list(0, 0, whereC, ""); //ini daftar orangnya                      

        long newsalevel = 0;
        String levelCode = "";
        //cari tahu ini untuk perubahan apa
        DocMasterActionParam docMasterActionParamForSalaryLevel = new DocMasterActionParam();
        docMasterActionParamForSalaryLevel = (DocMasterActionParam) hDocMasterAction.get("New Salary Level");
        String newObjectNameForSalaryLevel = (String) docMasterActionParamForSalaryLevel.getObjectName();
        String newValueForSalaryLevel = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForSalaryLevel, oidEmpDoc);
        String getClassNameForSalaryLevel = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForSalaryLevel, oidEmpDoc);
        if (getClassNameForSalaryLevel.equals("SALARYLEVEL")) {
            long newValuetoLong = Long.parseLong(newValueForSalaryLevel);
            newsalevel = newValuetoLong;
            try {
                SalaryLevel salaryLevel = PstSalaryLevel.fetchExc(newValuetoLong);
                newSalaryLevel = salaryLevel.getLevelName();
                levelCode = salaryLevel.getLevelCode();
            } catch (Exception e) {
            }

        }

        //new start date salary level
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DocMasterActionParam docMasterActionParamForStartDateSalaryLevel = new DocMasterActionParam();
        docMasterActionParamForStartDateSalaryLevel = (DocMasterActionParam) hDocMasterAction.get("Start Date Salary Level");
        String newObjectNameForStartDateSalaryLevel = (String) docMasterActionParamForStartDateSalaryLevel.getObjectName();
        String newValueForStartDateSalaryLevel = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForStartDateSalaryLevel, oidEmpDoc);
        Date dateStartDateSalaryLevel = new Date();

        try {
            dateStartDateSalaryLevel = formatter.parse(newValueForStartDateSalaryLevel);
        } catch (Exception e) {
        }

        //end date salary level
        DocMasterActionParam docMasterActionParamForEndDateSalaryLevel = new DocMasterActionParam();
        docMasterActionParamForEndDateSalaryLevel = (DocMasterActionParam) hDocMasterAction.get("Start Date Salary Level");
        String newObjectNameForEndDateSalaryLevel = (String) docMasterActionParamForEndDateSalaryLevel.getObjectName();
        String newValueForEndDateSalaryLevel = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForEndDateSalaryLevel, oidEmpDoc);
        Date dateEndDateSalaryLevel = new Date();

        try {
            dateEndDateSalaryLevel = formatter.parse(newValueForEndDateSalaryLevel);
        } catch (Exception e) {
        }


        if (iCommand == Command.SAVE) {

            for (int list = 0; list < listEmp.size(); list++) {

                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                String whereLevel1 = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] + " = '" + (employeeFetch.getOID()) + "'";
                String orderDate1 = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE] + " DESC ";
                Vector listEmpLevelByEmployeeId1 = PstPayEmpLevel.list(0, 0, whereLevel1, orderDate1);
                if (listEmpLevelByEmployeeId1.size() > 0) {
                    try {
                        payEmpLevel = (PayEmpLevel) listEmpLevelByEmployeeId1.get(0);
                    } catch (Exception e) {
                    }
                }


                //out.println("payEmpLevelId  "+payEmpLevelId);
                // cari apakah id employee sudah ada di tabel atau belum untuk melakukan update status
                PayEmpLevel objPayEmp = new PayEmpLevel();
                String whereLevel = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] + " = '" + (employeeFetch.getOID()) + "'";
                Vector listEmpLevelByEmployeeId = PstPayEmpLevel.list(0, 0, whereLevel, "");
                if (listEmpLevelByEmployeeId.size() > 0) {
                    objPayEmp = (PayEmpLevel) listEmpLevelByEmployeeId.get(0);
                    Date obj_start_date = objPayEmp.getStartDate();
                    Date date = new Date();
                    String s_date = Formater.formatDate(date, "yyyy-MM-dd");
                    Date dateInput = Formater.formatDate(s_date, "yyyy-MM-dd");
                    long duration = DateCalc.timeDifference(obj_start_date, dateInput);
                    if (duration > 0) {
                        PstPayEmpLevel.UpdateStatus(employeeFetch.getOID());
                    }
                }
                //PstPayEmpLevel.setupEmployee(Long.parseLong(employee_id[i]), s_salary_level,s_date, Long.parseLong(bank_name[i]),s_bank_acc,s_pos_for_tax,Integer.parseInt(period_begin[i]),Integer.parseInt(period_end[i]),s_com_status,s_prev_income,Integer.parseInt(prev_tax_paid[i] ));
                //PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setEmployeeId(employeeFetch.getOID());
                payEmpLevel.setLevelCode(levelCode);
                payEmpLevel.setStartDate(dateStartDateSalaryLevel);
                payEmpLevel.setEndDate(dateEndDateSalaryLevel);
                //payEmpLevel.setBankId(Long.parseLong(bank_name[i]));
                //payEmpLevel.setBankAccNr(s_bank_acc);
                //payEmpLevel.setPosForTax(s_pos_for_tax);
                //payEmpLevel.setPayPerBegin(Integer.parseInt(period_begin[i]));
                //payEmpLevel.setPayPerEnd(Integer.parseInt(period_end[i]));
                //payEmpLevel.setCommencingSt(Integer.parseInt(s_com_status));
                //payEmpLevel.setPrevIncome(Double.parseDouble(s_prev_income));
                //payEmpLevel.setPrevTaxPaid(Integer.parseInt(prev_tax_paid[i]));
                payEmpLevel.setStatusData(PstPayEmpLevel.CURRENT);
                //payEmpLevel.setMealAllowance(Integer.parseInt(meal_allowance[i]));
                //payEmpLevel.setOvtIdxType(Integer.parseInt(ovt_idx_type[i]));
                try {
                    PstPayEmpLevel.insertExc(payEmpLevel);
                } catch (Exception e) {
                }

            }
        }

    } else if (docMasterAction.getActionName().equals(DocMasterActionClass.actionKey[2])) {
        docMasterActionParam1 = (DocMasterActionParam) hDocMasterAction.get("Employee to Update Databank");


        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + docMasterActionParam1.getObjectName() + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
        listEmp = PstEmpDocList.list(0, 0, whereC, ""); //ini daftar orangnya 

        Employee employee = new Employee();

        DocMasterActionParam docMasterActionParamForCompany = new DocMasterActionParam();
        docMasterActionParamForCompany = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][1]);
        String newObjectNameForCompany = (String) docMasterActionParamForCompany.getObjectName();
        String newValueForCompany = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForCompany, oidEmpDoc);
        String getClassNameForCompany = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForCompany, oidEmpDoc);
        if (getClassNameForCompany.equals("COMPANY")) {
            long newValuetoLong = Long.parseLong(newValueForCompany);
            employee.setCompanyId(newValuetoLong);

        }



        //cari tahu ini untuk perubahan apa Divisi
        DocMasterActionParam docMasterActionParamForDivision = new DocMasterActionParam();
        docMasterActionParamForDivision = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][2]);
        String newObjectNameForDivision = (String) docMasterActionParamForDivision.getObjectName();
        String newValueForDivision = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForDivision, oidEmpDoc);
        String getClassNameForDivision = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForDivision, oidEmpDoc);
        if (getClassNameForDivision.equals("DIVISION")) {
            long newValuetoLong = Long.parseLong(newValueForDivision);
            employee.setDivisionId(newValuetoLong);

        }


        //cari tahu ini untuk perubahan apa
        DocMasterActionParam docMasterActionParamForDepartment = new DocMasterActionParam();
        docMasterActionParamForDepartment = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][3]);
        String newObjectNameForDepartment = (String) docMasterActionParamForDepartment.getObjectName();
        String newValueForDepartment = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForDepartment, oidEmpDoc);
        String getClassNameForDepartment = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForDepartment, oidEmpDoc);
        if (getClassNameForDepartment.equals("DEPARTMENT")) {
            long newValuetoLong = Long.parseLong(newValueForDepartment);
            employee.setDepartmentId(newValuetoLong);

        }

        //cari tahu ini untuk perubahan apa 
        DocMasterActionParam docMasterActionParamForEmpCat = new DocMasterActionParam();
        docMasterActionParamForEmpCat = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][4]);
        String newObjectNameForEmpCat = (String) docMasterActionParamForEmpCat.getObjectName();
        String newValueForEmpCat = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForEmpCat, oidEmpDoc);
        String getClassNameForEmpCat = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForEmpCat, oidEmpDoc);
        if (getClassNameForEmpCat.equals("EMPCAT")) {
            long newValuetoLong = Long.parseLong(newValueForEmpCat);
            employee.setEmpCategoryId(newValuetoLong);

        }

        //cari tahu ini untuk perubahan apa 
        DocMasterActionParam docMasterActionParamForLevel = new DocMasterActionParam();
        docMasterActionParamForLevel = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][5]);
        String newObjectNameForLevel = (String) docMasterActionParamForLevel.getObjectName();
        String newValueForLevel = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForLevel, oidEmpDoc);
        String getClassNameForLevel = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForLevel, oidEmpDoc);
        if (getClassNameForLevel.equals("LEVEL")) {
            long newValuetoLong = Long.parseLong(newValueForLevel);
            employee.setLevelId(newValuetoLong);

        }


        //cari tahu ini untuk perubahan apa
        DocMasterActionParam docMasterActionParamForPosition = new DocMasterActionParam();
        docMasterActionParamForPosition = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][6]);
        String newObjectNameForPosition = (String) docMasterActionParamForPosition.getObjectName();
        String newValueForPosition = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForPosition, oidEmpDoc);
        String getClassNameForPosition = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForPosition, oidEmpDoc);
        if (getClassNameForPosition.equals("POSITION")) {
            long newValuetoLong = Long.parseLong(newValueForPosition);
            employee.setPositionId(newValuetoLong);

        }

        DocMasterActionParam docMasterActionParamForSection = new DocMasterActionParam();
        docMasterActionParamForSection = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][7]);
        String newObjectNameForSection = (String) docMasterActionParamForSection.getObjectName();
        String newValueForSection = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForSection, oidEmpDoc);
        String getClassNameForSection = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForSection, oidEmpDoc);
        if (getClassNameForSection.equals("SECTION")) {
            long newValuetoLong = Long.parseLong(newValueForSection);
            employee.setSectionId(newValuetoLong);

        }

        DocMasterActionParam docMasterActionParamForWorkTo = new DocMasterActionParam();
        docMasterActionParamForWorkTo = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][8]);
        String newObjectNameForWorkTo = (String) docMasterActionParamForWorkTo.getObjectName();
        String newValueForWorkTo = PstEmpDocField.getvalueByObjectnameEmpDocId(newObjectNameForWorkTo, oidEmpDoc);
        //String getClassNameForWorkTo = PstEmpDocField.getClassNameByObjectnameEmpDocId(newObjectNameForWorkTo, oidEmpDoc);
        Date dateF = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateF = formatter.parse(newValueForWorkTo);
            employee.setResignedDate(dateF);
        } catch (Exception e) {
        }

    } else if (docMasterAction.getActionName().equals(DocMasterActionClass.actionKey[3])) {//
        docMasterActionParam1 = (DocMasterActionParam) hDocMasterAction.get(DocMasterActionClass.actionListParameterKey[0][0]);


        String whereC = " " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_OBJECT_NAME] + " = \"" + docMasterActionParam1.getObjectName() + "\" AND " + PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = \"" + oidEmpDoc + "\"";
        listEmp = PstEmpDocList.list(0, 0, whereC, ""); //ini daftar orangnya 






        if (iCommand == Command.SAVE) {

            for (int list = 0; list < listEmp.size(); list++) {//

                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                EmpDocListMutation empDocListMutation = new EmpDocListMutation();
                try {
                    empDocListMutation = PstEmpDocListMutation.getNewEmpDocListMutation(oidEmpDoc, employeeFetch.getOID(), docMasterActionParam1.getObjectName());
                } catch (Exception e) {
                }
                boolean statusExe = true;

                CareerPath careerPath = new CareerPath();
                if ((empDocListMutation.getHistoryType() == 0) || (empDocListMutation.getHistoryType() == 1)) {
                    careerPath.setCompanyId(employeeFetch.getCompanyId());
                    try {
                        Company company = PstCompany.fetchExc(employeeFetch.getCompanyId());
                        careerPath.setCompany(company.getCompany());
                    } catch (Exception e) {
                    }

                    careerPath.setDivisionId(employeeFetch.getDivisionId());
                    try {
                        Division division = PstDivision.fetchExc(employeeFetch.getDivisionId());
                        careerPath.setDivision(division.getDivision());
                    } catch (Exception e) {
                    }

                    careerPath.setDepartmentId(employeeFetch.getDepartmentId());
                    try {
                        Department department = PstDepartment.fetchExc(employeeFetch.getDepartmentId());
                        careerPath.setDepartment(department.getDepartment());
                    } catch (Exception e) {
                    }

                    careerPath.setSectionId(employeeFetch.getSectionId());
                    try {
                        Department department = PstDepartment.fetchExc(employeeFetch.getSectionId());
                        careerPath.setDepartment(department.getDepartment());
                    } catch (Exception e) {
                    }

                    careerPath.setEmpCategoryId(employeeFetch.getEmpCategoryId());
                    try {
                        EmpCategory empCategory = PstEmpCategory.fetchExc(employeeFetch.getEmpCategoryId());
                        careerPath.setEmpCategory(empCategory.getEmpCategory());
                    } catch (Exception e) {
                    }

                    careerPath.setPositionId(employeeFetch.getPositionId());
                    try {
                        Position position = PstPosition.fetchExc(employeeFetch.getPositionId());
                        careerPath.setPosition(position.getPosition());
                    } catch (Exception e) {
                    }

                    careerPath.setLevelId(employeeFetch.getLevelId());
                    try {
                        Level level = PstLevel.fetchExc(employeeFetch.getLevelId());
                        careerPath.setLevel(level.getLevel());
                    } catch (Exception e) {
                    }




                    //mencari work from
                    String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + employeeFetch.getOID() + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " )";
                    whereClause += " AND "+PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP] + " != "+PstCareerPath.RIWAYAT_GRADE;
                    String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]+" DESC";
                    Vector objectClass = PstCareerPath.list(0, 1, whereClause, orderClause);
                    CareerPath careerPath1 = new CareerPath();
                    if (objectClass != null && objectClass.size() > 0) {
                        careerPath1 = (CareerPath) objectClass.get(0);
                        Date fromWork = careerPath1.getWorkTo();
                        fromWork.setDate(fromWork.getDate() + 1);
                        try {
                            Date dt_WorkFrom = fromWork;
                            if (dt_WorkFrom == null) {
                                dt_WorkFrom = new Date();
                            }
                            careerPath.setWorkFrom(dt_WorkFrom);
                            // str_dt_WorkFrom = Formater.formatDate(dt_WorkFrom, "dd MMMM yyyy");
                        } catch (Exception e) {
                            //str_dt_WorkFrom = "";
                        }


                    } else {
                        careerPath.setWorkFrom(employeeFetch.getCommencingDate());
                        //rowx.add(""+employee.getCommencingDate());
                    }

                    careerPath.setEmployeeId(employeeFetch.getOID());
                    if (empDocListMutation.getWorkFrom() != null) {
                        Date careerDateWorkTo = empDocListMutation.getWorkFrom();
                        careerDateWorkTo.setDate(careerDateWorkTo.getDate() - 1);
                        careerPath.setWorkTo(careerDateWorkTo);
                    } else {
                        careerPath.setWorkTo(new Date());
                    }



                    /* 
                    code sebelumnya : careerPath.setHistoryGroup(employeeFetch.getHistoryGroup());
                    * diubah oleh Hendra, karena permintaan pak Adi BPD 
                    */
                    careerPath.setHistoryGroup(empDocListMutation.getHistoryGroup());
                    careerPath.setHistoryType(employeeFetch.getHistoryType());

                    //mencari work from
                    careerPath.setGradeLevelId(employeeFetch.getGradeLevelId());
                    //EmpDoc empDocXXX = new EmpDoc();
                    try {
                        //empDocXXX = PstEmpDoc.fetchExc(employeeFetch.getEmpDocId());
                        String SkNumber = PstEmpCustomField.getNoSK(employeeFetch.getOID(), empDocX.getDoc_number(), oidCustomFieldSkNumber);
                        careerPath.setNomorSk(SkNumber);
                        Date SkTanggal = PstEmpCustomField.getTglSk(employeeFetch.getOID(), empDocX.getDoc_number(), oidCustomFieldSkDate);
                        careerPath.setTanggalSk(SkTanggal);
                    } catch (Exception ex) {
                    }

                    careerPath.setEmpDocId(employeeFetch.getEmpDocId());
                } else {


                    careerPath.setCompanyId(empDocListMutation.getCompanyId());
                    try {
                        Company company = PstCompany.fetchExc(empDocListMutation.getCompanyId());
                        careerPath.setCompany(company.getCompany());
                    } catch (Exception e) {
                    }

                    careerPath.setDivisionId(empDocListMutation.getDivisionId());
                    try {
                        Division division = PstDivision.fetchExc(empDocListMutation.getDivisionId());
                        careerPath.setDivision(division.getDivision());
                    } catch (Exception e) {
                    }

                    careerPath.setDepartmentId(empDocListMutation.getDepartmentId());
                    try {
                        Department department = PstDepartment.fetchExc(empDocListMutation.getDepartmentId());
                        careerPath.setDepartment(department.getDepartment());
                    } catch (Exception e) {
                    }

                    careerPath.setSectionId(empDocListMutation.getSectionId());
                    try {
                        Department department = PstDepartment.fetchExc(empDocListMutation.getSectionId());
                        careerPath.setDepartment(department.getDepartment());
                    } catch (Exception e) {
                    }

                    careerPath.setEmpCategoryId(empDocListMutation.getEmpDocId());
                    try {
                        EmpCategory empCategory = PstEmpCategory.fetchExc(empDocListMutation.getEmpCatId());
                        careerPath.setEmpCategory(empCategory.getEmpCategory());
                    } catch (Exception e) {
                    }

                    careerPath.setPositionId(empDocListMutation.getPositionId());
                    try {
                        Position position = PstPosition.fetchExc(empDocListMutation.getPositionId());
                        careerPath.setPosition(position.getPosition());
                    } catch (Exception e) {
                    }

                    careerPath.setLevelId(empDocListMutation.getLevelId());
                    try {
                        Level level = PstLevel.fetchExc(empDocListMutation.getLevelId());
                        careerPath.setLevel(level.getLevel());
                    } catch (Exception e) {
                    }




                    //mencari work from
                    careerPath.setGradeLevelId(empDocListMutation.getGradeLevelId());
                    careerPath.setHistoryGroup(empDocListMutation.getHistoryGroup());
                    careerPath.setHistoryType(empDocListMutation.getHistoryType());
                    careerPath.setWorkFrom(empDocListMutation.getWorkFrom());
                    careerPath.setWorkTo(empDocListMutation.getWorkTo());
                    careerPath.setEmployeeId(employeeFetch.getOID());
                    careerPath.setEmpDocId(empDocX.getOID());

                    careerPath.setNomorSk(empDocX.getDoc_number());
                    careerPath.setTanggalSk(empDocX.getRequest_date());


                }



                long oidCareerPath = 0;

                Vector data = new Vector();
                int cheked = 0;
                try {
                    //  cheked  = FRMQueryString.requestInt(request,"userSelect"+employeeFetch.getOID());
                } catch (Exception e) {
                }
                // if((employeeFetch.getOID()!=0) && (cheked > 0) ){
                if ((employeeFetch.getOID() != 0)) {
                    long oidEmp = employeeFetch.getOID();
                    data = PstCareerPath.dateCareerPath(oidEmp);
                    /*
                     if(data !=null && data.size() >0){     
                     for(int i=0; i<data.size();i++){
                     CareerPath care = (CareerPath)data.get(i);
                     if(careerPath!=null && care!=null && care.getWorkFrom()!=null && care.getWorkTo()!=null && careerPath.getWorkFrom()!=null && careerPath.getWorkTo()!=null){
                     java.util.Date newStartDate = care.getWorkFrom();
                     java.util.Date newEndDate = care.getWorkTo();
                     java.util.Date startDate = careerPath.getWorkFrom();
                     java.util.Date endDate = careerPath.getWorkTo();
                     String sTanggalTo =Formater.formatDate(newStartDate, "dd-MM-yyyy");
                     String sTanggalFrom =Formater.formatDate(newEndDate, "dd-MM-yyyy");
                     String Error=""+sTanggalTo +" TO " + sTanggalFrom;
                     if ((oidCareerPath!=0 ? (care.getOID() == oidCareerPath?false:true) :  care.getOID() != oidCareerPath) &&newStartDate.after( careerPath.getWorkFrom()) && newStartDate.before(careerPath.getWorkTo())) {
                     statusExe = false; 
                                                                                                                               
                     msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                     } else if ((oidCareerPath!=0 ? (care.getOID() == oidCareerPath?false:true) :  care.getOID() != oidCareerPath) &&newEndDate.after(startDate) && newEndDate.before(endDate)) {
                     statusExe = false;  
                     msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                     } else if ((oidCareerPath!=0 ? (care.getOID() == oidCareerPath?false:true) :  care.getOID() != oidCareerPath) &&startDate.after(newStartDate) && startDate.before(newEndDate)) {
                     statusExe = false;  
                     msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                     } else if ( (oidCareerPath!=0 ? (care.getOID() == oidCareerPath?false:true) :  care.getOID() != oidCareerPath) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                     statusExe = false;  
                     msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                     } else if ( (oidCareerPath!=0 ? (care.getOID() == oidCareerPath?false:true) :  care.getOID() != oidCareerPath) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                     statusExe = false;  
                     msgString = "Tanggal yang diinputkan sudah ada" + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                     }


                     }
                     }
                     }
                     */

                    if (statusExe) {
                        try {
                            long oid = PstCareerPath.insertExc(careerPath);
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        } catch (DBException dbexc) {
                        }
                    }
                    employeeFetch.setCompanyId(empDocListMutation.getCompanyId());
                    employeeFetch.setDivisionId(empDocListMutation.getDivisionId());
                    employeeFetch.setDepartmentId(empDocListMutation.getDepartmentId());
                    employeeFetch.setSectionId(empDocListMutation.getSectionId());
                    employeeFetch.setEmpCategoryId(empDocListMutation.getEmpCatId());
                    employeeFetch.setLevelId(empDocListMutation.getLevelId());
                    employeeFetch.setGradeLevelId(empDocListMutation.getGradeLevelId());
                    employeeFetch.setPositionId(empDocListMutation.getPositionId());


                    employeeFetch.setHistoryGroup(empDocListMutation.getHistoryGroup());
                    employeeFetch.setHistoryType(empDocListMutation.getHistoryType());
                    employeeFetch.setEmpDocId(empDocListMutation.getEmpDocId());
                    if (empDocListMutation.getTipeDoc() == PstEmpDocListMutation.PENGANGKATAN) {
                        employeeFetch.setStatusPensiunProgram(PstEmployee.STATUS_PENSIUN_PROGRAM_INCLUDE);

                        Date mdl = (Date) empDocListMutation.getWorkFrom().clone();
                        mdl.setDate(empDocListMutation.getWorkFrom().getDate() + 1);
                        empDocListMutation.setWorkFrom(mdl);
                        employeeFetch.setStartDatePensiun(empDocListMutation.getWorkFrom());
                        employeeFetch.setEnd_contract(null);

                        //update databank
                        long oidCustomEmpNum = PstEmpCustomField.updateDATA_TEXT(employeeFetch.getOID(), employeeFetch.getEmployeeNum(), oidCustomFieldEmpNum);
                        employeeFetch.setEmployeeNum(empDocListMutation.getEmpNum());
                    }
                    if (empDocListMutation.getTipeDoc() == PstEmpDocListMutation.MUTASIDANPROMOSI) {
                        //employeeFetch.setStatusPensiunProgram(PstEmployee.STATUS_PENSIUN_PROGRAM_INCLUDE);
                        //employeeFetch.setStartDatePensiun(empDocListMutation.getWorkFrom());
                        //employeeFetch.setEnd_contract(null);
                    }
                    if (empDocListMutation.getTipeDoc() == PstEmpDocListMutation.RESIGN) {
                        employeeFetch.setMemOfBpjsKesahatan(0);
                        employeeFetch.setMemOfBpjsKetenagaKerjaan(0);
                        employeeFetch.setStatusPensiunProgram(PstEmployee.STATUS_PENSIUN_PROGRAM_NOT_INCLUDE);
                        //employeeFetch.setStartDatePensiun(empDocListMutation.getWorkFrom());
                        employeeFetch.setDanaPendidikan(0);
                        employeeFetch.setResigned(1);
                        employeeFetch.setResignedDate(empDocListMutation.getWorkFrom());
                        employeeFetch.setResignedReasonId(empDocListMutation.getResignReason());
                        employeeFetch.setResignedDesc(empDocListMutation.getResignDesc());
                    }
                    employeeFetch.setWorkassigncompanyId(empDocListMutation.getCompanyId());
                    employeeFetch.setWorkassigndivisionId(empDocListMutation.getDivisionId());
                    employeeFetch.setWorkassigndepartmentId(empDocListMutation.getDepartmentId());
                    employeeFetch.setWorkassignsectionId(empDocListMutation.getSectionId());
                    employeeFetch.setWorkassignpositionId(empDocListMutation.getPositionId());

                    if (statusExe && ((empDocListMutation.getHistoryType() == 0) || (empDocListMutation.getHistoryType() == 1))) {
                        try {

                            long oid = PstEmployee.updateExc(employeeFetch);
                            //update custom field Nomor SK Terakhir
                            long oidCustomSkNumber = PstEmpCustomField.updateDATA_TEXT(employeeFetch.getOID(), empDocX.getDoc_number(), oidCustomFieldSkNumber);

                            long oidCustomSkDate = PstEmpCustomField.updateDATA_DATE(employeeFetch.getOID(), empDocX.getRequest_date(), oidCustomFieldSkDate);

                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        } catch (DBException dbexc) {
                        }
                    }
                }

                msgStringAll = msgStringAll + "<br>" + employeeFetch.getFullName() + " = " + msgString + " ; ";
            }
        }
    }
    iErrCode = ctrlEmpDocAction.action(iCommand, oidEmpDocAction, oidDocAction, oidEmpDoc, saveType);
    //cek action yang sudah dilakukan
    int count = PstEmpDocAction.getCount("" + PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_EMP_DOC_ID] + "=" + oidEmpDoc + " AND " + PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_ACTION_NAME] + "=\"" + docMasterAction.getActionName() + "\"" + " AND " + PstEmpDocAction.fieldNames[PstEmpDocAction.FLD_ACTION_TITLE] + "=\"" + docMasterAction.getActionTitle() + "\"");

%>



<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - EmpDocAction</title>
        <script language="JavaScript">

            function cmdSave(){
                document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
                document.frmEmpDocField.action="EmpDocumentDetailAction.jsp";
                document.frmEmpDocField.submit();
   
            } 
            function cmdSaveUpSalary(){
                document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
                document.frmEmpDocField.action="EmpDocumentDetailAction.jsp";
                document.frmEmpDocField.submit();
   
            } 
            function cmdProses(){
                document.frmEmpDocField.command.value="<%=String.valueOf(Command.POST)%>";
                document.frmEmpDocField.action="EmpDocumentDetailAction.jsp";
                document.frmEmpDocField.submit();
   
            } 
            function cmdSaveMutation(){
                document.frmEmpDocField.command.value="<%=String.valueOf(Command.SAVE)%>";
                document.frmEmpDocField.action="EmpDocumentDetailAction.jsp?saveType="+0;
                document.frmEmpDocField.submit();
   
            }    


            //-------------- script control line -------------------
            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && document.getElementById) x=document.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }

        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
                function hideObjectForEmployee(){
                }

                function hideObjectForLockers(){
                }

                function hideObjectForCanteen(){
                }

                function hideObjectForClinic(){
                }

                function hideObjectForMasterdata(){
                }

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%//@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="10" valign="middle">

                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            <tr>
                <td width="88%" valign="top" align="left">
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                <!-- #BeginEditable "contenttitle" -->
                                                <%=docMasterAction.getActionName()%>
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <form name="frmEmpDocField" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="oidEmpDoc" value="<%=oidEmpDoc%>">
                                                                                    <input type="hidden" name="oidEmpDocAction" value="<%=oidEmpDocAction%>">
                                                                                    <input type="hidden" name="oidDocAction" value="<%=oidDocAction%>">
                                                                                    <% if ((docMasterAction.getActionName().equals("Mutation"))) {%>
                                                                                    <%=valid%>
                                                                                    <%=msgStringAll%>
                                                                                    <table style="border:1px solid ; border-color: #0084ff; border-style: groove;" width="80%" border="1" cellspacing="1" cellpadding="1" class="tablecolor">

                                                                                        <tr>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                EMP NUM
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                NAME
                                                                                            </td>
                                                                                            <!-- <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                 COMPANY CURRENT
                                                                                             </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                COMPANY AFTER
                                                                                            </td>
                                                                                            <!--<td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                DIVISION CURRENT
                                                                                            </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                DIVISION AFTER
                                                                                            </td>
                                                                                            <!--<td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                DEPARTMENT CURRENT
                                                                                            </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                DEPARTMENT AFTER
                                                                                            </td>
                                                                                            <!--<td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                EMP CAT CURRENT
                                                                                            </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                EMP CAT AFTER
                                                                                            </td>
                                                                                            <!--<td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                LEVEL CURRENT
                                                                                            </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                LEVEL AFTER
                                                                                            </td>
                                                                                            <!--<td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                POSITION CURRENT
                                                                                            </td>-->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                POSITION AFTER
                                                                                            </td>
                                                                                            <!-- <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                 SECTION CURRENT
                                                                                             </td> -->
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                SECTION AFTER
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                WORK TO AFTER
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                SELECT ALL
                                                                                            </td>

                                                                                        </tr>


                                                                                        <%
                                                                                            for (int list = 0; list < listEmp.size(); list++) {

                                                                                                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                                                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                                                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());

                                                                                        %>

                                                                                        <tr>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getEmployeeNum()%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getFullName()%>
                                                                                            </td>
                                                                                            <!--<td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getCompanyId()%>
                                                                                        </td>-->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newCompanyS%>
                                                                                            </td>
                                                                                            <!-- <td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getDivisionId()%>
                                                                                        </td>-->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newDivisionS%>
                                                                                            </td>
                                                                                            <!--<td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getDepartmentId()%>
                                                                                        </td>-->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newDepartmentS%>
                                                                                            </td>
                                                                                            <!--<td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getEmpCategoryId()%>
                                                                                        </td> -->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newEmpCatS%>
                                                                                            </td>
                                                                                            <!--<td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getLevelId()%>
                                                                                        </td> -->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newLevelS%>
                                                                                            </td>
                                                                                            <!-- <td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getPositionId()%>
                                                                                        </td> -->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newPositionS%>
                                                                                            </td>
                                                                                            <!--<td  style="background-color: #ffffff; text-align: center;">
                                                                                            <%//=employeeFetch.getSectionId()%>
                                                                                        </td> -->
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newSectionS%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=workFromS%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <input type="checkbox" name="userSelect<%=employeeFetch.getOID()%>" id="userSelect<%=employeeFetch.getOID()%>" value="1" >
                                                                                            </td>
                                                                                        </tr>

                                                                                        <% }%>
                                                                                    </table>
                                                                                    <% if (count == 0) {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            <button class="btn" onclick="cmdSaveMutation()">RUN</button>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% } else {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            Action sudah dilakukan
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% }%>
                                                                                    <% } else if (docMasterAction.getActionName().equals("Mutation to All")) {%>
                                                                                    <%=valid%>
                                                                                    <table style="border:1px solid ; border-color: #0084ff; border-style: groove;" width="80%" border="1" cellspacing="1" cellpadding="1" class="tablecolor">

                                                                                        <tr>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                Payroll number
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                Nama
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                Sekarang
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                Perubahan yang diinginkan
                                                                                            </td>

                                                                                        </tr>


                                                                                        <%
                                                                                            for (int list = 0; list < listEmp.size(); list++) {

                                                                                                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                                                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                                                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                                                String value = (String) HashtableEmp.get("NOW_POSITION");
                                                                                                String newposition = PstEmpDocListMutation.getNewPosition(oidEmpDoc, employeeFetch.getOID(), docMasterActionParam1.getObjectName());
                                                                                        %>

                                                                                        <tr>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getEmployeeNum()%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getFullName()%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=value%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newposition%>
                                                                                            </td>


                                                                                        </tr>

                                                                                        <% }%>
                                                                                    </table>
                                                                                    <% if (count == 0) {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            <button class="btn" onclick="cmdSaveMutation()">RUN</button>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% } else {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            Action sudah dilakukan
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% }%>
                                                                                    <% } else if (docMasterAction.getActionName().equals("Update Gaji Employee")) {%>

                                                                                    <%=valid%>
                                                                                    <table style="border:1px solid ; border-color: #0084ff; border-style: groove;" width="80%" border="1" cellspacing="1" cellpadding="1" class="tablecolor">

                                                                                        <tr>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                EMP NUM
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                NAME
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                SALARY LEVEL CURRENT
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                NEW SALARY LEVEL
                                                                                            </td>
                                                                                            <td style="background: #9e9e9e; text-align: center; font-size: 15; ">
                                                                                                SELECT
                                                                                            </td>

                                                                                        </tr>


                                                                                        <%
                                                                                            for (int list = 0; list < listEmp.size(); list++) {

                                                                                                EmpDocList empDocList = (EmpDocList) listEmp.get(list);
                                                                                                Employee employeeFetch = PstEmployee.fetchExc(empDocList.getEmployee_id());
                                                                                                Hashtable HashtableEmp = PstEmployee.fetchExcHashtable(empDocList.getEmployee_id());
                                                                                                String whereLevel1 = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] + " = '" + (employeeFetch.getOID()) + "'";
                                                                                                String orderDate1 = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_START_DATE] + " DESC ";
                                                                                                Vector listEmpLevelByEmployeeId1 = PstPayEmpLevel.list(0, 0, whereLevel1, orderDate1);
                                                                                                PayEmpLevel payEmpLevel = new PayEmpLevel();
                                                                                                String salaryLevelName = "";
                                                                                                if (listEmpLevelByEmployeeId1.size() > 0) {

                                                                                                    try {
                                                                                                        payEmpLevel = (PayEmpLevel) listEmpLevelByEmployeeId1.get(0);
                                                                                                    } catch (Exception e) {
                                                                                                    }

                                                                                                    salaryLevelName = PstSalaryLevel.getSalaryName(payEmpLevel.getLevelCode());
                                                                                                }
                                                                                        %>

                                                                                        <tr>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getEmployeeNum()%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=employeeFetch.getFullName()%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=salaryLevelName%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <%=newSalaryLevel%>
                                                                                            </td>
                                                                                            <td  style="background-color: #ffffff; text-align: center;">
                                                                                                <input type="checkbox" name="userSelect<%=employeeFetch.getOID()%>" id="userSelect<%=employeeFetch.getOID()%>" value="1" >
                                                                                            </td>
                                                                                        </tr>

                                                                                        <% }%>
                                                                                    </table>
                                                                                    <% if ((count == 0) && (!empDocX.getDetails().equals(""))) {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            <button class="btn" onclick="cmdSaveMutation()">RUN</button> Sebelum melakukan action, Mohon dicek apakah document ini sudah disave atau belum..
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% } else {%>
                                                                                    <tr>
                                                                                        <td colspan="2">
                                                                                            Action sudah dilakukan || document belum disimpan
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% }%>
                                                                                    <% }%>
                                                                                    <%=msgString%>
                                                                                </form>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp; </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > 
                    <%@ include file = "../../main/footer.jsp" %>
                </td>
            </tr>
            <%}%>
        </table>
    </body>
    <script language="JavaScript">
    </script>
</html>