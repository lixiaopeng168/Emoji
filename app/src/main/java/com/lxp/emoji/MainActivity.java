package com.lxp.emoji;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.lxp.emoji.utli.UIUtils;
import com.lxp.emoji.utli.emoji.SmileyParser;

public class MainActivity extends AppCompatActivity {
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEmoji();
        initView();


    }



    private void initView() {

        //初始化view
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("初始化表情数据，点击进入发送表情");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EmojiActivity.class));
            }
        });

    }


    /**
     * 初始化表情
     */
    private void initEmoji() {
        // 初始化英寸数
        double density = getDensity();
        UIUtils.log("density:"+density);
        if (density > 0) {
            SmileyParser.SCREEN_INCH = density;
        }
        //如果优化将这个初始化到首页MainActivity
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        SmileyParser.init(getBaseContext(),metrics);
    }
    /**
     * 获取缩放倍数
     * @return
     */
    public double getDensity() {

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;

    }
}
