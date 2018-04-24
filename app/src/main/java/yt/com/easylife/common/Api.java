package yt.com.easylife.common;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yt.com.easylife.bean.DriverBean;

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
    Observable<DriverBean> toLogin(@Query("Name")String Name, @Query("Pwd")String Pwd, @Query("DeviceNo")String DeviceNo);
}
