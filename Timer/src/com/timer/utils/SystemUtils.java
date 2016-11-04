package com.timer.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
/**
 * 获取手机系统信息类
 * @author liyusheng
 *
 */
public class SystemUtils {
	/** 获取android系统版本号 */
	public static String getOSVersion() {
		String release = android.os.Build.VERSION.RELEASE; // android系统版本号
		release = "android" + release;
		return release;
	}

	/** 获得android系统sdk版本号 */
	public static String getOSVersionSDK() {
		return android.os.Build.VERSION.SDK;
	}

	/** 获得android系统sdk版本号 */
	public static int getOSVersionSDKINT() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/** 获取手机型号 */
	public static String getDeviceModel() {
		return android.os.Build.MODEL;
	}
	 /**
     * 品牌
     * 
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
    /**
     * 获取 Channel
     * 
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
    	
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = ai.metaData;
            if (bundle != null) {
                return bundle.getString("UMENG_CHANNEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	/** 获取设备的IMEI */
	public static String getIMEI(Context context) {
		String imei = null;
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imei;
	}

	/** 检测手机是否已插入SIM卡 */
	public static boolean isCheckSimCardAvailable(Context context) {
		
		if (null == context) {
			return false;
		}
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
	}

	/** sim卡是否可读 */
	public static boolean isCanUseSim(Context context) {
		
		if (null == context) {
			return false;
		}
		try {
			TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 取得当前sim手机卡的imsi */
	public static String getIMSI(Context context) {
		
		if (null == context) {
			return null;
		}
		String imsi = null;
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = tm.getSubscriberId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imsi;
	}

	/** 返回本地手机号码，这个号码不一定能获取到 */
	public static String getNativePhoneNumber(Context context) {
		
		if (null == context) {
			return null;
		}
		TelephonyManager telephonyManager;
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String NativePhoneNumber = null;
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	/** 返回手机服务商名字 */
	public static String getProvidersName(Context context) {
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = getIMSI( context);
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		} else {
			ProvidersName = "其他服务商:" + IMSI;
		}
		return ProvidersName;
	}
	public static boolean isEmail(String email) {
		if (TextUtils.isEmpty(email)){
			return false;
		}
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isPhoneNumber(String mobiles) {

		if (TextUtils.isEmpty(mobiles)){
			return false;
		}
		Pattern pattern = Pattern.compile("^1\\d{10,10}$");
		Matcher matcher = pattern.matcher(mobiles);
		return matcher.matches();
	}

	public static boolean isPassword(String pwd) {

		if (TextUtils.isEmpty(pwd)){
			return false;
		}
		Pattern pattern = Pattern.compile("^([0-9a-zA-Z]){6,18}");
		Matcher matcher = pattern.matcher(pwd);
		return matcher.matches();
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	/** 获取当前设备的SN */
	public static String getSimSN(Context context) {
		
		if (null == context) {
			return null;
		}
		String simSN = null;
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			simSN = tm.getSimSerialNumber();
		} catch (Exception e) {
			return "";
		}
		return simSN;
	}
	/**
     * 宽度
     * 
     * @param context
     * @return
     */
    public static String getWidth(Context context)throws Exception  {
    	
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       return wm.getDefaultDisplay().getWidth() + "";
//        WindowManager wm = (WindowManager)activity.getWindowManager();
//        
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
    }

    /**
     * 高度
     * 
     * @param context
     * @return
     */
    public static String getHeight(Context context) throws Exception{
    	
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight() + "";
    }
	/** 获取当前设备的MAC地址 */
	public static String getMacAddress(Context context) {
		
		if (null == context) {
			return null;
		}
		String mac = null;
		try {
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wm.getConnectionInfo();
			mac = info.getMacAddress();
		} catch (Exception e) {
			return "";
		}
		return mac;
	}

	/** 获得设备ip地址 */
	public static String getLocalAddress() {
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			
		}
		return null;
	}

	/** 获取屏幕的分辨率 */
	@SuppressWarnings("deprecation")
	public static int[] getResolution(Context context) {
		
		if (null == context) {
			return null;
		}
		WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int[] res = new int[2];
		res[0] = windowMgr.getDefaultDisplay().getWidth();
		res[1] = windowMgr.getDefaultDisplay().getHeight();
		return res;
	}

	/** 获得设备的横向dpi */
	public static float getWidthDpi(Context context) {
		
		if (null == context) {
			return 0;
		}
		DisplayMetrics dm = null;
		try {
			if (context != null) {
				dm = new DisplayMetrics();
				dm = context.getApplicationContext().getResources().getDisplayMetrics();
			}

			return dm.densityDpi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** 获得设备的纵向dpi */
	public static float getHeightDpi(Context context) {
		
		if (null == context) {
			return 0;
		}
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.ydpi;
	}
	 // 获取CPU最大频率（单位KHZ）

    // "/system/bin/cat" 命令行

    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径

       public static String getMaxCpuFreq() {
               String result = "";
               ProcessBuilder cmd;
               try {
                       String[] args = { "/system/bin/cat",
                                       "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
                       cmd = new ProcessBuilder(args);
                       Process process = cmd.start();
                       InputStream in = process.getInputStream();
                       byte[] re = new byte[24];
                       while (in.read(re) != -1) {
                               result = result + new String(re);
                       }
                       in.close();
               } catch (IOException ex) {
                       ex.printStackTrace();
                       result = "N/A";
               }
               return result.trim();
       }

        // 获取CPU最小频率（单位KHZ）
       public static String getMinCpuFreq() {
               String result = "";
               ProcessBuilder cmd;
               try {
                       String[] args = { "/system/bin/cat",
                                       "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
                       cmd = new ProcessBuilder(args);
                       Process process = cmd.start();
                       InputStream in = process.getInputStream();
                       byte[] re = new byte[24];
                       while (in.read(re) != -1) {
                               result = result + new String(re);
                       }
                       in.close();
               } catch (IOException ex) {
                       ex.printStackTrace();
                       result = "N/A";
               }
               return result.trim();
       }

        // 实时获取CPU当前频率（单位KHZ）
       public static String getCurCpuFreq() {
               String result = "N/A";
               try {
                       FileReader fr = new FileReader(
                                       "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
                       BufferedReader br = new BufferedReader(fr);
                       String text = br.readLine();
                       result = text.trim();
               } catch (FileNotFoundException e) {
                       e.printStackTrace();
               } catch (IOException e) {
                       e.printStackTrace();
               }
               return result;
       }

       // 获取CPU名字
       public static String getCpuName() {
               try {
                       FileReader fr = new FileReader("/proc/cpuinfo");
                       BufferedReader br = new BufferedReader(fr);
                       String text = br.readLine();
                       String[] array = text.split(":\\s+", 2);
                       for (int i = 0; i < array.length; i++) {
                       }
                       return array[1];
               } catch (FileNotFoundException e) {
                       e.printStackTrace();
               } catch (IOException e) {
                       e.printStackTrace();
               }
               return null;
       }

	/** 获取设备信息 */
	public static String[] getDivceInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = {"", ""};
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}


	/** 获取当前设备cpu的型号 */
	public static int getCPUModel() {
		return matchABI(getSystemProperty("ro.product.cpu.abi")) | matchABI(getSystemProperty("ro.product.cpu.abi2"));
	}

	/** 匹配当前设备的cpu型号 */
	private static int matchABI(String abiString) {
		if (TextUtils.isEmpty(abiString)) {
			return 0;
		}
		if ("armeabi".equals(abiString)) {
			return 1;
		} else if ("armeabi-v7a".equals(abiString)) {
			return 2;
		} else if ("x86".equals(abiString)) {
			return 4;
		} else if ("mips".equals(abiString)) {
			return 8;
		}
		return 0;
	}

	/** 获取CPU核心数 */
	public static int getCpuCount() {
		return Runtime.getRuntime().availableProcessors();
	}

	/** 获取Rom版本 */
	public static String getRomversion() {
		String rom = "";
		try {
			String modversion = getSystemProperty("ro.modversion");
			String displayId = getSystemProperty("ro.build.display.id");
			if (modversion != null && !modversion.equals("")) {
				rom = modversion;
			}
			if (displayId != null && !displayId.equals("")) {
				rom = displayId;
			}
		} catch (Exception e) {
		}
		return rom;
	}

	/** 获取系统配置参数 */
	public static String getSystemProperty(String key) {
		String pValue = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method m = c.getMethod("get", String.class);
			pValue = m.invoke(null, key).toString();
		} catch (Exception e) {
		}
		return pValue;
	}



	public static String getKernelVersion() {
        String kernelVersion = "";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/proc/version");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return kernelVersion;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
        String info = "";
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                info += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (info != "") {
                final String keyword = "version ";
                int index = info.indexOf(keyword);
                line = info.substring(index + keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return kernelVersion;
    }
	
    /**
     * 网络连接
     * @tags @return 是否有连接
     */

    public static String checkNet(Context context) {
        try {
            // 获取手机所有连接管理对象（wi_fi,net等连接的管理）
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manger != null) {
                NetworkInfo info[] = manger.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        	String typeName = info[i].getSubtypeName();
                        	if(TextUtils.isEmpty(typeName)){
                        		 typeName = info[i].getTypeName();
                        	}
                        	return typeName;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";

    }
    /**
     * 网络连接
     * @tags @return 是否有连接
     */
    
    public static boolean checkAllNet(Context context) {
    	try {
    		// 获取手机所有连接管理对象（wi_fi,net等连接的管理）
    		ConnectivityManager manger = (ConnectivityManager) context
    				.getSystemService(Context.CONNECTIVITY_SERVICE);
    		if (manger != null) {
    			NetworkInfo info[] = manger.getAllNetworkInfo();
    			if (info != null) {
    				for (int i = 0; i < info.length; i++) {
    					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
    						return true;
    					}
    				}
    			}
    		}
    	} catch (Exception e) {
    		return false;
    	}
    	return false;
    	
    }
    /**
     * 检查WiFI
     * 
     * @param context
     * @return
     */
    public static boolean checkWiFi(Context context) {
        try {
            // 获取手机所有连接管理对象（wi_fi,net等连接的管理）
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}