package com.dimata.harisma.form.search;


import java.util.*;
import javax.servlet.http.*;

import com.dimata.qdep.form.*;

import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class FrmSrcEmpRace extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    public static final String FRM_NAME_SRC_EMP_RACE    =   "FRM_SRC_EMP_RACE";
    
    public static final int FRM_FIELD_DEPT_ID     =   0;
    public static final int FRM_FIELD_SECT_ID        =   1;
    
    public static String[] fieldNames = {
        "FRM_FIELD_DEPT_ID",
        "FRM_FIELD_SECT_ID"
    };
    
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG
    };
    
    private SrcEmpRace srcEmpRace;
    
    
    public FrmSrcEmpRace(SrcEmpRace srcEmpRace) {
        this.srcEmpRace = srcEmpRace;
    }
    
    public FrmSrcEmpRace(HttpServletRequest request, SrcEmpRace srcEmpRace) {
        super(new FrmSrcEmpRace(srcEmpRace), request);
        this.srcEmpRace = srcEmpRace;
    }

    
    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_SRC_EMP_RACE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public SrcEmpRace getEntityObject() {
        return srcEmpRace;
    }
    
    public void requestEntityObject(SrcEmpRace srcEmpRace) {
        this.requestParam();
        
        srcEmpRace.setDepartmentId(this.getLong(FRM_FIELD_DEPT_ID));
        srcEmpRace.setSectionId(this.getLong(FRM_FIELD_SECT_ID));
        
        try {
            Department dept = PstDepartment.fetchExc(srcEmpRace.getDepartmentId());
            srcEmpRace.setDepartment(dept.getDepartment());
        }
        catch(Exception e) {}
        
        try {
            Section sect = PstSection.fetchExc(srcEmpRace.getSectionId());
            srcEmpRace.setSection(sect.getSection());
        }
        catch(Exception e) {}
        
    }
    
}
