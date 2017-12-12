package com.example.ljw.basedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentField(getLayoutInflater().inflate(R.layout.activity_main, null));
        setTitleBarAndAction(R.string.title, R.mipmap.ic_launcher, R.color.colorAccent, R.string.left_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "左边按钮的点击事件", Toast.LENGTH_SHORT).show();
            }
        }, R.mipmap.ic_launcher, R.color.colorAccent, R.string.right_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "右边按钮的点击事件", Toast.LENGTH_SHORT).show();
            }
        }, R.mipmap.ic_launcher, R.color.colorAccent, R.string.very_right_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "最右按钮的点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
