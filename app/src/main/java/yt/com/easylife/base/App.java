package yt.com.easylife.base;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;

import yt.com.easylife.R;
import yt.com.easylife.common.ChangeSkinManager;
import yt.com.easylife.ready.SplashActivity;
import yt.com.lsmlibrary.base.BaseApplication;

/**
 * author  : LSM
 * time    : 2017/11/29
 * function: 启动相关初始化
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);//初始化sdk
        showWindowForm();
    }
    //设置悬浮窗体
    public void showWindowForm() {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.love2);

        TextView textView=new TextView(getApplicationContext());
        textView.setText("花花世界");
        FloatWindow
                .with(getApplicationContext())
                .setView(textView)
                .setWidth(Screen.width,0.1f)
                .setHeight(Screen.width,0.1f)
                .setX(Screen.width,0.7f)
                .setY(Screen.height,0.02f)
                .setTag("first")
                .setMoveType(MoveType.slide)
                .setMoveStyle(500, new AccelerateInterpolator())
                .setFilter(false,SplashActivity.class)
                .build();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("666");
            }
        });
    }
    //各个平台的配置
    {
        //微信
        PlatformConfig.setWeixin("wxafc7d178da2f6404", "810ed4dc42b1efb8865db0058a0023eb");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com/sina2/callback");
        //QQ
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
