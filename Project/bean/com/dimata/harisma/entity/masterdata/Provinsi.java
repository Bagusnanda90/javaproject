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

public class Provinsi extends Entity {

	private String kdProvinsi = "";
	private String nmProvinsi = "";
        private long idNegara = 0;

    public String getKdProvinsi(){
		return kdProvinsi;
	}

	public void setKdProvinsi(String kdProvinsi){
		if ( kdProvinsi == null ) {
			kdProvinsi = "";
		}
		this.kdProvinsi = kdProvinsi;
	}

	public String getNmProvinsi(){
		return nmProvinsi;
	}

	public void setNmProvinsi(String nmProvinsi){
		if ( nmProvinsi == null ) {
			nmProvinsi = "";
		}
		this.nmProvinsi = nmProvinsi;
	}

    /**
     * @return the idNegara
     */
    public long getIdNegara() {
        return idNegara;
    }

    /**
     * @param idNegara the idNegara to set
     */
    public void setIdNegara(long idNegara) {
        this.idNegara = idNegara;
    }

}
