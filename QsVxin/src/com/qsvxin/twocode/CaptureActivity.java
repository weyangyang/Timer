package com.qsvxin.twocode;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.qsvxin.R;
import com.qsvxin.SendOrderActivity;
import com.qsvxin.twocode.camera.CameraManager;
import com.qsvxin.twocode.decoding.CaptureActivityHandler;
import com.qsvxin.twocode.decoding.InactivityTimer;
import com.qsvxin.twocode.util.LightControl;
import com.qsvxin.twocode.view.ViewfinderView;

public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private boolean isShow = false;
	private Button btn_title_left;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		// 初始化 CameraManager
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		btn_title_left = (Button) findViewById(R.id.btnLeft);
		btn_title_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CaptureActivity.this.finish();
			}
		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	
	/**
	 * {
        "msgtype":1,
        "openid":"oPW6BuCKTGEphvKZrci-4-w1YMHQ",
        "type":1,
        "time":"2014-09-24 00:00:00",
        "nickname":"昵称",
        "head":"http://img4.duitang.com/uploads/item/201205/11/20120511155026_LrVCs.thumb.600_0.jpeg",
        "text":"text"
        }
	 */
	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 这里就是一系列初始化相机view的过程
		 */
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		/**
		 * 这里就是看看是否是正常声音播放模式，如果是就播放声音，如果不是，则不播放
		 */
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		/**
		 * 初始化声音
		 */
		initBeepSound();
		/**
		 * 是否震动
		 */
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		/**
		 * 关掉相机，关掉解码线程，清空looper队列中message
		 */
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();// 关掉相机
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 初始化相机
	 */
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			/**
			 * 新建解码结果handler
			 */
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		/**
		 * 初始化相机
		 */
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	/**
	 * 返回显示的view
	 */
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	/**
	 * 返回处理解码结果的handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * 清空view中先前扫描成功的图片
	 */
	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		/**
		 * 重新计时
		 */
		inactivityTimer.onActivity();
		/**
		 * 将结果绘制到view中
		 */
		viewfinderView.drawResultBitmap(barcode);
		/**
		 * 播放jeep声音
		 */
		playBeepSoundAndVibrate();
		/**
		 * 显示解码字符串
		 */
		txtResult.setText(obj.getBarcodeFormat().toString() + ":"
				+ obj.getText());
//		IndexActivity.RESULT_MESSAGE = obj.getBarcodeFormat().toString() + ":"
//				+ obj.getText();
//		IndexActivity.RESULT_BITMAP = barcode;
//
//		Intent intent = new Intent(CaptureActivity.this, null);// 跳到扫描后的activity
//		this.startActivity(intent);
//		this.finish();
		if (obj.getText().startsWith("http://") || obj.getText().endsWith(".apk")) {
			Intent intent= new Intent();        
		    intent.setAction("android.intent.action.VIEW");    
		    Uri content_url = Uri.parse(obj.getText());   
		    intent.setData(content_url);  
		    startActivity(intent);
		    this.finish();
		    return;
		}
		String type = getIntent().getStringExtra("type");
		if (!TextUtils.isEmpty(type) && type.equals("curier")) {
			Intent intent = new Intent();
			intent.putExtra("CODE", obj.getText());
			setResult(RESULT_OK, intent);
			this.finish();
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	/**
	 * 震动时间
	 */
	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		/**
		 * 播放声音
		 */
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		/**
		 * 震动
		 */
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * 闪光灯开关
	 * */
	public void lightSwitch(View view) {
		LightControl lightControl = new LightControl();
		switch (view.getId()) {
		case R.id.light_btn:
			if (isShow) {
				isShow = false;
				lightControl.turnOff();// 关闭
			} else {
				isShow = true;
				lightControl.turnOn();// 打开
			}
			break;

		default:
			break;
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}