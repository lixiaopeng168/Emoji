# Emoji 这个是表情封装面板，方便调用
=========================
### 我们先看一下效果
![](http://img.blog.csdn.net/20170606120403645?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGk1Njg1OTE4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
### 使用介绍
一.首先要初始化表情面板的信息
```Java   
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
```
二.然后使用的时候要xml面板替换为
```Java
 mEmoji = (EmojiRelativaLayout) findViewById(R.id.emojiView);
 ```
三.实现接口回调
```Java
mEmoji.setOnEmojiView(this);
```
四.在回调方法中调用
```Java
tv.setText( SmileyParser.getInstance().addSmileySpans(text));
```
----------------------------------------------------------------------------
# 使用注意：请确保你的assets目录下有图片，请确保你是string.xml文件中对应的array数组和图片名称一样，如果不一样是执行不了的。

### 第一次写GitHub博客，有点高大尚的感觉，也有点兴奋。
### 时间不会等你，加油吧，骚年！
