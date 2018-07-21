/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee;

import java.util.Vector;
        
/**
 *
 * @author Ketut Kartika T
 * @date
 * @description
 */
public class FamilyMemberList {
    private FamilyMember spouse;
    private Vector children;

    public FamilyMember getSpouse() {
        return spouse;
    }

    public void setSpouse(FamilyMember spouse) {
        this.spouse = spouse;
    }

    public FamilyMember getChild(int idx) {
        if(children!=null && children.size()>0 && children.size()> idx){
            return (FamilyMember) children.get(idx);
        } else {
            return null;
        }
            
    }
    
    /**
     * 
     * @param child
     * @return  -1 in case of error or  index of object inserted 
     */
    public int addChild(FamilyMember child) {
        if(child==null){
            return -1;
        }
        if(children==null){
           children= new Vector();
        } 
        children.add(child);
        return children.size()-1;
    }
    
}
