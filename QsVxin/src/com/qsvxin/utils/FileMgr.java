package com.qsvxin.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.qsvxin.MyApplication;

public class FileMgr {
	public static final String UPDATE_FOLDER_NAME = "update";
	public static final String LOGO_FOLDER_NAME = "logo";
	
    /**
     * 保存数据到文件,如果文件夹不存在，会自动创建好
     * @param ctx
     * @param inSDCard  是否保存到SDCard
     * @param dirName   保存到的文件夹名称
     * @param fileName  文件名
     * @param is        输入流
     * @return          写文件出错时返回false，成功时返回true
     */
    public static boolean saveFile(Context ctx, boolean inSDCard, String dirName, String fileName, InputStream is) {
        File outFile = newFile(ctx, inSDCard, dirName, fileName);
        
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        return true;
    }
    
    /**
     * 创建一个文件夹文件对象，创建对象后是否自动在存储设备上创建文件夹由参数createDirIfNeeded指定
     * @param ctx
     * @param inSDCard  是否在SDCard上创建
     * @param dirName   文件夹名称
     * @param createDirIfNeeded 是否自动在存储设备上创建对应的文件夹
     * @return  文件夹文件对象
     */
    public static File newDir(Context ctx, boolean inSDCard, String dirName, boolean createDirIfNeeded) {
        File dir = null;
        if (inSDCard) {
            dir = new File(Environment.getExternalStorageDirectory() + File.separator 
                    + ctx.getPackageName() + File.separator + dirName);
        } else {
            dir = new File(ctx.getFilesDir(), dirName);
        }
        
        if (createDirIfNeeded && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    /**
     * 创建一个文件夹文件对象，创建对象后会自动在存储设备上创建对应的文件夹，如不需要自动创建文件夹请使用
     * newDir(Context ctx, boolean inSDCard, String dirName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     * @param ctx
     * @param inSDCard  是否在SDCard上创建
     * @param dirName   文件夹名称
     * @return  文件夹文件对象
     */
    public static File newDir(Context ctx, boolean inSDCard, String dirName) {
        return newDir(ctx, inSDCard, dirName, true);
    }
    
    /**
     * 创建一个普通文件对象，创建对象后是否自动在存储设备上创建相关的文件夹由参数createDirIfNeeded指定
     * @param ctx
     * @param inSDCard  是否在SDCard上创建
     * @param dirName   保存到的文件夹名称
     * @param fileName  文件名
     * @param createDirIfNeeded 是否在存储设备上自动创建相关的文件夹
     * @return  文件对象
     */
    public static File newFile(Context ctx, boolean inSDCard, String dirName, String fileName, boolean createDirIfNeeded) {
        return new File(newDir(ctx, inSDCard, dirName, createDirIfNeeded), fileName);
    }
    
    /**
     * 创建一个普通文件对象，创建对象后会自动在存储设备上创建相关的文件夹，如不需要自动创建文件夹请使用
     * newFile(Context ctx, boolean inSDCard, String dirName, String fileName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     * @param ctx
     * @param inSDCard  是否在SDCard上创建
     * @param dirName   保存到的文件夹名称
     * @param fileName  文件名
     * @return  文件对象
     */
    public static File newFile(Context ctx, boolean inSDCard, String dirName, String fileName) {
        return newFile(ctx, inSDCard, dirName, fileName, true);
    }
    
    /**
     * 判断文件是否存在
     * @param ctx
     * @param inSDCard  是否在SDCard的文件
     * @param dirName   保存文件的文件夹名称
     * @param fileName  文件名
     * @return  true代表文件存在，false为不存在
     */
    public static boolean exists(Context ctx, boolean inSDCard, String dirName, String fileName) {
        File file = newFile(ctx, inSDCard, dirName, fileName, false);
        return file.exists();
    }
    
    /**
     * 判断文件夹是否存在
     * @param ctx
     * @param inSDCard  是否在SDCard的文件夹
     * @param dirName   文件夹名称
     * @return  true代表文件夹存在，false为不存在
     */
    public static boolean exists(Context ctx, boolean inSDCard, String dirName) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        return dir.exists();
    }
    
    /**
     * 删除存储设备上的文件
     * @param ctx
     * @param inSDCard  文件是否在SDCard
     * @param dirName   文件存储的文件夹
     * @param fileName  文件名
     * @return  true代表文件删除成功，false为删除失败
     */
    public static boolean deleteFile(Context ctx, boolean inSDCard, String dirName, String fileName) {
        File file = newFile(ctx, inSDCard, dirName, fileName, false);
        if (file != null && file.exists()) {
            return file.delete();
        } else {
            return true;
        }
    }
    
    /**
     * 删除指定的文件夹中的文件，满足filter条件的所有文件将被删除
     * @param ctx
     * @param inSDCard  文件是否在SDCard
     * @param dirName   文件夹名称
     * @param filter    文件过滤条件，filter.accept(File file)接口返回true的文件将被删除
     */
    public static void deleteFiles(Context ctx, boolean inSDCard, String dirName, FileFilter filter) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        if (dir.exists()) {
            if (filter != null) {
                for (File file : dir.listFiles()) {
                    if (filter.accept(file)) {
                        file.delete();
                    }
                }
            } else {
                for (File file : dir.listFiles()) {
                    file.delete();
                }
            }
        }
    }
    
