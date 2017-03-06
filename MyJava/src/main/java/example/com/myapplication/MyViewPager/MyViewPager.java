package example.com.myapplication.MyViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class MyViewPager extends ViewPager {

    private boolean noScroll = false;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        Log.i(MyViewPager.class.getSimpleName(), " onTouchEvent" + " event = " + arg0);
      if (noScroll){
          return false;
     }
       else
            return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Log.i(MyViewPager.class.getSimpleName(), " dispatchTouchEvent" + " event = " + ev + " noScroll =  " + noScroll);

           return super.dispatchTouchEvent(ev);

        }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        Log.i(MyViewPager.class.getSimpleName(), " onInterceptTouchEvent" + " event = " + arg0);
       if (noScroll) {
           return false;
        }
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
