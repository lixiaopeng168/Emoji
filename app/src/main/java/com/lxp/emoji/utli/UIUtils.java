package com.lxp.emoji.utli;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class UIUtils {




   /* private static Snackbar mSnackbar;
    public static void showSnackbar(View view,String text){
//        if (mSnackbar == null){
            mSnackbar = Snackbar.make(view,text, Toast.LENGTH_SHORT);
//        }
//        else {
//            mSnackbar.set
//            mSnackbar.setText(text);
//            mSnackbar.setDuration(Snackbar.LENGTH_SHORT);
//        }
        mSnackbar.show();
    }*/
    /**
     * log
     */
    public static void log(String text){
        Log.i("test",text);
    }
    /**
     * 强制隐藏键盘
     */
    public static void hindInput(View view, Context context){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    /**
     * 强制弹出键盘
     */
    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 显示键盘
     */
    public static void showInput(View view){
        InputMethodManager inputManager = (InputMethodManager) view
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

}
