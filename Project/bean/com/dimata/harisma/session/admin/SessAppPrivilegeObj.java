/*
 * SessAppPrivilegeObj.java
 *
 * Created on April 11, 2002, 1:15 PM
 */

package com.dimata.harisma.session.admin;
import java.util.*;
import com.dimata.harisma.entity.admin.*;

//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;


/**
 *
 * @author  ktanjana
 * @version 
 */
public class SessAppPrivilegeObj {

    /** Creates new SessAppPrivilegeObj */
    public SessAppPrivilegeObj() {
    }

    public static boolean existCode(Vector codes, int code){
//        System.out.println("codes..= "+codes.size());
        if((codes==null) || (codes.size()<1))
            return false;

        for(int i=0; i<codes.size();i++){
            if(code== ( (Integer) codes.get(i)).intValue() )
                return true;
        }
        
        return false;
        
    }   
    
    public static boolean existCodeG1G2(Vector codes, int codeG1G2){
        //System.out.println("codes..= "+codes.size());
        if((codes==null) || (codes.size()<1))
            return false;

        for(int i=0; i<codes.size();i++){
            if((codeG1G2 & (AppObjInfo.FILTER_CODE_G1 + AppObjInfo.FILTER_CODE_G2)) == ( ( (Integer) codes.get(i)).intValue()& (AppObjInfo.FILTER_CODE_G1 + AppObjInfo.FILTER_CODE_G2) ))
                return true;
        }
        
        return false;
        
    }       
}
