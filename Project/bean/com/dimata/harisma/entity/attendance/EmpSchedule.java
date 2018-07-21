/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.attendance;

/* package java */
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import java.util.Date;
import java.util.Vector;

/* package qdep */
import com.dimata.qdep.entity.*;

public class EmpSchedule extends Entity {

    private long periodId = 0;
    private long employeeId = 0;
    private int scheduleType = 0;
    
  
    private long d1 = 0;
    private long d2 = 0;
    private long d3 = 0;
    private long d4 = 0;
    private long d5 = 0;
    private long d6 = 0;
    private long d7 = 0;
    private long d8 = 0;
    private long d9 = 0;
    private long d10 = 0;
    private long d11 = 0;
    private long d12 = 0;
    private long d13 = 0;
    private long d14 = 0;
    private long d15 = 0;
    private long d16 = 0;
    private long d17 = 0;
    private long d18 = 0;
    private long d19 = 0;
    private long d20 = 0;
    private long d21 = 0;
    private long d22 = 0;
    private long d23 = 0;
    private long d24 = 0;
    private long d25 = 0;
    private long d26 = 0;
    private long d27 = 0;
    private long d28 = 0;
    private long d29 = 0;
    private long d30 = 0;
    private long d31 = 0;
    // added by Edhy for split shift schedule
    private long d2nd1 = 0;
    private long d2nd2 = 0;
    private long d2nd3 = 0;
    private long d2nd4 = 0;
    private long d2nd5 = 0;
    private long d2nd6 = 0;
    private long d2nd7 = 0;
    private long d2nd8 = 0;
    private long d2nd9 = 0;
    private long d2nd10 = 0;
    private long d2nd11 = 0;
    private long d2nd12 = 0;
    private long d2nd13 = 0;
    private long d2nd14 = 0;
    private long d2nd15 = 0;
    private long d2nd16 = 0;
    private long d2nd17 = 0;
    private long d2nd18 = 0;
    private long d2nd19 = 0;
    private long d2nd20 = 0;
    private long d2nd21 = 0;
    private long d2nd22 = 0;
    private long d2nd23 = 0;
    private long d2nd24 = 0;
    private long d2nd25 = 0;
    private long d2nd26 = 0;
    private long d2nd27 = 0;
    private long d2nd28 = 0;
    private long d2nd29 = 0;
    private long d2nd30 = 0;
    private long d2nd31 = 0;
    private int status1 = 0;
    private int status2 = 0;
    private int status3 = 0;
    private int status4 = 0;
    private int status5 = 0;
    private int status6 = 0;
    private int status7 = 0;
    private int status8 = 0;
    private int status9 = 0;
    private int status10 = 0;
    private int status11 = 0;
    private int status12 = 0;
    private int status13 = 0;
    private int status14 = 0;
    private int status15 = 0;
    private int status16 = 0;
    private int status17 = 0;
    private int status18 = 0;
    private int status19 = 0;
    private int status20 = 0;
    private int status21 = 0;
    private int status22 = 0;
    private int status23 = 0;
    private int status24 = 0;
    private int status25 = 0;
    private int status26 = 0;
    private int status27 = 0;
    private int status28 = 0;
    private int status29 = 0;
    private int status30 = 0;
    private int status31 = 0;
    private int status2nd1 = 0;
    private int status2nd2 = 0;
    private int status2nd3 = 0;
    private int status2nd4 = 0;
    private int status2nd5 = 0;
    private int status2nd6 = 0;
    private int status2nd7 = 0;
    private int status2nd8 = 0;
    private int status2nd9 = 0;
    private int status2nd10 = 0;
    private int status2nd11 = 0;
    private int status2nd12 = 0;
    private int status2nd13 = 0;
    private int status2nd14 = 0;
    private int status2nd15 = 0;
    private int status2nd16 = 0;
    private int status2nd17 = 0;
    private int status2nd18 = 0;
    private int status2nd19 = 0;
    private int status2nd20 = 0;
    private int status2nd21 = 0;
    private int status2nd22 = 0;
    private int status2nd23 = 0;
    private int status2nd24 = 0;
    private int status2nd25 = 0;
    private int status2nd26 = 0;
    private int status2nd27 = 0;
    private int status2nd28 = 0;
    private int status2nd29 = 0;
    private int status2nd30 = 0;
    private int status2nd31 = 0;
    private int reason1 = 0;
    private int reason2 = 0;
    private int reason3 = 0;
    private int reason4 = 0;
    private int reason5 = 0;
    private int reason6 = 0;
    private int reason7 = 0;
    private int reason8 = 0;
    private int reason9 = 0;
    private int reason10 = 0;
    private int reason11 = 0;
    private int reason12 = 0;
    private int reason13 = 0;
    private int reason14 = 0;
    private int reason15 = 0;
    private int reason16 = 0;
    private int reason17 = 0;
    private int reason18 = 0;
    private int reason19 = 0;
    private int reason20 = 0;
    private int reason21 = 0;
    private int reason22 = 0;
    private int reason23 = 0;
    private int reason24 = 0;
    private int reason25 = 0;
    private int reason26 = 0;
    private int reason27 = 0;
    private int reason28 = 0;
    private int reason29 = 0;
    private int reason30 = 0;
    private int reason31 = 0;
    private int reason2nd1 = 0;
    private int reason2nd2 = 0;
    private int reason2nd3 = 0;
    private int reason2nd4 = 0;
    private int reason2nd5 = 0;
    private int reason2nd6 = 0;
    private int reason2nd7 = 0;
    private int reason2nd8 = 0;
    private int reason2nd9 = 0;
    private int reason2nd10 = 0;
    private int reason2nd11 = 0;
    private int reason2nd12 = 0;
    private int reason2nd13 = 0;
    private int reason2nd14 = 0;
    private int reason2nd15 = 0;
    private int reason2nd16 = 0;
    private int reason2nd17 = 0;
    private int reason2nd18 = 0;
    private int reason2nd19 = 0;
    private int reason2nd20 = 0;
    private int reason2nd21 = 0;
    private int reason2nd22 = 0;
    private int reason2nd23 = 0;
    private int reason2nd24 = 0;
    private int reason2nd25 = 0;
    private int reason2nd26 = 0;
    private int reason2nd27 = 0;
    private int reason2nd28 = 0;
    private int reason2nd29 = 0;
    private int reason2nd30 = 0;
    private int reason2nd31 = 0;
    private String note1 = "";
    private String note2 = "";
    private String note3 = "";
    private String note4 = "";
    private String note5 = "";
    private String note6 = "";
    private String note7 = "";
    private String note8 = "";
    private String note9 = "";
    private String note10 = "";
    private String note11 = "";
    private String note12 = "";
    private String note13 = "";
    private String note14 = "";
    private String note15 = "";
    private String note16 = "";
    private String note17 = "";
    private String note18 = "";
    private String note19 = "";
    private String note20 = "";
    private String note21 = "";
    private String note22 = "";
    private String note23 = "";
    private String note24 = "";
    private String note25 = "";
    private String note26 = "";
    private String note27 = "";
    private String note28 = "";
    private String note29 = "";
    private String note30 = "";
    private String note31 = "";
    private String note2nd1 = "";
    private String note2nd2 = "";
    private String note2nd3 = "";
    private String note2nd4 = "";
    private String note2nd5 = "";
    private String note2nd6 = "";
    private String note2nd7 = "";
    private String note2nd8 = "";
    private String note2nd9 = "";
    private String note2nd10 = "";
    private String note2nd11 = "";
    private String note2nd12 = "";
    private String note2nd13 = "";
    private String note2nd14 = "";
    private String note2nd15 = "";
    private String note2nd16 = "";
    private String note2nd17 = "";
    private String note2nd18 = "";
    private String note2nd19 = "";
    private String note2nd20 = "";
    private String note2nd21 = "";
    private String note2nd22 = "";
    private String note2nd23 = "";
    private String note2nd24 = "";
    private String note2nd25 = "";
    private String note2nd26 = "";
    private String note2nd27 = "";
    private String note2nd28 = "";
    private String note2nd29 = "";
    private String note2nd30 = "";
    private String note2nd31 = "";
    private Date in1;
    private Date in2;
    private Date in3;
    private Date in4;
    private Date in5;
    private Date in6;
    private Date in7;
    private Date in8;
    private Date in9;
    private Date in10;
    private Date in11;
    private Date in12;
    private Date in13;
    private Date in14;
    private Date in15;
    private Date in16;
    private Date in17;
    private Date in18;
    private Date in19;
    private Date in20;
    private Date in21;
    private Date in22;
    private Date in23;
    private Date in24;
    private Date in25;
    private Date in26;
    private Date in27;
    private Date in28;
    private Date in29;
    private Date in30;
    private Date in31;
    private Date in2nd1;
    private Date in2nd2;
    private Date in2nd3;
    private Date in2nd4;
    private Date in2nd5;
    private Date in2nd6;
    private Date in2nd7;
    private Date in2nd8;
    private Date in2nd9;
    private Date in2nd10;
    private Date in2nd11;
    private Date in2nd12;
    private Date in2nd13;
    private Date in2nd14;
    private Date in2nd15;
    private Date in2nd16;
    private Date in2nd17;
    private Date in2nd18;
    private Date in2nd19;
    private Date in2nd20;
    private Date in2nd21;
    private Date in2nd22;
    private Date in2nd23;
    private Date in2nd24;
    private Date in2nd25;
    private Date in2nd26;
    private Date in2nd27;
    private Date in2nd28;
    private Date in2nd29;
    private Date in2nd30;
    private Date in2nd31;
    private Date out1;
    private Date out2;
    private Date out3;
    private Date out4;
    private Date out5;
    private Date out6;
    private Date out7;
    private Date out8;
    private Date out9;
    private Date out10;
    private Date out11;
    private Date out12;
    private Date out13;
    private Date out14;
    private Date out15;
    private Date out16;
    private Date out17;
    private Date out18;
    private Date out19;
    private Date out20;
    private Date out21;
    private Date out22;
    private Date out23;
    private Date out24;
    private Date out25;
    private Date out26;
    private Date out27;
    private Date out28;
    private Date out29;
    private Date out30;
    private Date out31;
    private Date out2nd1;
    private Date out2nd2;
    private Date out2nd3;
    private Date out2nd4;
    private Date out2nd5;
    private Date out2nd6;
    private Date out2nd7;
    private Date out2nd8;
    private Date out2nd9;
    private Date out2nd10;
    private Date out2nd11;
    private Date out2nd12;
    private Date out2nd13;
    private Date out2nd14;
    private Date out2nd15;
    private Date out2nd16;
    private Date out2nd17;
    private Date out2nd18;
    private Date out2nd19;
    private Date out2nd20;
    private Date out2nd21;
    private Date out2nd22;
    private Date out2nd23;
    private Date out2nd24;
    private Date out2nd25;
    private Date out2nd26;
    private Date out2nd27;
    private Date out2nd28;
    private Date out2nd29;
    private Date out2nd30;
    private Date out2nd31;

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    public long getD(int index) {
        long scheduleId = 0;
        switch (index) {
            case 1:
                scheduleId = this.getD1();
                break;

            case 2:
                scheduleId = this.getD2();
                break;

            case 3:
                scheduleId = this.getD3();
                break;

            case 4:
                scheduleId = this.getD4();
                break;

            case 5:
                scheduleId = this.getD5();
                break;

            case 6:
                scheduleId = this.getD6();
                break;

            case 7:
                scheduleId = this.getD7();
                break;

            case 8:
                scheduleId = this.getD8();
                break;

            case 9:
                scheduleId = this.getD9();
                break;

            case 10:
                scheduleId = this.getD10();
                break;

            case 11:
                scheduleId = this.getD11();
                break;

            case 12:
                scheduleId = this.getD12();
                break;

            case 13:
                scheduleId = this.getD13();
                break;

            case 14:
                scheduleId = this.getD14();
                break;

            case 15:
                scheduleId = this.getD15();
                break;

            case 16:
                scheduleId = this.getD16();
                break;

            case 17:
                scheduleId = this.getD17();
                break;

            case 18:
                scheduleId = this.getD18();
                break;

            case 19:
                scheduleId = this.getD19();
                break;

            case 20:
                scheduleId = this.getD20();
                break;

            case 21:
                scheduleId = this.getD21();
                break;

            case 22:
                scheduleId = this.getD22();
                break;

            case 23:
                scheduleId = this.getD23();
                break;

            case 24:
                scheduleId = this.getD24();
                break;

            case 25:
                scheduleId = this.getD25();
                break;

            case 26:
                scheduleId = this.getD26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                scheduleId = this.getD27();
                break;

            case 28:
                scheduleId = this.getD28();
                break;

            case 29:
                scheduleId = this.getD29();
                break;

            case 30:
                scheduleId = this.getD30();
                break;

            case 31:
                scheduleId = this.getD31();
                break;
        }

        return scheduleId;
    }


    //update by satrya 2013-04-28
    public void setIN(int index, Date dtIn) {
        switch (index) {
            case 1:
                this.setIn1(dtIn);
                break;

            case 2:
                this.setIn2(dtIn);
                break;

            case 3:
                this.setIn3(dtIn);
                break;

            case 4:
                this.setIn4(dtIn);
                break;

            case 5:
                this.setIn5(dtIn);
                break;

            case 6:
                this.setIn6(dtIn);
                break;

            case 7:
                this.setIn7(dtIn);
                break;

            case 8:
                this.setIn8(dtIn);
                break;

            case 9:
                this.setIn9(dtIn);
                break;

            case 10:
                this.setIn10(dtIn);
                break;

            case 11:
                this.setIn11(dtIn);
                break;

            case 12:
                this.setIn12(dtIn);
                break;

            case 13:
                this.setIn13(dtIn);
                break;

            case 14:
                this.setIn14(dtIn);
                break;

            case 15:
                this.setIn15(dtIn);
                break;

            case 16:
                this.setIn16(dtIn);
                break;

            case 17:
                this.setIn17(dtIn);
                break;

            case 18:
                this.setIn18(dtIn);
                break;

            case 19:
                this.setIn19(dtIn);
                break;

            case 20:
                this.setIn20(dtIn);
                break;

            case 21:
                this.setIn21(dtIn);
                break;

            case 22:
                this.setIn22(dtIn);
                break;

            case 23:
                this.setIn23(dtIn);
                break;

            case 24:
                this.setIn24(dtIn);
                break;

            case 25:
                this.setIn25(dtIn);
                break;

            case 26:
                this.setIn26(dtIn);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setIn27(dtIn);
                break;

            case 28:
                this.setIn28(dtIn);
                break;

            case 29:
                this.setIn29(dtIn);
                break;

            case 30:
                this.setIn30(dtIn);
                break;

            case 31:
                this.setIn31(dtIn);
                break;
        }
        
    }
    
