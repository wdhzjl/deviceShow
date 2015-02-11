package com.bosstun.mbop.view.custom;

import com.example.deviceshow.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



public class CustomDialog extends Dialog
{
  private final int CUSTOMVIEWMODE = 4;
  private final int ITEMSMODE = 5;
  private final int MSGMODE = 1;
  private final int MULTICHOICEMODE = 3;
  private final int SINGLECHOICEMODE = 2;
  private View customView;
  private boolean hasNegativeButton = false;
  private boolean hasNeutralButton = false;
  private boolean hasPositiveButton = false;
  private boolean hasTitle = true;
  private LayoutInflater inflater;
  private int mCheckedItem = -1;
  public  boolean[] mCheckedItems;
  protected Activity mContext;
  public Cursor mCursor = null;
  public CharSequence[] mItems;
  private int mMode;
  private Button mNegativeButton;
  private DialogInterface.OnClickListener mNegativeButtonListener;
  private CharSequence mNegativeButtonText;
  private Button mNeutralButton;
  private DialogInterface.OnClickListener mNeutralButtonListener;
  private CharSequence mNeutralButtonText;
  private DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
  public DialogInterface.OnClickListener mOnClickListener;
  private Button mPositiveButton;
  private DialogInterface.OnClickListener mPositiveButtonListener;
  private CharSequence mPositiveButtonText;
  private CharSequence msg;
  private int titleImageId;
  private ImageView titleImageView;
  private CharSequence titleText;
  private TextView titleTextView;
  private LinearLayout topPanel;
  private int which = 0;

  public CustomDialog(Context paramContext)
  {
    super(paramContext, R.style.MyDialog);
    this.mContext = ((Activity)paramContext);
  }

  private void init()
  {
    this.titleTextView = ((TextView)findViewById(R.id.titleText));
    this.titleImageView = ((ImageView)findViewById(R.id.titleImage));
    this.mPositiveButton = ((Button)findViewById(R.id.button1));
    this.topPanel = ((LinearLayout)findViewById(R.id.topPanel));
    this.mPositiveButton.setText(this.mPositiveButtonText);
    if (this.hasPositiveButton)
      this.mPositiveButton.setVisibility(View.VISIBLE);
    this.mPositiveButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CustomDialog.this.mPositiveButtonListener.onClick(CustomDialog.this, CustomDialog.this.which);
        CustomDialog.this.dismiss();
      }
    });
    this.mNegativeButton = ((Button)findViewById(R.id.button2));
    this.mNegativeButton.setText(this.mNegativeButtonText);
    if (this.hasNegativeButton)
      this.mNegativeButton.setVisibility(View.VISIBLE);
    this.mNegativeButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CustomDialog.this.mNegativeButtonListener.onClick(CustomDialog.this, CustomDialog.this.which);
        CustomDialog.this.dismiss();
      }
    });
    this.mNeutralButton = ((Button)findViewById(R.id.button3));
    this.mNeutralButton.setText(this.mNeutralButtonText);
    if (this.hasNeutralButton)
      this.mNeutralButton.setVisibility(View.VISIBLE);
    this.mNeutralButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CustomDialog.this.mNeutralButtonListener.onClick(CustomDialog.this, CustomDialog.this.which);
      }
    });
  }

  private void setButtonContent()
  {
    if ((this.hasPositiveButton) || (this.hasNegativeButton) || (this.hasNeutralButton))
      return;
    findViewById(R.id.buttonPanel).setVisibility(View.GONE);
  }

  private void setCutomViewContent()
  {
    ((FrameLayout)findViewById(R.id.custom)).addView(this.customView);
    findViewById(R.id.buttonPanel).setVisibility(View.GONE);
  }

  private void setItemsContent()
  {
    final ListView localListView = (ListView)this.inflater.inflate(R.layout.select_dialog, null);
    localListView.setAdapter(new ArrayAdapter(this.mContext, R.layout.select_dialog_item, R.id.text1, this.mItems));
    if (this.mOnClickListener != null)
      localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          localListView.setChoiceMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
          CustomDialog.this.mOnClickListener.onClick(CustomDialog.this, paramInt);
        }
      });
    ((FrameLayout)findViewById(R.id.custom)).addView(localListView);
  }

  private void setMegContent()
  {
    View localView = this.inflater.inflate(R.layout.dialog_msg, null);
    ((TextView)localView.findViewById(R.id.dialogMsg)).setText(this.msg);
    ((FrameLayout)findViewById(R.id.custom)).addView(localView);
  }

  @SuppressWarnings("unchecked")
