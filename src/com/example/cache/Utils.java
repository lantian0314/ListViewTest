package com.example.cache;

public class Utils {
	
	/**
	 * �ж��ַ��������Ƿ����
	 * @param string �ַ���
	 * @return
	 */
	public static boolean isDue(String string){
		return isDue(string.getBytes());
	}

	/**
	 * �ж��ַ��������Ƿ����
	 * @param bytes �ֽ�
	 * @return
	 */
	public static boolean isDue(byte[] bytes) {
		
		return false;
	} 
}
