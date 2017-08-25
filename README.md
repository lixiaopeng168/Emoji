# Emoji 这个是表情封装面板，方便调用
=========================
### 我们先看一下效果
![](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503664568029&di=8e984e93db25076ca80705bf45198c59&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D899aec235382b2b7b392318759c4a19a%2Fd1a20cf431adcbef5ed70a21a6af2edda3cc9f31.jpg)
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
