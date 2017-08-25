# Emoji 这个是表情封装面板，方便调用
=========================
### 使用介绍
>> 一.首先要初始化表情面板的信息
>>>```Java   
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
