package com.example.miniweatherself;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.pku.edu.liguocai.adapter.GuideAdapter;

public class GuideActivity extends Activity implements OnPageChangeListener{
	
    private ViewPager pageView;
    private List<View> views;
    private GuideAdapter guideAdapter;
    private ImageView dots[];
    private Button but;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		// TODO 自动生成的方法存根	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_layout);
		
		pageView=(ViewPager) findViewById(R.id.guidePage);
		
		views = new ArrayList<View>();
		views.add(LayoutInflater.from(this).inflate(R.layout.page1_guide, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.page2_guide, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.page3_guide, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.page4_guide, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.page5_guide, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.page6_guide, null));
		
		but=(Button)views.get(5).findViewById(R.id.go_weather);
		but.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent i= new Intent(GuideActivity.this,MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		dots=new ImageView[] {
				(ImageView) findViewById(R.id.image1),
				(ImageView) findViewById(R.id.image2),
				(ImageView) findViewById(R.id.image3),
				(ImageView) findViewById(R.id.image4),
				(ImageView) findViewById(R.id.image5),
				(ImageView) findViewById(R.id.image6)
		};
		
		guideAdapter=new GuideAdapter(this, views);
		
		pageView.setAdapter(guideAdapter);
		pageView.setOnPageChangeListener(this);
		
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO 自动生成的方法存根
		for(int i=0;i<dots.length;i++) {
			if(i==arg0) {
				dots[i].setImageResource(R.drawable.page_indicator_focused);
			}
			else {
				dots[i].setImageResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

}
