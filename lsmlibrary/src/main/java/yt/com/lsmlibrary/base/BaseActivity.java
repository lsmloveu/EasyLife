package yt.com.lsmlibrary.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import yt.com.lsmlibrary.attr.base.DynamicAttr;
import yt.com.lsmlibrary.common.SkinConfig;
import yt.com.lsmlibrary.loader.SkinInflaterFactory;
import yt.com.lsmlibrary.loader.SkinManager;
import yt.com.lsmlibrary.skininterface.IDynamicNewView;
import yt.com.lsmlibrary.skininterface.ISkinUpdate;
import yt.com.lsmlibrary.utils.SkinResourcesUtils;

/**
 * author  : LSM
 * time    : 2017/12/08
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity  implements ISkinUpdate, IDynamicNewView,BaseView {

    private SkinInflaterFactory mSkinInflaterFactory;
    private String TAG ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        changeStatusColor();
        ButterKnife.inject(this);
        this.initPresenter();
        this.initView();
        this.initOptions();
    }

    public String getTAG() {
        return getClass().getName();
    }

    //子类必须实现的方法
    //加载布局
    protected abstract int getLayout();
    //presenter实现
    protected abstract void initPresenter();
    //view初始化
    protected abstract void initView();
    //其它操作
    protected abstract void initOptions();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    public void changeStatusColor() {
        if (!SkinConfig.isCanChangeStatusColor()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = SkinResourcesUtils.getColorPrimaryDark();
            if (color != -1) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(SkinResourcesUtils.getColorPrimaryDark());
            }
        }
    }

    @Override
    public void onThemeUpdate() {
        LogUtils.iTag(TAG, "onThemeUpdate");
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }

    public SkinInflaterFactory getInflaterFactory() {
        return mSkinInflaterFactory;
    }
    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    @Override
    public void dynamicAddView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    @Override
    public void dynamicAddFontView(TextView textView) {
        mSkinInflaterFactory.dynamicAddFontEnableView(this, textView);
    }
}

