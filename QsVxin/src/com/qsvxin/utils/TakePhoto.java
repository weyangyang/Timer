package com.qsvxin.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qsvxin.R;

public class TakePhoto {
	private PhotoResult mPhotoResult = null;
	private Activity mActivity;
	private Fragment mFragment;
	private int outWidth = 300;
	private int outHeight = 300;
	public static final int REQUEST_CODE_CAMERA = 0x8620;
	public static final int REQUEST_CODE_CLIP = 0x8621;
	public static final int REQUEST_CODE_GALLERY = 0x8622;
	private File mOutputPath;
	private Handler mHandler = new Handler();
	private boolean fromFragment;
	private boolean isCircle;
	private Bitmap bitmap;
	private Uri uri;

	public TakePhoto(Activity activity, PhotoResult photoResult, File outputPath) {
		mActivity = activity;
		mPhotoResult = photoResult;
		mOutputPath = outputPath;
		this.isCircle = true;
	}

	public TakePhoto(Activity activity, PhotoResult photoResult,
			File outputPath, boolean isCircle) {
		mActivity = activity;
		mPhotoResult = photoResult;
		mOutputPath = outputPath;
		this.isCircle = isCircle;
	}

	public TakePhoto(Fragment fragment, PhotoResult photoResult, File outputPath) {
		mFragment = fragment;
		mPhotoResult = photoResult;
		mOutputPath = outputPath;
		fromFragment = true;
		this.isCircle = true;
		mActivity = mFragment.getActivity();
	}

	public TakePhoto(Fragment fragment, PhotoResult photoResult,
			File outputPath, boolean isCircle) {
		mFragment = fragment;
		mPhotoResult = photoResult;
		mOutputPath = outputPath;
		fromFragment = true;
		this.isCircle = isCircle;
		mActivity = mFragment.getActivity();
	}

	public void setOutputPath(File outputPath) {
		mOutputPath = outputPath;
	}

	public void setOutWidth(int outWidth) {
		this.outWidth = outWidth;
	}

	public void setOutHeight(int outHeight) {
		this.outHeight = outHeight;
	}

	private Bitmap getBitmapFromUri(Uri uri) {
		try {
			String imgPath = null;
			 String[] proj = { MediaStore.Images.Media.DATA };
			 Cursor cursor = null;
			 if (fromFragment) {
				 cursor = mFragment.getActivity().managedQuery(uri, proj, null, null, null);
			 } else {
				 cursor = mActivity.managedQuery(uri, proj, null, null, null);
			 }
             int index = cursor
                     .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
             cursor.moveToFirst();
             imgPath = cursor.getString(index);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imgPath, options);
			if (options.outWidth < 0 || options.outHeight < 0) {
				return null;
			}
			options.inJustDecodeBounds = false;
			Bitmap bmp = null;
			try {
				bmp = BitmapFactory.decodeFile(imgPath, options);
			} catch (OutOfMemoryError e) {
				options.inSampleSize *= 2;
				bmp = BitmapFactory.decodeFile(imgPath, options);
			}
			return bmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	BufferedOutputStream bos;
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_CAMERA:
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mPhotoResult.onPhotoResult(mOutputPath);
					}
				});
				
				break;

			case REQUEST_CODE_GALLERY:
				uri = data.getData();
				if (uri != null) {
					new Thread() {
						public void run() {
							bitmap = getBitmapFromUri(uri);
							if (bitmap != null) {
								try {
									bos = new BufferedOutputStream(
											new FileOutputStream(mOutputPath));
									bitmap = Bitmap.createScaledBitmap(bitmap,
											bitmap.getWidth(), bitmap.getHeight(),
											true);
									bitmap.compress(Bitmap.CompressFormat.JPEG,
											100, bos);// 将图片压缩的流里面
									bos.flush();// 刷新此缓冲区的输出流
									bos.close();// 关闭此输出流并释放与此流有关的所有系统资源
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										mPhotoResult.onPhotoResult(mOutputPath);
									}
								});
							}
						};
					}.start();
				} else {
					return;
				}
				
				break;

			}
		}
	}
	
	private Bitmap saveClipPhoto(File file) throws IOException {
		Bitmap bitmap = null;
		FileOutputStream fos = null;
		fos = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		return bitmap;
	}

	private void pickImageFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mOutputPath));
		if (fromFragment) {
			mFragment.startActivityForResult(intent, REQUEST_CODE_CAMERA);
		} else {
			mActivity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
		}
	};

	private void pickImageFromGallaryAndClip() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		try {
			if (fromFragment) {
				mFragment.startActivityForResult(intent, REQUEST_CODE_GALLERY);
			} else {
				mActivity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
			}
		} catch (ActivityNotFoundException e) {
			CommonUtils.toast(mActivity, "请确认手机是否有相册功能");
		}
	}

	public void start() {
		if (mPhotoResult == null || mOutputPath == null || mActivity == null
				|| mOutputPath.getParentFile() == null
				|| !mOutputPath.getParentFile().exists()
				|| TextUtils.isEmpty(mOutputPath.getName())) {
			return;
		}

		showMenuDialog();
	}

	private Dialog mMenuDialog;

	private void showMenuDialog() {
		if (mMenuDialog == null) {
			mMenuDialog = new Dialog(mActivity, R.style.myDialog);
			mMenuDialog.setContentView(R.layout.dialog_head);
			LinearLayout head_camera = (LinearLayout) mMenuDialog
					.findViewById(R.id.head_camera);
			head_camera.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO
					pickImageFromCamera();

					mMenuDialog.dismiss();
				}
			});
			LinearLayout head_picture = (LinearLayout) mMenuDialog
					.findViewById(R.id.head_picture);
			head_picture.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pickImageFromGallaryAndClip();
					mMenuDialog.dismiss();
				}
			});
			Button btnCancle = (Button) mMenuDialog.findViewById(R.id.cancle);
			btnCancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mMenuDialog.dismiss();
				}
			});
			mMenuDialog.show();
		}
		mMenuDialog.show();
	}

	public interface PhotoResult {
		void onPhotoResult(File outputPath);
	}
}
