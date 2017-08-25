package com.lxp.emoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lxp.emoji.utli.emoji.SmileyParser;


public class EmojiActivity extends AppCompatActivity implements EmojiRelativaLayout.OnEmojiView {
    private TextView tv;
    private EmojiRelativaLayout mEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        initView();
        initOper();


    }

    private void initOper() {
        mEmoji = (EmojiRelativaLayout) findViewById(R.id.emojiView);
        mEmoji.setOnEmojiView(this);
    }

    private void initView() {

        //初始化view
        tv = (TextView) findViewById(R.id.emojiTv);
        mEmoji = (EmojiRelativaLayout) findViewById(R.id.emojiView);


    }


    @Override
    public void setText(String text) {
//        SmileyParser.getInstance().addSmileySpans(text);
        tv.setText( SmileyParser.getInstance().addSmileySpans(text));
    }
}