    /**
     * create by satrya 2014-02-18
     * @param index
     * @param dtIn
     * @return 
     */
    public Date getIN(int index) {
		  Date dt = null;
        switch (index) {
            case 1:
                dt =this.getIn1();
                break;

            case 2:
                dt =this.getIn2();
                break;

            case 3:
                dt =this.getIn3();
                break;

            case 4:
                dt =this.getIn4();
                break;

            case 5:
                dt =this.getIn5();
                break;

            case 6:
                dt =this.getIn6();
                break;

            case 7:
                dt =this.getIn7();
                break;

            case 8:
                dt =this.getIn8();
                break;

            case 9:
                dt =this.getIn9();
                break;

            case 10:
                dt =this.getIn10();
                break;

            case 11:
                dt =this.getIn11();
                break;

            case 12:
                dt =this.getIn12();
                break;

            case 13:
                dt =this.getIn13();
                break;

            case 14:
                dt =this.getIn14();
                break;

            case 15:
                dt =this.getIn15();
                break;

            case 16:
                dt =this.getIn16();
                break;

            case 17:
                dt =this.getIn17();
                break;

            case 18:
                dt =this.getIn18();
                break;

            case 19:
                dt =this.getIn19();
                break;

            case 20:
                dt =this.getIn20();
                break;

            case 21:
                dt =this.getIn21();
                break;

            case 22:
                dt =this.getIn22();
                break;

            case 23:
                dt =this.getIn23();
                break;

            case 24:
                dt =this.getIn24();
                break;

            case 25:
                dt =this.getIn25();
                break;

            case 26:
                dt =this.getIn26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                dt =this.getIn27();
                break;

            case 28:
                dt =this.getIn28();
                break;

            case 29:
                dt =this.getIn29();
                break;

            case 30:
                dt =this.getIn30();
                break;

            case 31:
                dt =this.getIn31();
                break;
        }
		return dt;
        
    }
    
    /**
     * create by satrya 2014-02-18
     * @param index
     * @return 
     */
     public Date getIn2nd(int index) {
        Date dt = null;
        switch (index) {
            case 1:
                dt =this.getIn2nd1();
                break;

            case 2:
                dt =this.getIn2nd2();
                break;

            case 3:
                dt =this.getIn2nd3();
                break;

            case 4:
                dt =this.getIn2nd4();
                break;

            case 5:
                dt =this.getIn2nd5();
                break;

            case 6:
                dt =this.getIn2nd6();
                break;

            case 7:
                dt =this.getIn2nd7();
                break;

            case 8:
                dt =this.getIn2nd8();
                break;

            case 9:
                dt =this.getIn2nd9();
                break;

            case 10:
                dt =this.getIn2nd10();
                break;

            case 11:
                dt =this.getIn2nd11();
                break;

            case 12:
                dt =this.getIn2nd12();
                break;

            case 13:
                dt =this.getIn2nd13();
                break;

            case 14:
                dt =this.getIn2nd14();
                break;

            case 15:
                dt =this.getIn2nd15();
                break;

            case 16:
                dt =this.getIn2nd16();
                break;

            case 17:
                dt =this.getIn2nd17();
                break;

            case 18:
                dt =this.getIn2nd18();
                break;

            case 19:
                dt =this.getIn2nd19();
                break;

            case 20:
                dt =this.getIn2nd20();
                break;

            case 21:
                dt =this.getIn2nd21();
                break;

            case 22:
                dt =this.getIn2nd22();
                break;

            case 23:
                dt =this.getIn2nd23();
                break;

            case 24:
                dt =this.getIn2nd24();
                break;

            case 25:
                dt =this.getIn2nd25();
                break;

            case 26:
                dt =this.getIn2nd26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                dt =this.getIn2nd27();
                break;

            case 28:
                dt =this.getIn2nd28();
                break;

            case 29:
                dt =this.getIn2nd29();
                break;

            case 30:
                dt =this.getIn2nd30();
                break;

            case 31:
                dt =this.getIn2nd31();
                break;
        }
		return dt;
        
    }
    /**
     * create by satrya 2014-02-18
     * @param index
     * @return 
     */
    public Date getOut(int index) {
	Date dt = null;
        switch (index) {
            case 1:
                dt =this.getOut1();
                break;

            case 2:
                dt =this.getOut2();
                break;

            case 3:
                dt =this.getOut3();
                break;

            case 4:
                dt =this.getOut4();
                break;

            case 5:
                dt =this.getOut5();
                break;

            case 6:
                dt =this.getOut6();
                break;

            case 7:
                dt =this.getOut7();
                break;

            case 8:
                dt =this.getOut8();
                break;

            case 9:
                dt =this.getOut9();
                break;

            case 10:
                dt =this.getOut10();
                break;

            case 11:
                dt =this.getOut11();
                break;

            case 12:
                dt =this.getOut12();
                break;

            case 13:
                dt =this.getOut13();
                break;

            case 14:
                dt =this.getOut14();
                break;

            case 15:
                dt =this.getOut15();
                break;

            case 16:
                dt =this.getOut16();
                break;

            case 17:
                dt =this.getOut17();
                break;

            case 18:
                dt =this.getOut18();
                break;

            case 19:
                dt =this.getOut19();
                break;

            case 20:
                dt =this.getOut20();
                break;

            case 21:
                dt =this.getOut21();
                break;

            case 22:
                dt =this.getOut22();
                break;

            case 23:
                dt =this.getOut23();
                break;

            case 24:
                dt =this.getOut24();
                break;

            case 25:
                dt =this.getOut25();
                break;

            case 26:
                dt =this.getOut26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                dt =this.getOut27();
                break;

            case 28:
                dt =this.getOut28();
                break;

            case 29:
                dt =this.getOut29();
                break;

            case 30:
                dt =this.getOut30();
                break;

            case 31:
                dt =this.getOut31();
                break;
        }
		return dt;
        
    }
    /**
     * create by satrya 2014-02-18
     * @param index
     * @return 
     */
      public Date getOut2nd(int index) {
	Date dt = null;
        switch (index) {
            case 1:
                dt =this.getOut2nd1();
                break;

            case 2:
                dt =this.getOut2nd2();
                break;

            case 3:
                dt =this.getOut2nd3();
                break;

            case 4:
                dt =this.getOut2nd4();
                break;

            case 5:
                dt =this.getOut2nd5();
                break;

            case 6:
                dt =this.getOut2nd6();
                break;

            case 7:
                dt =this.getOut2nd7();
                break;

            case 8:
                dt =this.getOut2nd8();
                break;

            case 9:
                dt =this.getOut2nd9();
                break;

            case 10:
                dt =this.getOut2nd10();
                break;

            case 11:
                dt =this.getOut2nd11();
                break;

            case 12:
                dt =this.getOut2nd12();
                break;

            case 13:
                dt =this.getOut2nd13();
                break;

            case 14:
                dt =this.getOut2nd14();
                break;

            case 15:
                dt =this.getOut2nd15();
                break;

            case 16:
                dt =this.getOut2nd16();
                break;

            case 17:
                dt =this.getOut2nd17();
                break;

            case 18:
                dt =this.getOut2nd18();
                break;

            case 19:
                dt =this.getOut2nd19();
                break;

            case 20:
                dt =this.getOut2nd20();
                break;

            case 21:
                dt =this.getOut2nd21();
                break;

            case 22:
                dt =this.getOut2nd22();
                break;

            case 23:
                dt =this.getOut2nd23();
                break;

            case 24:
                dt =this.getOut2nd24();
                break;

            case 25:
                dt =this.getOut2nd25();
                break;

            case 26:
                dt =this.getOut2nd26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                dt =this.getOut2nd27();
                break;

            case 28:
                dt =this.getOut2nd28();
                break;

            case 29:
                dt =this.getOut2nd29();
                break;

            case 30:
                dt =this.getOut2nd30();
                break;

            case 31:
                dt =this.getOut2nd31();
                break;
        }
		return dt;
        
    }
    /**
     * create by satrya 2014-02-18
     * 
     * @param index
     * @param dtIn 
     */
    public void setIN2nd(int index, Date dtIn) {
        switch (index) {
            case 1:
               this.setIn2nd1(dtIn);
                break;

            case 2:
               this.setIn2nd2(dtIn);
                break;

            case 3:
               this.setIn2nd3(dtIn);
                break;

            case 4:
               this.setIn2nd4(dtIn);
                break;

            case 5:
               this.setIn2nd5(dtIn);
                break;

            case 6:
               this.setIn2nd6(dtIn);
                break;

            case 7:
               this.setIn2nd7(dtIn);
                break;

            case 8:
               this.setIn2nd8(dtIn);
                break;

            case 9:
               this.setIn2nd9(dtIn);
                break;

            case 10:
               this.setIn2nd10(dtIn);
                break;

            case 11:
               this.setIn2nd11(dtIn);
                break;

            case 12:
               this.setIn2nd12(dtIn);
                break;

            case 13:
               this.setIn2nd13(dtIn);
                break;

            case 14:
               this.setIn2nd14(dtIn);
                break;

            case 15:
               this.setIn2nd15(dtIn);
                break;

            case 16:
               this.setIn2nd16(dtIn);
                break;

            case 17:
               this.setIn2nd17(dtIn);
                break;

            case 18:
               this.setIn2nd18(dtIn);
                break;

            case 19:
               this.setIn2nd19(dtIn);
                break;

            case 20:
               this.setIn2nd20(dtIn);
                break;

            case 21:
               this.setIn2nd21(dtIn);
                break;

            case 22:
               this.setIn2nd22(dtIn);
                break;

            case 23:
               this.setIn2nd23(dtIn);
                break;

            case 24:
               this.setIn2nd24(dtIn);
                break;

            case 25:
               this.setIn2nd25(dtIn);
                break;

            case 26:
               this.setIn2nd26(dtIn);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
               this.setIn2nd27(dtIn);
                break;

            case 28:
               this.setIn2nd28(dtIn);
                break;

            case 29:
               this.setIn2nd29(dtIn);
                break;

            case 30:
               this.setIn2nd30(dtIn);
                break;

            case 31:
               this.setIn2nd31(dtIn);
                break;
        }
        
    }

    //update by satrya 2013-04-28
    public void setOUT(int index, Date dtOut) {
        switch (index) {
            case 1:
                this.setOut1(dtOut);
                break;

            case 2:
                this.setOut2(dtOut);
                break;

            case 3:
                this.setOut3(dtOut);
                break;

            case 4:
                this.setOut4(dtOut);
                break;

            case 5:
                this.setOut5(dtOut);
                break;

            case 6:
                this.setOut6(dtOut);
                break;

            case 7:
                this.setOut7(dtOut);
                break;

            case 8:
                this.setOut8(dtOut);
                break;

            case 9:
                this.setOut9(dtOut);
                break;

            case 10:
                this.setOut10(dtOut);
                break;

            case 11:
                this.setOut11(dtOut);
                break;

            case 12:
                this.setOut12(dtOut);
                break;

            case 13:
                this.setOut13(dtOut);
                break;

            case 14:
                this.setOut14(dtOut);
                break;

            case 15:
                this.setOut15(dtOut);
                break;

            case 16:
                this.setOut16(dtOut);
                break;

            case 17:
                this.setOut17(dtOut);
                break;

            case 18:
                this.setOut18(dtOut);
                break;

            case 19:
                this.setOut19(dtOut);
                break;

            case 20:
                this.setOut20(dtOut);
                break;

            case 21:
                this.setOut21(dtOut);
                break;

            case 22:
                this.setOut22(dtOut);
                break;

            case 23:
                this.setOut23(dtOut);
                break;

            case 24:
                this.setOut24(dtOut);
                break;

            case 25:
                this.setOut25(dtOut);
                break;

            case 26:
                this.setOut26(dtOut);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setOut27(dtOut);
                break;

            case 28:
                this.setOut28(dtOut);
                break;

            case 29:
                this.setOut29(dtOut);
                break;

            case 30:
                this.setOut30(dtOut);
                break;

            case 31:
                this.setOut31(dtOut);
                break;
        }
        
    }