private void setMultiChoiceContent()
  {
    final ListView localListView = (ListView)this.inflater.inflate(R.layout.select_dialog, null);
    Cursor localCursor = this.mCursor;
    ArrayAdapter local2 = null;
    if (localCursor == null)
      local2 = new ArrayAdapter(this.mContext, R.layout.select_dialog_checked_item, R.id.text1, this.mItems)
      {
        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
        {
          CustomDialog.ViewHolder localViewHolder = null;
          if (paramView == null)
          {
            paramView = CustomDialog.this.inflater.inflate(R.layout.select_dialog_checked_item, null);
            localViewHolder = new CustomDialog.ViewHolder();
            localViewHolder.text = ((TextView)paramView.findViewById(R.id.text));
            localViewHolder.check = ((CheckBox)paramView.findViewById(R.id.radio));
            paramView.setTag(localViewHolder);
          }
          else{
        	localViewHolder = (CustomDialog.ViewHolder)paramView.getTag();
            localViewHolder.check.setButtonDrawable(R.drawable.btn_check);
            localViewHolder.check.setChecked(CustomDialog.this.mCheckedItems[paramInt]);
            localViewHolder.text.setText(CustomDialog.this.mItems[paramInt]);
          }
          	return paramView;
        }
      };
    localListView.setAdapter(local2);
    if (this.mOnCheckboxClickListener != null)
      localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          boolean[] arrayOfBoolean = CustomDialog.this.mCheckedItems;
          for (int i = 0; i<arrayOfBoolean.length; i++)
          {
            ArrayAdapter localArrayAdapter = (ArrayAdapter)localListView.getAdapter();
            CustomDialog.this.mOnCheckboxClickListener.onClick(CustomDialog.this, paramInt, arrayOfBoolean[i]);
            if(arrayOfBoolean[i]){
            	localArrayAdapter.notifyDataSetChanged();
            }
          }
        }
      });
    ((FrameLayout)findViewById(R.id.custom)).addView(localListView);
  }
/*
  private void setSingleChoiceContent()
  {
    ListView localListView = (ListView)this.inflater.inflate(2130903060, null);
    localListView.setAdapter(new ArrayAdapter(this.mContext, 2130903061, 2131361882, this.mItems)
    {
      public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
      {
        CustomDialog.ViewHolder localViewHolder;
        label65: CheckBox localCheckBox;
        if (paramView == null)
        {
          paramView = CustomDialog.this.inflater.inflate(2130903061, null);
          localViewHolder = new CustomDialog.ViewHolder(CustomDialog.this);
          localViewHolder.text = ((TextView)paramView.findViewById(2131361880));
          localViewHolder.check = ((CheckBox)paramView.findViewById(2131361881));
          paramView.setTag(localViewHolder);
          localViewHolder.check.setButtonDrawable(2130837558);
          localCheckBox = localViewHolder.check;
          if (CustomDialog.this.mCheckedItem != paramInt)
            break label134;
        }
        for (boolean bool = true; ; bool = false)
        {
          localCheckBox.setChecked(bool);
          localViewHolder.text.setText(CustomDialog.this.mItems[paramInt]);
          return paramView;
          localViewHolder = (CustomDialog.ViewHolder)paramView.getTag();
          label134: break label65:
        }
      }
    });
    if (this.mOnClickListener != null)
      localListView.setOnItemClickListener(new AdapterView.OnItemClickListener(localListView)
      {
        public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          CustomDialog.this.mCheckedItem = paramInt;
          ((ArrayAdapter)this.val$listView.getAdapter()).notifyDataSetChanged();
          CustomDialog.this.mOnClickListener.onClick(CustomDialog.this, paramInt);
        }
      });
    ((FrameLayout)findViewById(2131361815)).addView(localListView);
  }
*/
  private void setTitleContent()
  {
    this.titleTextView.setText(this.titleText);
    this.titleImageView.setImageResource(this.titleImageId);
  }

