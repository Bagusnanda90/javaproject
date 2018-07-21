/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.harisma.entity.outsource.OutSourceCostProvDetailAvrg;
import com.dimata.harisma.entity.outsource.OutSourcePlan;
import com.dimata.harisma.entity.outsource.OutSourcePlanCost;
import com.dimata.harisma.entity.outsource.OutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.PstOutSourcePlan;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanCost;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

public class FrmOutSourcePlanCost extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutSourcePlanCost entOutSourcePlanCost;
    public static final String FRM_NAME_OUTSOURCEPLANCOST = "FRM_NAME_OUTSOURCEPLANCOST";
    public static final int FRM_FIELD_OUTSOURCEPLANCOSTID = 0;
    public static final int FRM_FIELD_OUTSOURCEPLANLOCID = 1;
    public static final int FRM_FIELD_OUTSOURCECOSTID = 2;
    public static final int FRM_FIELD_INCRSTOPREVYEAR = 3;
    public static final int FRM_FIELD_PLANAVRGCOST = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSOURCEPLANCOSTID",
        "FRM_FIELD_OUTSOURCEPLANLOCID",
        "FRM_FIELD_OUTSOURCECOSTID",
        "FRM_FIELD_INCRSTOPREVYEAR",
        "FRM_FIELD_PLANAVRGCOST"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    public FrmOutSourcePlanCost() {
    }

    public FrmOutSourcePlanCost(OutSourcePlanCost entOutSourcePlanCost) {
        this.entOutSourcePlanCost = entOutSourcePlanCost;
    }

    public FrmOutSourcePlanCost(HttpServletRequest request, OutSourcePlanCost entOutSourcePlanCost) {
        super(new FrmOutSourcePlanCost(entOutSourcePlanCost), request);
        this.entOutSourcePlanCost = entOutSourcePlanCost;
    }

    public String getFormName() {
        return FRM_NAME_OUTSOURCEPLANCOST;
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

    public OutSourcePlanCost getEntityObject() {
        return entOutSourcePlanCost;
    }

    public void requestEntityObject(OutSourcePlanCost entOutSourcePlanCost) {
        try {
            this.requestParam();
            entOutSourcePlanCost.setOutSourcePlanLocId(getLong(FRM_FIELD_OUTSOURCEPLANLOCID));
            entOutSourcePlanCost.setOutSourceCostId(getLong(FRM_FIELD_OUTSOURCECOSTID));
            entOutSourcePlanCost.setIncrsToPrevYear(getFloat(FRM_FIELD_INCRSTOPREVYEAR));
            entOutSourcePlanCost.setPlanAvrgCost(getFloat(FRM_FIELD_PLANAVRGCOST));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    public Vector<OutSourcePlanCost> requestEntityObjectMultiple(long oidOutSourcePlan, long oidOutMasterCost) {
        try {
            this.requestParam();

            String whereLoc = "ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID] + "=" + oidOutSourcePlan;
            Vector listSourcePlanLocationPosition = PstOutSourcePlanLocation.listJoinPositionOnly(0, 0, whereLoc, "");

            Vector listSourcePlanLocationDivision = PstOutSourcePlanLocation.listJoinDivisionOnly(0, 0, whereLoc, "");

            if (listSourcePlanLocationPosition.size() > 0) { // biaya laih daya YANG DI SAVE
                Hashtable<String, Float> hPosIncrease = new Hashtable<String, Float>(); // map position ID => Increase per year  eg. 10 artinya: 10%
                Vector<OutSourcePlanCost> vLocPosCost = new Vector<OutSourcePlanCost>();

                for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                    Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);

                    String whereLocSelected = " ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID] + "=" + oidOutSourcePlan
                            + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + "=" + vPosition.get(0);

                    Vector<OutSourcePlanLocation> listSourcePlanLocation = PstOutSourcePlanLocation.listJoin(0, 1, whereLocSelected, "");
                    OutSourcePlanLocation outPlanLoc = listSourcePlanLocation != null && listSourcePlanLocation.size() > 0 ? listSourcePlanLocation.get(0) : null;

                    String whereCostSelected = " co." + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID] + "=" + oidOutMasterCost
                            + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID] + "=" + oidOutSourcePlan
                            + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + "=" + vPosition.get(0);
                    Vector<OutSourcePlanCost> listSourcePlanCost = PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, "");
                    if (listSourcePlanCost != null && listSourcePlanCost.size() > 0) {
                        for (int j = 0; j < 1 /*listSourcePlanCost.size()*/; j++) {

                            OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) listSourcePlanCost.get(j);
                            try {
                                float incToPrevYear = this.getParamFloat(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR] + "-" + entOutSourcePlanCost.getOutSourcePlanLocId() + "-" + oidOutMasterCost);
                                hPosIncrease.put("" + vPosition.get(0), incToPrevYear);
                            } catch (Exception exc) {
                            }

                        }
                    } else {
                        try {
                            float incToPrevYear = this.getParamFloat(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_INCRSTOPREVYEAR] + "-" + outPlanLoc.getOID() + "-" + oidOutMasterCost);
                            hPosIncrease.put("" + vPosition.get(0), incToPrevYear);
                        } catch (Exception exc) {
                        }
                    }
                }

                //Vector vDivisionHeader = (Vector) listSourcePlanLocationDivision.get(1);
                OutSourcePlan outSourcePlan = null;
                try{
                    outSourcePlan = PstOutSourcePlan.fetchExc(oidOutSourcePlan);
                  }catch(Exception exc){
                      System.out.println(exc);
                      return null;
                  }
                Date startDate = new Date(outSourcePlan.getPlanYear() - 1900-1, 0, 1);
                Date endDate = new Date(outSourcePlan.getPlanYear() - 1900-1, 11, 31);

                Hashtable<String, OutSourceCostProvDetailAvrg> hCostAvrg = PstOutsrcCostProvDetail.listCostDetailByDivPos(startDate, endDate, 0, 0, oidOutMasterCost, I_DocStatus.DOCUMENT_STATUS_FINAL);

                for (int i = 0; i < listSourcePlanLocationDivision.size(); i++) {
                    Vector vDivision = (Vector) listSourcePlanLocationDivision.get(i);

                    for (int k = 0; k < listSourcePlanLocationPosition.size(); k++) {
                        Vector vPosition = (Vector) listSourcePlanLocationPosition.get(k);

                        String whereLocSelected = " ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID] + "=" + oidOutSourcePlan
                                + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + "=" + vDivision.get(0)
                                + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + "=" + vPosition.get(0);

                        Vector<OutSourcePlanLocation> listSourcePlanLocation = PstOutSourcePlanLocation.listJoin(0, 1, whereLocSelected, "");
                        OutSourcePlanLocation outPlanLoc = listSourcePlanLocation != null && listSourcePlanLocation.size() > 0 ? listSourcePlanLocation.get(0) : null;

                        String whereCostSelected = " co." + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID] + "=" + oidOutMasterCost
                                + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID] + "=" + oidOutSourcePlan
                                + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + "=" + vDivision.get(0)
                                + " AND ho." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID] + "=" + vPosition.get(0);
                        Vector<OutSourcePlanCost> listSourcePlanCost = PstOutSourcePlanCost.listJoin(0, 0, whereCostSelected, "");
                        if (listSourcePlanCost != null && listSourcePlanCost.size() > 0) {
                            for (int j = 0; j < 1 /*listSourcePlanCost.size()*/; j++) {
                                try {
                                    OutSourcePlanCost entOutSourcePlanCost = (OutSourcePlanCost) listSourcePlanCost.get(j);
                                    try {
                                        float avrgCost = this.getParamFloat(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST] + "-" + entOutSourcePlanCost.getOutSourcePlanLocId() + "-" + oidOutMasterCost);
                                        OutSourceCostProvDetailAvrg preCost= hCostAvrg.get(""+vDivision.get(0)+"_"+vPosition.get(0));
                                        float prevYearCost = preCost!=null? preCost.getAverageCost() : 0;
                                        if (hPosIncrease != null) {
                                            Float increase = hPosIncrease.get("" + vPosition.get(0));
                                            if (increase != null) {
                                                if (prevYearCost != 0 && increase.floatValue() != 0) {
                                                    avrgCost = prevYearCost * (1 + (increase / 100));
                                                }
                                                entOutSourcePlanCost.setIncrsToPrevYear(increase.floatValue());
                                            }
                                        }
                                        entOutSourcePlanCost.setPlanAvrgCost(avrgCost==0 && prevYearCost!=0 ? prevYearCost : avrgCost);
                                        vLocPosCost.add(entOutSourcePlanCost);
                                    } catch (Exception exc) {
                                        System.out.println(exc);
                                    }
                                } catch (Exception exc) {
                                    System.out.println(exc);
                                }
                            }
                        } else {
                            try {
                                float avrgCost = this.getParamFloat(PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_PLANAVRGCOST] + "-" + outPlanLoc.getOID() + "-" + oidOutMasterCost);
                                
                                OutSourceCostProvDetailAvrg preCost= hCostAvrg.get(""+vDivision.get(0)+"_"+vPosition.get(0));
                                float prevYearCost = preCost!=null? preCost.getAverageCost() : 0;
                                                                      
                                OutSourcePlanCost oCost = new OutSourcePlanCost();
                                if (hPosIncrease != null) {
                                    Float increase = hPosIncrease.get("" + vPosition.get(0));
                                    if (increase != null) {
                                        if (prevYearCost!=0 && increase.floatValue() != 0) {
                                            avrgCost = prevYearCost * (1 + (increase / 100));
                                        }
                                        oCost.setIncrsToPrevYear(increase.floatValue());
                                    }
                                }
                                oCost.setOutSourceCostId(oidOutMasterCost);
                                oCost.setOutSourcePlanLocId(outPlanLoc.getOID());
                                oCost.setPlanAvrgCost(avrgCost==0 && prevYearCost!=0 ? prevYearCost : avrgCost);
                                vLocPosCost.add(oCost);

                            } catch (Exception exc) {
                                System.out.println(exc);
                            }
                        }
                    }

                }
                return vLocPosCost;
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
        return null;
    }

}
