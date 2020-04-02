package com.tpmms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TPMMS_Main {

	public static int totalSubFiles=0;
	public static void main(String[] args) {
//		String folderPath = "D:\\Concordia University\\Courses Materials\\2 Winter 2020\\COMP 6521 - ADBMS\\Assignments\\Lab Assignments\\LA1\\COMP6521_LA1";
		PhaseOne phaseOne = new PhaseOne("./500000.txt", "./IO_Files/PhaseOneResult");//500000
		long startTime = System.currentTimeMillis();
		System.out.println("***************** Relation T1 *****************");
		long countOne = phaseOne.extractDetails();
		phaseOne = new PhaseOne("./1000000.txt", "./IO_Files/PhaseOneResult");//1000000
		System.out.println("***************** Relation T2 *****************");
		long countTwo = phaseOne.extractDetails();
		long phaseOneEndTime = System.currentTimeMillis();
		double phaseOneTotalTime = (double)(phaseOneEndTime-startTime)/1000;
		System.out.println("Phase-One-Time : " + phaseOneTotalTime + " seconds");
		System.out.println("Phase One I/O count : " + ((countOne + countTwo) / 40) * 2 + "Blocks");
		
		System.gc();
		PhaseTwo phaseTwo = new PhaseTwo();
		long phaseTwoStartTime = System.currentTimeMillis();
		phaseTwo.startPhaseTwo();
		long endTime = System.currentTimeMillis();
		
		double phaseTwoTotalTime = (double)(endTime-phaseTwoStartTime)/1000;
		System.out.println("Phase-Two-Time : " + phaseTwoTotalTime + " seconds");
		System.out.println("Phase One I/O count : " + ((countOne + countTwo) / 40) + "Blocks");
		double totalTime = (double)((endTime - phaseTwoStartTime) + (phaseOneEndTime - startTime))/1000;
		System.out.println("\nTotal time for TPMMS : " + totalTime + " seconds");
		System.out.println("\nTotal I/O count : " + ((countOne + countTwo) / 40) * 3 + "Blocks");
		System.out.println("Counting output tuples...");
		
		try {
			FileReader fr = new FileReader("./IO_Files/finalOutput.txt");
			Scanner sc = new Scanner(fr);
			int count = 0;
			while(sc.hasNextLine()) {
				sc.nextLine();
				count++;
			}
			System.out.println("Total tuples in Output File is " + count);
			System.out.println("Total blocks in Output File is " + count/40);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		System.out.println("TPMMS Done!!!");	
	}
}
