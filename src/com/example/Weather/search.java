package com.example.Weather;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class search {

	public Weather getWeather(String city) {
		Weather weather = null;
		JSONObject object = null;
		try {
			weather=new Weather();
			object=new JSONObject(readParse("http://apis.haoservice.com/weather?cityname="+city+"&key=abf0cd09d9cd479793938294a5f1ac79"));
			String reason=object.getString("reason");
			if (!reason.equals("³É¹¦")) {
				weather.setDressing_advice(reason);
			}else {
				JSONObject result=object.getJSONObject("result");
				JSONObject today=result.getJSONObject("today");
				
				weather.setCity(today.getString("city"));
				weather.setDate(today.getString("date_y"));
				weather.setWeather(today.getString("weather"));
				weather.setTemperature(today.getString("temperature"));
				weather.setWind(today.getString("wind"));
				weather.setDressing_advice(today.getString("dressing_advice"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return weather;
	}

	private String readParse(String path) {
		ByteArrayOutputStream baos = null;
		String result = null;
		try {
			baos = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int len = 0;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			while ((len = is.read(data)) > 0) {
				baos.write(data, 0, len);
			}
			is.close();
			return new String(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
