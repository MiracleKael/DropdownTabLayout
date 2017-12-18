    转载请标明出处：
	  
	  本文出自Miracle的博客

#简介
一款可下拉选择的TabLayout库，基于TabLayout
##背景
对于一个Android开发者来说，**TabLayout**在熟悉不过了，而且有类似功能的第三方库如：
flycotablayout、ViewPagerIndicator、MagicIndicator，这么多强大库，酷炫吧，挑花眼了吧。之前项目里也使用到了Tablayout功能，但UI要求是一个可下拉选择的Tablayout。

![](https://i.imgur.com/hIqZfrA.jpg)


好吧，就是这样的：

![](https://i.imgur.com/MxcQ69i.png)

以上几个库都没有这种类似的功能，好吧，自己动手做一个吧。

##思路
咋搞啊？
先来看看能不能基于TabLayout做点修改吧。让我们看看Tablayout源码，它提供了哪些方法。
一打开源码，你就会发现，这货继承了HorizontalScrollView，换了个龟壳就不认识它了
![](https://i.imgur.com/f5j7fID.png)

再瞅瞅它都提供了哪些方法，将可能有用的方法找出来
![](https://i.imgur.com/h0WyHR8.png)

然后研究研究这几个方法干啥用的，具体的我就不说了，自己研究去吧。然后我就发现了getTabAt（index:int）这个方法的特殊之处，getTabAt（index:int）.setCustomView(View view)
![](https://i.imgur.com/XGCejwv.png)

OK,八九不离十就从它开刀了。

接下来，就是自己去创建View添加进去

    getTabAt(i).setCustomView(getTabView(i, titleList.get(i)));


    /**
     * 得到自定义的tabItem
     * @param position
     * @return
     */
    public View getTabView(final int position, final List<String> titleList){
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
        final TextView tabText = view.findViewById(R.id.tab_text);
        tabText.setTextColor(getResources().getColor(R.color.colorTabViewText));
        tabText.setText(titleList.get(0));
        ImageView tab_img = view.findViewById(R.id.tab_img);
        if(titleList.size() == 1) {
            tab_img.setVisibility(GONE);
        }

        tab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showPopupwindow(tabText,position,view, titleList);
            }
        });
        return view;
    }

点击弹出下拉列表,那就用popupwindow吧

     private PopupWindow popupWindow;
    private void showPopupwindow(final TextView tabText, final int tabPosition, View v, final List<String> titleList) {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }else {
            WindowManager wm  = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.choose_popupwindow, null);
            ListView listview = (ListView) inflate.findViewById(R.id.listview);

            listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return titleList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View  view;
                    if(convertView == null) {
                        view = LayoutInflater.from(mContext).inflate(R.layout.item_dance_category,null);
                    }else {
                        view = convertView;
                    }
                    final TextView  name= (TextView) view.findViewById(R.id.dance_name);

                    final ImageView ivSelected= (ImageView) view.findViewById(R.id.iv_selected);
                    name.setText(titleList.get(position));

                    if(position == (int)sparseArray.get(tabPosition)) {
                        ivSelected.setVisibility(View.VISIBLE);
                    }else{
                        ivSelected.setVisibility(View.INVISIBLE);
                    }


                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sparseArray.setValueAt(tabPosition, position);

                            mViewPager.setCurrentItem(tabPosition);
                            tabText.setText(titleList.get(position));

                            onTabSelectedListener.selected(tabPosition, position);

                            //关闭窗口
                            popupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            popupWindow = new PopupWindow(inflate, wm.getDefaultDisplay().getWidth(), ViewPager.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            v.getLocationOnScreen(location);
            int x = 0;
            int y = location[1] + v.getHeight();
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
        }
    }

根据定义一个接口回调点击事件，对tab作出相应的修改

     public interface OnTabSelectedListener{
        void selected(int tabPosition, int listPosition);
    }
    private OnTabSelectedListener onTabSelectedListener;
    
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener){
        this.onTabSelectedListener = onTabSelectedListener;
    }

OK，到此基本就Over了，来试试吧

	 private DropdownTabLayout tablayout;
   	 private List<List<String>> titleList;
   	 private ViewPager viewpager;

     @Override
    protected void onStart() {
        super.onStart();
        //初始化几个fragment
        List<TestFragment> testFragmentList = new ArrayList<>();
        testFragmentList.add(new TestFragment().newInstance(1));
        testFragmentList.add(new TestFragment().newInstance(2));
        testFragmentList.add(new TestFragment().newInstance(3));
        testFragmentList.add(new TestFragment().newInstance(4));
        testFragmentList.add(new TestFragment().newInstance(5));

        TestViewPagerAdapter testAdapter = new TestViewPagerAdapter(getSupportFragmentManager(),testFragmentList);
        viewpager.setAdapter(testAdapter);
        tablayout.setupWithViewPager(viewpager);


        //这里tab的数据可以从网络获取也可以直接写死了，这里我们模拟联网请求，3秒
        //模拟联网请求加载数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tablayout.setUpTitle(titleList);
            }
        }, 3000);

    }


然后运行，就是这个样子

![](https://i.imgur.com/bpASv1D.png)

为了方便使用，我将它封装成了一个库，直接通过 compile 'com.android.support:design:26.0.0-alpha1'就可以直接使用
github地址：[https://github.com/jiaojingLdy/DropdownTabLayoutSample](https://github.com/jiaojingLdy/DropdownTabLayoutSample)



