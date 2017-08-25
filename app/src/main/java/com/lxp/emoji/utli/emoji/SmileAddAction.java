package com.lxp.emoji.utli.emoji;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

/**
 * 表情动作
 * cn.edu.tsinghua.emba.android.view.find.teacherfind.SmileAddAction
 * @author jafantansy <br/>
 * create at 2014-8-1 下午4:50:30
 */
public class SmileAddAction {
  
  public void addAction(GridView iGridView, final EditText contentEditText, final int postion){
    iGridView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if (arg2 != 23) {
          CharSequence convertContent = SmileyParser.getInstance().addSmileySpans(SmileyParser.mSmileyTexts[arg2 + postion]);
          contentEditText.append(convertContent);
          contentEditText.append(" ");
        }
        if (arg2 == 23) {
          deleteString(contentEditText);
        }
      }
    });
  }
  public static void deleteString(EditText editext) {

    String getString = editext.getText().toString();
    if (getString.length() > 0) {
      String lastString="";
      if(getString.length()>1){
        
        lastString = getString.substring(getString.toString().length() - 2, getString.toString().length());
      }else if(getString.length()==1){
         lastString = getString.substring(getString.toString().length() -1, getString.toString().length());
      }
      boolean tag = true;
      while (tag && getString.length() > 0) {
        if (lastString.equals("] ")) {
          if (getString.length() == 0) {
            Log.e("fan", "true");
            tag = false;
          } else {
            Log.e("fan", "true删除");
            try {
              Thread.sleep(20);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            getString = getString.substring(0, getString.toString().length() - 1);
            if (getString.length() > 0
                && getString.substring(getString.toString().length() - 1, getString.toString().length()).equals("[")) {
              tag = false;
              getString = getString.substring(0, getString.toString().length() - 1);
            }
          }
        } else {
          tag = false;
          getString = getString.substring(0, getString.toString().length() - 1);
        }

      }
      CharSequence convertContent = SmileyParser.getInstance().addSmileySpans(getString);
      editext.setText(convertContent);
      editext.setSelection(convertContent.length());// 将光标移至文字末尾
    }

  }

}

