package yt.com.easylife.ready;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import yt.com.easylife.R;
import yt.com.easylife.bean.DriverBean;
import yt.com.easylife.common.Api;
import yt.com.easylife.common.Constant;
import yt.com.easylife.main.MainActivity;
import yt.com.easylife.utils.ShareUtils;
import yt.com.lsmlibrary.base.BaseActivity;
import yt.com.lsmlibrary.net.HttpManager;
import yt.com.lsmlibrary.net.RxHelper;

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initOptions() {

    }

    public void qq(View view) {
        ShareUtils.shareWeb(this, Constant.url, Constant.title
                , Constant.text, Constant.imageurl, R.drawable.icon_logo_share, SHARE_MEDIA.QQ
        );
    }

    public void weiXin(View view) {
        ShareUtils.shareWeb(this, Constant.url, Constant.title
                , Constant.text, Constant.imageurl, R.drawable.icon_logo_share, SHARE_MEDIA.WEIXIN
        );
    }

    public void weixinCircle(View view) {
        ShareUtils.shareWeb(this, Constant.url, Constant.title
                , Constant.text, Constant.imageurl, R.drawable.icon_logo_share, SHARE_MEDIA.WEIXIN_CIRCLE
        );
    }

    public void sina(View view) {
        ShareUtils.shareWeb(this, Constant.url, Constant.title
                , Constant.text, Constant.imageurl, R.drawable.icon_logo_share, SHARE_MEDIA.SINA
        );
    }

    public void Qzone(View view) {
        ShareUtils.shareWeb(this, Constant.url, Constant.title
                , Constant.text, Constant.imageurl, R.drawable.icon_logo_share, SHARE_MEDIA.QZONE
        );
    }


    public void qqLogin(View view) {
        authorization(SHARE_MEDIA.QQ);
    }

    public void weiXinLogin(View view) {
        authorization(SHARE_MEDIA.WEIXIN);
    }

    public void sinaLogin(View view) {
        authorization(SHARE_MEDIA.SINA);
    }

    public void requestInterface(View view) {
        HttpManager
                .getInstance()
                .getApiService(Api.class,Constant.baseUrl)
                .toLogin("18871987520","123456","863513036641247")
                .compose(RxHelper.<DriverBean>rxSchedulerHelper())
                .subscribe(new Observer<DriverBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DriverBean driverBean) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void intoMain(View view) {
        ActivityUtils.startActivity(LoginActivity.this,MainActivity.class);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.iTag(getTAG(), "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.iTag(getTAG(),"onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();
                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.iTag(getTAG(),"onError " + "授权失败"+throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtils.iTag(getTAG(),"onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
