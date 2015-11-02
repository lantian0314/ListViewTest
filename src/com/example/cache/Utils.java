package com.example.cache;

public class Utils {
	
	/**
	 * ÅÐ¶Ï×Ö·û´®»º´æÊÇ·ñ¹ýÆÚ
	 * @param string ×Ö·û´®
	 * @return
	 */
	public static boolean isDue(String string){
		return isDue(string.getBytes());
	}

	/**
	 * ÅÐ¶Ï×Ö·û´®»º´æÊÇ·ñ¹ýÆÚ
	 * @param bytes ×Ö½Ú
	 * @return
	 */
	public static boolean isDue(byte[] bytes) {
		
		return false;
	} 
}
