/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author GUSWIK
 */
public class PayComponentDecimalFormat {
   
        
    public static double getDecimalFormat(int typeOfDecimalFormat, double value) {
        double resultDecimalFormat = value ;
        try {
            switch(typeOfDecimalFormat){
            case 0:
                break;
            case 1:
                resultDecimalFormat = customFormat("###.#", value);
                break;
            case 2:
                resultDecimalFormat = customFormat("###.##", value);
                break; 
            case 3:
                resultDecimalFormat = customFormat("###.###", value);
                break;
            case 4:
                resultDecimalFormat = convertInteger(0, value);
                break;
            case 5:
                resultDecimalFormat = convertInteger(-1, value);
                break;
            case 6:
                resultDecimalFormat = convertInteger(-2, value);
                break; 
            case 7:
                resultDecimalFormat = convertInteger(-3, value);
                break;
        }
            return resultDecimalFormat;
        } catch (Exception e) {
            return 0;
        } 
    }
    
        /* Decimal Format */
    public static double customFormat(String pattern, double value ) {
        double outDouble = 0;
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        outDouble = Double.valueOf(output);
        return outDouble;
   }
    
    /* Convert int */
    public static int convertInteger(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bDecimal.intValue();
    }
}
