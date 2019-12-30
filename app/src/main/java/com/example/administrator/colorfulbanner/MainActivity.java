package com.example.administrator.colorfulbanner;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.administrator.colorfulbanner.views.MainActivityViewModel;
import com.example.administrator.colorfulbanner.views.MomentsPageScrollEvent;
import com.example.administrator.colorfulbanner.views.UpdateSAEvent;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ImageView ivBannerHeadBg;
    private SlidingScaleTabLayout mWaiteIndicator;
    private ViewPager mViewpage;
    private ArrayList<MyFragment> mFragments = new ArrayList<>();
    SectionsPagerAdapter sectionsPagerAdapter;
    MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMKV.initialize(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initView();
        ivBannerHeadBg = findViewById(R.id.iv_banner_head_bg);

    }

    private void initView() {
        mWaiteIndicator = findViewById(R.id.waite_indicator);
        mViewpage = findViewById(R.id.viewpage);
        setmMessagePage();
    }

    private void setmMessagePage() {
        for (int i = 0; i < 4; i++) {
            MyFragment myFragment = new MyFragment();
            mFragments.add(myFragment);
        }
        ArrayList<Integer> integerArrayList=new ArrayList<>();
        for (int i=0;i<mFragments.size();i++){
            integerArrayList.add(0);
        }
        model.getColorlist().postValue(integerArrayList);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewpage.setAdapter(sectionsPagerAdapter);
        mWaiteIndicator.setViewPager(mViewpage);

        mViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {
                System.out.println("positions:"+position);
                EventBus.getDefault().post(new MomentsPageScrollEvent(positionOffset,position));
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        EventBus.getDefault().post(new UpdateSAEvent(true));
                        break;
                    case 2:
                        EventBus.getDefault().post(new UpdateSAEvent(false));
                        break;
                }
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position).instance(position, mWaiteIndicator);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "切换" + position;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            return (int) view.getTag();
        }
    }
    /**
     * 设置状态栏颜色
     *
     * @param activity
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //状态栏改变颜色。
            window.setStatusBarColor(color);
        }
    }
}
