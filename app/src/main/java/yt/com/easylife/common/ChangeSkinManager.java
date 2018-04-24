package yt.com.easylife.common;

import com.blankj.utilcode.util.LogUtils;

import yt.com.lsmlibrary.loader.SkinManager;
import yt.com.lsmlibrary.skininterface.SkinLoaderListener;

/**
 * author  : LSM
 * time    : 2017/12/08
 * function: 换肤相关
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public class ChangeSkinManager {
    private static ChangeSkinManager changeSkinManager;
    private ChangeSkinManager() {

    }
    public static ChangeSkinManager getInstance() {
        if(changeSkinManager==null) {
            changeSkinManager=new ChangeSkinManager();
        }
        return changeSkinManager;
    }

    public void skinChange(String skinName) {
        SkinManager.getInstance().loadSkin(skinName+".skin",
                new SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        LogUtils.iTag("SkinLoaderListener", "正在切换中");
                    }

                    @Override
                    public void onSuccess() {
                        LogUtils.iTag("SkinLoaderListener", "切换成功");
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        LogUtils.iTag("SkinLoaderListener", "切换失败:" + errMsg);
                    }

                    @Override
                    public void onProgress(int progress) {
                        LogUtils.iTag("SkinLoaderListener", "皮肤文件下载中:" + progress);

                    }
                }
        );
    }

    public void fontsChange(String fontsName) {
        SkinManager.getInstance().loadFont(fontsName+".ttf");
    }
    public void backChange() {
        SkinManager.getInstance().restoreDefaultTheme();
    }
}
