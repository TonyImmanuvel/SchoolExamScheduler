package yoogi.game.fileutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {
	
	
	public static String readFullStringText(String strFileName) {
		BufferedReader reader;
        String strFullDataA = "";	
		try {
			reader = new BufferedReader(new FileReader(strFileName));
			String line = reader.readLine();
				
			while (line != null) {
				System.out.println(line);
				// read next line
				strFullDataA+= line + "\n";
				line = reader.readLine();
				
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 	strFullDataA;
	}
	
	public static void writeFullStringText(String strFileName,String strFullText) {
		 FileWriter myWriter;
		try {
			myWriter = new FileWriter(strFileName);
			myWriter.write(strFullText);
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	      
	}
	
	public static void log(String strTag,String strMessage) {
		System.out.print(strTag + ","+strMessage);
	}
	
	
	public static byte[] readAllBytes(String strFileName) {
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(strFileName, "r");
			byte[] b = new byte[(int)f.length()];
			f.readFully(b);
			return b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