    /**
     * 删除指定的文件夹中的所有文件
     * @param ctx
     * @param inSDCard  文件是否在SDCard
     * @param dirName   文件夹名称
     */
    public static void deleteFiles(Context ctx, boolean inSDCard, String dirName) {
        deleteFiles(ctx, inSDCard, dirName, null);
    }
    
    /**
     * 删除文件夹中的所有文件，并将此文件夹也删除，如只需要删除文件夹中的文件，请使用deleteFiles
     * @param ctx
     * @param inSDCard  文件是否在SDCard
     * @param dirName   文件夹名称
     */
    public static void deleteDir(Context ctx, boolean inSDCard, String dirName) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        if (dir.exists()) {
            deleteFiles(ctx, inSDCard, dirName);
            dir.delete();
        }
    }
    
    /**
     * 判断sdcard是否可用
     * @param context
     * @return
     */
    public static boolean sdCardAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); 
    }
    
    /** 修改文件的权限,例如"777"等 */
	public static void chmod(String path, String mode) {
		try {
			String command = "chmod " + mode + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (Exception e) {
		}
	}
    /**
     * 获取文件的MD5值
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
		String s = null;
		
		if (!file.exists()) {
			return null;
		}
		
		FileInputStream in = null;
	    byte buffer[] = new byte[1024];
	    int len;

		try {  
			MessageDigest md = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, len);
		    }
			in.close();

			BigInteger bigInt = new BigInteger(1, md.digest());
			s = String.format("%032X", bigInt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return s;
	}
    
	/**
	 * @Title: creatDir
	 * @Description: 创建指定名称的文件夹
	 * @return File
	 * @author liang_xs
	 */
	public static File creatDir(String foldName) {
		File dir;
		if (sdCardAvailable()) {
			dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ "/Android/data"
					+ File.separator
					+ MyApplication.getApplication().getApplicationContext()
							.getPackageName(), foldName);
			if (!dir.exists()) {
				try {
					dir.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			dir = MyApplication.getApplication().getApplicationContext()
					.getFilesDir();
		}
		return dir;
	}
	
	/**
	 * @Title: delAllFile
	 * @Description: 删除除strFileName外的所有文件
	 * @return boolean
	 * @author liang_xs
	 */
	public static boolean delAllFile(String strPath, String strFileName) {
		boolean flag = false;
		File file = new File(strPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (strPath.endsWith(File.separator)) {
				temp = new File(strPath + tempList[i]);
			} else {
				temp = new File(strPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				if (!TextUtils.isEmpty(strFileName)
						&& temp.getName().equals(strFileName)) {
					continue;
				} else {
					temp.delete();
				}
			}
			if (temp.isDirectory()) {
				continue;
				// flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * @Title: installApp
	 * @Description: 安装
	 * @return void
	 * @author liang_xs
	 */
	public static void installApp(Context context, String strFilePath) {
		chmod(strFilePath, "704");
//		if (!FileUtils.isWriteable(strFilePath)) {
//			try {
//				FileOutputStream file = context.openFileOutput(strFilePath, Context.MODE_WORLD_READABLE);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		File file = new File(strFilePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	 * @Title: getFileSize
	 * @Description: 获取文件大小
	 * @return int
	 * @author liang_xs
	 */
	public static String getFileSize(String strFilePath) {
		File file = new File(strFilePath);
		if (file == null || !file.exists() || !file.isFile()) {
			return null;
		}
		long size = file.length();
		return formatSize(size);
	}


	/**
	 * @Title: formatSize
	 * @Description: 转换大小
	 * @return String
	 * @author liang_xs
	 */
	public static String formatSize(long size) {
		String strSuffix = null;
		float fSize = 0;

		if (size >= 1024) {
			strSuffix = "KB";
			fSize = size / 1024;
			if (fSize >= 1024) {
				strSuffix = "MB";
				fSize /= 1024;
			}
			if (fSize >= 1024) {
				strSuffix = "GB";
				fSize /= 1024;
			}
		} else {
			fSize = size;
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
		if (strSuffix != null)
			resultBuffer.append(strSuffix);
		return resultBuffer.toString();
	}

}
