package com.example.json;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.utils.Tools;

public class Json {

	public void main() {
		/**
		 * 简单JSON解析
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
		 * 两个JSON数据的解析
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
		 * 解析JSON数组
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
		 * 解析Object和Array
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
		 * 上面的例子，使用getType在碰到查找不到节点的时候，会抛出异常。 如果使用optType，找不到节点，则返回null或者默认值。
		 */
		
	}
}
