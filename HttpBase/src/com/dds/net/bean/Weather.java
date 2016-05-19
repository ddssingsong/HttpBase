package com.dds.net.bean;

import java.io.Serializable;

public class Weather implements Serializable {

	@Override
	public String toString() {
		return "Weather [city=" + city + ", pinyin=" + pinyin + ", citycode=" + citycode + ", date=" + date + ", time="
				+ time + ", postCode=" + postCode + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", altitude=" + altitude + ", weather=" + weather + ", temp=" + temp + ", l_tmp=" + l_tmp + ", h_tmp="
				+ h_tmp + ", WD=" + WD + ", WS=" + WS + ", sunrise=" + sunrise + ", sunset=" + sunset + "]";
	}

	private static final long serialVersionUID = 1L;
	/**
	 * city : 北京 pinyin : beijing citycode : 101010100 date : 15-02-11 time :
	 * 11:00 postCode : 100000 longitude : 116.391 latitude : 39.904 altitude :
	 * 33 weather : 晴 temp : 10 l_tmp : -4 h_tmp : 10 WD : 无持续风向 WS : 微风(<10m/h)
	 * sunrise : 07:12 sunset : 17:44
	 */
	private String city;
	private String pinyin;
	private String citycode;
	private String date;
	private String time;
	private String postCode;
	private double longitude;
	private double latitude;
	private String altitude;
	private String weather;
	private String temp;
	private String l_tmp;
	private String h_tmp;
	private String WD;
	private String WS;
	private String sunrise;
	private String sunset;

	public Weather() {
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setL_tmp(String l_tmp) {
		this.l_tmp = l_tmp;
	}

	public void setH_tmp(String h_tmp) {
		this.h_tmp = h_tmp;
	}

	public void setWD(String WD) {
		this.WD = WD;
	}

	public void setWS(String WS) {
		this.WS = WS;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getCity() {
		return city;
	}

	public String getPinyin() {
		return pinyin;
	}

	public String getCitycode() {
		return citycode;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getPostCode() {
		return postCode;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public String getWeather() {
		return weather;
	}

	public String getTemp() {
		return temp;
	}

	public String getL_tmp() {
		return l_tmp;
	}

	public String getH_tmp() {
		return h_tmp;
	}

	public String getWD() {
		return WD;
	}

	public String getWS() {
		return WS;
	}

	public String getSunrise() {
		return sunrise;
	}

	public String getSunset() {
		return sunset;
	}

}
