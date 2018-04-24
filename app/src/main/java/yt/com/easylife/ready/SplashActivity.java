package yt.com.easylife.ready;

import android.Manifest;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import yt.com.easylife.R;
import yt.com.easylife.common.ChangeSkinManager;
import yt.com.lsmlibrary.base.BaseActivity;

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
    @InjectView(R.id.tv_one)
    TextView tvOne;
    private int mTime=3;
    private int ttfNum=0;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {

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
//        rxPermissions
//                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_CALENDAR,
//                        Manifest.permission.READ_CALL_LOG,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.READ_SMS,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.CALL_PHONE,
//                        Manifest.permission.SEND_SMS)
//                .subscribe(new Consumer<Permission>() {
//                    @Override
//                    public void accept(Permission permission) throws Exception {
//                        if (permission.granted) {
//                            // 用户已经同意该权限
//                            LogUtils.iTag(getTAG(), permission.name + " is granted.");
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                            LogUtils.iTag(getTAG(), permission.name + " is denied. More info should be provided.");
//                        } else {
//                            // 用户拒绝了该权限，并且选中『不再询问』
//                            LogUtils.iTag(getTAG(), permission.name + " is denied.");
//                        }
//                    }
//                });
    }


    public void doOthers() {
        //检查更新

        //启动跳转
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                            ActivityUtils.startActivity(SplashActivity.this,LoginActivity.class);
                            finish();
                        ActivityUtils.finishToActivity(LoginActivity.class,true);
                    }
                });
    }

    @OnClick(R.id.im_logo)
    public void onClick() {
        ttfNum++;
        if (ttfNum<34) {
            ChangeSkinManager.getInstance().fontsChange(ttfNum + "");
            LogUtils.iTag(getTAG(), "当前所用字体："+ttfNum);
        }
    }
}
