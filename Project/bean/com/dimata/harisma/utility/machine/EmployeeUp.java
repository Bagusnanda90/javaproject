/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

/**
 *
 * @author Administrator
 */
public class EmployeeUp {
    /**
     * U..U\P..P\MXSSNNNNNNNNNN
     * U..U, USER ID Barcode MAKS 10 DIGIT
     * P..P, USER hex ID 6 DIGIT atau finger ID.  Urutkan dari terkecil hingga terbesar saat upload
     * M, 'C'=Card only, ' F'=Finger, 'S'=Supervisor  
     * X=Flag -> '1' ada SMS, '0' tnp SMS. S=Verifikasi akses -> bila tidak digunakan ganti dengan kode '@'  
     * N..N, Nama User maks 10 digit
     */
    private String barcode = "";
    private String userHexId = "";
    private String password = "";
    private String code = "";
    private String flag = "";
    private String name = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserHexId() {
        return userHexId;
    }

    public void setUserHexId(String userHexId) {
        this.userHexId = userHexId;
    }
    
    public void setUserHexId(int id){
        setUserHexId(Integer.toHexString(id));
    }
    
}
