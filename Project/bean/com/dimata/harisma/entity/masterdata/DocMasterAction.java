/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class DocMasterAction extends Entity {

private long docMasterId = 0;
private String actionName = "";
private String actionTitle = "";



public long getDocMasterId(){
return docMasterId;
}

public void setDocMasterId(long docMasterId){
this.docMasterId = docMasterId;
}

public String getActionName(){
return actionName;
}

public void setActionName(String actionName){
this.actionName = actionName;
}

public String getActionTitle(){
return actionTitle;
}

public void setActionTitle(String actionTitle){
this.actionTitle = actionTitle;
}

}