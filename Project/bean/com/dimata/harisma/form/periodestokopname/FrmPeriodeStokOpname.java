/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.periodestokopname;

import com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname;
import com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname;
import com.dimata.harisma.form.periodestokopname.FrmPeriodeStokOpname;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmPeriodeStokOpname  extends FRMHandler implements I_FRMInterface, 
        I_FRMType{
    private PeriodeStokOpname PeriodeStokOpname;
    public static final String FRM_PERIOD_STOK_OPNAME= "FRM_PERIOD_STOK_OPNAME";
    public static final int FRM_PERIOD_OPNAME_ID=0;
    public static final int FRM_NAME_PERIOD=1;
     public static final int FRM_PERIOD=2;
      public static final int FRM_START_DATE=3;
       public static final int FRM_END_DATE=4;
           
           
    public static String[] fieldNames ={
        "FRM_PERIOD_OPNAME_ID",
        "FRM_NAME_PERIOD",
         "FRM_PERIOD",
         "FRM_START_DATE",
         "FRM_END_DATE"
        
       
       
    };
    public static int[] fieldTypes = {
          TYPE_LONG,
          TYPE_STRING,
          TYPE_STRING,
          TYPE_DATE,
          TYPE_DATE
         
          
           };
public FrmPeriodeStokOpname(){
    }
    public FrmPeriodeStokOpname(PeriodeStokOpname periodeStokOpname){
        this.PeriodeStokOpname = periodeStokOpname;
    }
    public FrmPeriodeStokOpname(HttpServletRequest request, PeriodeStokOpname periodeStokOpname){
        super(new FrmPeriodeStokOpname(periodeStokOpname),request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.PeriodeStokOpname = periodeStokOpname;
    }
    public String getFormName() { return FRM_PERIOD_STOK_OPNAME; }
    public int[] getFieldTypes() { return fieldTypes; }
    public String[] getFieldNames() { return fieldNames; }
    public int getFieldSize() { return fieldNames.length; }
    public PeriodeStokOpname getEntityObject(){ return PeriodeStokOpname; }

    public void requestEntityObject(PeriodeStokOpname  periodeStokOpname) { //melakukan 
        ///pemanggilan terhadap Employee
        boolean cekDate=true;
            try{
		this.requestParam();
                    periodeStokOpname.setNamePeriod(getString(FRM_NAME_PERIOD));
                     periodeStokOpname.setPeriod(getString(FRM_PERIOD));
                      periodeStokOpname.setStartDate(getDate(FRM_START_DATE));
                       periodeStokOpname.setEndDate(getDate(FRM_END_DATE));
                       
                     
        if(cekDate){
            Date startDate=periodeStokOpname.getStartDate();
            Date endDate =periodeStokOpname.getEndDate();
            if(startDate.getTime()>endDate.getTime()){
                periodeStokOpname.setStartDate(getDate(FRM_END_DATE));
                periodeStokOpname.setEndDate(getDate(FRM_START_DATE));
                
                
            }
            
        }
                   
                      
                   
                    //set Nama_Employee
                    
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
