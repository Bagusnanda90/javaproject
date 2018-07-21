
package com.dimata.harisma.session.employee;

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.employee.*; 

/**
 *
 * @author bayu
 */

public class SessTraining {
    
   
    /*
     * get duration in minutes
     * based on string parameter. Hour and minutes values separated with .
     */
    
    public static int getTrainingDuration(String duration) {
        int result = 0;
        
        if(duration != null && duration.length() > 0) {
            StringTokenizer str = new StringTokenizer(duration, ".");
            
            try {
                if(str.countTokens() > 2 || str.countTokens() < 1) 
                    return 0;
                
                int hours = Integer.parseInt(str.nextToken());
                int minutes = 0;
                
                if(str.hasMoreElements()) {
                    String sMinute = str.nextToken();                
                
                    if(sMinute.trim().length() == 1)
                        minutes = Integer.parseInt(sMinute + "0");
                    else
                        minutes = Integer.parseInt(sMinute);

                    if(minutes > 60) {
                        hours += minutes / 60;
                        minutes = minutes % 60;
                    }   
                }
                
                return hours * 60 + minutes;
            }
            catch(Exception e) {
                return 0;
            }
        }
        
        return result;
    }
    
    /*
     * get duration in minutes
     * based on start and end date
     */
    
    public static int getTrainingDuration(Date startTime, Date endTime) 
    {
	int result = 0;
            
	if(startTime!=null && endTime!=null ) {
	
            if(startTime.compareTo(endTime) > 0)
                return 0;
                
            int startHour = startTime.getHours();
            int startMinute = startTime.getMinutes();

            int endHour = endTime.getHours();
            int endMinute = endTime.getMinutes();

            int hours = 0;
            int minutes = 0;

            hours = endHour - startHour;
            minutes = endMinute - startMinute;

            if(minutes < 0) {
                minutes = 60 + minutes;
                hours = hours - 1;
            }

            result = hours * 60 + minutes;         
	}
        		
	return result;
    }   
    
     /*
     * return hour and minutes values separated with .
     * based on parameter of duration value in minutes
     */
    
    public static String getDurationString(int duration) {
        
        int hour = duration / 60;
        int minutes = duration % 60;
        
        if(minutes == 0) 
            return String.valueOf(hour);
        else
            return hour + "." + ((minutes < 10)? "0":"") + minutes;
    }
    
    /*
     * get hour value
     * based on duration in minutes
     */
    
    public static int getHour(int duration) {
        if(duration > 60)
            return duration / 60;
        else
            return 0;
    }
    
     /*
     * get minutes value
     * based on duration in minutes
     */
    
    public static int getMinutes(int duration) {
        if(duration % 60 > 0)
            return duration % 60;
        else
            return 0;
    }
    
    public static Vector listTraining(long departmentId, Date date){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
                
		try {
                        GregorianCalendar calendar = new GregorianCalendar(date.getYear()+1900, date.getMonth(), date.getDate());
                    
                        String sql = " SELECT DISTINCT TP.* FROM " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TP" +
                                     " INNER JOIN " + PstTrainingSchedule.TBL_TRAIN_SCHEDULE + " TS" +
                                     " ON TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +
                                     " LEFT JOIN " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TA" +
                                     " ON TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " WHERE TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID] +
                                     " = " + departmentId + " AND TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] +
                                     " BETWEEN '" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DATE) +
                                     "' AND '" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "'" +
                                     " AND TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] +
                                     " IS NULL ";

                        System.out.println("training >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
            
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
                            Vector rowx = new Vector();
                            TrainingActivityPlan trainingactivityplan = new TrainingActivityPlan();
                            PstTrainingActivityPlan.resultToObject(rs, trainingactivityplan);

                            // training plan
                            rowx.add(trainingactivityplan);

                            // schedule detail          
                            Vector schedules = PstTrainingSchedule.listTrainingSchedule(trainingactivityplan.getOID());
                            rowx.add(schedules);
                            

                            lists.add(rowx);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
                
                return new Vector();
	}
    
        public static Vector summaryTraining(Date period, long deptId) {
                Vector lists = new Vector(); 
		DBResultSet dbrs = null;
                
		try {
                       
                        String sql = " SELECT * FROM " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TP" +
                                     " INNER JOIN " + PstTrainingSchedule.TBL_TRAIN_SCHEDULE + " TS" +
                                     " ON TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +                                     
                                     " LEFT JOIN " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TA" +
                                     " ON TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_SCHEDULE_ID] +                                     
                                     " WHERE " + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = "+ deptId +
                                     " AND YEAR(TS."+PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] + ") = '"+Formater.formatDate(period,"yyyy")+"'" +
                                     " AND MONTH(TS."+PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] + ") = '"+Formater.formatDate(period,"M")+"'" ;
                                     

                        System.out.println("training >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
            
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
                            Vector rowx = new Vector();
                            
                            TrainingActivityPlan trainingplan = new TrainingActivityPlan();
                            PstTrainingActivityPlan.resultToObject(rs, trainingplan);
                            rowx.add(trainingplan);
                            
                            TrainingSchedule trainingschedule = new TrainingSchedule();
                            PstTrainingSchedule.resultToObject(rs, trainingschedule);
                            rowx.add(trainingschedule);
                            
                            TrainingActivityActual trainingactual = new TrainingActivityActual();
                            PstTrainingActivityActual.resultToObject(rs, trainingactual);                            
                            rowx.add(trainingactual);

                            lists.add(rowx);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
                
                return new Vector();
        }
        
