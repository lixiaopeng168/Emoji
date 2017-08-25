package com.lxp.emoji.utli.emoji;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxp.emoji.R;




/**
 * 表情适配器
 *
 */
public class SimleyAdapter extends BaseAdapter
{
    private Context context;
    private int num;
    private SmileyParser smileyParser;



    public SimleyAdapter(Context context1, int num)
    {
        this.context = context1;
        this.num = num;
        this.smileyParser = SmileyParser.getInstance();
//        DisplayMetrics metrics = new DisplayMetrics();
//        metrics.widthPixels = 200;
//        metrics.heightPixels = 500;
//
//        smileyParser.init(context1,metrics);
    }



    final static class ViewHolder
    {
        TextView tvTitle;
    }



    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(
                    R.layout.emoji_expression_cell, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.image);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        CharSequence convertContent = null;
        if (smileyParser != null)
        {
            if (num == 1)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[position]);
            }
            else if (num == 2)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[position + 23]);
            }
            else if (num == 3)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[position + 46]);
            }
            else if (num == 4)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[position + 72]);
            }
            else if (num == 5)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[position + 95]);
            }
            if (position == 23)
            {
                convertContent = smileyParser
                        .addSmileySpans(SmileyParser.mSmileyTexts[105]);
            }
            viewHolder.tvTitle.setText(convertContent);
        }
        return view;
    }



    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        // return SmileyParser.mSmileyTexts.length;
        if (num == 5)
        {
            return 11;
        }
        return 24;
    }



    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }



    @Override
    public long getItemId(int position)
    {
        return position;
    }



}
