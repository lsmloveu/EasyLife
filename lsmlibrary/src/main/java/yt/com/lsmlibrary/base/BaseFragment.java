package yt.com.lsmlibrary.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import yt.com.lsmlibrary.attr.base.DynamicAttr;
import yt.com.lsmlibrary.loader.SkinInflaterFactory;
import yt.com.lsmlibrary.skininterface.IDynamicNewView;


/**
 * author  : LSM
 * time    : 2017/12/08
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */
public class BaseFragment extends Fragment implements IDynamicNewView {

    private IDynamicNewView mIDynamicNewView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIDynamicNewView = (IDynamicNewView) context;
        } catch (ClassCastException e) {
            mIDynamicNewView = null;
        }
    }

    @Override
    public final void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if (mIDynamicNewView == null) {
            throw new RuntimeException("IDynamicNewView should be implements !");
        } else {
            mIDynamicNewView.dynamicAddView(view, pDAttrs);
        }
    }

    @Override
    public final void dynamicAddView(View view, String attrName, int attrValueResId) {
        mIDynamicNewView.dynamicAddView(view, attrName, attrValueResId);
    }

    @Override
    public final void dynamicAddFontView(TextView textView) {
        mIDynamicNewView.dynamicAddFontView(textView);
    }

    public final SkinInflaterFactory getSkinInflaterFactory() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getInflaterFactory();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
    }

    protected void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllView(viewGroup.getChildAt(i));
            }
            removeViewInSkinInflaterFactory(v);
        } else {
            removeViewInSkinInflaterFactory(v);
        }
    }

    private void removeViewInSkinInflaterFactory(View v) {
        if (getSkinInflaterFactory() != null) {
            //此方法用于Activity中Fragment销毁的时候，移除Fragment中的View
            getSkinInflaterFactory().removeSkinView(v);
        }
    }
}
