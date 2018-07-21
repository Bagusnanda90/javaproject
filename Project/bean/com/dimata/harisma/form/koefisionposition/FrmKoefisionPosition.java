/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.koefisionposition;


import com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment;
import com.dimata.harisma.entity.koefisionposition.KoefisionPosition;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmKoefisionPosition  extends FRMHandler implements I_FRMInterface, 
        I_FRMType  {
      private KoefisionPosition KoefisionPosition;
    public static final String FRM_KOEFISION_POSITION= "FRM_KOEFISION_POSITION";
    
    public static final int FRM_FLD_KOEFISION_POSITION_ID=0;
    public static final int FRM_FLD_POSITION_ID=1;
    public static final int FRM_FLD_NILAI_KOEFISION_OUTLET=2;
    public static final int FRM_FLD_NILAI_KOEFISION_DC=3;

           
           
    public static String[] fieldNames ={
        "FRM_FLD_KOEFISION_POSITION_ID",
        "FRM_FLD_POSITION_ID",
        "FRM_FLD_NILAI_KOEFISION_OUTLET",
        "FRM_FLD_NILAI_KOEFISION_DC",
       
       
    };
    public static int[] fieldTypes = {
          TYPE_LONG,
          TYPE_LONG,
          TYPE_FLOAT,
          TYPE_FLOAT
          
           };
public FrmKoefisionPosition(){
    }
    public FrmKoefisionPosition(KoefisionPosition koefisionPosition){
        this.KoefisionPosition = koefisionPosition;
    }
    public FrmKoefisionPosition(HttpServletRequest request, KoefisionPosition koefisionPosition){
        super(new FrmKoefisionPosition(koefisionPosition),request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.KoefisionPosition = koefisionPosition;
    }
    public String getFormName() { return FRM_KOEFISION_POSITION; }
    public int[] getFieldTypes() { return fieldTypes; }
    public String[] getFieldNames() { return fieldNames; }
    public int getFieldSize() { return fieldNames.length; }
    public KoefisionPosition getEntityObject(){ return KoefisionPosition; }

    public void requestEntityObject(KoefisionPosition  koefisionPosition) { //melakukan 
        ///pemanggilan terhadap Employee
            try{
		this.requestParam();
                    koefisionPosition.setPositionId(getLong(FRM_FLD_POSITION_ID));
                    koefisionPosition.setNilaiKoefisionOutlet(getDouble(FRM_FLD_NILAI_KOEFISION_OUTLET)); 
                    koefisionPosition.setNilaiKoefisionDc(getDouble(FRM_FLD_NILAI_KOEFISION_DC)); 
                   
                      
                   
                    //set Nama_Employee
                    
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
    
}
