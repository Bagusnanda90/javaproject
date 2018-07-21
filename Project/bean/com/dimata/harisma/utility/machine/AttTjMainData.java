/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author roy a
 */
public class AttTjMainData {
    
    public static String Tbl_UserInfo = "HR_Personnel";
    public static String Tbl_CheckInOut = "TA_Record_Info";
    public static String Tbl_Machines = "Machines";
    
    /* Field Tbl UserInfo */
    public static String Fld_USERID = "USERID";
    public static String Fld_Badgenumber = "Badgenumber";
    public static String Fld_SSN = "SSN";
    public static String Fld_Name = "Name";
    public static String Fld_Gender = "Gender";
    public static String Fld_TITLE = "TITLE";
    public static String Fld_PAGER = "PAGER";
    public static String Fld_BIRTHDAY = "BIRTHDAY";
    public static String Fld_HIREDDAY = "HIREDDAY";
    public static String Fld_street = "street";
    public static String Fld_CITY = "CITY";
    public static String Fld_STATE = "STATE";
    public static String Fld_ZIP = "ZIP";
    public static String Fld_OPHONE = "OPHONE";
    public static String Fld_FPHONE = "FPHONE";
    public static String Fld_VERIFICATIONMETHOD = "VERIFICATIONMETHOD";
    public static String Fld_DEFAULTDEPTID = "DEFAULTDEPTID";
    public static String Fld_SECURITYFLAGS = "SECURITYFLAGS";
    public static String Fld_ATT = "ATT";
    public static String Fld_INLATE = "INLATE";
    public static String Fld_OUTEARLY = "OUTEARLY";
    public static String Fld_OVERTIME = "OVERTIME";
    public static String Fld_SEP = "SEP";
    public static String Fld_HOLIDAY = "HOLIDAY";
    public static String Fld_MINZU = "MINZU";
    public static String Fld_PASSWORD = "PASSWORD";
    public static String Fld_LUNCHDURATION = "LUNCHDURATION";
    public static String Fld_MVERIFYPASS = "MVERIFYPASS";
    public static String Fld_PHOTO = "PHOTO";
    public static String Fld_Notes = "Notes";
    public static String Fld_privilege = "privilege";
    public static String Fld_InheritDeptSch = "InheritDeptSch";
    public static String Fld_InheritDeptSchClass = "InheritDeptSchClass";
    public static String Fld_AutoSchPlan = "AutoSchPlan";
    public static String Fld_MinAutoSchInterval = "MinAutoSchInterval";
    public static String Fld_RegisterOT = "RegisterOT";
    public static String Fld_InheritDeptRule = "InheritDeptRule";
    public static String Fld_EMPRIVILEGE = "EMPRIVILEGE";
    public static String Fld_CardNo = "CardNo";
    public static String Fld_Pin1 = "Pin1";
                
    /* Field Tbl Check In Out*/
    public static String Fld_InOut_USERID = "USERID";
    public static String Fld_InOut_CHECKTIME = "CHECKTIME";
    public static String Fld_InOut_CHECKTYPE = "CHECKTYPE";
    public static String Fld_InOut_VERIFYCODE = "VERIFYCODE";
    public static String Fld_InOut_SENSORID = "SENSORID";
    public static String Fld_InOut_WorkCode = "WorkCode";
    public static String Fld_InOut_sn = "sn";
    public static String Fld_InOut_STATUS = "STATUS";
    
    /* Field Tbl Machines*/
    public static String Fld_MachineNumber = "MachineNumber";
    public static String Fld_MachineAlias = "MachineAlias";
    
    /* Work Code (Check in, Check out, Break In , Break Out)*/
    public static String CheckType_CheckIn  = "I";
    public static String CheckType_CheckOut = "O";
    public static int WorkCode_BreakIn      = 1;
    public static int WorkCode_BreakOut     = 0;
    
    /* Status type */
    public static int Status_Not_Transfered = 0; /* value 0 or null */
    public static int Status_Transfered = 1;
    
    /* type machine transaction */
    public static String mode_in    = "A";
    public static String mode_out   = "B";

}
