/**
 * 
 */
package com.tpmms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author PIYUSH
 *
 */
public class PhaseOne {

	private static String filePath, destinationFolder;
	ArrayList<String> subFilesPath;
	ArrayList<String> dataList;
	public int block_size = 0, current_subFile = 0;
	
	/**
	 * 
	 */
	public PhaseOne(String filePath, String destinationFolder) {
		this.filePath = filePath;
		this.destinationFolder = destinationFolder;
		subFilesPath = new ArrayList<>();
	}

	public void extractDetails() {
		System.out.println("extracting sublists...");
		FileReader fr = null;
		BufferedReader br = null;
		Scanner sc = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			sc = new Scanner(br); 
			
			long availableMemory = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() +
					Runtime.getRuntime().freeMemory();
			
			while(sc.hasNextLine()) {
				dataList = new ArrayList<>();
				while(true) {
					dataList.add(sc.nextLine());
					System.out.println("Available memory: " + availableMemory);
					if(availableMemory < sc.nextLine().length() || !(sc.hasNextLine())) {
						break;
					}
				}
				System.out.println("Datalist Size" + dataList.size());
				Collections.sort(dataList);
				createSubLists(dataList);
			}
			fr.close();
			br.close();
			sc.close();
				
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

	public boolean createSubLists(ArrayList<String> data) {
		PrintWriter pw;
		boolean writeSuccess = true;
		current_subFile++;
		String subfilePath = destinationFolder + "\\" + Integer.toString(current_subFile) + ".txt";
		subFilesPath.add(subfilePath);
		try {
			pw = new PrintWriter(new File(subfilePath));
			for(String tuple : data) {
				pw.println(tuple);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			current_subFile--;
			writeSuccess = false;
			e.printStackTrace();
		}
		
		return writeSuccess;
		
	}
}
