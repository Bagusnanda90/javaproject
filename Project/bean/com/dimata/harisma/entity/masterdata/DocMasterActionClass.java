/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class DocMasterActionClass {
    

       //update by priska
    public static final int[] actionValue = {0,1,2,3};    
    public static final String[] actionKey = {"Mutation","Update Gaji Employee","Update Employee","Mutation to All"};
    
    
    
    public static final String[][] actionListParameterKey = {
        {"Employee to Mutation","New Company","New Division","New Department","New Emp Cat","New Level","New Position","New Section","New Work to"},
        {"Employee to Update Salary Level","New Salary Level","Start Date Salary Level", "End Date Salary Level"},
        {"Employee to Update Databank","New Company","New Division","New Department","New Emp Cat","New Level","New Position","New Section","New Resign Date"},
        {"Employee to Mutation"}
            
    };
    
    public static final String[][] actionListParameterAttribut = {
        {"All"},
        {"All"},
        {"All"},
        {"All"}
    };
    
    public static int getIndexActionValue(String key){
        int index = -1;
        for (int i= 0; i< actionKey.length; i++){
            String name = (String.valueOf(actionKey[i]));
            if (key.equals(name)){
                return i;
            }
        }
        return index;
    }
    public static Vector getActionValue(){
        Vector action = new Vector();
        for (int i= 0; i< actionValue.length; i++){
            action.add(String.valueOf(actionValue[i]));
        }
        return action;
    }
    
    public static Vector getActionKey(){
        Vector action = new Vector();
         action.add("-");
        for (int i = 0; i < actionKey.length; i++ ){
            action.add(actionKey[i]);
        }
        return action;
    }       
    
      public static Vector getActionListParameterKey(){
        Vector actionListParameterV = new Vector();
        for (int i = 0; i < actionListParameterKey.length; i++ ){
            actionListParameterV.add(actionListParameterKey[i]);
        }
        return actionListParameterV;
    }  
      
      public static Vector getVectorfromArray(String[] array){
        Vector vectorfromArray = new Vector();
        for (int i = 0; i < array.length; i++ ){
            vectorfromArray.add(array[i]);
        }
        return vectorfromArray;
    }   
    
}
