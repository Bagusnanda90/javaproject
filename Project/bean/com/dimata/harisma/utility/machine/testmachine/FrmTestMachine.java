/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine.testmachine;


import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author RAMA
 */
public class FrmTestMachine extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private TestMachine testMachine;

	public static final String FRM_NAME		=  "FRM_NAME_MACHINE" ;

	public static final int FRM_FIELD_NAME_ODBC			=  0 ;
	public static final int FRM_FIELD_DNS			=  1 ;
	public static final int FRM_FIELD_USER			=  2 ;
	public static final int FRM_FIELD_PASSWORD			=  3 ;
	public static final int FRM_FIELD_EXSAMPLE_FUNGSI		=  4 ;
	public static final int FRM_FIELD_QUERY_EXSAMPLE		=  5 ;
        public static final int FRM_FIELD_TYPE_ODBC		=  6 ;
        public static final int FRM_FIELD_USE_LIMIT		=  7 ;
        public static final int FRM_FIELD_USE_LIMIT_VALUE		=  8 ;
	
    
    //}
	public static String[] fieldNames = {
            "FRM_FIELD_NAME_ODBC",//0
            "FRM_FIELD_DNS",//1
            "FRM_FIELD_USER",//2
            "FRM_FIELD_PASSWORD",//3
            "FRM_FIELD_EXSAMPLE_FUNGSI",//4
            "FRM_FIELD_QUERY_EXSAMPLE",//5
            "FRM_FIELD_TYPE_ODBC",//5
            "FRM_FIELD_USE_LIMIT",
            "FRM_FIELD_USE_LIMIT_VALUE"
            
    };
        
       
	public static int[] fieldTypes = {
            //0
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_INT,
            TYPE_STRING,
            TYPE_INT,
            TYPE_BOOL,
            TYPE_INT
                
    };

        public static final int TYPE_MYSQL			=  0 ;
	public static final int TYPE_MDB		=  1 ;
	public static final int TYPE_DBF		=  2 ;
        public static String[] fieldNamesTypeOdbc = {
            ".MYSQL",//0
            ".MDB",//1
            ".DBF"
           
       };
        
        public static Vector getKeyTypeOdbc(){
            Vector vKey = new Vector();
            for(int x=0;x<fieldNamesTypeOdbc.length;x++){
                vKey.add(fieldNamesTypeOdbc[x]);
            }
            return vKey;
            
        }
        
        public static Vector getValueTypeOdbc(){
            Vector vVal = new Vector();
            for(int x=0;x<fieldNamesTypeOdbc.length;x++){
                vVal.add(""+x);
            }
            return vVal;
            
        }
        
        
        
        
        
        
        public static final int INSERT			=  0 ;
	public static final int UPDATE		=  1 ;
        public static final int DELETE		=  2 ;
         public static final int LIST		=  3 ;
         public static String[] fieldNamesTypeQuery = {
            "INSERT",//0
            "UPDATE",//1
            "DELETE",
            "LIST"
       };
         
          public static Vector getKeyTypeQuery(){
            Vector vKey = new Vector();
            for(int x=0;x<fieldNamesTypeQuery.length;x++){
                vKey.add(fieldNamesTypeQuery[x]);
            }
            return vKey;
            
        }
        
        public static Vector getValueTypeQuery(){
            Vector vVal = new Vector();
            for(int x=0;x<fieldNamesTypeQuery.length;x++){
                vVal.add(""+x);
            }
            return vVal;
            
        }
       
     	public FrmTestMachine(){
	}
	public FrmTestMachine(TestMachine testMachine){
		this.testMachine = testMachine;
	}

	public FrmTestMachine(HttpServletRequest request, TestMachine testMachine){
		super(new FrmTestMachine(testMachine), request);
		this.testMachine = testMachine;
	}

	public String getFormName() { return FRM_NAME; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public TestMachine getEntityObject(){ return testMachine; }

	public void requestEntityObject(TestMachine testMachine) {
		try{
			this.requestParam();
			testMachine.setNamaOdbc(getString(FRM_FIELD_NAME_ODBC));
                        testMachine.setDns(getString(FRM_FIELD_DNS));
                        testMachine.setUser(getString(FRM_FIELD_USER));
                        testMachine.setPassword(getString(FRM_FIELD_PASSWORD));
                        testMachine.setCodeExsample(getInt(FRM_FIELD_EXSAMPLE_FUNGSI));
                        testMachine.setSampleQuery(getString(FRM_FIELD_QUERY_EXSAMPLE));
                         testMachine.setTypeOdbc(getInt(FRM_FIELD_TYPE_ODBC));
                         testMachine.setUseLimit(getBoolean(FRM_FIELD_USE_LIMIT));
                         testMachine.setUseLimitValue(getInt(FRM_FIELD_USE_LIMIT_VALUE));
			
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
		}
	}
}
