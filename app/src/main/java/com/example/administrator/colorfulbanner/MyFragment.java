package com.example.administrator.colorfulbanner;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.colorfulbanner.views.MainActivityViewModel;
import com.example.administrator.colorfulbanner.views.MomentsPageScrollEvent;
import com.example.administrator.colorfulbanner.views.MyAdapter;
import com.example.administrator.colorfulbanner.views.TestBen;
import com.example.administrator.colorfulbanner.views.UpdateSAEvent;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.tencent.mmkv.MMKV;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyFragment extends Fragment {
    View view;
    private String TAG = "AAA";
    private List<String> bannerList = new ArrayList<>();
    private List<ColorInfo> colorList = new ArrayList<>();
    private BannerImageLoader imageLoader;
    private ImageView ivBannerHeadBg;
    private int count;
    private boolean isInit = true;
    boolean ishow = false;
    int position;
    private static int color = Color.parseColor("#8F8F8F");
    private XBanner mBanner4;
    private Palette palette;
    ArrayList<TestBen> arrayList = new ArrayList<>();
    RecyclerView my_list;
    MyAdapter myAdapter;
    MainActivityViewModel model;
    SlidingScaleTabLayout tabLayout;

    public static MyFragment instance(int position, SlidingScaleTabLayout tabLayout) {
        MyFragment view = new MyFragment();
        view.position = position;
        view.tabLayout = tabLayout;
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("生命：", "onCreateView");
        view = inflater.inflate(R.layout.my_fragment, null);
        model = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        view.setTag(position);
        initView();
        initBanner(mBanner4);
        setMyAdapter();
        return view;

    }

    private void initView() {
        my_list = view.findViewById(R.id.my_list);
        mBanner4 = view.findViewById(R.id.banner4);
        mBanner4.setPageTransformer(Transformer.Default);
        ivBannerHeadBg = MainActivity.ivBannerHeadBg;
        if (bannerList.size() == 0) {
            bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532613936&di=3769695217e3424f18c3d23966ecd4dc&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F04%2F19%2F70e2846ebc02ae10161f25bf7f5461a1.jpg");
            bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532665664&di=9ead9eb8a9fe2af9a01b0dd39f3e41f4&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F05%2F37%2F28%2F475a43591370453.jpg");
            bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532613934&di=0be1c6bbf0441bd19ef6d4e3ce799263&imgtype=0&src=http%3A%2F%2Fpic96.nipic.com%2Ffile%2F20160430%2F7036970_215739900000_2.jpg");
            bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532613936&di=4dd453940f49d9801826e6b820490957&imgtype=0&src=http%3A%2F%2Fpic161.nipic.com%2Ffile%2F20180410%2F26429156_154754410034_2.jpg");
            bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532613935&di=39c387012e3d8fa2eef90129eaf83c5c&imgtype=0&src=http%3A%2F%2Fpic25.nipic.com%2F20121211%2F7031681_170238437383_2.jpg");
        }
        if (position % 2 == 0) {
            Collections.reverse(bannerList);
        }
    }

    private void setMyAdapter() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i + "");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        my_list.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(arrayList, getActivity());
        my_list.setAdapter(myAdapter);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.ishow = isVisibleToUser;
        if (ishow) {
            if (mBanner4 != null) {
                int vibrantColor = colorList.get(mBanner4.getBannerCurrentItem() + 1).getVibrantColor();
                ivBannerHeadBg.setBackgroundColor(vibrantColor);
                setStatusBarColor(getActivity(), vibrantColor);
                mBanner4.startAutoPlay();
            }
        } else {
            if (mBanner4 != null) {
                mBanner4.stopAutoPlay();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("22222222222");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("33333333333");
    }


    public int getColor() {
        return color;
    }

    /**
     * 初始化XBanner
     */
    private void initBanner(final XBanner banner) {
        final int tag = position;
        count = bannerList.size();
        if (arrayList.size() == 0) {
            for (int i = 0; i <= count + 1; i++) {
                ColorInfo info = new ColorInfo();
                if (i == 0) {
                    info.setImgUrl(bannerList.get(count - 1));
                } else if (i == count + 1) {
                    info.setImgUrl(bannerList.get(0));
                } else {
                    info.setImgUrl(bannerList.get(i - 1));
                }
                colorList.add(info);
            }
            banner.setPageTransformer(Transformer.Default);
            for (int i = 0; i < bannerList.size(); i++) {
                TestBen testBen = new TestBen();
                testBen.setUrl(bannerList.get(i));
                arrayList.add(testBen);
            }

        }
        banner.setBannerData(R.layout.home_banner, arrayList);
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object models, View view, int position) {
//                Intent intent = new Intent(getActivity(), ActivityTwo.class);
//                getActivity().startActivity(intent);
                Toast.makeText(getActivity(), "点击了第" + (mBanner4.getBannerCurrentItem() + 1) + "图片", Toast.LENGTH_SHORT).show();
            }
        });
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(final XBanner banner, Object models, View view, final int position) {
                ImageView imageView = view.findViewById(R.id.bannerimg);
                final String url = arrayList.get(position).getUrl();
                Glide.with(getActivity()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object models, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        setColorList(resource, url);
                        if (position == banner.getBannerCurrentItem()) {
                            int vibrantColor = colorList.get(position + 1).getVibrantColor();
                            if (ishow) {
                                if (isInit) {// 第一次,延时加载才能拿到颜色
                                    isInit = false;

                                    ivBannerHeadBg.setBackgroundColor(vibrantColor);
                                    setStatusBarColor(getActivity(), vibrantColor);
                                    color = vibrantColor;
                                }
                            }
                            System.out.println("getTag:" + getTag());
                            model.getColorlist().getValue().set(tag, vibrantColor);
                        }
                        return false;
                    }
                }).into(imageView);
            }
        });

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (ishow) {
                    if (positionOffset > 1) {//会出现极个别大于1的数据
                        return;
                    }
                    //修正position，解决两头颜色错乱，来自Banner控件源码
                    if (position == 0) {
                        position = count;
                    }
                    if (position > count) {
                        position = 1;
                    }
                    int pos = (position + 1) % count;//很关键
//System.out.println("positionOffset:"+positionOffset);
                    int vibrantColor = ColorUtils.blendARGB(colorList.get(pos).getVibrantColor(), colorList.get(pos + 1).getVibrantColor(), positionOffset);
                    ivBannerHeadBg.setBackgroundColor(vibrantColor);
                    setStatusBarColor(getActivity(), vibrantColor);
                }
            }

            @Override
            public void onPageSelected(int i) {
                int vibrantColor = colorList.get(mBanner4.getBannerCurrentItem() + 1).getVibrantColor();
                model.getColorlist().getValue().set(tag, vibrantColor);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void setColorList(Bitmap bitmap, String imgUrl) {
        if (colorList == null) {
            return;
        }
        palette = Palette.from(bitmap).generate();
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getImgUrl().equals(imgUrl)) {// imgUrl作为识别标志
                if (palette.getVibrantSwatch() != null) {
                    colorList.get(i).setVibrantColor(palette.getVibrantSwatch().getRgb());
                }
                if (palette.getDarkVibrantSwatch() != null) {
                    colorList.get(i).setVibrantDarkColor(palette.getDarkVibrantSwatch().getRgb());
                }
                if (palette.getLightVibrantSwatch() != null) {
                    colorList.get(i).setVibrantLightColor(palette.getLightVibrantSwatch().getRgb());
                }
                if (palette.getMutedSwatch() != null) {
                    colorList.get(i).setMutedColor(palette.getMutedSwatch().getRgb());
                }
                if (palette.getDarkMutedSwatch() != null) {
                    colorList.get(i).setMutedDarkColor(palette.getDarkMutedSwatch().getRgb());
                }
                if (palette.getLightVibrantSwatch() != null) {
                    colorList.get(i).setMutedLightColor(palette.getLightVibrantSwatch().getRgb());
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Update(UpdateSAEvent updateSAEvent) {
        if (updateSAEvent.isScroll()) {
            mBanner4.stopAutoPlay();
        } else {
            mBanner4.startAutoPlay();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MommentScroll(MomentsPageScrollEvent momentsPageScrollEvent) {
        if (ishow && momentsPageScrollEvent.getPositionOffset() != 0) {
            System.out.println("position:" + position);
            System.out.println("getPosition:"+momentsPageScrollEvent.getPosition());
            if (position == momentsPageScrollEvent.getPosition()) {
                int vibrantColor = ColorUtils.blendARGB(model.getColorlist().getValue().get(position), model.getColorlist().getValue().get(position + 1), momentsPageScrollEvent.getPositionOffset());
                ivBannerHeadBg.setBackgroundColor(vibrantColor);
                setStatusBarColor(getActivity(), vibrantColor);
            } else {
                int vibrantColor = ColorUtils.blendARGB(model.getColorlist().getValue().get(position - 1),model.getColorlist().getValue().get(position), momentsPageScrollEvent.getPositionOffset());
                ivBannerHeadBg.setBackgroundColor(vibrantColor);
                setStatusBarColor(getActivity(), vibrantColor);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
