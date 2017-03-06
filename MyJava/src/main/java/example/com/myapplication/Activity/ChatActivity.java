package example.com.myapplication.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import example.com.myapplication.Adapter.GridViewAdapter;
import example.com.myapplication.Adapter.MsgAdapter;
import example.com.myapplication.Adapter.MyViewPagerAdapter;
import example.com.myapplication.Bean.HeaderViewBean;
import example.com.myapplication.Bean.Msg;
import example.com.myapplication.R;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private List<View> mViewPagerGridList;
    private static final String SERVERIP = "172.28.12.130";
    private static final int SERVERPORT = 8090;
    private Thread mThread = null;
    private Socket mSocket = null;
    private BufferedReader mBufferedReader = null;
    private PrintWriter mPrintWriter = null;
    private static String mStrMSG = "";
    private static String TAG = "TCP";
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private  ImageView iv_motion;
    private Boolean IsShow=false;
    private ViewPager vp_motion;
    private List<HeaderViewBean> mDatas = new ArrayList<>();
    private  Button btnSend;
    private Button btLogin;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private int[] drawable;
    private TextView tv_Show;
    private  boolean IsSend=false;
    private String[] faceScr;
    private  Pattern pattern;
    private HashMap<String, Integer> faceBook;
    private Handler handler = new Handler() {

        // 该方法运行在主线程中
        // 接收到handler发送的消息，对UI进行操作
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 1) {


              //  tv_Show.setText(msg.getData().getCharSequence("time"));
              // msgList.add(new Msg(addSmileySpans, Msg.TYPE_SEND));
                msgList.add(new Msg(msg.getData().getCharSequence("time"), Msg.TYPE_SEND));
                adapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);
            }else if(msg.what==2){
                msgList.add(new Msg(msg.getData().getCharSequence("recive"), Msg.TYPE_RECEIVE));
                adapter.notifyItemInserted(msgList.size() - 1);
                recyclerView.scrollToPosition(msgList.size() - 1);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMsg();
        initDatas();
        //连接服务器
        ConnectServ();
        btnSend = (Button) this.findViewById(R.id.btnsend);
        btLogin=(Button) this.findViewById(R.id.btLogin);
        inputText = (EditText) this.findViewById(R.id.input_text);
        mViewPager = (ViewPager) findViewById(R.id.vp_motion);
        iv_motion = (ImageView) findViewById(R.id.iv_motion);
        vp_motion=(ViewPager)findViewById(R.id.vp_motion);
        recyclerView = (RecyclerView) this.findViewById(R.id.msg_recyclerView);
        mViewPagerGridList = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);
        btnSend.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        iv_motion.setOnClickListener(this);
        inputText.setOnClickListener(this);
        // 每页显示最大条目个数
        int pageSize = 28;
        //页数
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        //获取屏幕的宽度,单位px
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        //获取GridView中每个item的宽度 = 屏幕宽度 / GridView显示的列数
        int columnWidth = (int) Math.ceil((screenWidth) * 1.0 / 7);

        for (int index = 0; index < pageCount; index++) {
            GridView grid = (GridView) inflater.inflate(R.layout.gird_motion, mViewPager, false);
            //设置GridView每个item的宽度
            grid.setColumnWidth(columnWidth);
            //设置GirdView的布局参数(宽和高，宽为包裹父容器，高 = columnWidth)
            grid.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, columnWidth));
            grid.setAdapter(new GridViewAdapter(this, mDatas, index));
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Log.i("TAG",position+"******************"+id+"");
                    faceReplace(getApplicationContext(),drawable[(int) id],faceScr[(int)id],inputText);
                }
            });
            mViewPagerGridList.add(grid);
        }
        mViewPager.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));

    }





    public void sendMsg(){
        String content = inputText.getText().toString().trim();
        IsSend=true;
        if (!content.equals(""))
        {
            mPrintWriter.println(content);
            mPrintWriter.flush();
            inputText.setText("");
        }
        mThread = new Thread(mRunnable);
        mThread.start();


    }
    private Runnable mRunnable = new Runnable() {
        public void run() {
            while (true) {
                try {
                    if ((mStrMSG = mBufferedReader.readLine()) != null) {

                        if(IsSend){
                            Message message=new Message();
                            Bundle bundle=new Bundle();
                            CharSequence addSmileySpans=addSmileySpans(mStrMSG);
                            bundle.putCharSequence("time",addSmileySpans);
                            message.setData(bundle);//bundle传值，耗时，效率低
                            handler.sendMessage(message);//发送message信息
                            message.what=1;//标志是哪个线程传数据
                        }else{
                            Message message=new Message();
                            Bundle bundle=new Bundle();
                            CharSequence addSmileySpans=addSmileySpans(mStrMSG);
                            bundle.putCharSequence("recive",addSmileySpans);
                            message.setData(bundle);//bundle传值，耗时，效率低
                            handler.sendMessage(message);//发送message信息
                            message.what=2;//标志是哪个线程传数据
                        }
                        IsSend=false;

                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    };
    private void initMsg()
    {
        Msg msg1 = new Msg("hello sealong", Msg.TYPE_RECEIVE);
        msgList.add(msg1);
        Msg msg2 = new Msg("hello peipei", Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg = new Msg("What are you doing", Msg.TYPE_RECEIVE);
        msgList.add(msg);
    }
    public void ConnectServ(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.i(TAG, "lianjie ");
                    // ①Socket实例化，连接服务器
                    mSocket = new Socket(SERVERIP, SERVERPORT);
                    // ②获取Socket输入输出流进行读写操作
                    mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);

                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e(TAG, e.toString());
                }
            }

        }).start();
    }


   private void initDatas() {
       initMotion();
       for(int i=0;i<drawable.length;i++){
           HeaderViewBean headerViewBean = new HeaderViewBean("Item " + (i + 1), drawable[i]);
           mDatas.add(headerViewBean);
           headerViewBean=null;
       }
   }

    public void initMotion(){
        drawable = new int[]
                {
                        R.drawable.f000,R.drawable.f001,R.drawable.f002,R.drawable.f003,R.drawable.f004,R.drawable.f005,
                        R.drawable.f006,R.drawable.f007,R.drawable.f008,R.drawable.f009,R.drawable.f010,R.drawable.f011,
                        R.drawable.f012,R.drawable.f013,R.drawable.f014,R.drawable.f015,R.drawable.f016,R.drawable.f017,
                        R.drawable.f018,R.drawable.f019,R.drawable.f020,R.drawable.f021,R.drawable.f022,R.drawable.f023,
                        R.drawable.f024,R.drawable.f025,R.drawable.f026,R.drawable.f027,R.drawable.f059,R.drawable.f028,
                        R.drawable.f029,R.drawable.f030,R.drawable.f031,R.drawable.f032,R.drawable.f033,R.drawable.f034,
                        R.drawable.f035,R.drawable.f036,R.drawable.f037,R.drawable.f038,R.drawable.f039,R.drawable.f040,
                        R.drawable.f041,R.drawable.f042,R.drawable.f043,R.drawable.f044,R.drawable.f045,R.drawable.f046,
                        R.drawable.f047,R.drawable.f048,R.drawable.f049,R.drawable.f050,R.drawable.f051,R.drawable.f052,
                        R.drawable.f053,R.drawable.f054,R.drawable.f055,R.drawable.f056,R.drawable.f057,R.drawable.f058,
                };
        faceScr = new String[] { "#000", "#001", "#002", "#003", "#004",
                "#005", "#006", "#007", "#008", "#009", "#010", "#011", "#012",
                "#013", "#014", "#015", "#016", "#017", "#018", "#019", "#020",
                "#021", "#022", "#023", "#024", "#025", "#026", "#027", "#028",
                "#029", "#030", "#031", "#032", "#033", "#034", "#035", "#036",
                "#037", "#038", "#039", "#040", "#041", "#042", "#043", "#044",
                "#045", "#046", "#047", "#048", "#049", "#050", "#051", "#052",
                "#053", "#054", "#055", "#056", "#057", "#058", "#059" };
        faceBook=buildSmileyToRes();
        pattern=buildPattern();


    }
    private HashMap<String, Integer> buildSmileyToRes() {
        /**
         * 文字和ID不匹配，异常
         */
        if (drawable.length != faceScr.length) {
            throw new IllegalStateException("Smiley resource ID/text mismatch");
        }
        HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(
                faceScr.length);
        for (int i = 0; i < faceScr.length; i++) {
            smileyToRes.put(faceScr[i], drawable[i]);
        }
        return smileyToRes;
    }

    public  void showMotion(){
        if(IsShow==false){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputText.getWindowToken(),0);
            vp_motion .setVisibility(View.VISIBLE);
            IsShow=true;
        }else {
            vp_motion.setVisibility(View.GONE);
            IsShow=false;
        }
    }

    public void faceReplace(Context context, int resourceId,
                            String spannableStr, EditText edit) {
        /**
         * 根据ID获取图片资源
         */
        Drawable dr = context.getResources().getDrawable(resourceId);
        dr.setBounds(0, 0, dr.getIntrinsicWidth()*4/5, dr.getIntrinsicHeight()*4/5);
        /**
         * SpannableString 配置被替代的文字描述；ImageSpan 配置替代的图片
         */
        SpannableString spanStr = new SpannableString(spannableStr);
        ImageSpan spanImg = new ImageSpan(dr, ImageSpan.ALIGN_BASELINE);
        /**
         * 将文字如【smile】替换为表情 参数二、参数三分别指定所要替代字符的位置
         */
        spanStr.setSpan(spanImg, 0, spanStr.length(), spanStr.SPAN_EXCLUSIVE_EXCLUSIVE);
        edit.append(spanStr);
    }

    public CharSequence addSmileySpans(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int resId = faceBook.get(matcher.group());
            Drawable dr = this.getResources().getDrawable(resId);
            dr.setBounds(0, 0, dr.getIntrinsicWidth()*4/5, dr.getIntrinsicHeight()*4/5);
            builder.setSpan(new ImageSpan(dr, ImageSpan.ALIGN_BASELINE),
                    matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    private Pattern buildPattern() {

        StringBuilder patternString = new StringBuilder(faceScr.length * 3);
        patternString.append('(');
        for (String s : faceScr) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1,
                patternString.length(), ")");
        return Pattern.compile(patternString.toString());
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsend:
                sendMsg();
                break;

            case R.id.iv_motion:
                showMotion();
                break;
            case R.id.input_text:
                vp_motion.setVisibility(View.GONE);
                break;

        }
    }

}