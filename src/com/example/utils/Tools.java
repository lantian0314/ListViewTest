package com.example.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.util.Log;

public class Tools {

	private static String TAG="json.sky";
	public static void printLog(String string){
		Log.d(TAG, string);
	}
	
	public static void e(Exception e){
		try {
			if (e!=null) {
				Log.e(TAG, getStackTrace(e));
			}
		} catch (Exception e2) {
			Log.e(TAG,getStackTrace(e2));
		}
	}

	private static String getStackTrace(Throwable mThrowable) {
		if (mThrowable!=null) {
			Writer mWriter=new StringWriter();
			PrintWriter printWriter=new PrintWriter(mWriter);
			mThrowable.printStackTrace(printWriter);
			return mWriter.toString();
		}
		return null;
	}
}
