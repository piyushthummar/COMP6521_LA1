/**
 * 
 */
package com.tpmms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author PIYUSH
 *
 */
public final class PhaseTwo {

	public static int subFileCount = PhaseOne.current_subFile;
	public ArrayList<BufferedReader> listOfFilePointers = new ArrayList<>();
	public static ArrayList<String> bufferToMerge =  new ArrayList<String>();
	private Scanner scanner;
	static int passes = 0;
	private static String folderPath = "";
	public static int blockCount = 0;
	
	/**
	 * 
	 */
	public PhaseTwo() {
		
	}
	
	public void startPhaseTwo() {
		System.out.println("Starting phase-II...");
		Runtime.getRuntime().gc();
		this.readSubFiles();
	}
	public void readSubFiles() {	
		FileReader fr;
		for(int index=0; index < subFileCount; index++) {
			try {
				fr = new FileReader(new File("./IO_Files/PhaseOneResult/" + (index+1) + ".txt"));
				listOfFilePointers.add(new BufferedReader(fr));
				bufferToMerge.add(listOfFilePointers.get(index).readLine());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		merge();
		System.out.println("Merge Over");
	}
	public void merge() {
		long recordCounter = 0;
		int minIndex = 0;
		long minTuple;
//		int minTuple, minIndex = 0;
		boolean bufferOver = false;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("./IO_Files/MergeResult/merge.txt");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//loop continues until buffer is out of elements
		while(!bufferOver) {
			minTuple = Long.parseLong(bufferToMerge.get(0).substring(0,11));
//			minTuple = Integer.parseInt(bufferToMerge.get(0).substring(0,7));
			minIndex = 0;
			for(int index=1; index<bufferToMerge.size(); index++) {
				if(Long.parseLong(bufferToMerge.get(index).substring(0, 11)) < minTuple){
					minTuple = Long.parseLong(bufferToMerge.get(index).substring(0, 11));
					minIndex = index;	
				}
//				if(Integer.parseInt(bufferToMerge.get(index).substring(0, 7)) < minTuple){
//					minTuple = Integer.parseInt(bufferToMerge.get(index).substring(0, 7));
//					minIndex = index;	
//				}
			}
			
			try {
				bw.write(bufferToMerge.get(minIndex));
				bw.newLine();
				recordCounter++;
				
				String newTuple = listOfFilePointers.get(minIndex).readLine();
				if(newTuple == null) {
					listOfFilePointers.remove(minIndex);
					bufferToMerge.remove(minIndex);
				} else {
					bufferToMerge.set(minIndex, newTuple);
				}
				if(bufferToMerge.size() == 0) {
					bufferOver = true;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		try {
//			System.out.println(recordCounter + " records merged...");
			bw.close();
			fw.close();
			Runtime.getRuntime().gc();
			removeDuplicates();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeDuplicates() {
		
		File file = new File("./IO_Files/finalOutput.txt");
		FileReader fr = null;
		try {
			fr = new FileReader("./IO_Files/MergeResult/merge.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		Scanner sc = new Scanner(br);
		
		HashSet<String> tuplesWithoutDuplication = new HashSet<>();
		long availableMemory = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() +
				Runtime.getRuntime().freeMemory();
		int counter = 0;
		double maxTuples = Math.floor(availableMemory/100);
		
		while(sc.hasNextLine()) {
			for(int index=0; index<maxTuples; index++) {
				String line = sc.nextLine();
				if(!tuplesWithoutDuplication.contains(line.substring(0, 7))) {
					tuplesWithoutDuplication.add(line);
					counter++;
				}
				else {
					continue;
				}
//				System.out.println("tuple-count:" + index + ", tuple-size : " + line.getBytes().length + ", available-memory : "+ Runtime.getRuntime().freeMemory() + ", total-tuple:" + count);
				if(!sc.hasNextLine() || Runtime.getRuntime().freeMemory() < (Runtime.getRuntime().maxMemory() * 0.4)) {
					break;
				}
			}
			
			writeBlock(file, new ArrayList<>(tuplesWithoutDuplication));
			tuplesWithoutDuplication.clear();
			System.gc();
		}	
//		
//		ArrayList<String> outputTuples = new ArrayList<>(tuplesWithoutDuplication);
//		Collections.sort(outputTuples, Collections.reverseOrder());
//		ArrayList<String> finalTuples = new ArrayList<>();
//		String checkTuple = null;
//		for(String tuple : outputTuples) {
//			if(checkTuple == null) {
//				checkTuple = tuple;
//				continue;
//			} else {
//				if(checkTuple.substring(0, 7).equals(tuple.substring(0, 7))) {
//					continue;
//				} else {
//					finalTuples.add(tuple);
//					checkTuple = tuple;
//					
//				}
//			}
//		}
		sc.close();
		try {
			br.close();
			fr.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Runtime.getRuntime().gc();
//		System.out.println("Records in ouput is " + finalTuples.size());
//		Collections.sort(finalTuples);
//		try {
////			File file = new File("./IO_Files/finalOutput.txt");
//			FileWriter fw = new FileWriter(file);
//			int tupleCount = 0;
//			for(int index=finalTuples.size()-1; index>=0; index--) { //String tuple : finalTuples
////				fw.append(tuple).append("\n");
//				fw.append(finalTuples.get(index)).append("\n");
//				tupleCount++;
////				fw.flush();
//				if(tupleCount % 1000 == 0) {
//					fw.flush();
//				}
//			}
//			System.out.println("Duplicate records are removed...");
//			System.out.println("New output is generated at ./IO_Files/finalOutput.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.out.println("Duplicate records are removed...");
		System.out.println("New output is generated at ./IO_Files/finalOutput.txt");
	}
	
	public void writeBlock(File file, ArrayList<String> tuplesWithoutDuplication){
		ArrayList<String> outputTuples = new ArrayList<>(tuplesWithoutDuplication);
		Collections.sort(outputTuples, Collections.reverseOrder());
		ArrayList<String> finalTuples = new ArrayList<>();
		String checkTuple = null;
		
		for(String tuple : outputTuples) {
			if(checkTuple == null) {
				checkTuple = tuple;
				continue;
			} else {
				if(checkTuple.substring(0, 7).equals(tuple.substring(0, 7))) {
					continue;
				} else {
					finalTuples.add(tuple);
					checkTuple = tuple;
					
				}
			}
		}
		
		try {
//			File file = new File("./IO_Files/finalOutput.txt");
			FileWriter fw = new FileWriter(file, true);
			int tupleCount = 0;
			for(int index=finalTuples.size()-1; index>=0; index--) { //String tuple : finalTuples
//				fw.append(tuple).append("\n");
				fw.append(finalTuples.get(index)).append("\n");
				tupleCount++;
				fw.flush();
//				if(tupleCount % 1000 == 0) {
//					fw.flush();
//				}
			}
			blockCount++;
			System.out.println("block " + blockCount + " written with " + finalTuples.size() + " tuples");
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
