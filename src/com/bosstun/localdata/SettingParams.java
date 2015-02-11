package com.bosstun.localdata;

public class SettingParams {
	private Boolean bSelfStart;
	private int waitTimeBeforeTest;
	private int itvalTimeOfTest;
	private int waitTimeToReboot;

	public void setBSelfStart(Boolean bSelfStart) {
		this.bSelfStart = bSelfStart;
	}

	public void setWaitTimeBeforeTest(int waitTimeBeforeTest) {
		this.waitTimeBeforeTest = waitTimeBeforeTest;
	}

	public void setItvalTimeOfTest(int itvalTimeOfTest) {
		this.itvalTimeOfTest = itvalTimeOfTest;
	}

	public void setWaitTimeToReboot(int waitTimeToReboot) {
		this.waitTimeToReboot = waitTimeToReboot;
	}

	public Boolean getBSelfStart() {
		return bSelfStart;
	}

	public int getWaitTimeBeforeTest() {
		return waitTimeBeforeTest;
	}

	public int getItvalTimeOfTest() {
		return itvalTimeOfTest;
	}
	
	public int getWaitTimeToReboot() {
		return waitTimeToReboot;
	}
	
	@Override
	public String toString(){
		return 	"bSelfStart:" + bSelfStart + ",waitTimeBeforeTest:"+ waitTimeBeforeTest
				+ ",itvalTimeOfTest:" + itvalTimeOfTest + ",waitTimeToReboot:" + waitTimeToReboot;
	}
}
