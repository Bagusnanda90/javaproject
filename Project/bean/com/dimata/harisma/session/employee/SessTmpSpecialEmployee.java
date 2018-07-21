/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.employee;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.locker.Locker;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.LockerLocation;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.Race;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.entity.masterdata.ResignedReason;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.payroll.PayGeneral;
import java.util.Date;

/**
 *
 * @author Dimata 007
 */
public class SessTmpSpecialEmployee {
    
    private long employeeId;
    private String employeeNum;
    private String fullName;
    private String addressEmployee;
    private String phoneEmployee;
    private int postalCodeEmployee;
    private String IndentCardNr;
    private String phoneEmg;
    private String nameEmg;
    private int sexEmployee;
    private String birthPlaceEmployee;
    private Date birthDateEmployee;
    private Date commercingDateEmployee;
    private String bloodTypeEmployee;
    private String astekNumEMployee;
    private Date asktekDate;
    private String barcodeNumber;
    private String departement;
    private String position;
    private String section;
    private String empCategory;
    private String level;
    private String religion;
    private String maritalStatus;
    private int maritalNumberOfChildren;
    private String taxMarital;
    private int taxNumberChildren;
    private String raceName;
    private String companyName;
    private String division;
    private String resignedReason;
    private String lockerNumber;
    private String lockerLocation;
    private int umur;
    private String father;
    private String mother;
    private String parentsAddress;
    private String subSection;
    private String addressEmg;
    
    private String secondaryIdNo;
    private Date secondaryIdValid;
    private Date idCardValid;
    private Date resignedDate;
    private String resignedDesc;
    private String addressPermanent;
    private String handphone;
    private String currier;
    
    
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    
    private String provinsiPermanent;
    private String kabupatenPermanent;
    private String kecamatanPermanent;
    
    private String countryPermanent;
    private String country;
    private String location;
    private String grade;
    private String email;
    private Date endContractEmployee;
    //add priska
    private String idCardType;
    private String npwp;
    private String bpjsNo;
    private Date bpjsDate;
    private String shio;
    private String elemen;
    private String iq;
    private String eq;
    private Date probationEndDate;
    
