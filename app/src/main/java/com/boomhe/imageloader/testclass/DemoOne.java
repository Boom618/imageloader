package com.boomhe.imageloader.testclass;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by boomhe on 2017/7/13.
 *   线程安全的  “饿汉方式”实现的单例
 */

public class DemoOne {
    private static DemoOne INSTANCE;

    private DemoOne() {
    }

    // 但考虑到性能，一定不要简单粗暴地将其添加在如下位置
//    public static synchronized DemoOne getINSTANCE(){
    public static DemoOne getINSTANCE(){

        if (INSTANCE == null) {
            // 仅同步 实例化的代码
            synchronized (DemoOne.class){
                if (INSTANCE == null) {
                    INSTANCE = new DemoOne();
                }
            }
        }
        return INSTANCE;
    }

    private TextView textView;
    private Button temp;

    private void test(){
        if (textView instanceof View) {

        }

        for (int i = 0; i < 100; i++) {

        }
    }


}
