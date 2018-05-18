package yt.com.easylife.ready;

import android.Manifest;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import yt.com.easylife.R;
import yt.com.easylife.bean.YiYanBeen;
import yt.com.easylife.common.Api;
import yt.com.easylife.common.ChangeSkinManager;
import yt.com.easylife.common.Constant;
import yt.com.easylife.widget.VerticalTextView;
import yt.com.lsmlibrary.base.BaseActivity;
import yt.com.lsmlibrary.net.HttpManager;
import yt.com.lsmlibrary.net.RxHelper;

/**
 * author  : LSM
 * time    : 2017/12/08
 * function: 启动页
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */
public class SplashActivity extends BaseActivity {

    @InjectView(R.id.im_logo)
    ImageView imLogo;
    @InjectView(R.id.vt_show)
    VerticalTextView vtShow;
    private int mTime = 5;
    private int ttfNum = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        getYiYanApi();
    }

    @Override
    protected void initOptions() {
        getPermission();
    }


    //android  6.0权限申请
    public void getPermission() {
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            LogUtils.iTag(getTAG(), "App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
                        doOthers();
                    }
                });
    }


    public void doOthers() {
        //检查更新

        //启动跳转
        Observable.timer(mTime, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        ActivityUtils.startActivity(SplashActivity.this, LoginActivity.class);
                        finish();
                        ActivityUtils.finishToActivity(LoginActivity.class, true);
                    }
                });
    }

    public void getYiYanApi() {
        HttpManager
                .getInstance()
                .getApiService(Api.class, Constant.baseYiYanUrl)
                .getYiYan("json","utf-8")
                .compose(RxHelper.<YiYanBeen>rxSchedulerHelper())
                .subscribe(new Observer<YiYanBeen>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull YiYanBeen yiYan) {
                        vtShow.setText(yiYan.getHitokoto());
                        vtShow.postInvalidate();
                        LogUtils.iTag("http",yiYan);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        vtShow.setText(getResources().getString(R.string.yiyan));
                        LogUtils.iTag("http",e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.im_logo)
    public void onClick() {
        ttfNum++;
        if (ttfNum < 34) {
            ChangeSkinManager.getInstance().fontsChange(ttfNum + "");
            LogUtils.iTag(getTAG(), "当前所用字体：" + ttfNum);
        }
    }
}
