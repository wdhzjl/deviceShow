
package com.yunmai.android.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CameraManager
        implements Camera.AutoFocusCallback
{
    public static final int mHeight = 1200;
    public static final int mWidth = 1600;
    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback()
    {
        public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
        {
        }
    };
    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback()
    {
        public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
        {
            try
            {
                String str = FileUtil.newImageName();
                Bitmap localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0,
                        paramArrayOfByte.length);
                ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
                localBitmap.compress(Bitmap.CompressFormat.JPEG, 50, localByteArrayOutputStream);
                Message localMessage = new Message();
                localMessage.what = CameraManager.this.takeType;
                localMessage.obj = str;
                Bundle localBundle = new Bundle();
                localBundle.putByteArray("picData", localByteArrayOutputStream.toByteArray());
                localMessage.setData(localBundle);
                CameraManager.this.mHandler.sendMessage(localMessage);
                localByteArrayOutputStream.flush();
                localByteArrayOutputStream.close();
                localBitmap.recycle();
                return;
            }
            catch (NullPointerException localNullPointerException)
            {
                CameraManager.this.mHandler.sendEmptyMessage(0);
            }
            catch (FileNotFoundException localFileNotFoundException)
            {
                CameraManager.this.mHandler.sendEmptyMessage(0);
            }
            catch (IOException localIOException)
            {
                CameraManager.this.mHandler.sendEmptyMessage(0);
            }
        }
    };
    private Camera mCamera;
    private Handler mHandler;
    private Camera.PictureCallback rawCallback = new Camera.PictureCallback()
    {
        public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
        {
        }
    };
    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback()
    {
        public void onShutter()
        {
        }
    };
    private int takeType = 1;

    public CameraManager(Context paramContext, Handler paramHandler)
    {
        this.mHandler = paramHandler;
    }

    private List<String> getSupportedFocusModes()
    {
        List<String> localList = null;
        Iterator<String> localIterator;
        if (this.mCamera != null)
        {
            localList = this.mCamera.getParameters().getSupportedFocusModes();
            localIterator = localList.iterator();
            String str = "";
            while (localIterator.hasNext()) {
                str += localIterator.next();
            }
            Log.d("path", "------SupportedFocusModes----------->>" + str);
        }
        return localList;
    }

    private void setPictureSize()
    {
        Camera.Parameters parameters = this.mCamera.getParameters();
        parameters.setPictureFormat(256);
        List<Size> localList = parameters.getSupportedPictureSizes();
        Collections.sort(localList, new SizeComparator());
        int picIndex = 0;
        if (localList != null) {
            int size = localList.size();

            for (int i = 0; i < size; i++) {
                int width = localList.get(i).width;
                if (width == 1600) {
                    picIndex = i;
                    break;
                }
                else if (width < 1600) {
                    picIndex = (i - 1) > 0 ? (i - 1) : 0;
                    if (localList.get(picIndex).width > width) {
                        if (width > 1280) {
                            picIndex = i;
                            break;
                        }
                        // else {
                        // return;
                        // }
                    }
                }
            }
            parameters.setPictureSize(((Camera.Size) localList.get(picIndex)).width,
                    ((Camera.Size) localList.get(picIndex)).height);
        }
        mCamera.setParameters(parameters);
    }

    private void takePicture(boolean paramBoolean)
            throws RuntimeException
    {
        if (this.mCamera != null)
            ;
        try
        {
            Camera.Parameters localParameters = this.mCamera.getParameters();
            localParameters.setJpegQuality(100);
            this.mCamera.setParameters(localParameters);
            this.mCamera.takePicture(this.shutterCallback, this.rawCallback, this.jpegCallback);
            return;
        } catch (RuntimeException localRuntimeException)
        {
        }
    }

    public void autoFouce()
    {
        if (this.mCamera == null)
            return;
        this.mCamera.autoFocus(this.autoFocusCallback);
    }

    public void closeCamera()
    {
        if (this.mCamera == null)
            return;
        this.mCamera.stopPreview();
        this.mCamera.release();
        this.mCamera = null;
    }

    public String getDefaultFlashMode()
    {
        if (this.mCamera.getParameters().getSupportedFlashModes() == null)
            return "off";
        return this.mCamera.getParameters().getSupportedFlashModes().get(0);
    }

    public void initDisplay()
    {
        if (this.mCamera == null)
            return;
        this.mCamera.startPreview();
    }

    public boolean isSupportAutoFocus()
    {
        List<String> localList = getSupportedFocusModes();
        if (localList == null)
            return false;
        return localList.contains("auto");
    }

    public boolean isSupportFlash(String paramString)
    {
        if (mCamera == null) {
            return false;
        }
        List<String> localList = mCamera.getParameters().getSupportedFlashModes();
        if (localList == null) {
            return false;
        }
        return localList.contains(paramString);
    }

    public boolean isSupportFocus(String paramString)
    {
        List<String> localList = getSupportedFocusModes();
        if (localList == null)
            return false;
        return localList.contains(paramString);
    }

    public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
    {
        takePicture(paramBoolean);
    }

    public void openCamera(SurfaceHolder paramSurfaceHolder)
            throws RuntimeException, IOException
    {
        if (this.mCamera != null)
            return;
        this.mCamera = Camera.open();
        this.mCamera.setPreviewDisplay(paramSurfaceHolder);
        setPictureSize();
    }

    public void requestFocuse()
    {
        if (this.mCamera == null)
            return;
        this.mCamera.autoFocus(this);
    }

    public void setCameraFlashMode(String paramString)
    {
        Camera.Parameters localParameters = this.mCamera.getParameters();
        localParameters.setFlashMode(paramString);
        this.mCamera.setParameters(localParameters);
    }

    public void setPreviewSize(int paramInt1, int paramInt2)
    {
        if (mCamera == null)
            return;
        Camera.Parameters localParameters = mCamera.getParameters();
        List<Size> localList = localParameters.getSupportedPreviewSizes();
        Collections.sort(localList, new SizeComparator());

        int picIndex = 0;
        for (int i = 0; i < localList.size(); i++) {
            int width = localList.get(i).width;
            if (width == paramInt1) {
                picIndex = i;
                break;
            }
            else if (width < paramInt1) {
                picIndex = (i - 1) > 0 ? (i - 1) : 0;
                if ((paramInt1 - width) < (localList.get(picIndex).width - paramInt1)) {
                    picIndex = i;
                }
                break;
            }
        }

        if (Build.MODEL.startsWith("MI-ONE")) {
            if (Build.VERSION.INCREMENTAL.equals("2.10.12")) {
                localParameters.setPreviewSize(640, 480);
            }
            else {
                localParameters.setPreviewSize(1280, 720);
            }
        }
        else {
            localParameters.setPreviewSize(localList.get(picIndex).width,
                    localList.get(picIndex).height);
        }
        mCamera.setParameters(localParameters);
    }

    public void setTakeIdcardA()
    {
        this.takeType = 1;
    }

    public void setTakeIdcardB()
    {
        this.takeType = 2;
    }

    public void takePicture()
    {
        if (this.mCamera == null)
            return;
        try
        {
            Camera.Parameters localParameters = this.mCamera.getParameters();
            localParameters.setJpegQuality(100);
            this.mCamera.setParameters(localParameters);
            this.mCamera.takePicture(this.shutterCallback, this.rawCallback, this.jpegCallback);
        } catch (RuntimeException localRuntimeException)
        {
        }
    }

    public class SizeComparator
            implements Comparator<Camera.Size>
    {
        public SizeComparator()
        {
        }

        public int compare(Camera.Size paramSize1, Camera.Size paramSize2)
        {
            return paramSize2.width * paramSize2.height - paramSize1.width * paramSize1.height;
        }
    }
}

/*
 * Location:
 * D:\document\bsd_work\一体机资料\测试app\com.newland.activity\classes_dex2jar.jar
 * Qualified Name: com.yunmai.android.other.CameraManager JD-Core Version: 0.5.4
 */
