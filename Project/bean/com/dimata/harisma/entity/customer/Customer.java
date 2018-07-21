/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.customer;

import com.dimata.qdep.entity.*;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gunk_tra
 */
public class Customer extends Entity {
    public final static int STATUS_ACEH =0;
    public final static int STATUS_BALI =1;
    public final static int STATUS_BANTEN =2;
    public final static int STATUS_BENGKULU =3;
    public final static int STATUS_GORONTALO =4;
    public final static int STATUS_JAKARTA =5;
    public final static int STATUS_JAMBI =6;
    public final static int STATUS_JAWA_BARAT =7;
    public final static int STATUS_JAWA_TENGAH =8;
    public final static int STATUS_JAWA_TIMUR =9;
    public final static int STATUS_KALIMANTAN_BARAT =10;
    public final static int STATUS_KALIMANTAN_SELATAN =11;
    public final static int STATUS_KALIMANTAN_TENGAH =12;
    public final static int STATUS_KALIMANTAN_TIMUR =13;
    public final static int STATUS_KALIMANTAN_UTARA =14;
    public final static int STATUS_KEP_BANGKA_BELITUNG =15;
    public final static int STATUS_KEP_RIAU =16;
    public final static int STATUS_LAMPUNG =17;
    public final static int STATUS_MALUKU =18;
    public final static int STATUS_MALUKU_UTARA =19;
    public final static int STATUS_NUSA_TENGGARA_BARAT =20;
    public final static int STATUS_NUSA_TENGGARA_TIMUR =21;
    public final static int STATUS_PAPUA =22;
    public final static int STATUS_PAPUA_BARAT =23;
    public final static int STATUS_RIAU =24;
    public final static int STATUS_SULAWESI_BARAT =25;
    public final static int STATUS_SULAWESI_SELATAN =26;
    public final static int STATUS_SULAWESI_TENGAH =27;
    public final static int STATUS_SULAWESI_TENGGARA =28;
    public final static int STATUS_SULAWESI_UTARA =29;
    public final static int STATUS_SUMATERA_BARAT =30;
    public final static int STATUS_SUMATERA_SELATAN =31;
    public final static int STATUS_SUMATERA_UTARA =32; 
    public final static int STATUS_YOGYAKARTA =33;   
    
    public final static String[] statusTxt= {"Aceh", "Bali", "Banten","Bengkulu", "Gorontalo", "DKI. Jakarta","Jambi", "Jawa Barat", "Jawa Tengah","Jawa Timur", 
"Kalimantan Barat", "Kalimantan Selatan","Kalimantan Tengah", "Kalimantan Timur", "Kalimantan Utara","Kep. Bangka Belitung", "Kep. Riau", "Lampung","Maluku", 
"Maluku Utara","Nusa Tenggara Barat","Nusa Tenggara Timur", "Papua", "Papua Barat","Riau", "Sulawesi Barat", "Sulawesi Selatan","Sulawesi Tengah", "Sulawesi Tenggara", 
"Sulawesi Utara","Sumatera Barat", "Sumatera Selatan", "Sumatera Utara","DIY.Yogyakarta"};
    
   
    private String customerName = "";
    private String address = "";
    private String city = "";
    private String postCode = "";
    private String province = "";
    private String country = "";
    private String phone1 = "";
    private String phone2 = "";
    private String phone3 = "";
    private String fax = "";
    private String email = "";
    private String personName = "";
    private String personPhone = "";
    private Date regDate;
    private Date updateDate;
    private String note = "";

    public Customer()  {
    }
    /**
     * @return the CustomerId
     */
    /*public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param CustomerId the CustomerId to set
     */
   /* public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the CustomersName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param CustomersName the CustomersName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    public static String getStatusTxt(int sts){
        if((sts<0) || (sts> statusTxt.length))
            return "";
        return statusTxt[sts];
    }
    
    public static Vector getStatusTxts(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < statusTxt.length ; i++){
            vct.add(statusTxt[i]);
        }
        return vct;
    }
        
    public static Vector getStatusVals(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < statusTxt.length ; i++){
            vct.add(Integer.toString(i));
        }
        return vct;
    }
    
    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }
    
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * @param phone1 the phone1 to set
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    /**
     * @return the phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * @param phone2 the phone2 to set
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     * @return the phone3
     */
    public String getPhone3() {
        return phone3;
    }

    /**
     * @param phone3 the phone3 to set
     */
    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * @return the personPhone
     */
    public String getPersonPhone() {
        return personPhone;
    }

    /**
     * @param personPhone the personPhone to set
     */
    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    /**
     * @return the regDate
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    
    
   
}
