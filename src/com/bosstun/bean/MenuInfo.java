package com.bosstun.bean;

public class MenuInfo
{
  private int itemImage;
  private String itemText;

  public MenuInfo(int paramInt, String paramString)
  {
    this.itemImage = paramInt;
    this.itemText = paramString;
  }

  public int getItemImage()
  {
    return this.itemImage;
  }

  public String getItemText()
  {
    return this.itemText;
  }

  public void setItemImage(int paramInt)
  {
    this.itemImage = paramInt;
  }

  public void setItemText(String paramString)
  {
    this.itemText = paramString;
  }
}