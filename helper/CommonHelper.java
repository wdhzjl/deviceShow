package com.bosstun.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import com.bosstun.utils.cfg.BaseProperties;
import com.bosstun.utils.file.FileCommon;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CommonHelper {
	public static class Device {
		public static String getImeiNumber(Context paramContext) {
			String str = ((TelephonyManager) paramContext
					.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if (str == null)
				str = "000000000000000";
			return str;
		}

		public static String getLine1Number(Context paramContext) {
			return ((TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE))
					.getLine1Number();
		}

		public static String getLocalIpAddress(Context paramContext) {
			try {
				Enumeration localEnumeration1 = NetworkInterface
						.getNetworkInterfaces();
				if (localEnumeration1.hasMoreElements()) {
					Enumeration localEnumeration2 = ((NetworkInterface) localEnumeration1
							.nextElement()).getInetAddresses();
					InetAddress localInetAddress;
					do {
						if (localEnumeration2.hasMoreElements())
							;
						localInetAddress = (InetAddress) localEnumeration2
								.nextElement();
					} while (localInetAddress.isLoopbackAddress());
					String str = localInetAddress.getHostAddress().toString();
					return str;
				}
			} catch (SocketException localSocketException) {
				Log.e("WifiPreference IpAddress",
						localSocketException.toString());
			}
			return null;
		}

		public static String getLocalMacAddress(Context paramContext) {
			return ((WifiManager) paramContext.getSystemService(Context.WIFI_SERVICE))
					.getConnectionInfo().getMacAddress();
		}

		public static String getPhoneNumber(Context paramContext) {
			return ((TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE))
					.getLine1Number();
		}

		public static class Contants {
			public static String model;
			public static String release;
			public static String sdk = Build.VERSION.SDK;

			static {
				model = Build.MODEL;
				release = Build.VERSION.RELEASE;
			}
		}

		public static class Screen {
			public static int getDensityDpi(Activity paramActivity) {
				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
				paramActivity.getWindowManager().getDefaultDisplay()
						.getMetrics(localDisplayMetrics);
				return localDisplayMetrics.densityDpi;
			}

			public static double getSize(Activity paramActivity) {
				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
				paramActivity.getWindowManager().getDefaultDisplay()
						.getMetrics(localDisplayMetrics);
				int i = localDisplayMetrics.widthPixels;
				int j = localDisplayMetrics.heightPixels;
				int k = localDisplayMetrics.densityDpi;
				double d = Math.sqrt(Math.pow(i, 2.0D) + Math.pow(j, 2.0D)) / k;
				Log.d("SCREEN", "screenWidth:" + i + ",screenHeight:" + j
						+ ",densityDpi" + k);
				return d;
			}

			public static int[] getWidthHeight(Activity paramActivity) {
				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
				paramActivity.getWindowManager().getDefaultDisplay()
						.getMetrics(localDisplayMetrics);
				return new int[] { localDisplayMetrics.widthPixels,
						localDisplayMetrics.heightPixels };
			}

			public static boolean isHorizontal(Context paramContext) {
				int mCurrentOrientation = paramContext.getResources()
						.getConfiguration().orientation;
				if (2 == mCurrentOrientation)
					return true;
				else
					return false;
			}
		}

		public static class TelManager {
			private static TelephonyManager tm = null;

			public static int getCid(Context paramContext) {
				try {
					initTelephonyManager(paramContext);
					int i = ((GsmCellLocation) tm.getCellLocation()).getCid();
					return i;
				} catch (Exception localException) {
				}
				return -1;
			}

			public static int getLac(Context paramContext) {
				try {
					initTelephonyManager(paramContext);
					int i = ((GsmCellLocation) tm.getCellLocation()).getLac();
					return i;
				} catch (Exception localException) {
				}
				return -1;
			}

			public static String getNetworkOperator(Context paramContext) {
				try {
					initTelephonyManager(paramContext);
					String str = tm.getNetworkOperator();
					return str;
				} catch (Exception localException) {
				}
				return "";
			}

			private static void initTelephonyManager(Context paramContext) {
				if (tm != null)
					return;
				tm = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
			}
		}
	}

	public static class Msg {
		private static Toast to = null;

		public static Integer getMsgInteger(Message paramMessage) {
			if (paramMessage != null)
				return Integer.valueOf(paramMessage.arg1);
			return null;
		}

		public static Object getMsgObject(Message paramMessage) {
			if ((paramMessage != null) && (paramMessage.obj != null))
				return paramMessage.obj;
			return null;
		}

		public static String getMsgString(Message paramMessage) {
			if ((paramMessage != null) && (paramMessage.obj != null))
				return paramMessage.obj.toString();
			return null;
		}

		public static void sendMsg(Handler paramHandler, int paramInt) {
			sendMsg(paramHandler, paramInt, null, null);
		}

		public static void sendMsg(Handler paramHandler, int paramInt,
				Integer paramInteger) {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			if (paramInteger != null)
				localMessage.arg1 = paramInteger.intValue();
			paramHandler.sendMessage(localMessage);
		}

		public static void sendMsg(Handler paramHandler, int paramInt,
				Object paramObject) {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			if (paramObject != null)
				localMessage.obj = paramObject;
			paramHandler.sendMessage(localMessage);
		}

		public static void sendMsg(Handler paramHandler, int paramInt,
				Object paramObject, Integer paramInteger) {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			localMessage.obj = paramObject;
			if (paramInteger != null)
				localMessage.arg1 = paramInteger.intValue();
			paramHandler.sendMessage(localMessage);
		}

		public static void sendMsg(Handler paramHandler, int paramInt,
				String paramString) {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			if (paramString != null)
				localMessage.obj = paramString;
			paramHandler.sendMessage(localMessage);
		}

		public static void sendMsg(Handler paramHandler, int paramInt,
				String paramString, Integer paramInteger) {
			Message localMessage = new Message();
			localMessage.what = paramInt;
			if (paramString != null)
				localMessage.obj = paramString;
			if (paramInteger != null)
				localMessage.arg1 = paramInteger.intValue();
			paramHandler.sendMessage(localMessage);
		}

		public static void showShortText(Context paramContext, int paramInt) {
			Toast.makeText(paramContext,
					CommonHelper.Resource.getString(paramContext, paramInt), 0)
					.show();
		}

		public static void showShortText(Context paramContext,
				String paramString) {
			Toast.makeText(paramContext, paramString, 0).show();
		}
	}

	public static class Path {
		public static String getBaseFull() {
			String str = getSdCard()
					+ BaseProperties.getProperty("store_location", "base")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getBaseImagesFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("images_store_location",
							"images") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getBaseLogFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("log_file_folder", "logs")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getBaseUpdateFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("update_file_folder", "base")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getBaseWebCacheFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("web_cache_location",
							"webcache") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getBizFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("biz_location", "biz")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getConfigFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("conf_folder", "config")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getDownloadPackageWebCacheFull() {
			String str = getBaseWebCacheFull()
					+ BaseProperties.getProperty(
							"download_package_cache_location",
							"downloadpackage") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getElePicFull() {
			String str = getBizFull()
					+ BaseProperties.getProperty("elePic_location", "elePic")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getEleSignFull() {
			String str = getBizFull()
					+ BaseProperties.getProperty("eleSign_location", "eleSign")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getIdCardFull() {
			String str = getBaseFull()
					+ BaseProperties.getProperty("idcard_location", "idcard")
					+ File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getIdCardWebCacheFull() {
			String str = getBaseWebCacheFull()
					+ BaseProperties.getProperty("idcard_cache_location",
							"idcard") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getMennuImageWebCacheFull() {
			String str = getBaseWebCacheFull()
					+ BaseProperties.getProperty("menu_image_cache_location",
							"menuimage") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getOfferImageWebCacheFull() {
			String str = getBaseWebCacheFull()
					+ BaseProperties.getProperty("offer_image_cache_location",
							"offerimages") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getPrintConfigFileWebCacheFull() {
			return getPrintConfigWebCacheFull()
					+ BaseProperties.getProperty("print_config_file_name",
							"bt_idcreader_name2class.properties");
		}

		public static String getPrintConfigWebCacheFull() {
			String str = getBaseWebCacheFull()
					+ BaseProperties.getProperty("print_config_cache_location",
							"print") + File.separator;
			FileCommon.mkdirWithFullName(str);
			return str;
		}

		public static String getSdCard() {
			File localFile = Environment.getExternalStorageDirectory();
			return localFile.getParent() + File.separator + localFile.getName()
					+ File.separator;
		}
	}

	public static class Resource {
		public static String getArrayString(Context paramContext,
				int paramInt1, int paramInt2) {
			return paramContext.getResources().getStringArray(paramInt1)[paramInt2];
		}

		public static Integer getInteger(Context paramContext, int paramInt) {
			String str = paramContext.getResources().getString(paramInt);
			try {
				Integer localInteger = Integer.valueOf(Integer.parseInt(str));
				return localInteger;
			} catch (NumberFormatException localNumberFormatException) {
				localNumberFormatException.printStackTrace();
			}
			return null;
		}

		public static String getString(Context paramContext, int paramInt) {
			return paramContext.getResources().getString(paramInt);
		}
	}

	public static AppInfo getThatInfo(Context paramContext, String paramString) {
		AppInfo localAppInfo = new AppInfo();
		PackageManager localPackageManager = paramContext.getPackageManager();
		try {
			PackageInfo localPackageInfo = localPackageManager.getPackageInfo(
					paramString, 0);
			localAppInfo.appname = localPackageInfo.applicationInfo.loadLabel(
					paramContext.getPackageManager()).toString();
			localAppInfo.pname = localPackageInfo.packageName;
			localAppInfo.versionCode = localPackageInfo.versionCode;
			localAppInfo.versionName = localPackageInfo.versionName;
			localAppInfo.icon = localPackageInfo.applicationInfo
					.loadIcon(paramContext.getPackageManager());
			return localAppInfo;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return new AppInfo();
	}

	public static AppInfo getThisInfo(Context paramContext) {
		AppInfo localAppInfo = new AppInfo();
		PackageManager localPackageManager = paramContext.getPackageManager();
		try {
			PackageInfo localPackageInfo = localPackageManager.getPackageInfo(
					paramContext.getPackageName(), 0);
			localAppInfo.appname = localPackageInfo.applicationInfo.loadLabel(
					paramContext.getPackageManager()).toString();
			localAppInfo.pname = localPackageInfo.packageName;
			localAppInfo.versionCode = localPackageInfo.versionCode;
			localAppInfo.versionName = localPackageInfo.versionName;
			localAppInfo.icon = localPackageInfo.applicationInfo
					.loadIcon(paramContext.getPackageManager());
			return localAppInfo;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return new AppInfo();
	}

	public static class AppInfo {
		public String appname = "";
		public Drawable icon;
		public String pname = "";
		public int versionCode = 0;
		public String versionName = "";

		public String print() {
			return this.appname + "\t" + this.pname + "\t" + this.versionName
					+ "\t" + this.versionCode + "\t";
		}
	}

	public static class Tran {
		public static Bitmap drawableToBitmap(Drawable paramDrawable) {
			if (paramDrawable == null)
				return null;
			int i = paramDrawable.getIntrinsicWidth();
			int j = paramDrawable.getIntrinsicHeight();
			if (paramDrawable.getOpacity() != -1)
				;
			for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;; localConfig = Bitmap.Config.RGB_565) {
				Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
				Canvas localCanvas = new Canvas(localBitmap);
				paramDrawable.setBounds(0, 0,
						paramDrawable.getIntrinsicWidth(),
						paramDrawable.getIntrinsicHeight());
				paramDrawable.draw(localCanvas);
				return localBitmap;
			}
		}
	}
}
