package com.ffpy.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //怎么进行全屏设置
        setFullScreen();
        setContentView(R.layout.activity_lunch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置全屏，进行弹出
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部像弹框一样弹出
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               Intent intent= new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);
            }
        });
        Log.i("ccc","onCreate");
    }






    //设置全屏
    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ccc","onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ccc","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ccc","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ccc","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ccc","onDestory");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ccc","onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