    /**
     * create by satrya 2014-02-18
     * @param index
     * @param dtOut 
     */
     public void setOUT2nd(int index, Date dtOut) {
        switch (index) {
            case 1:
                this.setOut2nd1(dtOut);
                break;

            case 2:
                this.setOut2nd2(dtOut);
                break;

            case 3:
                this.setOut2nd3(dtOut);
                break;

            case 4:
                this.setOut2nd4(dtOut);
                break;

            case 5:
                this.setOut2nd5(dtOut);
                break;

            case 6:
                this.setOut2nd6(dtOut);
                break;

            case 7:
                this.setOut2nd7(dtOut);
                break;

            case 8:
                this.setOut2nd8(dtOut);
                break;

            case 9:
                this.setOut2nd9(dtOut);
                break;

            case 10:
                this.setOut2nd10(dtOut);
                break;

            case 11:
                this.setOut2nd11(dtOut);
                break;

            case 12:
                this.setOut2nd12(dtOut);
                break;

            case 13:
                this.setOut2nd13(dtOut);
                break;

            case 14:
                this.setOut2nd14(dtOut);
                break;

            case 15:
                this.setOut2nd15(dtOut);
                break;

            case 16:
                this.setOut2nd16(dtOut);
                break;

            case 17:
                this.setOut2nd17(dtOut);
                break;

            case 18:
                this.setOut2nd18(dtOut);
                break;

            case 19:
                this.setOut2nd19(dtOut);
                break;

            case 20:
                this.setOut2nd20(dtOut);
                break;

            case 21:
                this.setOut2nd21(dtOut);
                break;

            case 22:
                this.setOut2nd22(dtOut);
                break;

            case 23:
                this.setOut2nd23(dtOut);
                break;

            case 24:
                this.setOut2nd24(dtOut);
                break;

            case 25:
                this.setOut2nd25(dtOut);
                break;

            case 26:
                this.setOut2nd26(dtOut);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setOut2nd27(dtOut);
                break;

            case 28:
                this.setOut2nd28(dtOut);
                break;

            case 29:
                this.setOut2nd29(dtOut);
                break;

            case 30:
                this.setOut2nd30(dtOut);
                break;

            case 31:
                this.setOut2nd31(dtOut);
                break;
        }
        
    }
    public void setD(int index, long scheduleId) {
        switch (index) {
            case 1:
                this.setD1(scheduleId);
                break;

            case 2:
                this.setD2(scheduleId);
                break;

            case 3:
                this.setD3(scheduleId);
                break;

            case 4:
                this.setD4(scheduleId);
                break;

            case 5:
                this.setD5(scheduleId);
                break;

            case 6:
                this.setD6(scheduleId);
                break;

            case 7:
                this.setD7(scheduleId);
                break;

            case 8:
                this.setD8(scheduleId);
                break;

            case 9:
                this.setD9(scheduleId);
                break;

            case 10:
                this.setD10(scheduleId);
                break;

            case 11:
                this.setD11(scheduleId);
                break;

            case 12:
                this.setD12(scheduleId);
                break;

            case 13:
                this.setD13(scheduleId);
                break;

            case 14:
                this.setD14(scheduleId);
                break;

            case 15:
                this.setD15(scheduleId);
                break;

            case 16:
                this.setD16(scheduleId);
                break;

            case 17:
                this.setD17(scheduleId);
                break;

            case 18:
                this.setD18(scheduleId);
                break;

            case 19:
                this.setD19(scheduleId);
                break;

            case 20:
                this.setD20(scheduleId);
                break;

            case 21:
                this.setD21(scheduleId);
                break;

            case 22:
                this.setD22(scheduleId);
                break;

            case 23:
                this.setD23(scheduleId);
                break;

            case 24:
                this.setD24(scheduleId);
                break;

            case 25:
                this.setD25(scheduleId);
                break;

            case 26:
                this.setD26(scheduleId);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setD27(scheduleId);
                break;

            case 28:
                this.setD28(scheduleId);
                break;

            case 29:
                this.setD29(scheduleId);
                break;

            case 30:
                this.setD30(scheduleId);
                break;

            case 31:
                this.setD31(scheduleId);
                break;
        }
        
    }

    public long getD2nd(int index) {
        long scheduleId = 0;
        switch (index) {
            case 1:
                scheduleId = this.getD2nd1();
                break;

            case 2:
                scheduleId = this.getD2nd2();
                break;

            case 3:
                scheduleId = this.getD2nd3();
                break;

            case 4:
                scheduleId = this.getD2nd4();
                break;

            case 5:
                scheduleId = this.getD2nd5();
                break;

            case 6:
                scheduleId = this.getD2nd6();
                break;

            case 7:
                scheduleId = this.getD2nd7();
                break;

            case 8:
                scheduleId = this.getD2nd8();
                break;

            case 9:
                scheduleId = this.getD2nd9();
                break;

            case 10:
                scheduleId = this.getD2nd10();
                break;

            case 11:
                scheduleId = this.getD2nd11();
                break;

            case 12:
                scheduleId = this.getD2nd12();
                break;

            case 13:
                scheduleId = this.getD2nd13();
                break;

            case 14:
                scheduleId = this.getD2nd14();
                break;

            case 15:
                scheduleId = this.getD2nd15();
                break;

            case 16:
                scheduleId = this.getD2nd16();
                break;

            case 17:
                scheduleId = this.getD2nd17();
                break;

            case 18:
                scheduleId = this.getD2nd18();
                break;

            case 19:
                scheduleId = this.getD2nd19();
                break;

            case 20:
                scheduleId = this.getD2nd20();
                break;

            case 21:
                scheduleId = this.getD2nd21();
                break;

            case 22:
                scheduleId = this.getD2nd22();
                break;

            case 23:
                scheduleId = this.getD2nd23();
                break;

            case 24:
                scheduleId = this.getD2nd24();
                break;

            case 25:
                scheduleId = this.getD2nd25();
                break;

            case 26:
                scheduleId = this.getD2nd26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                scheduleId = this.getD2nd27();
                break;

            case 28:
                scheduleId = this.getD2nd28();
                break;

            case 29:
                scheduleId = this.getD2nd29();
                break;

            case 30:
                scheduleId = this.getD2nd30();
                break;

            case 31:
                scheduleId = this.getD2nd31();
                break;
        }

        return scheduleId;
    }


    public void setD2nd(int index, long scheduleId) {
        switch (index) {
            case 1:
                this.setD2nd1(scheduleId);
                break;

            case 2:
                this.setD2nd2(scheduleId);
                break;

            case 3:
                this.setD2nd3(scheduleId);
                break;

            case 4:
                this.setD2nd4(scheduleId);
                break;

            case 5:
                this.setD2nd5(scheduleId);
                break;

            case 6:
                this.setD2nd6(scheduleId);
                break;

            case 7:
                this.setD2nd7(scheduleId);
                break;

            case 8:
                this.setD2nd8(scheduleId);
                break;

            case 9:
                this.setD2nd9(scheduleId);
                break;

            case 10:
                this.setD2nd10(scheduleId);
                break;

            case 11:
                this.setD2nd11(scheduleId);
                break;

            case 12:
                this.setD2nd12(scheduleId);
                break;

            case 13:
                this.setD2nd13(scheduleId);
                break;

            case 14:
                this.setD2nd14(scheduleId);
                break;

            case 15:
                this.setD2nd15(scheduleId);
                break;

            case 16:
                this.setD2nd16(scheduleId);
                break;

            case 17:
                this.setD2nd17(scheduleId);
                break;

            case 18:
                this.setD2nd18(scheduleId);
                break;

            case 19:
                this.setD2nd19(scheduleId);
                break;

            case 20:
                this.setD2nd20(scheduleId);
                break;

            case 21:
                this.setD2nd21(scheduleId);
                break;

            case 22:
                this.setD2nd22(scheduleId);
                break;

            case 23:
                this.setD2nd23(scheduleId);
                break;

            case 24:
                this.setD2nd24(scheduleId);
                break;

            case 25:
                this.setD2nd25(scheduleId);
                break;

            case 26:
                this.setD2nd26(scheduleId);
                break;

            case 27:
                this.setD2nd27(scheduleId);
                break;

            case 28:
                this.setD2nd28(scheduleId);
                break;

            case 29:
                this.setD2nd29(scheduleId);
                break;

            case 30:
                this.setD2nd30(scheduleId);
                break;

            case 31:
                this.setD2nd31(scheduleId);
                break;
        }
        
    }    

    public int getStatus(int index) {
        int status = 0;
        switch (index) {
            case 1:
                status = this.getStatus1();
                break;

            case 2:
                status = this.getStatus2();
                break;

            case 3:
                status = this.getStatus3();
                break;

            case 4:
                status = this.getStatus4();
                break;

            case 5:
                status = this.getStatus5();
                break;

            case 6:
                status = this.getStatus6();
                break;

            case 7:
                status = this.getStatus7();
                break;

            case 8:
                status = this.getStatus8();
                break;

            case 9:
                status = this.getStatus9();
                break;

            case 10:
                status = this.getStatus10();
                break;

            case 11:
                status = this.getStatus11();
                break;

            case 12:
                status = this.getStatus12();
                break;

            case 13:
                status = this.getStatus13();
                break;

            case 14:
                status = this.getStatus14();
                break;

            case 15:
                status = this.getStatus15();
                break;

            case 16:
                status = this.getStatus16();
                break;

            case 17:
                status = this.getStatus17();
                break;

            case 18:
                status = this.getStatus18();
                break;

            case 19:
                status = this.getStatus19();
                break;

            case 20:
                status = this.getStatus20();
                break;

            case 21:
                status = this.getStatus21();
                break;

            case 22:
                status = this.getStatus22();
                break;

            case 23:
                status = this.getStatus23();
                break;

            case 24:
                status = this.getStatus24();
                break;

            case 25:
                status = this.getStatus25();
                break;

            case 26:
                status = this.getStatus26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                status = this.getStatus27();
                break;

            case 28:
                status = this.getStatus28();
                break;

            case 29:
                status = this.getStatus29();
                break;

            case 30:
                status = this.getStatus30();
                break;

            case 31:
                status = this.getStatus31();
                break;
        }

        return status;
    }
    
    public void setStatus(int index, int status) {
        switch (index) {
            case 1:
                this.setStatus1(status);
                break;

            case 2:
                this.setStatus2(status);
                break;

            case 3:
                this.setStatus3(status);
                break;

            case 4:
                this.setStatus4(status);
                break;

            case 5:
                this.setStatus5(status);
                break;

            case 6:
                this.setStatus6(status);
                break;

            case 7:
                this.setStatus7(status);
                break;

            case 8:
                this.setStatus8(status);
                break;

            case 9:
                this.setStatus9(status);
                break;

            case 10:
                this.setStatus10(status);
                break;

            case 11:
                this.setStatus11(status);
                break;

            case 12:
                this.setStatus12(status);
                break;

            case 13:
                this.setStatus13(status);
                break;

            case 14:
                this.setStatus14(status);
                break;

            case 15:
                this.setStatus15(status);
                break;

            case 16:
                this.setStatus16(status);
                break;

            case 17:
                this.setStatus17(status);
                break;

            case 18:
                this.setStatus18(status);
                break;

            case 19:
                this.setStatus19(status);
                break;

            case 20:
                this.setStatus20(status);
                break;

            case 21:
                this.setStatus21(status);
                break;

            case 22:
                this.setStatus22(status);
                break;

            case 23:
                this.setStatus23(status);
                break;

            case 24:
                this.setStatus24(status);
                break;

            case 25:
                this.setStatus25(status);
                break;

            case 26:
                this.setStatus26(status);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setStatus27(status);
                break;

            case 28:
                this.setStatus28(status);
                break;

            case 29:
                this.setStatus29(status);
                break;

            case 30:
                this.setStatus30(status);
                break;

            case 31:
                this.setStatus31(status);
                break;
        }
        
    }

  
    public void setReason(int index, int reason) {
        switch (index) {
            case 1:
                this.setReason1(reason);
                break;

            case 2:
                this.setReason2(reason);
                break;

            case 3:
                this.setReason3(reason);
                break;

            case 4:
                this.setReason4(reason);
                break;

            case 5:
                this.setReason5(reason);
                break;

            case 6:
                this.setReason6(reason);
                break;

            case 7:
                this.setReason7(reason);
                break;

            case 8:
                this.setReason8(reason);
                break;

            case 9:
                this.setReason9(reason);
                break;

            case 10:
                this.setReason10(reason);
                break;

            case 11:
                this.setReason11(reason);
                break;

            case 12:
                this.setReason12(reason);
                break;

            case 13:
                this.setReason13(reason);
                break;

            case 14:
                this.setReason14(reason);
                break;

            case 15:
                this.setReason15(reason);
                break;

            case 16:
                this.setReason16(reason);
                break;

            case 17:
                this.setReason17(reason);
                break;

            case 18:
                this.setReason18(reason);
                break;

            case 19:
                this.setReason19(reason);
                break;

            case 20:
                this.setReason20(reason);
                break;

            case 21:
                this.setReason21(reason);
                break;

            case 22:
                this.setReason22(reason);
                break;

            case 23:
                this.setReason23(reason);
                break;

            case 24:
                this.setReason24(reason);
                break;

            case 25:
                this.setReason25(reason);
                break;

            case 26:
                this.setReason26(reason);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setReason27(reason);
                break;

            case 28:
                this.setReason28(reason);
                break;

            case 29:
                this.setReason29(reason);
                break;

            case 30:
                this.setReason30(reason);
                break;

            case 31:
                this.setReason31(reason);
                break;
        }
        
    }
    
     public String getNote(int index) {
        String note = ""; 
        switch (index) {
            case 1:
                note = this.getNote1();
                break;

            case 2:
                note = this.getNote2();
                break;

            case 3:
                note = this.getNote3();
                break;

            case 4:
                note = this.getNote4();
                break;

            case 5:
                note = this.getNote5();
                break;

            case 6:
                note = this.getNote6();
                break;

            case 7:
                note = this.getNote7();
                break;

            case 8:
                note = this.getNote8();
                break;

            case 9:
                note = this.getNote9();
                break;

            case 10:
                note = this.getNote10();
                break;

            case 11:
                note = this.getNote11();
                break;

            case 12:
                note = this.getNote12();
                break;

            case 13:
                note = this.getNote13();
                break;

            case 14:
                note = this.getNote14();
                break;

            case 15:
                note = this.getNote15();
                break;

            case 16:
                note = this.getNote16();
                break;

            case 17:
                note = this.getNote17();
                break;

            case 18:
                note = this.getNote18();
                break;

            case 19:
                note = this.getNote19();
                break;

            case 20:
                note = this.getNote20();
                break;

            case 21:
                note = this.getNote21();
                break;

            case 22:
                note = this.getNote22();
                break;

            case 23:
                note = this.getNote23();
                break;

            case 24:
                note = this.getNote24();
                break;

            case 25:
                note = this.getNote25();
                break;

            case 26:
                note = this.getNote26();
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                note = this.getNote27();
                break;

            case 28:
                note = this.getNote28();
                break;

            case 29:
                note = this.getNote29();
                break;

            case 30:
                note = this.getNote30();
                break;

            case 31:
                note = this.getNote31();
                break;
        }

        return note;
    }
    
    public void setNote(int index, String note) {
        switch (index) {
            case 1:
                this.setNote1(note);
                break;

            case 2:
                this.setNote2(note);
                break;

            case 3:
                this.setNote3(note);
                break;

            case 4:
                this.setNote4(note);
                break;

            case 5:
                this.setNote5(note);
                break;

            case 6:
                this.setNote6(note);
                break;

            case 7:
                this.setNote7(note);
                break;

            case 8:
                this.setNote8(note);
                break;

            case 9:
                this.setNote9(note);
                break;

            case 10:
                this.setNote10(note);
                break;

            case 11:
                this.setNote11(note);
                break;

            case 12:
                this.setNote12(note);
                break;

            case 13:
                this.setNote13(note);
                break;

            case 14:
                this.setNote14(note);
                break;

            case 15:
                this.setNote15(note);
                break;

            case 16:
                this.setNote16(note);
                break;

            case 17:
                this.setNote17(note);
                break;

            case 18:
                this.setNote18(note);
                break;

            case 19:
                this.setNote19(note);
                break;

            case 20:
                this.setNote20(note);
                break;

            case 21:
                this.setNote21(note);
                break;

            case 22:
                this.setNote22(note);
                break;

            case 23:
                this.setNote23(note);
                break;

            case 24:
                this.setNote24(note);
                break;

            case 25:
                this.setNote25(note);
                break;

            case 26:
                this.setNote26(note);
                //System.out.println("scheduleId  "+scheduleId);
                break;

            case 27:
                this.setNote27(note);
                break;

            case 28:
                this.setNote28(note);
                break;

            case 29:
                this.setNote29(note);
                break;

            case 30:
                this.setNote30(note);
                break;

            case 31:
                this.setNote31(note);
                break;
        }
        
    }
    
