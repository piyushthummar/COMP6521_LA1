package com.tpmms;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class TPMMS_Main {

	public static void main(String[] args) {
//		List<String> mainMemorySize = ManagementFactory.getRuntimeMXBean().getInputArguments();
//		if(mainMemorySize.get(0).contentEquals("-Xmx10M")) {
//			
//		} else if(mainMemorySize.get(0).contentEquals("-Xmx20M")) {
//			
//		}
		PhaseOne phaseOne = new PhaseOne("D:\\Concordia University\\Courses Materials\\2 Winter 2020\\COMP 6521 - ADBMS\\Assignments\\Lab Assignments\\LA1\\COMP6521_LA1\\IO_Files\\sample.txt", "D:\\Concordia University\\Courses Materials\\2 Winter 2020\\COMP 6521 - ADBMS\\Assignments\\Lab Assignments\\LA1\\COMP6521_LA1\\IO_Files\\PhaseOneResult");
		long startTime = System.currentTimeMillis();
		phaseOne.extractDetails();
		long phaseOneEndTime = System.currentTimeMillis();
		System.out.println("Phase-One-Time : " + (phaseOneEndTime-startTime)/1000 + " seconds");
	}

}
