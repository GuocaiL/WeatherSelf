/**
 * 
 */
/**
 * @author ¹ú²Å
 *
 */
package com.pku.edu.liguocai.bean;

public class TodayWeather{
	
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;
    public Weather [] sixWeather=new Weather[] {new Weather(),new Weather(),new Weather(),new Weather(),new Weather(),new Weather()};
    
    public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getWendu() {
		return wendu;
	}
	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	public String getShidu() {
		return shidu;
	}
	public void setShidu(String shidu) {
		this.shidu = shidu;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getFengxiang() {
		return fengxiang;
	}
	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}
	public String getFengli() {
		return fengli;
	}
	public void setFengli(String fengli) {
		this.fengli = fengli;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "TodayWeather [city=" + city + ", updatetime=" + updatetime
				+ ", wendu=" + wendu + ", shidu=" + shidu + ", pm25=" + pm25
				+ ", quality=" + quality + ", fengxiang=" + fengxiang
				+ ", fengli=" + fengli + ", date=" + date + ", high=" + high
				+ ", low=" + low + ", type=" + type + ", toString()="
				+ super.toString() + "]"+sixWeather[0].getDate()+sixWeather[0].getFengxiang()+sixWeather[0].getHigh()+sixWeather[0].getLow()+sixWeather[0].getType()+
				sixWeather[1].getDate()+sixWeather[1].getFengxiang()+sixWeather[1].getHigh()+sixWeather[1].getLow()+sixWeather[1].getType()
				+sixWeather[2].getDate()+sixWeather[2].getFengxiang()+sixWeather[2].getHigh()+sixWeather[2].getLow()+sixWeather[2].getType()
				+sixWeather[3].getDate()+sixWeather[3].getFengxiang()+sixWeather[3].getHigh()+sixWeather[3].getLow()+sixWeather[3].getType()
				+sixWeather[4].getDate()+sixWeather[4].getFengxiang()+sixWeather[4].getHigh()+sixWeather[4].getLow()+sixWeather[4].getType()
				+sixWeather[5].getDate()+sixWeather[5].getFengxiang()+sixWeather[5].getHigh()+sixWeather[5].getLow()+sixWeather[5].getType()
				;
	}
	
}