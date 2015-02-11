package com.bosstun.utils.cfg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesHelper
{
  Properties p;

  public PropertiesHelper(Properties paramProperties)
  {
    this.p = paramProperties;
  }

  public static Properties loadByFilePath(String paramString)
  {
    Properties localProperties = new Properties();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localProperties.load(localFileInputStream);
      localFileInputStream.close();
      return localProperties;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      System.out.println("装载文件--->失败!");
      localIOException.printStackTrace();
    }
    return null;
  }

  public void clear()
  {
    this.p.clear();
  }

  public Set<Map.Entry<Object, Object>> entrySet()
  {
    return this.p.entrySet();
  }

  public Boolean getBoolean(String paramString)
  {
    if (getProperty(paramString) == null)
      return null;
    return Boolean.valueOf(Boolean.parseBoolean(getRequiredProperty(paramString)));
  }

  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    if (getProperty(paramString) == null)
      return paramBoolean;
    return Boolean.parseBoolean(getRequiredProperty(paramString));
  }

  public int getInt(String paramString, int paramInt)
  {
    if (getProperty(paramString) == null)
      return paramInt;
    return Integer.parseInt(getRequiredProperty(paramString));
  }

  public Integer getInt(String paramString)
  {
    if (getProperty(paramString) == null)
      return null;
    return Integer.valueOf(Integer.parseInt(getRequiredProperty(paramString)));
  }

  public String getNullIfBlank(String paramString)
  {
    String str = getProperties().getProperty(paramString);
    if ((str == null) || ("".equals(str.trim())))
      str = null;
    return str;
  }

  public Properties getProperties()
  {
    return this.p;
  }

  public String getProperty(String paramString)
  {
    return getProperties().getProperty(paramString);
  }

  public String getProperty(String paramString1, String paramString2)
  {
    return getProperties().getProperty(paramString1, paramString2);
  }

  public List<String[]> getPropertysPre(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.p.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      String str = (String)localIterator.next();
      if (!str.startsWith(paramString))
        continue;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = str;
      arrayOfString[1] = this.p.getProperty(str);
      localArrayList.add(arrayOfString);
    }
  }

  public boolean getRequiredBoolean(String paramString)
  {
    return Boolean.parseBoolean(getRequiredProperty(paramString));
  }

  public int getRequiredInt(String paramString)
  {
    return Integer.parseInt(getRequiredProperty(paramString));
  }

  public long getRequiredLong(String paramString)
  {
    return Long.parseLong(getRequiredProperty(paramString));
  }

  public String getRequiredProperty(String paramString)
  {
    String str = getProperty(paramString);
    if ((str == null) || ("".equals(str.trim())))
      throw new IllegalStateException("required property is blank by key=" + paramString);
    return str;
  }

  public Enumeration<?> propertyNames()
  {
    return this.p.propertyNames();
  }

  public PropertiesHelper setProperty(String paramString1, String paramString2)
  {
    this.p.setProperty(paramString1, paramString2);
    return this;
  }
}