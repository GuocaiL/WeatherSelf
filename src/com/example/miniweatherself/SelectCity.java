package com.example.miniweatherself;

import java.util.ArrayList;
import java.util.List;

import cn.pku.edu.liguocai.adapter.MyAdapter;
import cn.pku.edu.liguocai.view.ClearEditText;

import com.pku.edu.liguocai.app.MyApplication;
import com.pku.edu.liguocai.bean.City;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class SelectCity extends Activity implements OnClickListener {

	private ImageView mBackBtn;
	private ListView mList;
	private List<City> cityList;
	private List<City> filterDateList;
	private MyAdapter myadpter;
	private ClearEditText mClearEditText;
	
	private void initViews(){
		//mBackBtn=(ImageView) findViewById(R.id.title_back);
		//mBackBtn.setOnClickListener(this);
		filterDateList=new ArrayList<City>();
		mList=(ListView) findViewById(R.id.title_list);
		MyApplication myApplication=(MyApplication)getApplication();
		cityList=myApplication.getCityList();
		
		
		
		for(City city:cityList){
			filterDateList.add(city);
		}
		myadpter=new MyAdapter(cityList,SelectCity.this);
		mList.setAdapter(myadpter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long l) {
				// TODO Auto-generated method stub
				//City city=filterDateList.get(position);
				City city=filterDateList.get(position);
				Intent i =new Intent();
				MainActivity.quanJuCode=city.getNumber();
				i.putExtra("cityCode",MainActivity.quanJuCode);
				setResult(RESULT_OK,i);
				finish();
			}
			});
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_city);
		
		mBackBtn=(ImageView) findViewById(R.id.title_back);
		mBackBtn.setOnClickListener(this);
		initViews();
		
		mClearEditText=(ClearEditText) findViewById(R.id.search_city);
		mClearEditText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				filterData(s.toString());
				mList.setAdapter(myadpter);
			}
			
		});
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()){
		case R.id.title_back:
			Intent i=new Intent();
			i.putExtra("cityCode", MainActivity.quanJuCode);
			setResult(RESULT_OK,i);
			finish();
			break;
		default:
			break;
		}			
	}


	private void filterData(String filterStr){
		filterDateList=new ArrayList<City>();
		
		if(TextUtils.isEmpty(filterStr)){
			for(City city:cityList){
				
				filterDateList.add(city);
			}
		}else{
			filterDateList.clear();
			for(City city:cityList){
				if((city.getCity().contains(filterStr.toString()))||city.getAllPY().contains(filterStr.toString().toUpperCase())
						||city.getAllFristPY().contains(filterStr.toString().toUpperCase())){
					filterDateList.add(city);
				}
			}
		}
		myadpter.updateListView(filterDateList);
	}
}






