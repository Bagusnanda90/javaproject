/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.admin;

/**
 *
 * @author Gunadi
 */
import com.dimata.harisma.entity.admin.Machine;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmMachine extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Machine entMachine;
    public static final String FRM_NAME_MACHINE = "FRM_NAME_MACHINE";
    public static final int FRM_FIELD_MACHINE_NAME = 0;
    public static final int FRM_FIELD_MACHINE_IP = 1;
    public static final int FRM_FIELD_MACHINE_PORT = 2;
    public static final int FRM_FIELD_MACHINE_COM_KEY = 3;
    public static final int FRM_FIELD_MACHINE_ID = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_MACHINE_NAME",
        "FRM_FIELD_MACHINE_IP",
        "FRM_FIELD_MACHINE_PORT",
        "FRM_FIELD_MACHINE_COM_KEY",
        "FRM_FIELD_MACHINE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmMachine() {
    }

    public FrmMachine(Machine entMachine) {
        this.entMachine = entMachine;
    }

    public FrmMachine(HttpServletRequest request, Machine entMachine) {
        super(new FrmMachine(entMachine), request);
        this.entMachine = entMachine;
    }

    public String getFormName() {
        return FRM_NAME_MACHINE;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public Machine getEntityObject() {
        return entMachine;
    }

    public void requestEntityObject(Machine entMachine) {
        try {
            this.requestParam();
            entMachine.setMachineName(getString(FRM_FIELD_MACHINE_NAME));
            entMachine.setMachineIP(getString(FRM_FIELD_MACHINE_IP));
            entMachine.setMachinePort(getInt(FRM_FIELD_MACHINE_PORT));
            entMachine.setMachineComKey(getInt(FRM_FIELD_MACHINE_COM_KEY));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}