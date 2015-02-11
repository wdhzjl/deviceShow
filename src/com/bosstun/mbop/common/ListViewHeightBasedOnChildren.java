package com.bosstun.mbop.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewHeightBasedOnChildren
{
  public static void setListViewHeightBasedOnChildren(ListView paramListView)
  {
    ListAdapter localListAdapter = paramListView.getAdapter();
    if (localListAdapter == null)
      return;
    int i = 0;
    for (int j = 0; ; ++j)
    {
      if (j >= localListAdapter.getCount())
      {
        ViewGroup.LayoutParams localLayoutParams = paramListView.getLayoutParams();
        localLayoutParams.height = (i + paramListView.getDividerHeight() * (-1 + localListAdapter.getCount()));
        ((ViewGroup.MarginLayoutParams)localLayoutParams).setMargins(10, 10, 10, 10);
        paramListView.setLayoutParams(localLayoutParams);
        return;
      }
      View localView = localListAdapter.getView(j, null, paramListView);
      localView.measure(0, 0);
      i += localView.getMeasuredHeight();
    }
  }
}