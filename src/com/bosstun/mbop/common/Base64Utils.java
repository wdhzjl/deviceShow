package com.bosstun.mbop.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.util.Base64;
public class Base64Utils
{
  public static void decoderBase64File(String paramString1, String paramString2)
    throws Exception
  {
    byte[] arrayOfByte = Base64.decode(paramString1, 0);
    FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
    localFileOutputStream.write(arrayOfByte);
    localFileOutputStream.close();
  }

  public static String encodeBase64File(String paramString)
    throws Exception
  {
    File localFile = new File(paramString);
    FileInputStream localFileInputStream = new FileInputStream(localFile);
    byte[] arrayOfByte = new byte[(int)localFile.length()];
    localFileInputStream.read(arrayOfByte);
    localFileInputStream.close();
    return Base64.encodeToString(arrayOfByte, 0);
  }

  public static void toFile(String paramString1, String paramString2)
    throws Exception
  {
    byte[] arrayOfByte = paramString1.getBytes();
    FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
    localFileOutputStream.write(arrayOfByte);
    localFileOutputStream.close();
  }
}