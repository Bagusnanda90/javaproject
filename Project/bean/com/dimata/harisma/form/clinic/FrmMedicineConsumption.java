/* 
 * Form Name  	:  FrmMedicineConsumption.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.clinic;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.clinic.*;

public class FrmMedicineConsumption extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MedicineConsumption medicineConsumption;

	public static final String FRM_NAME_MEDICINECONSUMPTION		=  "FRM_NAME_MEDICINECONSUMPTION" ;

	public static final int FRM_FIELD_MEDICINE_CONSUMPTION_ID			=  0 ;
	public static final int FRM_FIELD_MEDICINE_ID			=  1 ;
	public static final int FRM_FIELD_MONTH			=  2 ;
	public static final int FRM_FIELD_LAST_MONTH			=  3 ;
	public static final int FRM_FIELD_PURCHASE_THIS_MONTH			=  4 ;
	public static final int FRM_FIELD_STOCK_THIS_MONTH			=  5 ;
	public static final int FRM_FIELD_CONSUMP_THIS_MONTH			=  6 ;
	public static final int FRM_FIELD_TOTAL_CONSUMP			=  7 ;
	public static final int FRM_FIELD_CLOSE_INVENTORY			=  8 ;
	public static final int FRM_FIELD_CLOSE_AMOUNT			=  9 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICINE_CONSUMPTION_ID",  "FRM_FIELD_MEDICINE_ID",
		"FRM_FIELD_MONTH",  "FRM_FIELD_LAST_MONTH",
		"FRM_FIELD_PURCHASE_THIS_MONTH",  "FRM_FIELD_STOCK_THIS_MONTH",
		"FRM_FIELD_CONSUMP_THIS_MONTH",  "FRM_FIELD_TOTAL_CONSUMP",
		"FRM_FIELD_CLOSE_INVENTORY",  "FRM_FIELD_CLOSE_AMOUNT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED,
		TYPE_INT,  TYPE_INT + ENTRY_REQUIRED,
		TYPE_INT,  TYPE_FLOAT,
		TYPE_INT + ENTRY_REQUIRED,  TYPE_FLOAT + ENTRY_REQUIRED
	} ;

	public FrmMedicineConsumption(){
	}
	public FrmMedicineConsumption(MedicineConsumption medicineConsumption){
		this.medicineConsumption = medicineConsumption;
	}

	public FrmMedicineConsumption(HttpServletRequest request, MedicineConsumption medicineConsumption){
		super(new FrmMedicineConsumption(medicineConsumption), request);
		this.medicineConsumption = medicineConsumption;
	}

	public String getFormName() { return FRM_NAME_MEDICINECONSUMPTION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicineConsumption getEntityObject(){ return medicineConsumption; }

	public void requestEntityObject(MedicineConsumption medicineConsumption) {
		try{
			this.requestParam();
			medicineConsumption.setMedicineId(getLong(FRM_FIELD_MEDICINE_ID));
			medicineConsumption.setMonth(getDate(FRM_FIELD_MONTH));
			medicineConsumption.setLastMonth(getInt(FRM_FIELD_LAST_MONTH));
			medicineConsumption.setPurchaseThisMonth(getInt(FRM_FIELD_PURCHASE_THIS_MONTH));
			medicineConsumption.setStockThisMonth(getInt(FRM_FIELD_STOCK_THIS_MONTH));
			medicineConsumption.setConsumpThisMonth(getInt(FRM_FIELD_CONSUMP_THIS_MONTH));
			medicineConsumption.setTotalConsump(getDouble(FRM_FIELD_TOTAL_CONSUMP));
			medicineConsumption.setCloseInventory(getInt(FRM_FIELD_CLOSE_INVENTORY));
			medicineConsumption.setCloseAmount(getDouble(FRM_FIELD_CLOSE_AMOUNT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
