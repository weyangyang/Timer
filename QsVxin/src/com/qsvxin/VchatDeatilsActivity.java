package com.qsvxin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.qsvxin.bean.EngineStaticBean;
import com.qsvxin.bean.GetUserMsgBean;
import com.qsvxin.bean.GetUserMsgListBean;
import com.qsvxin.bean.LoginBean;
import com.qsvxin.engine.GetUserChatMsg;
import com.qsvxin.engine.SendTextMsg2User;
import com.qsvxin.net.interf.GetUserMsgParserData;
import com.qsvxin.net.interf.SendTextMsg2UserParserData;
import com.qsvxin.utils.CommonUtils;
import com.qsvxin.utils.FileMgr;
import com.qsvxin.utils.ImageLoaderManager;
import com.qsvxin.utils.PreferenceUtils;
import com.qsvxin.utils.QsConstants;
import com.qsvxin.utils.TakePhoto;
import com.sge.imageloader.core.DisplayImageOptions;
import com.sge.imageloader.core.ImageLoader;

/**
 * @Description: TODO
 * @author liang_xs
 * @date 2014-9-22
 */
public class VchatDeatilsActivity extends BaseActivity implements OnClickListener {
    public static VchatDeatilsActivity mActivity;
    private ListView mListView;
    private ImageView ivBackGround;
    private TextView tvTitle;
    private Button btnSendMsg, btnLeft, btnRight;
    private EditText etSendMsg;
    private MyAdapter mAdapter;
    private String str_secretKey, str_userOpenID, str_nickname, str_headUrl;
    private ArrayList<GetUserMsgBean> beans = new ArrayList<GetUserMsgBean>();
    private GetUserMsgListBean bean;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    private TakePhoto mTakePhoto;
    private File headPhotoFile = null;
    private Vibrator vibrator;

