package com.bosstun.utils.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class BaseProperties
{
  private static String PROPERTIES_FILE_NAME = "assets/base.properties";
  public static PropertiesHelper props;

  private static PropertiesHelper getHelper()
  {
    if (props == null)
      loadProperties();
    return props;
  }

  public static String getNullIfBlank(String paramString)
  {
    return getHelper().getNullIfBlank(paramString);
  }

  public static String getPROPERTIES_FILE_NAME()
  {
    return PROPERTIES_FILE_NAME;
  }

  public static Properties getProperties()
  {
    return getHelper().getProperties();
  }

  public static String getProperty(String paramString)
  {
    return getHelper().getProperty(paramString);
  }

  public static String getProperty(String paramString1, String paramString2)
  {
    return getHelper().getProperty(paramString1, paramString2);
  }

  public static boolean getRequiredBoolean(String paramString)
  {
    return getHelper().getRequiredBoolean(paramString);
  }

  public static int getRequiredInt(String paramString)
  {
    return getHelper().getRequiredInt(paramString);
  }

  public static String getRequiredProperty(String paramString)
  {
    return getHelper().getRequiredProperty(paramString);
  }

  public static Properties loadAllPropertiesByClassLoader(String paramString)
    throws IOException
  {
    Properties localProperties = new Properties();
    Enumeration localEnumeration = BaseProperties.class.getClassLoader().getResources(paramString);
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return localProperties;
      URL localURL = (URL)localEnumeration.nextElement();
      InputStream localInputStream = null;
      try
      {
        URLConnection localURLConnection = localURL.openConnection();
        localURLConnection.setUseCaches(false);
        localInputStream = localURLConnection.getInputStream();
        localProperties.load(localInputStream);
        if (localInputStream != null);
        localInputStream.close();
      }
      finally
      {
        if (localInputStream != null)
          localInputStream.close();
      }
    }
  }

  private static void loadProperties()
  {
    try
    {
      System.out.println("Load [generator.properties] from classpath");
      props = new PropertiesHelper(loadAllPropertiesByClassLoader(PROPERTIES_FILE_NAME));
      Iterator localIterator = props.entrySet().iterator();
      if (!localIterator.hasNext())
      {
        System.out.println("no properties");
        return;
      }
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      System.out.println("[Property] " + localEntry.getKey() + "=" + localEntry.getValue());
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Load Properties error", localIOException);
    }
  }

  public static void reloadProperties(String paramString)
  {
    if (paramString != null)
      PROPERTIES_FILE_NAME = paramString;
    props = null;
    loadProperties();
  }

  public static void setProperties(Properties paramProperties)
  {
    props = new PropertiesHelper(paramProperties);
  }

  public static void setProperty(String paramString1, String paramString2)
  {
    getHelper().setProperty(paramString1, paramString2);
  }
}