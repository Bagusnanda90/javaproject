/*
 * LongLeaveMonthly.java
 *
 * Created on October 14, 2004, 11:10 AM
 */

package com.dimata.harisma.session.attendance;

// import java core package
import java.util.*;
import java.sql.ResultSet;

// import dimata package
import com.dimata.qdep.db.*;
import com.dimata.util.*;

// import harisma package
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;  
import com.dimata.harisma.entity.search.SrcLeaveManagement;


/**
 *
 * @author  gedhy
 */
public class LongLeaveReport {
    
    /**
     * @param srcLeaveManagement
     * @return
     * @created by Edhy    
     */    
    public static Vector getListLlStockReport(SrcLeaveManagement srcLeaveManagement)
    {
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try
        {
            String sQL = "SELECT EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +
                         ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID] +                         
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE1] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE2] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE3] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE4] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE5] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE6] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE7] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE8] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE9] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE10] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TOTAL_LL] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_MTD] +
                         ", LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_YTD] +
                         " FROM " + PstLlStockReport.TBL_LL_STOCK_REPORT + " AS LL" +
                         " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                         " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + 
                         " = LL." + PstLlStockReport.fieldNames[PstLlStockReport.FLD_EMPLOYEE_ID];                         
                         
                        String strNameCondition = "";
                        if((srcLeaveManagement.getEmpName()!= null)&& (srcLeaveManagement.getEmpName().length()>0))
                        {
                            Vector vectName = logicParser(srcLeaveManagement.getEmpName());
                            if(vectName != null && vectName.size()>0)
                            {                    
                                strNameCondition = " (";
                                for(int i = 0; i <vectName.size();i++)
                                {
                                    String str = (String)vectName.get(i);
                                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str))
                                    {
                                            strNameCondition = strNameCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                                                               " LIKE '%"+str.trim()+"%' ";
                                    }
                                    else
                                    {
                                            strNameCondition = strNameCondition + str.trim();
                                    }
                                }
                                strNameCondition = strNameCondition + ")";
                            }
                        }
  
                        String strNumCondition = "";
                        if((srcLeaveManagement.getEmpNum()!= null)&& (srcLeaveManagement.getEmpNum().length()>0))
                        {
                            Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
                            if(vectNum != null && vectNum.size()>0)
                            {                    
                                strNumCondition = " (";
                                for(int i = 0; i <vectNum.size();i++)
                                {
                                    String str = (String)vectNum.get(i);
                                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str))
                                    {
                                            strNumCondition = strNumCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                                                                        " LIKE '"+str.trim()+"%' ";
                                    }
                                    else
                                    {
                                            strNumCondition = strNumCondition + str.trim();
                                    }
                                }
                                strNumCondition = strNumCondition + ")";
                            }
                        }

                        String strCategoryCondition = "";
                        if(srcLeaveManagement.getEmpCatId() != 0) 
                        {
                            strCategoryCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
                                                   " = "+srcLeaveManagement.getEmpCatId();
                        }

                        String strDepartmentCondition = "";
                        if(srcLeaveManagement.getEmpDeptId() != 0) 
                        {
                            strDepartmentCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
                                                     " = "+srcLeaveManagement.getEmpDeptId();
                        }

                        String strSectionCondition = "";
                        if(srcLeaveManagement.getEmpSectionId() != 0) 
                        {
                            strSectionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                                                  " = "+srcLeaveManagement.getEmpSectionId();
                        }

                        String strPositionCondition = "";
                        if(srcLeaveManagement.getEmpPosId() != 0) 
                        {
                            strPositionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
                                                   " = "+srcLeaveManagement.getEmpPosId();
                        }

                        String strLeavePeriodCondition = "";
                        if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked())      
                        {   
                            Date selectedDate = srcLeaveManagement.getLeavePeriod();
                            long periodId = PstPeriod.getPeriodIdBySelectedDate(selectedDate);
                            strLeavePeriodCondition = " LL."+PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID]+
                                                      " = "+periodId;                                             
                        }

                        String whereClause = "";            
                        if(strNameCondition!=null && strNameCondition.length()>0)
                        {
                            whereClause = strNameCondition;
                        }
  
                        if(strNumCondition!=null && strNumCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strNumCondition;
                            }
                            else
                            {
                                whereClause = strNumCondition;
                            }                
                        }

                        if(strCategoryCondition!=null && strCategoryCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strCategoryCondition;
                            }
                            else
                            {
                                whereClause = strCategoryCondition;
                            }                
                        }


                        if(strDepartmentCondition!=null && strDepartmentCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strDepartmentCondition;
                            }
                            else
                            {
                                whereClause = strDepartmentCondition;
                            }                
                        }

                        if(strSectionCondition!=null && strSectionCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strSectionCondition;
                            }
                            else
                            {
                                whereClause = strSectionCondition;
                            }                
                        }

                        if(strPositionCondition!=null && strPositionCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strPositionCondition;
                            }
                            else
                            {
                                whereClause = strPositionCondition;
                            }                
                        }

                        if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0)
                        {
                            if(whereClause!=null && whereClause.length()>0)
                            {
                                whereClause = whereClause + " AND " + strLeavePeriodCondition;
                            }
                            else
                            {
                                whereClause = strLeavePeriodCondition;  
                            }                
                        }
                        
            if(whereClause != null && whereClause.length()>0)
            {           	                            
            	sQL = sQL + " WHERE " + whereClause + 
                      " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            }                        

            System.out.println("sQL listLlStockReport : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next())
            {
                Vector vtTemp = new Vector(1,1);   
                
                Employee emp = new Employee();   
                emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                emp.setCommencingDate(rs.getDate(PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                vtTemp.add(emp);
                
                LlStockReport llStockReport = new LlStockReport();  
                llStockReport.setPeriodId(rs.getLong(PstLlStockReport.fieldNames[PstLlStockReport.FLD_PERIODE_ID]));                
                llStockReport.setQtyEnt1(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE1]));
                llStockReport.setQtyEnt2(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE2]));
                llStockReport.setQtyEnt3(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE3]));
                llStockReport.setQtyEnt4(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE4]));
                llStockReport.setQtyEnt5(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE5]));
                llStockReport.setQtyEnt6(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE6]));
                llStockReport.setQtyEnt7(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE7]));
                llStockReport.setQtyEnt8(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE8]));
                llStockReport.setQtyEnt9(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE9]));
                llStockReport.setQtyEnt10(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_ENTITLE10]));
                llStockReport.setQtyLlTotal(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TOTAL_LL]));
                llStockReport.setQtyLlTakenMtd(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_MTD]));
                llStockReport.setQtyLlTakenYtd(rs.getInt(PstLlStockReport.fieldNames[PstLlStockReport.FLD_QTY_TAKEN_YTD]));                
                vtTemp.add(llStockReport);
                
                result.add(vtTemp);
            }
            
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getListLlStockReport : "+e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }    
    
    /**
     * @param text
     * @return
     */    
    private static Vector logicParser(String text)
    {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++)
        {
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
              ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH)))
            { 
                vector.remove(vector.size()-1);
            }
        }
        return vector;
    }
    
}
