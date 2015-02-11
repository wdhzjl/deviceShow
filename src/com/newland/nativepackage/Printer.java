package com.newland.nativepackage;

public class Printer {
	public static native int closePrinter();

	public static native int getPrinterVersion(byte[] paramArrayOfByte);

	public static native int initialPrinter();

	public static native int openPrinter(int paramInt, String paramString1,
			String  paramString2);

	public static native int print(String paramString);

	public static native int printHTML(String paramString);

	public static native int setAlignType(int paramInt);

	public static native int setBold(int paramInt);

	public static native int setInverse(int paramInt);

	public static native int setLeftMargin(int paramInt);

	public static native int setLineSpacingByDotPitch(int paramInt);

	public static native int setPrintOrientation(int paramInt);

	public static native int setRightMargin(int paramInt);

	public static native int setUnderLine(int paramInt);

	public static native int setWordSpacingByDotPitch(int paramInt);

	public static native int setZoonIn(int paramInt1, int paramInt2);
}
