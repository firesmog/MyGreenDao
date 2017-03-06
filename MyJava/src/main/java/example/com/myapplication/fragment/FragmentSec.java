package example.com.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import example.com.myapplication.MainActivity;
import example.com.myapplication.R;

/**
 * Created by 20256473 on 2017/2/22.
 */

public class FragmentSec extends Fragment {
    private  Button bt;
    MainActivity main=(MainActivity)getActivity();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_2, container, false);//关联布局文件
        bt=(Button)rootView.findViewById(R.id.bt_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"haluo",Toast.LENGTH_LONG).show();

            }
        });
        return  rootView;
    }
}
