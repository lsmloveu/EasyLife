package yt.com.lsmlibrary.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import yt.com.lsmlibrary.net.RxManager;

/**
 * author  : LSM
 * time    : 2018/01/24
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public abstract class BasePresenter <V extends BaseView ,M extends BaseModel> {
    protected  V mView;
    protected  M mModel;
    protected RxManager mRxManager = new RxManager();
    protected void attach(V mView,M mModel){
        if (this.mView==null){
            this.mView=mView;
        }
        if (this.mModel==null){
            this.mModel=mModel;
        }
    }

    protected void deatch() {
        if (mView!=null){
            mView=null;
        }
        mRxManager.unSubscribe();
    }
}