        public static boolean checkAttendance(long oidTrainingActual, long oidEmployee) {
                DBResultSet dbrs = null;
               
                try {
                    String sql = " SELECT COUNT(" + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + ")" +
                                 " FROM " + PstTrainingHistory.TBL_HR_TRAINING_HISTORY + " TH " + 
                                 " WHERE TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + 
                                 " = " + oidTrainingActual +
                                 " AND TH." + PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] +
                                 " = " + oidEmployee;
                                     

                     System.out.println("check attendance >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                     dbrs = DBHandler.execQueryResult(sql);			

                     ResultSet rs = dbrs.getResultSet();
                     
                     while(rs.next()) {
                         if(rs.getInt(1) > 0)
                             return true;
                     }
                     
                     return false;
                } 
                catch(Exception e) {
                     System.out.println(e);
                     return false;
		}        
                finally {
                     DBResultSet.close(dbrs);
                }

        }
        
        public static void incrementAttendance(long oidTrainingActual) {                
		
		try {
                       
                        String sql = " UPDATE " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL +
                                     " SET " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES] +
                                     " = " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES] + "+1" +
                                     " WHERE " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + 
                                     " = " + oidTrainingActual;
                                     

                        System.out.println("increment >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        DBHandler.execUpdate(sql);			
		}
                catch(Exception e) {
			System.out.println(e);
		}
        }
        
        public static void decrementAttendance(long oidTrainingActual) {  
		try {
                       
                        String sql = " UPDATE " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL +
                                     " SET " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES] +
                                     " = " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES] + "-1" +
                                     " WHERE " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + 
                                     " = " + oidTrainingActual;
                                     

                        System.out.println("decrement >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        DBHandler.execUpdate(sql);    
		}
                catch(Exception e) {
			System.out.println(e);
                }
        }
    
        // obsolete
        /*public static Vector summaryTraining(Date period, long deptId) {
                Vector lists = new Vector(); 
		DBResultSet dbrs = null;
                
		try {
                       
                        String sql = " SELECT * FROM " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TP" +
                                     " INNER JOIN " + PstTrainingSchedule.TBL_TRAIN_SCHEDULE + " TS" +
                                     " ON TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +                                     
                                     " LEFT JOIN " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TA" +
                                     " ON TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " AND TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] +
                                     " AND TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_START_TIME] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_START_TIME] +
                                     " AND TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_END_TIME] +
                                     " = TA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_END_TIME] +
                                     " WHERE " + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = "+ deptId +
                                     " AND YEAR(TS."+PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] + ") = '"+Formater.formatDate(period,"yyyy")+"'" +
                                     " AND MONTH(TS."+PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] + ") = '"+Formater.formatDate(period,"M")+"'" ;
                                     

                        System.out.println("training >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
            
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
                            Vector rowx = new Vector();
                            
                            TrainingActivityPlan trainingplan = new TrainingActivityPlan();
                            PstTrainingActivityPlan.resultToObject(rs, trainingplan);
                            rowx.add(trainingplan);
                            
                            TrainingSchedule trainingschedule = new TrainingSchedule();
                            PstTrainingSchedule.resultToObject(rs, trainingschedule);
                            rowx.add(trainingschedule);
                            
                            TrainingActivityActual trainingactual = new TrainingActivityActual();
                            PstTrainingActivityActual.resultToObject(rs, trainingactual);                            
                            rowx.add(trainingactual);

                            lists.add(rowx);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
                
                return new Vector();
        }*/
        
        /*public static Vector getActual(Date schDate, Date schStart, Date schEnd) {
                DBResultSet dbrs = null;
                Vector result = new Vector(1,1);
                
		try {
                    /*String sql = "  SELECT COUNT(DISTINCT "+ PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + ") AS TOT_PRG "+
                		 ", SUM("+PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES]+") AS TOT_TRAINEES"+
                                 ", (SUM(TIME_TO_SEC("+PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_END_TIME]+")) - "+
                                 "  SUM(TIME_TO_SEC("+PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_START_TIME ]+")))/3600 AS TOT_HOURS " +
                		 "  FROM " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL+
                                 "  WHERE "+PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID]+
                                 " = "+oid +
                                 " GROUP BY "+PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID];
                    *
                    
                    String sql = " SELECT " + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_START_TIME] + 
                                 "," + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_END_TIME] +
                                 "," + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES] +
                                 " FROM " + PstTrainingActivityActual.TBL_HR_TRAINING_ACTIVITY_ACTUAL;
                    
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+sql);
                    
                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();

                    /*while(rs.next()) {
                        result.add(""+rs.getInt(1));
                        result.add(""+rs.getInt(2));
                        double hours = rs.getDouble(3);
                        long iHours = Math.round(hours);                
                        result.add(""+iHours);                
                    }*
                    
                    while(rs.next()) {
                        result.add(rs.getDate(1));
                        result.add(rs.getDate(2));
                        result.add(rs.getString(3));
                    }

                    rs.close();
                    return result;
		}
                catch(Exception e) {
                        System.out.println(e);
			return new Vector(1,1);
		}
                finally {
			DBResultSet.close(dbrs);
		}
        }*/
        
        
        /* tester method */
        
        public static void main(String[] args) {
            Vector tmp = SessTraining.summaryTraining(new Date(), 42);
        }
    
}
