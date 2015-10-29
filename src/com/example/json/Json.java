package com.example.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.utils.Tools;

public class Json {

	public void main() {
		/**
		 * ��JSON����
		 */
		String json = "{'url':'http://www.cnblogs.com/qianxudetianxia'}";

		try {
			JSONObject jsonObject = new JSONObject(json);
			String url = jsonObject.getString("url");
			String url1=jsonObject.optString("url1");
			Tools.printLog(url);
			Tools.printLog("URL1:"+url1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		/**
		 * ����JSON���ݵĽ���
		 */
		String json2 = "{'name':'android','version':'1.1.0'}";
		try {
			JSONObject jsonObject = new JSONObject(json2);
			String name = jsonObject.getString("name");
			String version = jsonObject.getString("version");
			Tools.printLog("name:" + name + "........." + "version:" + version);
		} catch (Exception e) {
			// TODO: handle exception
		}

		/**
		 * ����JSON����
		 */
		String array = "{'number':[1,2,3]}";
		try {
			JSONObject object = new JSONObject(array);
			JSONArray jsonArray = object.getJSONArray("number");
			for (int i = 0; i < jsonArray.length(); i++) {
				Tools.printLog("data:" + jsonArray.getInt(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		/**
		 * ����Object��Array
		 */
		String string = "{'mobile':[{'name1':'apple'},{'name2':'android'}]}";
		try {
			JSONObject object2 = new JSONObject(string);
			JSONArray jsonArray = object2.getJSONArray("mobile");
			for (int i = 0; i < jsonArray.length(); i++) {
				String name1 = jsonArray.getJSONObject(i).getString("name1");
				Tools.printLog("name1" + name1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		/**
		 * ��������ӣ�ʹ��getType���������Ҳ����ڵ��ʱ�򣬻��׳��쳣�� ���ʹ��optType���Ҳ����ڵ㣬�򷵻�null����Ĭ��ֵ��
		 */
		
	}
}
