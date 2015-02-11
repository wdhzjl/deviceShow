package com.bosstun.mbop.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;

import com.bosstun.utils.file.FileCommon;
import com.bosstun.utils.helper.CommonHelper;

public class BizFileHelper {
	public static String bizPath = CommonHelper.Path.getBizFull();

	public static void base64ToBitmapFile(String paramString1,
			String paramString2) throws Exception {
		FileCommon.mkdirWithFullName(bizPath + paramString2);
		Base64Utils.decoderBase64File(paramString1, bizPath + paramString2);
	}

	public static String bitmapToBase64Str(String paramString) throws Exception {
		return Base64Utils.encodeBase64File(bizPath + paramString);
	}

	public static boolean deleteFile(String paramString) {
		return new File(bizPath + paramString).delete();
	}

	public static String[] getFileList(String paramString) {
		return new File(bizPath + paramString).list();
	}

	public static String getLocalFileUrl(String paramString) {
		return "file:" + bizPath + paramString;
	}

	// ERROR //
	public static void saveBitmap(android.graphics.Bitmap paramBitmap,
			String paramString) {
		// Byte code:
		// 0: new 25 java/lang/StringBuilder
		// 3: dup
		// 4: getstatic 16 com/newland/mbop/common/BizFileHelper:bizPath
		// Ljava/lang/String;
		// 7: invokestatic 31 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 10: invokespecial 34 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 13: aload_1
		// 14: invokevirtual 38 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 17: invokevirtual 41 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 20: invokestatic 46 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 23: new 78 java/io/FileOutputStream
		// 26: dup
		// 27: new 25 java/lang/StringBuilder
		// 30: dup
		// 31: getstatic 16 com/newland/mbop/common/BizFileHelper:bizPath
		// Ljava/lang/String;
		// 34: invokestatic 31 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 37: invokespecial 34 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 40: aload_1
		// 41: invokevirtual 38 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 44: invokevirtual 41 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 47: invokespecial 79 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;)V
		// 50: astore_2
		// 51: aload_0
		// 52: getstatic 85 android/graphics/Bitmap$CompressFormat:PNG
		// Landroid/graphics/Bitmap$CompressFormat;
		// 55: bipush 90
		// 57: aload_2
		// 58: invokevirtual 91 android/graphics/Bitmap:compress
		// (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
		// 61: pop
		// 62: aload_2
		// 63: invokevirtual 94 java/io/FileOutputStream:close ()V
		// 66: aload_0
		// 67: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 70: return
		// 71: astore_3
		// 72: aload_3
		// 73: invokevirtual 100 java/lang/Exception:printStackTrace ()V
		// 76: aload_0
		// 77: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 80: return
		// 81: astore 4
		// 83: aload_0
		// 84: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 87: aload 4
		// 89: athrow
		// 90: astore 4
		// 92: goto -9 -> 83
		// 95: astore_3
		// 96: goto -24 -> 72
		//
		// Exception table:
		// from to target type
		// 23 51 71 java/lang/Exception
		// 23 51 81 finally
		// 72 76 81 finally
		// 51 66 90 finally
		// 51 66 95 java/lang/Exception
	}

	// ERROR //
	public static void saveBitmap(android.graphics.Bitmap paramBitmap,
			String paramString, int paramInt) {
		// Byte code:
		// 0: new 25 java/lang/StringBuilder
		// 3: dup
		// 4: getstatic 16 com/newland/mbop/common/BizFileHelper:bizPath
		// Ljava/lang/String;
		// 7: invokestatic 31 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 10: invokespecial 34 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 13: aload_1
		// 14: invokevirtual 38 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 17: invokevirtual 41 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 20: invokestatic 46 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 23: new 78 java/io/FileOutputStream
		// 26: dup
		// 27: new 25 java/lang/StringBuilder
		// 30: dup
		// 31: getstatic 16 com/newland/mbop/common/BizFileHelper:bizPath
		// Ljava/lang/String;
		// 34: invokestatic 31 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 37: invokespecial 34 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 40: aload_1
		// 41: invokevirtual 38 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 44: invokevirtual 41 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 47: invokespecial 79 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;)V
		// 50: astore_3
		// 51: aload_0
		// 52: getstatic 104 android/graphics/Bitmap$CompressFormat:JPEG
		// Landroid/graphics/Bitmap$CompressFormat;
		// 55: iload_2
		// 56: aload_3
		// 57: invokevirtual 91 android/graphics/Bitmap:compress
		// (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
		// 60: pop
		// 61: aload_3
		// 62: invokevirtual 94 java/io/FileOutputStream:close ()V
		// 65: aload_0
		// 66: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 69: return
		// 70: astore 4
		// 72: aload 4
		// 74: invokevirtual 100 java/lang/Exception:printStackTrace ()V
		// 77: aload_0
		// 78: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 81: return
		// 82: astore 5
		// 84: aload_0
		// 85: invokevirtual 97 android/graphics/Bitmap:recycle ()V
		// 88: aload 5
		// 90: athrow
		// 91: astore 5
		// 93: goto -9 -> 84
		// 96: astore 4
		// 98: goto -26 -> 72
		//
		// Exception table:
		// from to target type
		// 23 51 70 java/lang/Exception
		// 23 51 82 finally
		// 72 77 82 finally
		// 51 65 91 finally
		// 51 65 96 java/lang/Exception
	}

	// ERROR //
	public static void saveBitmapForJPG(Bitmap bitmap, String file) {
		File f = new File(file);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}