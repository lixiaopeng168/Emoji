package com.lxp.emoji;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxp.emoji.utli.UIUtils;
import com.lxp.emoji.utli.emoji.EmojiPagerAdapter;
import com.lxp.emoji.utli.emoji.SimleyAdapter;
import com.lxp.emoji.utli.emoji.SmileAddAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class EmojiRelativaLayout extends RelativeLayout implements View.OnClickListener, View.OnTouchListener, TextView.OnEditorActionListener {
    private Context mContext;

    private EditText mEmojiEdit;//输入框
    private ImageButton mEmojiBtn;//表情按钮
    private RelativeLayout mEmojiView;//表情布局
    private ViewPager mEmojiVp;//布局vp
    private LinearLayout mEmojiPoint;//滑动的小圆点
    private EmojiPagerAdapter mEmojiPagerAdapter;//表情vp适配器
    private List<View> mVpList;//表情vp数据列表

    //监听底部表情布局和键盘的切换
    private static boolean isEmoji,isKeyBrodEmoji;

    //回调
    private OnEmojiView mOnEmojiView;
    public EmojiRelativaLayout(Context context) {
        this(context,null);

    }

    public EmojiRelativaLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmojiRelativaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void setOnEmojiView(OnEmojiView onEmojiView){
        this.mOnEmojiView = onEmojiView;
    }
    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.emoji_layout,null);
        mEmojiEdit = (EditText) view.findViewById(R.id.emojiEt);
        mEmojiBtn = (ImageButton) view.findViewById(R.id.mainEmojiBtn);
        mEmojiView = (RelativeLayout) view.findViewById(R.id.emojiViewLayout);
        mEmojiVp = (ViewPager) view.findViewById(R.id.emojiVp);
        mEmojiPoint = (LinearLayout) view.findViewById(R.id.emojiPoint);
        addView(view);
        initoper();
        initViewPager();
    }

    private void initViewPager() {
        mVpList = new ArrayList<>();

//        SmileyParser.getInstance().init();
        //循环添加表情view到适配器
        for (int i = 0; i < 5; i++) {
            View gridview1 = LayoutInflater.from(mContext).inflate(R.layout.emoji_gridview, null);
            GridView gv = (GridView) gridview1.findViewById(R.id.gridview1);
            //表情
            SimleyAdapter mAdapter = new SimleyAdapter(mContext,i+1);

            //添加小圆点
            ImageView ivPoint = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60,60);

            //第一个页面默认选中状态，所以添加的是选中的小圆点
            params.setMargins(5,0,0,0);
            if (i == 0){
                ivPoint.setImageResource(R.mipmap.point_select);
            }else {

                ivPoint.setImageResource(R.mipmap.point_no);
            }
            ivPoint.setLayoutParams(params);
            mEmojiPoint.addView(ivPoint);

            gv.setAdapter(mAdapter);
            mVpList.add(gridview1);

            SmileAddAction addAction = new SmileAddAction();
//            addAction.addAction(gv, mEmojiEdit, i==0 ? 23 : (i+1)*23);
            if (i == 0){
                addAction.addAction(gv,mEmojiEdit,0);
            }else if (i == 1){
                addAction.addAction(gv,mEmojiEdit,23);
            }else if (i == 2){
                addAction.addAction(gv,mEmojiEdit,46);
            }else if (i == 3){
                addAction.addAction(gv,mEmojiEdit,72);
            }else if (i == 4){
                addAction.addAction(gv,mEmojiEdit,95);
            }
//            iaddAction.addAction(gridView1, contentEditText, 0);
//            iaddAction.addAction(gridView2, contentEditText, 23);23+23=46
//            iaddAction.addAction(gridView3, contentEditText, 46);46+23=69
//            iaddAction.addAction(gridView4, contentEditText, 72);72+23=95
//            iaddAction.addAction(gridView5, contentEditText, 95);
        }
        mEmojiPagerAdapter = new EmojiPagerAdapter(mVpList);
        mEmojiVp.setAdapter(mEmojiPagerAdapter);
        mEmojiVp.setCurrentItem(0);
        mEmojiVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pointPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 设置小圆点
     * @param position
     */
    private void pointPager(int position){
        for (int i = 0; i < 5; i++) {
            if (position == i){
                ((ImageView)mEmojiPoint.getChildAt(i)).setImageResource(R.mipmap.point_select);
            }else {
                ((ImageView)mEmojiPoint.getChildAt(i)).setImageResource(R.mipmap.point_no);
            }
        }
    }

    /**
     * 监听
     */
    private void initoper() {
        mEmojiBtn.setOnClickListener(this);
        mEmojiEdit.setOnTouchListener(this);
        mEmojiEdit.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainEmojiBtn://切换表情按钮

                toggle(v,true);

                break;


        }
    }

    /**
     * 为true隐藏键盘，显示表情面板
     * 为false弹出键盘，隐藏表情面板
     * @param v
     */
    private void toggle(View v,boolean flag) {
        if (flag) {//显示表情面板，隐藏布局
            if (!isEmoji) {
                UIUtils.hindInput(mEmojiEdit, mContext);
                mEmojiView.setVisibility(View.VISIBLE);
                mEmojiBtn.setImageResource(R.mipmap.emoji_select);
                isEmoji = true;
            }else {
                mEmojiView.setVisibility(View.GONE);
                //显示键盘
//                UIUtils.showInput(mEmojiEdit);
                mEmojiBtn.setImageResource(R.mipmap.emoji_gone);
                isEmoji = false;
            }
        }else {
            mEmojiBtn.setImageResource(R.mipmap.emoji_gone);
//            if (!isKeyBrodEmoji) {
                mEmojiView.setVisibility(View.GONE);
                isKeyBrodEmoji = true;
//            }else {
//                UIUtils.hindInput(v,mContext);
////                rlEmojiView.setVisibility(View.GONE);
//                isKeyBrodEmoji = false;
//            }

        }
    }

    /**
     * 设置edittext按下
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == mEmojiEdit.getId()) {
            toggle(mEmojiEdit, false);
        }
        return false;
    }

    /**
     * 获取textview的值
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //监听键盘发送
        if (actionId == EditorInfo.IME_ACTION_SEND){
            String text = v.getText().toString().trim();
            if (TextUtils.isEmpty(text)){
                Toast.makeText(mContext,text + "不能为空",Toast.LENGTH_SHORT).show();
            }else {
                //隐藏键盘
//                UIUtils.hindInput(mEmojiEdit,mContext);
                mEmojiEdit.setText("");
                //回调text
                mOnEmojiView.setText(text);
            }
        }
        return false;
    }


    public interface OnEmojiView{
        //获取textview内容
        void setText(String text);
        //获取表情

    }
}
