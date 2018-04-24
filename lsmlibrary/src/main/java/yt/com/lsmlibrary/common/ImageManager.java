package yt.com.lsmlibrary.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * author  : LSM
 * time    : 2017/12/08
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 * 简书    : http://www.jianshu.com/u/644036b17b6f
 */

public class ImageManager {
    private static ImageManager imageManager;
    private ImageManager(){

    }
    public static ImageManager getInstance() {
        if (imageManager==null) {
            imageManager=new ImageManager();
        }
        return imageManager;
    }
    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param placeholderResourceId
     * @param errorResourceId
     * @param targetImageView
     */
    public void loadImage(Context context, Object path, int placeholderResourceId, int errorResourceId, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .placeholder(placeholderResourceId)
                .error(errorResourceId)
                .into(targetImageView);
    }
    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param targetImageView
     */
    public void loadImageAsCircle(Context context, Object path, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(targetImageView);
    }
    /**
     * 加载圆角图片
     *
     * @param context
     * @param path
     * @param targetImageView
     */
    public void loadImageAsRound(Context context, Object path, ImageView targetImageView,int radius,int margin) {
        Glide.with(context)
                .load(path)
                .bitmapTransform(new RoundedCornersTransformation(context,radius,margin))
                .into(targetImageView);
    }
    /**
     * 加载gif图片
     *
     * @param context
     * @param path
     * @param targetImageView
     */
    public void loadImageAsGif(Context context, Object path, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .asGif()
                .into(targetImageView);
    }


    /**
     * 加载高斯模糊图片
     *
     * @param context
     * @param path
     * @param placeholderResourceId
     * @param errorResourceId
     * @param targetImageView
     * @param radius    设置模糊度(在0.0到25.0之间)，默认”25";
     * @param sampling  图片缩放比例,默认“1”。
     */
    public void loadImageAsBlur(Context context, Object path, int placeholderResourceId, int errorResourceId, ImageView targetImageView,int radius,int sampling) {
        Glide.with(context)
                .load(path)
                .placeholder(placeholderResourceId)
                .error(errorResourceId)
                .bitmapTransform(new BlurTransformation(context,radius,sampling))
                .into(targetImageView);
    }

    /**
     * 加载图片，获取bitmap数据
     * @param context
     * @param path
     * @return
     */
    public Bitmap loadImage(Context context, Object path) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return bitmap;
    }
}