    private Date StartDatePensiun;
    private String statusPensionProgram;
    private String presenceCheckParameter;
    private String medicalInfo;
    private String danaPendidikan;
    private String payrollGroup;
    private String waCompany;
    private String waDivision;
    private String waDepartement;
    private String waPosition;
    private String waSection;
    /* Add by Hendra Putu | 2015-12-15 */
    private String noRekening = "";
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
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        return employeeNum;
    }

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
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
     * @return the addressEmployee
     */
    public String getAddressEmployee() {
        return addressEmployee;
    }

    /**
     * @param addressEmployee the addressEmployee to set
     */
    public void setAddressEmployee(String addressEmployee) {
        this.addressEmployee = addressEmployee;
    }

    /**
     * @return the phoneEmployee
     */
    public String getPhoneEmployee() {
        return phoneEmployee;
    }

    /**
     * @param phoneEmployee the phoneEmployee to set
     */
    public void setPhoneEmployee(String phoneEmployee) {
        this.phoneEmployee = phoneEmployee;
    }

    /**
     * @return the postalCodeEmployee
     */
    public int getPostalCodeEmployee() {
        return postalCodeEmployee;
    }

    /**
     * @param postalCodeEmployee the postalCodeEmployee to set
     */
    public void setPostalCodeEmployee(int postalCodeEmployee) {
        this.postalCodeEmployee = postalCodeEmployee;
    }

    /**
     * @return the sexEmployee
     */
    public int getSexEmployee() {
        return sexEmployee;
    }

    /**
     * @param sexEmployee the sexEmployee to set
     */
    public void setSexEmployee(int sexEmployee) {
        this.sexEmployee = sexEmployee;
    }

    /**
     * @return the birthPlaceEmployee
     */
    public String getBirthPlaceEmployee() {
        return birthPlaceEmployee;
    }

    /**
     * @param birthPlaceEmployee the birthPlaceEmployee to set
     */
    public void setBirthPlaceEmployee(String birthPlaceEmployee) {
        this.birthPlaceEmployee = birthPlaceEmployee;
    }

    /**
     * @return the birthDateEmployee
     */
    public Date getBirthDateEmployee() {
        return birthDateEmployee;
    }

    /**
     * @param birthDateEmployee the birthDateEmployee to set
     */
    public void setBirthDateEmployee(Date birthDateEmployee) {
        this.birthDateEmployee = birthDateEmployee;
    }

    /**
     * @return the commercingDateEmployee
     */
    public Date getCommercingDateEmployee() {
        return commercingDateEmployee;
    }

    /**
     * @param commercingDateEmployee the commercingDateEmployee to set
     */
    public void setCommercingDateEmployee(Date commercingDateEmployee) {
        this.commercingDateEmployee = commercingDateEmployee;
    }

    /**
     * @return the bloodTypeEmployee
     */
    public String getBloodTypeEmployee() {
        return bloodTypeEmployee;
    }

    /**
     * @param bloodTypeEmployee the bloodTypeEmployee to set
     */
    public void setBloodTypeEmployee(String bloodTypeEmployee) {
        this.bloodTypeEmployee = bloodTypeEmployee;
    }

    /**
     * @return the astekNumEMployee
     */
    public String getAstekNumEMployee() {
        return astekNumEMployee;
    }

    /**
     * @param astekNumEMployee the astekNumEMployee to set
     */
    public void setAstekNumEMployee(String astekNumEMployee) {
        this.astekNumEMployee = astekNumEMployee;
    }

    /**
     * @return the asktekDate
     */
    public Date getAsktekDate() {
        return asktekDate;
    }

    /**
     * @param asktekDate the asktekDate to set
     */
    public void setAsktekDate(Date asktekDate) {
        this.asktekDate = asktekDate;
    }

    /**
     * @return the barcodeNumber
     */
    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    /**
     * @param barcodeNumber the barcodeNumber to set
     */
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @param departement the departement to set
     */
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return the maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the maritalNumberOfChildren
     */
    public int getMaritalNumberOfChildren() {
        return maritalNumberOfChildren;
    }

    /**
     * @param maritalNumberOfChildren the maritalNumberOfChildren to set
     */
    public void setMaritalNumberOfChildren(int maritalNumberOfChildren) {
        this.maritalNumberOfChildren = maritalNumberOfChildren;
    }

    /**
     * @return the taxMarital
     */
    public String getTaxMarital() {
        return taxMarital;
    }

    /**
     * @param taxMarital the taxMarital to set
     */
    public void setTaxMarital(String taxMarital) {
        this.taxMarital = taxMarital;
    }

    /**
     * @return the taxNumberChildren
     */
    public int getTaxNumberChildren() {
        return taxNumberChildren;
    }

    /**
     * @param taxNumberChildren the taxNumberChildren to set
     */
    public void setTaxNumberChildren(int taxNumberChildren) {
        this.taxNumberChildren = taxNumberChildren;
    }

    /**
     * @return the raceName
     */
    public String getRaceName() {
        return raceName;
    }

    /**
     * @param raceName the raceName to set
     */
    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the resignedReason
     */
    public String getResignedReason() {
        return resignedReason;
    }

    /**
     * @param resignedReason the resignedReason to set
     */
    public void setResignedReason(String resignedReason) {
        this.resignedReason = resignedReason;
    }

    /**
     * @return the lockerNumber
     */
    public String getLockerNumber() {
        return lockerNumber;
    }

    /**
     * @param lockerNumber the lockerNumber to set
     */
    public void setLockerNumber(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    /**
     * @return the lockerLocation
     */
    public String getLockerLocation() {
        return lockerLocation;
    }

    /**
     * @param lockerLocation the lockerLocation to set
     */
    public void setLockerLocation(String lockerLocation) {
        this.lockerLocation = lockerLocation;
    }

    /**
     * @return the IndentCardNr
     */
    public String getIndentCardNr() {
        return IndentCardNr;
    }

    /**
     * @param IndentCardNr the IndentCardNr to set
     */
    public void setIndentCardNr(String IndentCardNr) {
        this.IndentCardNr = IndentCardNr;
    }

    /**
     * @return the phoneEmg
     */
    public String getPhoneEmg() {
        return phoneEmg;
    }

    /**
     * @param phoneEmg the phoneEmg to set
     */
    public void setPhoneEmg(String phoneEmg) {
        this.phoneEmg = phoneEmg;
    }

    /**
     * @return the nameEmg
     */
    public String getNameEmg() {
        return nameEmg;
    }

    /**
     * @param nameEmg the nameEmg to set
     */
    public void setNameEmg(String nameEmg) {
        this.nameEmg = nameEmg;
    }

    /**
     * @return the umur
     */
    public int getUmur() {
        
         if(birthDateEmployee!=null){
            int yearBorn = birthDateEmployee.getYear()+1900;
            int monthBorn = birthDateEmployee.getMonth()+1;
            int dateBorn = birthDateEmployee.getDate();
            
            int yearToday = new Date().getYear()+1900;
            int monthToday = new Date().getMonth()+1;
            int dateToday = new Date().getDate();
            
            if(monthBorn >= monthToday){
                if(dateBorn > dateToday){
                    setUmur((yearToday - yearBorn) - 1);//dianggap belum pass dengan umur dia bulan ini
                }else{
                     setUmur(yearToday - yearBorn);
                }
                
            }else{
                setUmur(yearToday - yearBorn);
            }
        }
        return umur;
    }

    /**
     * @return the father
     */
    public String getFather() {
        return father;
    }

    /**
     * @param father the father to set
     */
    public void setFather(String father) {
        this.father = father;
    }

    /**
     * @return the mother
     */
    public String getMother() {
        return mother;
    }

    /**
     * @param mother the mother to set
     */
    public void setMother(String mother) {
        this.mother = mother;
    }

    /**
     * @return the parentsAddress
     */
    public String getParentsAddress() {
        return parentsAddress;
    }

    /**
     * @param parentsAddress the parentsAddress to set
     */
    public void setParentsAddress(String parentsAddress) {
        this.parentsAddress = parentsAddress;
    }

    /**
     * @return the resignedDate
     */
    public Date getResignedDate() {
        return resignedDate;
    }

    /**
     * @param resignedDate the resignedDate to set
     */
    public void setResignedDate(Date resignedDate) {
        this.resignedDate = resignedDate;
    }

    /**
     * @return the resignedDesc
     */
    public String getResignedDesc() {
        return resignedDesc;
    }

    /**
     * @param resignedDesc the resignedDesc to set
     */
    public void setResignedDesc(String resignedDesc) {
        this.resignedDesc = resignedDesc;
    }

    /**
     * @return the addressPermanent
     */
    public String getAddressPermanent() {
        return addressPermanent;
    }

    /**
     * @param addressPermanent the addressPermanent to set
     */
    public void setAddressPermanent(String addressPermanent) {
        this.addressPermanent = addressPermanent;
    }

    /**
     * @return the handphone
     */
    public String getHandphone() {
        return handphone;
    }

    /**
     * @param handphone the handphone to set
     */
    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    /**
     * @return the currier
     */
    public String getCurrier() {
        return currier;
    }

    /**
     * @param currier the currier to set
     */
    public void setCurrier(String currier) {
        this.currier = currier;
    }

    /**
     * @return the provinsi
     */
    public String getProvinsi() {
         if(provinsi==null || provinsi.length()==0){
            return "-";
        }
        return provinsi;
    }

    /**
     * @param provinsi the provinsi to set
     */
    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    /**
     * @return the kabupaten
     */
    public String getKabupaten() {
         if(kabupaten==null || kabupaten.length()==0){
            return "-";
        }
        return kabupaten;
    }

    /**
     * @param kabupaten the kabupaten to set
     */
    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    /**
     * @return the kecamatan
     */
    public String getKecamatan() {
         if(kecamatan==null || kecamatan.length()==0){
            return "-";
        }
        return kecamatan;
    }

    /**
     * @param kecamatan the kecamatan to set
     */
    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    /**
     * @return the provinsiPermanent
     */
    public String getProvinsiPermanent() {
         if(provinsiPermanent==null || provinsiPermanent.length()==0){
            return "-";
        }
        return provinsiPermanent;
    }

    /**
     * @param provinsiPermanent the provinsiPermanent to set
     */
    public void setProvinsiPermanent(String provinsiPermanent) {
        this.provinsiPermanent = provinsiPermanent;
    }

    /**
     * @return the kabupatenPermanent
     */
    public String getKabupatenPermanent() {
         if(kabupatenPermanent==null || kabupatenPermanent.length()==0){
            return "-";
        }
        return kabupatenPermanent;
    }

    /**
     * @param kabupatenPermanent the kabupatenPermanent to set
     */
    public void setKabupatenPermanent(String kabupatenPermanent) {
        this.kabupatenPermanent = kabupatenPermanent;
    }

    /**
     * @return the kecamatanPermanent
     */
    public String getKecamatanPermanent() {
        if(kecamatanPermanent==null || kecamatanPermanent.length()==0){
            return "-";
        }
        return kecamatanPermanent;
    }

    /**
     * @param kecamatanPermanent the kecamatanPermanent to set
     */
    public void setKecamatanPermanent(String kecamatanPermanent) {
        this.kecamatanPermanent = kecamatanPermanent;
    }

    /**
     * @return the countryPermanent
     */
    public String getCountryPermanent() {
        if(countryPermanent==null || countryPermanent.length()==0){
            return "-";
        }
        return countryPermanent;
    }

    /**
     * @param countryPermanent the countryPermanent to set
     */
    public void setCountryPermanent(String countryPermanent) {
        this.countryPermanent = countryPermanent;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        if(country==null || country.length()==0){
            return "-";
        }
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return the endContractEmployee
     */
    public Date getEndContractEmployee() {
        return endContractEmployee;
    }

    /**
     * @param endContractEmployee the endContractEmployee to set
     */
    public void setEndContractEmployee(Date endContractEmployee) {
        this.endContractEmployee = endContractEmployee;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the idCardType
     */
    public String getIdCardType() {
        return idCardType;
    }

    /**
     * @param idCardType the idCardType to set
     */
    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    /**
     * @return the npwp
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * @param npwp the npwp to set
     */
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    /**
     * @return the bpjsNo
     */
    public String getBpjsNo() {
        return bpjsNo;
    }

    /**
     * @param bpjsNo the bpjsNo to set
     */
    public void setBpjsNo(String bpjsNo) {
        this.bpjsNo = bpjsNo;
    }

    /**
     * @return the shio
     */
    public String getShio() {
        return shio;
    }

    /**
     * @param shio the shio to set
     */
    public void setShio(String shio) {
        this.shio = shio;
    }

    /**
     * @return the elemen
     */
    public String getElemen() {
        return elemen;
    }

    /**
     * @param elemen the elemen to set
     */
    public void setElemen(String elemen) {
        this.elemen = elemen;
    }

    /**
     * @return the iq
     */
    public String getIq() {
        return iq;
    }

    /**
     * @param iq the iq to set
     */
    public void setIq(String iq) {
        this.iq = iq;
    }

    /**
     * @return the eq
     */
    public String getEq() {
        return eq;
    }

    /**
     * @param eq the eq to set
     */
    public void setEq(String eq) {
        this.eq = eq;
    }

  
    /**
     * @return the statusPensionProgram
     */
    public String getStatusPensionProgram() {
        return statusPensionProgram;
    }

    /**
     * @param statusPensionProgram the statusPensionProgram to set
     */
    public void setStatusPensionProgram(String statusPensionProgram) {
        this.statusPensionProgram = statusPensionProgram;
    }

    /**
     * @return the presenceCheckParameter
     */
    public String getPresenceCheckParameter() {
        return presenceCheckParameter;
    }

    /**
     * @param presenceCheckParameter the presenceCheckParameter to set
     */
    public void setPresenceCheckParameter(String presenceCheckParameter) {
        this.presenceCheckParameter = presenceCheckParameter;
    }

    /**
     * @return the medicalInfo
     */
    public String getMedicalInfo() {
        return medicalInfo;
    }

    /**
     * @param medicalInfo the medicalInfo to set
     */
    public void setMedicalInfo(String medicalInfo) {
        this.medicalInfo = medicalInfo;
    }

    /**
     * @return the danaPendidikan
     */
    public String getDanaPendidikan() {
        return danaPendidikan;
    }

    /**
     * @param danaPendidikan the danaPendidikan to set
     */
    public void setDanaPendidikan(String danaPendidikan) {
        this.danaPendidikan = danaPendidikan;
    }

    /**
     * @return the payrollGroup
     */
    public String getPayrollGroup() {
        return payrollGroup;
    }

    /**
     * @param payrollGroup the payrollGroup to set
     */
    public void setPayrollGroup(String payrollGroup) {
        this.payrollGroup = payrollGroup;
    }

    /**
     * @param umur the umur to set
     */
    public void setUmur(int umur) {
        this.umur = umur;
    }

    /**
     * @return the bpjsDate
     */
    public Date getBpjsDate() {
        return bpjsDate;
    }

    /**
     * @param bpjsDate the bpjsDate to set
     */
    public void setBpjsDate(Date bpjsDate) {
        this.bpjsDate = bpjsDate;
    }

    /**
     * @return the probationEndDate
     */
    public Date getProbationEndDate() {
        return probationEndDate;
    }

    /**
     * @param probationEndDate the probationEndDate to set
     */
    public void setProbationEndDate(Date probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    /**
     * @return the StartDatePensiun
     */
    public Date getStartDatePensiun() {
        return StartDatePensiun;
    }

    /**
     * @param StartDatePensiun the StartDatePensiun to set
     */
    public void setStartDatePensiun(Date StartDatePensiun) {
        this.StartDatePensiun = StartDatePensiun;
    }

    /**
     * @return the waCompany
     */
    public String getWaCompany() {
        return waCompany;
    }

    /**
     * @param waCompany the waCompany to set
     */
    public void setWaCompany(String waCompany) {
        this.waCompany = waCompany;
    }

    /**
     * @return the waDivision
     */
    public String getWaDivision() {
        return waDivision;
    }

    /**
     * @param waDivision the waDivision to set
     */
    public void setWaDivision(String waDivision) {
        this.waDivision = waDivision;
    }

    /**
     * @return the waDepartement
     */
    public String getWaDepartement() {
        return waDepartement;
    }

    /**
     * @param waDepartement the waDepartement to set
     */
    public void setWaDepartement(String waDepartement) {
        this.waDepartement = waDepartement;
    }

    /**
     * @return the waPosition
     */
    public String getWaPosition() {
        return waPosition;
    }

    /**
     * @param waPosition the waPosition to set
     */
    public void setWaPosition(String waPosition) {
        this.waPosition = waPosition;
    }

    /**
     * @return the waSection
     */
    public String getWaSection() {
        return waSection;
    }

    /**
     * @param waSection the waSection to set
     */
    public void setWaSection(String waSection) {
        this.waSection = waSection;
    }

    /**
     * @return the noRekening
     */
    public String getNoRekening() {
        return noRekening;
    }

    /**
     * @param noRekening the noRekening to set
     */
    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    /**
     * @return the subSection
     */
    public String getSubSection() {
        return subSection;
    }

    /**
     * @param subSection the subSection to set
     */
    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    /**
     * @return the addressEmg
     */
    public String getAddressEmg() {
        return addressEmg;
    }

    /**
     * @param addressEmg the addressEmg to set
     */
    public void setAddressEmg(String addressEmg) {
        this.addressEmg = addressEmg;
    }

    /**
     * @return the secondaryIdNo
     */
    public String getSecondaryIdNo() {
        return secondaryIdNo;
    }

    /**
     * @param secondaryIdNo the secondaryIdNo to set
     */
    public void setSecondaryIdNo(String secondaryIdNo) {
        this.secondaryIdNo = secondaryIdNo;
    }

    /**
     * @return the secondaryIdValid
     */
    public Date getSecondaryIdValid() {
        return secondaryIdValid;
    }

    /**
     * @param secondaryIdValid the secondaryIdValid to set
     */
    public void setSecondaryIdValid(Date secondaryIdValid) {
        this.secondaryIdValid = secondaryIdValid;
    }

    /**
     * @return the idCardValid
     */
    public Date getIdCardValid() {
        return idCardValid;
    }

    /**
     * @param idCardValid the idCardValid to set
     */
    public void setIdCardValid(Date idCardValid) {
        this.idCardValid = idCardValid;
    }
                
    
    
}
