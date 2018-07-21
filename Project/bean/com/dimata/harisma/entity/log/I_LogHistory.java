/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.log;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author AGUS
 */
public interface I_LogHistory {
    /**
     * get detail perubahan dokumen/data yg dibandingkan dengan dokument/data sebelumnya
     * @param prevDoc
     * @return : String dari data yang berubah saja
     */
    public String getLogDetail(Entity prevDoc);
    
    public static final int SYSTEM_AISO = 0;
    public static final int SYSTEM_DRS = 1;
    public static final int SYSTEM_HAIRISMA = 2;
    public static final int SYSTEM_HANOMAN = 3;
    public static final int SYSTEM_LOGBOOK = 4;
    public static final int SYSTEM_PROCHAIN = 5;
    public static final int SYSTEM_SARAS = 6;
    public static final int SYSTEM_TRAVEL = 7;
    
    public static final String[] SYSTEM_NAME =  {
        "AISO", "DRS", "Hairisma", "Hanoman", "Logbook", "Prochain",  "Saras", "Travel"
    };
    
    
}

