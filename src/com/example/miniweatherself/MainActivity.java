package com.example.miniweatherself;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.pku.edu.liguocai.app.MyApplication;
import com.pku.edu.liguocai.bean.TodayWeather;
import com.pku.edu.liguocai.util.NetUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.pku.edu.liguocai.adapter.GuideAdapter;


 public class MainActivity extends Activity implements OnClickListener {
	 
	private  TextView[] day0=new TextView[4],day1=new TextView[4],
			day2=new TextView[4],day3=new TextView[4],day4=new TextView[4],day5=new TextView[4];
	private  ImageView[] sixDayImage=new ImageView[6];	 	
	//private BaiduCityLocation baiduCity=new BaiduCityLocation(this);
	private ImageView mUpdateBtn,title_share;
	private ImageView mCitySelect,titleLocation;
	private TextView cityTv,timeTv,humidityTv,weekTv,pmDataTv,pmQualityTv,
	        temperatureTv,climateTv,windTv,city_name_Tv,currentTemTV;
	private ImageView weatherImg,pmImg;
	private ProgressBar progressBar;
	private static final int UPDATE_TODAY_WEATHER = 1;	
	private ViewPager viewPage;
	private List<View> views;
	private GuideAdapter guideAdapter;
	public static String quanJuCode="101010100";
	
	//高德定位
	//声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	//声明定位回调监听器
	public AMapLocationListener1 mLocationListener = new AMapLocationListener1();
	//声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption = null;
	
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }

    };
	
    @SuppressLint({ "HandlerLeak", "InflateParams" })
	void initView(){    	
    	viewPage=(ViewPager) findViewById(R.id.sixday);
    	views = new ArrayList<View>();
    	views.add(LayoutInflater.from(this).inflate(R.layout.threeday1, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.threeday2, null));
        guideAdapter=new GuideAdapter(this, views);		
		viewPage.setAdapter(guideAdapter);
		
	    day0[0]=(TextView) views.get(0).findViewById(R.id.day1);
	    day0[1]=(TextView) views.get(0).findViewById(R.id.temperature1);
	    day0[2]=(TextView) views.get(0).findViewById(R.id.climate1);
	    day0[3]=(TextView) views.get(0).findViewById(R.id.wind1);
	    sixDayImage[0]=(ImageView) views.get(0).findViewById(R.id.img1);	    
	    day1[0]=(TextView) views.get(0).findViewById(R.id.day2);
	    day1[1]=(TextView) views.get(0).findViewById(R.id.temperature2);
	    day1[2]=(TextView) views.get(0).findViewById(R.id.climate2);
	    day1[3]=(TextView) views.get(0).findViewById(R.id.wind2);
	    sixDayImage[1]=(ImageView) views.get(0).findViewById(R.id.img2);	    
	    day2[0]=(TextView) views.get(0).findViewById(R.id.day3);
	    day2[1]=(TextView) views.get(0).findViewById(R.id.temperature3);
	    day2[2]=(TextView) views.get(0).findViewById(R.id.climate3);
	    day2[3]=(TextView) views.get(0).findViewById(R.id.wind3);
	    sixDayImage[2]=(ImageView) views.get(0).findViewById(R.id.img3);	    
	    day3[0]=(TextView) views.get(1).findViewById(R.id.day4);
	    day3[1]=(TextView) views.get(1).findViewById(R.id.temperature4);
	    day3[2]=(TextView) views.get(1).findViewById(R.id.climate4);
	    day3[3]=(TextView) views.get(1).findViewById(R.id.wind4);
	    sixDayImage[3]=(ImageView) views.get(1).findViewById(R.id.img4);	    
	    day4[0]=(TextView) views.get(1).findViewById(R.id.day5);
	    day4[1]=(TextView) views.get(1).findViewById(R.id.temperature5);
	    day4[2]=(TextView) views.get(1).findViewById(R.id.climate5);
	    day4[3]=(TextView) views.get(1).findViewById(R.id.wind5);
	    sixDayImage[4]=(ImageView) views.get(1).findViewById(R.id.img5);	    
	    day5[0]=(TextView) views.get(1).findViewById(R.id.day6);
	    day5[1]=(TextView) views.get(1).findViewById(R.id.temperature6);
	    day5[2]=(TextView) views.get(1).findViewById(R.id.climate6);
	    day5[3]=(TextView) views.get(1).findViewById(R.id.wind6);
	    sixDayImage[5]=(ImageView) views.get(1).findViewById(R.id.img6);
	    
		
		title_share=(ImageView) findViewById(R.id.title_share);
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        currentTemTV=(TextView) findViewById(R.id.Current_Temperature);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        mCitySelect=(ImageView) findViewById(R.id.title_city_manager);
        titleLocation=(ImageView) findViewById(R.id.title_location);// mmApplication=MyApplication.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.loading);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
        currentTemTV.setText("N/A");
    }
	
    //更新六天天气
    void updateSixWeather(TextView[] days,int i,TodayWeather todayWeather) {
    	days[0].setText(todayWeather.sixWeather[i].getDate());
	    days[1].setText(todayWeather.sixWeather[i].getLow()+"~"+todayWeather.sixWeather[i].getHigh());
	    days[2].setText(todayWeather.sixWeather[i].getType());
	    days[3].setText(todayWeather.sixWeather[i].getFengxiang());
	    
	    if(days[2].getText().toString().contains("暴雪")){
	    	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_baoxue);
        }else if(days[2].getText().toString().contains("暴雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_baoyu);
        }else if(days[2].getText().toString().contains("大雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_dayu);
        }else if(days[2].getText().toString().contains("大暴雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        }else if(days[2].getText().toString().contains("大雪")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_daxue);
        }else if(days[2].getText().toString().contains("多云")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_duoyun);
        }else if(days[2].getText().toString().contains("雷阵雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        }else if(days[2].getText().toString().contains("雷阵雨冰雹")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        }else if(days[2].getText().toString().contains("晴")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_qing);
        }else if(days[2].getText().toString().contains("沙尘暴")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        }else if(days[2].getText().toString().contains("特大暴雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        }else if(days[2].getText().toString().contains("小雪")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        }else if(days[2].getText().toString().contains("小雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        }else if(days[2].getText().toString().contains("阴")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_yin);
        }else if(days[2].getText().toString().contains("雾")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_wu);
        }else if(days[2].getText().toString().contains("雨夹雪")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        }else if(days[2].getText().toString().contains("阵雪")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        }else if(days[2].getText().toString().contains("阵雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        }else if(days[2].getText().toString().contains("中雪")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        }else if(days[2].getText().toString().contains("中雨")){
        	sixDayImage[i].setImageResource(R.drawable.biz_plugin_weather_zhongyu);
        }
    }
    
    void updateTodayWeather(TodayWeather todayWeather){
       	
    	if(mUpdateBtn.getVisibility()==View.GONE&&progressBar.getVisibility()==View.VISIBLE) {
        	progressBar.setVisibility(View.GONE);
        	mUpdateBtn.setVisibility(View.VISIBLE);
        }
    	//六天的天气更新 
    	updateSixWeather(day0,0,todayWeather);
    	updateSixWeather(day1,1,todayWeather);
    	updateSixWeather(day2,2,todayWeather);
    	updateSixWeather(day3,3,todayWeather);
    	updateSixWeather(day4,4,todayWeather);
    	updateSixWeather(day5,5,todayWeather);
    	
    	day0[0].setText(todayWeather.sixWeather[0].getDate());
	    day0[1].setText(todayWeather.sixWeather[0].getLow()+"~"+todayWeather.sixWeather[0].getHigh());
	    day0[2].setText(todayWeather.sixWeather[0].getType());
	    day0[3].setText(todayWeather.sixWeather[0].getDate());
    	
        city_name_Tv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+ "发布");
        humidityTv.setText("湿度："+todayWeather.getShidu());
        currentTemTV.setText("温度："+todayWeather.getWendu()+"℃");
        pmDataTv.setText(todayWeather.getPm25());
        //pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getLow()+"~"+todayWeather.getHigh());
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力:"+todayWeather.getFengli());
        pmQualityTv.setText(todayWeather.getQuality());
        
        if(climateTv.getText().toString().contains("暴雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        }else if(climateTv.getText().toString().contains("暴雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        }else if(climateTv.getText().toString().contains("大雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_dayu);
        }else if(climateTv.getText().toString().contains("大暴雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        }else if(climateTv.getText().toString().contains("大雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
        }else if(climateTv.getText().toString().contains("多云")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        }else if(climateTv.getText().toString().contains("雷阵雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        }else if(climateTv.getText().toString().contains("雷阵雨冰雹")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        }else if(climateTv.getText().toString().contains("晴")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
        }else if(climateTv.getText().toString().contains("沙尘暴")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        }else if(climateTv.getText().toString().contains("特大暴雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        }else if(climateTv.getText().toString().contains("小雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        }else if(climateTv.getText().toString().contains("小雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        }else if(climateTv.getText().toString().contains("阴")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
        }else if(climateTv.getText().toString().contains("雾")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
        }else if(climateTv.getText().toString().contains("雨夹雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        }else if(climateTv.getText().toString().contains("阵雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        }else if(climateTv.getText().toString().contains("阵雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        }else if(climateTv.getText().toString().contains("中雪")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        }else if(climateTv.getText().toString().contains("中雨")){
        	weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
        }
        
        if(pmQualityTv.getText().toString().contains("优")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
        }else if(pmQualityTv.getText().toString().contains("良")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
        }else if(pmQualityTv.getText().toString().contains("轻度污染")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
        }else if(pmQualityTv.getText().toString().contains("中度污染")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
        }else if(pmQualityTv.getText().toString().contains("重度污染")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
        }else if(pmQualityTv.getText().toString().contains("严重污染")){
        	pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        }
        
        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
        
        
    	
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {    
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //高德定位
      //初始化定位
    	mLocationClient = new AMapLocationClient(getApplicationContext());
    	//设置定位回调监听
    	mLocationClient.setLocationListener(mLocationListener);
    	//初始化AMapLocationClientOption对象
    	mLocationOption = new AMapLocationClientOption();
    	//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
    	mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
    	//获取一次定位结果：
    	//该方法默认为false。
    	mLocationOption.setOnceLocation(true);
    	//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
    	mLocationOption.setInterval(1000);
    	//设置是否返回地址信息（默认返回地址信息）
    	mLocationOption.setNeedAddress(true);
    	//设置是否允许模拟位置,默认为true，允许模拟位置
    	mLocationOption.setMockEnable(true);
    	//单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
    	mLocationOption.setHttpTimeOut(20000);
    	//关闭缓存机制
    	mLocationOption.setLocationCacheEnable(false);
    	//给定位客户端对象设置定位参数
    	mLocationClient.setLocationOption(mLocationOption);
    	
        mUpdateBtn=(ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);
        initView();
        mCitySelect.setOnClickListener(this);
        titleLocation.setOnClickListener(this);   
        title_share.setOnClickListener(this);
        
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE) {
            Log.d("myWeather", "网络OK");
            Toast.makeText(MainActivity.this,"网络OK！", Toast.LENGTH_LONG).show();
            if(mLocationClient.isStarted()) {
				mLocationClient.stopLocation();
			}
			mLocationClient.startLocation();
        }else
        {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了！", Toast.LENGTH_LONG).show();
        }
       // baiduCity.mLocationClient.start();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    //解析xml
    private TodayWeather parseXML(String xmldata) throws IOException
                     , XmlPullParserException{
    	TodayWeather todayWeather=null;
        int fengxiangCount=0;
        int dateCount=0;
        int highCount =0;
        int lowCount=0;
        int typeCount=0;
        int fengliCount=0;
    	XmlPullParserFactory fac=XmlPullParserFactory.newInstance();
    	XmlPullParser xmlPullPerse=fac.newPullParser();
    	xmlPullPerse.setInput(new StringReader(xmldata));
    	int eventType=xmlPullPerse.getEventType();
    	Log.d("myWheather","parseXML");
    	while(eventType!=XmlPullParser.END_DOCUMENT){
    		switch(eventType){
    		case XmlPullParser.START_DOCUMENT:
    			break;
    		case XmlPullParser.START_TAG:
    			if(xmlPullPerse.getName().equals("resp")){
    				todayWeather=new TodayWeather();
    			}
    			if(todayWeather!=null){
    				if(xmlPullPerse.getName().equals("city")){
        				eventType=xmlPullPerse.next();
        				todayWeather.setCity(xmlPullPerse.getText());
        				Log.d("myWheather","city:    "+xmlPullPerse.getText());
        			}else if(xmlPullPerse.getName().equals("updatetime")){
        				eventType=xmlPullPerse.next();
        				todayWeather.setUpdatetime(xmlPullPerse.getText());
        				Log.d("myWeather","updatetime:    "+xmlPullPerse.getText());		
                    }else if (xmlPullPerse.getName().equals("shidu")) {
                        eventType = xmlPullPerse.next();
                        todayWeather.setShidu(xmlPullPerse.getText());
                        Log.d("myWeather", "shidu:    "+xmlPullPerse.getText());
                    } else if (xmlPullPerse.getName().equals("wendu")) {
                        eventType = xmlPullPerse.next();
                        todayWeather.setWendu(xmlPullPerse.getText());
                        Log.d("myWeather", "wendu:    "+xmlPullPerse.getText());
                    } else if (xmlPullPerse.getName().equals("pm25")) {
                        eventType = xmlPullPerse.next();
                        todayWeather.setPm25(xmlPullPerse.getText());
                        Log.d("myWeather", "pm25:    "+xmlPullPerse.getText());
                    } else if (xmlPullPerse.getName().equals("quality")) {
                        eventType = xmlPullPerse.next();
                        todayWeather.setQuality(xmlPullPerse.getText());
                        Log.d("myWeather", "quality:    "+xmlPullPerse.getText());
                    } else if (xmlPullPerse.getName().equals("fengli")) {
                        eventType = xmlPullPerse.next();
                        if(fengliCount==0) {
                        Log.d("myWeather", "fengli:    "+xmlPullPerse.getText());
                        todayWeather.setFengli(xmlPullPerse.getText());
                        fengliCount++;
                        }
                    }else if (xmlPullPerse.getName().equals("date_1")&&dateCount<6) {
                        eventType = xmlPullPerse.next();
                        Log.d("myWeather", dateCount+"date_1:    "+xmlPullPerse.getText());
                        todayWeather.sixWeather[dateCount].setDate(xmlPullPerse.getText());
                        dateCount++;
                    }else if (xmlPullPerse.getName().equals("high_1")&&highCount<6) {
                        eventType = xmlPullPerse.next();
                        Log.d("myWeather", "high_1:    "+xmlPullPerse.getText());
                        todayWeather.sixWeather[highCount].setHigh(xmlPullPerse.getText().split(" ")[1]);
                        highCount++;
                    }else if (xmlPullPerse.getName().equals("low_1")&&lowCount<6) {
                        eventType = xmlPullPerse.next();
                        Log.d("myWeather", "low_1:    "+xmlPullPerse.getText().split(" ")[1]);
                        todayWeather.sixWeather[lowCount].setLow(xmlPullPerse.getText().split(" ")[1]);
                        lowCount++;
                    }else if (xmlPullPerse.getName().equals("type_1")&&typeCount<6) {
                        eventType = xmlPullPerse.next();
                        Log.d("myWeather", "type_1:    "+xmlPullPerse.getText());
                        todayWeather.sixWeather[typeCount].setType(xmlPullPerse.getText());
                        typeCount++;
                    }else if (xmlPullPerse.getName().equals("fengxiang")&&fengxiangCount<6&&fengxiangCount==0) {
                        eventType = xmlPullPerse.next();
                        todayWeather.setFengxiang(xmlPullPerse.getText());
                        Log.d("myWeather", "fengxiang"+fengxiangCount+xmlPullPerse.getText());
                    }else if (xmlPullPerse.getName().equals("fx_1")&&fengxiangCount<6) {
                        eventType = xmlPullPerse.next();
                        Log.d("myWeather", "fx_1:    "+xmlPullPerse.getText());
                        todayWeather.sixWeather[fengxiangCount].setFengxiang(xmlPullPerse.getText());
                        fengxiangCount++;
                    }else if (xmlPullPerse.getName().equals("fengxiang")&&fengxiangCount<6&&fengxiangCount!=0) {
                        eventType = xmlPullPerse.next();
                        todayWeather.sixWeather[fengxiangCount].setFengxiang(xmlPullPerse.getText());
                        Log.d("myWeather", "fengxiang"+fengxiangCount+xmlPullPerse.getText());
                        fengxiangCount++;
                    }else if (xmlPullPerse.getName().equals("date")&&dateCount<6) {
                        eventType = xmlPullPerse.next();
                        if (dateCount==1) {
                        	 todayWeather.setDate(xmlPullPerse.getText());
                        }
                        Log.d("myWeather", "dateCount"+dateCount+xmlPullPerse.getText());
                        todayWeather.sixWeather[dateCount].setDate(xmlPullPerse.getText());
                        dateCount++;
                    }else if (xmlPullPerse.getName().equals("high")&&highCount<6) {
                        eventType = xmlPullPerse.next();
                        if(highCount==1) {
                        	todayWeather.setHigh(xmlPullPerse.getText().split(" ")[1]);
                        }
                        todayWeather.sixWeather[highCount].setHigh(xmlPullPerse.getText().split(" ")[1]);
                        Log.d("myWeather", "high:    "+ highCount+highCount+xmlPullPerse.getText());
                        highCount++;
                    }else if (xmlPullPerse.getName().equals("low")&&lowCount<6) {
                        eventType = xmlPullPerse.next();
                        if(lowCount==1) {
                        	todayWeather.setLow(xmlPullPerse.getText().split(" ")[1]);
                        }
                        Log.d("myWeather", "low:    "+lowCount+xmlPullPerse.getText());
                        todayWeather.sixWeather[lowCount].setLow(xmlPullPerse.getText().split(" ")[1]);
                        lowCount++;
                    }else if (xmlPullPerse.getName().equals("type")&&typeCount<6) {
                        eventType = xmlPullPerse.next();
                        if(typeCount==1) {
                        	todayWeather.setType(xmlPullPerse.getText());
                        	Log.d("myWeather", "todayWeather.setType(xmlPullPerse.getText());"+ typeCount+xmlPullPerse.getText());
                        }
                        Log.d("myWeather", "type:    "+ typeCount+xmlPullPerse.getText());
                        todayWeather.sixWeather[typeCount].setType(xmlPullPerse.getText());
                        typeCount++;
                    }else if (xmlPullPerse.getName().equals("night")||xmlPullPerse.getName().equals("night_1")) {
                    	for (int i=0;i<9;i++) {
                    		 eventType = xmlPullPerse.next();
                    	}
                    }
    				
    			}   			
                break;
    		case XmlPullParser.END_TAG:
    			break;
    		}
    		//进入下一个元素并触发相应事件
    		eventType=xmlPullPerse.next();
    	}
    	return todayWeather;
    	
    }
    
    //获取天气信息
    private void queryWeatherCode(String cityCode){
    	final String address="http://wthrcdn.etouch.cn/WeatherApi?citykey="+cityCode;
    	Log.d("myWeather",address);
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection con=null;
				TodayWeather todayWeather=null;
				try{
					URL url=new URL(address);
					con=(HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setConnectTimeout(8000);
					con.setReadTimeout(8000);
					InputStream in=con.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String str;
					while((str=reader.readLine())!=null){
						response.append(str);
						Log.d("myWeather",str);
					}
					String responseStr=response.toString();
					Log.d("myWeather",responseStr);
					todayWeather=parseXML(responseStr);
					if(todayWeather!=null){
						Log.d("myWeather",todayWeather.toString());
                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=todayWeather;
                        mHandler.sendMessage(msg);
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(con!=null){
						con.disconnect();
					}
				}				
			}
    		
    	}).start();
    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.title_update_btn:
			SharedPreferences sharedPrefences=getSharedPreferences("config",MODE_PRIVATE);
			String cityCode=sharedPrefences.getString("main_city_code", quanJuCode);
			Log.d("myWeather",cityCode);						
			
			if (NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE) {
            Log.d("myWeather", "网络OK");
            queryWeatherCode(cityCode);       
			}else 
			{
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了！", Toast.LENGTH_LONG).show();        
			}
			mUpdateBtn.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
        	
			break;
		case R.id.title_city_manager:
			Intent i=new Intent(this,SelectCity.class);
			//startActivity(i);
			startActivityForResult(i, 1);
			break;
		case R.id.title_location:
			Log.d("定位结束","yes");
			//baiduCity.mLocationClient.start();
			try{
				//if(mLocationClient.isStarted()) {
					//mLocationClient.stop();
				if(mLocationClient.isStarted()) {
					mLocationClient.stopLocation();
				}
				mLocationClient.startLocation();
				//mLocationClient.start();						
			}catch(Exception e){
				e.printStackTrace();
			}finally{						
			}		
			break;	
		case R.id.title_share:
			Intent intent = new Intent(Intent.ACTION_SEND);
		    intent.setType("image/*");
		    intent.putExtra(Intent.EXTRA_SUBJECT,"share");
		    intent.putExtra(Intent.EXTRA_TEXT,"我在使用鸳鸯天气，你也来试试吧。");
		    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    startActivity(Intent.createChooser(intent,getTitle()));
		    break;
		}
		
	}
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		if(requestCode==1&&resultCode==RESULT_OK){
			
			String newCityCode=data.getStringExtra("cityCode");
			Log.d("myWeather","选择的城市代码为"+newCityCode);
			
			if (NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE) {
	            Log.d("myWeather", "网络OK");
	            queryWeatherCode(newCityCode);
				}else 
				{
	            Log.d("myWeather", "网络挂了");
	            Toast.makeText(MainActivity.this,"网络挂了！", Toast.LENGTH_LONG).show();        
				}
		}
	}
	
	//高德定位
	//可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
	//以下为前者的举例：
	public class AMapLocationListener1 implements AMapLocationListener{

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO 自动生成的方法存根
			if (amapLocation != null) {
			    if (amapLocation.getErrorCode() == 0) {
			        //可在其中解析amapLocation获取相应内容。
			    	java.util.Iterator it = MyApplication.cityToNumber.entrySet().iterator();
			    	while(it.hasNext()){
			    		  Entry entry = (Entry)it.next();
			    	      if(entry.getKey().toString().contains(amapLocation.getCity())||amapLocation.getCity().contains(entry.getKey().toString()))
			    	      {
			    	    	  quanJuCode=entry.getValue().toString();
			    	    	  queryWeatherCode(quanJuCode);
			    	      }
			    	}
			 }else {
			    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
			    Log.e("AmapError","location Error, ErrCode:"
			        + amapLocation.getErrorCode() + ", errInfo:"
			        + amapLocation.getErrorInfo());
			    }
			}
			
		}
		
	}

}
