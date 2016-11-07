package com.qsvxin.net.interf;

import android.graphics.Bitmap;

/**
 * 获取网络图片
 *
 */
public interface GetNetBitmapInterf {
/**
 * 获取网络异常
 * @param errCode
 * @param errMsg
 */
public void getBitmapErrData(int errCode, String errMsg);
/**
 * 获取图片并转换成Bitmap对象
 * @param mBitmap
 */
public void getBitmap(Bitmap mBitmap);
}
