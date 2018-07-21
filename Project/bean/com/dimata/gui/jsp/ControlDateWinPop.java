/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.gui.jsp;

import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class ControlDateWinPop {

    /**
     * create data object
     * @param formName
     * @param elementsName
     * @param objName
     * @param path
     * @param isTimeComp
     * @return String obj Date
     */
    public static String writeObj(String formName, String elementsName, String objName, String path, boolean isTimeComp){
        String strObj = "";
        strObj = "var "+objName+" = new calendar3("
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_yr\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_mn\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_dy\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_hr\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_mi\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_sec\'],"
		+"document.forms[\'"+formName+"\'].elements[\'"+elementsName+"_mer\']"
	+");"
	+objName+".year_scroll = true;"
	+objName+".time_comp = "+isTimeComp+";"
	+objName+".cal_path = \""+path+"\";";
        return strObj;
    }
    
    /**
     * create input object
     * @param elementName
     * @param name object
     * @param date
     * @return String input
     */
    public static String writeInputDate(String elementName, String objName, Date date){
        String strObjDate = "";
        strObjDate = "<input onClick=\""+objName+".popup_first();\" name=\""
                +elementName+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""
                +Formater.formatDate((date == null? new Date() : date), "yyyy-MM-dd")+"\"/>";
        String strDateObj =
               "<input type=\"hidden\" name=\'"+elementName+"_mn\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_dy\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_yr\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_hr\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_mi\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_sec\'>"
               +"<input type=\"hidden\" name=\'"+elementName+"_mer\'>";
        
        strObjDate += strDateObj;
               
        return strObjDate;
    }
}
