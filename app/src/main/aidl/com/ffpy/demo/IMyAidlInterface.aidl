// IMyAidlInterface.aidl
package com.ffpy.demo;
// Declare any non-default types here with import statements
import com.ffpy.demo.Beauty;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int arg1, int arg2);


    void show();

    /**
       * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
         */
   Beauty getBeauty();


}
