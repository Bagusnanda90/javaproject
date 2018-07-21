/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.payroll;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class PayInputNote {
    private Date tglNotePayInput;
    private String note;

    /**
     * @return the tglNotePayInput
     */
    public Date getTglNotePayInput() {
        return tglNotePayInput;
    }

    /**
     * @param tglNotePayInput the tglNotePayInput to set
     */
    public void setTglNotePayInput(Date tglNotePayInput) {
        this.tglNotePayInput = tglNotePayInput;
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
