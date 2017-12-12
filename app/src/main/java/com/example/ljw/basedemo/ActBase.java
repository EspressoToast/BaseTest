package com.example.ljw.basedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;


public class ActBase extends AppCompatActivity {


//    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        setContentView(R.layout.app_template_page);

    }

    @Override
    protected void onResume() {
        //可以做友盟统计
        super.onResume();
    }



    /**
     * @param titleText       标题
     * @param leftBtnListener 左边按钮点击事件, 默认为返回按钮
     */
    protected void setTitleBarAndAction(@StringRes int titleText, OnClickListener leftBtnListener) {
        setTitleBarAndAction(titleText, leftBtnListener, null);
    }

    /**
     * @param titleText           标题
     * @param leftBtnListener     左边按钮点击事件, 默认为返回按钮
     * @param subRightBtnListener 右边按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText, OnClickListener leftBtnListener, OnClickListener subRightBtnListener) {
        setTitleBarAndAction(titleText, R.drawable.app_title_back_selector, leftBtnListener, 0, subRightBtnListener);
    }

    /**
     * @param titleText           标题
     * @param leftResource        左边图片资源
     * @param leftBtnListener     左边按钮点击事件
     * @param subRightResource    右边图片资源
     * @param subRightBtnListener 右边按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText,
                                        @DrawableRes int leftResource, OnClickListener leftBtnListener,
                                        @DrawableRes int subRightResource, OnClickListener subRightBtnListener) {
        setTitleBarAndAction(titleText, leftResource, 0, 0, leftBtnListener, subRightResource, 0, 0, subRightBtnListener);
    }

    /**
     * @param titleText                 标题
     * @param leftResource              左边图片资源
     * @param leftResourceTextColor     左边文字颜色
     * @param leftText                  左边文字
     * @param leftBtnListener           左边按钮点击事件
     * @param subRightResource          右边图片资源
     * @param subRightResourceTextColor 右边文字颜色
     * @param subRightText              右边文字
     * @param subRightBtnListener       右边按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText,
                                        @DrawableRes int leftResource, @ColorRes int leftResourceTextColor, @StringRes int leftText, OnClickListener leftBtnListener,
                                        @DrawableRes int subRightResource, @ColorRes int subRightResourceTextColor, @StringRes int subRightText, OnClickListener subRightBtnListener) {
        setTitleBarAndAction(titleText, leftResource, leftResourceTextColor, leftText, leftBtnListener,
                subRightResource, subRightResourceTextColor, subRightText, subRightBtnListener,
                0, 0, 0, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                    }
                });
    }

    /**
     * @param titleText                 标题
     * @param leftBtnListener           左边按钮点击事件
     * @param subRightResource          右边图片资源
     * @param subRightResourceTextColor 右边文字颜色
     * @param subRightText              右边文字
     * @param subRightBtnListener       右边按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText,
                                        OnClickListener leftBtnListener,
                                        @DrawableRes int subRightResource, @ColorRes int subRightResourceTextColor, @StringRes int subRightText, OnClickListener subRightBtnListener) {
        setTitleBarAndAction(titleText, leftBtnListener,
                subRightResource, subRightResourceTextColor, subRightText, subRightBtnListener,
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击事件
                    }
                });
    }

    /**
     * @param titleText                 标题
     * @param leftBtnListener           左边按钮点击事件
     * @param subRightResource          右边图片资源
     * @param subRightResourceTextColor 右边文字颜色
     * @param subRightText              右边文字
     * @param subRightBtnListener       右边按钮点击事件
     * @param rightBtnListener          最右侧按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText,
                                        OnClickListener leftBtnListener,
                                        @DrawableRes int subRightResource, @ColorRes int subRightResourceTextColor, @StringRes int subRightText, OnClickListener subRightBtnListener,
                                        OnClickListener rightBtnListener) {
        setTitleBarAndAction(titleText, 0, 0, 0, leftBtnListener,
                subRightResource, subRightResourceTextColor, subRightText, subRightBtnListener,
                0, 0, 0, rightBtnListener);
    }

    /**
     * @param titleText                 标题
     * @param leftResource              左边图片资源
     * @param leftResourceTextColor     左边文字颜色
     * @param leftText                  左边文字
     * @param leftBtnListener           左边按钮点击事件
     * @param subRightResource          右边图片资源
     * @param subRightResourceTextColor 右边文字颜色
     * @param subRightText              右边文字
     * @param subRightBtnListener       右边按钮点击事件
     * @param rightResource             最右侧按钮图片资源
     * @param rightResourceTextColor    最右侧按钮文字颜色
     * @param rightText                 最右侧按钮文字
     * @param rightBtnListener          最右侧按钮点击事件
     */
    protected void setTitleBarAndAction(@StringRes int titleText,
                                        @DrawableRes int leftResource, @ColorRes int leftResourceTextColor, @StringRes int leftText, OnClickListener leftBtnListener,
                                        @DrawableRes int subRightResource, @ColorRes int subRightResourceTextColor, @StringRes int subRightText, OnClickListener subRightBtnListener,
                                        @DrawableRes int rightResource, @ColorRes int rightResourceTextColor, @StringRes int rightText, OnClickListener rightBtnListener) {
        alabSetBarTitleText(titleText);
        setLeftBtnAction(leftResource, leftResourceTextColor, leftText, leftBtnListener);
        setSubRightBtnAction(subRightResource, subRightResourceTextColor, subRightText, subRightBtnListener);
        setRightBtnAction(rightResource, rightResourceTextColor, rightText, rightBtnListener);
    }

    private void setLeftBtnAction(@DrawableRes int leftResource, @ColorRes int leftResourceTextColor,
                                  @StringRes int leftText, OnClickListener leftBtnListener) {
        TextView leftBtn = alabGetButtonLeft();
        if (0 != leftResource) {
            leftBtn.setCompoundDrawablesWithIntrinsicBounds(leftResource, 0, 0, 0);
        }

        if (0 != leftResourceTextColor) {
            leftBtn.setTextColor(ContextCompat.getColor(this, leftResourceTextColor));
        }

        if (0 != leftText) {
            leftBtn.setText(leftText);
        }

        if (null != leftBtnListener) {
            leftBtn.setVisibility(View.VISIBLE);
            leftBtn.setOnClickListener(leftBtnListener);
        }
    }

    protected void setRightBtnAction(@DrawableRes int rightResource, @ColorRes int rightResourceTextColor,
                                     @StringRes int rightText, OnClickListener rightBtnListener) {
        TextView rightBtn = alabGetButtonRight();
        if (0 != rightResource || 0 != rightText) {
            rightBtn.setCompoundDrawablesWithIntrinsicBounds(rightResource, 0, 0, 0);
            rightBtn.setCompoundDrawablePadding(3);
            if (0 != rightText) {
                rightBtn.setText(rightText);
            }
        }

        if (0 != rightResourceTextColor) {
            rightBtn.setTextColor(ContextCompat.getColor(this, rightResourceTextColor));
        }

        if (null != rightBtnListener) {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setOnClickListener(rightBtnListener);
        } else {
            rightBtn.setVisibility(View.INVISIBLE);
            rightBtn.setOnClickListener(null);
        }
    }


    protected void setSubRightBtnAction(@DrawableRes int subRightResource, @ColorRes int subRightResourceTextColor,
                                        @StringRes int subRightText, OnClickListener subRightBtnListener) {
        TextView subRightBtn = alabGetButtonSubRight();
        if (0 != subRightResource) {
            subRightBtn.setCompoundDrawablesWithIntrinsicBounds(subRightResource, 0, 0, 0);
        }

        if (0 != subRightResourceTextColor) {
            subRightBtn.setTextColor(ContextCompat.getColor(this, subRightResourceTextColor));
        }

        if (0 != subRightText) {
            subRightBtn.setText(subRightText);
        }

        if (null != subRightBtnListener) {
            subRightBtn.setVisibility(View.VISIBLE);
            subRightBtn.setOnClickListener(subRightBtnListener);
        }
    }

    private TextView alabGetButtonLeft() {
        return (TextView) findViewById(R.id.title_back);
    }

    protected TextView alabGetButtonRight() {
        return (TextView) findViewById(R.id.title_right);
    }

    private TextView alabGetButtonSubRight() {
        return (TextView) findViewById(R.id.title_sub_right);
    }

    /**
     * Title: hideTitleBar
     * Description: 隐藏标题栏
     */
    protected void hideTitleBar() {
        RelativeLayout titleBar = (RelativeLayout) findViewById(R.id.id_title_bar);
        titleBar.setVisibility(View.GONE);
    }


    /**
     * Title: showTitleDivider
     * Description: 显示标题栏的水平分割线
     */
    protected void showTitleDivider() {
        ImageView titleDivider = (ImageView) findViewById(R.id.id_title_divider);
        titleDivider.setVisibility(View.VISIBLE);
    }

    protected String getResourceStr(int id) {
        return getResources().getString(id);
    }

    /**
     * 设置主内容区域的View
     *
     * @param v view
     */
    protected void setContentField(View v) {
        /* 底层 */
        LinearLayout llContent = (LinearLayout) findViewById(R.id.llContent1);

        llContent.removeAllViews();
        llContent.addView(v);

        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this);
        initData();
        findView(v);
        setOnClickEvent();
    }

    /**
     * 设置主内容区域的layoutRes
     *
     * @param layoutResId layoutid
     */
    protected void setContentField(int layoutResId) {
        /* 底层 */
        LinearLayout llContent = (LinearLayout) findViewById(R.id.llContent1);

        llContent.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResId, null);
        llContent.addView(v);

        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ButterKnife.bind(this);

        initData();
        findView(v);
        setOnClickEvent();
    }

    /**
     * Title: initData
     * Description: 初始化数据， 在加载view以前
     */
    protected void initData() {
    }

    /**
     * Title: findView
     * Description: 加载view
     *
     * @param v view
     */
    protected void findView(View v) {
    }

    /**
     * Title: setOnClickEvent
     * Description: 设置button点击事件
     */
    protected void setOnClickEvent() {
    }


    protected void alabSetBarTitleText(String titleText) {
        TextView tv = (TextView) findViewById(R.id.title_txt);
        if (null != tv) {
            tv.setText(titleText);
        }
    }

    protected void alabSetBarTitleText(@StringRes int resourceId) {
        TextView tv = (TextView) findViewById(R.id.title_txt);
        if (null != tv && 0 != resourceId) {
            tv.setText(resourceId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