/*
public Dialog create()
  {
    return this;
  }
*/
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.custom_dialog);
    this.inflater = getLayoutInflater();
    init();
    setTitleContent();
    if (!this.hasTitle)
      this.topPanel.setVisibility(View.GONE);
    switch (this.mMode)
    {
    default:
    	setButtonContent();
    	break;
    case MSGMODE:
    	setMegContent();
    	break;
    case SINGLECHOICEMODE:
    	//setSingleChoiceContent();
    	break;
    case MULTICHOICEMODE:
    	setMultiChoiceContent();
    	break;
    case CUSTOMVIEWMODE:
    	setCutomViewContent();
    	break;
    case ITEMSMODE:
    	setItemsContent();
    	break;
    }
  }

  public CustomDialog setIcon(int paramInt)
  {
    this.titleImageId = paramInt;
    return this;
  }

  public CustomDialog setItems(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mItems = this.mContext.getResources().getTextArray(paramInt);
    this.mOnClickListener = paramOnClickListener;
    this.mMode = ITEMSMODE;
    return this;
  }

  public CustomDialog setItems(CharSequence[] paramArrayOfCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mItems = paramArrayOfCharSequence;
    this.mOnClickListener = paramOnClickListener;
    this.mMode = ITEMSMODE;
    return this;
  }

  public CustomDialog setMessage(CharSequence paramCharSequence)
  {
    this.msg = paramCharSequence;
    this.mMode = MSGMODE;
    return this;
  }

  public CustomDialog setMultiChoiceItems(CharSequence[] paramArrayOfCharSequence, boolean[] paramArrayOfBoolean, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
  {
    this.mItems = paramArrayOfCharSequence;
    this.mCheckedItems = paramArrayOfBoolean;
    this.mOnCheckboxClickListener = paramOnMultiChoiceClickListener;
    this.mMode = MULTICHOICEMODE;
    return this;
  }

  public CustomDialog setNegativeButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.hasNegativeButton = true;
    this.mNegativeButtonText = this.mContext.getResources().getString(paramInt);
    this.mNegativeButtonListener = paramOnClickListener;
    return this;
  }

  public CustomDialog setNegativeButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.hasNegativeButton = true;
    this.mNegativeButtonText = paramCharSequence;
    this.mNegativeButtonListener = paramOnClickListener;
    return this;
  }

  public void setNegativeButtonClickable(boolean paramBoolean)
  {
    this.mNegativeButton.setEnabled(paramBoolean);
  }

  public CustomDialog setNeutralButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.hasNeutralButton = true;
    this.mNeutralButtonText = paramCharSequence;
    this.mNeutralButtonListener = paramOnClickListener;
    return this;
  }

  public void setNoTitle()
  {
    this.hasTitle = false;
  }

  public CustomDialog setPositiveButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.hasPositiveButton = true;
    this.mPositiveButtonText = this.mContext.getResources().getString(paramInt);
    this.mPositiveButtonListener = paramOnClickListener;
    return this;
  }

  public CustomDialog setPositiveButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.hasPositiveButton = true;
    this.mPositiveButtonText = paramCharSequence;
    this.mPositiveButtonListener = paramOnClickListener;
    return this;
  }

  public CustomDialog setSingleChoiceItems(CharSequence[] paramArrayOfCharSequence, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mItems = paramArrayOfCharSequence;
    this.mCheckedItem = paramInt;
    this.mOnClickListener = paramOnClickListener;
    this.mMode = SINGLECHOICEMODE;
    return this;
  }

  public CustomDialog setTitle(String paramString)
  {
    this.titleText = paramString;
    return this;
  }

  public void setTitle(int paramInt)
  {
    this.titleText = this.mContext.getResources().getString(paramInt);
  }

  public void setTitle(CharSequence paramCharSequence)
  {
    this.titleText = paramCharSequence;
  }

  public CustomDialog setView(View paramView)
  {
    this.customView = paramView;
    this.mMode = CUSTOMVIEWMODE;
    return this;
  }

  public class ViewHolder
  {
    public CheckBox check;
    public TextView text;

    public ViewHolder()
    {
    }
  }
}