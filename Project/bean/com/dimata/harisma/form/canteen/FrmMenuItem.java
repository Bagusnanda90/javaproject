/* 
 * Form Name  	:  FrmMenuItem.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.canteen;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.canteen.*;

public class FrmMenuItem extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MenuItem menuItem;

	public static final String FRM_NAME_MENUITEM		=  "FRM_NAME_MENUITEM" ;

	public static final int FRM_FIELD_MENU_ITEM_ID			=  0 ;
	public static final int FRM_FIELD_ITEM_NAME			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MENU_ITEM_ID",  "FRM_FIELD_ITEM_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmMenuItem(){
	}
	public FrmMenuItem(MenuItem menuItem){
		this.menuItem = menuItem;
	}

	public FrmMenuItem(HttpServletRequest request, MenuItem menuItem){
		super(new FrmMenuItem(menuItem), request);
		this.menuItem = menuItem;
	}

	public String getFormName() { return FRM_NAME_MENUITEM; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MenuItem getEntityObject(){ return menuItem; }

	public void requestEntityObject(MenuItem menuItem) {
		try{
			this.requestParam();
			menuItem.setItemName(getString(FRM_FIELD_ITEM_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
