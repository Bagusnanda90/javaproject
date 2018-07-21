
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.clinic; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class MedicineConsumption extends Entity { 

	private long medicineId;
	private Date month;
	private int lastMonth;
	private int purchaseThisMonth;
	private int stockThisMonth;
	private int consumpThisMonth;
	private double totalConsump;
	private int closeInventory;
	private double closeAmount;

	public long getMedicineId(){ 
		return medicineId; 
	} 

	public void setMedicineId(long medicineId){ 
		this.medicineId = medicineId; 
	} 

	public Date getMonth(){ 
		return month; 
	} 

	public void setMonth(Date month){ 
		this.month = month; 
	} 

	public int getLastMonth(){ 
		return lastMonth; 
	} 

	public void setLastMonth(int lastMonth){ 
		this.lastMonth = lastMonth; 
	} 

	public int getPurchaseThisMonth(){ 
		return purchaseThisMonth; 
	} 

	public void setPurchaseThisMonth(int purchaseThisMonth){ 
		this.purchaseThisMonth = purchaseThisMonth; 
	} 

	public int getStockThisMonth(){ 
		return stockThisMonth; 
	} 

	public void setStockThisMonth(int stockThisMonth){ 
		this.stockThisMonth = stockThisMonth; 
	} 

	public int getConsumpThisMonth(){ 
		return consumpThisMonth; 
	} 

	public void setConsumpThisMonth(int consumpThisMonth){ 
		this.consumpThisMonth = consumpThisMonth; 
	} 

	public double getTotalConsump(){ 
		return totalConsump; 
	} 

	public void setTotalConsump(double totalConsump){ 
		this.totalConsump = totalConsump; 
	} 

	public int getCloseInventory(){ 
		return closeInventory; 
	} 

	public void setCloseInventory(int closeInventory){ 
		this.closeInventory = closeInventory; 
	} 

	public double getCloseAmount(){ 
		return closeAmount; 
	} 

	public void setCloseAmount(double closeAmount){ 
		this.closeAmount = closeAmount; 
	} 

}
