package com.newland.nativepackage;

public class IDCard {

	public static native int closeIDCard();

	public static native int getIDCardVersion(byte[] paramArrayOfByte);

	public static native int getIdCardInfo(String[] paramArrayOfString,
			byte[] paramArrayOfByte);

	public static native int initialIDCard();

	public static native int openIDCard(int paramInt, String paramString1,
			String paramString2);
}

/*
 * Location: D:\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar Qualified
 * Name: com.newland.nativepackage.IDCard JD-Core Version: 0.5.4
 */