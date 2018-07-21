
package com.dimata.harisma.form.masterdata.payday;

// import java
import com.dimata.harisma.form.masterdata.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

// import qdep
import com.dimata.qdep.form.*;

// import harisma
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.payday.PayDay;

/**
 *
 * @author bayu
 */

public class FrmPayDay extends FRMHandler implements I_FRMInterface, I_FRMType {

    public static final String FRM_NAME_PAY_DAY       =   "FRM_PAY_DAY";
    
    public static final int FRM_PAY_DAY_ID = 0;
    public static final int FRM_EMP_CATEGORY_ID = 1;
    public static final int FRM_POSITION_ID = 2;
    public static final int FRM_VALUE_PAY_DAY = 3;
    public static String[] fieldNames = {
        "FRM_PAY_DAY_ID",
        "FRM_EMP_CATEGORY_ID",
        "FRM_POSITION_ID",
        "FRM_VALUE_PAY_DAY",
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG,
        TYPE_FLOAT
    };

  
    
    private PayDay payDay;
    
    
    public FrmPayDay() {
    }
    
    public FrmPayDay(PayDay payDay) {
        this.payDay = payDay;
    }
    
    public FrmPayDay(HttpServletRequest request, PayDay payDay) {
        super(new FrmPayDay(payDay), request);
        this.payDay = payDay;
    }

    
    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_PAY_DAY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    
    public PayDay getEntityObject() {
        return payDay;
    }
    
    public void requestEntityObject(PayDay payDay) {
        try {
            this.requestParam();
            payDay.setEmpCategoryId(this.getLong(FRM_EMP_CATEGORY_ID));
            payDay.setPositionId(this.getLong(FRM_POSITION_ID));
            payDay.setValuePayDay(this.getDouble(FRM_VALUE_PAY_DAY));
            payDay.setOID(this.getLong(FRM_PAY_DAY_ID));
            
        }
        catch(Exception e) {}
    }
    
}
