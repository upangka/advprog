///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.Arrays;

import model.Sample;

/**
byte age;
	byte jobAdmin;
	byte jobBlueCollar;
	byte jobEntrepreneur;
	byte jobHousemaid;
	byte jobManagement;
	byte jobRetired;
	byte jobSelfEmployed;
	byte jobServices;
	byte jobStudent;
	byte jobTechnician;
	byte jobUnemployed;
	byte jobUnknown;
	byte maritalDivorced;
	byte maritalMarried;
	byte maritalSingle;
	byte maritalUnknown;
	byte educationBasic4y;
	byte educationBasic6y;
	byte educationBasic9y;
	byte educationHighSchool;
	byte educationIlliterate;
	byte educationProfessionalCourse;
	byte educationUniversityDegree;
	byte educationUnknown;
	byte creditNo;
	byte creditYes;
	byte creditUnknown;
	byte housingNo;
	byte housingYes;
	byte housingUnknown;
	byte loanNo;
	byte loanYes;
	byte loanUnknown;
	byte contactCellular;
	byte contactTelephone;
	byte contactJan;
	byte contactFeb;
	byte contactMar;
	byte contactApr;
	byte contactMay;
	byte contactJun;
	byte contactJul;
	byte contactAug;
	byte contactSep;
	byte contactOct;
	byte contactNov;
	byte contactDec;
	byte contactMon;
	byte contactTue;
	byte contactWed;
	byte contactThu;
	byte contactFri;
	int duration;
	byte campaign;
	int pdays;
	byte pdaysNever;
	byte previous;
	byte poutcomeFailure;
	byte poutcomeNonexistent;
	byte poutcomeSuccess;
	float empVarRate;
	float consPriceIdx;
	float consConfIdx;
	float euribor3m;
	float nrEmployed;
	String target;
 */
public class BankMarketing implements Sample{
    private static final int COLUMNS_NUM = 67;
    private final double[] columns = new double[COLUMNS_NUM - 1];
    private final String tag;

    public BankMarketing(String[] datas){
        if(datas.length != COLUMNS_NUM){
            throw new IllegalArgumentException("Wrong data length: "+ datas.length);
        }

        for (int i = 0; i < COLUMNS_NUM - 1; i++) {
            columns[i] = Double.parseDouble(datas[i]);
        }


        this.tag = datas[COLUMNS_NUM - 1];
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public double[] getExample() {
        return Arrays.copyOf(columns, columns.length);
    }
}