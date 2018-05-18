package yt.com.easylife.common;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yt.com.easylife.bean.MusicBean;
import yt.com.easylife.bean.YiYanBeen;

/**
 * author  : LSM
 * time    : 2017/12/04
 * function:
 * e-mail  : lsmloveu@126.com
 * github  : https://github.com/lsmloveu
 * csdn    : http://blog.csdn.net/csdn_android_lsm
 */

public interface Api {
    @POST("Account/AccLogon")
    Observable<MusicBean> toLogin(@Query("Name")String Name, @Query("Pwd")String Pwd, @Query("DeviceNo")String DeviceNo);

    @GET("/")
    Observable<YiYanBeen> getYiYan(@Query("encode")String encode, @Query("charset")String charset);

    @FormUrlEncoded
    @POST("/")
    Observable<MusicBean> getMusic(@FieldMap Map<String, String> params);
}
