
/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: Company
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Wiweka
 */

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Company extends Entity {
    private String company = "";
    private String description = "";
    private String codeCompany="";
    private long companyParentsId;

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        if ( company == null ) {
		company = "";
		}
        this.company = company;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        if ( description == null ) {
			description = "";
		} 
        this.description = description;
    }

    /**
     * @return the codeCompany
     */
    public String getCodeCompany() {
        if(codeCompany==null){
            return "";
        }
        return codeCompany;
    }

    /**
     * @param codeCompany the codeCompany to set
     */
    public void setCodeCompany(String codeCompany) {
        this.codeCompany = codeCompany;
    }

    /**
     * @return the companyParentsId
     */
    public long getCompanyParentsId() {
        return companyParentsId;
    }

    /**
     * @param companyParentsId the companyParentsId to set
     */
    public void setCompanyParentsId(long companyParentsId) {
        this.companyParentsId = companyParentsId;
    }

}
