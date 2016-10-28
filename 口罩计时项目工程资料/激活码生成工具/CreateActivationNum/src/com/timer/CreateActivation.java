package com.timer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CreateActivation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count =0;
		for(int i=0;i<1000000;i++){
			String activationNum = generateWord();
			List<String> numList = getStringNumList(activationNum);
			int temp = 0;
			for(String str :numList){
				temp +=Integer.parseInt(str);
			}
			String sTemp = String.valueOf(temp);
			sTemp = sTemp.substring(sTemp.length()-1);
			if("6".equals(sTemp)){
				count++;
				writeLogFile("/Users/liyusheng/Timer/激活码库.txt",activationNum);
				System.out.println(count+":::"+activationNum);
			}
		}
	}
	
	private static String generateWord() {    
	    String[] beforeShuffle = new String[] { "1","2", "3", "4", "5", "6", "7",    
	            "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",    
	            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",    
	            "W", "X", "Y", "Z" ,"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",    
	            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",    
	            "w", "x", "y", "z"};    
	    List list = Arrays.asList(beforeShuffle);    
	    Collections.shuffle(list);    
	    StringBuilder sb = new StringBuilder();    
	    for (int i = 0; i < list.size(); i++) {    
	        sb.append(list.get(i));    
	    }    
	    String afterShuffle = sb.toString();    
	    String result = afterShuffle.substring(3, 9); 
	    if(getStringNum(result).length()==3){
	    	return result;
	    }else{
	    	return generateWord();   
	    }
	  
	     
	}  
	private static String getStringNum(String str) {
		str = str.trim();
		String str2 = "";
		if (null!=str && ""!=str) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
					str2 += str.charAt(i);
				}
			}

		}
		return str2;
	}
	private static List<String>  getStringNumList(String str) {
		str = str.trim();
		List<String> result = new ArrayList<String>();
		
		if (null!=str && ""!=str) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
					result.add(String.valueOf(str.charAt(i)));
				}
			}
		}
		return result;
	}
	static public boolean writeLogFile(String sfile, String msg) {
		if (null == sfile || null == msg || msg.length() == 0) {
			return false;
		}
		FileOutputStream fos = null;
		try {
			String spath = sfile.substring(0, sfile.lastIndexOf("/"));

			File parentdir = new File(spath);
			if (!parentdir.exists())
				parentdir.mkdirs();

			File file = new File(sfile);
			fos = new FileOutputStream(file, true);
			//SimpleDateFormat formatter = new SimpleDateFormat("[yy/MM/dd_HH:mm:ss] ");
			//String dateTime = formatter.format(new Date(System.currentTimeMillis()));
//			msg = dateTime + msg + "\n";
			msg =  msg + "\r\n";
			byte[] buffer = msg.getBytes();

			fos.write(buffer);
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

		return false;
	}
}
