package example.com.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import example.com.myapplication.Activity.ChatActivity;
import example.com.myapplication.Adapter.MyfrageCycleAdapter;
import example.com.myapplication.Bean.Contactor;
import example.com.myapplication.MainActivity;
import example.com.myapplication.R;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class FragmentFir extends Fragment {

    private  RecyclerView mRecyclerView;
    private Context context;
    public List<Contactor> mlist=null;
    private static final String SERVERIP = "172.28.12.130";
    private static final int SERVERPORT = 8090;
    private Thread mThread = null;
    private Socket mSocket = null;  private BufferedReader mBufferedReader = null;
    private PrintWriter mPrintWriter = null;
    private static String mStrMSG = "";
    private static String TAG = "TCP";



    private MyfrageCycleAdapter mAdapter;
    public FragmentFir(){}


    public List<Contactor> getdata() {
        Contactor con = null;
        List<Contactor> list=new ArrayList<Contactor>();
        for (int i = 0; i < 10; i++) {
            con = new Contactor("ID" + i, "message" + i);
            list.add(con);
            con = null;

        }
        return  list;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.context=getActivity();
        mlist=getdata();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);//关联布局文件
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.re_frag);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new MyfrageCycleAdapter(this.getActivity(),mlist);
       mAdapter.setOnItemClickListener(new MyfrageCycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int data) {
                Toast.makeText(context, "点击了一次", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getActivity(), ChatActivity.class);

                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new MyfrageCycleAdapter.OnRecyclerItemLongListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(context,"长按了一次",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return  rootView;
    }

}
