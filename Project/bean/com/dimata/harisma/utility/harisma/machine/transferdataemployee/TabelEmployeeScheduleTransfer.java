/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class TabelEmployeeScheduleTransfer {
    //hr_period
    private long periodId;
    private String namaPeriod;
    private Date dtStartPeriod;
    private Date dtEndPeriod;
    
    //hr_schedule symbol
    private long scheduleId;
    private long scheduleCategory;
    private String nameSchedule;
    private String scheduleSymbol;
    private Date timeIn;
    private Date timeOut;
    private Date breakIn;
    private Date breakOut;
    
    //hr_EMP_SCHEDUKLE
    private long empScheduleId;
    //private long periodEmpSchedule; karna sdh ada periodeId
    private long employeeId;
    private String fullName;
    private String employeeNUmber;
    private long scheduleType;
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

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the namaPeriod
     */
    public String getNamaPeriod() {
        return namaPeriod;
    }

    /**
     * @param namaPeriod the namaPeriod to set
     */
    public void setNamaPeriod(String namaPeriod) {
        this.namaPeriod = namaPeriod;
    }

    /**
     * @return the dtStartPeriod
     */
    public Date getDtStartPeriod() {
        return dtStartPeriod;
    }

    /**
     * @param dtStartPeriod the dtStartPeriod to set
     */
    public void setDtStartPeriod(Date dtStartPeriod) {
        this.dtStartPeriod = dtStartPeriod;
    }

    /**
     * @return the dtEndPeriod
     */
    public Date getDtEndPeriod() {
        return dtEndPeriod;
    }

    /**
     * @param dtEndPeriod the dtEndPeriod to set
     */
    public void setDtEndPeriod(Date dtEndPeriod) {
        this.dtEndPeriod = dtEndPeriod;
    }

    /**
     * @return the scheduleId
     */
    public long getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the scheduleCategory
     */
    public long getScheduleCategory() {
        return scheduleCategory;
    }

    /**
     * @param scheduleCategory the scheduleCategory to set
     */
    public void setScheduleCategory(long scheduleCategory) {
        this.scheduleCategory = scheduleCategory;
    }

    /**
     * @return the nameSchedule
     */
    public String getNameSchedule() {
        return nameSchedule;
    }

    /**
     * @param nameSchedule the nameSchedule to set
     */
    public void setNameSchedule(String nameSchedule) {
        this.nameSchedule = nameSchedule;
    }

    /**
     * @return the scheduleSymbol
     */
    public String getScheduleSymbol() {
        return scheduleSymbol;
    }

    /**
     * @param scheduleSymbol the scheduleSymbol to set
     */
    public void setScheduleSymbol(String scheduleSymbol) {
        this.scheduleSymbol = scheduleSymbol;
    }

    /**
     * @return the timeIn
     */
    public Date getTimeIn() {
        return timeIn;
    }

    /**
     * @param timeIn the timeIn to set
     */
    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    /**
     * @return the timeOut
     */
    public Date getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @return the breakIn
     */
    public Date getBreakIn() {
        return breakIn;
    }

    /**
     * @param breakIn the breakIn to set
     */
    public void setBreakIn(Date breakIn) {
        this.breakIn = breakIn;
    }

    /**
     * @return the breakOut
     */
    public Date getBreakOut() {
        return breakOut;
    }

    /**
     * @param breakOut the breakOut to set
     */
    public void setBreakOut(Date breakOut) {
        this.breakOut = breakOut;
    }

    /**
     * @return the empScheduleId
     */
    public long getEmpScheduleId() {
        return empScheduleId;
    }

    /**
     * @param empScheduleId the empScheduleId to set
     */
    public void setEmpScheduleId(long empScheduleId) {
        this.empScheduleId = empScheduleId;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the scheduleType
     */
    public long getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */
    public void setScheduleType(long scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * @return the d1
     */
    public long getD1() {
        return d1;
    }

    /**
     * @param d1 the d1 to set
     */
    public void setD1(long d1) {
        this.d1 = d1;
    }

    /**
     * @return the d2
     */
    public long getD2() {
        return d2;
    }

    /**
     * @param d2 the d2 to set
     */
    public void setD2(long d2) {
        this.d2 = d2;
    }

    /**
     * @return the d3
     */
    public long getD3() {
        return d3;
    }

    /**
     * @param d3 the d3 to set
     */
    public void setD3(long d3) {
        this.d3 = d3;
    }

    /**
     * @return the d4
     */
    public long getD4() {
        return d4;
    }

    /**
     * @param d4 the d4 to set
     */
    public void setD4(long d4) {
        this.d4 = d4;
    }

    /**
     * @return the d5
     */
    public long getD5() {
        return d5;
    }

    /**
     * @param d5 the d5 to set
     */
    public void setD5(long d5) {
        this.d5 = d5;
    }

    /**
     * @return the d6
     */
    public long getD6() {
        return d6;
    }

    /**
     * @param d6 the d6 to set
     */
    public void setD6(long d6) {
        this.d6 = d6;
    }

    /**
     * @return the d7
     */
    public long getD7() {
        return d7;
    }

    /**
     * @param d7 the d7 to set
     */
    public void setD7(long d7) {
        this.d7 = d7;
    }

    /**
     * @return the d8
     */
    public long getD8() {
        return d8;
    }

    /**
     * @param d8 the d8 to set
     */
    public void setD8(long d8) {
        this.d8 = d8;
    }

    /**
     * @return the d9
     */
    public long getD9() {
        return d9;
    }

    /**
     * @param d9 the d9 to set
     */
    public void setD9(long d9) {
        this.d9 = d9;
    }

    /**
     * @return the d10
     */
    public long getD10() {
        return d10;
    }

    /**
     * @param d10 the d10 to set
     */
    public void setD10(long d10) {
        this.d10 = d10;
    }

    /**
     * @return the d11
     */
    public long getD11() {
        return d11;
    }

    /**
     * @param d11 the d11 to set
     */
    public void setD11(long d11) {
        this.d11 = d11;
    }

    /**
     * @return the d12
     */
    public long getD12() {
        return d12;
    }

    /**
     * @param d12 the d12 to set
     */
    public void setD12(long d12) {
        this.d12 = d12;
    }

    /**
     * @return the d13
     */
    public long getD13() {
        return d13;
    }

    /**
     * @param d13 the d13 to set
     */
    public void setD13(long d13) {
        this.d13 = d13;
    }

    /**
     * @return the d14
     */
    public long getD14() {
        return d14;
    }

    /**
     * @param d14 the d14 to set
     */
    public void setD14(long d14) {
        this.d14 = d14;
    }

    /**
     * @return the d15
     */
    public long getD15() {
        return d15;
    }

    /**
     * @param d15 the d15 to set
     */
    public void setD15(long d15) {
        this.d15 = d15;
    }

    /**
     * @return the d16
     */
    public long getD16() {
        return d16;
    }

    /**
     * @param d16 the d16 to set
     */
    public void setD16(long d16) {
        this.d16 = d16;
    }

    /**
     * @return the d17
     */
    public long getD17() {
        return d17;
    }

    /**
     * @param d17 the d17 to set
     */
    public void setD17(long d17) {
        this.d17 = d17;
    }

    /**
     * @return the d18
     */
    public long getD18() {
        return d18;
    }

    /**
     * @param d18 the d18 to set
     */
    public void setD18(long d18) {
        this.d18 = d18;
    }

    /**
     * @return the d19
     */
    public long getD19() {
        return d19;
    }

    /**
     * @param d19 the d19 to set
     */
    public void setD19(long d19) {
        this.d19 = d19;
    }

    /**
     * @return the d20
     */
    public long getD20() {
        return d20;
    }

    /**
     * @param d20 the d20 to set
     */
    public void setD20(long d20) {
        this.d20 = d20;
    }

    /**
     * @return the d21
     */
    public long getD21() {
        return d21;
    }

    /**
     * @param d21 the d21 to set
     */
    public void setD21(long d21) {
        this.d21 = d21;
    }

    /**
     * @return the d22
     */
    public long getD22() {
        return d22;
    }

    /**
     * @param d22 the d22 to set
     */
    public void setD22(long d22) {
        this.d22 = d22;
    }

    /**
     * @return the d23
     */
    public long getD23() {
        return d23;
    }

    /**
     * @param d23 the d23 to set
     */
    public void setD23(long d23) {
        this.d23 = d23;
    }

    /**
     * @return the d24
     */
    public long getD24() {
        return d24;
    }

    /**
     * @param d24 the d24 to set
     */
    public void setD24(long d24) {
        this.d24 = d24;
    }

    /**
     * @return the d25
     */
    public long getD25() {
        return d25;
    }

    /**
     * @param d25 the d25 to set
     */
    public void setD25(long d25) {
        this.d25 = d25;
    }

    /**
     * @return the d26
     */
    public long getD26() {
        return d26;
    }

    /**
     * @param d26 the d26 to set
     */
    public void setD26(long d26) {
        this.d26 = d26;
    }

    /**
     * @return the d27
     */
    public long getD27() {
        return d27;
    }

    /**
     * @param d27 the d27 to set
     */
    public void setD27(long d27) {
        this.d27 = d27;
    }

    /**
     * @return the d28
     */
    public long getD28() {
        return d28;
    }

    /**
     * @param d28 the d28 to set
     */
    public void setD28(long d28) {
        this.d28 = d28;
    }

    /**
     * @return the d29
     */
    public long getD29() {
        return d29;
    }

    /**
     * @param d29 the d29 to set
     */
    public void setD29(long d29) {
        this.d29 = d29;
    }

    /**
     * @return the d30
     */
    public long getD30() {
        return d30;
    }

    /**
     * @param d30 the d30 to set
     */
    public void setD30(long d30) {
        this.d30 = d30;
    }

    /**
     * @return the d31
     */
    public long getD31() {
        return d31;
    }

    /**
     * @param d31 the d31 to set
     */
    public void setD31(long d31) {
        this.d31 = d31;
    }

    /**
     * @return the d2nd1
     */
    public long getD2nd1() {
        return d2nd1;
    }

    /**
     * @param d2nd1 the d2nd1 to set
     */
    public void setD2nd1(long d2nd1) {
        this.d2nd1 = d2nd1;
    }

    /**
     * @return the d2nd2
     */
    public long getD2nd2() {
        return d2nd2;
    }

    /**
     * @param d2nd2 the d2nd2 to set
     */
    public void setD2nd2(long d2nd2) {
        this.d2nd2 = d2nd2;
    }

    /**
     * @return the d2nd3
     */
    public long getD2nd3() {
        return d2nd3;
    }

    /**
     * @param d2nd3 the d2nd3 to set
     */
    public void setD2nd3(long d2nd3) {
        this.d2nd3 = d2nd3;
    }

    /**
     * @return the d2nd4
     */
    public long getD2nd4() {
        return d2nd4;
    }

    /**
     * @param d2nd4 the d2nd4 to set
     */
    public void setD2nd4(long d2nd4) {
        this.d2nd4 = d2nd4;
    }

    /**
     * @return the d2nd5
     */
    public long getD2nd5() {
        return d2nd5;
    }

    /**
     * @param d2nd5 the d2nd5 to set
     */
    public void setD2nd5(long d2nd5) {
        this.d2nd5 = d2nd5;
    }

    /**
     * @return the d2nd6
     */
    public long getD2nd6() {
        return d2nd6;
    }

    /**
     * @param d2nd6 the d2nd6 to set
     */
    public void setD2nd6(long d2nd6) {
        this.d2nd6 = d2nd6;
    }

    /**
     * @return the d2nd7
     */
    public long getD2nd7() {
        return d2nd7;
    }

    /**
     * @param d2nd7 the d2nd7 to set
     */
    public void setD2nd7(long d2nd7) {
        this.d2nd7 = d2nd7;
    }

    /**
     * @return the d2nd8
     */
    public long getD2nd8() {
        return d2nd8;
    }

    /**
     * @param d2nd8 the d2nd8 to set
     */
    public void setD2nd8(long d2nd8) {
        this.d2nd8 = d2nd8;
    }

    /**
     * @return the d2nd9
     */
    public long getD2nd9() {
        return d2nd9;
    }

    /**
     * @param d2nd9 the d2nd9 to set
     */
    public void setD2nd9(long d2nd9) {
        this.d2nd9 = d2nd9;
    }

    /**
     * @return the d2nd10
     */
    public long getD2nd10() {
        return d2nd10;
    }

    /**
     * @param d2nd10 the d2nd10 to set
     */
    public void setD2nd10(long d2nd10) {
        this.d2nd10 = d2nd10;
    }

    /**
     * @return the d2nd11
     */
    public long getD2nd11() {
        return d2nd11;
    }

    /**
     * @param d2nd11 the d2nd11 to set
     */
    public void setD2nd11(long d2nd11) {
        this.d2nd11 = d2nd11;
    }

    /**
     * @return the d2nd12
     */
    public long getD2nd12() {
        return d2nd12;
    }

    /**
     * @param d2nd12 the d2nd12 to set
     */
    public void setD2nd12(long d2nd12) {
        this.d2nd12 = d2nd12;
    }

    /**
     * @return the d2nd13
     */
    public long getD2nd13() {
        return d2nd13;
    }

    /**
     * @param d2nd13 the d2nd13 to set
     */
    public void setD2nd13(long d2nd13) {
        this.d2nd13 = d2nd13;
    }

    /**
     * @return the d2nd14
     */
    public long getD2nd14() {
        return d2nd14;
    }

    /**
     * @param d2nd14 the d2nd14 to set
     */
    public void setD2nd14(long d2nd14) {
        this.d2nd14 = d2nd14;
    }

    /**
     * @return the d2nd15
     */
    public long getD2nd15() {
        return d2nd15;
    }

    /**
     * @param d2nd15 the d2nd15 to set
     */
    public void setD2nd15(long d2nd15) {
        this.d2nd15 = d2nd15;
    }

    /**
     * @return the d2nd16
     */
    public long getD2nd16() {
        return d2nd16;
    }

    /**
     * @param d2nd16 the d2nd16 to set
     */
    public void setD2nd16(long d2nd16) {
        this.d2nd16 = d2nd16;
    }

    /**
     * @return the d2nd17
     */
    public long getD2nd17() {
        return d2nd17;
    }

    /**
     * @param d2nd17 the d2nd17 to set
     */
    public void setD2nd17(long d2nd17) {
        this.d2nd17 = d2nd17;
    }

    /**
     * @return the d2nd18
     */
    public long getD2nd18() {
        return d2nd18;
    }

    /**
     * @param d2nd18 the d2nd18 to set
     */
    public void setD2nd18(long d2nd18) {
        this.d2nd18 = d2nd18;
    }

    /**
     * @return the d2nd19
     */
    public long getD2nd19() {
        return d2nd19;
    }

    /**
     * @param d2nd19 the d2nd19 to set
     */
    public void setD2nd19(long d2nd19) {
        this.d2nd19 = d2nd19;
    }

    /**
     * @return the d2nd20
     */
    public long getD2nd20() {
        return d2nd20;
    }

    /**
     * @param d2nd20 the d2nd20 to set
     */
    public void setD2nd20(long d2nd20) {
        this.d2nd20 = d2nd20;
    }

    /**
     * @return the d2nd21
     */
    public long getD2nd21() {
        return d2nd21;
    }

    /**
     * @param d2nd21 the d2nd21 to set
     */
    public void setD2nd21(long d2nd21) {
        this.d2nd21 = d2nd21;
    }

    /**
     * @return the d2nd22
     */
    public long getD2nd22() {
        return d2nd22;
    }

    /**
     * @param d2nd22 the d2nd22 to set
     */
    public void setD2nd22(long d2nd22) {
        this.d2nd22 = d2nd22;
    }

    /**
     * @return the d2nd23
     */
    public long getD2nd23() {
        return d2nd23;
    }

    /**
     * @param d2nd23 the d2nd23 to set
     */
    public void setD2nd23(long d2nd23) {
        this.d2nd23 = d2nd23;
    }

    /**
     * @return the d2nd24
     */
    public long getD2nd24() {
        return d2nd24;
    }

    /**
     * @param d2nd24 the d2nd24 to set
     */
    public void setD2nd24(long d2nd24) {
        this.d2nd24 = d2nd24;
    }

    /**
     * @return the d2nd25
     */
    public long getD2nd25() {
        return d2nd25;
    }

    /**
     * @param d2nd25 the d2nd25 to set
     */
    public void setD2nd25(long d2nd25) {
        this.d2nd25 = d2nd25;
    }

    /**
     * @return the d2nd26
     */
    public long getD2nd26() {
        return d2nd26;
    }

    /**
     * @param d2nd26 the d2nd26 to set
     */
    public void setD2nd26(long d2nd26) {
        this.d2nd26 = d2nd26;
    }

    /**
     * @return the d2nd27
     */
    public long getD2nd27() {
        return d2nd27;
    }

    /**
     * @param d2nd27 the d2nd27 to set
     */
    public void setD2nd27(long d2nd27) {
        this.d2nd27 = d2nd27;
    }

    /**
     * @return the d2nd28
     */
    public long getD2nd28() {
        return d2nd28;
    }

    /**
     * @param d2nd28 the d2nd28 to set
     */
    public void setD2nd28(long d2nd28) {
        this.d2nd28 = d2nd28;
    }

    /**
     * @return the d2nd29
     */
    public long getD2nd29() {
        return d2nd29;
    }

    /**
     * @param d2nd29 the d2nd29 to set
     */
    public void setD2nd29(long d2nd29) {
        this.d2nd29 = d2nd29;
    }

    /**
     * @return the d2nd30
     */
    public long getD2nd30() {
        return d2nd30;
    }

    /**
     * @param d2nd30 the d2nd30 to set
     */
    public void setD2nd30(long d2nd30) {
        this.d2nd30 = d2nd30;
    }

    /**
     * @return the d2nd31
     */
    public long getD2nd31() {
        return d2nd31;
    }

    /**
     * @param d2nd31 the d2nd31 to set
     */
    public void setD2nd31(long d2nd31) {
        this.d2nd31 = d2nd31;
    }

    /**
     * @return the status1
     */
    public int getStatus1() {
        return status1;
    }

    /**
     * @param status1 the status1 to set
     */
    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    /**
     * @return the status2
     */
    public int getStatus2() {
        return status2;
    }

    /**
     * @param status2 the status2 to set
     */
    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    /**
     * @return the status3
     */
    public int getStatus3() {
        return status3;
    }

    /**
     * @param status3 the status3 to set
     */
    public void setStatus3(int status3) {
        this.status3 = status3;
    }

    /**
     * @return the status4
     */
    public int getStatus4() {
        return status4;
    }

    /**
     * @param status4 the status4 to set
     */
    public void setStatus4(int status4) {
        this.status4 = status4;
    }

    /**
     * @return the status5
     */
    public int getStatus5() {
        return status5;
    }

    /**
     * @param status5 the status5 to set
     */
    public void setStatus5(int status5) {
        this.status5 = status5;
    }

    /**
     * @return the status6
     */
    public int getStatus6() {
        return status6;
    }

    /**
     * @param status6 the status6 to set
     */
    public void setStatus6(int status6) {
        this.status6 = status6;
    }

    /**
     * @return the status7
     */
    public int getStatus7() {
        return status7;
    }

    /**
     * @param status7 the status7 to set
     */
    public void setStatus7(int status7) {
        this.status7 = status7;
    }

    /**
     * @return the status8
     */
    public int getStatus8() {
        return status8;
    }

    /**
     * @param status8 the status8 to set
     */
    public void setStatus8(int status8) {
        this.status8 = status8;
    }

    /**
     * @return the status9
     */
    public int getStatus9() {
        return status9;
    }

    /**
     * @param status9 the status9 to set
     */
    public void setStatus9(int status9) {
        this.status9 = status9;
    }

    /**
     * @return the status10
     */
    public int getStatus10() {
        return status10;
    }

    /**
     * @param status10 the status10 to set
     */
    public void setStatus10(int status10) {
        this.status10 = status10;
    }

    /**
     * @return the status11
     */
    public int getStatus11() {
        return status11;
    }

    /**
     * @param status11 the status11 to set
     */
    public void setStatus11(int status11) {
        this.status11 = status11;
    }

    /**
     * @return the status12
     */
    public int getStatus12() {
        return status12;
    }

    /**
     * @param status12 the status12 to set
     */
    public void setStatus12(int status12) {
        this.status12 = status12;
    }

    /**
     * @return the status13
     */
    public int getStatus13() {
        return status13;
    }

    /**
     * @param status13 the status13 to set
     */
    public void setStatus13(int status13) {
        this.status13 = status13;
    }

    /**
     * @return the status14
     */
    public int getStatus14() {
        return status14;
    }

    /**
     * @param status14 the status14 to set
     */
    public void setStatus14(int status14) {
        this.status14 = status14;
    }

    /**
     * @return the status15
     */
    public int getStatus15() {
        return status15;
    }

    /**
     * @param status15 the status15 to set
     */
    public void setStatus15(int status15) {
        this.status15 = status15;
    }

    /**
     * @return the status16
     */
    public int getStatus16() {
        return status16;
    }

    /**
     * @param status16 the status16 to set
     */
    public void setStatus16(int status16) {
        this.status16 = status16;
    }

    /**
     * @return the status17
     */
    public int getStatus17() {
        return status17;
    }

    /**
     * @param status17 the status17 to set
     */
    public void setStatus17(int status17) {
        this.status17 = status17;
    }

    /**
     * @return the status18
     */
    public int getStatus18() {
        return status18;
    }

    /**
     * @param status18 the status18 to set
     */
    public void setStatus18(int status18) {
        this.status18 = status18;
    }

    /**
     * @return the status19
     */
    public int getStatus19() {
        return status19;
    }

    /**
     * @param status19 the status19 to set
     */
    public void setStatus19(int status19) {
        this.status19 = status19;
    }

    /**
     * @return the status20
     */
    public int getStatus20() {
        return status20;
    }

    /**
     * @param status20 the status20 to set
     */
    public void setStatus20(int status20) {
        this.status20 = status20;
    }

    /**
     * @return the status21
     */
    public int getStatus21() {
        return status21;
    }

    /**
     * @param status21 the status21 to set
     */
    public void setStatus21(int status21) {
        this.status21 = status21;
    }

    /**
     * @return the status22
     */
    public int getStatus22() {
        return status22;
    }

    /**
     * @param status22 the status22 to set
     */
    public void setStatus22(int status22) {
        this.status22 = status22;
    }

    /**
     * @return the status23
     */
    public int getStatus23() {
        return status23;
    }

    /**
     * @param status23 the status23 to set
     */
    public void setStatus23(int status23) {
        this.status23 = status23;
    }

    /**
     * @return the status24
     */
    public int getStatus24() {
        return status24;
    }

    /**
     * @param status24 the status24 to set
     */
    public void setStatus24(int status24) {
        this.status24 = status24;
    }

    /**
     * @return the status25
     */
    public int getStatus25() {
        return status25;
    }

    /**
     * @param status25 the status25 to set
     */
    public void setStatus25(int status25) {
        this.status25 = status25;
    }

    /**
     * @return the status26
     */
    public int getStatus26() {
        return status26;
    }

    /**
     * @param status26 the status26 to set
     */
    public void setStatus26(int status26) {
        this.status26 = status26;
    }

    /**
     * @return the status27
     */
    public int getStatus27() {
        return status27;
    }

    /**
     * @param status27 the status27 to set
     */
    public void setStatus27(int status27) {
        this.status27 = status27;
    }

    /**
     * @return the status28
     */
    public int getStatus28() {
        return status28;
    }

    /**
     * @param status28 the status28 to set
     */
    public void setStatus28(int status28) {
        this.status28 = status28;
    }

    /**
     * @return the status29
     */
    public int getStatus29() {
        return status29;
    }

    /**
     * @param status29 the status29 to set
     */
    public void setStatus29(int status29) {
        this.status29 = status29;
    }

    /**
     * @return the status30
     */
    public int getStatus30() {
        return status30;
    }

    /**
     * @param status30 the status30 to set
     */
    public void setStatus30(int status30) {
        this.status30 = status30;
    }

    /**
     * @return the status31
     */
    public int getStatus31() {
        return status31;
    }

    /**
     * @param status31 the status31 to set
     */
    public void setStatus31(int status31) {
        this.status31 = status31;
    }

    /**
     * @return the status2nd1
     */
    public int getStatus2nd1() {
        return status2nd1;
    }

    /**
     * @param status2nd1 the status2nd1 to set
     */
    public void setStatus2nd1(int status2nd1) {
        this.status2nd1 = status2nd1;
    }

    /**
     * @return the status2nd2
     */
    public int getStatus2nd2() {
        return status2nd2;
    }

    /**
     * @param status2nd2 the status2nd2 to set
     */
    public void setStatus2nd2(int status2nd2) {
        this.status2nd2 = status2nd2;
    }

    /**
     * @return the status2nd3
     */
    public int getStatus2nd3() {
        return status2nd3;
    }

    /**
     * @param status2nd3 the status2nd3 to set
     */
    public void setStatus2nd3(int status2nd3) {
        this.status2nd3 = status2nd3;
    }

    /**
     * @return the status2nd4
     */
    public int getStatus2nd4() {
        return status2nd4;
    }

    /**
     * @param status2nd4 the status2nd4 to set
     */
    public void setStatus2nd4(int status2nd4) {
        this.status2nd4 = status2nd4;
    }

    /**
     * @return the status2nd5
     */
    public int getStatus2nd5() {
        return status2nd5;
    }

    /**
     * @param status2nd5 the status2nd5 to set
     */
    public void setStatus2nd5(int status2nd5) {
        this.status2nd5 = status2nd5;
    }

    /**
     * @return the status2nd6
     */
    public int getStatus2nd6() {
        return status2nd6;
    }

    /**
     * @param status2nd6 the status2nd6 to set
     */
    public void setStatus2nd6(int status2nd6) {
        this.status2nd6 = status2nd6;
    }

    /**
     * @return the status2nd7
     */
    public int getStatus2nd7() {
        return status2nd7;
    }

    /**
     * @param status2nd7 the status2nd7 to set
     */
    public void setStatus2nd7(int status2nd7) {
        this.status2nd7 = status2nd7;
    }

    /**
     * @return the status2nd8
     */
    public int getStatus2nd8() {
        return status2nd8;
    }

    /**
     * @param status2nd8 the status2nd8 to set
     */
    public void setStatus2nd8(int status2nd8) {
        this.status2nd8 = status2nd8;
    }

    /**
     * @return the status2nd9
     */
    public int getStatus2nd9() {
        return status2nd9;
    }

    /**
     * @param status2nd9 the status2nd9 to set
     */
    public void setStatus2nd9(int status2nd9) {
        this.status2nd9 = status2nd9;
    }

    /**
     * @return the status2nd10
     */
    public int getStatus2nd10() {
        return status2nd10;
    }

    /**
     * @param status2nd10 the status2nd10 to set
     */
    public void setStatus2nd10(int status2nd10) {
        this.status2nd10 = status2nd10;
    }

    /**
     * @return the status2nd11
     */
    public int getStatus2nd11() {
        return status2nd11;
    }

    /**
     * @param status2nd11 the status2nd11 to set
     */
    public void setStatus2nd11(int status2nd11) {
        this.status2nd11 = status2nd11;
    }

    /**
     * @return the status2nd12
     */
    public int getStatus2nd12() {
        return status2nd12;
    }

    /**
     * @param status2nd12 the status2nd12 to set
     */
    public void setStatus2nd12(int status2nd12) {
        this.status2nd12 = status2nd12;
    }

    /**
     * @return the status2nd13
     */
    public int getStatus2nd13() {
        return status2nd13;
    }

    /**
     * @param status2nd13 the status2nd13 to set
     */
    public void setStatus2nd13(int status2nd13) {
        this.status2nd13 = status2nd13;
    }

    /**
     * @return the status2nd14
     */
    public int getStatus2nd14() {
        return status2nd14;
    }

    /**
     * @param status2nd14 the status2nd14 to set
     */
    public void setStatus2nd14(int status2nd14) {
        this.status2nd14 = status2nd14;
    }

    /**
     * @return the status2nd15
     */
    public int getStatus2nd15() {
        return status2nd15;
    }

    /**
     * @param status2nd15 the status2nd15 to set
     */
    public void setStatus2nd15(int status2nd15) {
        this.status2nd15 = status2nd15;
    }

    /**
     * @return the status2nd16
     */
    public int getStatus2nd16() {
        return status2nd16;
    }

    /**
     * @param status2nd16 the status2nd16 to set
     */
    public void setStatus2nd16(int status2nd16) {
        this.status2nd16 = status2nd16;
    }

    /**
     * @return the status2nd17
     */
    public int getStatus2nd17() {
        return status2nd17;
    }

    /**
     * @param status2nd17 the status2nd17 to set
     */
    public void setStatus2nd17(int status2nd17) {
        this.status2nd17 = status2nd17;
    }

    /**
     * @return the status2nd18
     */
    public int getStatus2nd18() {
        return status2nd18;
    }

    /**
     * @param status2nd18 the status2nd18 to set
     */
    public void setStatus2nd18(int status2nd18) {
        this.status2nd18 = status2nd18;
    }

    /**
     * @return the status2nd19
     */
    public int getStatus2nd19() {
        return status2nd19;
    }

    /**
     * @param status2nd19 the status2nd19 to set
     */
    public void setStatus2nd19(int status2nd19) {
        this.status2nd19 = status2nd19;
    }

    /**
     * @return the status2nd20
     */
    public int getStatus2nd20() {
        return status2nd20;
    }

    /**
     * @param status2nd20 the status2nd20 to set
     */
    public void setStatus2nd20(int status2nd20) {
        this.status2nd20 = status2nd20;
    }

    /**
     * @return the status2nd21
     */
    public int getStatus2nd21() {
        return status2nd21;
    }

    /**
     * @param status2nd21 the status2nd21 to set
     */
    public void setStatus2nd21(int status2nd21) {
        this.status2nd21 = status2nd21;
    }

    /**
     * @return the status2nd22
     */
    public int getStatus2nd22() {
        return status2nd22;
    }

    /**
     * @param status2nd22 the status2nd22 to set
     */
    public void setStatus2nd22(int status2nd22) {
        this.status2nd22 = status2nd22;
    }

    /**
     * @return the status2nd23
     */
    public int getStatus2nd23() {
        return status2nd23;
    }

    /**
     * @param status2nd23 the status2nd23 to set
     */
    public void setStatus2nd23(int status2nd23) {
        this.status2nd23 = status2nd23;
    }

    /**
     * @return the status2nd24
     */
    public int getStatus2nd24() {
        return status2nd24;
    }

    /**
     * @param status2nd24 the status2nd24 to set
     */
    public void setStatus2nd24(int status2nd24) {
        this.status2nd24 = status2nd24;
    }

    /**
     * @return the status2nd25
     */
    public int getStatus2nd25() {
        return status2nd25;
    }

    /**
     * @param status2nd25 the status2nd25 to set
     */
    public void setStatus2nd25(int status2nd25) {
        this.status2nd25 = status2nd25;
    }

    /**
     * @return the status2nd26
     */
    public int getStatus2nd26() {
        return status2nd26;
    }

    /**
     * @param status2nd26 the status2nd26 to set
     */
    public void setStatus2nd26(int status2nd26) {
        this.status2nd26 = status2nd26;
    }

    /**
     * @return the status2nd27
     */
    public int getStatus2nd27() {
        return status2nd27;
    }

    /**
     * @param status2nd27 the status2nd27 to set
     */
    public void setStatus2nd27(int status2nd27) {
        this.status2nd27 = status2nd27;
    }

    /**
     * @return the status2nd28
     */
    public int getStatus2nd28() {
        return status2nd28;
    }

    /**
     * @param status2nd28 the status2nd28 to set
     */
    public void setStatus2nd28(int status2nd28) {
        this.status2nd28 = status2nd28;
    }

    /**
     * @return the status2nd29
     */
    public int getStatus2nd29() {
        return status2nd29;
    }

    /**
     * @param status2nd29 the status2nd29 to set
     */
    public void setStatus2nd29(int status2nd29) {
        this.status2nd29 = status2nd29;
    }

    /**
     * @return the status2nd30
     */
    public int getStatus2nd30() {
        return status2nd30;
    }

    /**
     * @param status2nd30 the status2nd30 to set
     */
    public void setStatus2nd30(int status2nd30) {
        this.status2nd30 = status2nd30;
    }

    /**
     * @return the status2nd31
     */
    public int getStatus2nd31() {
        return status2nd31;
    }

    /**
     * @param status2nd31 the status2nd31 to set
     */
    public void setStatus2nd31(int status2nd31) {
        this.status2nd31 = status2nd31;
    }

    /**
     * @return the reason1
     */
    public int getReason1() {
        return reason1;
    }

    /**
     * @param reason1 the reason1 to set
     */
    public void setReason1(int reason1) {
        this.reason1 = reason1;
    }

    /**
     * @return the reason2
     */
    public int getReason2() {
        return reason2;
    }

    /**
     * @param reason2 the reason2 to set
     */
    public void setReason2(int reason2) {
        this.reason2 = reason2;
    }

    /**
     * @return the reason3
     */
    public int getReason3() {
        return reason3;
    }

    /**
     * @param reason3 the reason3 to set
     */
    public void setReason3(int reason3) {
        this.reason3 = reason3;
    }

    /**
     * @return the reason4
     */
    public int getReason4() {
        return reason4;
    }

    /**
     * @param reason4 the reason4 to set
     */
    public void setReason4(int reason4) {
        this.reason4 = reason4;
    }

    /**
     * @return the reason5
     */
    public int getReason5() {
        return reason5;
    }

    /**
     * @param reason5 the reason5 to set
     */
    public void setReason5(int reason5) {
        this.reason5 = reason5;
    }

    /**
     * @return the reason6
     */
    public int getReason6() {
        return reason6;
    }

    /**
     * @param reason6 the reason6 to set
     */
    public void setReason6(int reason6) {
        this.reason6 = reason6;
    }

    /**
     * @return the reason7
     */
    public int getReason7() {
        return reason7;
    }

    /**
     * @param reason7 the reason7 to set
     */
    public void setReason7(int reason7) {
        this.reason7 = reason7;
    }

    /**
     * @return the reason8
     */
    public int getReason8() {
        return reason8;
    }

    /**
     * @param reason8 the reason8 to set
     */
    public void setReason8(int reason8) {
        this.reason8 = reason8;
    }

    /**
     * @return the reason9
     */
    public int getReason9() {
        return reason9;
    }

    /**
     * @param reason9 the reason9 to set
     */
    public void setReason9(int reason9) {
        this.reason9 = reason9;
    }

    /**
     * @return the reason10
     */
    public int getReason10() {
        return reason10;
    }

    /**
     * @param reason10 the reason10 to set
     */
    public void setReason10(int reason10) {
        this.reason10 = reason10;
    }

    /**
     * @return the reason11
     */
    public int getReason11() {
        return reason11;
    }

    /**
     * @param reason11 the reason11 to set
     */
    public void setReason11(int reason11) {
        this.reason11 = reason11;
    }

    /**
     * @return the reason12
     */
    public int getReason12() {
        return reason12;
    }

    /**
     * @param reason12 the reason12 to set
     */
    public void setReason12(int reason12) {
        this.reason12 = reason12;
    }

    /**
     * @return the reason13
     */
    public int getReason13() {
        return reason13;
    }

    /**
     * @param reason13 the reason13 to set
     */
    public void setReason13(int reason13) {
        this.reason13 = reason13;
    }

    /**
     * @return the reason14
     */
    public int getReason14() {
        return reason14;
    }

    /**
     * @param reason14 the reason14 to set
     */
    public void setReason14(int reason14) {
        this.reason14 = reason14;
    }

    /**
     * @return the reason15
     */
    public int getReason15() {
        return reason15;
    }

    /**
     * @param reason15 the reason15 to set
     */
    public void setReason15(int reason15) {
        this.reason15 = reason15;
    }

    /**
     * @return the reason16
     */
    public int getReason16() {
        return reason16;
    }

    /**
     * @param reason16 the reason16 to set
     */
    public void setReason16(int reason16) {
        this.reason16 = reason16;
    }

    /**
     * @return the reason17
     */
    public int getReason17() {
        return reason17;
    }

    /**
     * @param reason17 the reason17 to set
     */
    public void setReason17(int reason17) {
        this.reason17 = reason17;
    }

    /**
     * @return the reason18
     */
    public int getReason18() {
        return reason18;
    }

    /**
     * @param reason18 the reason18 to set
     */
    public void setReason18(int reason18) {
        this.reason18 = reason18;
    }

    /**
     * @return the reason19
     */
    public int getReason19() {
        return reason19;
    }

    /**
     * @param reason19 the reason19 to set
     */
    public void setReason19(int reason19) {
        this.reason19 = reason19;
    }

    /**
     * @return the reason20
     */
    public int getReason20() {
        return reason20;
    }

    /**
     * @param reason20 the reason20 to set
     */
    public void setReason20(int reason20) {
        this.reason20 = reason20;
    }

    /**
     * @return the reason21
     */
    public int getReason21() {
        return reason21;
    }

    /**
     * @param reason21 the reason21 to set
     */
    public void setReason21(int reason21) {
        this.reason21 = reason21;
    }

    /**
     * @return the reason22
     */
    public int getReason22() {
        return reason22;
    }

    /**
     * @param reason22 the reason22 to set
     */
    public void setReason22(int reason22) {
        this.reason22 = reason22;
    }

    /**
     * @return the reason23
     */
    public int getReason23() {
        return reason23;
    }

    /**
     * @param reason23 the reason23 to set
     */
    public void setReason23(int reason23) {
        this.reason23 = reason23;
    }

    /**
     * @return the reason24
     */
    public int getReason24() {
        return reason24;
    }

    /**
     * @param reason24 the reason24 to set
     */
    public void setReason24(int reason24) {
        this.reason24 = reason24;
    }

    /**
     * @return the reason25
     */
    public int getReason25() {
        return reason25;
    }

    /**
     * @param reason25 the reason25 to set
     */
    public void setReason25(int reason25) {
        this.reason25 = reason25;
    }

    /**
     * @return the reason26
     */
    public int getReason26() {
        return reason26;
    }

    /**
     * @param reason26 the reason26 to set
     */
    public void setReason26(int reason26) {
        this.reason26 = reason26;
    }

    /**
     * @return the reason27
     */
    public int getReason27() {
        return reason27;
    }

    /**
     * @param reason27 the reason27 to set
     */
    public void setReason27(int reason27) {
        this.reason27 = reason27;
    }

    /**
     * @return the reason28
     */
    public int getReason28() {
        return reason28;
    }

    /**
     * @param reason28 the reason28 to set
     */
    public void setReason28(int reason28) {
        this.reason28 = reason28;
    }

    /**
     * @return the reason29
     */
    public int getReason29() {
        return reason29;
    }

    /**
     * @param reason29 the reason29 to set
     */
    public void setReason29(int reason29) {
        this.reason29 = reason29;
    }

    /**
     * @return the reason30
     */
    public int getReason30() {
        return reason30;
    }

    /**
     * @param reason30 the reason30 to set
     */
    public void setReason30(int reason30) {
        this.reason30 = reason30;
    }

    /**
     * @return the reason31
     */
    public int getReason31() {
        return reason31;
    }

    /**
     * @param reason31 the reason31 to set
     */
    public void setReason31(int reason31) {
        this.reason31 = reason31;
    }

    /**
     * @return the reason2nd1
     */
    public int getReason2nd1() {
        return reason2nd1;
    }

    /**
     * @param reason2nd1 the reason2nd1 to set
     */
    public void setReason2nd1(int reason2nd1) {
        this.reason2nd1 = reason2nd1;
    }

    /**
     * @return the reason2nd2
     */
    public int getReason2nd2() {
        return reason2nd2;
    }

    /**
     * @param reason2nd2 the reason2nd2 to set
     */
    public void setReason2nd2(int reason2nd2) {
        this.reason2nd2 = reason2nd2;
    }

    /**
     * @return the reason2nd3
     */
    public int getReason2nd3() {
        return reason2nd3;
    }

    /**
     * @param reason2nd3 the reason2nd3 to set
     */
    public void setReason2nd3(int reason2nd3) {
        this.reason2nd3 = reason2nd3;
    }

    /**
     * @return the reason2nd4
     */
    public int getReason2nd4() {
        return reason2nd4;
    }

    /**
     * @param reason2nd4 the reason2nd4 to set
     */
    public void setReason2nd4(int reason2nd4) {
        this.reason2nd4 = reason2nd4;
    }

    /**
     * @return the reason2nd5
     */
    public int getReason2nd5() {
        return reason2nd5;
    }

    /**
     * @param reason2nd5 the reason2nd5 to set
     */
    public void setReason2nd5(int reason2nd5) {
        this.reason2nd5 = reason2nd5;
    }

    /**
     * @return the reason2nd6
     */
    public int getReason2nd6() {
        return reason2nd6;
    }

    /**
     * @param reason2nd6 the reason2nd6 to set
     */
    public void setReason2nd6(int reason2nd6) {
        this.reason2nd6 = reason2nd6;
    }

    /**
     * @return the reason2nd7
     */
    public int getReason2nd7() {
        return reason2nd7;
    }

    /**
     * @param reason2nd7 the reason2nd7 to set
     */
    public void setReason2nd7(int reason2nd7) {
        this.reason2nd7 = reason2nd7;
    }

    /**
     * @return the reason2nd8
     */
    public int getReason2nd8() {
        return reason2nd8;
    }

    /**
     * @param reason2nd8 the reason2nd8 to set
     */
    public void setReason2nd8(int reason2nd8) {
        this.reason2nd8 = reason2nd8;
    }

    /**
     * @return the reason2nd9
     */
    public int getReason2nd9() {
        return reason2nd9;
    }

    /**
     * @param reason2nd9 the reason2nd9 to set
     */
    public void setReason2nd9(int reason2nd9) {
        this.reason2nd9 = reason2nd9;
    }

    /**
     * @return the reason2nd10
     */
    public int getReason2nd10() {
        return reason2nd10;
    }

    /**
     * @param reason2nd10 the reason2nd10 to set
     */
    public void setReason2nd10(int reason2nd10) {
        this.reason2nd10 = reason2nd10;
    }

    /**
     * @return the reason2nd11
     */
    public int getReason2nd11() {
        return reason2nd11;
    }

    /**
     * @param reason2nd11 the reason2nd11 to set
     */
    public void setReason2nd11(int reason2nd11) {
        this.reason2nd11 = reason2nd11;
    }

    /**
     * @return the reason2nd12
     */
    public int getReason2nd12() {
        return reason2nd12;
    }

    /**
     * @param reason2nd12 the reason2nd12 to set
     */
    public void setReason2nd12(int reason2nd12) {
        this.reason2nd12 = reason2nd12;
    }

    /**
     * @return the reason2nd13
     */
    public int getReason2nd13() {
        return reason2nd13;
    }

    /**
     * @param reason2nd13 the reason2nd13 to set
     */
    public void setReason2nd13(int reason2nd13) {
        this.reason2nd13 = reason2nd13;
    }

    /**
     * @return the reason2nd14
     */
    public int getReason2nd14() {
        return reason2nd14;
    }

    /**
     * @param reason2nd14 the reason2nd14 to set
     */
    public void setReason2nd14(int reason2nd14) {
        this.reason2nd14 = reason2nd14;
    }

    /**
     * @return the reason2nd15
     */
    public int getReason2nd15() {
        return reason2nd15;
    }

    /**
     * @param reason2nd15 the reason2nd15 to set
     */
    public void setReason2nd15(int reason2nd15) {
        this.reason2nd15 = reason2nd15;
    }

    /**
     * @return the reason2nd16
     */
    public int getReason2nd16() {
        return reason2nd16;
    }

    /**
     * @param reason2nd16 the reason2nd16 to set
     */
    public void setReason2nd16(int reason2nd16) {
        this.reason2nd16 = reason2nd16;
    }

    /**
     * @return the reason2nd17
     */
    public int getReason2nd17() {
        return reason2nd17;
    }

    /**
     * @param reason2nd17 the reason2nd17 to set
     */
    public void setReason2nd17(int reason2nd17) {
        this.reason2nd17 = reason2nd17;
    }

    /**
     * @return the reason2nd18
     */
    public int getReason2nd18() {
        return reason2nd18;
    }

    /**
     * @param reason2nd18 the reason2nd18 to set
     */
    public void setReason2nd18(int reason2nd18) {
        this.reason2nd18 = reason2nd18;
    }

    /**
     * @return the reason2nd19
     */
    public int getReason2nd19() {
        return reason2nd19;
    }

    /**
     * @param reason2nd19 the reason2nd19 to set
     */
    public void setReason2nd19(int reason2nd19) {
        this.reason2nd19 = reason2nd19;
    }

    /**
     * @return the reason2nd20
     */
    public int getReason2nd20() {
        return reason2nd20;
    }

    /**
     * @param reason2nd20 the reason2nd20 to set
     */
    public void setReason2nd20(int reason2nd20) {
        this.reason2nd20 = reason2nd20;
    }

    /**
     * @return the reason2nd21
     */
    public int getReason2nd21() {
        return reason2nd21;
    }

    /**
     * @param reason2nd21 the reason2nd21 to set
     */
    public void setReason2nd21(int reason2nd21) {
        this.reason2nd21 = reason2nd21;
    }

    /**
     * @return the reason2nd22
     */
    public int getReason2nd22() {
        return reason2nd22;
    }

    /**
     * @param reason2nd22 the reason2nd22 to set
     */
    public void setReason2nd22(int reason2nd22) {
        this.reason2nd22 = reason2nd22;
    }

    /**
     * @return the reason2nd23
     */
    public int getReason2nd23() {
        return reason2nd23;
    }

    /**
     * @param reason2nd23 the reason2nd23 to set
     */
    public void setReason2nd23(int reason2nd23) {
        this.reason2nd23 = reason2nd23;
    }

    /**
     * @return the reason2nd24
     */
    public int getReason2nd24() {
        return reason2nd24;
    }

    /**
     * @param reason2nd24 the reason2nd24 to set
     */
    public void setReason2nd24(int reason2nd24) {
        this.reason2nd24 = reason2nd24;
    }

    /**
     * @return the reason2nd25
     */
    public int getReason2nd25() {
        return reason2nd25;
    }

    /**
     * @param reason2nd25 the reason2nd25 to set
     */
    public void setReason2nd25(int reason2nd25) {
        this.reason2nd25 = reason2nd25;
    }

    /**
     * @return the reason2nd26
     */
    public int getReason2nd26() {
        return reason2nd26;
    }

    /**
     * @param reason2nd26 the reason2nd26 to set
     */
    public void setReason2nd26(int reason2nd26) {
        this.reason2nd26 = reason2nd26;
    }

    /**
     * @return the reason2nd27
     */
    public int getReason2nd27() {
        return reason2nd27;
    }

    /**
     * @param reason2nd27 the reason2nd27 to set
     */
    public void setReason2nd27(int reason2nd27) {
        this.reason2nd27 = reason2nd27;
    }

    /**
     * @return the reason2nd28
     */
    public int getReason2nd28() {
        return reason2nd28;
    }

    /**
     * @param reason2nd28 the reason2nd28 to set
     */
    public void setReason2nd28(int reason2nd28) {
        this.reason2nd28 = reason2nd28;
    }

    /**
     * @return the reason2nd29
     */
    public int getReason2nd29() {
        return reason2nd29;
    }

    /**
     * @param reason2nd29 the reason2nd29 to set
     */
    public void setReason2nd29(int reason2nd29) {
        this.reason2nd29 = reason2nd29;
    }

    /**
     * @return the reason2nd30
     */
    public int getReason2nd30() {
        return reason2nd30;
    }

    /**
     * @param reason2nd30 the reason2nd30 to set
     */
    public void setReason2nd30(int reason2nd30) {
        this.reason2nd30 = reason2nd30;
    }

    /**
     * @return the reason2nd31
     */
    public int getReason2nd31() {
        return reason2nd31;
    }

    /**
     * @param reason2nd31 the reason2nd31 to set
     */
    public void setReason2nd31(int reason2nd31) {
        this.reason2nd31 = reason2nd31;
    }

    /**
     * @return the note1
     */
    public String getNote1() {
        return note1;
    }

    /**
     * @param note1 the note1 to set
     */
    public void setNote1(String note1) {
        this.note1 = note1;
    }

    /**
     * @return the note2
     */
    public String getNote2() {
        return note2;
    }

    /**
     * @param note2 the note2 to set
     */
    public void setNote2(String note2) {
        this.note2 = note2;
    }

    /**
     * @return the note3
     */
    public String getNote3() {
        return note3;
    }

    /**
     * @param note3 the note3 to set
     */
    public void setNote3(String note3) {
        this.note3 = note3;
    }

    /**
     * @return the note4
     */
    public String getNote4() {
        return note4;
    }

    /**
     * @param note4 the note4 to set
     */
    public void setNote4(String note4) {
        this.note4 = note4;
    }

    /**
     * @return the note5
     */
    public String getNote5() {
        return note5;
    }

    /**
     * @param note5 the note5 to set
     */
    public void setNote5(String note5) {
        this.note5 = note5;
    }

    /**
     * @return the note6
     */
    public String getNote6() {
        return note6;
    }

    /**
     * @param note6 the note6 to set
     */
    public void setNote6(String note6) {
        this.note6 = note6;
    }

    /**
     * @return the note7
     */
    public String getNote7() {
        return note7;
    }

    /**
     * @param note7 the note7 to set
     */
    public void setNote7(String note7) {
        this.note7 = note7;
    }

    /**
     * @return the note8
     */
    public String getNote8() {
        return note8;
    }

    /**
     * @param note8 the note8 to set
     */
    public void setNote8(String note8) {
        this.note8 = note8;
    }

    /**
     * @return the note9
     */
    public String getNote9() {
        return note9;
    }

    /**
     * @param note9 the note9 to set
     */
    public void setNote9(String note9) {
        this.note9 = note9;
    }

    /**
     * @return the note10
     */
    public String getNote10() {
        return note10;
    }

    /**
     * @param note10 the note10 to set
     */
    public void setNote10(String note10) {
        this.note10 = note10;
    }

    /**
     * @return the note11
     */
    public String getNote11() {
        return note11;
    }

    /**
     * @param note11 the note11 to set
     */
    public void setNote11(String note11) {
        this.note11 = note11;
    }

    /**
     * @return the note12
     */
    public String getNote12() {
        return note12;
    }

    /**
     * @param note12 the note12 to set
     */
    public void setNote12(String note12) {
        this.note12 = note12;
    }

    /**
     * @return the note13
     */
    public String getNote13() {
        return note13;
    }

    /**
     * @param note13 the note13 to set
     */
    public void setNote13(String note13) {
        this.note13 = note13;
    }

    /**
     * @return the note14
     */
    public String getNote14() {
        return note14;
    }

    /**
     * @param note14 the note14 to set
     */
    public void setNote14(String note14) {
        this.note14 = note14;
    }

    /**
     * @return the note15
     */
    public String getNote15() {
        return note15;
    }

    /**
     * @param note15 the note15 to set
     */
    public void setNote15(String note15) {
        this.note15 = note15;
    }

    /**
     * @return the note16
     */
    public String getNote16() {
        return note16;
    }

    /**
     * @param note16 the note16 to set
     */
    public void setNote16(String note16) {
        this.note16 = note16;
    }

    /**
     * @return the note17
     */
    public String getNote17() {
        return note17;
    }

    /**
     * @param note17 the note17 to set
     */
    public void setNote17(String note17) {
        this.note17 = note17;
    }

    /**
     * @return the note18
     */
    public String getNote18() {
        return note18;
    }

    /**
     * @param note18 the note18 to set
     */
    public void setNote18(String note18) {
        this.note18 = note18;
    }

    /**
     * @return the note19
     */
    public String getNote19() {
        return note19;
    }

    /**
     * @param note19 the note19 to set
     */
    public void setNote19(String note19) {
        this.note19 = note19;
    }

    /**
     * @return the note20
     */
    public String getNote20() {
        return note20;
    }

    /**
     * @param note20 the note20 to set
     */
    public void setNote20(String note20) {
        this.note20 = note20;
    }

    /**
     * @return the note21
     */
    public String getNote21() {
        return note21;
    }

    /**
     * @param note21 the note21 to set
     */
    public void setNote21(String note21) {
        this.note21 = note21;
    }

    /**
     * @return the note22
     */
    public String getNote22() {
        return note22;
    }

    /**
     * @param note22 the note22 to set
     */
    public void setNote22(String note22) {
        this.note22 = note22;
    }

    /**
     * @return the note23
     */
    public String getNote23() {
        return note23;
    }

    /**
     * @param note23 the note23 to set
     */
    public void setNote23(String note23) {
        this.note23 = note23;
    }

    /**
     * @return the note24
     */
    public String getNote24() {
        return note24;
    }

    /**
     * @param note24 the note24 to set
     */
    public void setNote24(String note24) {
        this.note24 = note24;
    }

    /**
     * @return the note25
     */
    public String getNote25() {
        return note25;
    }

    /**
     * @param note25 the note25 to set
     */
    public void setNote25(String note25) {
        this.note25 = note25;
    }

    /**
     * @return the note26
     */
    public String getNote26() {
        return note26;
    }

    /**
     * @param note26 the note26 to set
     */
    public void setNote26(String note26) {
        this.note26 = note26;
    }

    /**
     * @return the note27
     */
    public String getNote27() {
        return note27;
    }

    /**
     * @param note27 the note27 to set
     */
    public void setNote27(String note27) {
        this.note27 = note27;
    }

    /**
     * @return the note28
     */
    public String getNote28() {
        return note28;
    }

    /**
     * @param note28 the note28 to set
     */
    public void setNote28(String note28) {
        this.note28 = note28;
    }

    /**
     * @return the note29
     */
    public String getNote29() {
        return note29;
    }

    /**
     * @param note29 the note29 to set
     */
    public void setNote29(String note29) {
        this.note29 = note29;
    }

    /**
     * @return the note30
     */
    public String getNote30() {
        return note30;
    }

    /**
     * @param note30 the note30 to set
     */
    public void setNote30(String note30) {
        this.note30 = note30;
    }

    /**
     * @return the note31
     */
    public String getNote31() {
        return note31;
    }

    /**
     * @param note31 the note31 to set
     */
    public void setNote31(String note31) {
        this.note31 = note31;
    }

    /**
     * @return the note2nd1
     */
    public String getNote2nd1() {
        return note2nd1;
    }

    /**
     * @param note2nd1 the note2nd1 to set
     */
    public void setNote2nd1(String note2nd1) {
        this.note2nd1 = note2nd1;
    }

    /**
     * @return the note2nd2
     */
    public String getNote2nd2() {
        return note2nd2;
    }

    /**
     * @param note2nd2 the note2nd2 to set
     */
    public void setNote2nd2(String note2nd2) {
        this.note2nd2 = note2nd2;
    }

    /**
     * @return the note2nd3
     */
    public String getNote2nd3() {
        return note2nd3;
    }

    /**
     * @param note2nd3 the note2nd3 to set
     */
    public void setNote2nd3(String note2nd3) {
        this.note2nd3 = note2nd3;
    }

    /**
     * @return the note2nd4
     */
    public String getNote2nd4() {
        return note2nd4;
    }

    /**
     * @param note2nd4 the note2nd4 to set
     */
    public void setNote2nd4(String note2nd4) {
        this.note2nd4 = note2nd4;
    }

    /**
     * @return the note2nd5
     */
    public String getNote2nd5() {
        return note2nd5;
    }

    /**
     * @param note2nd5 the note2nd5 to set
     */
    public void setNote2nd5(String note2nd5) {
        this.note2nd5 = note2nd5;
    }

    /**
     * @return the note2nd6
     */
    public String getNote2nd6() {
        return note2nd6;
    }

    /**
     * @param note2nd6 the note2nd6 to set
     */
    public void setNote2nd6(String note2nd6) {
        this.note2nd6 = note2nd6;
    }

    /**
     * @return the note2nd7
     */
    public String getNote2nd7() {
        return note2nd7;
    }

    /**
     * @param note2nd7 the note2nd7 to set
     */
    public void setNote2nd7(String note2nd7) {
        this.note2nd7 = note2nd7;
    }

    /**
     * @return the note2nd8
     */
    public String getNote2nd8() {
        return note2nd8;
    }

    /**
     * @param note2nd8 the note2nd8 to set
     */
    public void setNote2nd8(String note2nd8) {
        this.note2nd8 = note2nd8;
    }

    /**
     * @return the note2nd9
     */
    public String getNote2nd9() {
        return note2nd9;
    }

    /**
     * @param note2nd9 the note2nd9 to set
     */
    public void setNote2nd9(String note2nd9) {
        this.note2nd9 = note2nd9;
    }

    /**
     * @return the note2nd10
     */
    public String getNote2nd10() {
        return note2nd10;
    }

    /**
     * @param note2nd10 the note2nd10 to set
     */
    public void setNote2nd10(String note2nd10) {
        this.note2nd10 = note2nd10;
    }

    /**
     * @return the note2nd11
     */
    public String getNote2nd11() {
        return note2nd11;
    }

    /**
     * @param note2nd11 the note2nd11 to set
     */
    public void setNote2nd11(String note2nd11) {
        this.note2nd11 = note2nd11;
    }

    /**
     * @return the note2nd12
     */
    public String getNote2nd12() {
        return note2nd12;
    }

    /**
     * @param note2nd12 the note2nd12 to set
     */
    public void setNote2nd12(String note2nd12) {
        this.note2nd12 = note2nd12;
    }

    /**
     * @return the note2nd13
     */
    public String getNote2nd13() {
        return note2nd13;
    }

    /**
     * @param note2nd13 the note2nd13 to set
     */
    public void setNote2nd13(String note2nd13) {
        this.note2nd13 = note2nd13;
    }

    /**
     * @return the note2nd14
     */
    public String getNote2nd14() {
        return note2nd14;
    }

    /**
     * @param note2nd14 the note2nd14 to set
     */
    public void setNote2nd14(String note2nd14) {
        this.note2nd14 = note2nd14;
    }

    /**
     * @return the note2nd15
     */
    public String getNote2nd15() {
        return note2nd15;
    }

    /**
     * @param note2nd15 the note2nd15 to set
     */
    public void setNote2nd15(String note2nd15) {
        this.note2nd15 = note2nd15;
    }

    /**
     * @return the note2nd16
     */
    public String getNote2nd16() {
        return note2nd16;
    }

    /**
     * @param note2nd16 the note2nd16 to set
     */
    public void setNote2nd16(String note2nd16) {
        this.note2nd16 = note2nd16;
    }

    /**
     * @return the note2nd17
     */
    public String getNote2nd17() {
        return note2nd17;
    }

    /**
     * @param note2nd17 the note2nd17 to set
     */
    public void setNote2nd17(String note2nd17) {
        this.note2nd17 = note2nd17;
    }

    /**
     * @return the note2nd18
     */
    public String getNote2nd18() {
        return note2nd18;
    }

    /**
     * @param note2nd18 the note2nd18 to set
     */
    public void setNote2nd18(String note2nd18) {
        this.note2nd18 = note2nd18;
    }

    /**
     * @return the note2nd19
     */
    public String getNote2nd19() {
        return note2nd19;
    }

    /**
     * @param note2nd19 the note2nd19 to set
     */
    public void setNote2nd19(String note2nd19) {
        this.note2nd19 = note2nd19;
    }

    /**
     * @return the note2nd20
     */
    public String getNote2nd20() {
        return note2nd20;
    }

    /**
     * @param note2nd20 the note2nd20 to set
     */
    public void setNote2nd20(String note2nd20) {
        this.note2nd20 = note2nd20;
    }

    /**
     * @return the note2nd21
     */
    public String getNote2nd21() {
        return note2nd21;
    }

    /**
     * @param note2nd21 the note2nd21 to set
     */
    public void setNote2nd21(String note2nd21) {
        this.note2nd21 = note2nd21;
    }

    /**
     * @return the note2nd22
     */
    public String getNote2nd22() {
        return note2nd22;
    }

    /**
     * @param note2nd22 the note2nd22 to set
     */
    public void setNote2nd22(String note2nd22) {
        this.note2nd22 = note2nd22;
    }

    /**
     * @return the note2nd23
     */
    public String getNote2nd23() {
        return note2nd23;
    }

    /**
     * @param note2nd23 the note2nd23 to set
     */
    public void setNote2nd23(String note2nd23) {
        this.note2nd23 = note2nd23;
    }

    /**
     * @return the note2nd24
     */
    public String getNote2nd24() {
        return note2nd24;
    }

    /**
     * @param note2nd24 the note2nd24 to set
     */
    public void setNote2nd24(String note2nd24) {
        this.note2nd24 = note2nd24;
    }

    /**
     * @return the note2nd25
     */
    public String getNote2nd25() {
        return note2nd25;
    }

    /**
     * @param note2nd25 the note2nd25 to set
     */
    public void setNote2nd25(String note2nd25) {
        this.note2nd25 = note2nd25;
    }

    /**
     * @return the note2nd26
     */
    public String getNote2nd26() {
        return note2nd26;
    }

    /**
     * @param note2nd26 the note2nd26 to set
     */
    public void setNote2nd26(String note2nd26) {
        this.note2nd26 = note2nd26;
    }

    /**
     * @return the note2nd27
     */
    public String getNote2nd27() {
        return note2nd27;
    }

    /**
     * @param note2nd27 the note2nd27 to set
     */
    public void setNote2nd27(String note2nd27) {
        this.note2nd27 = note2nd27;
    }

    /**
     * @return the note2nd28
     */
    public String getNote2nd28() {
        return note2nd28;
    }

    /**
     * @param note2nd28 the note2nd28 to set
     */
    public void setNote2nd28(String note2nd28) {
        this.note2nd28 = note2nd28;
    }

    /**
     * @return the note2nd29
     */
    public String getNote2nd29() {
        return note2nd29;
    }

    /**
     * @param note2nd29 the note2nd29 to set
     */
    public void setNote2nd29(String note2nd29) {
        this.note2nd29 = note2nd29;
    }

    /**
     * @return the note2nd30
     */
    public String getNote2nd30() {
        return note2nd30;
    }

    /**
     * @param note2nd30 the note2nd30 to set
     */
    public void setNote2nd30(String note2nd30) {
        this.note2nd30 = note2nd30;
    }

    /**
     * @return the note2nd31
     */
    public String getNote2nd31() {
        return note2nd31;
    }

    /**
     * @param note2nd31 the note2nd31 to set
     */
    public void setNote2nd31(String note2nd31) {
        this.note2nd31 = note2nd31;
    }

    /**
     * @return the in1
     */
    public Date getIn1() {
        return in1;
    }

    /**
     * @param in1 the in1 to set
     */
    public void setIn1(Date in1) {
        this.in1 = in1;
    }

    /**
     * @return the in2
     */
    public Date getIn2() {
        return in2;
    }

    /**
     * @param in2 the in2 to set
     */
    public void setIn2(Date in2) {
        this.in2 = in2;
    }

    /**
     * @return the in3
     */
    public Date getIn3() {
        return in3;
    }

    /**
     * @param in3 the in3 to set
     */
    public void setIn3(Date in3) {
        this.in3 = in3;
    }

    /**
     * @return the in4
     */
    public Date getIn4() {
        return in4;
    }

    /**
     * @param in4 the in4 to set
     */
    public void setIn4(Date in4) {
        this.in4 = in4;
    }

    /**
     * @return the in5
     */
    public Date getIn5() {
        return in5;
    }

    /**
     * @param in5 the in5 to set
     */
    public void setIn5(Date in5) {
        this.in5 = in5;
    }

    /**
     * @return the in6
     */
    public Date getIn6() {
        return in6;
    }

    /**
     * @param in6 the in6 to set
     */
    public void setIn6(Date in6) {
        this.in6 = in6;
    }

    /**
     * @return the in7
     */
    public Date getIn7() {
        return in7;
    }

    /**
     * @param in7 the in7 to set
     */
    public void setIn7(Date in7) {
        this.in7 = in7;
    }

    /**
     * @return the in8
     */
    public Date getIn8() {
        return in8;
    }

    /**
     * @param in8 the in8 to set
     */
    public void setIn8(Date in8) {
        this.in8 = in8;
    }

    /**
     * @return the in9
     */
    public Date getIn9() {
        return in9;
    }

    /**
     * @param in9 the in9 to set
     */
    public void setIn9(Date in9) {
        this.in9 = in9;
    }

    /**
     * @return the in10
     */
    public Date getIn10() {
        return in10;
    }

    /**
     * @param in10 the in10 to set
     */
    public void setIn10(Date in10) {
        this.in10 = in10;
    }

    /**
     * @return the in11
     */
    public Date getIn11() {
        return in11;
    }

    /**
     * @param in11 the in11 to set
     */
    public void setIn11(Date in11) {
        this.in11 = in11;
    }

    /**
     * @return the in12
     */
    public Date getIn12() {
        return in12;
    }

    /**
     * @param in12 the in12 to set
     */
    public void setIn12(Date in12) {
        this.in12 = in12;
    }

    /**
     * @return the in13
     */
    public Date getIn13() {
        return in13;
    }

    /**
     * @param in13 the in13 to set
     */
    public void setIn13(Date in13) {
        this.in13 = in13;
    }

    /**
     * @return the in14
     */
    public Date getIn14() {
        return in14;
    }

    /**
     * @param in14 the in14 to set
     */
    public void setIn14(Date in14) {
        this.in14 = in14;
    }

    /**
     * @return the in15
     */
    public Date getIn15() {
        return in15;
    }

    /**
     * @param in15 the in15 to set
     */
    public void setIn15(Date in15) {
        this.in15 = in15;
    }

    /**
     * @return the in16
     */
    public Date getIn16() {
        return in16;
    }

    /**
     * @param in16 the in16 to set
     */
    public void setIn16(Date in16) {
        this.in16 = in16;
    }

    /**
     * @return the in17
     */
    public Date getIn17() {
        return in17;
    }

    /**
     * @param in17 the in17 to set
     */
    public void setIn17(Date in17) {
        this.in17 = in17;
    }

    /**
     * @return the in18
     */
    public Date getIn18() {
        return in18;
    }

    /**
     * @param in18 the in18 to set
     */
    public void setIn18(Date in18) {
        this.in18 = in18;
    }

    /**
     * @return the in19
     */
    public Date getIn19() {
        return in19;
    }

    /**
     * @param in19 the in19 to set
     */
    public void setIn19(Date in19) {
        this.in19 = in19;
    }

    /**
     * @return the in20
     */
    public Date getIn20() {
        return in20;
    }

    /**
     * @param in20 the in20 to set
     */
    public void setIn20(Date in20) {
        this.in20 = in20;
    }

    /**
     * @return the in21
     */
    public Date getIn21() {
        return in21;
    }

    /**
     * @param in21 the in21 to set
     */
    public void setIn21(Date in21) {
        this.in21 = in21;
    }

    /**
     * @return the in22
     */
    public Date getIn22() {
        return in22;
    }

    /**
     * @param in22 the in22 to set
     */
    public void setIn22(Date in22) {
        this.in22 = in22;
    }

    /**
     * @return the in23
     */
    public Date getIn23() {
        return in23;
    }

    /**
     * @param in23 the in23 to set
     */
    public void setIn23(Date in23) {
        this.in23 = in23;
    }

    /**
     * @return the in24
     */
    public Date getIn24() {
        return in24;
    }

    /**
     * @param in24 the in24 to set
     */
    public void setIn24(Date in24) {
        this.in24 = in24;
    }

    /**
     * @return the in25
     */
    public Date getIn25() {
        return in25;
    }

    /**
     * @param in25 the in25 to set
     */
    public void setIn25(Date in25) {
        this.in25 = in25;
    }

    /**
     * @return the in26
     */
    public Date getIn26() {
        return in26;
    }

    /**
     * @param in26 the in26 to set
     */
    public void setIn26(Date in26) {
        this.in26 = in26;
    }

    /**
     * @return the in27
     */
    public Date getIn27() {
        return in27;
    }

    /**
     * @param in27 the in27 to set
     */
    public void setIn27(Date in27) {
        this.in27 = in27;
    }

    /**
     * @return the in28
     */
    public Date getIn28() {
        return in28;
    }

    /**
     * @param in28 the in28 to set
     */
    public void setIn28(Date in28) {
        this.in28 = in28;
    }

    /**
     * @return the in29
     */
    public Date getIn29() {
        return in29;
    }

    /**
     * @param in29 the in29 to set
     */
    public void setIn29(Date in29) {
        this.in29 = in29;
    }

    /**
     * @return the in30
     */
    public Date getIn30() {
        return in30;
    }

    /**
     * @param in30 the in30 to set
     */
    public void setIn30(Date in30) {
        this.in30 = in30;
    }

    /**
     * @return the in31
     */
    public Date getIn31() {
        return in31;
    }

    /**
     * @param in31 the in31 to set
     */
    public void setIn31(Date in31) {
        this.in31 = in31;
    }

    /**
     * @return the in2nd1
     */
    public Date getIn2nd1() {
        return in2nd1;
    }

    /**
     * @param in2nd1 the in2nd1 to set
     */
    public void setIn2nd1(Date in2nd1) {
        this.in2nd1 = in2nd1;
    }

    /**
     * @return the in2nd2
     */
    public Date getIn2nd2() {
        return in2nd2;
    }

    /**
     * @param in2nd2 the in2nd2 to set
     */
    public void setIn2nd2(Date in2nd2) {
        this.in2nd2 = in2nd2;
    }

    /**
     * @return the in2nd3
     */
    public Date getIn2nd3() {
        return in2nd3;
    }

    /**
     * @param in2nd3 the in2nd3 to set
     */
    public void setIn2nd3(Date in2nd3) {
        this.in2nd3 = in2nd3;
    }

    /**
     * @return the in2nd4
     */
    public Date getIn2nd4() {
        return in2nd4;
    }

    /**
     * @param in2nd4 the in2nd4 to set
     */
    public void setIn2nd4(Date in2nd4) {
        this.in2nd4 = in2nd4;
    }

    /**
     * @return the in2nd5
     */
    public Date getIn2nd5() {
        return in2nd5;
    }

    /**
     * @param in2nd5 the in2nd5 to set
     */
    public void setIn2nd5(Date in2nd5) {
        this.in2nd5 = in2nd5;
    }

    /**
     * @return the in2nd6
     */
    public Date getIn2nd6() {
        return in2nd6;
    }

    /**
     * @param in2nd6 the in2nd6 to set
     */
    public void setIn2nd6(Date in2nd6) {
        this.in2nd6 = in2nd6;
    }

    /**
     * @return the in2nd7
     */
    public Date getIn2nd7() {
        return in2nd7;
    }

    /**
     * @param in2nd7 the in2nd7 to set
     */
    public void setIn2nd7(Date in2nd7) {
        this.in2nd7 = in2nd7;
    }

    /**
     * @return the in2nd8
     */
    public Date getIn2nd8() {
        return in2nd8;
    }

    /**
     * @param in2nd8 the in2nd8 to set
     */
    public void setIn2nd8(Date in2nd8) {
        this.in2nd8 = in2nd8;
    }

    /**
     * @return the in2nd9
     */
    public Date getIn2nd9() {
        return in2nd9;
    }

    /**
     * @param in2nd9 the in2nd9 to set
     */
    public void setIn2nd9(Date in2nd9) {
        this.in2nd9 = in2nd9;
    }

    /**
     * @return the in2nd10
     */
    public Date getIn2nd10() {
        return in2nd10;
    }

    /**
     * @param in2nd10 the in2nd10 to set
     */
    public void setIn2nd10(Date in2nd10) {
        this.in2nd10 = in2nd10;
    }

    /**
     * @return the in2nd11
     */
    public Date getIn2nd11() {
        return in2nd11;
    }

    /**
     * @param in2nd11 the in2nd11 to set
     */
    public void setIn2nd11(Date in2nd11) {
        this.in2nd11 = in2nd11;
    }

    /**
     * @return the in2nd12
     */
    public Date getIn2nd12() {
        return in2nd12;
    }

    /**
     * @param in2nd12 the in2nd12 to set
     */
    public void setIn2nd12(Date in2nd12) {
        this.in2nd12 = in2nd12;
    }

    /**
     * @return the in2nd13
     */
    public Date getIn2nd13() {
        return in2nd13;
    }

    /**
     * @param in2nd13 the in2nd13 to set
     */
    public void setIn2nd13(Date in2nd13) {
        this.in2nd13 = in2nd13;
    }

    /**
     * @return the in2nd14
     */
    public Date getIn2nd14() {
        return in2nd14;
    }

    /**
     * @param in2nd14 the in2nd14 to set
     */
    public void setIn2nd14(Date in2nd14) {
        this.in2nd14 = in2nd14;
    }

    /**
     * @return the in2nd15
     */
    public Date getIn2nd15() {
        return in2nd15;
    }

    /**
     * @param in2nd15 the in2nd15 to set
     */
    public void setIn2nd15(Date in2nd15) {
        this.in2nd15 = in2nd15;
    }

    /**
     * @return the in2nd16
     */
    public Date getIn2nd16() {
        return in2nd16;
    }

    /**
     * @param in2nd16 the in2nd16 to set
     */
    public void setIn2nd16(Date in2nd16) {
        this.in2nd16 = in2nd16;
    }

    /**
     * @return the in2nd17
     */
    public Date getIn2nd17() {
        return in2nd17;
    }

    /**
     * @param in2nd17 the in2nd17 to set
     */
    public void setIn2nd17(Date in2nd17) {
        this.in2nd17 = in2nd17;
    }

    /**
     * @return the in2nd18
     */
    public Date getIn2nd18() {
        return in2nd18;
    }

    /**
     * @param in2nd18 the in2nd18 to set
     */
    public void setIn2nd18(Date in2nd18) {
        this.in2nd18 = in2nd18;
    }

    /**
     * @return the in2nd19
     */
    public Date getIn2nd19() {
        return in2nd19;
    }

    /**
     * @param in2nd19 the in2nd19 to set
     */
    public void setIn2nd19(Date in2nd19) {
        this.in2nd19 = in2nd19;
    }

    /**
     * @return the in2nd20
     */
    public Date getIn2nd20() {
        return in2nd20;
    }

    /**
     * @param in2nd20 the in2nd20 to set
     */
    public void setIn2nd20(Date in2nd20) {
        this.in2nd20 = in2nd20;
    }

    /**
     * @return the in2nd21
     */
    public Date getIn2nd21() {
        return in2nd21;
    }

    /**
     * @param in2nd21 the in2nd21 to set
     */
    public void setIn2nd21(Date in2nd21) {
        this.in2nd21 = in2nd21;
    }

    /**
     * @return the in2nd22
     */
    public Date getIn2nd22() {
        return in2nd22;
    }

    /**
     * @param in2nd22 the in2nd22 to set
     */
    public void setIn2nd22(Date in2nd22) {
        this.in2nd22 = in2nd22;
    }

    /**
     * @return the in2nd23
     */
    public Date getIn2nd23() {
        return in2nd23;
    }

    /**
     * @param in2nd23 the in2nd23 to set
     */
    public void setIn2nd23(Date in2nd23) {
        this.in2nd23 = in2nd23;
    }

    /**
     * @return the in2nd24
     */
    public Date getIn2nd24() {
        return in2nd24;
    }

    /**
     * @param in2nd24 the in2nd24 to set
     */
    public void setIn2nd24(Date in2nd24) {
        this.in2nd24 = in2nd24;
    }

    /**
     * @return the in2nd25
     */
    public Date getIn2nd25() {
        return in2nd25;
    }

    /**
     * @param in2nd25 the in2nd25 to set
     */
    public void setIn2nd25(Date in2nd25) {
        this.in2nd25 = in2nd25;
    }

    /**
     * @return the in2nd26
     */
    public Date getIn2nd26() {
        return in2nd26;
    }

    /**
     * @param in2nd26 the in2nd26 to set
     */
    public void setIn2nd26(Date in2nd26) {
        this.in2nd26 = in2nd26;
    }

    /**
     * @return the in2nd27
     */
    public Date getIn2nd27() {
        return in2nd27;
    }

    /**
     * @param in2nd27 the in2nd27 to set
     */
    public void setIn2nd27(Date in2nd27) {
        this.in2nd27 = in2nd27;
    }

    /**
     * @return the in2nd28
     */
    public Date getIn2nd28() {
        return in2nd28;
    }

    /**
     * @param in2nd28 the in2nd28 to set
     */
    public void setIn2nd28(Date in2nd28) {
        this.in2nd28 = in2nd28;
    }

    /**
     * @return the in2nd29
     */
    public Date getIn2nd29() {
        return in2nd29;
    }

    /**
     * @param in2nd29 the in2nd29 to set
     */
    public void setIn2nd29(Date in2nd29) {
        this.in2nd29 = in2nd29;
    }

    /**
     * @return the in2nd30
     */
    public Date getIn2nd30() {
        return in2nd30;
    }

    /**
     * @param in2nd30 the in2nd30 to set
     */
    public void setIn2nd30(Date in2nd30) {
        this.in2nd30 = in2nd30;
    }

    /**
     * @return the in2nd31
     */
    public Date getIn2nd31() {
        return in2nd31;
    }

    /**
     * @param in2nd31 the in2nd31 to set
     */
    public void setIn2nd31(Date in2nd31) {
        this.in2nd31 = in2nd31;
    }

    /**
     * @return the out1
     */
    public Date getOut1() {
        return out1;
    }

    /**
     * @param out1 the out1 to set
     */
    public void setOut1(Date out1) {
        this.out1 = out1;
    }

    /**
     * @return the out2
     */
    public Date getOut2() {
        return out2;
    }

    /**
     * @param out2 the out2 to set
     */
    public void setOut2(Date out2) {
        this.out2 = out2;
    }

    /**
     * @return the out3
     */
    public Date getOut3() {
        return out3;
    }

    /**
     * @param out3 the out3 to set
     */
    public void setOut3(Date out3) {
        this.out3 = out3;
    }

    /**
     * @return the out4
     */
    public Date getOut4() {
        return out4;
    }

    /**
     * @param out4 the out4 to set
     */
    public void setOut4(Date out4) {
        this.out4 = out4;
    }

    /**
     * @return the out5
     */
    public Date getOut5() {
        return out5;
    }

    /**
     * @param out5 the out5 to set
     */
    public void setOut5(Date out5) {
        this.out5 = out5;
    }

    /**
     * @return the out6
     */
    public Date getOut6() {
        return out6;
    }

    /**
     * @param out6 the out6 to set
     */
    public void setOut6(Date out6) {
        this.out6 = out6;
    }

    /**
     * @return the out7
     */
    public Date getOut7() {
        return out7;
    }

    /**
     * @param out7 the out7 to set
     */
    public void setOut7(Date out7) {
        this.out7 = out7;
    }

    /**
     * @return the out8
     */
    public Date getOut8() {
        return out8;
    }

    /**
     * @param out8 the out8 to set
     */
    public void setOut8(Date out8) {
        this.out8 = out8;
    }

    /**
     * @return the out9
     */
    public Date getOut9() {
        return out9;
    }

    /**
     * @param out9 the out9 to set
     */
    public void setOut9(Date out9) {
        this.out9 = out9;
    }

    /**
     * @return the out10
     */
    public Date getOut10() {
        return out10;
    }

    /**
     * @param out10 the out10 to set
     */
    public void setOut10(Date out10) {
        this.out10 = out10;
    }

    /**
     * @return the out11
     */
    public Date getOut11() {
        return out11;
    }

    /**
     * @param out11 the out11 to set
     */
    public void setOut11(Date out11) {
        this.out11 = out11;
    }

    /**
     * @return the out12
     */
    public Date getOut12() {
        return out12;
    }

    /**
     * @param out12 the out12 to set
     */
    public void setOut12(Date out12) {
        this.out12 = out12;
    }

    /**
     * @return the out13
     */
    public Date getOut13() {
        return out13;
    }

    /**
     * @param out13 the out13 to set
     */
    public void setOut13(Date out13) {
        this.out13 = out13;
    }

    /**
     * @return the out14
     */
    public Date getOut14() {
        return out14;
    }

    /**
     * @param out14 the out14 to set
     */
    public void setOut14(Date out14) {
        this.out14 = out14;
    }

    /**
     * @return the out15
     */
    public Date getOut15() {
        return out15;
    }

    /**
     * @param out15 the out15 to set
     */
    public void setOut15(Date out15) {
        this.out15 = out15;
    }

    /**
     * @return the out16
     */
    public Date getOut16() {
        return out16;
    }

    /**
     * @param out16 the out16 to set
     */
    public void setOut16(Date out16) {
        this.out16 = out16;
    }

    /**
     * @return the out17
     */
    public Date getOut17() {
        return out17;
    }

    /**
     * @param out17 the out17 to set
     */
    public void setOut17(Date out17) {
        this.out17 = out17;
    }

    /**
     * @return the out18
     */
    public Date getOut18() {
        return out18;
    }

    /**
     * @param out18 the out18 to set
     */
    public void setOut18(Date out18) {
        this.out18 = out18;
    }

    /**
     * @return the out19
     */
    public Date getOut19() {
        return out19;
    }

    /**
     * @param out19 the out19 to set
     */
    public void setOut19(Date out19) {
        this.out19 = out19;
    }

    /**
     * @return the out20
     */
    public Date getOut20() {
        return out20;
    }

    /**
     * @param out20 the out20 to set
     */
    public void setOut20(Date out20) {
        this.out20 = out20;
    }

    /**
     * @return the out21
     */
    public Date getOut21() {
        return out21;
    }

    /**
     * @param out21 the out21 to set
     */
    public void setOut21(Date out21) {
        this.out21 = out21;
    }

    /**
     * @return the out22
     */
    public Date getOut22() {
        return out22;
    }

    /**
     * @param out22 the out22 to set
     */
    public void setOut22(Date out22) {
        this.out22 = out22;
    }

    /**
     * @return the out23
     */
    public Date getOut23() {
        return out23;
    }

    /**
     * @param out23 the out23 to set
     */
    public void setOut23(Date out23) {
        this.out23 = out23;
    }

    /**
     * @return the out24
     */
    public Date getOut24() {
        return out24;
    }

    /**
     * @param out24 the out24 to set
     */
    public void setOut24(Date out24) {
        this.out24 = out24;
    }

    /**
     * @return the out25
     */
    public Date getOut25() {
        return out25;
    }

    /**
     * @param out25 the out25 to set
     */
    public void setOut25(Date out25) {
        this.out25 = out25;
    }

    /**
     * @return the out26
     */
    public Date getOut26() {
        return out26;
    }

    /**
     * @param out26 the out26 to set
     */
    public void setOut26(Date out26) {
        this.out26 = out26;
    }

    /**
     * @return the out27
     */
    public Date getOut27() {
        return out27;
    }

    /**
     * @param out27 the out27 to set
     */
    public void setOut27(Date out27) {
        this.out27 = out27;
    }

    /**
     * @return the out28
     */
    public Date getOut28() {
        return out28;
    }

    /**
     * @param out28 the out28 to set
     */
    public void setOut28(Date out28) {
        this.out28 = out28;
    }

    /**
     * @return the out29
     */
    public Date getOut29() {
        return out29;
    }

    /**
     * @param out29 the out29 to set
     */
    public void setOut29(Date out29) {
        this.out29 = out29;
    }

    /**
     * @return the out30
     */
    public Date getOut30() {
        return out30;
    }

    /**
     * @param out30 the out30 to set
     */
    public void setOut30(Date out30) {
        this.out30 = out30;
    }

    /**
     * @return the out31
     */
    public Date getOut31() {
        return out31;
    }

    /**
     * @param out31 the out31 to set
     */
    public void setOut31(Date out31) {
        this.out31 = out31;
    }

    /**
     * @return the out2nd1
     */
    public Date getOut2nd1() {
        return out2nd1;
    }

    /**
     * @param out2nd1 the out2nd1 to set
     */
    public void setOut2nd1(Date out2nd1) {
        this.out2nd1 = out2nd1;
    }

    /**
     * @return the out2nd2
     */
    public Date getOut2nd2() {
        return out2nd2;
    }

    /**
     * @param out2nd2 the out2nd2 to set
     */
    public void setOut2nd2(Date out2nd2) {
        this.out2nd2 = out2nd2;
    }

    /**
     * @return the out2nd3
     */
    public Date getOut2nd3() {
        return out2nd3;
    }

    /**
     * @param out2nd3 the out2nd3 to set
     */
    public void setOut2nd3(Date out2nd3) {
        this.out2nd3 = out2nd3;
    }

    /**
     * @return the out2nd4
     */
    public Date getOut2nd4() {
        return out2nd4;
    }

    /**
     * @param out2nd4 the out2nd4 to set
     */
    public void setOut2nd4(Date out2nd4) {
        this.out2nd4 = out2nd4;
    }

    /**
     * @return the out2nd5
     */
    public Date getOut2nd5() {
        return out2nd5;
    }

    /**
     * @param out2nd5 the out2nd5 to set
     */
    public void setOut2nd5(Date out2nd5) {
        this.out2nd5 = out2nd5;
    }

    /**
     * @return the out2nd6
     */
    public Date getOut2nd6() {
        return out2nd6;
    }

    /**
     * @param out2nd6 the out2nd6 to set
     */
    public void setOut2nd6(Date out2nd6) {
        this.out2nd6 = out2nd6;
    }

    /**
     * @return the out2nd7
     */
    public Date getOut2nd7() {
        return out2nd7;
    }

    /**
     * @param out2nd7 the out2nd7 to set
     */
    public void setOut2nd7(Date out2nd7) {
        this.out2nd7 = out2nd7;
    }

    /**
     * @return the out2nd8
     */
    public Date getOut2nd8() {
        return out2nd8;
    }

    /**
     * @param out2nd8 the out2nd8 to set
     */
    public void setOut2nd8(Date out2nd8) {
        this.out2nd8 = out2nd8;
    }

    /**
     * @return the out2nd9
     */
    public Date getOut2nd9() {
        return out2nd9;
    }

    /**
     * @param out2nd9 the out2nd9 to set
     */
    public void setOut2nd9(Date out2nd9) {
        this.out2nd9 = out2nd9;
    }

    /**
     * @return the out2nd10
     */
    public Date getOut2nd10() {
        return out2nd10;
    }

    /**
     * @param out2nd10 the out2nd10 to set
     */
    public void setOut2nd10(Date out2nd10) {
        this.out2nd10 = out2nd10;
    }

    /**
     * @return the out2nd11
     */
    public Date getOut2nd11() {
        return out2nd11;
    }

    /**
     * @param out2nd11 the out2nd11 to set
     */
    public void setOut2nd11(Date out2nd11) {
        this.out2nd11 = out2nd11;
    }

    /**
     * @return the out2nd12
     */
    public Date getOut2nd12() {
        return out2nd12;
    }

    /**
     * @param out2nd12 the out2nd12 to set
     */
    public void setOut2nd12(Date out2nd12) {
        this.out2nd12 = out2nd12;
    }

    /**
     * @return the out2nd13
     */
    public Date getOut2nd13() {
        return out2nd13;
    }

    /**
     * @param out2nd13 the out2nd13 to set
     */
    public void setOut2nd13(Date out2nd13) {
        this.out2nd13 = out2nd13;
    }

    /**
     * @return the out2nd14
     */
    public Date getOut2nd14() {
        return out2nd14;
    }

    /**
     * @param out2nd14 the out2nd14 to set
     */
    public void setOut2nd14(Date out2nd14) {
        this.out2nd14 = out2nd14;
    }

    /**
     * @return the out2nd15
     */
    public Date getOut2nd15() {
        return out2nd15;
    }

    /**
     * @param out2nd15 the out2nd15 to set
     */
    public void setOut2nd15(Date out2nd15) {
        this.out2nd15 = out2nd15;
    }

    /**
     * @return the out2nd16
     */
    public Date getOut2nd16() {
        return out2nd16;
    }

    /**
     * @param out2nd16 the out2nd16 to set
     */
    public void setOut2nd16(Date out2nd16) {
        this.out2nd16 = out2nd16;
    }

    /**
     * @return the out2nd17
     */
    public Date getOut2nd17() {
        return out2nd17;
    }

    /**
     * @param out2nd17 the out2nd17 to set
     */
    public void setOut2nd17(Date out2nd17) {
        this.out2nd17 = out2nd17;
    }

    /**
     * @return the out2nd18
     */
    public Date getOut2nd18() {
        return out2nd18;
    }

    /**
     * @param out2nd18 the out2nd18 to set
     */
    public void setOut2nd18(Date out2nd18) {
        this.out2nd18 = out2nd18;
    }

    /**
     * @return the out2nd19
     */
    public Date getOut2nd19() {
        return out2nd19;
    }

    /**
     * @param out2nd19 the out2nd19 to set
     */
    public void setOut2nd19(Date out2nd19) {
        this.out2nd19 = out2nd19;
    }

    /**
     * @return the out2nd20
     */
    public Date getOut2nd20() {
        return out2nd20;
    }

    /**
     * @param out2nd20 the out2nd20 to set
     */
    public void setOut2nd20(Date out2nd20) {
        this.out2nd20 = out2nd20;
    }

    /**
     * @return the out2nd21
     */
    public Date getOut2nd21() {
        return out2nd21;
    }

    /**
     * @param out2nd21 the out2nd21 to set
     */
    public void setOut2nd21(Date out2nd21) {
        this.out2nd21 = out2nd21;
    }

    /**
     * @return the out2nd22
     */
    public Date getOut2nd22() {
        return out2nd22;
    }

    /**
     * @param out2nd22 the out2nd22 to set
     */
    public void setOut2nd22(Date out2nd22) {
        this.out2nd22 = out2nd22;
    }

    /**
     * @return the out2nd23
     */
    public Date getOut2nd23() {
        return out2nd23;
    }

    /**
     * @param out2nd23 the out2nd23 to set
     */
    public void setOut2nd23(Date out2nd23) {
        this.out2nd23 = out2nd23;
    }

    /**
     * @return the out2nd24
     */
    public Date getOut2nd24() {
        return out2nd24;
    }

    /**
     * @param out2nd24 the out2nd24 to set
     */
    public void setOut2nd24(Date out2nd24) {
        this.out2nd24 = out2nd24;
    }

    /**
     * @return the out2nd25
     */
    public Date getOut2nd25() {
        return out2nd25;
    }

    /**
     * @param out2nd25 the out2nd25 to set
     */
    public void setOut2nd25(Date out2nd25) {
        this.out2nd25 = out2nd25;
    }

    /**
     * @return the out2nd26
     */
    public Date getOut2nd26() {
        return out2nd26;
    }

    /**
     * @param out2nd26 the out2nd26 to set
     */
    public void setOut2nd26(Date out2nd26) {
        this.out2nd26 = out2nd26;
    }

    /**
     * @return the out2nd27
     */
    public Date getOut2nd27() {
        return out2nd27;
    }

    /**
     * @param out2nd27 the out2nd27 to set
     */
    public void setOut2nd27(Date out2nd27) {
        this.out2nd27 = out2nd27;
    }

    /**
     * @return the out2nd28
     */
    public Date getOut2nd28() {
        return out2nd28;
    }

    /**
     * @param out2nd28 the out2nd28 to set
     */
    public void setOut2nd28(Date out2nd28) {
        this.out2nd28 = out2nd28;
    }

    /**
     * @return the out2nd29
     */
    public Date getOut2nd29() {
        return out2nd29;
    }

    /**
     * @param out2nd29 the out2nd29 to set
     */
    public void setOut2nd29(Date out2nd29) {
        this.out2nd29 = out2nd29;
    }

    /**
     * @return the out2nd30
     */
    public Date getOut2nd30() {
        return out2nd30;
    }

    /**
     * @param out2nd30 the out2nd30 to set
     */
    public void setOut2nd30(Date out2nd30) {
        this.out2nd30 = out2nd30;
    }

    /**
     * @return the out2nd31
     */
    public Date getOut2nd31() {
        return out2nd31;
    }

    /**
     * @param out2nd31 the out2nd31 to set
     */
    public void setOut2nd31(Date out2nd31) {
        this.out2nd31 = out2nd31;
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

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the employeeNUmber
     */
    public String getEmployeeNUmber() {
        return employeeNUmber;
    }

    /**
     * @param employeeNUmber the employeeNUmber to set
     */
    public void setEmployeeNUmber(String employeeNUmber) {
        this.employeeNUmber = employeeNUmber;
    }
    
    
    
}
