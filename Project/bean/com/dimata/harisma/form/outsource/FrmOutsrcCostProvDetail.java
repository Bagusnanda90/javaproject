/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceCostMaster;
import com.dimata.harisma.entity.outsource.OutsrcCostProvDetail;
import com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmOutsrcCostProvDetail extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutsrcCostProvDetail entOutsrcCostProvDetail;
    public static final String FRM_NAME_OUTSRC_COST_PROV_DETAIL = "FRM_NAME_OUTSRC_COST_PROV_DETAIL";
    public static final int FRM_FIELD_OUTSRC_COST_PROV_DETLD_ID = 0;
    public static final int FRM_FIELD_OUTSRC_COST_PROVIDER_ID = 1;
    public static final int FRM_FIELD_OUTSRC_COST_ID = 2;
    public static final int FRM_FIELD_COST_VAL = 3;
    public static final int FRM_FIELD_NOTE = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSRC_COST_PROV_DETLD_ID",
        "FRM_FIELD_OUTSRC_COST_PROVIDER_ID",
        "FRM_FIELD_OUTSRC_COST_ID",
        "FRM_FIELD_COST_VAL",
        "FRM_FIELD_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING
    };

    public FrmOutsrcCostProvDetail() {
    }

    public FrmOutsrcCostProvDetail(OutsrcCostProvDetail entOutsrcCostProvDetail) {
        this.entOutsrcCostProvDetail = entOutsrcCostProvDetail;
    }

    public FrmOutsrcCostProvDetail(HttpServletRequest request, OutsrcCostProvDetail entOutsrcCostProvDetail) {
        super(new FrmOutsrcCostProvDetail(entOutsrcCostProvDetail), request);
        this.entOutsrcCostProvDetail = entOutsrcCostProvDetail;
    }

    public String getFormName() {
        return FRM_NAME_OUTSRC_COST_PROV_DETAIL;
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

    public OutsrcCostProvDetail getEntityObject() {
        return entOutsrcCostProvDetail;
    }

    public void requestEntityObject(OutsrcCostProvDetail entOutsrcCostProvDetail) {
        try {
            this.requestParam();
            entOutsrcCostProvDetail.setOutsrcCostProviderId(getLong(FRM_FIELD_OUTSRC_COST_PROVIDER_ID));
            entOutsrcCostProvDetail.setOutsrcCostId(getLong(FRM_FIELD_OUTSRC_COST_ID));
            entOutsrcCostProvDetail.setCostVal(getFloat(FRM_FIELD_COST_VAL));
            entOutsrcCostProvDetail.setNote(getString(FRM_FIELD_NOTE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
      public Vector<OutsrcCostProvDetail> requestEntityObject(long outsourceCostProvId, Vector vMaster) {
        Vector<OutsrcCostProvDetail> lists = new Vector();
        
        try {
            lists = new Vector();
            
            for(int i = 0; i < vMaster.size(); i++) {
                OutSourceCostMaster outSourceCostMaster = (OutSourceCostMaster)vMaster.get(i);
                OutsrcCostProvDetail costProvDetail = new OutsrcCostProvDetail();
                long oidDetail = PstOutsrcCostProvDetail.getOid("OUTSRC_COST_PROVIDER_ID="+outsourceCostProvId+" && OUTSRC_COST_ID="+outSourceCostMaster.getOID());
                if(oidDetail != 0){
                    costProvDetail.setOID(oidDetail);
                }
                costProvDetail.setOutsrcCostProviderId(outsourceCostProvId);
                costProvDetail.setOutsrcCostId(outSourceCostMaster.getOID());
                costProvDetail.setCostVal(getParamFloat(FrmOutsrcCostProvDetail.fieldNames[FrmOutsrcCostProvDetail.FRM_FIELD_COST_VAL]+"_"+ outSourceCostMaster.getOID()));
                costProvDetail.setNote(getParamString(FrmOutsrcCostProvDetail.fieldNames[FrmOutsrcCostProvDetail.FRM_FIELD_NOTE]+"_"+ outSourceCostMaster.getOID()));
                lists.add(costProvDetail);
            }
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
        return lists;
    }
}
