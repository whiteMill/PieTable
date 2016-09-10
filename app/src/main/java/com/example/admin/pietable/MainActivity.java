package com.example.admin.pietable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private View view1, view2, view3, view4, view5;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private LayoutInflater mInflater;
    private Boolean misScrolled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        mCircleIndicator= (CircleIndicator) findViewById(R.id.indicator);

        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.view, null);
        view2 = mInflater.inflate(R.layout.view1, null);
        view3 = mInflater.inflate(R.layout.view2, null);
        view4 = mInflater.inflate(R.layout.view1, null);
        view5 = mInflater.inflate(R.layout.view, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));
                return mViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }
        });
        mViewPager.addOnPageChangeListener(this);
        mCircleIndicator.setViewPager(mViewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    /*
    * 正常拖动过程中状态变化时 SCROLL_STATE_IDLE——》SCROLL_STATE_DRAGGING——》SCROLL_STATE_SETTLING——》SCROLL_STATE_IDLE
     但是如果最后一页向左滑动和第一页向右滑动是不可能滑动成功的，于是状态改变就有些不同SCROLL_STATE_IDLE——》SCROLL_STATE_DRAGGING——》SCROLL_STATE_IDLE */

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case ViewPager.SCROLL_STATE_IDLE: // 空闲状态
                Log.d("TTT","空闲状态");
                if(mViewPager.getCurrentItem()==mViewPager.getAdapter().getCount()-1&&!misScrolled){
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                misScrolled=true;
                break;
            case ViewPager.SCROLL_STATE_DRAGGING: //正在被拖动
                Log.d("TTT","正在被拖动");
                misScrolled=false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING://一个拖动过程完成
                Log.d("TTT","一个拖动过程结束");
                misScrolled=true;
                break;

        }

    }
}
