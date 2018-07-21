/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
//public class FrmOutSourcePlanLocation {
//    
//}
import com.dimata.harisma.entity.outsource.OutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

public class FrmOutSourcePlanLocation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutSourcePlanLocation entOutSourcePlanLocation;
    
    private Vector getListValue = new Vector();
    private  Hashtable<String, Integer> persentaseKenaikan = null;
    
    public static final String FRM_NAME_OUTSOURCEPLANLOCATION = "FRM_NAME_OUTSOURCEPLANLOCATION";
    public static final int FRM_FIELD_OUTSOURCEPLANLOCID = 0;
    public static final int FRM_FIELD_OUTSOURCEPLANID = 1;
    public static final int FRM_FIELD_COMPANYID = 2;
    public static final int FRM_FIELD_DIVISIONID = 3;
    public static final int FRM_FIELD_DEPARTMENTID = 4;
    public static final int FRM_FIELD_SECTIONID = 5;
    public static final int FRM_FIELD_NUMBERTW1 = 6;
    public static final int FRM_FIELD_NUMBERTW2 = 7;
    public static final int FRM_FIELD_NUMBERTW3 = 8;
    public static final int FRM_FIELD_NUMBERTW4 = 9;
    public static final int FRM_FIELD_PREV_EXISTING = 10;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSOURCEPLANLOCID",
        "FRM_FIELD_OUTSOURCEPLANID",
        "FRM_FIELD_COMPANYID",
        "FRM_FIELD_DIVISIONID",
        "FRM_FIELD_DEPARTMENTID",
        "FRM_FIELD_SECTIONID",
        "FRM_FIELD_NUMBERTW1",
        "FRM_FIELD_NUMBERTW2",
        "FRM_FIELD_NUMBERTW3",
        "FRM_FIELD_NUMBERTW4",
        "PREV_EXISTING"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    public FrmOutSourcePlanLocation() {
    }

    public FrmOutSourcePlanLocation(OutSourcePlanLocation entOutSourcePlanLocation) {
        this.entOutSourcePlanLocation = entOutSourcePlanLocation;
    }

    public FrmOutSourcePlanLocation(HttpServletRequest request, OutSourcePlanLocation entOutSourcePlanLocation) {
        super(new FrmOutSourcePlanLocation(entOutSourcePlanLocation), request);
        this.entOutSourcePlanLocation = entOutSourcePlanLocation;
    }

    public String getFormName() {
        return FRM_NAME_OUTSOURCEPLANLOCATION;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public OutSourcePlanLocation getEntityObject() {
        return entOutSourcePlanLocation;
    }

    public void requestEntityObject(OutSourcePlanLocation entOutSourcePlanLocation) {
        try {
            this.requestParam();
            entOutSourcePlanLocation.setOutsourcePlanId(getLong(FRM_FIELD_OUTSOURCEPLANID));
            entOutSourcePlanLocation.setCompanyId(getLong(FRM_FIELD_COMPANYID));
            entOutSourcePlanLocation.setDivisionId(getLong(FRM_FIELD_DIVISIONID));
            entOutSourcePlanLocation.setDepartmentId(getLong(FRM_FIELD_DEPARTMENTID));
            entOutSourcePlanLocation.setSectionId(getLong(FRM_FIELD_SECTIONID));
            entOutSourcePlanLocation.setNumberTW1(getInt(FRM_FIELD_NUMBERTW1));
            entOutSourcePlanLocation.setNumberTW2(getInt(FRM_FIELD_NUMBERTW2));
            entOutSourcePlanLocation.setNumberTW3(getInt(FRM_FIELD_NUMBERTW3));
            entOutSourcePlanLocation.setNumberTW4(getInt(FRM_FIELD_NUMBERTW4));
            entOutSourcePlanLocation.setPrevExisting(getInt(FRM_FIELD_PREV_EXISTING));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    
    public void requestEntityObjectMultiple() {
             try{
			this.requestParam();
                        String inputExisting[]=null;
                        inputExisting = this.getParamsStringValues("inputan");
                        persentaseKenaikan = null;
                        persentaseKenaikan =  new Hashtable<String, Integer>();
                        if(inputExisting!=null && inputExisting.length>0){
                           String formName= "" ;
                           long idxDivisionId=0;
                           long idxPositionId=0;
                           long idxPlanLocationId=0;
                           long prevIdxPlanLocationId=0;
                           double prevValue=0.0;
                           double prevValueTW1=0.0;
                           double prevValueTW2=0.0;
                           double prevValueTW3=0.0;
                           double nextValue=0.0;
                           try{
                            for(int x=0;x<inputExisting.length;x++){
                              try{
                                formName =   inputExisting[x].split("-")[0];
                                idxDivisionId = Long.parseLong(inputExisting[x].split("-")[1]);
                                idxPositionId= Long.parseLong(inputExisting[x].split("-")[2]);
                                idxPlanLocationId = Long.parseLong(inputExisting[x].split("-")[3]);
                                
                                double planvalue = this.getParamDouble(formName+"-"+idxDivisionId+"-"+idxPositionId+"-"+idxPlanLocationId);
                                
                                OutSourcePlanLocation outSourcePlanLocation =  new OutSourcePlanLocation();
                                outSourcePlanLocation.setOID(idxPlanLocationId);
                                outSourcePlanLocation.setNameColomn(formName);
                                outSourcePlanLocation.setValueInput(planvalue);
                                
                                getListValue.add(outSourcePlanLocation);
                                
                                if(formName.equals(""+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_PREV_EXISTING])){
                                    prevValue = planvalue;
                                    prevIdxPlanLocationId = idxPlanLocationId;
                                }else if (formName.equals(""+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW1])){
                                    prevValueTW1=planvalue;
                                    nextValue = prevValue - planvalue;
                                    persentaseKenaikan.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_PREV_EXISTING]+"_"+prevIdxPlanLocationId, (int) nextValue);
                                    prevIdxPlanLocationId = idxPlanLocationId;
                                }else if(formName.equals(""+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW2])){
                                    prevValueTW2=planvalue;
                                    nextValue = prevValueTW1 - planvalue;
                                    persentaseKenaikan.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW1]+"_"+prevIdxPlanLocationId, (int) nextValue);
                                    prevIdxPlanLocationId = idxPlanLocationId;
                                }else if(formName.equals(""+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW3])){
                                    prevValueTW3=planvalue;
                                    nextValue = prevValueTW2 - planvalue;
                                    persentaseKenaikan.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW2]+"_"+prevIdxPlanLocationId, (int) nextValue);
                                    prevIdxPlanLocationId = idxPlanLocationId;
                                }else{
                                    nextValue = prevValueTW3 - planvalue;
                                    persentaseKenaikan.put(""+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_NUMBERTW3]+"_"+prevIdxPlanLocationId, (int) nextValue);
                                    prevIdxPlanLocationId = idxPlanLocationId;
                                }
                              }catch(Exception exc){
                                  System.out.println("exc"+exc);
                              }
                            }
                           }catch(Exception exc){
                               System.out.println("exc frmPriceMapping"+exc);
                           }
                        }
                        
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
        }
    
      /**
     * @return the getListBudget
     */
    public Vector getGetValueInput() {
        return getListValue;
    }
    
    public Hashtable getGetHastableValueInput() {
        return persentaseKenaikan;
    }
}