    public long getD1() {
        return d1;
    }

    
    public void setD1(long d1) {
        this.d1 = d1;
    }

    public long getD2() {
        return d2;
    }

    public void setD2(long d2) {
        this.d2 = d2;
    }

    public long getD3() {
        return d3;
    }

    public void setD3(long d3) {
        this.d3 = d3;
    }

    public long getD4() {
        return d4;
    }

    public void setD4(long d4) {
        this.d4 = d4;
    }

    public long getD5() {
        return d5;
    }

    public void setD5(long d5) {
        this.d5 = d5;
    }

    public long getD6() {
        return d6;
    }

    public void setD6(long d6) {
        this.d6 = d6;
    }

    public long getD7() {
        return d7;
    }

    public void setD7(long d7) {
        this.d7 = d7;
    }

    public long getD8() {
        return d8;
    }

    public void setD8(long d8) {
        this.d8 = d8;
    }

    public long getD9() {
        return d9;
    }

    public void setD9(long d9) {
        this.d9 = d9;
    }

    public long getD10() {
        return d10;
    }

    public void setD10(long d10) {
        this.d10 = d10;
    }

    public long getD11() {
        return d11;
    }

    public void setD11(long d11) {
        this.d11 = d11;
    }

    public long getD12() {
        return d12;
    }

    public void setD12(long d12) {
        this.d12 = d12;
    }

    public long getD13() {
        return d13;
    }

    public void setD13(long d13) {
        this.d13 = d13;
    }

    public long getD14() {
        return d14;
    }

    public void setD14(long d14) {
        this.d14 = d14;
    }

    public long getD15() {
        return d15;
    }

    public void setD15(long d15) {
        this.d15 = d15;
    }

    public long getD16() {
        return d16;
    }

    public void setD16(long d16) {
        this.d16 = d16;
    }

    public long getD17() {
        return d17;
    }

    public void setD17(long d17) {
        this.d17 = d17;
    }

    public long getD18() {
        return d18;
    }

    public void setD18(long d18) {
        this.d18 = d18;
    }

    public long getD19() {
        return d19;
    }

    public void setD19(long d19) {
        this.d19 = d19;
    }

    public long getD20() {
        return d20;
    }

    public void setD20(long d20) {
        this.d20 = d20;
    }

    public long getD21() {
        return d21;
    }

    public void setD21(long d21) {
        this.d21 = d21;
    }

    public long getD22() {
        return d22;
    }

    public void setD22(long d22) {
        this.d22 = d22;
    }

    public long getD23() {
        return d23;
    }

    public void setD23(long d23) {
        this.d23 = d23;
    }

    public long getD24() {
        return d24;
    }

    public void setD24(long d24) {
        this.d24 = d24;
    }

    public long getD25() {
        return d25;
    }

    public void setD25(long d25) {
        this.d25 = d25;
    }

    public long getD26() {
        return d26;
    }

    public void setD26(long d26) {
        this.d26 = d26;
    }

    public long getD27() {
        return d27;
    }

    public void setD27(long d27) {
        this.d27 = d27;
    }

    public long getD28() {
        return d28;
    }

    public void setD28(long d28) {
        this.d28 = d28;
    }

    public long getD29() {
        return d29;
    }

    public void setD29(long d29) {
        this.d29 = d29;
    }

    public long getD30() {
        return d30;
    }

    public void setD30(long d30) {
        this.d30 = d30;
    }

    public long getD31() {
        return d31;
    }

    public void setD31(long d31) {
        this.d31 = d31;
    }


    // added by Edhy for split shift schedule
    public long getD2nd1() {
        return d2nd1;
    }

    public void setD2nd1(long d2nd1) {
        this.d2nd1 = d2nd1;
    }

    public long getD2nd2() {
        return d2nd2;
    }

    public void setD2nd2(long d2nd2) {
        this.d2nd2 = d2nd2;
    }

    public long getD2nd3() {
        return d2nd3;
    }

    public void setD2nd3(long d2nd3) {
        this.d2nd3 = d2nd3;
    }

    public long getD2nd4() {
        return d2nd4;
    }

    public void setD2nd4(long d2nd4) {
        this.d2nd4 = d2nd4;
    }

    public long getD2nd5() {
        return d2nd5;
    }

    public void setD2nd5(long d2nd5) {
        this.d2nd5 = d2nd5;
    }

    public long getD2nd6() {
        return d2nd6;
    }

    public void setD2nd6(long d2nd6) {
        this.d2nd6 = d2nd6;
    }

    public long getD2nd7() {
        return d2nd7;
    }

    public void setD2nd7(long d2nd7) {
        this.d2nd7 = d2nd7;
    }

    public long getD2nd8() {
        return d2nd8;
    }

    public void setD2nd8(long d2nd8) {
        this.d2nd8 = d2nd8;
    }

    public long getD2nd9() {
        return d2nd9;
    }

    public void setD2nd9(long d2nd9) {
        this.d2nd9 = d2nd9;
    }

    public long getD2nd10() {
        return d2nd10;
    }

    public void setD2nd10(long d2nd10) {
        this.d2nd10 = d2nd10;
    }

    public long getD2nd11() {
        return d2nd11;
    }

    public void setD2nd11(long d2nd11) {
        this.d2nd11 = d2nd11;
    }

    public long getD2nd12() {
        return d2nd12;
    }

    public void setD2nd12(long d2nd12) {
        this.d2nd12 = d2nd12;
    }

    public long getD2nd13() {
        return d2nd13;
    }

    public void setD2nd13(long d2nd13) {
        this.d2nd13 = d2nd13;
    }

    public long getD2nd14() {
        return d2nd14;
    }

    public void setD2nd14(long d2nd14) {
        this.d2nd14 = d2nd14;
    }

    public long getD2nd15() {
        return d2nd15;
    }

    public void setD2nd15(long d2nd15) {
        this.d2nd15 = d2nd15;
    }

    public long getD2nd16() {
        return d2nd16;
    }

    public void setD2nd16(long d2nd16) {
        this.d2nd16 = d2nd16;
    }

    public long getD2nd17() {
        return d2nd17;
    }

    public void setD2nd17(long d2nd17) {
        this.d2nd17 = d2nd17;
    }

    public long getD2nd18() {
        return d2nd18;
    }

    public void setD2nd18(long d2nd18) {
        this.d2nd18 = d2nd18;
    }

    public long getD2nd19() {
        return d2nd19;
    }

    public void setD2nd19(long d2nd19) {
        this.d2nd19 = d2nd19;
    }

    public long getD2nd20() {
        return d2nd20;
    }

    public void setD2nd20(long d2nd20) {
        this.d2nd20 = d2nd20;
    }

    public long getD2nd21() {
        return d2nd21;
    }

    public void setD2nd21(long d2nd21) {
        this.d2nd21 = d2nd21;
    }

    public long getD2nd22() {
        return d2nd22;
    }

    public void setD2nd22(long d2nd22) {
        this.d2nd22 = d2nd22;
    }

    public long getD2nd23() {
        return d2nd23;
    }

    public void setD2nd23(long d2nd23) {
        this.d2nd23 = d2nd23;
    }

    public long getD2nd24() {
        return d2nd24;
    }

    public void setD2nd24(long d2nd24) {
        this.d2nd24 = d2nd24;
    }

    public long getD2nd25() {
        return d2nd25;
    }

    public void setD2nd25(long d2nd25) {
        this.d2nd25 = d2nd25;
    }

    public long getD2nd26() {
        return d2nd26;
    }

    public void setD2nd26(long d2nd26) {
        this.d2nd26 = d2nd26;
    }

    public long getD2nd27() {
        return d2nd27;
    }

    public void setD2nd27(long d2nd27) {
        this.d2nd27 = d2nd27;
    }

    public long getD2nd28() {
        return d2nd28;
    }

    public void setD2nd28(long d2nd28) {
        this.d2nd28 = d2nd28;
    }

    public long getD2nd29() {
        return d2nd29;
    }

    public void setD2nd29(long d2nd29) {
        this.d2nd29 = d2nd29;
    }

    public long getD2nd30() {
        return d2nd30;
    }

    public void setD2nd30(long d2nd30) {
        this.d2nd30 = d2nd30;
    }

    public long getD2nd31() {
        return d2nd31;
    }

