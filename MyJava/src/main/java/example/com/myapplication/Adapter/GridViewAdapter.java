package example.com.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.myapplication.Bean.HeaderViewBean;
import example.com.myapplication.R;


/**
 * Created by 20256473 on 2017/3/3.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<HeaderViewBean> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int columnWidth;
    /**
     * 页数下标,从0开始
     */
    private int mIndex;
    /**
     * 每页显示最大条目个数 ,默认是dimes.xml里 HomePageHeaderColumn 属性值的两倍
     */
    private int mPageSize;

    /**
     * @param context 上下文
     * @param mDatas 传递的数据
     * @param columnWidth
     * @param mIndex 页码
     */
    public GridViewAdapter(Context context, List<HeaderViewBean> mDatas, int columnWidth, int mIndex) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize =28;
        this.columnWidth= columnWidth;
    }

    public GridViewAdapter(Context context, List<HeaderViewBean> mDatas, int index) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = index;
        mPageSize =28;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (mDatas.size() - mIndex * mPageSize);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "position:" + position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gird, parent, false);
            vh = new ViewHolder();
            //vh.tv = (TextView) convertView.findViewById(R.id.id_tv_title);
            vh.iv = (ImageView) convertView.findViewById(R.id.id_iv_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        int pos = position + mIndex * mPageSize;
        //vh.tv.setText(mDatas.get(pos).name);
        vh.iv.setImageResource(mDatas.get(pos).iconRes);

       /* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"第"+mIndex+"页,Item"+position,Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
