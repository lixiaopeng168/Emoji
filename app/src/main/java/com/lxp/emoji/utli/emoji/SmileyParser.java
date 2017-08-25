package com.lxp.emoji.utli.emoji;

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;

import com.lxp.emoji.R;
import com.lxp.emoji.utli.UIUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 表情处理类：使用示例
 * TextView tv = (TextView) findViewById(R.id.tvShow); // 构建处理对象
 * SmileyParser.init(this);
 * SmileyParser parser =SmileyParser.getInstance();
 * String[] smileTexts =SmileyParser.getSmileTexts();
 * int smileNum =smileTexts.length;
 * String text ="32131[]";
 * for (int i = 0; i < smileNum; i++){
 * text += smileTexts[i];
 * }
 * tv.setText(parser.addSmileySpans([]));
 */
public class SmileyParser {
	//默认从0
	public static double SCREEN_INCH = 0;
	/*** 表情大小 */
	private static int SMILE_ICON_SIZE = (int) (15 * SCREEN_INCH - 21);
	/*** 表情文本 */
	public static String[] mSmileyTexts;
	/*** 表情图标 */
	public static Bitmap[] mSmileyIcons;
	/*** 表情名称 、图标的映射 */
	private final HashMap<String, Bitmap> mSmileyToRes;

	private final Pattern mPattern;
	private final Context mContext;
	private static SmileyParser sInstance;

	/***
	 * 取得表情处理类实例
	 * 
	 * @return
	 */
	public static SmileyParser getInstance() {

		return sInstance;
	}

	/***
	 * 初始化表情解析器（将文本解析为表情图标显示，如[微笑] 显示微笑图标）
	 * 
	 * @param context
	 */
	public static void init(Context context, DisplayMetrics metrics) {
		sInstance = new SmileyParser(context, metrics);
	}

	/***
	 * 构造函数
	 * 
	 * @param context
	 */
	private SmileyParser(Context context, DisplayMetrics metrics) {
        float density = metrics.density;
		UIUtils.log("Smiley:----density--"+metrics.density+"--metrics.widthPixels---"+metrics.widthPixels);
        int mWidth = metrics.widthPixels;
        if (density == 0.75)
        {
            SMILE_ICON_SIZE = 20;
        } else if (density == 1.0)
        {
            SMILE_ICON_SIZE = 25;
        } else
        {
            if (mWidth < 640)
                SMILE_ICON_SIZE = 20;
            else
            {
                SMILE_ICON_SIZE = 32;
            }
        }
        mContext = context;
        //通过屏幕大小匹配表情图片大小
        int width=0,height=0;
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        height=dm.heightPixels;
        width=dm.widthPixels;
        if(width!=0&&height!=0){
            SMILE_ICON_SIZE=(int) (15*SCREEN_INCH-31);
        }
        Log.i("Smiley", SCREEN_INCH+"---SMILE_ICON_SIZE---"+SMILE_ICON_SIZE);
        // 表情文本 读取arrays.xml
        mSmileyTexts = mContext.getResources().getStringArray(
                R.array.default_smiley_texts);
        // 构建表情文本和表情图标之间的映射
        mSmileyToRes = buildSmileyToRes();
        // 构建正则表达式
        mPattern = buildPattern();
	}

	/**
	 * 根据文本替换成图片
	 */
	public CharSequence addSmileySpans(CharSequence text)
	{
		if (text == null)
		{
			text = "";
		}
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		Matcher matcher = mPattern.matcher(text);
		while (matcher.find())
		{
			Bitmap icon = mSmileyToRes.get(matcher.group());
			builder.setSpan(new ImageSpan(mContext, icon), matcher.start(),
					matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}



	/***
	 * 构建表情文本和表情图标之间的映射
	 *
	 * @return HashMap<String, Bitmap>
	 */

	private HashMap<String, Bitmap> buildSmileyToRes()
	{

		// 表情图标名称
		String[] smileyIconNames = mContext.getResources().getStringArray(
				R.array.default_smiley_names);
		System.out.println();
		// 判断 图标数组 与 图标名称数组 是否匹配
		if (smileyIconNames.length != mSmileyTexts.length)
		{
			throw new IllegalStateException("Smiley resource ID/text mismatch");
		}

		int iconNum = smileyIconNames.length;
		mSmileyIcons = new Bitmap[iconNum];
		// 图标和名称映射的表
		HashMap<String, Bitmap> smileyToRes = new HashMap<String, Bitmap>(
				iconNum);
		for (int i = 0; i < iconNum; i++)
		{
			// 构建图标路径
			String iconFileName = "smiley/" + smileyIconNames[i] + ".png";
			System.err.println(smileyIconNames[i] + ".png");
			// 从Assets中读取图片
			Bitmap iconBitmap = getImageFromAssets(mContext, iconFileName);
			if (iconBitmap == null)
				continue;
			// 图片任意形状的放大缩小
			iconBitmap = ZoomToFixShape(iconBitmap, SMILE_ICON_SIZE,
					SMILE_ICON_SIZE);
			mSmileyIcons[i] = iconBitmap;
			smileyToRes.put(mSmileyTexts[i], mSmileyIcons[i]);
		}

		return smileyToRes;
	}



	/** 构建正则表达式 */
	private Pattern buildPattern()
	{
		StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);

		// Build a regex that looks like (:-)|:-(|...), but escaping the smilies
		// properly so they will be interpreted literally by the regex matcher.
		patternString.append('(');
		for (String s : mSmileyTexts)
		{
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		// Replace the extra '|' with a ')'
		patternString.replace(patternString.length() - 1,
				patternString.length(), ")");

		return Pattern.compile(patternString.toString());
	}



	/***
	 * 从Assets中读取图片 范例： Bitmap bmp = getImageFromAssetsFile("kugame/test.png");
	 *
	 * @param context
	 * @param fileName
	 * @return
	 */
	static Bitmap getImageFromAssets(Context context, String fileName)
	{
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try
		{
			InputStream is = am.open(fileName);
			image = decodeStream(is);
			is.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return image;
	}



	/**
	 * 解码图片流，据说可以避免内存溢出：bitmap size exceeds VM budget
	 *
	 * @param picStream
	 * @return
	 */
	public static Bitmap decodeStream(InputStream picStream)
	{
		Bitmap bitmap = null;
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = 4;
		bitmap = BitmapFactory.decodeStream(picStream);
		return bitmap;
	}



	/**
	 * 图片任意形状的放大缩小
	 */
	static public Bitmap ZoomToFixShape(Bitmap pic1, int w, int h)
	{
		Bitmap tempBitmap = null;
		int bitH = pic1.getHeight();
		int bitW = pic1.getWidth();
		Matrix mMatrix = new Matrix();

		float scoleW = (float) w / (float) bitW;
		float scoleH = (float) h / (float) bitH;

		mMatrix.reset();
		mMatrix.postScale(scoleW, scoleH);
		tempBitmap = Bitmap.createBitmap(pic1, 0, 0, bitW, bitH, mMatrix, true);
		// pic1.recycle();
		return tempBitmap;

	}



	/** 对外接口：获取表情文本数组 */
	public static String[] getSmileTexts()
	{
		return mSmileyTexts;
	}



	/** 对外接口：获取表情图标数组 */
	public static Bitmap[] getSmileIcons()
	{
		return mSmileyIcons;
	}
}
