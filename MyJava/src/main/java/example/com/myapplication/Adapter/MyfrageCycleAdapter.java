package example.com.myapplication.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.myapplication.Bean.Contactor;
import example.com.myapplication.MyViewPager.MyViewPager;
import example.com.myapplication.R;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class MyfrageCycleAdapter extends RecyclerView.Adapter<MyfrageCycleAdapter.MyViewHolder>  {
    private Context context;
    private List<Contactor> list;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerItemLongListener mOnItemLong = null;

    public  MyfrageCycleAdapter(Context context,List<Contactor >list){
        this.list=list;
        this.context=context;
    }

    //定义接口，回调点击事件方法
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }
    public interface OnRecyclerItemLongListener{
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnRecyclerItemLongListener listener){
        this.mOnItemLong =  listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        View view=LayoutInflater.from(this.context).inflate(R.layout.item_fragment1,parent,false);
        holder = new MyViewHolder(view,mOnItemClickListener,mOnItemLong);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_message.setText(list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        TextView tv_name;
        TextView tv_message;
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;
        private OnRecyclerItemLongListener mOnItemLong = null;


        public MyViewHolder(View view, OnRecyclerViewItemClickListener mListener,OnRecyclerItemLongListener longListener) {
            super(view);
            this.mOnItemClickListener = mListener;
            this.mOnItemLong = longListener;
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_message=(TextView) view.findViewById(R.id.tv_message);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View v) {
            if(mOnItemLong != null){
                mOnItemLong.onItemLongClick(v,getPosition());
            }
            return true;
        }
        }


    }


