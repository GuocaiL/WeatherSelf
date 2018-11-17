package cn.pku.edu.liguocai.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.miniweatherself.R;
import com.pku.edu.liguocai.bean.City;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "InflateParams", "ViewHolder" }) public class MyAdapter extends BaseAdapter{ 	
	

	private List<String> list=new ArrayList<String>();	
	private Context context;
	
	
	public MyAdapter(List<City> listCity,Context context) {		
		// TODO Auto-generated constructor stub	
		list.clear();
		for(City city:listCity){
			list.add(city.getCity().toString());
		}
		this.context=context;
		}	
		
	public void updateListView(List<City> listCity)	{
		list.clear();
		for(City city:listCity){
			list.add(city.getCity().toString());
		}
		notifyDataSetChanged();
	}
	
	@Override		
	public int getCount() {			
		// TODO Auto-generated method stub			
		return list.size();
		//得到适配器中填充的集合的个数，即是适配器的个数		
		} 		
	@Override		
	public Object getItem(int position) {			
			
		// TODO Auto-generated method stub						
		return list.get(position);//得到数据集的选项		
	} 		
		
	@Override				
	public long getItemId(int position) {			
			
		// TODO Auto-generated method stub			
			
		return position;		
		} 		
		
		
	@Override		
	public View getView(int position, View convertView, ViewGroup parent) {
		    View view = null;
			try{
				// TODO Auto-generated method stub			
				view=LayoutInflater.from(context).inflate(R.layout.adapter_cityitem, null);
				//手工加载一个布局			
				TextView textView=(TextView) view.findViewById(R.id.textView1);
				//把items.xml中的TextView加载到页面			
				textView.setText(list.get(position));			
				//Log.d("city",list.get(position));			
				
			}catch(Exception e){
				Log.d("getView","异常");
				}
			return view;								
	}	
		
}

