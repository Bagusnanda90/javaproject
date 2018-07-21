/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class KPI_List extends Entity{
    private long kpi_list_id ;
    private long company_id ;
    private String kpi_title = "";
    private String description = "";
    private Date valid_from ;
    private Date valid_to ;  
    private String value_type;
    private Vector<Long> arrkpigroup = new Vector();

    /**
     * @return the kpi_list_id
     */
    public long getKpi_list_id() {
        return kpi_list_id;
    }

    /**
     * @param kpi_list_id the kpi_list_id to set
     */
    public void setKpi_list_id(long kpi_list_id) {
        this.kpi_list_id = kpi_list_id;
    }

    /**
     * @return the company_id
     */
    public long getCompany_id() {
        return company_id;
    }

    /**
     * @param company_id the company_id to set
     */
    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    /**
     * @return the kpi_title
     */
    public String getKpi_title() {
        return kpi_title;
    }

    /**
     * @param kpi_title the kpi_title to set
     */
    public void setKpi_title(String kpi_title) {
        this.kpi_title = kpi_title;
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
        this.description = description;
    }

    /**
     * @return the valid_from
     */
    public Date getValid_from() {
        return valid_from;
    }

    /**
     * @param valid_from the valid_from to set
     */
    public void setValid_from(Date valid_from) {
        this.valid_from = valid_from;
    }

    /**
     * @return the valid_to
     */
    public Date getValid_to() {
        return valid_to;
    }

    /**
     * @param valid_to the valid_to to set
     */
    public void setValid_to(Date valid_to) {
        this.valid_to = valid_to;
    }

    /**
     * @return the value_type
     */
    public String getValue_type() {
        return value_type;
    }

    /**
     * @param value_type the value_type to set
     */
    public void setValue_type(String value_type) {
        this.value_type = value_type;
    }

   
    public int getArrkpigroupSize() {
        return arrkpigroup==null ? 0 : arrkpigroup.size();
    }

/**
     * @return the arrMaritalId
     */
    public Long getArrkpigroup(int idx) {
        //return arrMaritalId;
        
         if((arrkpigroup==null)||(idx>=arrkpigroup.size())){
            return null;
        }
         return arrkpigroup.get(idx);
    }

    /**
     * @param arrMaritalId the arrMaritalId to set
     */
    public void addArrkpigroup(String[] arrkpigroup) {
        //this.arrMaritalId = arrMaritalId;
        if(arrkpigroup!=null){
            for(int i=0; i <  arrkpigroup.length ; i++){
                try{
                this.arrkpigroup.add(new Long( arrkpigroup[i]));
                }catch(Exception e){
                    
                }
            }
            
        }
    }
    }