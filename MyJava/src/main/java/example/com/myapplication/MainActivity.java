package example.com.myapplication;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import example.com.myapplication.Adapter.MenuAdapter;
import example.com.myapplication.Adapter.MyFragmentPagerAdapter;
import example.com.myapplication.MyViewPager.MyViewPager;
import example.com.myapplication.fragment.FragmentFir;
import example.com.myapplication.fragment.FragmentSec;
import example.com.myapplication.fragment.FragmentTir;

public class MainActivity extends AppCompatActivity {

    private MyViewPager mPager;
   public ArrayList<Fragment> fragmentList;
    private RadioGroup rd_group;
    private RadioButton rd_1;
    private RadioButton rd_2;
    private RadioButton rd_3;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidMenu();
        InitViewPager();
        rd_group=(RadioGroup)findViewById(R.id.rd_group);
        rd_1= (RadioButton)findViewById(R.id.rd_1);
        rd_2= (RadioButton)findViewById(R.id.rd_2);
        rd_3 = (RadioButton) findViewById(R.id.rd_3);
        rd_1.setChecked(true);
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rd_1.getId()) {
                    rd_2.setChecked(false);
                    rd_3.setChecked(false);
                    rd_1.setChecked(true);
                    mPager.setCurrentItem(0);
                } else if (checkedId == rd_2.getId()) {
                    rd_1.setChecked(false);
                    rd_3.setChecked(false);
                    rd_2.setChecked(true);
                    mPager.setCurrentItem(1);
                } else if (checkedId == rd_3.getId()) {
                    rd_2.setChecked(false);
                    rd_1.setChecked(false);
                    rd_3.setChecked(true);
                    mPager.setCurrentItem(2);
                }
            }
        });


    }

    public  void initSlidMenu(){
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.color.colorAccent);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(getLeftMenu());
    }
    public View getLeftMenu() {
        //从主布局文件绑定的Activity调用另一个布局文件必须调用LayoutInflater
        LayoutInflater inflater = getLayoutInflater();
        //得到menu的View
        View v = inflater.inflate(R.layout.left_menu, null);
        RecyclerView listview = (RecyclerView) v.findViewById(R.id.teach_recycler);
        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setAdapter(new MenuAdapter(this,getData()));



        return v;
    }
    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }
    public void InitViewPager(){
        mPager = (MyViewPager)findViewById(R.id.vp_main);
        mPager.setNoScroll(true);
        fragmentList = new ArrayList<Fragment>();
        Fragment btFragment= new FragmentFir();
        Fragment secondFragment = new FragmentSec();
        Fragment thirdFragment =new FragmentTir();
        fragmentList.add(btFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(thirdFragment);
        //给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页

    }
}
