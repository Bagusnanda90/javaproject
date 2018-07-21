/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Wiweka
 */
/* package qdep */
import com.dimata.qdep.entity.Entity;

public class Negara extends Entity {

	private String benua = "";
	private String nmNegara = "";

    public String getBenua(){
		return benua;
	}

	public void setBenua(String benua){
		if ( benua == null ) {
			benua = "";
		}
		this.benua = benua;
	}

	public String getNmNegara(){
		return nmNegara;
	}

	public void setNmNegara(String nmNegara){
		if ( nmNegara == null ) {
			nmNegara = "";
		}
		this.nmNegara = nmNegara;
	}

}