    public void setD2nd31(long d2nd31) {
        this.d2nd31 = d2nd31;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public int getStatus3() {
        return status3;
    }

    public void setStatus3(int status3) {
        this.status3 = status3;
    }

    public int getStatus4() {
        return status4;
    }

    public void setStatus4(int status4) {
        this.status4 = status4;
    }

    public int getStatus5() {
        return status5;
    }

    public void setStatus5(int status5) {
        this.status5 = status5;
    }

    public int getStatus6() {
        return status6;
    }

    public void setStatus6(int status6) {
        this.status6 = status6;
    }

    public int getStatus7() {
        return status7;
    }

    public void setStatus7(int status7) {
        this.status7 = status7;
    }

    public int getStatus8() {
        return status8;
    }

    public void setStatus8(int status8) {
        this.status8 = status8;
    }

    public int getStatus9() {
        return status9;
    }

    public void setStatus9(int status9) {
        this.status9 = status9;
    }

    public int getStatus10() {
        return status10;
    }

    public void setStatus10(int status10) {
        this.status10 = status10;
    }

    public int getStatus11() {
        return status11;
    }

    public void setStatus11(int status11) {
        this.status11 = status11;
    }

    public int getStatus12() {
        return status12;
    }

    public void setStatus12(int status12) {
        this.status12 = status12;
    }

    public int getStatus13() {
        return status13;
    }

    public void setStatus13(int status13) {
        this.status13 = status13;
    }

    public int getStatus14() {
        return status14;
    }

    public void setStatus14(int status14) {
        this.status14 = status14;
    }

    public int getStatus15() {
        return status15;
    }

    public void setStatus15(int status15) {
        this.status15 = status15;
    }

    public int getStatus16() {
        return status16;
    }

    public void setStatus16(int status16) {
        this.status16 = status16;
    }

    public int getStatus17() {
        return status17;
    }

    public void setStatus17(int status17) {
        this.status17 = status17;
    }

    public int getStatus18() {
        return status18;
    }

    public void setStatus18(int status18) {
        this.status18 = status18;
    }

    public int getStatus19() {
        return status19;
    }

    public void setStatus19(int status19) {
        this.status19 = status19;
    }

    public int getStatus20() {
        return status20;
    }

    public void setStatus20(int status20) {
        this.status20 = status20;
    }

    public int getStatus21() {
        return status21;
    }

    public void setStatus21(int status21) {
        this.status21 = status21;
    }

    public int getStatus22() {
        return status22;
    }

    public void setStatus22(int status22) {
        this.status22 = status22;
    }

    public int getStatus23() {
        return status23;
    }

    public void setStatus23(int status23) {
        this.status23 = status23;
    }

    public int getStatus24() {
        return status24;
    }

    public void setStatus24(int status24) {
        this.status24 = status24;
    }

    public int getStatus25() {
        return status25;
    }

    public void setStatus25(int status25) {
        this.status25 = status25;
    }

    public int getStatus26() {
        return status26;
    }

    public void setStatus26(int status26) {
        this.status26 = status26;
    }

    public int getStatus27() {
        return status27;
    }

    public void setStatus27(int status27) {
        this.status27 = status27;
    }

    public int getStatus28() {
        return status28;
    }

    public void setStatus28(int status28) {
        this.status28 = status28;
    }

    public int getStatus29() {
        return status29;
    }

    public void setStatus29(int status29) {
        this.status29 = status29;
    }

    public int getStatus30() {
        return status30;
    }

    public void setStatus30(int status30) {
        this.status30 = status30;
    }

    public int getStatus31() {
        return status31;
    }

    public void setStatus31(int status31) {
        this.status31 = status31;
    }

    public int getStatus2nd1() {
        return status2nd1;
    }

    public void setStatus2nd1(int status2nd1) {
        this.status2nd1 = status2nd1;
    }

    public int getStatus2nd2() {
        return status2nd2;
    }

    public void setStatus2nd2(int status2nd2) {
        this.status2nd2 = status2nd2;
    }

    public int getStatus2nd3() {
        return status2nd3;
    }

    public void setStatus2nd3(int status2nd3) {
        this.status2nd3 = status2nd3;
    }

    public int getStatus2nd4() {
        return status2nd4;
    }

    public void setStatus2nd4(int status2nd4) {
        this.status2nd4 = status2nd4;
    }

    public int getStatus2nd5() {
        return status2nd5;
    }

    public void setStatus2nd5(int status2nd5) {
        this.status2nd5 = status2nd5;
    }

    public int getStatus2nd6() {
        return status2nd6;
    }

    public void setStatus2nd6(int status2nd6) {
        this.status2nd6 = status2nd6;
    }

    public int getStatus2nd7() {
        return status2nd7;
    }

    public void setStatus2nd7(int status2nd7) {
        this.status2nd7 = status2nd7;
    }

    public int getStatus2nd8() {
        return status2nd8;
    }

    public void setStatus2nd8(int status2nd8) {
        this.status2nd8 = status2nd8;
    }

    public int getStatus2nd9() {
        return status2nd9;
    }

    public void setStatus2nd9(int status2nd9) {
        this.status2nd9 = status2nd9;
    }

    public int getStatus2nd10() {
        return status2nd10;
    }

    public void setStatus2nd10(int status2nd10) {
        this.status2nd10 = status2nd10;
    }

    public int getStatus2nd11() {
        return status2nd11;
    }

    public void setStatus2nd11(int status2nd11) {
        this.status2nd11 = status2nd11;
    }

    public int getStatus2nd12() {
        return status2nd12;
    }

    public void setStatus2nd12(int status2nd12) {
        this.status2nd12 = status2nd12;
    }

    public int getStatus2nd13() {
        return status2nd13;
    }

    public void setStatus2nd13(int status2nd13) {
        this.status2nd13 = status2nd13;
    }

    public int getStatus2nd14() {
        return status2nd14;
    }

    public void setStatus2nd14(int status2nd14) {
        this.status2nd14 = status2nd14;
    }

    public int getStatus2nd15() {
        return status2nd15;
    }

    public void setStatus2nd15(int status2nd15) {
        this.status2nd15 = status2nd15;
    }

    public int getStatus2nd16() {
        return status2nd16;
    }

    public void setStatus2nd16(int status2nd16) {
        this.status2nd16 = status2nd16;
    }

    public int getStatus2nd17() {
        return status2nd17;
    }

    public void setStatus2nd17(int status2nd17) {
        this.status2nd17 = status2nd17;
    }

    public int getStatus2nd18() {
        return status2nd18;
    }

    public void setStatus2nd18(int status2nd18) {
        this.status2nd18 = status2nd18;
    }

    public int getStatus2nd19() {
        return status2nd19;
    }

    public void setStatus2nd19(int status2nd19) {
        this.status2nd19 = status2nd19;
    }

    public int getStatus2nd20() {
        return status2nd20;
    }

    public void setStatus2nd20(int status2nd20) {
        this.status2nd20 = status2nd20;
    }

    public int getStatus2nd21() {
        return status2nd21;
    }

    public void setStatus2nd21(int status2nd21) {
        this.status2nd21 = status2nd21;
    }

    public int getStatus2nd22() {
        return status2nd22;
    }

    public void setStatus2nd22(int status2nd22) {
        this.status2nd22 = status2nd22;
    }

    public int getStatus2nd23() {
        return status2nd23;
    }

    public void setStatus2nd23(int status2nd23) {
        this.status2nd23 = status2nd23;
    }

    public int getStatus2nd24() {
        return status2nd24;
    }

    public void setStatus2nd24(int status2nd24) {
        this.status2nd24 = status2nd24;
    }

    public int getStatus2nd25() {
        return status2nd25;
    }

    public void setStatus2nd25(int status2nd25) {
        this.status2nd25 = status2nd25;
    }

    public int getStatus2nd26() {
        return status2nd26;
    }

    public void setStatus2nd26(int status2nd26) {
        this.status2nd26 = status2nd26;
    }

    public int getStatus2nd27() {
        return status2nd27;
    }

    public void setStatus2nd27(int status2nd27) {
        this.status2nd27 = status2nd27;
    }

    public int getStatus2nd28() {
        return status2nd28;
    }

    public void setStatus2nd28(int status2nd28) {
        this.status2nd28 = status2nd28;
    }

    public int getStatus2nd29() {
        return status2nd29;
    }

    public void setStatus2nd29(int status2nd29) {
        this.status2nd29 = status2nd29;
    }

    public int getStatus2nd30() {
        return status2nd30;
    }

    public void setStatus2nd30(int status2nd30) {
        this.status2nd30 = status2nd30;
    }

    public int getStatus2nd31() {
        return status2nd31;
    }

    public void setStatus2nd31(int status2nd31) {
        this.status2nd31 = status2nd31;
    }

    public int getReason1() {
        return reason1;
    }

    public void setReason1(int reason1) {
        this.reason1 = reason1;
    }

    public int getReason2() {
        return reason2;
    }

    public void setReason2(int reason2) {
        this.reason2 = reason2;
    }

    public int getReason3() {
        return reason3;
    }

    public void setReason3(int reason3) {
        this.reason3 = reason3;
    }

    public int getReason4() {
        return reason4;
    }

    public void setReason4(int reason4) {
        this.reason4 = reason4;
    }

    public int getReason5() {
        return reason5;
    }

    public void setReason5(int reason5) {
        this.reason5 = reason5;
    }

    public int getReason6() {
        return reason6;
    }

    public void setReason6(int reason6) {
        this.reason6 = reason6;
    }

    public int getReason7() {
        return reason7;
    }

    public void setReason7(int reason7) {
        this.reason7 = reason7;
    }

    public int getReason8() {
        return reason8;
    }

    public void setReason8(int reason8) {
        this.reason8 = reason8;
    }

    public int getReason9() {
        return reason9;
    }

    public void setReason9(int reason9) {
        this.reason9 = reason9;
    }

    public int getReason10() {
        return reason10;
    }

    public void setReason10(int reason10) {
        this.reason10 = reason10;
    }

    public int getReason11() {
        return reason11;
    }

    public void setReason11(int reason11) {
        this.reason11 = reason11;
    }

    public int getReason12() {
        return reason12;
    }

    public void setReason12(int reason12) {
        this.reason12 = reason12;
    }

    public int getReason13() {
        return reason13;
    }

    public void setReason13(int reason13) {
        this.reason13 = reason13;
    }

    public int getReason14() {
        return reason14;
    }

    public void setReason14(int reason14) {
        this.reason14 = reason14;
    }

    public int getReason15() {
        return reason15;
    }

    public void setReason15(int reason15) {
        this.reason15 = reason15;
    }

    public int getReason16() {
        return reason16;
    }

    public void setReason16(int reason16) {
        this.reason16 = reason16;
    }

    public int getReason17() {
        return reason17;
    }

    public void setReason17(int reason17) {
        this.reason17 = reason17;
    }

    public int getReason18() {
        return reason18;
    }

    public void setReason18(int reason18) {
        this.reason18 = reason18;
    }

    public int getReason19() {
        return reason19;
    }

    public void setReason19(int reason19) {
        this.reason19 = reason19;
    }

    public int getReason20() {
        return reason20;
    }

    public void setReason20(int reason20) {
        this.reason20 = reason20;
    }

    public int getReason21() {
        return reason21;
    }

    public void setReason21(int reason21) {
        this.reason21 = reason21;
    }

    public int getReason22() {
        return reason22;
    }

    public void setReason22(int reason22) {
        this.reason22 = reason22;
    }

    public int getReason23() {
        return reason23;
    }

    public void setReason23(int reason23) {
        this.reason23 = reason23;
    }

    public int getReason24() {
        return reason24;
    }

    public void setReason24(int reason24) {
        this.reason24 = reason24;
    }

    public int getReason25() {
        return reason25;
    }

    public void setReason25(int reason25) {
        this.reason25 = reason25;
    }

    public int getReason26() {
        return reason26;
    }

    public void setReason26(int reason26) {
        this.reason26 = reason26;
    }

    public int getReason27() {
        return reason27;
    }

    public void setReason27(int reason27) {
        this.reason27 = reason27;
    }

    public int getReason28() {
        return reason28;
    }

    public void setReason28(int reason28) {
        this.reason28 = reason28;
    }

    public int getReason29() {
        return reason29;
    }

    public void setReason29(int reason29) {
        this.reason29 = reason29;
    }

    public int getReason30() {
        return reason30;
    }

    public void setReason30(int reason30) {
        this.reason30 = reason30;
    }

    public int getReason31() {
        return reason31;
    }

    public void setReason31(int reason31) {
        this.reason31 = reason31;
    }

    public int getReason2nd1() {
        return reason2nd1;
    }

    public void setReason2nd1(int reason2nd1) {
        this.reason2nd1 = reason2nd1;
    }

    public int getReason2nd2() {
        return reason2nd2;
    }

    public void setReason2nd2(int reason2nd2) {
        this.reason2nd2 = reason2nd2;
    }

    public int getReason2nd3() {
        return reason2nd3;
    }

    public void setReason2nd3(int reason2nd3) {
        this.reason2nd3 = reason2nd3;
    }

    public int getReason2nd4() {
        return reason2nd4;
    }

    public void setReason2nd4(int reason2nd4) {
        this.reason2nd4 = reason2nd4;
    }

    public int getReason2nd5() {
        return reason2nd5;
    }

    public void setReason2nd5(int reason2nd5) {
        this.reason2nd5 = reason2nd5;
    }

    public int getReason2nd6() {
        return reason2nd6;
    }

    public void setReason2nd6(int reason2nd6) {
        this.reason2nd6 = reason2nd6;
    }

    public int getReason2nd7() {
        return reason2nd7;
    }

    public void setReason2nd7(int reason2nd7) {
        this.reason2nd7 = reason2nd7;
    }

    public int getReason2nd8() {
        return reason2nd8;
    }

    public void setReason2nd8(int reason2nd8) {
        this.reason2nd8 = reason2nd8;
    }

    public int getReason2nd9() {
        return reason2nd9;
    }

    public void setReason2nd9(int reason2nd9) {
        this.reason2nd9 = reason2nd9;
    }

    public int getReason2nd10() {
        return reason2nd10;
    }

    public void setReason2nd10(int reason2nd10) {
        this.reason2nd10 = reason2nd10;
    }

    public int getReason2nd11() {
        return reason2nd11;
    }

    public void setReason2nd11(int reason2nd11) {
        this.reason2nd11 = reason2nd11;
    }

    public int getReason2nd12() {
        return reason2nd12;
    }

    public void setReason2nd12(int reason2nd12) {
        this.reason2nd12 = reason2nd12;
    }

    public int getReason2nd13() {
        return reason2nd13;
    }

    public void setReason2nd13(int reason2nd13) {
        this.reason2nd13 = reason2nd13;
    }

    public int getReason2nd14() {
        return reason2nd14;
    }

    public void setReason2nd14(int reason2nd14) {
        this.reason2nd14 = reason2nd14;
    }

    public int getReason2nd15() {
        return reason2nd15;
    }

    public void setReason2nd15(int reason2nd15) {
        this.reason2nd15 = reason2nd15;
    }

    public int getReason2nd16() {
        return reason2nd16;
    }

    public void setReason2nd16(int reason2nd16) {
        this.reason2nd16 = reason2nd16;
    }

    public int getReason2nd17() {
        return reason2nd17;
    }

    public void setReason2nd17(int reason2nd17) {
        this.reason2nd17 = reason2nd17;
    }

    public int getReason2nd18() {
        return reason2nd18;
    }

    public void setReason2nd18(int reason2nd18) {
        this.reason2nd18 = reason2nd18;
    }

    public int getReason2nd19() {
        return reason2nd19;
    }

    public void setReason2nd19(int reason2nd19) {
        this.reason2nd19 = reason2nd19;
    }

    public int getReason2nd20() {
        return reason2nd20;
    }

    public void setReason2nd20(int reason2nd20) {
        this.reason2nd20 = reason2nd20;
    }

    public int getReason2nd21() {
        return reason2nd21;
    }

    public void setReason2nd21(int reason2nd21) {
        this.reason2nd21 = reason2nd21;
    }

    public int getReason2nd22() {
        return reason2nd22;
    }

    public void setReason2nd22(int reason2nd22) {
        this.reason2nd22 = reason2nd22;
    }

    public int getReason2nd23() {
        return reason2nd23;
    }

    public void setReason2nd23(int reason2nd23) {
        this.reason2nd23 = reason2nd23;
    }

    public int getReason2nd24() {
        return reason2nd24;
    }

    public void setReason2nd24(int reason2nd24) {
        this.reason2nd24 = reason2nd24;
    }

    public int getReason2nd25() {
        return reason2nd25;
    }

    public void setReason2nd25(int reason2nd25) {
        this.reason2nd25 = reason2nd25;
    }

    public int getReason2nd26() {
        return reason2nd26;
    }

    public void setReason2nd26(int reason2nd26) {
        this.reason2nd26 = reason2nd26;
    }

    public int getReason2nd27() {
        return reason2nd27;
    }

    public void setReason2nd27(int reason2nd27) {
        this.reason2nd27 = reason2nd27;
    }

    public int getReason2nd28() {
        return reason2nd28;
    }

    public void setReason2nd28(int reason2nd28) {
        this.reason2nd28 = reason2nd28;
    }

    public int getReason2nd29() {
        return reason2nd29;
    }

    public void setReason2nd29(int reason2nd29) {
        this.reason2nd29 = reason2nd29;
    }

    public int getReason2nd30() {
        return reason2nd30;
    }

    public void setReason2nd30(int reason2nd30) {
        this.reason2nd30 = reason2nd30;
    }

    public int getReason2nd31() {
        return reason2nd31;
    }

    public void setReason2nd31(int reason2nd31) {
        this.reason2nd31 = reason2nd31;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getNote3() {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3;
    }

    public String getNote4() {
        return note4;
    }

    public void setNote4(String note4) {
        this.note4 = note4;
    }

    public String getNote5() {
        return note5;
    }

    public void setNote5(String note5) {
        this.note5 = note5;
    }

    public String getNote6() {
        return note6;
    }

    public void setNote6(String note6) {
        this.note6 = note6;
    }

    public String getNote7() {
        return note7;
    }

    public void setNote7(String note7) {
        this.note7 = note7;
    }

    public String getNote8() {
        return note8;
    }

    public void setNote8(String note8) {
        this.note8 = note8;
    }

    public String getNote9() {
        return note9;
    }

    public void setNote9(String note9) {
        this.note9 = note9;
    }

    public String getNote10() {
        return note10;
    }

    public void setNote10(String note10) {
        this.note10 = note10;
    }

    public String getNote11() {
        return note11;
    }

    public void setNote11(String note11) {
        this.note11 = note11;
    }

    public String getNote12() {
        return note12;
    }

    public void setNote12(String note12) {
        this.note12 = note12;
    }

    public String getNote13() {
        return note13;
    }

    public void setNote13(String note13) {
        this.note13 = note13;
    }

    public String getNote14() {
        return note14;
    }

    public void setNote14(String note14) {
        this.note14 = note14;
    }

    public String getNote15() {
        return note15;
    }

    public void setNote15(String note15) {
        this.note15 = note15;
    }

    public String getNote16() {
        return note16;
    }

    public void setNote16(String note16) {
        this.note16 = note16;
    }

    public String getNote17() {
        return note17;
    }

    public void setNote17(String note17) {
        this.note17 = note17;
    }

    public String getNote18() {
        return note18;
    }

    public void setNote18(String note18) {
        this.note18 = note18;
    }

    public String getNote19() {
        return note19;
    }

    public void setNote19(String note19) {
        this.note19 = note19;
    }

    public String getNote20() {
        return note20;
    }

    public void setNote20(String note20) {
        this.note20 = note20;
    }

    public String getNote21() {
        return note21;
    }

    public void setNote21(String note21) {
        this.note21 = note21;
    }

    public String getNote22() {
        return note22;
    }

    public void setNote22(String note22) {
        this.note22 = note22;
    }

    public String getNote23() {
        return note23;
    }

    public void setNote23(String note23) {
        this.note23 = note23;
    }

    public String getNote24() {
        return note24;
    }

    public void setNote24(String note24) {
        this.note24 = note24;
    }

    public String getNote25() {
        return note25;
    }

    public void setNote25(String note25) {
        this.note25 = note25;
    }

    public String getNote26() {
        return note26;
    }

    public void setNote26(String note26) {
        this.note26 = note26;
    }

    public String getNote27() {
        return note27;
    }

    public void setNote27(String note27) {
        this.note27 = note27;
    }

    public String getNote28() {
        return note28;
    }

    public void setNote28(String note28) {
        this.note28 = note28;
    }

    public String getNote29() {
        return note29;
    }

    public void setNote29(String note29) {
        this.note29 = note29;
    }

    public String getNote30() {
        return note30;
    }

    public void setNote30(String note30) {
        this.note30 = note30;
    }

    public String getNote31() {
        return note31;
    }

    public void setNote31(String note31) {
        this.note31 = note31;
    }

    public String getNote2nd1() {
        return note2nd11;
    }

    public void setNote2nd1(String note2nd1) {
        this.note2nd1 = note2nd1;
    }

    public String getNote2nd2() {
        return note2nd12;
    }

    public void setNote2nd2(String note2nd2) {
        this.note2nd2 = note2nd2;
    }

    public String getNote2nd3() {
        return note2nd13;
    }

    public void setNote2nd3(String note2nd3) {
        this.note2nd3 = note2nd3;
    }

    public String getNote2nd4() {
        return note2nd14;
    }

    public void setNote2nd4(String note2nd4) {
        this.note2nd4 = note2nd4;
    }

    public String getNote2nd5() {
        return note2nd15;
    }

    public void setNote2nd5(String note2nd5) {
        this.note2nd5 = note2nd5;
    }

    public String getNote2nd6() {
        return note2nd16;
    }

    public void setNote2nd6(String note2nd6) {
        this.note2nd6 = note2nd6;
    }

    public String getNote2nd7() {
        return note2nd17;
    }

    public void setNote2nd7(String note2nd7) {
        this.note2nd7 = note2nd7;
    }

    public String getNote2nd8() {
        return note2nd18;
    }

    public void setNote2nd8(String note2nd8) {
        this.note2nd8 = note2nd8;
    }

    public String getNote2nd9() {
        return note2nd19;
    }

    public void setNote2nd9(String note2nd9) {
        this.note2nd9 = note2nd9;
    }

    public String getNote2nd10() {
        return note2nd10;
    }

    public void setNote2nd10(String note2nd10) {
        this.note2nd10 = note2nd10;
    }

    public String getNote2nd11() {
        return note2nd11;
    }

    public void setNote2nd11(String note2nd11) {
        this.note2nd11 = note2nd11;
    }

    public String getNote2nd12() {
        return note2nd12;
    }

    public void setNote2nd12(String note2nd12) {
        this.note2nd12 = note2nd12;
    }

    public String getNote2nd13() {
        return note2nd13;
    }

    public void setNote2nd13(String note2nd13) {
        this.note2nd13 = note2nd13;
    }

    public String getNote2nd14() {
        return note2nd14;
    }

    public void setNote2nd14(String note2nd14) {
        this.note2nd14 = note2nd14;
    }

    public String getNote2nd15() {
        return note2nd15;
    }

    public void setNote2nd15(String note2nd15) {
        this.note2nd15 = note2nd15;
    }

    public String getNote2nd16() {
        return note2nd16;
    }

    public void setNote2nd16(String note2nd16) {
        this.note2nd16 = note2nd16;
    }

    public String getNote2nd17() {
        return note2nd17;
    }

    public void setNote2nd17(String note2nd17) {
        this.note2nd17 = note2nd17;
    }

    public String getNote2nd18() {
        return note2nd18;
    }

    public void setNote2nd18(String note2nd18) {
        this.note2nd18 = note2nd18;
    }

    public String getNote2nd19() {
        return note2nd19;
    }

    public void setNote2nd19(String note2nd19) {
        this.note2nd19 = note2nd19;
    }

    public String getNote2nd20() {
        return note2nd20;
    }

    public void setNote2nd20(String note2nd20) {
        this.note2nd20 = note2nd20;
    }

    public String getNote2nd21() {
        return note2nd21;
    }

    public void setNote2nd21(String note2nd21) {
        this.note2nd21 = note2nd21;
    }

    public String getNote2nd22() {
        return note2nd22;
    }

    public void setNote2nd22(String note2nd22) {
        this.note2nd22 = note2nd22;
    }

    public String getNote2nd23() {
        return note2nd23;
    }

    public void setNote2nd23(String note2nd23) {
        this.note2nd23 = note2nd23;
    }

    public String getNote2nd24() {
        return note2nd24;
    }

    public void setNote2nd24(String note2nd24) {
        this.note2nd24 = note2nd24;
    }

    public String getNote2nd25() {
        return note2nd25;
    }

    public void setNote2nd25(String note2nd25) {
        this.note2nd25 = note2nd25;
    }

    public String getNote2nd26() {
        return note2nd26;
    }

    public void setNote2nd26(String note2nd26) {
        this.note2nd26 = note2nd26;
    }

    public String getNote2nd27() {
        return note2nd27;
    }

    public void setNote2nd27(String note2nd27) {
        this.note2nd27 = note2nd27;
    }

    public String getNote2nd28() {
        return note2nd28;
    }

    public void setNote2nd28(String note2nd28) {
        this.note2nd28 = note2nd28;
    }

    public String getNote2nd29() {
        return note2nd29;
    }

    public void setNote2nd29(String note2nd29) {
        this.note2nd29 = note2nd29;
    }

    public String getNote2nd30() {
        return note2nd30;
    }

    public void setNote2nd30(String note2nd30) {
        this.note2nd30 = note2nd30;
    }

    public String getNote2nd31() {
        return note2nd31;
    }

    public void setNote2nd31(String note2nd31) {
        this.note2nd31 = note2nd31;
    }

    public Date getIn1() {
        return in1;
    }

    public void setIn1(Date in1) {
        this.in1 = in1;
    }

    public Date getIn2() {
        return in2;
    }

    public void setIn2(Date in2) {
        this.in2 = in2;
    }

    public Date getIn3() {
        return in3;
    }

    public void setIn3(Date in3) {
        this.in3 = in3;
    }

    public Date getIn4() {
        return in4;
    }

    public void setIn4(Date in4) {
        this.in4 = in4;
    }

    public Date getIn5() {
        return in5;
    }

    public void setIn5(Date in5) {
        this.in5 = in5;
    }

    public Date getIn6() {
        return in6;
    }

    public void setIn6(Date in6) {
        this.in6 = in6;
    }

    public Date getIn7() {
        return in7;
    }

    public void setIn7(Date in7) {
        this.in7 = in7;
    }

    public Date getIn8() {
        return in8;
    }

    public void setIn8(Date in8) {
        this.in8 = in8;
    }

    public Date getIn9() {
        return in9;
    }

    public void setIn9(Date in9) {
        this.in9 = in9;
    }

    public Date getIn10() {
        return in10;
    }

    public void setIn10(Date in10) {
        this.in10 = in10;
    }

    public Date getIn11() {
        return in11;
    }

    public void setIn11(Date in11) {
        this.in11 = in11;
    }

    public Date getIn12() {
        return in12;
    }

    public void setIn12(Date in12) {
        this.in12 = in12;
    }

    public Date getIn13() {
        return in13;
    }

    public void setIn13(Date in13) {
        this.in13 = in13;
    }

    public Date getIn14() {
        return in14;
    }

    public void setIn14(Date in14) {
        this.in14 = in14;
    }

    public Date getIn15() {
        return in15;
    }

    public void setIn15(Date in15) {
        this.in15 = in15;
    }

    public Date getIn16() {
        return in16;
    }

    public void setIn16(Date in16) {
        this.in16 = in16;
    }

    public Date getIn17() {
        return in17;
    }

    public void setIn17(Date in17) {
        this.in17 = in17;
    }

    public Date getIn18() {
        return in18;
    }

    public void setIn18(Date in18) {
        this.in18 = in18;
    }

    public Date getIn19() {
        return in19;
    }

    public void setIn19(Date in19) {
        this.in19 = in19;
    }

    public Date getIn20() {
        return in20;
    }

    public void setIn20(Date in20) {
        this.in20 = in20;
    }

    public Date getIn21() {
        return in21;
    }

    public void setIn21(Date in21) {
        this.in21 = in21;
    }

    public Date getIn22() {
        return in22;
    }

    public void setIn22(Date in22) {
        this.in22 = in22;
    }

    public Date getIn23() {
        return in23;
    }

    public void setIn23(Date in23) {
        this.in23 = in23;
    }

    public Date getIn24() {
        return in24;
    }

    public void setIn24(Date in24) {
        this.in24 = in24;
    }

    public Date getIn25() {
        return in25;
    }

    public void setIn25(Date in25) {
        this.in25 = in25;
    }

    public Date getIn26() {
        return in26;
    }

    public void setIn26(Date in26) {
        this.in26 = in26;
    }

    public Date getIn27() {
        return in27;
    }

    public void setIn27(Date in27) {
        this.in27 = in27;
    }

    public Date getIn28() {
        return in28;
    }

    public void setIn28(Date in28) {
        this.in28 = in28;
    }

    public Date getIn29() {
        return in29;
    }

    public void setIn29(Date in29) {
        this.in29 = in29;
    }

    public Date getIn30() {
        return in30;
    }

    public void setIn30(Date in30) {
        this.in30 = in30;
    }

    public Date getIn31() {
        return in31;
    }

    public void setIn31(Date in31) {
        this.in31 = in31;
    }

    public Date getIn2nd1() {
        return in2nd1;
    }

    public void setIn2nd1(Date in2nd1) {
        this.in2nd1 = in2nd1;
    }

    public Date getIn2nd2() {
        return in2nd2;
    }

    public void setIn2nd2(Date in2nd2) {
        this.in2nd2 = in2nd2;
    }

    public Date getIn2nd3() {
        return in2nd3;
    }

    public void setIn2nd3(Date in2nd3) {
        this.in2nd3 = in2nd3;
    }

    public Date getIn2nd4() {
        return in2nd4;
    }

    public void setIn2nd4(Date in2nd4) {
        this.in2nd4 = in2nd4;
    }

    public Date getIn2nd5() {
        return in2nd5;
    }

    public void setIn2nd5(Date in2nd5) {
        this.in2nd5 = in2nd5;
    }

    public Date getIn2nd6() {
        return in2nd6;
    }

    public void setIn2nd6(Date in2nd6) {
        this.in2nd6 = in2nd6;
    }

    public Date getIn2nd7() {
        return in2nd7;
    }

    public void setIn2nd7(Date in2nd7) {
        this.in2nd7 = in2nd7;
    }

    public Date getIn2nd8() {
        return in2nd8;
    }

    public void setIn2nd8(Date in2nd8) {
        this.in2nd8 = in2nd8;
    }

    public Date getIn2nd9() {
        return in2nd9;
    }

    public void setIn2nd9(Date in2nd9) {
        this.in2nd9 = in2nd9;
    }

    public Date getIn2nd10() {
        return in2nd10;
    }

    public void setIn2nd10(Date in2nd10) {
        this.in2nd10 = in2nd10;
    }

    public Date getIn2nd11() {
        return in2nd11;
    }

    public void setIn2nd11(Date in2nd11) {
        this.in2nd11 = in2nd11;
    }

    public Date getIn2nd12() {
        return in2nd12;
    }

    public void setIn2nd12(Date in2nd12) {
        this.in2nd12 = in2nd12;
    }

    public Date getIn2nd13() {
        return in2nd13;
    }

    public void setIn2nd13(Date in2nd13) {
        this.in2nd13 = in2nd13;
    }

    public Date getIn2nd14() {
        return in2nd14;
    }

    public void setIn2nd14(Date in2nd14) {
        this.in2nd14 = in2nd14;
    }

    public Date getIn2nd15() {
        return in2nd15;
    }

    public void setIn2nd15(Date in2nd15) {
        this.in2nd15 = in2nd15;
    }

    public Date getIn2nd16() {
        return in2nd16;
    }

    public void setIn2nd16(Date in2nd16) {
        this.in2nd16 = in2nd16;
    }

    public Date getIn2nd17() {
        return in2nd17;
    }

    public void setIn2nd17(Date in2nd17) {
        this.in2nd17 = in2nd17;
    }

    public Date getIn2nd18() {
        return in2nd18;
    }

    public void setIn2nd18(Date in2nd18) {
        this.in2nd18 = in2nd18;
    }

    public Date getIn2nd19() {
        return in2nd19;
    }

    public void setIn2nd19(Date in2nd19) {
        this.in2nd19 = in2nd19;
    }

    public Date getIn2nd20() {
        return in2nd20;
    }

    public void setIn2nd20(Date in2nd20) {
        this.in2nd20 = in2nd20;
    }

    public Date getIn2nd21() {
        return in2nd21;
    }

    public void setIn2nd21(Date in2nd21) {
        this.in2nd21 = in2nd21;
    }

    public Date getIn2nd22() {
        return in2nd22;
    }

    public void setIn2nd22(Date in2nd22) {
        this.in2nd22 = in2nd22;
    }

    public Date getIn2nd23() {
        return in2nd23;
    }

    public void setIn2nd23(Date in2nd23) {
        this.in2nd23 = in2nd23;
    }

    public Date getIn2nd24() {
        return in2nd24;
    }

    public void setIn2nd24(Date in2nd24) {
        this.in2nd24 = in2nd24;
    }

    public Date getIn2nd25() {
        return in2nd25;
    }

    public void setIn2nd25(Date in2nd25) {
        this.in2nd25 = in2nd25;
    }

    public Date getIn2nd26() {
        return in2nd26;
    }

    public void setIn2nd26(Date in2nd26) {
        this.in2nd26 = in2nd26;
    }

    public Date getIn2nd27() {
        return in2nd27;
    }

    public void setIn2nd27(Date in2nd27) {
        this.in2nd27 = in2nd27;
    }

    public Date getIn2nd28() {
        return in2nd28;
    }

    public void setIn2nd28(Date in2nd28) {
        this.in2nd28 = in2nd28;
    }

    public Date getIn2nd29() {
        return in2nd29;
    }

    public void setIn2nd29(Date in2nd29) {
        this.in2nd29 = in2nd29;
    }

    public Date getIn2nd30() {
        return in2nd30;
    }

    public void setIn2nd30(Date in2nd30) {
        this.in2nd30 = in2nd30;
    }

    public Date getIn2nd31() {
        return in2nd31;
    }

    public void setIn2nd31(Date in2nd31) {
        this.in2nd31 = in2nd31;
    }

    public Date getOut1() {
        return out1;
    }

    public void setOut1(Date out1) {
        this.out1 = out1;
    }

    public Date getOut2() {
        return out2;
    }

    public void setOut2(Date out2) {
        this.out2 = out2;
    }

    public Date getOut3() {
        return out3;
    }

    public void setOut3(Date out3) {
        this.out3 = out3;
    }

    public Date getOut4() {
        return out4;
    }

    public void setOut4(Date out4) {
        this.out4 = out4;
    }

    public Date getOut5() {
        return out5;
    }

    public void setOut5(Date out5) {
        this.out5 = out5;
    }

    public Date getOut6() {
        return out6;
    }

    public void setOut6(Date out6) {
        this.out6 = out6;
    }

    public Date getOut7() {
        return out7;
    }

    public void setOut7(Date out7) {
        this.out7 = out7;
    }

    public Date getOut8() {
        return out8;
    }

    public void setOut8(Date out8) {
        this.out8 = out8;
    }

    public Date getOut9() {
        return out9;
    }

    public void setOut9(Date out9) {
        this.out9 = out9;
    }

    public Date getOut10() {
        return out10;
    }

    public void setOut10(Date out10) {
        this.out10 = out10;
    }

    public Date getOut11() {
        return out11;
    }

    public void setOut11(Date out11) {
        this.out11 = out11;
    }

    public Date getOut12() {
        return out12;
    }

    public void setOut12(Date out12) {
        this.out12 = out12;
    }

    public Date getOut13() {
        return out13;
    }

    public void setOut13(Date out13) {
        this.out13 = out13;
    }

    public Date getOut14() {
        return out14;
    }

    public void setOut14(Date out14) {
        this.out14 = out14;
    }

    public Date getOut15() {
        return out15;
    }

    public void setOut15(Date out15) {
        this.out15 = out15;
    }

    public Date getOut16() {
        return out16;
    }

    public void setOut16(Date out16) {
        this.out16 = out16;
    }

    public Date getOut17() {
        return out17;
    }

    public void setOut17(Date out17) {
        this.out17 = out17;
    }

    public Date getOut18() {
        return out18;
    }

    public void setOut18(Date out18) {
        this.out18 = out18;
    }

    public Date getOut19() {
        return out19;
    }

    public void setOut19(Date out19) {
        this.out19 = out19;
    }

    public Date getOut20() {
        return out20;
    }

    public void setOut20(Date out20) {
        this.out20 = out20;
    }

    public Date getOut21() {
        return out21;
    }

    public void setOut21(Date out21) {
        this.out21 = out21;
    }

    public Date getOut22() {
        return out22;
    }

    public void setOut22(Date out22) {
        this.out22 = out22;
    }

    public Date getOut23() {
        return out23;
    }

    public void setOut23(Date out23) {
        this.out23 = out23;
    }

    public Date getOut24() {
        return out24;
    }

    public void setOut24(Date out24) {
        this.out24 = out24;
    }

    public Date getOut25() {
        return out25;
    }

    public void setOut25(Date out25) {
        this.out25 = out25;
    }

    public Date getOut26() {
        return out26;
    }

    public void setOut26(Date out26) {
        this.out26 = out26;
    }

    public Date getOut27() {
        return out27;
    }

    public void setOut27(Date out27) {
        this.out27 = out27;
    }

    public Date getOut28() {
        return out28;
    }

    public void setOut28(Date out28) {
        this.out28 = out28;
    }

    public Date getOut29() {
        return out29;
    }

    public void setOut29(Date out29) {
        this.out29 = out29;
    }

    public Date getOut30() {
        return out30;
    }

    public void setOut30(Date out30) {
        this.out30 = out30;
    }

    public Date getOut31() {
        return out31;
    }

    public void setOut31(Date out31) {
        this.out31 = out31;
    }

    public Date getOut2nd1() {
        return out2nd1;
    }

    public void setOut2nd1(Date out2nd1) {
        this.out2nd1 = out2nd1;
    }

    public Date getOut2nd2() {
        return out2nd2;
    }

    public void setOut2nd2(Date out2nd2) {
        this.out2nd2 = out2nd2;
    }

    public Date getOut2nd3() {
        return out2nd3;
    }

    public void setOut2nd3(Date out2nd3) {
        this.out2nd3 = out2nd3;
    }

    public Date getOut2nd4() {
        return out2nd4;
    }

    public void setOut2nd4(Date out2nd4) {
        this.out2nd4 = out2nd4;
    }

    public Date getOut2nd5() {
        return out2nd5;
    }

    public void setOut2nd5(Date out2nd5) {
        this.out2nd5 = out2nd5;
    }

    public Date getOut2nd6() {
        return out2nd6;
    }

    public void setOut2nd6(Date out2nd6) {
        this.out2nd6 = out2nd6;
    }

    public Date getOut2nd7() {
        return out2nd7;
    }

    public void setOut2nd7(Date out2nd7) {
        this.out2nd7 = out2nd7;
    }

    public Date getOut2nd8() {
        return out2nd8;
    }

    public void setOut2nd8(Date out2nd8) {
        this.out2nd8 = out2nd8;
    }

    public Date getOut2nd9() {
        return out2nd9;
    }

    public void setOut2nd9(Date out2nd9) {
        this.out2nd9 = out2nd9;
    }

    public Date getOut2nd10() {
        return out2nd10;
    }

    public void setOut2nd10(Date out2nd10) {
        this.out2nd10 = out2nd10;
    }

    public Date getOut2nd11() {
        return out2nd11;
    }

    public void setOut2nd11(Date out2nd11) {
        this.out2nd11 = out2nd11;
    }

    public Date getOut2nd12() {
        return out2nd12;
    }

    public void setOut2nd12(Date out2nd12) {
        this.out2nd12 = out2nd12;
    }

    public Date getOut2nd13() {
        return out2nd13;
    }

    public void setOut2nd13(Date out2nd13) {
        this.out2nd13 = out2nd13;
    }

    public Date getOut2nd14() {
        return out2nd14;
    }

    public void setOut2nd14(Date out2nd14) {
        this.out2nd14 = out2nd14;
    }

    public Date getOut2nd15() {
        return out2nd15;
    }

    public void setOut2nd15(Date out2nd15) {
        this.out2nd15 = out2nd15;
    }

    public Date getOut2nd16() {
        return out2nd16;
    }

    public void setOut2nd16(Date out2nd16) {
        this.out2nd16 = out2nd16;
    }

    public Date getOut2nd17() {
        return out2nd17;
    }

    public void setOut2nd17(Date out2nd17) {
        this.out2nd17 = out2nd17;
    }

    public Date getOut2nd18() {
        return out2nd1;
    }

    public void setOut2nd18(Date out2nd18) {
        this.out2nd18 = out2nd18;
    }

    public Date getOut2nd19() {
        return out2nd19;
    }

    public void setOut2nd19(Date out2nd19) {
        this.out2nd19 = out2nd19;
    }

    public Date getOut2nd20() {
        return out2nd20;
    }

    public void setOut2nd20(Date out2nd20) {
        this.out2nd20 = out2nd20;
    }

    public Date getOut2nd21() {
        return out2nd21;
    }

    public void setOut2nd21(Date out2nd21) {
        this.out2nd21 = out2nd21;
    }

    public Date getOut2nd22() {
        return out2nd22;
    }

    public void setOut2nd22(Date out2nd22) {
        this.out2nd22 = out2nd22;
    }

    public Date getOut2nd23() {
        return out2nd23;
    }

    public void setOut2nd23(Date out2nd23) {
        this.out2nd23 = out2nd23;
    }

    public Date getOut2nd24() {
        return out2nd24;
    }

    public void setOut2nd24(Date out2nd24) {
        this.out2nd24 = out2nd24;
    }

    public Date getOut2nd25() {
        return out2nd25;
    }

    public void setOut2nd25(Date out2nd25) {
        this.out2nd25 = out2nd25;
    }

    public Date getOut2nd26() {
        return out2nd26;
    }

    public void setOut2nd26(Date out2nd26) {
        this.out2nd26 = out2nd26;
    }

    public Date getOut2nd27() {
        return out2nd27;
    }

    public void setOut2nd27(Date out2nd27) {
        this.out2nd27 = out2nd27;
    }

    public Date getOut2nd28() {
        return out2nd28;
    }

    public void setOut2nd28(Date out2nd28) {
        this.out2nd28 = out2nd28;
    }

    public Date getOut2nd29() {
        return out2nd29;
    }

    public void setOut2nd29(Date out2nd29) {
        this.out2nd29 = out2nd29;
    }

    public Date getOut2nd30() {
        return out2nd30;
    }

    public void setOut2nd30(Date out2nd30) {
        this.out2nd30 = out2nd30;
    }

    public Date getOut2nd31() {
        return out2nd31;
    }

    public void setOut2nd31(Date out2nd31) {
        this.out2nd31 = out2nd31;
    }
    
    
    public int numberOfScheduleSymbol(Vector schSymbols){
        if( (schSymbols==null) || (schSymbols.size()<1) )
            return 0;
        int nr=0;
        for(int i=0;i< schSymbols.size();i++ ){           
            long id = ((Long)schSymbols.get(i)).longValue();
            for(int s=1;s<32;s++){
               if(this.getD(s)==id){
                   nr++;
               }
           }
        }
        return nr;
    }

    public int matchOfScheduleSymbol(int idx, Vector schSymbols){
        if( (schSymbols==null) || (schSymbols.size()<1) )
            return 0;
        int nr=0;
        for(int i=0;i< schSymbols.size();i++ ){           
            long id = ((Long)schSymbols.get(i)).longValue();            
            if(this.getD(idx)==id){
                   nr++;
            }
           
        }
        return nr;
    }
    
    
    /**
     * Check the date when the employee has schedule symbol that match on vector of schedule symbols
     * @param schSymbols     : vector of schedule symbol to be match
     * @param dateStartPeriod : start date of periode
     * @return
     */
    public Vector dateOfScheduleSymbol(Vector schSymbols, Date dateStartPeriod ){
        Vector  datesMatch=new Vector();
        if( (schSymbols==null) || (schSymbols.size()<1) || (dateStartPeriod==null)  )
            return datesMatch;
        for(int i=0;i< schSymbols.size();i++ ){           
            long id = ((Long)schSymbols.get(i)).longValue();
            for(int s=1;s<32;s++){
               if(this.getD(s)==id){
                   Date dt = new Date(dateStartPeriod.getTime());
                   if(s<dateStartPeriod.getDate()){  // date probe less than start date then , the month will be after the month on start date of period
                       dt.setDate(s);
                       dt.setMonth(dt.getMonth()+1);                       
                   } else {
                       dt.setDate(s);                       
                   }
                   datesMatch.add(dt);
               }
           }
        }
        return datesMatch;
    }

     /**
     * Get duration ( Out - In ) of first schedule or not splitted schedule
     * @param index
     * @return duration in milliseconds
     * @author Ketut Kartika
     */

    public long getPresentDurationMilSeconds(int index){
        long lDuration=0;
        try{
        switch (index) {
            case 1:
                lDuration = this.getOut1().getTime()- this.getIn1().getTime();
                break;

            case 2:
                lDuration = this.getOut2().getTime()- this.getIn2().getTime();
                break;

            case 3:
                lDuration = this.getOut3().getTime()- this.getIn3().getTime();
                break;

            case 4:
                lDuration = this.getOut4().getTime()- this.getIn4().getTime();
                break;

            case 5:
                lDuration = this.getOut5().getTime()- this.getIn5().getTime();
                break;

            case 6:
                lDuration = this.getOut6().getTime()- this.getIn6().getTime();
                break;

            case 7:
                lDuration = this.getOut7().getTime()- this.getIn7().getTime();
                break;

            case 8:
                lDuration = this.getOut8().getTime()- this.getIn8().getTime();
                break;

            case 9:
                lDuration = this.getOut9().getTime()- this.getIn9().getTime();
                break;

            case 10:
                lDuration = this.getOut10().getTime()- this.getIn10().getTime();
                break;

            case 11:
                lDuration = this.getOut11().getTime()- this.getIn11().getTime();
                break;

            case 12:
                lDuration = this.getOut12().getTime()- this.getIn12().getTime();
                break;

            case 13:
                lDuration = this.getOut13().getTime()- this.getIn13().getTime();
                break;

            case 14:
                lDuration = this.getOut14().getTime()- this.getIn14().getTime();
                break;

            case 15:
                lDuration = this.getOut15().getTime()- this.getIn15().getTime();
                break;

            case 16:
                lDuration = this.getOut16().getTime()- this.getIn16().getTime();
                break;

            case 17:
                lDuration = this.getOut17().getTime()- this.getIn17().getTime();
                break;

            case 18:
                lDuration = this.getOut18().getTime()- this.getIn18().getTime();
                break;

            case 19:
                lDuration = this.getOut19().getTime()- this.getIn19().getTime();
                break;

            case 20:
                lDuration = this.getOut20().getTime()- this.getIn20().getTime();
                break;

            case 21:
                lDuration = this.getOut21().getTime()- this.getIn21().getTime();
                break;

            case 22:
                lDuration = this.getOut22().getTime()- this.getIn22().getTime();
                break;

            case 23:
                lDuration = this.getOut23().getTime()- this.getIn23().getTime();
                break;

            case 24:
                lDuration = this.getOut24().getTime()- this.getIn24().getTime();
                break;

            case 25:
                lDuration = this.getOut25().getTime()- this.getIn25().getTime();
                break;

            case 26:
                lDuration = this.getOut26().getTime()- this.getIn26().getTime();
                //System.out.println("lDuration  "+lDuration);
                break;

            case 27:
                lDuration = this.getOut27().getTime()- this.getIn27().getTime();
                break;

            case 28:
                lDuration = this.getOut28().getTime()- this.getIn28().getTime(); 
                break;

            case 29:
                lDuration = this.getOut29().getTime()- this.getIn29().getTime();
                break;

            case 30:
                lDuration = this.getOut30().getTime()- this.getIn30().getTime();
                break;

            case 31:
                lDuration = this.getOut31().getTime()- this.getIn31().getTime();
                break;
        }
        } catch(Exception exc){
            
        }
        
       return lDuration; 
        
    }

    /**
     * Get duration ( Out2nd - In2nd ) of second schedule 
     * @param index
     * @return duration in milliseconds
     * @author Ketut Kartika
     */
    public long getPresent2ndDurationMilSeconds(int index){
        long lDuration=0;
        try{
        switch (index) {
            case 1:
                lDuration = this.getOut2nd1().getTime()- this.getIn2nd1().getTime();
                break;

            case 2:
                lDuration = this.getOut2nd2().getTime()- this.getIn2nd2().getTime();
                break;

            case 3:
                lDuration = this.getOut2nd3().getTime()- this.getIn2nd3().getTime();
                break;

            case 4:
                lDuration = this.getOut2nd4().getTime()- this.getIn2nd4().getTime();
                break;

            case 5:
                lDuration = this.getOut2nd5().getTime()- this.getIn2nd5().getTime();
                break;

            case 6:
                lDuration = this.getOut2nd6().getTime()- this.getIn2nd6().getTime();
                break;

            case 7:
                lDuration = this.getOut2nd7().getTime()- this.getIn2nd7().getTime();
                break;

            case 8:
                lDuration = this.getOut2nd8().getTime()- this.getIn2nd8().getTime();
                break;

            case 9:
                lDuration = this.getOut2nd9().getTime()- this.getIn2nd9().getTime();
                break;

            case 10:
                lDuration = this.getOut2nd10().getTime()- this.getIn2nd10().getTime();
                break;

            case 11:
                lDuration = this.getOut2nd11().getTime()- this.getIn2nd11().getTime();
                break;

            case 12:
                lDuration = this.getOut2nd12().getTime()- this.getIn2nd12().getTime();
                break;

            case 13:
                lDuration = this.getOut2nd13().getTime()- this.getIn2nd13().getTime();
                break;

            case 14:
                lDuration = this.getOut2nd14().getTime()- this.getIn2nd14().getTime();
                break;

            case 15:
                lDuration = this.getOut2nd15().getTime()- this.getIn2nd15().getTime();
                break;

            case 16:
                lDuration = this.getOut2nd16().getTime()- this.getIn2nd16().getTime();
                break;

            case 17:
                lDuration = this.getOut2nd17().getTime()- this.getIn2nd17().getTime();
                break;

            case 18:
                lDuration = this.getOut2nd18().getTime()- this.getIn2nd18().getTime();
                break;

            case 19:
                lDuration = this.getOut2nd19().getTime()- this.getIn2nd19().getTime();
                break;

            case 20:
                lDuration = this.getOut2nd20().getTime()- this.getIn2nd20().getTime();
                break;

            case 21:
                lDuration = this.getOut2nd21().getTime()- this.getIn2nd21().getTime();
                break;

            case 22:
                lDuration = this.getOut2nd22().getTime()- this.getIn2nd22().getTime();
                break;

            case 23:
                lDuration = this.getOut2nd23().getTime()- this.getIn2nd23().getTime();
                break;

            case 24:
                lDuration = this.getOut2nd24().getTime()- this.getIn2nd24().getTime();
                break;

            case 25:
                lDuration = this.getOut2nd25().getTime()- this.getIn2nd25().getTime();
                break;

            case 26:
                lDuration = this.getOut2nd26().getTime()- this.getIn2nd26().getTime();
                //System.out.println("lDuration  "+lDuration);
                break;

            case 27:
                lDuration = this.getOut2nd27().getTime()- this.getIn2nd27().getTime();
                break;

            case 28:
                lDuration = this.getOut2nd28().getTime()- this.getIn2nd28().getTime();
                break;

            case 29:
                lDuration = this.getOut2nd29().getTime()- this.getIn2nd29().getTime();
                break;

            case 30:
                lDuration = this.getOut2nd30().getTime()- this.getIn2nd30().getTime();
                break;

            case 31:
                lDuration = this.getOut31().getTime()- this.getIn31().getTime();
                break;
        }
        } catch(Exception exc){
            
        }
        
       
       return lDuration; 
        
    }   

    public long getStatusData(int index) {
        int dataStatus = 0;
        switch (index) {
            case 1:
                dataStatus = this.getStatus1();
                break;

            case 2:
                dataStatus = this.getStatus2();
                break;

            case 3:
                dataStatus = this.getStatus3();
                break;

            case 4:
                dataStatus = this.getStatus4();
                break;

            case 5:
                dataStatus = this.getStatus5();
                break;

            case 6:
                dataStatus = this.getStatus6();
                break;

            case 7:
                dataStatus = this.getStatus7();
                break;

            case 8:
                dataStatus = this.getStatus8();
                break;

            case 9:
                dataStatus = this.getStatus9();
                break;

            case 10:
                dataStatus = this.getStatus10();
                break;

            case 11:
                dataStatus = this.getStatus11();
                break;

            case 12:
                dataStatus = this.getStatus12();
                break;

            case 13:
                dataStatus = this.getStatus13();
                break;

            case 14:
                dataStatus = this.getStatus14();
                break;

            case 15:
                dataStatus = this.getStatus15();
                break;

            case 16:
                dataStatus = this.getStatus16();
                break;

            case 17:
                dataStatus = this.getStatus17();
                break;

            case 18:
                dataStatus = this.getStatus18();
                break;

            case 19:
                dataStatus = this.getStatus19();
                break;

            case 20:
                dataStatus = this.getStatus20();
                break;

            case 21:
                dataStatus = this.getStatus21();
                break;

            case 22:
                dataStatus = this.getStatus22();
                break;

            case 23:
                dataStatus = this.getStatus23();
                break;

            case 24:
                dataStatus = this.getStatus24();
                break;

            case 25:
                dataStatus = this.getStatus25();
                break;

            case 26:
                dataStatus = this.getStatus26();
                //System.out.println("dataStatus  "+dataStatus);
                break;

            case 27:
                dataStatus = this.getStatus27();
                break;

            case 28:
                dataStatus = this.getStatus28();
                break;

            case 29:
                dataStatus = this.getStatus29();
                break;

            case 30:
                dataStatus = this.getStatus30();
                break;

            case 31:
                dataStatus = this.getStatus31();
                break;
        }

        return dataStatus;
    }
    
 public long getStatusData2nd(int index) {
        int dataStatus = 0;
        switch (index) {
            case 1:
                dataStatus = this.getStatus2nd1();
                break;

            case 2:
                dataStatus = this.getStatus2nd2();
                break;

            case 3:
                dataStatus = this.getStatus2nd3();
                break;

            case 4:
                dataStatus = this.getStatus2nd4();
                break;

            case 5:
                dataStatus = this.getStatus2nd5();
                break;

            case 6:
                dataStatus = this.getStatus2nd6();
                break;

            case 7:
                dataStatus = this.getStatus2nd7();
                break;

            case 8:
                dataStatus = this.getStatus2nd8();
                break;

            case 9:
                dataStatus = this.getStatus2nd9();
                break;

            case 10:
                dataStatus = this.getStatus2nd10();
                break;

            case 11:
                dataStatus = this.getStatus2nd11();
                break;

            case 12:
                dataStatus = this.getStatus2nd12();
                break;

            case 13:
                dataStatus = this.getStatus2nd13();
                break;

            case 14:
                dataStatus = this.getStatus2nd14();
                break;

            case 15:
                dataStatus = this.getStatus2nd15();
                break;

            case 16:
                dataStatus = this.getStatus2nd16();
                break;

            case 17:
                dataStatus = this.getStatus2nd17();
                break;

            case 18:
                dataStatus = this.getStatus2nd18();
                break;

            case 19:
                dataStatus = this.getStatus2nd19();
                break;

            case 20:
                dataStatus = this.getStatus2nd20();
                break;

            case 21:
                dataStatus = this.getStatus2nd21();
                break;

            case 22:
                dataStatus = this.getStatus2nd22();
                break;

            case 23:
                dataStatus = this.getStatus2nd23();
                break;

            case 24:
                dataStatus = this.getStatus2nd24();
                break;

            case 25:
                dataStatus = this.getStatus2nd25();
                break;

            case 26:
                dataStatus = this.getStatus2nd26();
                //System.out.println("dataStatus  "+dataStatus);
                break;

            case 27:
                dataStatus = this.getStatus2nd27();
                break;

            case 28:
                dataStatus = this.getStatus2nd28();
                break;

            case 29:
                dataStatus = this.getStatus2nd29();
                break;

            case 30:
                dataStatus = this.getStatus2nd30();
                break;

            case 31:
                dataStatus = this.getStatus2nd31();
                break;
        }

        return dataStatus;
    }    

 
    public long getReason(int index) {
        int absReason = 0;
        switch (index) {
            case 1:
                absReason = this.getReason1();
                break;

            case 2:
                absReason = this.getReason2();
                break;

            case 3:
                absReason = this.getReason3();
                break;

            case 4:
                absReason = this.getReason4();
                break;

            case 5:
                absReason = this.getReason5();
                break;

            case 6:
                absReason = this.getReason6();
                break;

            case 7:
                absReason = this.getReason7();
                break;

            case 8:
                absReason = this.getReason8();
                break;

            case 9:
                absReason = this.getReason9();
                break;

            case 10:
                absReason = this.getReason10();
                break;

            case 11:
                absReason = this.getReason11();
                break;

            case 12:
                absReason = this.getReason12();
                break;

            case 13:
                absReason = this.getReason13();
                break;

            case 14:
                absReason = this.getReason14();
                break;

            case 15:
                absReason = this.getReason15();
                break;

            case 16:
                absReason = this.getReason16();
                break;

            case 17:
                absReason = this.getReason17();
                break;

            case 18:
                absReason = this.getReason18();
                break;

            case 19:
                absReason = this.getReason19();
                break;

            case 20:
                absReason = this.getReason20();
                break;

            case 21:
                absReason = this.getReason21();
                break;

            case 22:
                absReason = this.getReason22();
                break;

            case 23:
                absReason = this.getReason23();
                break;

            case 24:
                absReason = this.getReason24();
                break;

            case 25:
                absReason = this.getReason25();
                break;

            case 26:
                absReason = this.getReason26();
                //System.out.println("absReason  "+absReason);
                break;

            case 27:
                absReason = this.getReason27();
                break;

            case 28:
                absReason = this.getReason28();
                break;

            case 29:
                absReason = this.getReason29();
                break;

            case 30:
                absReason = this.getReason30();
                break;

            case 31:
                absReason = this.getReason31();
                break;
        }

        return absReason;
    } 
 
   /**
    * 
    * @param minDur : minimum duration of present
    * @param maxDur : maximum duration of present
    * @param presentStatus : present status to be check the duration, set -1 to ignore the check of status
    * @param firstSchPart : 0 = ignore ; 1 = include; 
    * @param scndSchPart : 0 = ignore ; 1 = include; 
    * @return
    */
   public int isDatePresentDuration(int idxdate, long minDur, long maxDur, int presentStatus, long firstSchPart, long scndSchPart){
          int numDate =0;
           try{
           if ( (this.getStatusData(idxdate)==presentStatus) || (presentStatus==-1) ){
               long dur = firstSchPart * this.getPresentDurationMilSeconds(idxdate);
               if((dur>=minDur) && (dur<=maxDur)){
                   numDate++;
               }
           }}
           catch(Exception exc){               
           }

       return numDate;
   }

   public int getNumberDatePresentDuration(long minDur, long maxDur, int presentStatus, long firstSchPart, long scndSchPart,int dayOfMonth,Date dtPeriod){
       int numDate =0;
       int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(dtPeriod);
       int x=0;
       int idx=0;
       for(int i=0;i<=dayOfMonth;i++){
           //update by satrya 2013-02-20
           //for(int i=1;i<32;i++){ 
            int idxFieldNameX =  idxFieldName + i;
            if(idxFieldNameX>=32){
                idxFieldNameX = x +1;
                x = x+1;
            } 
             String sIdx = ""+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameX - 1].substring(1);
                        if(sIdx!=null && sIdx.length()>0){
                            idx = Integer.parseInt(sIdx);
                        }
           try{
           if ( (this.getStatusData(idx)==presentStatus) || (presentStatus==-1) ){
               // if ( (this.getStatusData(i)==presentStatus) || (presentStatus==-1) ){
               long dur = firstSchPart * this.getPresentDurationMilSeconds(i);
               if((dur>=minDur) && (dur<=maxDur)){
                   numDate++;
               }
            }
           }
           catch(Exception exc){               
              return 0;
           }
       }
       return numDate;
   }
   
   
   /**
    * create by satrya 2013-02-20
    * Keterangan: untuk mencari status reason dispensation
    * @param employeeId
    * @param periodId
    * @param dayOfMonth
    * @param intSchStatus
    * @param intReasonStatus
    * @param dtPeriod
    * @return 
    */
    public int getSumStatusDisp(long employeeId, long periodId, int dayOfMonth, Vector intSchStatus, Vector intReasonStatus,Date dtPeriod){
       if (employeeId==0 || dtPeriod==null || periodId==0 || intSchStatus == null || intSchStatus.size() < 1 || intReasonStatus == null || intReasonStatus.size() < 1) {
            return 0;
        }
        int numDate =0;
       int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(dtPeriod);
       int x=0;
       int idx=0;
       for(int i=0;i<=dayOfMonth;i++){
            int idxFieldNameX =  idxFieldName + i;
            if(idxFieldNameX>=32){
                idxFieldNameX = x +1;
                x = x+1;
            } 
             String sIdx = ""+PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameX - 1].substring(1);
                if(sIdx!=null && sIdx.length()>0){
                    idx = Integer.parseInt(sIdx);
                }
           try{
                for (int si = 0; si < intSchStatus.size(); si++) {
                    for (int ri = 0; ri < intReasonStatus.size(); ri++) {
                        int schStatus = ((Integer) intSchStatus.get(si)).intValue();
                        int abt = ((Integer) intReasonStatus.get(ri)).intValue();
                        if ( (this.getStatusData(idx)==schStatus) && (this.getReason(PstEmpSchedule.OFFSET_INDEX_REASON + idxFieldNameX - 1) == abt) ){
                                numDate++;
                         }
                    }
               }
           }
           catch(Exception exc){               
              return 0;
           }
       }
       return numDate;
   }

   

   
    
}





