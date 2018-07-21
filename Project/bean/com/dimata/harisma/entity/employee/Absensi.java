/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee;
import com.dimata.qdep.entity.*;
import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class Absensi extends Entity { 
    private String trans_kode;
    private String trans_pengenal;
    private Date trans_tanggal;
    private Date trans_jam;
    private String trans_status;
    private String trans_mesin;
    private String trans_falg;

    public String getTrans_kode() {
        return trans_kode;
    }

    public void setTrans_kode(String trans_kode) {
        this.trans_kode = trans_kode;
    }

    public String getTrans_pengenal() {
        return trans_pengenal;
    }

    public void setTrans_pengenal(String trans_pengenal) {
        this.trans_pengenal = trans_pengenal;
    }

    public Date getTrans_tanggal() {
        return trans_tanggal;
    }

    public void setTrans_tanggal(Date trans_tanggal) {
        this.trans_tanggal = trans_tanggal;
    }

    public Date getTrans_jam() {
        return trans_jam;
    }

    public void setTrans_jam(Date trans_jam) {
        this.trans_jam = trans_jam;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_mesin() {
        return trans_mesin;
    }

    public void setTrans_mesin(String trans_mesin) {
        this.trans_mesin = trans_mesin;
    }

    public String getTrans_falg() {
        return trans_falg;
    }

    public void setTrans_falg(String trans_falg) {
        this.trans_falg = trans_falg;
    }   
}