    /*
     * (non-Javadoc)
     * 
     * @see com.qsvxin.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vchat_details);
        mActivity = this;

        bean = EngineStaticBean.getInstance().getUserMsgListBean();
        initView();
        mImageLoader = ImageLoaderManager.getImageLoader(this);
        mDisplayImageOptions = ImageLoaderManager.getImageOptions(this);
        str_secretKey = LoginBean.getInstance().getStrSecretKey();
        // str_secretKey = "qPC8sd3VvK@BtyvIy9JrUw==";
        str_userOpenID = getIntent().getStringExtra("str_userOpenID");
        str_nickname = getIntent().getStringExtra("str_nickname");
        str_headUrl = getIntent().getStringExtra("str_headUrl");
        if (TextUtils.isEmpty(str_userOpenID)) {
            if (bean == null) {
                toast("获取数据失败，请稍候重试");
                return;
            }
            str_userOpenID = bean.getStrOpenID();
        }
        if (TextUtils.isEmpty(str_nickname)) {
            if (bean == null) {
                toast("获取数据失败，请稍候重试");
                return;
            }
            str_nickname = bean.getStrNickName();
        }
        if (TextUtils.isEmpty(str_headUrl)) {
            if (bean == null) {
                toast("获取数据失败，请稍候重试");
                return;
            }
            str_headUrl = bean.getStrHeadUrl();
        }
        tvTitle.setText(str_nickname);
        if (!TextUtils.isEmpty(str_secretKey) && CommonUtils.checkNet(this)) {
            initDataFromNet(str_secretKey, true);
        } else {
            CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
        }
        String strBackground = PreferenceUtils.getPrefString(VchatDeatilsActivity.this, str_userOpenID + "background",
                "");
        String strBackgroundAll = PreferenceUtils.getPrefString(VchatDeatilsActivity.this, "background", "");
        if (!TextUtils.isEmpty(strBackground)) {
            ivBackGround.setImageURI(Uri.parse(strBackground));
        } else {
            if (!TextUtils.isEmpty(strBackgroundAll)) {
                ivBackGround.setImageURI(Uri.parse(strBackgroundAll));
            }
        }

        headPhotoFile = new File(this.getFilesDir(), str_userOpenID + ".png");
        try {
            headPhotoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileMgr.chmod(headPhotoFile.getAbsolutePath(), "777");
        mTakePhoto = new TakePhoto(this, new TakePhoto.PhotoResult() {
            @Override
            public void onPhotoResult(File outputPath) {
                Bitmap bitmap = BitmapFactory.decodeFile(outputPath.getAbsolutePath());
                ivBackGround.setImageBitmap(bitmap);
                PreferenceUtils.setPrefString(VchatDeatilsActivity.this, str_userOpenID + "background",
                        headPhotoFile.getAbsolutePath());
            }
        }, headPhotoFile);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePhoto.onActivityResult(requestCode, resultCode, data);
    }

    public void initDataFromNet(final String str_secretKey, boolean isShowDialog) {
        new GetUserChatMsg(this, str_secretKey, str_userOpenID, new GetUserMsgParserData() {

            @Override
            public void getUserMsgSuccData(String succCode, String succMsg, final ArrayList<GetUserMsgBean> mArrayList) {
                VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beans = mArrayList;
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void getUserMsgErrData(String errCode, final String errMsg) {
                // TODO:保存异常日志相关信息
            }

            @Override
            public void getParserErrData(int errCode, final String errMsg) {
                // TODO:保存异常日志相关信息
            }

            @Override
            public void getNetErrData(int errCode, final String errMsg) {
                if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//					initDataFromNet(str_secretKey,false);
                	VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonUtils
									.errorTipsDialog(
											VchatDeatilsActivity.this,
											VchatDeatilsActivity.this
													.getString(R.string.str_prompt),
													VchatDeatilsActivity.this
													.getString(R.string.str_net_timeout));
						}
					});
                } else {
                    VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.errorTipsDialog(VchatDeatilsActivity.this,
                                    VchatDeatilsActivity.this.getString(R.string.str_prompt),
                                    VchatDeatilsActivity.this.getString(R.string.str_vmember_net_error));
                        }
                    });
                }
            }
        }, isShowDialog).excute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qsvxin.BaseActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EngineStaticBean.getInstance().setUserMsgListBean(null);
        mActivity = null;
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        etSendMsg = (EditText) findViewById(R.id.etSendMsg);
        ivBackGround = (ImageView) findViewById(R.id.ivBackGround);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        btnSendMsg.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    private class MyAdapter extends BaseAdapter {
    	private String strHead = null;

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(VchatDeatilsActivity.this, R.layout.vchat_details_item, null);
                holder = new ViewHolder();
                holder.ivLeftHead = (ImageView) convertView.findViewById(R.id.ivLeftHead);
                // holder.tvLeftName = (TextView)
                // convertView.findViewById(R.id.tvLeftName);
                holder.tvLeftContent = (TextView) convertView.findViewById(R.id.tvLeftContent);
                holder.tvLeftTime = (TextView) convertView.findViewById(R.id.tvLeftTime);

                holder.ivRightHead = (ImageView) convertView.findViewById(R.id.ivRightHead);
                holder.imgv_userSendImage = (ImageView) convertView.findViewById(R.id.imgv_userSendImage);
                // holder.tvRightName = (TextView)
                // convertView.findViewById(R.id.tvRightName);
                holder.tvRightContent = (TextView) convertView.findViewById(R.id.tvRightContent);
                holder.tvRightTime = (TextView) convertView.findViewById(R.id.tvRightTime);
                holder.layoutLeft = convertView.findViewById(R.id.layoutLeft);
                holder.layoutRight = convertView.findViewById(R.id.layoutRight);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final GetUserMsgBean msgBean = beans.get(position);
            if (!TextUtils.isEmpty(msgBean.getStrIOType())) {
                switch (Integer.parseInt(msgBean.getStrIOType())) {
                case 1:
                    holder.layoutLeft.setVisibility(View.VISIBLE);
                    holder.layoutRight.setVisibility(View.GONE);
                    // holder.tvLeftName.setText(bean.getStrNickName());
                    if("2".equals(msgBean.getStrMsgType())){
                        //TODO:..
                        holder.tvLeftContent.setVisibility(View.GONE);
                        holder.imgv_userSendImage.setVisibility(View.VISIBLE);
                        holder.tvLeftTime.setText(msgBean.getStrCurrentMsgTime());
                        mImageLoader.displayImage(str_headUrl, holder.ivLeftHead, mDisplayImageOptions);
                        mImageLoader.displayImage(msgBean.getStrMsgText(), holder.imgv_userSendImage, mDisplayImageOptions);
                    }else if("4".equals(msgBean.getStrMsgType())){
                        holder.tvLeftContent.setVisibility(View.VISIBLE);
                        holder.imgv_userSendImage.setVisibility(View.GONE);
                        holder.tvLeftContent.setText("【位置】");
                        holder.tvLeftTime.setText(msgBean.getStrCurrentMsgTime());
                        mImageLoader.displayImage(str_headUrl, holder.ivLeftHead, mDisplayImageOptions);
                    }else{
                        holder.tvLeftContent.setVisibility(View.VISIBLE);
                        holder.imgv_userSendImage.setVisibility(View.GONE);
                        holder.tvLeftContent.setText(msgBean.getStrMsgText());
                        holder.tvLeftTime.setText(msgBean.getStrCurrentMsgTime());
                        mImageLoader.displayImage(str_headUrl, holder.ivLeftHead, mDisplayImageOptions);
                    }
                    break;
                case 2:
                    holder.layoutRight.setVisibility(View.VISIBLE);
                    holder.layoutLeft.setVisibility(View.GONE);
                    // holder.tvRightName.setText(bean.getStrNickName());
                    holder.tvRightContent.setText(msgBean.getStrMsgText());
                    holder.tvRightTime.setText(msgBean.getStrCurrentMsgTime());
                    strHead = PreferenceUtils.getPrefString(
            				VchatDeatilsActivity.this, "myHead", "");
                    if (TextUtils.isEmpty(strHead)) {
                    	mImageLoader.displayImage(null, holder.ivRightHead, mDisplayImageOptions);
                    } else {
                    	holder.ivRightHead.setImageURI(Uri.parse(strHead));
                    }
                    break;
                default:
                    break;
                }
                holder.imgv_userSendImage.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					CommonUtils.headZoom(VchatDeatilsActivity.this, msgBean.getStrMsgText());
    				}
    			});
                holder.ivLeftHead.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CommonUtils.headZoom(VchatDeatilsActivity.this, str_headUrl);
					}
				});
                holder.ivRightHead.setOnClickListener(new OnClickListener() {
                	
                	@Override
                	public void onClick(View v) {
                		// TODO 右面自己头像尚未修改
                		CommonUtils.headZoom(VchatDeatilsActivity.this, new File(strHead));
                	}
                });
            }

            return convertView;
        }
    }

    private static class ViewHolder {
        private ImageView ivLeftHead, ivRightHead,imgv_userSendImage;
        // private TextView tvLeftName, tvRightName;
        private TextView tvLeftContent, tvRightContent;
        private TextView tvLeftTime, tvRightTime;
        private View layoutLeft, layoutRight;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnSendMsg:
            if (CommonUtils.checkNet(this)) {
                sendMsgToNet(false);
            } else {
                CommonUtils.createDialog(this, "提示", "网络异常，请检查网络连接");
            }
            break;

        case R.id.btnLeft:
            this.finish();
            break;

        case R.id.btnRight:
        	mTakePhoto.start();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//            builder.setTitle("提示");
//            final String[] groups = new String[] { "更换背景图片" };
//            builder.setItems(groups, new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int which) {
//                    VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            mTakePhoto.start();
//                        }
//                    });
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
            break;
        }
    }

    private void sendMsgToNet(boolean isShowDialog) {
        final String strMsgContent = etSendMsg.getText().toString();
        etSendMsg.getText().clear();
        if (!TextUtils.isEmpty(strMsgContent)) {
            // str_openID=oPW6BuDGEoakWTasFampZmcXLyCg,
            // str_secretKey=qPC8sd3VvK@BtyvIy9JrUw==,
            // str_appSecret=abb99fb43e432b7bce6b018d7dc3ed06,
            // str_appID=wxbd33effb7ab02764
//            String str_appSecret = "abb99fb43e432b7bce6b018d7dc3ed06";
//            String str_appID = "wxbd33effb7ab02764";
            Map<String, String> mMap = new HashMap<String, String>();
            // mMap.put("str_secretKey", str_secretKey);
            // mMap.put("str_appID", str_appID);
            // mMap.put("str_appSecret", str_appSecret);
            // mMap.put("str_openID", str_userOpenID);
            // mMap.put("str_sendText", strMsgContent);
            mMap.put("str_secretKey", LoginBean.getInstance().getStrSecretKey());
            mMap.put("str_appID", LoginBean.getInstance().getStrAppid());
            mMap.put("str_appSecret", LoginBean.getInstance().getStrAppsecret());
            mMap.put("str_openID", str_userOpenID);
            mMap.put("str_sendText", strMsgContent);

            new SendTextMsg2User(VchatDeatilsActivity.this, mMap, new SendTextMsg2UserParserData() {

                @Override
                public void getSuccData(String succCode, String succMsg) {
                    VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast("发送成功");
                            GetUserMsgBean b = new GetUserMsgBean();
                            b.setStrCurrentMsgTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                            b.setStrIOType("2");
                            b.setStrMsgText(strMsgContent);
                            b.setStrMsgType("1");
                            b.setStrOpenID(str_userOpenID);
                            beans.add(b);
                            mAdapter.notifyDataSetChanged();
                        }
                    });

                }

                @Override
                public void getParserErrData(int errCode, final String errMsg) {
                    // TODO:保存异常日志相关信息
                }

                @Override
                public void getNetErrData(int errCode, final String errMsg) {
                    if (errCode == QsConstants.CLIENT_PROTOCOL_EXCEPTION) {
//    					initDataFromNet(str_secretKey,false);
                    	VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
    						@Override
    						public void run() {
    							CommonUtils
    									.errorTipsDialog(
    											VchatDeatilsActivity.this,
    											VchatDeatilsActivity.this
    													.getString(R.string.str_prompt),
    													VchatDeatilsActivity.this
    													.getString(R.string.str_net_timeout));
    						}
    					});
                    } else {
                        VchatDeatilsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtils.errorTipsDialog(VchatDeatilsActivity.this,
                                        VchatDeatilsActivity.this.getString(R.string.str_prompt),
                                        VchatDeatilsActivity.this.getString(R.string.str_vmember_net_error));
                            }
                        });
                    }
                }

                @Override
                public void getErrData(String errCode, final String errMsg) {
                    // TODO:保存异常日志相关信息
                }
            }, isShowDialog).excute();
        } else {
            toast("请输入内容");
        }
    }

}
