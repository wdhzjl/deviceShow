package com.bosstun.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bosstun.bean.MenuInfo;
import com.bosstun.mbop.common.GetPhoneInfoUtil;
import com.bosstun.mbop.common.ImageProcessUtil;
import com.example.deviceshow.R;


public class MenuGridViewAdapter extends BaseAdapter
{
  private int colNum = 0;
  private Context context;
  private int gridHeight;
  public GridView gridView = null;
  private int gridWidth = 0;
  private int horizen_spacing = 6;
  private LayoutInflater inflater;
  private List<MenuInfo> menuList = null;
  private int rowNum = 0;
  private int vertral_spacing = 15;

  public MenuGridViewAdapter(Activity paramActivity, List<MenuInfo> paramList, GridView paramGridView)
  {
////    boolean blarge = paramActivity.getResources().getBoolean(R.bool.large);
////    boolean bxhdpi = paramActivity.getResources().getBoolean(R.bool.xhdpi);
//    this.gridHeight = (((Integer)GetPhoneInfoUtil.getScreenDisplay(paramActivity).get("height")).intValue() - ImageProcessUtil.dipToPx(paramActivity, 55.0F));
//    this.gridWidth = (((Integer)GetPhoneInfoUtil.getScreenDisplay(paramActivity).get("width")).intValue() - ImageProcessUtil.dipToPx(paramActivity, 2.0F));
//    if ((blarge) && (bxhdpi))
//    	this.colNum = 6;
//    else{
//    	this.colNum = 5;
//    }
    this.rowNum = 5;
    this.gridView = paramGridView;
    this.gridView.setNumColumns(this.colNum);
    this.gridView.setHorizontalSpacing(ImageProcessUtil.dipToPx(paramActivity, this.horizen_spacing));
    this.gridView.setVerticalSpacing(ImageProcessUtil.dipToPx(paramActivity, this.vertral_spacing));
    this.context = paramActivity;
    this.inflater = LayoutInflater.from(paramActivity);
    this.menuList = paramList;
  }

  public int getCount()
  {
    if (this.menuList != null)
      return this.menuList.size();
    return 0;
  }

  public Object getItem(int paramInt)
  {
    if (this.menuList != null)
      return (MenuInfo)this.menuList.get(paramInt);
    return null;
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    if (paramView == null)
    {
//      paramView = this.inflater.inflate(R.layout.meunitem, null);
      localViewHolder = new ViewHolder();
//      localViewHolder.textView = ((TextView)paramView.findViewById(R.id.ItemText));
//      localViewHolder.image = ((ImageView)paramView.findViewById(R.id.ItemImage));
      int width = (this.gridWidth - ImageProcessUtil.dipToPx(this.context, this.horizen_spacing) * (1 + this.colNum)) / this.colNum;
      int height = (this.gridHeight - ImageProcessUtil.dipToPx(this.context, this.vertral_spacing) * (1 + this.rowNum)) / this.rowNum;
      AbsListView.LayoutParams localLayoutParams = new AbsListView.LayoutParams(width, height);
      this.gridView.setColumnWidth(width);
      paramView.setLayoutParams(localLayoutParams);
      paramView.setTag(localViewHolder);
    }else{
    	localViewHolder = (ViewHolder)paramView.getTag();
    }
    MenuInfo localMenuInfo = (MenuInfo)this.menuList.get(paramInt);
    localViewHolder.setText(localMenuInfo.getItemText());
    localViewHolder.setImageResource(localMenuInfo.getItemImage());
    return paramView;
  }
}