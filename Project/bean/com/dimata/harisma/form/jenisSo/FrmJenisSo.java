/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.jenisSo;


import com.dimata.harisma.entity.jenisSo.JenisSo;
import com.dimata.harisma.form.jenisSo.FrmJenisSo;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmJenisSo extends FRMHandler implements I_FRMInterface, 
        I_FRMType{
     private JenisSo JenisSo;
    public static final String FRM_JENIS_SO= "FRM_JENIS_SO";
    public static final int FRM_JENIS_SO_ID=0;
    public static final int FRM_NAMA_SO=1;
    
           
           
    public static String[] fieldNames ={
        "FRM_JENIS_SO_ID",
        "FRM_FLD_NAMA_SO"
       
        
       
       
    };
    public static int[] fieldTypes = {
          TYPE_LONG,
          TYPE_STRING
         
          
           };
public FrmJenisSo(){
    }
    public FrmJenisSo(JenisSo jenisSo){
        this.JenisSo = jenisSo;
    }
    public FrmJenisSo(HttpServletRequest request, JenisSo jenisSo){
        super(new FrmJenisSo(jenisSo),request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.JenisSo = jenisSo;
    }
    public String getFormName() { return FRM_JENIS_SO; }
    public int[] getFieldTypes() { return fieldTypes; }
    public String[] getFieldNames() { return fieldNames; }
    public int getFieldSize() { return fieldNames.length; }
    public JenisSo getEntityObject(){ return JenisSo; }

    public void requestEntityObject(JenisSo  jenisSo) { //melakukan 
        ///pemanggilan terhadap Employee
            try{
		this.requestParam();
                    jenisSo.setNamaSo(getString(FRM_NAMA_SO));
                    
                   
                      
                   
                    //set Nama_Employee
                    
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
