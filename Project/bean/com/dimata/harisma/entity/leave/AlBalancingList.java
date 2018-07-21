/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;
import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class AlBalancingList {
    
    private long    employee_id;
    private long    al_upload_id;
    private int     emp_to_clear_last_period;
    private int     emp_taken_curr_period;
    private int     data_status;        
    private boolean is_proces;
    private Date    opname_date;
    private int     emp_earned_curr_period;
    private int     emp_earned_last_period;
    private int     emp_al_qty_new;
    
    
}
