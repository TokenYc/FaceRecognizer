package edu.uj.faceRecognizer.faceRecognition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.File;

// ----------------------------------------------------------------------

public class MainActivity extends Activity {
    private FrameLayout layout;
    private FaceView faceView;
    private Preview preview;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private File storageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        createDirectories();
    }

    private void createDirectories() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/faceRecognizer/");
        if (!folder.exists()) {
            folder.mkdir();
        }
        folder = new File(Environment.getExternalStorageDirectory() + "/faceRecognizerTest/");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        startActivity(intent);
    }



    public void startCamera(View view) {

        // Create our Preview view and set it as the content of our activity.
        try {
            layout = new FrameLayout(this);
            faceView = new FaceView(this);
            preview = new Preview(this, faceView);
            layout.addView(preview);
            layout.addView(faceView);
            setContentView(layout);
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(this).setMessage(e.getMessage()).create().show();
        }
    }

    public void stopPreview() {
        preview.stopPreview();
    }

    public void startPreview() {
        preview.startPreview();
    }

//    public void setEmail(View view) {
//        startActivity(new Intent(this, SetEmail.class));
//    }

}