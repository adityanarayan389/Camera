package com.example.camera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    public CameraSurfaceView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (surfaceHolder!= null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);

                camera.setDisplayOrientation(90);
                Camera .Parameters param = camera.getParameters();
                param.setRotation(Camera.CameraInfo.CAMERA_FACING_BACK);
                camera.setParameters(param);
                camera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null){
            camera.stopPreview();
            camera.release();
        }

    }
}
