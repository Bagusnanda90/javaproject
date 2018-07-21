/* 
 * Form Name  	:  FrmMenuList.java 
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

public class FrmMenuList extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MenuList menuList;

	public static final String FRM_NAME_MENULIST		=  "FRM_NAME_MENULIST" ;

	public static final int FRM_FIELD_MENU_LIST_ID			=  0 ;
	public static final int FRM_FIELD_MENU_ITEM_ID			=  1 ;
	public static final int FRM_FIELD_MENU_DATE			=  2 ;
	public static final int FRM_FIELD_MENU_TIME			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MENU_LIST_ID",  "FRM_FIELD_MENU_ITEM_ID",
		"FRM_FIELD_MENU_DATE",  "FRM_FIELD_MENU_TIME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmMenuList(){
	}
	public FrmMenuList(MenuList menuList){
		this.menuList = menuList;
	}

	public FrmMenuList(HttpServletRequest request, MenuList menuList){
		super(new FrmMenuList(menuList), request);
		this.menuList = menuList;
	}

	public String getFormName() { return FRM_NAME_MENULIST; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MenuList getEntityObject(){ return menuList; }

	public void requestEntityObject(MenuList menuList) {
		try{
			this.requestParam();
			menuList.setMenuItemId(getLong(FRM_FIELD_MENU_ITEM_ID));
			menuList.setMenuDate(getDate(FRM_FIELD_MENU_DATE));
			menuList.setMenuTime(getInt(FRM_FIELD_MENU_TIME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
