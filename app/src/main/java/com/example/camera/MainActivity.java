package com.example.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FrameLayout cameraFrame;
    private Camera camera;
    private ImageView mivimage;

    private boolean currentmode;

    Camera.PictureCallback pictureCallback =  new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            mivimage.setImageBitmap(bitmap);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraFrame = findViewById(R.id.camera_frame);
     ImageButton button = findViewById(R.id.btn_capture);
     mivimage = findViewById(R.id.iv_captureimage);
     ImageButton switchcamera = findViewById(R.id.btn_switch_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new  String[]{Manifest.permission.CAMERA}, 1000);
            }else{
                initiateCamera(true);
            }
        }else {
            initiateCamera(true);
        }

        switchcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                camera.stopPreview();

                boolean currentFocus = currentmode == true ? false : true;

                initiateCamera(currentFocus);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null,null,pictureCallback);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initiateCamera(true);
            }
            Toast.makeText(MainActivity.this, "user Denied Permission " ,Toast.LENGTH_LONG).show();

        }
    }

    public void initiateCamera( boolean isback){

        try {
            currentmode = isback;
            int cameraId;
            if (isback){
                cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            }else {
                cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            }

            camera = Camera.open(cameraId);
            CameraSurfaceView surfaceView = new CameraSurfaceView(MainActivity.this,camera);
            cameraFrame.addView(surfaceView);
        }catch (Exception e){

        }

    }
}