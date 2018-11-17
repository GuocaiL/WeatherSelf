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
		//�õ������������ļ��ϵĸ����������������ĸ���		
		} 		
	@Override		
	public Object getItem(int position) {			
			
		// TODO Auto-generated method stub						
		return list.get(position);//�õ����ݼ���ѡ��		
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
				//�ֹ�����һ������			
				TextView textView=(TextView) view.findViewById(R.id.textView1);
				//��items.xml�е�TextView���ص�ҳ��			
				textView.setText(list.get(position));			
				//Log.d("city",list.get(position));			
				
			}catch(Exception e){
				Log.d("getView","�쳣");
				}
			return view;								
	}	
		
}

