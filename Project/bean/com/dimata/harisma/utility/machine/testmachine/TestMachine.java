/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine.testmachine;

/**
 *
 * @author RAMA
 */
public class TestMachine {
    private String namaOdbc;
    private String Dns;
    private String user;
    private String password;
    private int codeExsample;
    private String sampleQuery;
    private int typeOdbc;
    private boolean useLimit;
    private int useLimitValue;
    private int totalRecordQuery;

    /**
     * @return the namaOdbc
     */
    public String getNamaOdbc() {
        if(namaOdbc==null){
            return "ex: AttFoxProAnantaraTransfer";
        }
        return namaOdbc;
    }

    /**
     * @param namaOdbc the namaOdbc to set
     */
    public void setNamaOdbc(String namaOdbc) {
        this.namaOdbc = namaOdbc;
    }

    /**
     * @return the Dns
     */
    public String getDns() {
        if(Dns==null){
             return "ex mysql: localhost:3306//harisma_anantara,ex dbf letak database,ex. mdb nama odbc";
        }
        return Dns;
    }

    /**
     * @param Dns the Dns to set
     */
    public void setDns(String Dns) {
        this.Dns = Dns;
    }

    /**
     * @return the user
     */
    public String getUser() {
        if(user==null){
            return "";
        }
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        if(password==null){
            return "";
        }
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the codeExsample
     */
    public int getCodeExsample() {
        
        return codeExsample;
    }

    /**
     * @param codeExsample the codeExsample to set
     */
    public void setCodeExsample(int codeExsample) {
        this.codeExsample = codeExsample;
    }

    /**
     * @return the sampleQuery
     */
    public String getSampleQuery() {
        String query ="";
          if(sampleQuery==null){
            return query;
        }else{
              String replace1="";
             String tmpReplace1="";
            // String replace11="";
             //String tmpReplace11="";
              query =sampleQuery;
              replace1 = query.replace("petik.", "'");
              
              tmpReplace1 = replace1;
              String replace2=  tmpReplace1.replace(".petik", "'");
              String replace11 =  replace2.replace("&lt", "<");
              String tmpReplace2 = replace11.replace("&gt", ">");
               return tmpReplace2;
          }
       
    }

    /**
     * @param sampleQuery the sampleQuery to set
     */
    public void setSampleQuery(String sampleQuery) {
        this.sampleQuery = sampleQuery;
    }

    /**
     * @return the typeOdbc
     */
    public int getTypeOdbc() {
        return typeOdbc;
    }

    /**
     * @param typeOdbc the typeOdbc to set
     */
    public void setTypeOdbc(int typeOdbc) {
        this.typeOdbc = typeOdbc;
    }

    /**
     * @return the useLimit
     */
    public boolean isUseLimit() {
        return useLimit;
    }

    /**
     * @param useLimit the useLimit to set
     */
    public void setUseLimit(boolean useLimit) {
        this.useLimit = useLimit;
    }

    /**
     * @return the useLimitValue
     */
    public int getUseLimitValue() {
        if(useLimitValue==0){
            return 100;
        }
        return useLimitValue;
    }

    /**
     * @param useLimitValue the useLimitValue to set
     */
    public void setUseLimitValue(int useLimitValue) {
        
        this.useLimitValue = useLimitValue;
    }

    /**
     * @return the totalRecordQuery
     */
    public int getTotalRecordQuery() {
        return totalRecordQuery;
    }

    /**
     * @param totalRecordQuery the totalRecordQuery to set
     */
    public void setTotalRecordQuery(int totalRecordQuery) {
        this.totalRecordQuery = totalRecordQuery;
    }
}
