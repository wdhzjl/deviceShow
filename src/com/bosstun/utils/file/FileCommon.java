package com.bosstun.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class FileCommon {
	static final int bufferSize = 20480;

	public static String byteArr2HexStr(byte[] paramArrayOfByte)
			throws Exception {
		int iLen = paramArrayOfByte.length;
		StringBuffer localStringBuffer = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = paramArrayOfByte[i];
			while (intTmp < 0) {
				intTmp += 256;
			}
			if (intTmp < 16) {
				localStringBuffer.append("0");
			} else {
				localStringBuffer.append(Integer.toString(intTmp, 16));
			}
		}
		return localStringBuffer.toString();
	}

	public static void delFolderAndFiles(File paramFile) {
		if (paramFile == null)
			return;

		if (!paramFile.isDirectory()) {
			paramFile.delete();
			return;
		}
		File[] arrayOfFile = paramFile.listFiles();
		if (arrayOfFile == null) {
			return;
		}
		for (int i = 0; i < arrayOfFile.length; i++) {
			delFolderAndFiles(arrayOfFile[i]);
		}

	}

	public static void delFolderAndFiles(String paramString) {
		delFolderAndFiles(new File(paramString));
	}

	private static int getNextSize(byte[] line) {
		if (null == line) {
			return -1;
		}
		int len = line.length;
		if (len <= 6 && len > 2 && line[len - 2] == '\r'
				&& line[len - 1] == '\n') {
			String str = new String(line, 0, len - 2);
			return Integer.parseInt(str, 16);
		}
		return -1;
	}

	public static byte[] hexStr2ByteArr(String paramString) throws Exception {
		byte[] arrayOfByte1 = paramString.getBytes();
		int i = arrayOfByte1.length;
		byte[] arrayOfByte2 = new byte[i / 2];
		for (int j = 0;; j += 2) {
			if (j >= i)
				return arrayOfByte2;
			String str = new String(arrayOfByte1, j, 2);
			arrayOfByte2[(j / 2)] = (byte) Integer.parseInt(str, 16);
		}
	}

	public static boolean isTotalFileExist(String path, String[] fileNames) {
		for (String fileName : fileNames) {
			if (!new File(path + fileName).exists())
				return false;
		}
		return true;
	}

	public static void mkdirWithFullName(String fullPath) {
		int tag = fullPath.lastIndexOf("/");
		if ((tag == -1) || (tag == 0))
			return;
		File localFile = new File(fullPath.substring(0,
				fullPath.lastIndexOf("/")));
		if (localFile.exists())
			return;
		localFile.mkdirs();
	}

	public static void printHexString(byte[] paramArrayOfByte) {
		StringBuffer strBuf = new StringBuffer(paramArrayOfByte.length);
		for (byte bee : paramArrayOfByte) {
			String str = Integer.toHexString(0xFF & bee);
			if (str.length() == 1) {
				str = '0' + str;
			}
			strBuf.append(str);
		}
		System.out.print(strBuf.toString().toUpperCase(Locale.US));
	}

	public static byte[] readFileToByte(String paramString) throws IOException {
		FileInputStream localFileInputStream = new FileInputStream(new File(
				paramString));
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[20480];
		while (true) {
			int i = localFileInputStream.read(arrayOfByte);
			if (-1 == i) {
				localFileInputStream.close();
				return localByteArrayOutputStream.toByteArray();
			}
			localByteArrayOutputStream.write(arrayOfByte, 0, i);
		}

	}

	public static String readFileToString(String paramString1,
			String paramString2) throws IOException {
		FileInputStream localFileInputStream = new FileInputStream(new File(
				paramString1));
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte1 = new byte[20480];
		while (true) {
			int i = localFileInputStream.read(arrayOfByte1);
			if (-1 == i) {
				byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
				localFileInputStream.close();
				if (paramString2 == null)
					paramString2 = "utf-8";
				return new String(arrayOfByte2, paramString2);
			}
			localByteArrayOutputStream.write(arrayOfByte1, 0, i);
		}
	}

	public static void readIn(InputStream ins,
			ByteArrayOutputStream byteOut)
			throws IOException {
		byte[] line = readRawLine(ins);
		if (line == null)
			return;
		byte[] nextLine;
		if (getNextSize(line) >= 0) {
			nextLine = readRawLine(ins);
			if (nextLine == null) {
				return;
			}else{
				int len = nextLine.length;
				int tail = nextLine[(len - 2)] == '\r' ? (len-2) : len;
				byteOut.write(nextLine, 0, tail);
			}
		}else{
			byteOut.write(line);
			readIn(ins, byteOut);
			return;
		}
	}

	public static byte[] readInputStreamToByte(InputStream paramInputStream)
			throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[20480];
		while (true) {
			int i = paramInputStream.read(arrayOfByte);
			if (-1 == i)
				return localByteArrayOutputStream.toByteArray();
			localByteArrayOutputStream.write(arrayOfByte, 0, i);
		}
	}

	private static byte[] readRawLine(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int ch = 0;
		while((ch = inputStream.read()) >=0){
			buf.write(ch);
			if(ch == '\r')
				break;
		}
		if(buf.size() == 0)
			return null;
		return buf.toByteArray();
	}

	// ERROR //
	public static void writeFile(String paramString1, String paramString2) {
		// Byte code:
		// 0: aconst_null
		// 1: astore_2
		// 2: aload_0
		// 3: invokestatic 183 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 6: new 185 java/io/FileOutputStream
		// 9: dup
		// 10: aload_0
		// 11: invokespecial 186 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;)V
		// 14: astore 7
		// 16: aload 7
		// 18: aload_1
		// 19: invokevirtual 77 java/lang/String:getBytes ()[B
		// 22: invokevirtual 187 java/io/FileOutputStream:write ([B)V
		// 25: aload 7
		// 27: invokevirtual 190 java/io/FileOutputStream:flush ()V
		// 30: aload 7
		// 32: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 35: return
		// 36: astore 5
		// 38: aload 5
		// 40: invokevirtual 196 java/lang/Exception:printStackTrace ()V
		// 43: aload_2
		// 44: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 47: return
		// 48: astore 6
		// 50: return
		// 51: astore_3
		// 52: aload_2
		// 53: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 56: aload_3
		// 57: athrow
		// 58: astore 8
		// 60: return
		// 61: astore 4
		// 63: goto -7 -> 56
		// 66: astore_3
		// 67: aload 7
		// 69: astore_2
		// 70: goto -18 -> 52
		// 73: astore 5
		// 75: aload 7
		// 77: astore_2
		// 78: goto -40 -> 38
		//
		// Exception table:
		// from to target type
		// 2 16 36 java/lang/Exception
		// 43 47 48 java/io/IOException
		// 2 16 51 finally
		// 38 43 51 finally
		// 30 35 58 java/io/IOException
		// 52 56 61 java/io/IOException
		// 16 30 66 finally
		// 16 30 73 java/lang/Exception
	}

	// ERROR //
	public static void writeFileByAppend(String paramString1,
			String paramString2) {
		// Byte code:
		// 0: aconst_null
		// 1: astore_2
		// 2: aload_0
		// 3: invokestatic 183 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 6: new 185 java/io/FileOutputStream
		// 9: dup
		// 10: aload_0
		// 11: iconst_1
		// 12: invokespecial 200 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;Z)V
		// 15: astore 7
		// 17: aload 7
		// 19: aload_1
		// 20: invokevirtual 77 java/lang/String:getBytes ()[B
		// 23: invokevirtual 187 java/io/FileOutputStream:write ([B)V
		// 26: aload 7
		// 28: invokevirtual 190 java/io/FileOutputStream:flush ()V
		// 31: aload 7
		// 33: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 36: return
		// 37: astore 5
		// 39: aload 5
		// 41: invokevirtual 196 java/lang/Exception:printStackTrace ()V
		// 44: aload_2
		// 45: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 48: return
		// 49: astore 6
		// 51: return
		// 52: astore_3
		// 53: aload_2
		// 54: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 57: aload_3
		// 58: athrow
		// 59: astore 8
		// 61: return
		// 62: astore 4
		// 64: goto -7 -> 57
		// 67: astore_3
		// 68: aload 7
		// 70: astore_2
		// 71: goto -18 -> 53
		// 74: astore 5
		// 76: aload 7
		// 78: astore_2
		// 79: goto -40 -> 39
		//
		// Exception table:
		// from to target type
		// 2 17 37 java/lang/Exception
		// 44 48 49 java/io/IOException
		// 2 17 52 finally
		// 39 44 52 finally
		// 31 36 59 java/io/IOException
		// 53 57 62 java/io/IOException
		// 17 31 67 finally
		// 17 31 74 java/lang/Exception
	}

	// ERROR //
	public static void writeFileByByte(String paramString,
			byte[] paramArrayOfByte) {
		// Byte code:
		// 0: aconst_null
		// 1: astore_2
		// 2: aload_0
		// 3: invokestatic 183 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 6: new 185 java/io/FileOutputStream
		// 9: dup
		// 10: aload_0
		// 11: invokespecial 186 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;)V
		// 14: astore 7
		// 16: aload 7
		// 18: aload_1
		// 19: invokevirtual 187 java/io/FileOutputStream:write ([B)V
		// 22: aload 7
		// 24: invokevirtual 190 java/io/FileOutputStream:flush ()V
		// 27: aload 7
		// 29: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 32: return
		// 33: astore 5
		// 35: aload 5
		// 37: invokevirtual 196 java/lang/Exception:printStackTrace ()V
		// 40: aload_2
		// 41: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 44: return
		// 45: astore 6
		// 47: return
		// 48: astore_3
		// 49: aload_2
		// 50: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 53: aload_3
		// 54: athrow
		// 55: astore 8
		// 57: return
		// 58: astore 4
		// 60: goto -7 -> 53
		// 63: astore_3
		// 64: aload 7
		// 66: astore_2
		// 67: goto -18 -> 49
		// 70: astore 5
		// 72: aload 7
		// 74: astore_2
		// 75: goto -40 -> 35
		//
		// Exception table:
		// from to target type
		// 2 16 33 java/lang/Exception
		// 40 44 45 java/io/IOException
		// 2 16 48 finally
		// 35 40 48 finally
		// 27 32 55 java/io/IOException
		// 49 53 58 java/io/IOException
		// 16 27 63 finally
		// 16 27 70 java/lang/Exception
	}

	// ERROR //
	public static void writeFileByInputStream(String paramString,
			InputStream paramInputStream) {
		// Byte code:
		// 0: aconst_null
		// 1: astore_2
		// 2: aload_1
		// 3: invokestatic 206
		// com/base/utils/file/FileCommon:readInputStreamToByte
		// (Ljava/io/InputStream;)[B
		// 6: astore 7
		// 8: aload_0
		// 9: invokestatic 183 com/base/utils/file/FileCommon:mkdirWithFullName
		// (Ljava/lang/String;)V
		// 12: new 185 java/io/FileOutputStream
		// 15: dup
		// 16: aload_0
		// 17: invokespecial 186 java/io/FileOutputStream:<init>
		// (Ljava/lang/String;)V
		// 20: astore 8
		// 22: aload 8
		// 24: aload 7
		// 26: invokevirtual 187 java/io/FileOutputStream:write ([B)V
		// 29: aload 8
		// 31: invokevirtual 190 java/io/FileOutputStream:flush ()V
		// 34: aload 8
		// 36: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 39: return
		// 40: astore 5
		// 42: aload 5
		// 44: invokevirtual 196 java/lang/Exception:printStackTrace ()V
		// 47: aload_2
		// 48: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 51: return
		// 52: astore 6
		// 54: return
		// 55: astore_3
		// 56: aload_2
		// 57: invokevirtual 193 java/io/FileOutputStream:close ()V
		// 60: aload_3
		// 61: athrow
		// 62: astore 9
		// 64: return
		// 65: astore 4
		// 67: goto -7 -> 60
		// 70: astore_3
		// 71: aload 8
		// 73: astore_2
		// 74: goto -18 -> 56
		// 77: astore 5
		// 79: aload 8
		// 81: astore_2
		// 82: goto -40 -> 42
		//
		// Exception table:
		// from to target type
		// 2 22 40 java/lang/Exception
		// 47 51 52 java/io/IOException
		// 2 22 55 finally
		// 42 47 55 finally
		// 34 39 62 java/io/IOException
		// 56 60 65 java/io/IOException
		// 22 34 70 finally
		// 22 34 77 java/lang/Exception
	}
}