package com.pku.edu.liguocai.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pku.edu.liguocai.bean.City;
import com.pku.edu.liguocai.db.CityDB;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class MyApplication extends Application {
	private static final String TAG="MyAPP";
	
	private static MyApplication mApplication;
	private static CityDB mCityDB;
	private static List<City> mCityList;
	public static Map<String,String> cityToNumber;
	public static String citi ="北京";
	public static String getCity() {
		return citi;
	}

	public static void setCity(String citi) {
		MyApplication.citi = citi;
	}

	public static String getCitiCode() {
		return CitiCode;
	}

	public static void setCitiCode(String citiCode) {
		CitiCode = citiCode;
	}
	public static String CitiCode="101010100";
	
	@Override
	public void onCreate(){
		super.onCreate();
		Log.d(TAG,"MyApplication->Oncreate");		
		mApplication=this;
		mCityDB=openCityDB();
		initCity();
	}
	
	public static MyApplication getInstance(){
		return mApplication;
	}
	
//加载city.db，如果没有则在程序的缓存中创建，返回citydb对象
	private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        if (!db.exists()) {

            String pathfolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;            File dirFirstFolder = new File(pathfolder);
                    if(!dirFirstFolder.exists()){
                        dirFirstFolder.mkdirs();
                        Log.i("MyApp","mkdirs");
                    }
                    Log.i("MyApp","db is not exists");
                    try {
                        InputStream is = getAssets().open("city.db");
                        FileOutputStream fos = new FileOutputStream(db);
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
          
        }
        return new CityDB(this, path);  
	}
	
	//启动线程将城市列表加载到mCityList
    private void initCity(){
        mCityList = new ArrayList<City>();
        cityToNumber=new HashMap<String,String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                prepareCityList();
            }
        }).start();

    }
    
    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();
        cityToNumber=mCityDB.getCityToNumberMap();
        /*int i=0;
        for (City city : mCityList) {
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            Log.d(TAG,cityCode+":"+cityName);
        }
        Log.d(TAG,"i="+i);*/       
        return true;
    }
    
    public static List<City> getCityList() {
        return mCityList;
    }
    public static Map<String,String> getCityMap() {
        return cityToNumber;
    }

}
